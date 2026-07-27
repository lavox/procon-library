package string;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RollingHashTest {
  @Test
  public void test() {
    String str = "qwertyasdfgzxcvb";
    int len = 4;
    long b = 10007;
    RollingHash rhAll = RollingHashBuilder.createWithBase(b, RollingHashBuilder.MOD1000000007).newHash(str.length());
    RollingHash rh = RollingHashBuilder.createWithBase(b, RollingHashBuilder.MOD1000000007).newHash(len);
    rhAll.add(str);
    rh.add(str.substring(0, len - 1));
    for (int i = len - 1; i < str.length(); i++) {
      char c = str.charAt(i);
      rh.add(c);
      assertEquals(rhAll.hash(i + 1 - len, len), rh.hash(i + 1 - len, len));
    }
  }
}
