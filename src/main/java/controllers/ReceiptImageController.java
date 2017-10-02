package controllers;

import api.ReceiptSuggestionResponse;
import com.google.cloud.vision.v1.*;
import com.google.protobuf.ByteString;
import java.math.BigDecimal;
import java.util.*;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import org.hibernate.validator.constraints.NotEmpty;

import static java.lang.System.out;

@Path("/images")
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.APPLICATION_JSON)
public class ReceiptImageController {
    private final AnnotateImageRequest.Builder requestBuilder;

    public ReceiptImageController() {
        // DOCUMENT_TEXT_DETECTION is not the best or only OCR method available
        Feature ocrFeature = Feature.newBuilder().setType(Feature.Type.TEXT_DETECTION).build();
        this.requestBuilder = AnnotateImageRequest.newBuilder().addFeatures(ocrFeature);

    }

    /**
     * This borrows heavily from the Google Vision API Docs.  See:
     * https://cloud.google.com/vision/docs/detecting-fulltext
     *
     * YOU SHOULD MODIFY THIS METHOD TO RETURN A ReceiptSuggestionResponse:
     *
     * public class ReceiptSuggestionResponse {
     *     String merchantName;
     *     String amount;
     * }
     */
    @POST
    public ReceiptSuggestionResponse parseReceipt(@NotEmpty String base64EncodedImage) throws Exception {
        Image img = Image.newBuilder().setContent(ByteString.copyFrom(Base64.getDecoder().decode(base64EncodedImage))).build();
        AnnotateImageRequest request = this.requestBuilder.setImage(img).build();

        try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
            BatchAnnotateImagesResponse responses = client.batchAnnotateImages(Collections.singletonList(request));
            AnnotateImageResponse res = responses.getResponses(0);

            String merchantName = null;
            BigDecimal amount = null;

            // Your Algo Here!!
            // Sort text annotations by bounding polygon.  Top-most non-decimal text is the merchant
            // bottom-most decimal text is the total amount
            TreeMap<Integer,List<String>> textMap =  new TreeMap<Integer,List<String>>();
            for(int i = 1; i < res.getTextAnnotationsList().size(); i++) {
                EntityAnnotation annotation = res.getTextAnnotationsList().get(i);
                Integer yVertex =annotation.getBoundingPoly().getVertices(0).getY();
                if (textMap.containsKey(yVertex)) {
                    List<String> currText = textMap.get(yVertex);
                    currText.add(annotation.getDescription());
                    textMap.put(annotation.getBoundingPoly().getVertices(0).getY(),currText);
                }else {
                    List<String> currText = new ArrayList<String>();
                    currText.add(annotation.getDescription());
                    textMap.put(annotation.getBoundingPoly().getVertices(0).getY(), currText);
                }
            }

            for(Map.Entry<Integer,List<String>> entry : textMap.entrySet()) {
                List<String> value = entry.getValue();
                for (String t : value){
                    if(!t.matches(".*\\d.*")){
                        merchantName = t;
                        break;
                    }
                }
                if (merchantName != null) { break;}
            }

            NavigableSet<Integer> reverseVertices = textMap.descendingKeySet();
            final String regExp = "[0-9]+([,.][0-9]{1,2})?";
            for (Integer ind: reverseVertices){
                List<String> value = textMap.get(ind);
                for (String t : value){
                    if( t.charAt(0)=='$'){
                        String tmpt = t.substring(1);
                        if (tmpt.matches(regExp)){
                            amount = new BigDecimal(tmpt);
                            break;
                        }
                    }
                    if (t.matches(regExp)){
                        amount = new BigDecimal(t);
                        break;
                    }
                }
                if (amount != null) { break;}
            }

            return new ReceiptSuggestionResponse(merchantName, amount);
        }
    }
}
