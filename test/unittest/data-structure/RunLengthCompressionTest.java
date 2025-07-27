import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

public class RunLengthCompressionTest {
	@Test
	public void test() {
		int[] org = new int[] {0, 0, 4, 4, 4, 1, 4, 4};
		ArrayList<RunLength> array = RunLengthCompression.encode(org);
		assertEquals(4, array.size());
		assertEquals(org.length, RunLengthCompression.totalLength(array));
		assertArrayEquals(org, RunLengthCompression.decode(array));
		assertEquals(2, RunLengthCompression.searchIndex(array, 5));
		assertEquals(3, RunLengthCompression.searchIndex(array, 6));
		assertEquals(2, RunLengthCompression.search(array, 6).length);
		array = RunLengthCompression.add(array, new int[] {4, 1, 1});
		assertEquals(5, array.size());
		assertArrayEquals(new int[] {0, 0, 4, 4, 4, 1, 4, 4, 4, 1, 1}, RunLengthCompression.decode(array));

		String str = "aabbbzbb";
		ArrayList<RunLength> array2 = RunLengthCompression.encode(str);
		assertEquals(4, array2.size());
		assertEquals(str.length(), RunLengthCompression.totalLength(array2));
		assertEquals(str, RunLengthCompression.decodeAsString(array2));
		assertEquals(2, RunLengthCompression.searchIndex(array2, 5));
		assertEquals(3, RunLengthCompression.searchIndex(array2, 6));
		assertEquals(2, RunLengthCompression.search(array2, 6).length);
		array2 = RunLengthCompression.add(array2, "bcc");
		assertEquals(5, array2.size());
		assertEquals(str + "bcc", RunLengthCompression.decodeAsString(array2));
	}
}
