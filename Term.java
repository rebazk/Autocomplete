import java.util.Arrays;
import java.util.Comparator;
import stdlib.In;
import stdlib.StdOut;

public class Term implements Comparable<Term> {
    private String query; // Query string.
    private long weight; // Query weight.

    // Constructs a term given the associated query string, having weight 0.
    public Term(String query) {
        // If query is null, throw exception (Corner Case).
        if (query == null) {
            throw new NullPointerException("query is null");
        }
        // Initialize instance variables.
        this.query = query;
        weight = 0;
    }

    // Constructs a term given the associated query string and weight.
    public Term(String query, long weight) {
        // If query is null or weight is negative, throw exception (Corner Cases).
        if (query == null) {
            throw new NullPointerException("query is null");
        } else if (weight < 0) {
            throw new IllegalArgumentException("Illegal weight");
        }
        // Initialize instance variables.
        this.query = query;
        this.weight = weight;
    }

    // Returns a string representation of this term.
    public String toString() {
        // Returns a string containing the weight and query separated by a tab.
        return weight + "\t" + query;
    }

    // Returns a comparison of this term and other by query.
    public int compareTo(Term other) {
        // Returns a negative, zero, or positive integer based on whether
        // this.query is less than, equal to, or greater than other.query.
        return query.compareTo(other.query);
    }

    // Returns a comparator for comparing two terms in reverse order of their weights.
    public static Comparator<Term> byReverseWeightOrder() {
        // Returns an object of type ReverseWeightOrder.
        return new ReverseWeightOrder();
    }

    // Returns a comparator for comparing two terms by their prefixes of length r.
    public static Comparator<Term> byPrefixOrder(int r) {
        // If r is negative, throw exception (Corner Case).
        if (r < 0) {
            throw new IllegalArgumentException("Illegal r");
        }
        // Returns an object of type PrefixOrder.
        return new PrefixOrder(r);
    }

    // Reverse-weight comparator.
    private static class ReverseWeightOrder implements Comparator<Term> {
        // Returns a comparison of terms v and w by their weights in reverse order.
        public int compare(Term v, Term w) {
            // Returns a negative, zero, or positive integer based on whether
            // v.weight is less than, equal to, or greater than w.weight.
            return Long.compare(w.weight, v.weight);
        }
    }

    // Prefix-order comparator.
    private static class PrefixOrder implements Comparator<Term> {
        private int r; // Prefix length.

        // Constructs a new prefix order given the prefix length.
        PrefixOrder(int r) {
            // Initialize instance variables.
            this.r = r;
        }

        // Returns a comparison of terms v and w by their prefixes of length r.
        public int compare(Term v, Term w) {
            // Returns a negative, zero, or positive integer based on whether a is
            // less than, equal to, or greater than b, where a is a substring of v
            // of length min(r, v.query.length()) and b is a substring of w of length
            // min(r, w.query.length()).
            String a = v.query.substring(0, Math.min(r, v.query.length()));
            String b = w.query.substring(0, Math.min(r, w.query.length()));
            return a.compareTo(b);
        }
    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        String filename = args[0];
        int k = Integer.parseInt(args[1]);
        In in = new In(filename);
        int N = in.readInt();
        Term[] terms = new Term[N];
        for (int i = 0; i < N; i++) {
            long weight = in.readLong();
            in.readChar();
            String query = in.readLine();
            terms[i] = new Term(query.trim(), weight);
        }
        StdOut.printf("Top %d by lexicographic order:\n", k);
        Arrays.sort(terms);
        for (int i = 0; i < k; i++) {
            StdOut.println(terms[i]);
        }
        StdOut.printf("Top %d by reverse-weight order:\n", k);
        Arrays.sort(terms, Term.byReverseWeightOrder());
        for (int i = 0; i < k; i++) {
            StdOut.println(terms[i]);
        }
    }
}
