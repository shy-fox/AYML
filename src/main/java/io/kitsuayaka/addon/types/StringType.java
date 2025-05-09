package io.kitsuayaka.addon.types;

import io.kitsuayaka.addon.functions.ArrayTools;
import io.kitsuayaka.addon.functions.StringRangeIterator;
import io.kitsuayaka.addon.tools.Range;

import io.kitsuayaka.core.annotations.StatusMarkers;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.*;

import java.util.*;

/**
 * Objects of the class {@code StringType} behave similarly to the built-in {@link java.lang.String}, although, it cannot
 * use string literals like {@code "abc"}. It can be created using several constructors, the default one which normally
 * can, and should be used is using {@code String} &mdash; {@link StringType#StringType(String)}. It retains the
 * functionality through using {@link CharSequence} and {@link Comparable}. Different methods and operations using this
 * class can be done in al of the other children of {@link BaseType}; e.g. using {@code StringType} in {@link NumberType}.
 * <hr/>
 * Concatenation can be used through several methods, such as {@link #insert(char, int)}, {@link #append(String)} or
 * {@link #push(char)}. See the example below:
 * <blockquote>
 * <pre>{@code StringType t = new StringType("Some value");
 * StringType u = new StringType("Some other value");
 * StringType v = t.append(u);}</pre>
 * </blockquote>
 * Other functionality of this class comes from implementing the interface {@link Iterable}, thus allowing for it to be
 * used in {@code for-loops}, e.g. iteration through all characters without having to cast it to a {@code char[]}.
 * Iteration of this object means iteration through this object's contained {@code char[]}, e.g.
 * <blockquote>
 * <pre>{@code StringType t = new StringType("Iteration of this object");
 * for (char c : t) {
 *     // do something with this...
 * }}</pre>
 * </blockquote>
 * It can also be used with {@link io.kitsuayaka.addon.tools.ComplexStringType ComplexStringType}, which allows for
 * formatting, similar to {@link String#format(String, Object...)}, which does use similar definitions, using {@code $}
 * as the prefix. In addition to that, it allows for other handling of iteration,
 * using {@link #forEachChar(StringRangeIterator)}. Which will do the same as {@code for (char c : o)}, iterating through
 * all {@code char}. It also allows for iterating through a specific range using {@link #forRangeRange(int, StringRangeIterator)}.
 * <hr/>
 * General checks that can be performed using this class also extend to {@code length}, allowing to compare the length of
 * the contained {@code String} against specific {@code lengths}, without having to use {@code if (t.length() == x)} or
 * {@code if (t.length() < x)}. These methods simple handle these checks: {@link #isLength(int)}, {@link #isShorterThan(int)}
 * and {@link #isLongerThan(int)}. But if those are used to append and lengthen a {@code String}, it is recommended to use
 * {@link #matchLengthFromStart(String, char, int)} or {@link #padLengthFromStart(String, int)}. Those methods also exist
 * as <em>non-static</em> members of this class, {@link #matchLengthFromStart(char, int)} and {@link #padLengthFromStart(int)}.
 * <hr/>
 * This object is the core of {@link io.kitsuayaka.core.elements.StringElement StringElement}, which will then be handled
 * by any further functions and methods built into this {@code YAML} file handler.
 * <hr/>
 * <strong>NOTE: This class serves as an {@code addon} to the core functionality as this program and might get exported
 * into its own library.</strong>
 *
 * @author Ayaka (<a href="https://github.com/shy-fox">GitHub</a>)
 * @version <code>1.6.2</code>
 * @since <code>1.0.0</code>
 */
@StatusMarkers.Addon
public final class StringType extends BaseType<String> implements Serializable,
        CharSequence,
        Iterable<Character>,
        Comparable<StringType> {

    @Serial
    private static final long serialVersionUID = 102L;

    private int lastIndex;
    private int length;

    private transient char[] chars;

    /**
     * Creates a new instance of a {@code StringType} using another one as a template.
     *
     * @param stringType The {@code StringType} to use as a template.
     * @see #StringType()
     * @see #StringType(char)
     * @see #StringType(char[])
     * @see #StringType(String)
     * @see #StringType(StringBuilder)
     * @see #StringType(Object)
     * @since <code>1.0.3</code>
     */
    public StringType(@NotNull StringType stringType) {
        this(stringType.chars);
    }

    /**
     * Creates a new instance of a {@code StringType} with an empty {@code String} as value.
     *
     * @see #StringType(char)
     * @see #StringType(char[])
     * @see #StringType(String)
     * @see #StringType(StringBuilder)
     * @see #StringType(StringType)
     * @see #StringType(Object)
     * @since <code>1.0.0</code>
     */
    public StringType() {
        this("");
    }

    /**
     * Creates a new instance of a {@code StringType} with a given {@code String} as value.
     *
     * @param string The value of a {@code StringType}.
     * @see #StringType()
     * @see #StringType(char)
     * @see #StringType(char[])
     * @see #StringType(StringBuilder)
     * @see #StringType(StringType)
     * @see #StringType(Object)
     * @since <code>1.0.0</code>
     */
    public StringType(@NotNull String string) {
        this(string.toCharArray());
    }

    /**
     * Creates a new instance of a {@code StringType} with a given {@code character} as value.
     *
     * @param c The value of a {@code StringType}.
     * @see #StringType()
     * @see #StringType(char[])
     * @see #StringType(String)
     * @see #StringType(StringBuilder)
     * @see #StringType(StringType)
     * @see #StringType(Object)
     * @since <code>1.0.0</code>
     */
    public StringType(char c) {
        this(String.valueOf(c));
    }

    /**
     * Creates a new instance of a {@code StringType} with a given {@code StringBuilder}, taking its value as this {@code StringType}'s value.
     *
     * @param sb The {@code StringBuilder} to take the value of.
     * @see #StringType()
     * @see #StringType(char)
     * @see #StringType(char[])
     * @see #StringType(String)
     * @see #StringType(StringType)
     * @see #StringType(Object)
     * @since <code>1.0.0</code>
     */
    public StringType(@NotNull StringBuilder sb) {
        this(sb.toString());
    }

    /**
     * Creates a new instance of a {@code StringType} with a given {@code char[]} as value.
     *
     * @param chars The value of a {@code StringType}.
     * @see #StringType()
     * @see #StringType(char)
     * @see #StringType(String)
     * @see #StringType(StringBuilder)
     * @see #StringType(StringType)
     * @see #StringType(Object)
     * @since <code>1.0.0</code>
     */
    public StringType(char[] chars) {
        super(String.valueOf(chars));
        length = chars.length;
        lastIndex = Math.max(0, length - 1); // range checking, making sure it is never oob
    }

    /**
     * Creates a new instance of a {@code StringType} with a given {@code Object}, on which the method {@link Objects#toString(Object)} will be applied on.
     *
     * @param o The {@code StringType} to apply the method {@code toString(Object)} on.
     * @see #StringType()
     * @see #StringType(char)
     * @see #StringType(char[])
     * @see #StringType(String)
     * @see #StringType(StringBuilder)
     * @see #StringType(StringType)
     * @since <code>1.0.2</code>
     */
    public StringType(@NotNull Object o) {
        this(Objects.toString(o));
    }

    // --------------------------------------------------------- Casings

    /**
     * Returns a new {@code StringType} containing only the uppercase letters of the applied on object.
     *
     * @return A new {@code StringType} containing only uppercase letters.
     * @see #toLowerCase()
     * @see #capitalize()
     * @since <code>1.0.0</code>
     */
    @Contract(" -> new")
    public @NotNull StringType toUpperCase() {
        char[] cs = chars;
        for (int i = 0; i < cs.length; i++) {
            char c = cs[i];
            if (0x61 <= c && c <= 0x7A) cs[i] = (char) (c - 0x20);
        }
        return new StringType(cs);
    }

    /**
     * Returns a new {@code StringType} containing only the lowercase letters of the applied on object.
     *
     * @return A new {@code StringType} containing only lowercase letters.
     * @see #toUpperCase()
     * @see #capitalize()
     * @since <code>1.0.0</code>
     */
    @Contract(" -> new")
    public @NotNull StringType toLowerCase() {
        char[] cs = chars;
        for (int i = 0; i < cs.length; i++) {
            char c = cs[i];
            if (0x41 <= c && c <= 0x5A) cs[i] = (char) (c + 0x20);
        }
        return new StringType(cs);
    }

    /**
     * Returns a nww {@code StringType} containing the same characters as the applied on object, with the first letter
     * (index {@code 0}) being capitalized.
     *
     * @return a new {@code StringType} with a capitalized value.
     * @see #toUpperCase()
     * @see #toLowerCase()
     * @since <code>1.0.0</code>
     */
    @Contract(" -> new")
    public @NotNull StringType capitalize() {
        char[] cs = chars;
        char c = cs[0];
        if (0x41 <= c && c <= 0x5A) cs[0] = (char) (c + 0x20);
        return new StringType(cs);
    }

    // --------------------------------------------------------- Checks

    /**
     * Returns whether this {@code StringType} contains an empty string or not.
     *
     * @return Whether a {@code StringType} contains an empty string or not.
     * @since <code>1.0.0</code>
     */
    public boolean isEmpty() {
        return length == 0 && chars.length == 0;
    }

    /**
     * Checks whether the {@code char} at the given {@code index} is the specified {@code char} or not.
     *
     * @param index The {@code index} of the {@code char} to check.
     * @param c     The {@code char} to compare against.
     * @return Whether the {@code char} at the given {@code index} is the specified {@code char} or not.
     * @since <code>1.0.0</code>
     */
    public boolean isCharAt(int index, char c) {
        return charAt(chars, index) == c;
    }

    /**
     * Checks if the {@code length} of this {@code StringType} is equal to the specified length or not.
     *
     * @param length The {@code length} to check against.
     * @return Whether the {@code length} of a {@code StringType} is equal to the specified length or not.
     * @see #isShorterThan(int)
     * @see #isLongerThan(int)
     * @since <code>1.0.0</code>
     */
    public boolean isLength(int length) {
        return length == this.length;
    }

    /**
     * Checks if the {@code length} of this {@code StringType} is shorter than the specified length or not.
     *
     * @param length The {@code length} to check against.
     * @return Whether the {@code length} of a {@code StringType} is shorter than the specified length or not.
     * @see #isLength(int)
     * @see #isLongerThan(int)
     * @since <code>1.6.0</code>
     */
    @StatusMarkers.Unstable
    @ApiStatus.Experimental
    public boolean isShorterThan(int length) {
        return length > this.length;
    }

    /**
     * Checks if the {@code length} of this {@code StringType} is longer than the specified length or not.
     *
     * @param length The {@code length} to check against.
     * @return Whether the {@code length} of a {@code StringType} is longer than the specified length or not.
     * @see #isLength(int)
     * @see #isShorterThan(int)
     * @since <code>1.6.0</code>
     */
    @StatusMarkers.Unstable
    @ApiStatus.Experimental
    public boolean isLongerThan(int length) {
        return length < this.length;
    }

    /**
     * Returns whether this {@code StringType} contains only digits (0-9) as {@code characters} or not.
     *
     * @return Whether a {@code StringType} contains only digits (0-9) as {@code characters} or not.
     * @since <code>1.0.0</code>
     */
    public boolean isDigits() {
        for (char c : chars) if (c < 0x30 || 0x39 < c) return false;
        return true;
    }

    /**
     * Returns whether this {@code StringType} contains the given {@code substring} or not.
     *
     * @param substring The {@code substring} to look for.
     * @return Whether a {@code StringType} contains the given {@code substring} or not.
     * @see #contains(char)
     * @since <code>1.0.0</code>
     */
    public boolean contains(@NotNull String substring) {
        return indexOf(chars, substring.toCharArray(), 0, length) > -1;
    }

    /**
     * Returns whether this {@code StringType} contains the given {@code character} or not.
     *
     * @param c the {@code character} to look for.
     * @return Whether a {@code StringType} contains the given {@code character} or not.
     * @see #contains(String)
     * @since <code>1.0.0</code>
     */
    public boolean contains(char c) {
        return quickFind(chars, c) > -1;
    }

    /**
     * Returns whether this {@code StringType} starts with the given {@code character} or not.
     *
     * @param c the {@code character} to check against.
     * @return Whether a {@code StringType} starts with the given {@code character} or not.
     * @see #startsWith(String)
     * @see #endsWith(char)
     * @see #endsWith(String)
     * @since <code>1.0.0</code>
     */
    public boolean startsWith(char c) {
        return chars[0] == c;
    }

    /**
     * Returns whether this {@code StringType} starts with the given {@code String} or not.
     *
     * @param string the {@code String} to check against.
     * @return Whether a {@code StringType} starts with the given {@code String} or not.
     * @see #startsWith(char)
     * @see #endsWith(char)
     * @see #endsWith(String)
     * @since <code>1.0.0</code>
     */
    public boolean startsWith(@NotNull String string) {
        if (string.length() > length) return false;
        for (int i = 0; i < string.length(); i++)
            if (string.charAt(i) != chars[i]) return false;
        return true;
    }

    /**
     * Returns whether this {@code StringType} ends with the given {@code character} or not.
     *
     * @param c the {@code character} to check against.
     * @return Whether a {@code StringType} ends with the given {@code character} or not.
     * @see #startsWith(char)
     * @see #startsWith(String)
     * @see #endsWith(String)
     * @since <code>1.0.0</code>
     */
    public boolean endsWith(char c) {
        return chars[lastIndex] == c;
    }

    /**
     * Returns whether this {@code StringType} ends with the given {@code String} or not.
     *
     * @param string the {@code String} to check against.
     * @return Whether a {@code StringType} ends with the given {@code String} or not.
     * @see #startsWith(char)
     * @see #startsWith(String)
     * @see #endsWith(char)
     * @since <code>1.0.0</code>
     */
    public boolean endsWith(@NotNull String string) {
        int startingIndex = length - string.length();
        if (startingIndex < 0) return false;
        for (int i = startingIndex, j = 0; i < length; i++, j++)
            if (string.charAt(j) != chars[i]) return false;
        return true;
    }

    // --------------------------------------------------------- Indexing

    /**
     * Finds and returns the {@code Range} of the first match of the given string.
     *
     * @param string The string to find the first match of.
     * @return The {@link Range} of the first match of the given string; if none is found, returns {@link Range#UNDEFINED}
     * @since <code>1.0.0</code>
     */
    public @NotNull Range find(@NotNull String string) {
        int start = indexOf(chars, string.toCharArray(), 0, length);
        return start == -1 ? Range.UNDEFINED : new Range(start, start + string.length());
    }

    /**
     * Returns the {@code char} at the specified {@code index}.
     *
     * @param index The index of the {@code char} to return.
     * @return The {@code char} at the specified {@code index}.
     * @since <code>1.0.0</code>
     */
    public char charAt(int index) {
        return charAt(chars, index);
    }

    /**
     * Returns the {@code index} of the first occurrence of the specified {@code character}.
     *
     * @param c The {@code char} to get the index of.
     * @return The {@code index} of the first occurrence of the specified {@code character}.
     * @since <code>1.0.0</code>
     */
    public int indexOf(char c) {
        return index(chars, c);
    }

    /**
     * Returns the {@code index} of the last occurrence of the specified {@code character}.
     *
     * @param c The {@code char} to get the index of.
     * @return The {@code index} of the last occurrence of the specified {@code character}.
     * @since <code>1.0.0</code>
     */
    public int lastIndexOf(char c) {
        return lastIndexOfRange(chars, c, 0, length);
    }

    /**
     * Returns the {@code index} of the first occurrence of the specified {@code String}.
     *
     * @param string The {@code String} to get the index of.
     * @return The {@code index} of the first occurrence of the specified {@code String}.
     * @since <code>1.0.9</code>
     */
    public int indexOf(@NotNull String string) {
        return indexOf(chars, string.toCharArray(), 0, length);
    }

    /**
     * Returns the {@code index} of the last occurrence of the specified {@code String}.
     *
     * @param string The {@code String} to get the index of.
     * @return The {@code index} of the last occurrence of the specified {@code String}.
     * @since <code>1.0.9</code>
     */
    public int lastIndexOf(@NotNull String string) {
        return lastIndexOf(chars, string.toCharArray(), 0, length);
    }

    /**
     * Returns an array of {@code indexes} of the specified {@code char}, will return an array with length {@code 0} if
     * no occurrence was found.
     *
     * @param c The {@code char} to get the indexes of.
     * @return An array of {@code indexes} of the specified {@code char}.
     * @since <code>1.0.0</code>
     */
    public int[] indexesOf(char c) {
        return indexesOf(chars, c);
    }

    /**
     * Gets the first {@code char} of this {@code StringType}.
     *
     * @return The first {@code char} of this {@code StringType}.
     * @see #charAt(int)
     * @since <code>1.0.0</code>
     */
    public char getFirst() {
        return chars[0];
    }

    /**
     * Gets the last {@code char} of this {@code StringType}.
     *
     * @return THe last {@code char} of this {@code StringType}.
     * @see #charAt(int)
     * @since <code>1.0.0</code>
     */
    public char getLast() {
        return chars[lastIndex];
    }

    /**
     * Returns the length of this {@code StringType}.
     *
     * @return The length of this {@code StringType}.
     * @since <code>1.0.0</code>
     */
    public int length() {
        return length;
    }

    // --------------------------------------------------------- Basic modifications

    /**
     * Removes the {@code char} at the specified {@code index} and returns it.
     *
     * @param index The {@code index} of the {@code char} to remove.
     * @return The {@code char} that was removed.
     * @see #remove(char)
     * @see #remove(String)
     * @since <code>1.0.0</code>
     */
    public char remove(int index) {
        char c = charAt(chars, index);
        chars = fastRemove(chars, index, 1);
        return c;
    }

    /**
     * Removes the {@code char} if it can be found.
     *
     * @param c The {@code char} to remove.
     * @return {@code True} in any case.
     * @see #remove(int)
     * @see #remove(String)
     * @since <code>1.0.0</code>
     */
    public boolean remove(char c) {
        int index = index(chars, c);
        chars = fastRemove(chars, index, 1);
        return true;
    }

    /**
     * Removes the {@code String} if it can be found.
     *
     * @param string The {@code String} to remove.
     * @return If the {@code String} was removed if found.
     * @see #remove(char)
     * @see #remove(int)
     * @since <code>1.0.9</code>
     */
    public boolean remove(@NotNull String string) {
        int i = indexOf(chars, string.toCharArray(), 0, length);
        if (i > -1) chars = fastRemove(chars, i, string.length());
        return i > -1;
    }

    /**
     * Removes all character from this {@code StringType} in the given range.
     *
     * @param start The starting index of the character's to remove. <em>(Inclusive.)</em>
     * @param end   The ending index of the character's to remove. <em>(Inclusive.)</em>
     * @return {@code true} in any case.
     * @see #removeChars(char...)
     * @since <code>1.6.1</code>
     */
    public boolean removeChars(int start, int end) {
        int length = end - start;
        chars = fastRemove(chars, start, length);
        return true;
    }

    /**
     * Removes all characters provided from this {@code StringType}.
     *
     * @param chars An array of {@code chars} to remove.
     * @return {@code true} in any case.
     * @see #removeChars(int, int)
     * @since <code>1.6.1</code>
     */
    @Contract(pure = true)
    public boolean removeChars(char @NotNull ... chars) {
        char[] cs = this.chars;
        for (char c : chars) {
            int i;
            while ((i = quickFind(cs, c)) != -1) {
                cs = fastRemove(cs, i, 1);
            }
        }
        return true;
    }

    /**
     * Inserts the given {@code char} at the specified {@code index}.
     *
     * @param c     The {@code char} to insert.
     * @param index The {@code index} of the {@code char} to insert.
     * @return {@code true} in any case.
     * @see #insert(String, int)
     * @see #insert(Object, int)
     * @see #insert(StringType, int)
     * @see #append(char)
     * @see #append(String)
     * @see #append(Object)
     * @see #append(StringType)
     * @since <code>1.0.0</code>
     */
    public boolean insert(char c, int index) {
        char[] cs = chars;
        chars = fastAdd(cs, new char[]{c}, index);
        return true;
    }

    /**
     * Inserts the given {@code String} at the specified {@code index}.
     *
     * @param string The {@code String} to insert.
     * @param index  The {@code index} of the {@code String} to insert.
     * @return {@code true} in any case.
     * @see #insert(char, int)
     * @see #insert(Object, int)
     * @see #insert(StringType, int)
     * @see #append(char)
     * @see #append(String)
     * @see #append(Object)
     * @see #append(StringType)
     * @since <code>1.1.0</code>
     */
    public boolean insert(@NotNull String string, int index) {
        char[] cs = chars;
        chars = fastAdd(cs, string.toCharArray(), index);
        return true;
    }

    /**
     * Inserts the given {@code Object} at the specified {@code index}.
     *
     * @param o     The {@code StringType} converted to a {@code String} to insert.
     * @param index The {@code index} of the {@code Object} to insert.
     * @return {@code true} in any case.
     * @see #insert(char, int)
     * @see #insert(String, int)
     * @see #insert(StringType, int)
     * @see #append(char)
     * @see #append(String)
     * @see #append(Object)
     * @see #append(StringType)
     * @since <code>1.1.0</code>
     */
    public boolean insert(Object o, int index) {
        char[] cs = chars;
        chars = fastAdd(cs, Objects.toString(o).toCharArray(), index);
        return true;
    }

    /**
     * Inserts the given {@code StringType} at the specified {@code index}.
     *
     * @param stringType The {@code StringType} to insert.
     * @param index      The {@code index} of the {@code StringType} to insert.
     * @return {@code true} in any case.
     * @see #insert(char, int)
     * @see #insert(String, int)
     * @see #insert(Object, int)
     * @see #append(char)
     * @see #append(String)
     * @see #append(Object)
     * @see #append(StringType)
     * @since <code>1.1.0</code>
     */
    public boolean insert(@NotNull StringType stringType, int index) {
        char[] cs = chars;
        chars = fastAdd(cs, stringType.chars, index);
        return true;
    }

    /**
     * Inserts the given {@code char} at the start of this {@code StringType}.
     *
     * @param c The {@code char} to insert.
     * @return {@code true} in any case.
     * @see #insert(char, int)
     * @see #insert(String, int)
     * @see #insert(Object, int)
     * @see #insert(StringType, int)
     * @see #push(String)
     * @see #push(Object)
     * @see #push(StringType)
     * @see #append(char)
     * @see #append(String)
     * @see #append(Object)
     * @see #append(StringType)
     * @since <code>1.6.0</code>
     */
    public boolean push(char c) {
        char[] cs = chars;
        chars = fastAdd(cs, new char[]{c}, 0);
        return true;
    }

    /**
     * Inserts the given {@code String} at the start of this {@code StringType}.
     *
     * @param string The {@code String} to insert.
     * @return {@code true} in any case.
     * @see #insert(char, int)
     * @see #insert(String, int)
     * @see #insert(Object, int)
     * @see #insert(StringType, int)
     * @see #push(char)
     * @see #push(Object)
     * @see #push(StringType)
     * @see #append(char)
     * @see #append(String)
     * @see #append(Object)
     * @see #append(StringType)
     * @since <code>1.6.0</code>
     */
    public boolean push(@NotNull String string) {
        char[] cs = chars;
        chars = fastAdd(cs, string.toCharArray(), 0);
        return true;
    }

    /**
     * Inserts the given {@code Object} at the start of this {@code StringType}.
     *
     * @param o The {@code Object} to insert.
     * @return {@code true} in any case.
     * @see #insert(char, int)
     * @see #insert(String, int)
     * @see #insert(Object, int)
     * @see #insert(StringType, int)
     * @see #push(char)
     * @see #push(String)
     * @see #push(StringType)
     * @see #append(char)
     * @see #append(String)
     * @see #append(Object)
     * @see #append(StringType)
     * @since <code>1.6.0</code>
     */
    public boolean push(Object o) {
        char[] cs = chars;
        chars = fastAdd(cs, Objects.toString(o).toCharArray(), 0);
        return true;
    }

    /**
     * Inserts the given {@code StringType} at the start of this {@code StringType}.
     *
     * @param stringType The {@code StringType} to insert.
     * @return {@code true} in any case.
     * @see #insert(char, int)
     * @see #insert(String, int)
     * @see #insert(Object, int)
     * @see #insert(StringType, int)
     * @see #push(char)
     * @see #push(String)
     * @see #push(Object)
     * @see #append(char)
     * @see #append(String)
     * @see #append(Object)
     * @see #append(StringType)
     * @since <code>1.6.0</code>
     */
    public boolean push(@NotNull StringType stringType) {
        char[] cs = chars;
        chars = fastAdd(cs, stringType.chars, 0);
        return true;
    }

    /**
     * Adds the given {@code char} at the end of this {@code StringType}.
     *
     * @param c The {@code char} to insert.
     * @return {@code true} in any case.
     * @see #insert(char, int)
     * @see #insert(String, int)
     * @see #append(String)
     * @see #append(Object)
     * @see #append(StringType)
     * @since <code>1.0.0</code>
     */
    public boolean append(char c) {
        char[] cs = chars;
        chars = fastAdd(cs, new char[]{c}, length);
        return true;
    }

    /**
     * Adds the given {@code String} at the end of this {@code StringType}.
     *
     * @param string The {@code String} to insert.
     * @return {@code true} in any case.
     * @see #insert(char, int)
     * @see #insert(String, int)
     * @see #append(char)
     * @see #append(Object)
     * @see #append(StringType)
     * @since <code>1.0.0</code>
     */
    public boolean append(@NotNull String string) {
        char[] cs = chars;
        chars = fastAdd(cs, string.toCharArray(), length);
        return true;
    }

    /**
     * Adds the given {@code Object} at the end of this {@code StringType}.
     *
     * @param o The {@code StringType} to convert to a {@code String} and then appends this object to the end of this {@code StringType}.
     * @return {@code true} in any case.
     * @see #insert(char, int)
     * @see #insert(String, int)
     * @see #append(char)
     * @see #append(String)
     * @see #append(StringType)
     * @since <code>1.0.0</code>
     */
    public boolean append(Object o) {
        char[] cs = chars;
        chars = fastAdd(cs, Objects.toString(o).toCharArray(), length);
        return true;
    }

    /**
     * Adds the given {@code StringType} at the end of this {@code StringType}.
     *
     * @param stringType The {@code StringType} to append to this {@code StringType}.
     * @return {@code true} in any case.
     * @see #insert(char, int)
     * @see #insert(String, int)
     * @see #append(char)
     * @see #append(String)
     * @see #append(Object)
     * @since <code>1.0.0</code>
     */
    public boolean append(@NotNull StringType stringType) {
        char[] cs = chars;
        chars = fastAdd(cs, stringType.chars, length);
        return true;
    }

    /**
     * Repeats this {@code StringType}'s contents {@code count} times and appends each new iteration to the previous one.
     *
     * @param count The amount of repetitions.
     * @return A new {@code StringType} containing the contents of a {@code StringType}, repeated {@code count} times.
     * @since <code>1.2.2</code>
     */
    @Contract("_ -> new")
    public @NotNull StringType repeat(int count) {
        char[] chars1 = new char[length * count];
        for (int i = 0; i < chars1.length; i++) chars1[i] = chars[i % length];
        return new StringType(chars1);
    }

    /**
     * Repeatedly appends the {@code char} {@code index} times to the end of this {@code StringType}.
     *
     * @param c     The {@code char} to repeat.
     * @param count The amount of repetitions.
     * @return A {@code StringType} containing the given {@code char}, which has been added to the {@code StringType}.
     * @see #repeatAppend(String, int)
     * @since <code>1.2.2</code>
     */
    @Contract("_, _ -> new")
    public @NotNull StringType repeatAppend(char c, int count) {
        char[] cs = new char[count];
        Arrays.fill(cs, c);
        return new StringType(fastAdd(chars, cs, length));
    }

    /**
     * Repeatedly appends the {@code String} {@code index} times to the end of this {@code StringType}.
     *
     * @param string The {@code String} to repeat.
     * @param count  The amount of repetitions.
     * @return A {@code StringType} containing the given {@code String}, which has been added to the {@code StringType}.
     * @see #repeatAppend(char, int)
     * @since <code>1.2.2</code>
     */
    @Contract("_, _ -> new")
    public @NotNull StringType repeatAppend(@NotNull String string, int count) {
        char[] cs = new char[string.length() * count];
        for (int i = 0; i < cs.length; i++) cs[i] = string.charAt(i % string.length());
        return new StringType(fastAdd(chars, cs, length));
    }

    /**
     * Matches the length of the supplied {@code string} to a specified {@code length}, from the start. Filling the empty
     * spaces with a supplied {@code filler}.
     *
     * @param string The {@code string} to match to a given length.
     * @param filler The {@code character} to use to fill the empty length with.
     * @param length The target {@code length} of the string.
     * @return A {@code StringType} with the specified length, filled with the {@code filler} from the start.
     * @see #padLengthFromStart(String, int)
     * @see #padLengthFromEnd(String, int)
     * @see #matchLengthFromEnd(String, char, int)
     * @since <code>1.6.2</code>
     */
    @Contract("_, _, _ -> new")
    public static @NotNull StringType matchLengthFromStart(@NotNull String string, char filler, int length) {
        return new StringType(matchLength(string.toCharArray(), filler, length, true));
    }

    /**
     * Matches the length of the supplied {@code string} to a specified {@code length}, from the end. Filling the empty
     * spaces with a supplied {@code filler}.
     *
     * @param string The {@code string} to match to a given length.
     * @param filler The {@code character} to use to fill the empty length with.
     * @param length The target {@code length} of the string.
     * @return A {@code StringType} with the specified length, filled with the {@code filler} from the end.
     * @see #padLengthFromStart(String, int)
     * @see #matchLengthFromStart(String, char, int)
     * @see #padLengthFromEnd(String, int)
     * @since <code>1.6.2</code>
     */
    @Contract("_, _, _ -> new")
    public static @NotNull StringType matchLengthFromEnd(@NotNull String string, char filler, int length) {
        return new StringType(matchLength(string.toCharArray(), filler, length, false));
    }

    /**
     * Matches the length of the supplied {@code string} to a specified {@code length}, from the start. Filling the empty
     * spaces with <em>whitespaces</em> ({@code ' '}).
     *
     * @param string The {@code string} to match to a given length.
     * @param length The target {@code length} of the string.
     * @return A {@code StringType} with the specified length, filled with a <em>whitespace</em> from the start.
     * @see #matchLengthFromStart(String, char, int)
     * @see #padLengthFromEnd(String, int)
     * @see #matchLengthFromEnd(String, char, int)
     * @since <code>1.6.2</code>
     */
    @Contract("_, _ -> new")
    public static @NotNull StringType padLengthFromStart(@NotNull String string, int length) {
        return new StringType(matchLength(string.toCharArray(), ' ', length, true));
    }

    /**
     * Matches the length of the supplied {@code string} to a specified {@code length}, from the end. Filling the empty
     * spaces with <em>whitespaces</em> ({@code ' '}).
     *
     * @param string The {@code string} to match to a given length.
     * @param length The target {@code length} of the string.
     * @return A {@code StringType} with the specified length, filled with a <em>whitespace</em> from the end.
     * @see #padLengthFromStart(String, int)
     * @see #matchLengthFromStart(String, char, int)
     * @see #matchLengthFromEnd(String, char, int)
     * @since <code>1.6.2</code>
     */
    @Contract("_, _ -> new")
    public static @NotNull StringType padLengthFromEnd(@NotNull String string, int length) {
        return new StringType(matchLength(string.toCharArray(), ' ', length, false));
    }

    /**
     * Matches the length of {@code this object} to a specified {@code length}, from the start. Filling the empty
     * spaces with a supplied {@code filler}.
     *
     * @param filler The {@code character} to use to fill the empty length with.
     * @param length The target {@code length} of the string.
     * @return {@code This object} with the specified length, filled with the {@code filler} from the start.
     * @see #padLengthFromStart(int)
     * @see #padLengthFromEnd(int)
     * @see #matchLengthFromEnd(char, int)
     * @since <code>1.6.2</code>
     */
    @Contract(mutates = "this")
    public StringType matchLengthFromStart(char filler, int length) {
        chars = matchLength(chars, filler, length, true);
        this.length = length;
        lastIndex = length - 1;
        value = String.valueOf(chars);
        return this;
    }

    /**
     * Matches the length of {@code this object} to a specified {@code length}, from the end. Filling the empty
     * spaces with a supplied {@code filler}.
     *
     * @param filler The {@code character} to use to fill the empty length with.
     * @param length The target {@code length} of the string.
     * @return {@code This object} with the specified length, filled with the {@code filler} from the end.
     * @see #padLengthFromStart(int)
     * @see #padLengthFromEnd(int)
     * @see #matchLengthFromStart(char, int)
     * @since <code>1.6.2</code>
     */
    @Contract(mutates = "this")
    public StringType matchLengthFromEnd(char filler, int length) {
        chars = matchLength(chars, filler, length, false);
        this.length = length;
        lastIndex = length - 1;
        value = String.valueOf(chars);
        return this;
    }

    /**
     * Matches the length of {@code this object} to a specified {@code length}, from the start. Filling the empty
     * spaces with <em>whitespaces</em> ({@code ' '}).
     *
     * @param length The target {@code length} of the string.
     * @return {@code This object} with the specified length, filled with a <em>whitespace</em> from the start.
     * @see #matchLengthFromStart(char, int)
     * @see #matchLengthFromEnd(char, int)
     * @see #padLengthFromEnd(int)
     * @since <code>1.6.2</code>
     */
    @Contract(mutates = "this")
    public StringType padLengthFromStart(int length) {
        chars = matchLength(chars, ' ', length, true);
        this.length = length;
        lastIndex = length - 1;
        value = String.valueOf(chars);
        return this;
    }

    /**
     * Matches the length of {@code this object} to a specified {@code length}, from the end. Filling the empty
     * spaces with <em>whitespaces</em> ({@code ' '}).
     *
     * @param length The target {@code length} of the string.
     * @return {@code This object} with the specified length, filled with a <em>whitespace</em> from the end.
     * @see #padLengthFromStart(int)
     * @see #matchLengthFromStart(char, int)
     * @see #matchLengthFromEnd(char, int)
     * @since <code>1.6.2</code>
     */
    @Contract(mutates = "this")
    public StringType padLengthFromEnd(int length) {
        chars = matchLength(chars, ' ', length, false);
        this.length = length;
        lastIndex = length - 1;
        value = String.valueOf(chars);
        return this;
    }

    /**
     * Replaces the target {@code char} with the given {@code char}.
     *
     * @param oldChar The {@code char} to be replaced.
     * @param newChar The {@code char} to replace with.
     * @return A new {@code StringType} with the {@code char} replaced.
     * @see #replace(String, String)
     * @see #replaceAll(char, char)
     * @see #replaceAll(String, String)
     * @since <code>1.2.2</code>
     */
    @Contract("_, _ -> new")
    public @NotNull StringType replace(char oldChar, char newChar) {
        int index = indexOfRange(chars, oldChar, 0, length);
        chars = replaceValueAt(chars, index, newChar);
        return new StringType(chars);
    }

    /**
     * Replaces all the target {@code char} occurrences with the given {@code char}.
     *
     * @param oldChar The {@code chars} to be replaced.
     * @param newChar The {@code char} to replace with.
     * @return A new {@code StringType} with all {@code chars} replaced.
     * @see #replaceAll(String, String)
     * @see #replace(char, char)
     * @see #replace(String, String)
     * @since <code>1.2.2</code>
     */
    @Contract("_, _ -> new")
    public @NotNull StringType replaceAll(char oldChar, char newChar) {
        int index = 0;
        while ((index = indexOfRange(chars, oldChar, index, length)) != -1 || index == length - 1) {
            chars = replaceValueAt(chars, index, newChar);
        }
        return new StringType(chars);
    }

    /**
     * Replaces the target {@code String} with the given {@code String}.
     *
     * @param oldString The {@code String} to be replaced.
     * @param newString The {@code String} to replace with.
     * @return A new {@code StringType} with the {@code String} replaced.
     * @see #replace(char, char)
     * @see #replaceAll(char, char)
     * @see #replaceAll(String, String)
     * @since <code>1.2.2</code>
     */
    @Contract("_, _ -> new")
    public @NotNull StringType replace(@NotNull String oldString, @NotNull String newString) {
        int index = indexOf(chars, oldString.toCharArray(), 0, length);
        chars = replaceValuesAt(chars, index, newString.toCharArray());
        return new StringType(chars);
    }

    /**
     * Replaces all the target {@code String} occurrences with the given {@code String}.
     *
     * @param oldString The {@code Strings} to be replaced.
     * @param newString The {@code String} to replace with.
     * @return A new {@code StringType} with all {@code Strings} replaced.
     * @see #replaceAll(char, char)
     * @see #replace(char, char)
     * @see #replace(String, String)
     * @since <code>1.2.2</code>
     */
    @Contract("_, _ -> new")
    public @NotNull StringType replaceAll(@NotNull String oldString, @NotNull String newString) {
        int index = 0;
        while ((index = indexOf(chars, oldString.toCharArray(), index, length)) != -1 || index == length - 1) {
            chars = replaceValuesAt(chars, index, newString.toCharArray());
        }
        return new StringType(chars);
    }

    /**
     * Clears the {@code StringType}'s values to be an empty {@code char[]} with length {@code 0}.
     *
     * @since <code>1.0.0</code>
     */
    public void clear() {
        chars = new char[0];
        length = 0;
        lastIndex = 0;
    }

    /**
     * Removes all leading and trailing whitespaces.
     *
     * @return a {@code StringType}, but with all leading and trailing whitespaces removed.
     * @since <code>1.0.0</code>
     */
    public StringType trim() {
        trimSize(chars);
        length = chars.length;
        lastIndex = length - 1;
        value = String.valueOf(chars);
        return this;
    }

    // --------------------------------------------------------- Array interactions

    /**
     * Creates a new {@code StringType} based on a {@code String[]}, joined together with a given {@code delimiter}.
     *
     * @param delimiter The delimiter to join the {@code array} with.
     * @param strings   An array of {@code Strings}.
     * @return A new {@code StringType}, of a {@code String[]} joined with a {@code delimiter}.
     * @see #join(char, StringType...)
     * @see #join(String, String...)
     * @see #join(String, StringType...)
     * @see #join(StringType, String...)
     * @see #join(StringType, StringType...)
     * @since <code>1.2.1</code>
     */
    public static @NotNull StringType join(char delimiter, String @NotNull ... strings) {
        StringType tmp = new StringType();
        for (int i = 0; ; i++) {
            tmp.append(strings[i]);
            if (i == strings.length - 1) return tmp;
            tmp.append(delimiter);
        }
    }

    /**
     * Creates a new {@code StringType} based on a {@code StringType[]}, joined together with a given {@code delimiter}.
     *
     * @param delimiter   The delimiter to join the {@code array} with.
     * @param stringTypes An array of {@code StringTypes}.
     * @return A new {@code StringType}, of a {@code StringType[]} joined with a {@code delimiter}.
     * @see #join(char, String...)
     * @see #join(String, String...)
     * @see #join(String, StringType...)
     * @see #join(StringType, String...)
     * @see #join(StringType, StringType...)
     * @since <code>1.2.1</code>
     */
    public static @NotNull StringType join(char delimiter, StringType @NotNull ... stringTypes) {
        StringType tmp = new StringType();
        for (int i = 0; ; i++) {
            tmp.append(stringTypes[i]);
            if (i == stringTypes.length - 1) return tmp;
            tmp.append(delimiter);
        }
    }

    /**
     * Creates a new {@code StringType} based on a {@code String[]}, joined together with a given {@code delimiter}.
     *
     * @param delimiter The delimiter to join the {@code array} with.
     * @param strings   An array of {@code Strings}.
     * @return A new {@code StringType}, of a {@code String[]} joined with a {@code delimiter}.
     * @see #join(char, String...)
     * @see #join(char, StringType...)
     * @see #join(String, StringType...)
     * @see #join(StringType, String...)
     * @see #join(StringType, StringType...)
     * @since <code>1.2.1</code>
     */
    public static @NotNull StringType join(String delimiter, String @NotNull ... strings) {
        StringType tmp = new StringType();
        for (int i = 0; ; i++) {
            tmp.append(strings[i]);
            if (i == strings.length - 1) return tmp;
            tmp.append(delimiter);
        }
    }

    /**
     * Creates a new {@code StringType} based on a {@code StringType[]}, joined together with a given {@code delimiter}.
     *
     * @param delimiter   The delimiter to join the {@code array} with.
     * @param stringTypes An array of {@code StringTypes}.
     * @return A new {@code StringType}, of a {@code StringType[]} joined with a {@code delimiter}.
     * @see #join(char, String...)
     * @see #join(char, StringType...)
     * @see #join(String, String...)
     * @see #join(StringType, String...)
     * @see #join(StringType, StringType...)
     * @since <code>1.2.1</code>
     */
    public static @NotNull StringType join(String delimiter, StringType @NotNull ... stringTypes) {
        StringType tmp = new StringType();
        for (int i = 0; ; i++) {
            tmp.append(stringTypes[i]);
            if (i == stringTypes.length - 1) return tmp;
            tmp.append(delimiter);
        }
    }

    /**
     * Creates a new {@code StringType} based on a {@code String[]}, joined together with a given {@code delimiter}.
     *
     * @param delimiter The delimiter to join the {@code array} with.
     * @param strings   An array of {@code Strings}.
     * @return A new {@code StringType}, of a {@code String[]} joined with a {@code delimiter}.
     * @see #join(char, String...)
     * @see #join(char, StringType...)
     * @see #join(String, String...)
     * @see #join(String, StringType...)
     * @see #join(StringType, StringType...)
     * @since <code>1.2.1</code>
     */
    public static @NotNull StringType join(StringType delimiter, String @NotNull ... strings) {
        StringType tmp = new StringType();
        for (int i = 0; ; i++) {
            tmp.append(strings[i]);
            if (i == strings.length - 1) return tmp;
            tmp.append(delimiter);
        }
    }

    /**
     * Creates a new {@code StringType} based on a {@code StringType[]}, joined together with a given {@code delimiter}.
     *
     * @param delimiter   The delimiter to join the {@code array} with.
     * @param stringTypes An array of {@code StringTypes}.
     * @return A new {@code StringType}, of a {@code StringType[]} joined with a {@code delimiter}.
     * @see #join(char, String...)
     * @see #join(char, StringType...)
     * @see #join(String, String...)
     * @see #join(StringType, String...)
     * @since <code>1.2.1</code>
     */
    public static @NotNull StringType join(StringType delimiter, StringType @NotNull ... stringTypes) {
        StringType tmp = new StringType();
        for (int i = 0; ; i++) {
            tmp.append(stringTypes[i]);
            if (i == stringTypes.length - 1) return tmp;
            tmp.append(delimiter);
        }
    }

    // --------------------------------------------------------- Interactions

    /**
     * Applies the specified function passed in for the {@code substring}.
     *
     * @param range    The {@code length} of the substring.
     * @param iterator The {@code function} to apply to the substring.
     * @see #forEachChar(StringRangeIterator)
     * @since <code>1.0.2</code>
     */
    public void forRangeRange(int range, StringRangeIterator iterator) {
        for (String s : charRange(chars, range)) iterator.apply(s);
    }

    /**
     * Applies the specified function passed in for every {@code char} of a {@code StringType}.
     *
     * @param iterator The {@code function} to apply to every {@code char}.
     * @see #forRangeRange(int, StringRangeIterator)
     * @since <code>1.0.2</code>
     */
    public void forEachChar(StringRangeIterator iterator) {
        for (String s : charRange(chars, 1)) iterator.apply(s);
    }

    /**
     * Splits a {@code StringType} at the given {@code delimiter} and returns a new {@code array} containing substrings. split at
     * the {@code delimiter}, the parameter {@code limit} specifies the <em>result threshold:</em>
     * <blockquote>
     *     <ul>
     *         <li>If <code>limit</code> is positive (<code>limit > 0</code>), it will be applied at most <code>limit - 1</code> times.</li>
     *         <li>If <code>limit</code> is 0, (<code>limit == 0</code>), it will be applied as many times as possible, while leaving no trailing empty entries.</li>
     *         <li>If <code>limit</code> is negative (<code>limit < 0</code>), it will be applied as many times as possible, adding trailing empty entries.</li>
     *     </ul>
     * </blockquote>
     *
     * @param delimiter The {@code String} to use as the delimiter.
     * @param limit     The result threshold, as described above.
     * @return An array of substrings split by the {@code delimiter}.
     * @see #split(String)
     * @see #split(char, int)
     * @see #split(char)
     * @since <code>1.2.0</code>
     */
    @Contract(pure = true)
    public StringType @NotNull [] split(@NotNull String delimiter, int limit) {
        char[][] tmp = split(chars, delimiter.toCharArray(), limit);
        StringType[] split = new StringType[tmp.length];
        for (int i = 0; i < tmp.length; i++) split[i] = new StringType(tmp[i]);
        return split;
    }

    /**
     * Splits a {@code StringType} at the given {@code delimiter} and returns a new {@code array} containing substrings. split at
     * the {@code delimiter}, the parameter {@code limit} specifies the <em>result threshold:</em>
     * <blockquote>
     *     <ul>
     *         <li>If <code>limit</code> is positive (<code>limit > 0</code>), it will be applied at most <code>limit - 1</code> times.</li>
     *         <li>If <code>limit</code> is 0, (<code>limit == 0</code>), it will be applied as many times as possible, while leaving no trailing empty entries.</li>
     *         <li>If <code>limit</code> is negative (<code>limit < 0</code>), it will be applied as many times as possible, adding trailing empty entries.</li>
     *     </ul>
     * </blockquote>
     *
     * @param delimiter The {@code char} to use as the delimiter.
     * @param limit     The result threshold, as described above.
     * @return An array of substrings split by the {@code delimiter}.
     * @see #split(String, int)
     * @see #split(String)
     * @see #split(char)
     * @since <code>1.2.0</code>
     */
    public StringType @NotNull [] split(char delimiter, int limit) {
        char[][] tmp = split(chars, new char[]{delimiter}, limit);
        StringType[] split = new StringType[tmp.length];
        for (int i = 0; i < tmp.length; i++) split[i] = new StringType(tmp[i]);
        return split;
    }

    /**
     * Splits a {@code StringType} at the given {@code delimiter} and returns a new {@code array} containing substrings. split at
     * the {@code delimiter}.
     *
     * @param delimiter The {@code String} to use as the delimiter.
     * @return An array of substrings split by the {@code delimiter}.
     * @see #split(String, int)
     * @see #split(char, int)
     * @see #split(char)
     * @since <code>1.2.1</code>
     */
    public StringType @NotNull [] split(@NotNull String delimiter) {
        char[][] tmp = split(chars, delimiter.toCharArray(), 0);
        StringType[] split = new StringType[tmp.length];
        for (int i = 0; i < tmp.length; i++) split[i] = new StringType(tmp[i]);
        return split;
    }

    /**
     * Splits a {@code StringType} at the given {@code delimiter} and returns a new {@code array} containing substrings. split at
     * the {@code delimiter}.
     *
     * @param delimiter The {@code char} to use as the delimiter.
     * @return An array of substrings split by the {@code delimiter}.
     * @see #split(String, int)
     * @see #split(String)
     * @see #split(char, int)
     * @since <code>1.2.1</code>
     */
    public StringType @NotNull [] split(char delimiter) {
        char[][] tmp = split(chars, new char[]{delimiter}, 0);
        StringType[] split = new StringType[tmp.length];
        for (int i = 0; i < tmp.length; i++) split[i] = new StringType(tmp[i]);
        return split;
    }

    // --------------------------------------------------------- Substrings

    /**
     * Returns a subsection of a {@code StringType} starting with the {@code start} index and ending with the {@code end} index.
     *
     * @param start The {@code start} index, inclusive.
     * @param end   The {@code end} index, exclusive.
     * @return A subsection of a {@code StringType}.
     * @see #substring(int)
     * @since <code>1.0.0</code>
     */
    @Contract("_, _ -> new")
    public @NotNull StringType substring(int start, int end) {
        return new StringType(subCharArray(chars, start, end));
    }

    /**
     * Returns a subsection of a {@code StringType} starting at the specified {@code index}.
     *
     * @param start The {@code index} at which the specified subsection should start, inclusive.
     * @return A subsection of a {@code StringType}
     * @see #substring(int, int)
     * @since <code>1.0.0</code>
     */
    @Contract("_ -> new")
    public @NotNull StringType substring(int start) {
        return new StringType(subCharArray(chars, start, length));
    }

    // --------------------------------------------------------- Array Stuff

    /**
     * Returns the {@code char[]} a {@code StringType} contains.
     *
     * @return The {@code char[]} a {@code StringType} contains.
     * @since <code>1.0.0</code>
     */
    public char[] toCharArray() {
        return chars;
    }

    /**
     * Returns a subsection of the {@code char[]} a {@code StringType} contains.
     *
     * @param start The {@code start} index, inclusive.
     * @param end   The {@code end} index, exclusive.
     * @return A subsection of the {@code char[]} a {@code StringType} contains.
     * @see #substring(int, int)
     * @see #substring(int)
     * @since <code>1.6.1</code>
     */
    public char @NotNull [] toCharArray(int start, int end) {
        return subCharArray(chars, start, end);
    }

    // --------------------------------------------------------- Equals

    /**
     * Checks if this {@code StringType} is equal to the given {@code StringType}, will run a strict-equals operation, checking
     * casing.
     *
     * @param other the {@code StringType} to check against.
     * @return Whether a {@code StringType} is equal to the given {@code StringType} or not.
     * @see #equals(Object)
     * @see #equalsIgnoreCase(StringType)
     * @since <code>1.0.0</code>
     */
    @Contract(pure = true)
    public boolean equals(@NotNull StringType other) {
        return quickCompare(chars, other.chars);
    }

    /**
     * Checks if this {@code StringType} is equal to the given {@code StringType}, ignoring casing.
     *
     * @param other the {@code StringType} to check against.
     * @return Whether a {@code StringType} is equal to the given {@code StringType} or not.
     * @see #equals(Object)
     * @see #equalsIgnoreCase(StringType)
     * @since <code>1.6.1</code>
     */
    public boolean equalsIgnoreCase(@NotNull StringType other) {
        return quickCompare(toUpperCase().chars, other.toUpperCase().chars);
    }

    // --------------------------------------------------------- Serial Stuff

    @Serial
    private void writeObject(@NotNull ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeObject(value);
        objectOutputStream.writeObject(chars);
        objectOutputStream.writeObject(length);
        objectOutputStream.writeObject(lastIndex);
    }

    @Serial
    private void readObject(@NotNull ObjectInputStream objectInputStream)
            throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();

        value = (String) objectInputStream.readObject();
        chars = (char[]) objectInputStream.readObject();
        length = (int) objectInputStream.readObject();
        lastIndex = (int) objectInputStream.readObject();
    }

    // --------------------------------------------------------- Overrides

    @Contract(" -> new")
    @Override
    public @NotNull StringType copy() {
        return this;
    }

    @Override
    public @NotNull StringType clone() throws CloneNotSupportedException {
        StringType t = (StringType) super.clone();
        t.value = value;
        t.chars = chars;
        t.lastIndex = lastIndex;
        t.length = length;

        return t;
    }

    @Override
    public char[] toBuffer() {
        return chars;
    }

    @Override
    public @NotNull CharSequence subSequence(int start, int end) {
        return java.nio.CharBuffer.wrap(subCharArray(chars, start, end));
    }

    @Override
    public @NotNull Iterator<Character> iterator() {
        return new Itr();
    }

    @Override
    public int compareTo(@NotNull StringType type) {
        int i = 0;
        int len = Math.max(length, type.length);
        boolean thisLonger = length > type.length;
        while (i < len) {
            if (chars[i] == type.chars[i]) {
                i++;
                continue;
            }
            if (thisLonger && i == type.length) return 1;
            if (!thisLonger && i == length) return -1;
            return chars[i] > type.chars[i] ? -1 : 1;
        }

        return 0;
    }

    // --------------------------------------------------------- Helper stuff

    @Contract(pure = true)
    private static int indexOfRange(char @NotNull [] cs, char c, int start, int end) {
        Objects.checkFromToIndex(start, end, cs.length);
        for (int i = start; i < end; i++) if (cs[i] == c) return i;
        return -1;
    }

    @Contract(pure = true)
    private int lastIndexOfRange(char @NotNull [] cs, char c, int start, int end) {
        Objects.checkFromToIndex(start, end, cs.length);
        for (int i = end - 1; i > start; i--) if (cs[i] == c) return i;
        return -1;
    }

    @Contract(pure = true)
    private static int quickFind(char @NotNull [] cs, char c) {
        return indexOfRange(cs, c, 0, cs.length - 1);
    }

    @Contract(pure = true)
    private static boolean quickCompare(char @NotNull [] a, char @NotNull [] b) {
        if (a.length != b.length) return false;
        for (int i = 0; i < a.length; i++) if (a[i] != b[i]) return false;
        return true;
    }

    @Contract(pure = true)
    private static char charAt(char @NotNull [] cs, int index) {
        Objects.checkIndex(index, cs.length);
        return cs[index];
    }

    @Contract(pure = true)
    private static int index(char @NotNull [] cs, char c) {
        for (int i = 0; i < cs.length; i++) if (cs[i] == c) return i;
        return -1;
    }

    @Contract(pure = true)
    private static int[] indexesOf(char @NotNull [] cs, char c) {
        int lastIndexOf = 0;

        int[] indexes = new int[0];
        int i;

        while ((i = indexOfRange(cs, c, lastIndexOf, cs.length)) != -1) {
            indexes = grow(indexes, i);
            lastIndexOf = i + 1;
        }

        return indexes;
    }

    @Contract(pure = true)
    private static int[][] indexesOf(char[] cs, char[] cs1) {
        int[][] indexes = {{-1, -1}};

        int index;
        int lastIndex = 0;
        while ((index = indexOf(cs, cs1, lastIndex, cs.length)) != -1) {
            int[] range = {index, (index + cs.length) - 1};
            int[][] nIndexes = new int[indexes.length + 1][2];
            System.arraycopy(indexes, 0, nIndexes, 0, indexes.length);
            nIndexes[indexes.length] = range;
            indexes = nIndexes;
            lastIndex = index + cs.length;
        }

        return indexes;
    }

    private static int indexOf(char[] cs, char @NotNull [] cs1, int start, int end) {
        int[] groupAll = indexesOf(cs, cs1[0]);

        if (groupAll.length == 0 ||
                start == cs.length - 1 ||
                start == end ||
                start + cs1.length > cs.length) return -1;

        if (groupAll.length == 1 && groupAll[0] < start) return -1;

        int countToRemove = 0, j = groupAll.length - 1, i;

        while (groupAll[countToRemove] < start) {
            if (countToRemove == j) return -1;
            countToRemove++;
        }

        i = countToRemove;

        while (groupAll[j] > end) {
            countToRemove++;
            j--;
        }

        int[] all = new int[groupAll.length - countToRemove];
        System.arraycopy(groupAll, i, all, 0, all.length);

        for (int idx : all) if (idx + cs1.length <= cs.length && isMatch(cs1, cs, idx)) return idx;
        return -1;
    }

    private static int lastIndexOf(char[] cs, char @NotNull [] cs1, int start, int end) {
        int[] groupAll = indexesOf(cs, cs1[0]);

        if (groupAll.length == 0 ||
                start == cs.length - 1 ||
                start == end ||
                start + cs1.length > cs.length) return -1;

        int[] rGroupAll = new int[groupAll.length];
        for (int i = 0, j = groupAll.length - 1; i < groupAll.length; i++, j--) rGroupAll[i] = groupAll[i];

        System.arraycopy(rGroupAll, 0, groupAll, 0, groupAll.length);

        if (groupAll.length == 1 && groupAll[0] < start) return -1;

        int countToRemove = 0, j = groupAll.length - 1, i;

        while (groupAll[countToRemove] > end) {
            if (countToRemove == j) return -1;
            countToRemove++;
        }

        i = countToRemove;

        while (groupAll[j] < start) {
            countToRemove++;
            j--;
        }

        int[] all = new int[groupAll.length - countToRemove];
        System.arraycopy(groupAll, i, all, 0, all.length);

        for (int idx : all) if (idx + cs1.length <= cs.length && isMatch(cs1, cs, idx)) return idx;
        return -1;
    }

    @Contract(pure = true)
    private static int @NotNull [] grow(int @NotNull [] is, int i) {
        int[] newIs = new int[is.length + 1];
        System.arraycopy(is, 0, newIs, 0, is.length);
        newIs[is.length] = i;
        return newIs;
    }

    private static char @NotNull [] fastAdd(char @NotNull [] cs, char @NotNull [] cs1, int i) {
        int range = cs1.length;
        Objects.checkIndex(i, cs.length);

        char[] cpy = new char[cs.length + range];

        if (i >= 0) System.arraycopy(cs, 0, cpy, 0, i);
        for (int j = i, k = 0; k < range; j++, k++) cpy[j] = cs1[k];
        for (int j = i + range, k = i; k < cs.length; j++, k++) cpy[j] = cs[k];

        return cpy;
    }

    private static char @NotNull [] fastRemove(char @NotNull [] cs, int index, int length) {
        if (index + length > cs.length) throw new IndexOutOfBoundsException("Length for removal ia out of bounds.");
        Objects.checkIndex(index, cs.length);

        char[] newCs = new char[cs.length - length - 1];
        int j = 0;

        for (int i = 0; i < cs.length; i++) {
            if (i >= index && i < cs.length + length) continue;
            newCs[j++] = cs[i];
        }

        return newCs;
    }

    private static String @NotNull [] charRange(char @NotNull [] chars, int range) {
        int length = chars.length;
        int segmentCount = Math.ceilDiv(length, range);

        int current = 0;

        String[] segments = new String[segmentCount];
        int cursor = 0;

        while (cursor < segmentCount) {
            int remainder = length - current;
            int result = Math.min(remainder, range);

            char[] segment = new char[result];
            System.arraycopy(chars, current, segment, 0, result);

            current += range;
            current = Math.min(current, length);

            segments[cursor++] = String.valueOf(segment);
        }

        return segments;
    }

    @Contract(mutates = "param")
    private static void trimSize(char @NotNull [] cs) {
        char c = cs[0];
        while ((c == 0x000) ||
                (c == 0x0009) ||
                (c == 0x0020) ||
                (c == 0x00A0) ||
                (c == 0x00B0) ||
                (c == 0x1680) ||
                (c == 0x2000) ||
                (c == 0x2001) ||
                (c == 0x2002) ||
                (c == 0x2003) ||
                (c == 0x2004) ||
                (c == 0x2005) ||
                (c == 0x2006) ||
                (c == 0x2007) ||
                (c == 0x2008) ||
                (c == 0x2009) ||
                (c == 0x200A) ||
                (c == 0x202F) ||
                (c == 0x205F) ||
                (c == 0x3000)) {
            char[] content = new char[cs.length - 1];
            System.arraycopy(cs, 1, content, 0, content.length);
            cs = content;
            c = cs[0];
        }

        ArrayTools.reverse(cs);

        c = cs[0];
        while ((c == 0x000) ||
                (c == 0x0009) ||
                (c == 0x0020) ||
                (c == 0x00A0) ||
                (c == 0x00B0) ||
                (c == 0x1680) ||
                (c == 0x2000) ||
                (c == 0x2001) ||
                (c == 0x2002) ||
                (c == 0x2003) ||
                (c == 0x2004) ||
                (c == 0x2005) ||
                (c == 0x2006) ||
                (c == 0x2007) ||
                (c == 0x2008) ||
                (c == 0x2009) ||
                (c == 0x200A) ||
                (c == 0x202F) ||
                (c == 0x205F) ||
                (c == 0x3000)) {
            char[] content = new char[cs.length - 1];
            System.arraycopy(cs, 1, content, 0, content.length);
            cs = content;
            c = cs[0];
        }

        ArrayTools.reverse(cs);
    }

    private static char @NotNull [] subCharArray(char @NotNull [] cs, int start, int end) {
        Objects.checkFromToIndex(start, end, cs.length);
        char[] cs1 = new char[end - start];
        System.arraycopy(cs, start, cs1, 0, cs1.length);
        return cs1;
    }

    @Contract(pure = true)
    private static boolean isMatch(char @NotNull [] cs, char[] cs1, int i) {
        for (int j = 1; j < cs.length; j++) if (cs[i] != cs1[i + j]) return false;
        return true;
    }

    private static char @NotNull [][] split(char @NotNull [] cs, char @NotNull [] del, int count) {
        if (indexOf(cs, del, 0, cs.length) == -1) return new char[][]{cs};

        Range[] ranges = Range.fromIntArray(indexesOf(cs, del));

        int total = count < 0 ? ranges.length + Math.abs(count) : count == 0 ? ranges.length : Math.max(ranges.length - count, 0);
        int lastIndex = -1;

        if (total < count) lastIndex = ranges[total].end() + 1;

        char[][] container = new char[total][0];

        boolean endReached = false;
        for (int i = 0; i < total - 1; i++) {
            if (i == ranges.length) endReached = true;
            if (endReached) container[i] = new char[]{0x200B};

            container[i] = subCharArray(cs, ranges[i].end(), ranges[i + 1].start());
        }

        if (lastIndex != -1 || ranges.length - count == 0)
            container[total - 1] = subCharArray(cs, lastIndex, cs.length);

        return container;
    }

    @Contract(mutates = "param1")
    private static char @NotNull [] replaceValueAt(char @NotNull [] cs, int index, char replacement) {
        Objects.checkIndex(index, cs.length);
        cs[index] = replacement;
        return cs;
    }

    @Contract(mutates = "param1")
    private static char @NotNull [] replaceValuesAt(char @NotNull [] cs, int index, char @NotNull [] replacement) {
        Objects.checkIndex(index, cs.length);
        Objects.checkIndex(index + replacement.length, cs.length);

        for (int i = 0; i < replacement.length; i++, index++) cs[index] = replacement[i];
        return cs;
    }

    private static char @NotNull [] matchLength(char @NotNull [] cs, char filler, int targetLength, boolean start) {
        if (cs.length > targetLength) throw new IllegalStateException("Target length shorter than actual length.");
        if (cs.length == targetLength) return cs;

        char[] cs1 = new char[targetLength];
        Arrays.fill(cs1, filler);
        System.arraycopy(cs, 0, cs1, start ? 0 : targetLength - cs.length, cs.length);
        return cs1;
    }

    // --------------------------------------------------------- Helper class

    private class Itr implements Iterator<Character> {
        int cursor;
        int lastRet = -1;

        Itr() {
        }

        @Override
        public boolean hasNext() {
            return cursor != length;
        }

        @Override
        public Character next() {
            int i = cursor;
            if (i >= length) throw new NoSuchElementException();
            char[] value = StringType.this.value.toCharArray();
            if (i >= value.length) throw new ConcurrentModificationException();
            cursor = i + 1;
            return value[lastRet = i];
        }

        @Override
        public void remove() {
            if (lastRet < 0) throw new IllegalStateException();
            try {
                StringType.this.remove(lastRet);
                cursor = lastRet;
                lastRet = -1;
            } catch (IndexOutOfBoundsException e) {
                throw new ConcurrentModificationException(e);
            }
        }

        @Override
        public void forEachRemaining(java.util.function.Consumer<? super Character> action) {
            Objects.requireNonNull(action);
            final int length = StringType.this.length;
            int i = cursor;

            if (i < length) {
                final char[] cs = chars;
                if (i >= cs.length) throw new ConcurrentModificationException();
                for (; i < length; i++) action.accept(charAt(cs, i));
                cursor = i;
                lastRet = i - 1;
            }
        }
    }
}
