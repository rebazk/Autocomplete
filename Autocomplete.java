import java.util.Arrays;
import java.util.Comparator;
import stdlib.In;
import stdlib.StdIn;
import stdlib.StdOut;

public class Autocomplete {
    private Term[] terms; // Array of terms.

    // Constructs an autocomplete data structure from an array of terms.
    public Autocomplete(Term[] terms) {
        // If terms is null, throw exception (Corner Case).
        if (terms == null) {
            throw new NullPointerException("terms is null");
        }
        // Initialize this.terms to a defensive copy of terms.
        this.terms = terms.clone();
        // Sort this.terms in lexicographic order.
        Arrays.sort(this.terms);
    }

    // Returns all terms that start with prefix, in descending order of their weights.
    public Term[] allMatches(String prefix) {
        // If prefix is null, throw exception (Corner Case).
        if (prefix == null) {
            throw new NullPointerException("prefix is null");
        }
        // Find the index i of the first term in terms that starts with prefix
        // and the number of terms n in terms that start with prefix.
        Term temp = new Term(prefix);
        Comparator<Term> prefixorder = Term.byPrefixOrder(prefix.length());
        int i = BinarySearchDeluxe.firstIndexOf(this.terms, temp, prefixorder);
        int n = BinarySearchDeluxe.lastIndexOf(terms, temp, prefixorder);
        // Construct an array matches containing n elements from terms, starting at index i.
        Term[] matches = Arrays.copyOfRange(terms, i, n + 1);
        // Sort matches in reverse order of weight and return the sorted array.
        Arrays.sort(matches, Term.byReverseWeightOrder());
        return matches;
    }

    // Returns the number of terms that start with prefix.
    public int numberOfMatches(String prefix) {
        // If prefix is null, throw exception (Corner Case).
        if (prefix == null) {
            throw new NullPointerException("prefix is null");
        }
        // Find the indices i and j of the first and last term in terms that start with prefix.
        Term temp = new Term(prefix);
        Comparator<Term> prefixorder = Term.byPrefixOrder(prefix.length());
        int i = BinarySearchDeluxe.firstIndexOf(this.terms, temp, prefixorder);
        int j = BinarySearchDeluxe.lastIndexOf(this.terms, temp, prefixorder);
        // Using the indices, compute the number of terms that start with prefix,
        // and return that value.
        return j - i + 1;
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
        Autocomplete autocomplete = new Autocomplete(terms);
        StdOut.print("Enter a prefix (or ctrl-d to quit): ");
        while (StdIn.hasNextLine()) {
            String prefix = StdIn.readLine();
            Term[] results = autocomplete.allMatches(prefix);
            String msg = " matches for \"" + prefix + "\", in descending order by weight:";
            if (results.length == 0) {
                msg = "No matches";
            } else if (results.length > k) {
                msg = "First " + k + msg;
            } else {
                msg = "All" + msg;
            }
            StdOut.printf("%s\n", msg);
            for (int i = 0; i < Math.min(k, results.length); i++) {
                StdOut.println("  " + results[i]);
            }
            StdOut.print("Enter a prefix (or ctrl-d to quit): ");
        }
    }
}
