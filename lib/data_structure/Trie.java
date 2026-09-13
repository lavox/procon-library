package data_structure;

import java.util.Arrays;
import java.util.function.IntUnaryOperator;

import primitive.IntArrayList;
import primitive.LongIntMap;

public interface Trie {
	public static final int NONE = -1;
	public static final int ROOT = 0;

	public int size();
	public int alphabetSize();
	public int getChild(int node, int i);
	public int createChild(int node, int i);
	public int getOrCreateChild(int node, int i);
}

class DenseTrie implements Trie {
	private IntArrayList child = null;
	private int alphabetSize = 0;
	private final IntArrayList NONE_LIST;
	protected int size = 0;

	public DenseTrie(int alphabetSize) {
		this(alphabetSize, 1);
	}
	public DenseTrie(int alphabetSize, int defaultCapacity) {
		this.alphabetSize = alphabetSize;
		int[] tmp = new int[alphabetSize];
		Arrays.fill(tmp, NONE);
		NONE_LIST = new IntArrayList(tmp);
		child = new IntArrayList(defaultCapacity * alphabetSize);
		newNode(NONE);
	}
	private int newNode(int idx) {
		int newNode = size++;
		child.addAll(NONE_LIST);
		if (idx != NONE) child.set(idx, newNode);
		return newNode;
	}

	@Override
	public int size() {return size;}
	@Override 
	public int alphabetSize() {return alphabetSize;}
	@Override
	public int getChild(int node, int i) {
		return child.get(index(node, i));
	}
	@Override
	public int createChild(int node, int i) {
		int idx = index(node, i);
		assert child.get(idx) == NONE;
		return newNode(idx);
	}
	@Override
	public int getOrCreateChild(int node, int i) {
		int idx = index(node, i);
		int curChild = child.get(idx);
		if (curChild == NONE) {
			return newNode(idx);
		} else {
			return curChild;
		}
	}

	private int index(int node, int i) {
		assert ROOT <= node && node < size;
		assert 0 <= i && i < alphabetSize;
		return node * alphabetSize + i;
	}
}

class SparseTrie implements Trie {
	private LongIntMap child = null;
	private int alphabetSize = 0;
	protected int size = 0;

	public SparseTrie(int alphabetSize) {
		this(alphabetSize, 1);
	}
	public SparseTrie(int alphabetSize, int defaultSize) {
		this.alphabetSize = alphabetSize;
		this.child = new LongIntMap(defaultSize, NONE);
		newNode(NONE);
	}
	private int newNode(long idx) {
		int newNode = size++;
		if (idx != NONE) child.put(idx, newNode);
		return newNode;
	}

	@Override
	public int size() {return size;}
	@Override 
	public int alphabetSize() {return alphabetSize;}
	@Override
	public int getChild(int node, int i) {
		return child.get(index(node, i));
	}
	@Override
	public int createChild(int node, int i) {
		long idx = index(node, i);
		assert child.get(idx) == NONE;
		return newNode(idx);
	}
	@Override
	public int getOrCreateChild(int node, int i) {
		long idx = index(node, i);
		int curChild = child.get(idx);
		if (curChild == NONE) {
			return newNode(idx);
		} else {
			return curChild;
		}
	}

	private long index(int node, int i) {
		assert ROOT <= node && node < size;
		assert 0 <= i && i < alphabetSize;
		return ((long)node) * alphabetSize + i;
	}
}

class TrieMultiset {
	private Trie trie;
	private IntArrayList passCnt;
	private IntArrayList terminalCnt;
	public TrieMultiset(Trie trie) {
		this(trie, trie.size());
	}
	public TrieMultiset(Trie trie, int defaultCapacity) {
		this.trie = trie;
		this.passCnt = new IntArrayList(defaultCapacity);
		this.terminalCnt = new IntArrayList(defaultCapacity);
		prepareSize(trie.size());
	}
	private void prepareSize(int requiredSize) {
		while (passCnt.size() < requiredSize) {
			passCnt.add(0);
			terminalCnt.add(0);
		}
	}

	public int size() {
		return passCount(Trie.ROOT);
	}
	public int add(int length, int[] data) {
		return add(length, data, null);
	}
	public int add(int length, int[] data, int[] path) {
		assert path == null || path.length >= length + 1;
		int node = Trie.ROOT;
		for (int l = 0; l < length; l++) {
			countUp(node, false);
			if (path != null) path[l] = node;
			node = trie.getOrCreateChild(node, data[l]);
			prepareSize(trie.size());
		}
		countUp(node, true);
		if (path != null) path[length] = node;
		return node;
	}
	public int add(int length, IntUnaryOperator dataAt) {
		return add(length, dataAt, null);
	}
	public int add(int length, IntUnaryOperator dataAt, int[] path) {
		assert path == null || path.length >= length + 1;
		int node = Trie.ROOT;
		for (int l = 0; l < length; l++) {
			countUp(node, false);
			if (path != null) path[l] = node;
			node = trie.getOrCreateChild(node, dataAt.applyAsInt(l));
			prepareSize(trie.size());
		}
		countUp(node, true);
		if (path != null) path[length] = node;
		return node;
	}
	public int remove(int length, int[] data) {
		return remove(length, data, null);
	}
	public int remove(int length, int[] data, int[] path) {
		assert path == null || path.length >= length + 1;
		int node = Trie.ROOT;
		for (int l = 0; l < length; l++) {
			countDown(node, false);
			if (path != null) path[l] = node;
			node = trie.getChild(node, data[l]);
		}
		countDown(node, true);
		if (path != null) path[length] = node;
		return node;
	}
	public int remove(int length, IntUnaryOperator dataAt) {
		return remove(length, dataAt, null);
	}
	public int remove(int length, IntUnaryOperator dataAt, int[] path) {
		assert path == null || path.length >= length + 1;
		int node = Trie.ROOT;
		for (int l = 0; l < length; l++) {
			countDown(node, false);
			if (path != null) path[l] = node;
			node = trie.getChild(node, dataAt.applyAsInt(l));
		}
		countDown(node, true);
		if (path != null) path[length] = node;
		return node;
	}
	int node(int length, int[] data) {
		int node = Trie.ROOT;
		for (int l = 0; l < length && node != Trie.NONE; l++) {
			node = trie.getChild(node, data[l]);
		}
		return node;
	}
	int node(int length, IntUnaryOperator dataAt) {
		int node = Trie.ROOT;
		for (int l = 0; l < length && node != Trie.NONE; l++) {
			node = trie.getChild(node, dataAt.applyAsInt(l));
		}
		return node;
	}
	public int passCount(int node) {
		return node == Trie.NONE ? 0 : passCnt.get(node);
	}
	public int terminalCount(int node) {
		return node == Trie.NONE ? 0 : terminalCnt.get(node);
	}
	public int count(int length, int[] data) {
		return terminalCount(node(length, data));
	}
	public int count(int length, IntUnaryOperator dataAt) {
		return terminalCount(node(length, dataAt));
	}
	public int countPrefix(int length, int[] data) {
		return passCount(node(length, data));
	}
	public int countPrefix(int length, IntUnaryOperator dataAt) {
		return passCount(node(length, dataAt));
	}
	public boolean contains(int length, int[] data) {
		return count(length, data) > 0;
	}
	public boolean contains(int length, IntUnaryOperator dataAt) {
		return count(length, dataAt) > 0;
	}

	private void countUp(int node, boolean terminal) {
		passCnt.set(node, passCnt.get(node) + 1);
		if (terminal) terminalCnt.set(node, terminalCnt.get(node) + 1);
	}
	private void countDown(int node, boolean terminal) {
		passCnt.set(node, passCnt.get(node) - 1);
		if (terminal) terminalCnt.set(node, terminalCnt.get(node) - 1);
	}
}