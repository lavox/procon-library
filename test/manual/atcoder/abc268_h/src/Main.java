import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.function.IntUnaryOperator;
import java.util.function.LongUnaryOperator;
import java.util.function.UnaryOperator;

import java.util.ArrayDeque;

// template & library : https://github.com/lavox/procon-library
public class Main {
	public static void main(String[] args) {
		Main o = new Main();
		o.solve();
	}

	public void solve() {
		FastScanner sc = new FastScanner(System.in);
		String S = sc.next();
		int N = sc.nextInt();
		String[] T = sc.nextStringArray(N);
		int K = 26;
		AhoCorasick ac = new AhoCorasick(K, 'a');
		for (int i = 0; i < N; i++) {
			ac.addWord(T[i], i);
		}
		ac.buildLink();

		AhoCorasick.Node cur = ac.root();
		int ans = 0;
		for (int i = 0; i < S.length(); i++) {
			int c = S.charAt(i) - 'a';
			cur = cur.next(c);
			if (cur.hasOutput()) {
				ans++;
				cur = ac.root();
			}
		}
		System.out.println(ans);
	}

	public static final char LF = '\n';
	public static final char SPACE = ' ';
	public static final String YES = "Yes";
	public static final String NO = "No";
	public static void print(int[] array, char sep) {
		print(array, sep, n -> n, 0, array.length);
	}
	public static void print(int[] array, char sep, IntUnaryOperator conv) {
		print(array, sep, conv, 0, array.length);
	}
	public static void print(int[] array, char sep, IntUnaryOperator conv, int start, int end) {
		StringBuilder ans = new StringBuilder();
		for (int i = start; i < end; i++) {
			ans.append(conv.applyAsInt(array[i]));
			ans.append(sep);
		}
		if (ans.length() > 0) ans.deleteCharAt(ans.length() - 1);
		System.out.println(ans.toString());
	}
	public static void print(long[] array, char sep) {
		print(array, sep, n -> n, 0, array.length);
	}
	public static void print(long[] array, char sep, LongUnaryOperator conv) {
		print(array, sep, conv, 0, array.length);
	}
	public static void print(long[] array, char sep, LongUnaryOperator conv, int start, int end) {
		StringBuilder ans = new StringBuilder();
		for (int i = start; i < end; i++) {
			ans.append(conv.applyAsLong(array[i]));
			ans.append(sep);
		}
		if (ans.length() > 0) ans.deleteCharAt(ans.length() - 1);
		System.out.println(ans.toString());
	}
	public static <T> void print(T[] array, char sep) {
		print(array, sep, n -> n, 0, array.length);
	}
	public static <T> void print(T[] array, char sep, LongUnaryOperator conv) {
		print(array, sep, conv, 0, array.length);
	}
	public static <T> void print(T[] array, char sep, LongUnaryOperator conv, int start, int end) {
		StringBuilder ans = new StringBuilder();
		for (int i = start; i < end; i++) {
			ans.append(array[i].toString());
			ans.append(sep);
		}
		if (ans.length() > 0) ans.deleteCharAt(ans.length() - 1);
		System.out.println(ans.toString());
	}
	public static void printYesNo(boolean[] array, char sep) {
		printYesNo(array, sep, n -> n, 0, array.length);
	}
	public static void printYesNo(boolean[] array, char sep, LongUnaryOperator conv) {
		printYesNo(array, sep, conv, 0, array.length);
	}
	public static void printYesNo(boolean[] array, char sep, LongUnaryOperator conv, int start, int end) {
		StringBuilder ans = new StringBuilder();
		for (int i = start; i < end; i++) {
			ans.append(array[i] ? YES : NO);
			ans.append(sep);
		}
		if (ans.length() > 0) ans.deleteCharAt(ans.length() - 1);
		System.out.println(ans.toString());
	}
	public static <T> void print(ArrayList<T> array, char sep) {
		print(array, sep, a -> a, 0, array.size());
	}
	public static <T> void print(ArrayList<T> array, char sep, UnaryOperator<T> conv) {
		print(array, sep, conv, 0, array.size());
	}
	public static <T> void print(ArrayList<T> array, char sep, UnaryOperator<T> conv, int start, int end) {
		StringBuilder ans = new StringBuilder();
		for (int i = start; i < end; i++) {
			ans.append(conv.apply(array.get(i)).toString());
			ans.append(sep);
		}
		if (ans.length() > 0) ans.deleteCharAt(ans.length() - 1);
		System.out.println(ans.toString());
	}
	public static void print(int a) { System.out.println(a); }
	public static void print(long a) { System.out.println(a); }
	public static <T> void print(T s) { System.out.println(s.toString()); }
	public static void printYesNo(boolean yesno) {
		System.out.println(yesno ? YES : NO);
	}
	public static void printDouble(double val, int digit) {
		System.out.println(String.format("%." + digit + "f", val));
	}
	public static void print(int... a) { print(a, SPACE); }
	public static void print(long... a) { print(a, SPACE); }
	@SuppressWarnings("unchecked")
	public static <T> void print(T... s) { print(s, SPACE); }
}
class FastScanner {
	private final InputStream in;
	private final byte[] buf = new byte[1024];
	private int ptr = 0;
	private int buflen = 0;
	FastScanner( InputStream source ) { this.in = source; }
	private boolean hasNextByte() {
		if ( ptr < buflen ) return true;
		else {
			ptr = 0;
			try { buflen = in.read(buf); } catch (IOException e) { e.printStackTrace(); }
			if ( buflen <= 0 ) return false;
		}
		return true;
	} 
	private int readByte() { if ( hasNextByte() ) return buf[ptr++]; else return -1; } 
	private boolean isPrintableChar( int c ) { return 33 <= c && c <= 126; }
	private boolean isNumeric( int c ) { return '0' <= c && c <= '9'; }
	private void skipToNextPrintableChar() { while ( hasNextByte() && !isPrintableChar(buf[ptr]) ) ptr++; }
	public boolean hasNext() { skipToNextPrintableChar(); return hasNextByte(); }
	public String next() {
		if ( !hasNext() ) throw new NoSuchElementException();
		StringBuilder ret = new StringBuilder();
		int b = readByte();
		while ( isPrintableChar(b) ) { ret.appendCodePoint(b); b = readByte(); }
		return ret.toString();
	}
	public long nextLong() {
		if ( !hasNext() ) throw new NoSuchElementException();
		long ret = 0;
		int b = readByte();
		boolean negative = false;
		if ( b == '-' ) { negative = true; if ( hasNextByte() ) b = readByte(); }
		if ( !isNumeric(b) ) throw new NumberFormatException();
		while ( true ) {
			if ( isNumeric(b) ) ret = ret * 10 + b - '0';
			else if ( b == -1 || !isPrintableChar(b) ) return negative ? -ret : ret;
			else throw new NumberFormatException();
			b = readByte();
		}
	}
	public int nextInt() { return (int)nextLong(); }
	public double nextDouble() { return Double.parseDouble(next()); }
	public int[] nextIntArray(int N) { return nextIntArray(N, n -> n); }
	public int[] nextIntArray(int N, IntUnaryOperator conv) {
		int[] ret = new int[N];
		for (int i = 0; i < N; i++) ret[i] = conv.applyAsInt(nextInt());
		return ret;
	}
	public long[] nextLongArray(int N) {
		long[] ret = new long[N];
		for (int i = 0; i < N; i++) ret[i] = nextLong();
		return ret;
	}
	public String[] nextStringArray(int N) {
		String[] ret = new String[N];
		for (int i = 0; i < N; i++) ret[i] = next();
		return ret;
	}
	public int[][] nextIntMatrix(int N, int M) { return nextIntMatrix(N, M, n -> n); }
	public int[][] nextIntMatrix(int N, int M, IntUnaryOperator conv) {
		int[][] ret = new int[N][M];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				ret[i][j] = conv.applyAsInt(nextInt());
			}
		}
		return ret;
	}
	public long[][] nextLongMatrix(int N, int M) {
		long[][] ret = new long[N][M];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				ret[i][j] = nextLong();
			}
		}
		return ret;
	}
	public String[][] nextStringMatrix(int N, int M) {
		String[][] ret = new String[N][M];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				ret[i][j] = next();
			}
		}
		return ret;
	}
}

// === begin: string/AhoCorasick.java ===
class AhoCorasick {
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
// === end: string/AhoCorasick.java ===
