/**
 * This class is generated by jOOQ
 */
package generated;


import javax.annotation.Generated;

import org.jooq.Sequence;
import org.jooq.impl.SequenceImpl;


/**
 * Convenience access to all sequences in public
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.4"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Sequences {

	/**
	 * The sequence <code>public.system_sequence_3e585696_f2eb_4913_8381_60ae144d4b36</code>
	 */
	public static final Sequence<Long> SYSTEM_SEQUENCE_3E585696_F2EB_4913_8381_60AE144D4B36 = new SequenceImpl<Long>("system_sequence_3e585696_f2eb_4913_8381_60ae144d4b36", Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT);

	/**
	 * The sequence <code>public.system_sequence_7a43b691_2ddc_4df1_b01a_4162d38e4322</code>
	 */
	public static final Sequence<Long> SYSTEM_SEQUENCE_7A43B691_2DDC_4DF1_B01A_4162D38E4322 = new SequenceImpl<Long>("system_sequence_7a43b691_2ddc_4df1_b01a_4162d38e4322", Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT);
}
