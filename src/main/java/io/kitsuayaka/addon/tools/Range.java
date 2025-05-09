package io.kitsuayaka.addon.tools;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.InputMismatchException;

/**
 * Instances of the <em>record class</em> <code>Range</code> store
 * the range of a {@code String} match inside {@code StringType},
 * this class also is used heavily inside {@link io.kitsuayaka.addon.types.StringType#split(String, int) <code>StringType.split(String, int)</code>},
 * allowing splitting using a {@code String} delimiter.
 *
 * @param start the start index of the match, inclusive.
 * @param end   the end index of the match, inclusive.
 * @author Ayaka
 * @version {@code 1.0}
 *
 * @see io.kitsuayaka.addon.types.StringType StringType
 */
public record Range(int start, int end) {

    /**
     * Returns the start of the match.
     *.
     * @return The start of the match
     */
    @Override
    public int start() {
        return start;
    }

    /**
     * Returns the end of the match.
     *
     * @return The end of the match.
     */
    @Override
    public int end() {
        return end;
    }

    /**
     * Returns the length of the match, equal to either {@code end - start} or
     * <em>the length of the matched string</em>.
     *
     * @return The length of the match.
     */
    public int range() {
        return end - start;
    }

    /**
     * Returns whether the {@link #start} is equal to the {@link #end} value of this object.
     * @return Whether the {@link #start} is equal to the {@link #end} value of this object.
     */
    public boolean startIsEnd() {
        return range() == 0;
    }

    /**
     * Returns a string representation of this object
     *
     * @return A string representation of this object
     */
    @Override
    public @NotNull String toString() {
        return String.format("Range { start: %s, end: %s; range: %s }", start, end, range());
    }

    public static final Range UNDEFINED = new Range(-1, -1);

    /**
     * Creates a new {@code Range} object from an {@code integer} array with length 2 (start & end).
     * @param range The {@code integer} array with start and end.
     * @return A new {@code Range} object with the value at {@index 0} as {@link #start} and {@code index 1} as {@link #end}.
     * @throws InputMismatchException If the passed in {@code array} isn't of length 2.
     *
     * @see #fromIntArray(int[][])
     */
    @Contract("_ -> new")
    public static @NotNull Range fromIntArray(int @NotNull [] range) throws InputMismatchException {
        if (range.length != 2) throw new InputMismatchException("Expected integer array of length 2, got array of length " + range.length + " instead");
        return new Range(range[0], range[1]);
    }

    /**
     * Creates a new {@code Range[]} based on a passed in {@code 2d integer} array.
     * @param ranges The {@code 2d integer} array to convert.
     * @return A new {@code Range[]} with all {@code array} entries converted using the function {@link #fromIntArray(int[])}.
     * @throws InputMismatchException If any of the items is not an {@code array} of length 2.
     */
    public static Range @NotNull [] fromIntArray(int @NotNull [][] ranges) throws InputMismatchException {
        Range[] ranges1 = new Range[ranges.length];
        for (int i = 0; i < ranges1.length; i++) ranges1[i] = fromIntArray(ranges[i]);
        return ranges1;
    }
}
