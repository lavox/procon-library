package data_structure;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TrieTest {
	@Test
	public void testDenseTrie() {
		testTrie(new DenseTrie(3));
	}

	@Test
	public void testSparseTrie() {
		testTrie(new SparseTrie(3));
	}

	private void testTrie(Trie trie) {
		assertEquals(1, trie.size());
		assertEquals(3, trie.alphabetSize());
		assertEquals(Trie.NONE, trie.getChild(Trie.ROOT, 0));

		int node0 = trie.createChild(Trie.ROOT, 0);
		assertEquals(2, trie.size());
		assertEquals(node0, trie.getChild(Trie.ROOT, 0));
		assertEquals(node0, trie.getOrCreateChild(Trie.ROOT, 0));
		assertEquals(2, trie.size());

		int node01 = trie.getOrCreateChild(node0, 1);
		int node2 = trie.getOrCreateChild(Trie.ROOT, 2);
		int node22 = trie.getOrCreateChild(node2, 2);

		assertEquals(node01, trie.getChild(node0, 1));
		assertEquals(node2, trie.getChild(Trie.ROOT, 2));
		assertEquals(node22, trie.getChild(node2, 2));
		assertEquals(Trie.NONE, trie.getChild(node0, 0));
		assertEquals(5, trie.size());
	}

	@Test
	public void testSparseTrieWithLargeAlphabet() {
		Trie trie = new SparseTrie(Integer.MAX_VALUE);

		int node0 = trie.getOrCreateChild(Trie.ROOT, 0);
		int node1 = trie.getOrCreateChild(Trie.ROOT, 1);
		int node11 = trie.getOrCreateChild(node1, 1);

		assertEquals(node0, trie.getChild(Trie.ROOT, 0));
		assertEquals(node1, trie.getChild(Trie.ROOT, 1));
		assertEquals(node11, trie.getChild(node1, 1));
		assertEquals(4, trie.size());
	}

	@Test
	public void testDenseTrieMultiset() {
		testTrieMultiset(new DenseTrie(3));
	}

	@Test
	public void testSparseTrieMultiset() {
		testTrieMultiset(new SparseTrie(3));
	}

	private void testTrieMultiset(Trie trie) {
		TrieMultiset multiset = new TrieMultiset(trie);
		int[] empty = new int[0];
		int[] a = new int[] {0};
		int[] ab = new int[] {0, 1};
		int[] ac = new int[] {0, 2};
		int[] missing = new int[] {1};

		assertEquals(0, multiset.size());
		assertEquals(0, multiset.count(0, empty));
		assertEquals(0, multiset.countPrefix(0, empty));

		assertEquals(Trie.ROOT, multiset.add(0, empty));

		int[] addPath = new int[ab.length + 1];
		int nodeAB = multiset.add(ab.length, ab, addPath);
		assertEquals(Trie.ROOT, addPath[0]);
		assertEquals(nodeAB, addPath[ab.length]);

		multiset.add(ab.length, ab);
		int nodeA = multiset.add(a.length, a);
		int nodeAC = multiset.add(ac.length, i -> ac[i]);

		assertEquals(5, multiset.size());
		assertEquals(1, multiset.count(0, empty));
		assertEquals(5, multiset.countPrefix(0, empty));
		assertEquals(1, multiset.count(a.length, a));
		assertEquals(4, multiset.countPrefix(a.length, a));
		assertEquals(2, multiset.count(ab.length, ab));
		assertEquals(2, multiset.countPrefix(ab.length, ab));
		assertEquals(1, multiset.count(ac.length, i -> ac[i]));
		assertEquals(0, multiset.count(missing.length, missing));
		assertEquals(0, multiset.countPrefix(missing.length, missing));
		assertTrue(multiset.contains(ab.length, ab));
		assertFalse(multiset.contains(missing.length, i -> missing[i]));

		assertEquals(5, multiset.passCount(Trie.ROOT));
		assertEquals(1, multiset.terminalCount(Trie.ROOT));
		assertEquals(4, multiset.passCount(nodeA));
		assertEquals(1, multiset.terminalCount(nodeA));
		assertEquals(2, multiset.passCount(nodeAB));
		assertEquals(2, multiset.terminalCount(nodeAB));
		assertEquals(1, multiset.passCount(nodeAC));
		assertEquals(1, multiset.terminalCount(nodeAC));

		int[] removePath = new int[ab.length + 1];
		assertEquals(nodeAB, multiset.remove(ab.length, ab, removePath));
		assertArrayEquals(addPath, removePath);
		assertEquals(4, multiset.size());
		assertEquals(1, multiset.count(ab.length, ab));
		assertEquals(3, multiset.countPrefix(a.length, a));

		multiset.remove(ac.length, i -> ac[i]);
		assertEquals(3, multiset.size());
		assertEquals(0, multiset.count(ac.length, ac));
		assertEquals(2, multiset.countPrefix(a.length, a));

		multiset.remove(0, empty);
		assertEquals(2, multiset.size());
		assertEquals(0, multiset.count(0, empty));
		assertEquals(2, multiset.countPrefix(0, empty));
		assertEquals(4, trie.size());
	}
}
