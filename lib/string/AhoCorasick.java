package string;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.function.IntUnaryOperator;

public class AhoCorasick {
	private Node root = null;
	private int K = 0;
	private char base = 0;
	private ArrayList<Node> allNodes = null;

	public AhoCorasick(int K) {
		this(K, (char)0);
	}
	public AhoCorasick(int K, char base) {
		this.K = K;
		this.base = base;
		this.allNodes = new ArrayList<>();
		this.root = new Node();
	}
	public Node addWord(String word, int wordId) {
		return addWord((i) -> word.charAt(i) - base, word.length(), wordId);
	}
	public Node addWord(int[] word, int wordId) {
		return addWord((i) -> word[i], word.length, wordId);
	}
	public Node addWord(IntUnaryOperator op, int n, int wordId) {
		Node cur = root;
		for (int i = 0; i < n; i++) {
			cur = cur.getOrCreateChild(op.applyAsInt(i));
		}
		cur.wordId = wordId;
		return cur;
	}
	public void buildLink() {
		root.failure = root;
		ArrayDeque<Node> queue = new ArrayDeque<>();
		for (int c = 0; c < K; c++) {
			Node n = root.child(c);
			if (n == null) {
				root.setChild(c, root);
			} else {
				n.failure = root;
				queue.addLast(n);
			}
		}
		while (queue.size() > 0) {
			Node cur = queue.pollFirst();
			for (int c = 0; c < K; c++) {
				Node ch = cur.child(c);
				Node fch = cur.failure.child(c);
				if (ch == null) {
					cur.setChild(c, fch == null ? root: fch);
				} else {
					ch.failure = fch == null ? root : fch;
					ch.outputLink = fch == null ? null : (fch.isOutput() ? fch : fch.outputLink);
					queue.addLast(ch);
				}
			}
		}
	}
	public int nodeSize() {
		return allNodes.size();
	}
	public Node node(int id) {
		return allNodes.get(id);
	}
	public Node root() {
		return root;
	}

	public class Node {
		private Node parent = null;
		private int val = 0;
		private int depth = 0;
		private int wordId = -1;
		private Node[] child = null;
		private Node failure = null;
		private Node outputLink = null;
		private int id = 0;

		private Node() {
			this(-1, null);
		}
		private Node(int val, Node parent) {
			this.val = val;
			this.parent = parent;
			this.depth = parent == null ? 0 : parent.depth + 1;
			this.id = allNodes.size();
			allNodes.add(this);
		}
		private Node getOrCreateChild(int c) {
			if (child == null) child = new Node[K];
			if (child[c] == null) child[c] = new Node(c, this);
			return child[c];
		}
		private Node child(int c) {
			return child == null ? null : child[c];
		}
		private void setChild(int c, Node n) {
			if (child == null) child = new Node[K];
			child[c] = n; 
		}
		public Node parent() {
			return parent;
		}
		public int id() {
			return id;
		}
		public int val() {
			return val;
		}
		public int[] wordIntArray() {
			int[] ret = new int[depth];
			this._wordIntArray(ret);
			return ret;
		}
		private void _wordIntArray(int[] ret) {
			if (depth > 0) {
				ret[depth - 1] = val;
				parent._wordIntArray(ret);
			}
		}
		public String word() {
			char[] ret = new char[depth];
			this._wordArray(ret);
			return new String(ret);
		}
		private void _wordArray(char[] ret) {
			if (depth > 0) {
				ret[depth - 1] = (char)(val + base);
				parent._wordArray(ret);
			}
		}
		public Node failure() {
			return failure;
		}
		public boolean isOutput() {
			return wordId != -1;
		}
		public int wordId() {
			return wordId;
		}
		public boolean hasOutput() {
			return isOutput() || outputLink != null;
		}
		public Node nextOutput() {
			return outputLink;
		}
		public ArrayList<Node> outputWords() {
			if (!hasOutput()) return null;
			ArrayList<Node> ret = new ArrayList<>();
			Node cur = this;
			while (cur != null) {
				if (cur.isOutput()) ret.add(cur);
				cur = cur.outputLink;
			}
			return ret;
		}
		public Node next(int c) {
			return child[c];
			// return child == null || child[c] == null ? root : child[c];
		}
		@Override
		public int hashCode() {
			return id;
		}
		@Override
		public boolean equals(Object o) {
			if (o instanceof Node) {
				Node n = (Node) o;
				return id == n.id;
			}
			return false;
		}
		@Override
		public String toString() {
			return String.format("val:%d, depth:%d, id:%d, failure_id:%d", val, depth, id, failure == null ? -1 : failure.id);
		}
	}
}
