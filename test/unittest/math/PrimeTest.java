package math;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.Test;

public class PrimeTest {
	@Test
	public void test() {
		Prime prime = new Prime(20);
		
		assertArrayEquals(new int[] {2, 3, 5, 7, 11, 13, 17, 19}, prime.primes());
		assertTrue(prime.isPrime(13));
		assertTrue(prime.isPrime(23));
		assertFalse(prime.isPrime(12));
		assertFalse(prime.isPrime(22));

		prime = new Prime(50);
		for (int i = 1; i <= 2500; i++) {
			ArrayList<Prime.Factor> factor = prime.factorize(i);
			long[] divisors = prime.divisors(i);
			int mul = 1;
			int divCnt = 1;
			for (int j = 0; j < factor.size(); j++) {
				if (j > 0) assertTrue(factor.get(j - 1).base < factor.get(j).base);
				Prime.Factor f = factor.get(j);
				for (int k = 0; k < f.pow; k++) mul *= f.base;
				divCnt *= 1 + f.pow;
			}
			assertEquals(i, mul);
			HashSet<Integer> divs = new HashSet<>();
			for (long d: divisors) divs.add((int)d);
			assertEquals(divCnt, divs.size());
			for (int d: divs) {
				assertTrue(i % d == 0);
			}
		}
	}
}