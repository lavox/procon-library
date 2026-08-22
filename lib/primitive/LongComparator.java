package primitive;

// https://github.com/lavox/procon-library/blob/main/lib/primitive/LongComparator.java
@FunctionalInterface
public interface LongComparator {
    int compare(long a, long b);
}
