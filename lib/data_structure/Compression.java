package data_structure;
import java.util.Arrays;

public class Compression {
	long[] vals = null;
	
	public Compression(long[] vals) {
		long[] arr = Arrays.copyOf(vals, vals.length);
		int sz = 0;
		Arrays.sort(arr);
		for ( int i = 0 ; i < vals.length ; i++ ) {
			if ( i == 0 || arr[i] != arr[i - 1] ) sz++;
		}
		this.vals = new long[sz];
		int vi = 0;
		for ( int i = 0 ; i < vals.length ; i++ ) {
			if ( i == 0 || arr[i] != arr[i - 1] ) this.vals[vi++] = arr[i];
		}
	}
	public long toValue(int i) {
		return vals[i];
	}
	public int toIndex(long v) {
		int idx = search(v);
		if ( idx < 0 ) return -1;
		return vals[idx] == v ? idx : -1;
	}
	public int toIndexFloor(long v) {
		return search(v);
	}
	public int toIndexCeiling(long v) {
		int idx = search(v);
		if ( idx < 0 ) return 0;
		return vals[idx] == v ? idx : idx + 1;
	}
	public int size() {
		return vals.length;
	}
	private int search(long v) {
		if ( vals[vals.length - 1] <= v ) return vals.length - 1;
		if ( v < vals[0] ) return -1;
		int min = 0;
		int max = vals.length - 1;
		while ( max - min > 1 ) {
			int mid = ( min + max ) / 2;
			if ( vals[mid] <= v ) { min = mid; } else { max = mid; }
		}
		return min;
	}
}
