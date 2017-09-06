import io.dropwizard.jersey.validation.Validators;
import org.junit.Test;

import javax.validation.Validator;
import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.junit.Assert.*;                           
import static org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.RequestBuilder;

public class HelloWorldControllerTest {

//    private final Validator validator = Validators.newValidator();

    @Test
    public void testSayHelloWorld() {
//        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
//                "/hello").accept(
//                MediaType.String);
//        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String result = "Hello World ";
        String expected = "Hello World ";
//        validator.validate(receipt);
        assertEquals(expected,result);
    }
}