package io.kitsuayaka.addon.tools;

import io.kitsuayaka.addon.types.NumberType;
import io.kitsuayaka.core.annotations.StatusMarkers;
import org.jetbrains.annotations.NotNull;

/**
 * The {@code interface} <em>Math</em> behaves similar to the built in {@code interface} {@link java.lang.Math}, with
 * a few additions and mostly acts as support for {@link NumberType}. It stores the minimum and maximum values for numeric
 * types, like {@code byte}, {@code short} or {@code int}. While also having the equivalent for {@code NumberType},
 * such as {@link #NT_LONG_MIN} (<em>equivalent to {@code new NumberType(Long.MIN_VALUE)}</em>). It acts as the core
 * functionality for any functions using {@link NumberType}, {@link io.kitsuayaka.core.elements.IntegerElement IntegerElement}
 * and {@link io.kitsuayaka.core.elements.DecimalElement DecimalElement}.
 * <hr />
 * Basic mathematical operations cannot be performed using this class, as that functionality is built into {@code NumberType},
 * see {@link NumberType#sum(NumberType)}, {@link NumberType#sub(NumberType)} or {@link NumberType#sqrt()} as an example.
 * However, this {@code interface} allows for casting into other values, like {@code double} to {@code int} with a theoretical
 * overflow handling, using {@link #intValue(double)} (<em>for overflow handling</em>) and {@link #intValueSafe(double)}
 * (<em>throws an error on a too big value</em>).
 *
 * @author Ayaka (<a href="https://github.com/shy-fox">GitHub</a>)
 * @version <code>1.6.2</code>
 * @since <code>1.0.9</code>
 */
@StatusMarkers.Addon
public interface Math {
    /**
     * The maximum possible value for the type {@code byte}, <code>2<sup>7</sup>-1</code> or {@code 127}.
     *
     * @see #BYTE_MIN
     * @see Byte#MAX_VALUE
     * @see #NT_BYTE_MAX
     */
    byte BYTE_MAX = (byte) 0x7F;
    /**
     * The minimum possible value for the type {@code byte}, <code>-2<sup>7</sup></code> or {@code -128}.
     *
     * @see #BYTE_MAX
     * @see Byte#MIN_VALUE
     * @see #NT_BYTE_MIN
     */
    byte BYTE_MIN = (byte) 0x80;
    /**
     * The maximum possible value for the type {@code short}, <code>2<sup>15</sup>-1</code> or {@code 32,767}.
     *
     * @see #SHORT_MIN
     * @see Short#MAX_VALUE
     * @see #NT_SHORT_MAX
     */
    short SHORT_MAX = (short) 0x7FFF;
    /**
     * The minimum possible value for the type {@code short}, <code>-2<sup>15</sup></code> or {@code -32,768}.
     *
     * @see #SHORT_MAX
     * @see Short#MIN_VALUE
     * @see #NT_SHORT_MIN
     */
    short SHORT_MIN = (short) 0x8000;
    /**
     * The maximum possible value for the type {@code int}, <code>2<sup>31</sup>-1</code> or {@code 2,147,483,647}.
     *
     * @see #INT_MIN
     * @see Integer#MAX_VALUE
     * @see #NT_INT_MAX
     */
    int INT_MAX = 0x7FFF_FFFF;
    /**
     * The minimum possible value for the type {@code int}, <code>-2<sup>31</sup></code> or {@code -2,147,483,648}.
     *
     * @see #INT_MAX
     * @see Integer#MIN_VALUE
     * @see #NT_INT_MIN
     */
    int INT_MIN = 0x8000_0000;
    /**
     * The maximum possible value for the type {@code long}, <code>2<sup>63</sup>-1</code> or {@code 9,223,372,036,854,775,807}.
     *
     * @see #LONG_MIN
     * @see Long#MAX_VALUE
     * @see #NT_LONG_MAX
     */
    long LONG_MAX = 0x7FFF_FFFF_FFFF_FFFFL;
    /**
     * The minimum possible value for the type {@code int}, <code>-2<sup>63</sup></code> or {@code -9,223,372,036,854,775,808}.
     *
     * @see #LONG_MAX
     * @see Long#MIN_VALUE
     * @see #NT_LONG_MIN
     */
    long LONG_MIN = 0x8000_0000_0000_0000L;
    /**
     * The maximum possible value for the type {@code float}, <code>(2-2<sup>-23</sup>)&middot;2<sup>127</sup></code>
     * or {@code 3.4028235E38} or <code>3.4028235&middot;10<sup>38</sup></code> or the hexadecimal floating-point literal
     * {@code 0x1.FFFFFEP+127F}.
     *
     * @see #FLOAT_MIN
     * @see Float#MAX_VALUE
     * @see #NT_FLOAT_MAX
     */
    float FLOAT_MAX = 0x1.FFFF_FEP+127F;
    /**
     * The minimum possible value for the type {@code float}, <code>2<sup>-149</sup></code>
     * or {@code 1.4E-45} or the hexadecimal floating-point literal
     * {@code 0x0.000002P-126F}.
     *
     * @see #FLOAT_MAX
     * @see Float#MIN_VALUE
     * @see #NT_FLOAT_MIN
     */
    float FLOAT_MIN = 0x0.0000_02P-126F;
    /**
     * The maximum possible value for the type {@code double}, <code>(2-2<sup>-52</sup>)&middot;2<sup>1023</sup></code>
     * or {@code 1.7976931348623157E308} or <code>1.7976931348623157&middot;10<sup>308</sup></code> or the hexadecimal
     * floating-point literal {@code 0x1.FFFFFFFFFFFFFP+1023}.
     *
     * @see #DOUBLE_MIN
     * @see Double#MAX_VALUE
     * @see #NT_DOUBLE_MAX
     */
    double DOUBLE_MAX = 0x1.FFFF_FFFF_FFFF_FP+1023;
    /**
     * The minimum possible value for the type {@code double}, <code>2<sup>-1047</sup></code>
     * or {@code 4.9E-324} or the hexadecimal floating-point literal
     * {@code 0x0.0000000000001P-1022}.
     *
     * @see #DOUBLE_MAX
     * @see Double#MIN_VALUE
     * @see #NT_DOUBLE_MIN
     */
    double DOUBLE_MIN = 0x0.0000_0000_0000_1P-1022;

    /**
     * A constant {@code value} carrying the {@code double} value of {@code 0.0000000000000000}.
     *
     * @see #NT_ZERO
     */
    double ZERO = 0.0000000000000000D;

    /**
     * A constant holding the value {@code positive infinity} (<em>&infin;</em>). Equal to {@code 1.0 / 0.0}.
     *
     * @see #NEGATIVE_INFINITY
     * @see NumberType#POSITIVE_INFINITY
     * @see NumberType#NEGATIVE_INFINITY
     */
    @SuppressWarnings({"NumericOverflow", "divzero"})
    double POSITIVE_INFINITY = 1.0 / 0d;

    /**
     * A constant holding the value {@code negative infinity} (<em>-&infin;</em>). Equal to {@code -1.0 / 0.0}.
     *
     * @see #POSITIVE_INFINITY
     * @see NumberType#POSITIVE_INFINITY
     * @see NumberType#NEGATIVE_INFINITY
     */
    @SuppressWarnings({"NumericOverflow", "divzero"})
    double NEGATIVE_INFINITY = -1.0 / 0d;

    /**
     * A constant holding the value {@code NaN} <em>(Not-a-Number)</em>, equal to {@code 0.0 / 0.0}.
     * Will return {@code false} if checked against itself.
     *
     * @see NumberType#NaN
     */
    @SuppressWarnings("divzero")
    double NaN = 0.0 / 0d;

    // --------------------------------------------------------- NumberType stuff

    /**
     * A {@code static} constant {@link NumberType} holding the maximum {@code byte} value specified by {@link #BYTE_MAX}.
     *
     * @see #NT_BYTE_MIN
     * @since <code>1.1.0</code>
     */
    NumberType NT_BYTE_MAX = new NumberType(BYTE_MAX);
    /**
     * A {@code static} constant {@link NumberType} holding the minimum {@code byte} value specified by {@link #BYTE_MIN}.
     *
     * @see #NT_BYTE_MAX
     * @since <code>1.1.0</code>
     */
    NumberType NT_BYTE_MIN = new NumberType(BYTE_MIN);
    /**
     * A {@code static} constant {@link NumberType} holding the maximum {@code short} value specified by {@link #SHORT_MAX}.
     *
     * @see #NT_SHORT_MIN
     * @since <code>1.1.0</code>
     */
    NumberType NT_SHORT_MAX = new NumberType(SHORT_MAX);
    /**
     * A {@code static} constant {@link NumberType} holding the minimum {@code short} value specified by {@link #SHORT_MIN}.
     *
     * @see #NT_SHORT_MAX
     * @since <code>1.1.0</code>
     */
    NumberType NT_SHORT_MIN = new NumberType(SHORT_MIN);
    /**
     * A {@code static} constant {@link NumberType} holding the maximum {@code int} value specified by {@link #INT_MAX}.
     *
     * @see #NT_INT_MIN
     * @since <code>1.1.0</code>
     */
    NumberType NT_INT_MAX = new NumberType(INT_MAX);
    /**
     * A {@code static} constant {@link NumberType} holding the minimum {@code int} value specified by {@link #INT_MIN}.
     *
     * @see #NT_INT_MAX
     * @since <code>1.1.0</code>
     */
    NumberType NT_INT_MIN = new NumberType(INT_MIN);
    /**
     * A {@code static} constant {@link NumberType} holding the maximum {@code long} value specified by {@link #LONG_MAX}.
     *
     * @see #NT_LONG_MIN
     * @since <code>1.1.0</code>
     */
    NumberType NT_LONG_MAX = new NumberType(LONG_MAX);
    /**
     * A {@code static} constant {@link NumberType} holding the minimum {@code long} value specified by {@link #LONG_MIN}.
     *
     * @see #NT_LONG_MAX
     * @since <code>1.1.0</code>
     */
    NumberType NT_LONG_MIN = new NumberType(LONG_MIN);
    /**
     * A {@code static} constant {@link NumberType} holding the maximum {@code float} value specified by {@link #FLOAT_MAX}.
     *
     * @see #NT_FLOAT_MIN
     * @since <code>1.1.0</code>
     */
    NumberType NT_FLOAT_MAX = new NumberType(FLOAT_MAX);
    /**
     * A {@code static} constant {@link NumberType} holding the minimum {@code float} value specified by {@link #FLOAT_MIN}.
     *
     * @see #NT_FLOAT_MAX
     * @since <code>1.1.0</code>
     */
    NumberType NT_FLOAT_MIN = new NumberType(FLOAT_MAX);
    /**
     * A {@code static} constant {@link NumberType} holding the maximum {@code double} value specified by {@link #DOUBLE_MAX}.
     *
     * @see #NT_DOUBLE_MIN
     * @see NumberType#MAX_VALUE
     * @since <code>1.1.0</code>
     */
    NumberType NT_DOUBLE_MAX = new NumberType(DOUBLE_MAX);
    /**
     * A {@code static} constant {@link NumberType} holding the minimum {@code double} value specified by {@link #DOUBLE_MIN}.
     *
     * @see #NT_DOUBLE_MAX
     * @see NumberType#MIN_VALUE
     * @since <code>1.1.0</code>
     */
    NumberType NT_DOUBLE_MIN = new NumberType(DOUBLE_MIN);

    /**
     * A constant {@code value} carrying the {@code double} value of {@code 0.0000000000000000}.
     *
     * @see #ZERO
     * @since <code>1.1.0</code>
     */
    NumberType NT_ZERO = new NumberType(0.0000000000000000D);

    // --------------------------------------------------------- Basic functions

    /**
     * Compares two values of {@link NumberType} and returns a value based on that.
     *
     * @param a The value to compare.
     * @param b THe value to compare to.
     * @return {@code 1} if {@code a} is greater than {@code b}, {@code -1} if {@code b} is Greater than {@code a} and {@code 0}
     * if both values are equal to each other.
     * @see #compare(double, double)
     * @since <code>1.1.0</code>
     */
    static int compare(@NotNull NumberType a, @NotNull NumberType b) {
        return a.doubleValue() > b.doubleValue() ? 1 : (a.doubleValue() < b.doubleValue() ? -1 : 0);
    }

    /**
     * Compares two values of {@code double} and returns a value based on that.
     *
     * @param a The value to compare.
     * @param b THe value to compare to.
     * @return {@code 1} if {@code a} is greater than {@code b}, {-1} if {@code b} is Greater than {@code a} and {@code 0}
     * if both values are equal to each other.
     * @see #compare(NumberType, NumberType)
     * @since <code>1.1.0</code>
     */
    static int compare(double a, double b) {
        return a > b ? 1 : a < b ? -1 : 0;
    }

    /**
     * Returns the largest value of type {@code double} that was passed in, with an optional third argument, which can
     * be an array of {@code n} elements.
     *
     * @param a A {@code double}.
     * @param b Another {@code double}
     * @param c A list of {@code double} with length {@code n} (<strong>Optional</strong>).
     * @return The larger of {@code a} and {@code b}, adapted to include {@code c} if any arguments are passed in for it.
     * @see #max(NumberType, NumberType, NumberType...)
     * @see #min(double, double, double...)
     * @see #min(NumberType, NumberType, NumberType...)
     * @since <code>1.1.0</code>
     */
    static double max(double a, double b, double... c) {
        double recordV = a > DOUBLE_MIN ? a : DOUBLE_MIN;
        if (b > recordV) recordV = b;
        for (double d : c) if (d > recordV) recordV = d;
        return recordV;
    }

    /**
     * Returns the largest value of type {@code NumberType} that was passed in, with an optional third argument, which can
     * be an array of {@code n} elements.
     *
     * @param a A {@code NumberType}.
     * @param b Another {@code NumberType}
     * @param c A list of {@code NumberTypes} with length {@code n} (<strong>Optional</strong>).
     * @return The larger of {@code a} and {@code b}, adapted to include {@code c} if any arguments are passed in for it.
     * @see #max(double, double, double...)
     * @see #min(double, double, double...)
     * @see #min(NumberType, NumberType, NumberType...)
     * @since <code>1.1.0</code>
     */
    static NumberType max(NumberType a, NumberType b, NumberType... c) {
        NumberType recordV = compare(NT_DOUBLE_MIN, a) == -1 ? a : NT_DOUBLE_MIN;
        if (compare(recordV, b) == -1) recordV = b;
        for (NumberType d : c) if (compare(recordV, d) == -1) recordV = d;
        return recordV;
    }

    /**
     * Returns the smallest value of type {@code double} that was passed in, with an optional third argument, which can
     * be an array of {@code n} elements.
     *
     * @param a A {@code double}.
     * @param b Another {@code double}
     * @param c A list of {@code double} with length {@code n} (<strong>Optional</strong>).
     * @return The smaller of {@code a} and {@code b}, adapted to include {@code c} if any arguments are passed in for it.
     * @see #max(NumberType, NumberType, NumberType...)
     * @see #max(double, double, double...)
     * @see #min(NumberType, NumberType, NumberType...)
     * @since <code>1.1.0</code>
     */
    static double min(double a, double b, double... c) {
        double recordV = a < DOUBLE_MAX ? a : DOUBLE_MAX;
        if (b < recordV) recordV = b;
        for (double d : c) if (d < recordV) recordV = d;
        return recordV;
    }

    /**
     * Returns the smallest value of type {@code NumberType} that was passed in, with an optional third argument, which can
     * be an array of {@code n} elements.
     *
     * @param a A {@code NumberType}.
     * @param b Another {@code NumberType}
     * @param c A list of {@code NumberTypes} with length {@code n} (<strong>Optional</strong>).
     * @return The smaller of {@code a} and {@code b}, adapted to include {@code c} if any arguments are passed in for it.
     * @see #max(NumberType, NumberType, NumberType...)
     * @see #max(double, double, double...)
     * @see #min(double, double, double...)
     * @since <code>1.1.0</code>
     */
    static NumberType min(NumberType a, NumberType b, NumberType... c) {
        NumberType recordV = compare(NT_DOUBLE_MAX, a) == 1 ? a : NT_DOUBLE_MAX;
        if (compare(recordV, b) == 1) recordV = b;
        for (NumberType d : c) if (compare(recordV, d) == 1) recordV = d;
        return recordV;
    }

    /**
     * Checks if the supplied {@code value} is in the specified {@code range (start - end)}.
     *
     * @param value The value to check.
     * @param start The {@code start} value, inclusive.
     * @param end   The {@code end} value, inclusive.
     * @return Whether the supplied {@code value} is in the specified {@code range} or not.
     * @see #threshold(NumberType, NumberType, NumberType)
     * @see #inBounds(double, double, double)
     * @since <code>1.1.6</code>
     */
    static boolean threshold(double value, double start, double end) {
        return start <= end && start <= value && value <= end;
    }

    /**
     * Checks if the supplied {@code value} is in the specified {@code range (start - end)}.
     *
     * @param value The value to check.
     * @param start The {@code start} value, inclusive.
     * @param end   The {@code end} value, inclusive.
     * @return Whether the supplied {@code value} is in the specified {@code range} or not.
     * @see #threshold(double, double, double)
     * @see #inBounds(double, double, double)
     * @since <code>1.1.6</code>
     */
    static boolean threshold(NumberType value, NumberType start, NumberType end) {
        return compare(start, end) != 1 && compare(start, value) != 1 && compare(value, end) != 1;
    }

    /**
     * Checks if the supplied {@code value} is in the specified {@code range (start - end)}.
     *
     * @param value The value to check.
     * @param start The {@code start} value, inclusive.
     * @param end   The {@code end} value, inclusive.
     * @throws IndexOutOfBoundsException If the supplied {@code value} is outside those bounds.
     * @see #threshold(double, double, double)
     * @see #inBounds(NumberType, NumberType, NumberType)
     * @since <code>1.1.2</code>
     */
    static void inBounds(double value, double start, double end) throws IndexOutOfBoundsException {
        if (end < value || value < start || start > end)
            throw new IndexOutOfBoundsException("Value not in range; Start = " + start + " / End = " + end);
    }

    /**
     * Checks if the supplied {@code value} is in the specified {@code range (start - end)}.
     *
     * @param value The value to check.
     * @param start The {@code start} value, inclusive.
     * @param end   The {@code end} value, inclusive.
     * @throws IndexOutOfBoundsException If the supplied {@code value} is outside those bounds.
     * @see #threshold(double, double, double)
     * @see #inBounds(double, double, double)
     * @since <code>1.1.2</code>
     */
    static void inBounds(NumberType value, NumberType start, NumberType end) throws IndexOutOfBoundsException {
        if (compare(value, end) == 1 || compare(start, value) == 1 || compare(start, end) == 1)
            throw new IndexOutOfBoundsException("Value not in range; Start = " + start + " / End = " + end);
    }

    // --------------------------------------------------------- Conversions

    /**
     * Casts a given value of any range to a {@code byte}, throws an error if the {@code value} is larger than the maximum
     * for {@code byte}.
     *
     * @param value The {@code value} to cast.
     * @return A converted value, constrained to the range of {@code byte}.
     * @throws IndexOutOfBoundsException If the supplied value is larger than the range of {@code byte}.
     * @see #byteValue(double)
     * @since <code>1.2.3</code>
     */
    static byte byteValueSafe(double value) throws IndexOutOfBoundsException {
        return (byte) constrain(value, BYTE_MIN, BYTE_MAX, false);
    }

    /**
     * Casts a given value of any range to a {@code byte}, throws an error if the {@code value} is larger than the maximum
     * for {@code byte}. This value will loop until it reaches a value which is inside the range of {@code byte}.
     *
     * @param value The {@code value} to cast.
     * @return A converted value, constrained to the range of {@code byte}.
     * @see #byteValueSafe(double)
     * @since <code>1.2.3</code>
     */
    static byte byteValue(double value) {
        return (byte) constrain(value, BYTE_MIN, BYTE_MAX, true);
    }

    /**
     * Casts a given value of any range to a {@code short}, throws an error if the {@code value} is larger than the maximum
     * for {@code short}.
     *
     * @param value The {@code value} to cast.
     * @return A converted value, constrained to the range of {@code short}.
     * @throws IndexOutOfBoundsException If the supplied value is larger than the range of {@code short}.
     * @see #shortValue(double)
     * @since <code>1.2.3</code>
     */
    static short shortValueSafe(double value) throws IndexOutOfBoundsException {
        return (short) constrain(value, SHORT_MIN, SHORT_MAX, false);
    }

    /**
     * Casts a given value of any range to a {@code short}, throws an error if the {@code value} is larger than the maximum
     * for {@code short}. This value will loop until it reaches a value which is inside the range of {@code short}.
     *
     * @param value The {@code value} to cast.
     * @return A converted value, constrained to the range of {@code short}.
     * @see #shortValueSafe(double)
     * @since <code>1.2.3</code>
     */
    static short shortValue(double value) {
        return (short) constrain(value, SHORT_MIN, SHORT_MAX, true);
    }

    /**
     * Casts a given value of any range to a {@code int}, throws an error if the {@code value} is larger than the maximum
     * for {@code int}.
     *
     * @param value The {@code value} to cast.
     * @return A converted value, constrained to the range of {@code int}.
     * @throws IndexOutOfBoundsException If the supplied value is larger than the range of {@code int}.
     * @see #intValue(double)
     * @since <code>1.2.3</code>
     */
    static int intValueSafe(double value) throws IndexOutOfBoundsException {
        return (int) constrain(value, INT_MIN, INT_MAX, false);
    }

    /**
     * Casts a given value of any range to a {@code int}, throws an error if the {@code value} is larger than the maximum
     * for {@code int}. This value will loop until it reaches a value which is inside the range of {@code int}.
     *
     * @param value The {@code value} to cast.
     * @return A converted value, constrained to the range of {@code int}.
     * @see #intValueSafe(double)
     * @since <code>1.2.3</code>
     */
    static int intValue(double value) {
        return (int) constrain(value, INT_MIN, INT_MAX, true);
    }

    /**
     * Casts a given value of any range to a {@code long}, throws an error if the {@code value} is larger than the maximum
     * for {@code int}.
     *
     * @param value The {@code value} to cast.
     * @return A converted value, constrained to the range of {@code long}.
     * @throws IndexOutOfBoundsException If the supplied value is larger than the range of {@code long}.
     * @see #longValue(double)
     * @since <code>1.2.3</code>
     */
    static long longValueSafe(double value) throws IndexOutOfBoundsException {
        return (long) constrain(value, LONG_MIN, LONG_MAX, false);
    }

    /**
     * Casts a given value of any range to a {@code long}, throws an error if the {@code value} is larger than the maximum
     * for {@code long}. This value will loop until it reaches a value which is inside the range of {@code long}.
     *
     * @param value The {@code value} to cast.
     * @return A converted value, constrained to the range of {@code long}.
     * @see #longValueSafe(double)
     * @since <code>1.2.3</code>
     */
    static long longValue(double value) {
        return (long) constrain(value, LONG_MIN, LONG_MAX, true);
    }

    /**
     * Casts a given value of any range to a {@code float}, throws an error if the {@code value} is larger than the maximum
     * for {@code float}.
     *
     * @param value The {@code value} to cast.
     * @return A converted value, constrained to the range of {@code float}.
     * @throws IndexOutOfBoundsException If the supplied value is larger than the range of {@code float}.
     * @see #floatValue(double)
     * @since <code>1.2.3</code>
     */
    static float floatValueSafe(double value) throws IndexOutOfBoundsException {
        return (float) constrain(value, FLOAT_MIN, FLOAT_MAX, false);
    }

    /**
     * Casts a given value of any range to a {@code float}, throws an error if the {@code value} is larger than the maximum
     * for {@code float}. This value will loop until it reaches a value which is inside the range of {@code float}.
     *
     * @param value The {@code value} to cast.
     * @return A converted value, constrained to the range of {@code float}.
     * @see #floatValueSafe(double
     * @since <code>1.2.3</code>
     */
    static float floatValue(double value) {
        return (float) constrain(value, FLOAT_MIN, FLOAT_MAX, true);
    }

    /**
     * Casts a given value of any range to a {@code double}, throws an error if the {@code value} is larger than the maximum
     * for {@code double}.
     *
     * @param value The {@code value} to cast.
     * @return A converted value, constrained to the range of {@code double}.
     * @throws IndexOutOfBoundsException If the supplied value is larger than the range of {@code double}.
     *                                   <code>({@link #NEGATIVE_INFINITY}, {@link #POSITIVE_INFINITY} and {@link #NaN})</code>
     * @since <code>1.2.3</code>
     */
    static double doubleValue(double value) throws IndexOutOfBoundsException {
        return constrain(value, DOUBLE_MIN, DOUBLE_MAX, true);
    }

    /**
     * Casts a given value of any range to a {@code NumberType}, throws an error if the {@code value} is larger than the maximum
     * for {@code byte}.
     *
     * @param value The {@code NumberType} to cast.
     * @return A converted value, constrained to the range of {@code byte}.
     * @throws IndexOutOfBoundsException If the supplied value is larger than the range of {@code byte}.
     * @see #byteValue(NumberType)
     * @since <code>1.2.3</code>
     */
    static byte byteValueSafe(NumberType value) {
        return (byte) constrain(value, NT_BYTE_MIN, NT_BYTE_MAX, false).doubleValue();
    }

    /**
     * Casts a given value of any range to a {@code byte}, throws an error if the {@code NumberType} is larger than the maximum
     * for {@code byte}. This value will loop until it reaches a value which is inside the range of {@code byte}.
     *
     * @param value The {@code NumberType} to cast.
     * @return A converted value, constrained to the range of {@code byte}.
     * @see #byteValueSafe(NumberType)
     * @since <code>1.2.3</code>
     */
    static byte byteValue(NumberType value) {
        return (byte) constrain(value, NT_BYTE_MIN, NT_BYTE_MAX, true).doubleValue();
    }

    /**
     * Casts a given value of any range to a {@code short}, throws an error if the {@code NumberType} is larger than the maximum
     * for {@code short}.
     *
     * @param value The {@code NumberType} to cast.
     * @return A converted value, constrained to the range of {@code short}.
     * @throws IndexOutOfBoundsException If the supplied value is larger than the range of {@code short}.
     * @see #shortValue(NumberType)
     * @since <code>1.2.3</code>
     */
    static short shortValueSafe(NumberType value) {
        return (short) constrain(value, NT_SHORT_MIN, NT_SHORT_MAX, false).doubleValue();
    }

    /**
     * Casts a given value of any range to a {@code short}, throws an error if the {@code NumberType} is larger than the maximum
     * for {@code short}. This value will loop until it reaches a value which is inside the range of {@code short}.
     *
     * @param value The {@code NumberType} to cast.
     * @return A converted value, constrained to the range of {@code short}.
     * @see #shortValueSafe(NumberType)
     * @since <code>1.2.3</code>
     */
    static short shortValue(NumberType value) {
        return (short) constrain(value, NT_SHORT_MIN, NT_SHORT_MAX, true).doubleValue();
    }

    /**
     * Casts a given value of any range to a {@code int}, throws an error if the {@code NumberType} is larger than the maximum
     * for {@code int}.
     *
     * @param value The {@code NumberType} to cast.
     * @return A converted value, constrained to the range of {@code int}.
     * @throws IndexOutOfBoundsException If the supplied value is larger than the range of {@code int}.
     * @see #intValue(NumberType)
     * @since <code>1.2.3</code>
     */
    static int intValueSafe(NumberType value) {
        return (int) constrain(value, NT_INT_MIN, NT_INT_MAX, false).doubleValue();
    }

    /**
     * Casts a given value of any range to a {@code int}, throws an error if the {@code NumberType} is larger than the maximum
     * for {@code int}. This value will loop until it reaches a value which is inside the range of {@code int}.
     *
     * @param value The {@code NumberType} to cast.
     * @return A converted value, constrained to the range of {@code int}.
     * @see #intValueSafe(NumberType)
     * @since <code>1.2.3</code>
     */
    static int intValue(NumberType value) {
        return (int) constrain(value, NT_INT_MIN, NT_INT_MAX, true).doubleValue();
    }

    /**
     * Casts a given value of any range to a {@code long}, throws an error if the {@code NumberType} is larger than the maximum
     * for {@code int}.
     *
     * @param value The {@code NumberType} to cast.
     * @return A converted value, constrained to the range of {@code long}.
     * @throws IndexOutOfBoundsException If the supplied value is larger than the range of {@code long}.
     * @see #longValue(NumberType)
     * @since <code>1.2.3</code>
     */
    static long longValueSafe(NumberType value) {
        return (long) constrain(value, NT_LONG_MIN, NT_LONG_MAX, false).doubleValue();
    }

    /**
     * Casts a given value of any range to a {@code long}, throws an error if the {@code NumberType} is larger than the maximum
     * for {@code long}. This value will loop until it reaches a value which is inside the range of {@code long}.
     *
     * @param value The {@code NumberType} to cast.
     * @return A converted value, constrained to the range of {@code long}.
     * @see #longValueSafe(NumberType)
     * @since <code>1.2.3</code>
     */
    static long longValue(NumberType value) {
        return (long) constrain(value, NT_LONG_MIN, NT_LONG_MAX, true).doubleValue();
    }

    /**
     * Casts a given value of any range to a {@code float}, throws an error if the {@code NumberType} is larger than the maximum
     * for {@code float}.
     *
     * @param value The {@code NumberType} to cast.
     * @return A converted value, constrained to the range of {@code float}.
     * @throws IndexOutOfBoundsException If the supplied value is larger than the range of {@code float}.
     * @see #floatValue(NumberType)
     * @since <code>1.2.3</code>
     */
    static float floatValueSafe(NumberType value) {
        return (float) constrain(value, NT_FLOAT_MIN, NT_FLOAT_MAX, false).doubleValue();
    }

    /**
     * Casts a given value of any range to a {@code float}, throws an error if the {@code NumberType} is larger than the maximum
     * for {@code float}. This value will loop until it reaches a value which is inside the range of {@code float}.
     *
     * @param value The {@code NumberType} to cast.
     * @return A converted value, constrained to the range of {@code float}.
     * @see #floatValueSafe(NumberType)
     * @since <code>1.2.3</code>
     */
    static float floatValue(NumberType value) {
        return (float) constrain(value, NT_FLOAT_MIN, NT_FLOAT_MAX, true).doubleValue();
    }

    /**
     * Casts a given value of any range to a {@code double}, throws an error if the {@code NumberType} is larger than the maximum
     * for {@code double}.
     *
     * @param value The {@code NumberType} to cast.
     * @return A converted value, constrained to the range of {@code double}.
     * @throws IndexOutOfBoundsException If the supplied value is larger than the range of {@code double}.
     *                                   <code>({@link NumberType#NEGATIVE_INFINITY},
     *                                   {@link NumberType#POSITIVE_INFINITY} and {@link NumberType#NaN})</code>
     * @since <code>1.2.3</code>
     */
    static double doubleValue(NumberType value) {
        return constrain(value, NT_DOUBLE_MIN, NT_DOUBLE_MAX, true).doubleValue();
    }

    // --------------------------------------------------------- Helper stuff

    /**
     * Constrains a {@code value} into a specified {@code range}, and based on the value passed in for {@code unsafe},
     * will loop until it hits a value which is in the specified {@code range}.
     *
     * @param value  The {@code value} to constrain.
     * @param start  The starting {@code value}, also acting as the {@code minimum + 1}.
     * @param end    The ending {@code value}, also acting as the {@code maximum - 1}.
     * @param unsafe Whether it should be allowed to loop or not, if set to {@code false}, it will throw an {@code error}
     *               if the value is out of bounds.
     * @return An adjusted {@code value}, based on the supplied {@code start} and {@code end}.
     * @throws IndexOutOfBoundsException If {@code unsafe} is set to {@code false} and the supplied {@code value} is out
     *                                   of bounds.
     * @since <code>1.2.2</code>
     */
    @StatusMarkers.Experimental(since = "1.6.2", signatureChanged = true)
    static double constrain(double value, double start, double end, boolean unsafe) throws IndexOutOfBoundsException {
        try {
            inBounds(value, start, end);
            return max(min(end, value), start);
        } catch (IndexOutOfBoundsException exception) {
            if (unsafe) {
                double difference = end - start;
                // upper loop
                if (value > end)
                    //noinspection StatementWithEmptyBody
                    while ((value -= difference) > end) {
                    }
                if (start > value)
                    //noinspection StatementWithEmptyBody
                    while (start > (value += difference)) {
                    }

                return value;
            }
            throw exception;
        }
    }

    /**
     * Constrains a {@code value} into a specified {@code range}, and based on the value passed in for {@code unsafe},
     * will loop until it hits a value which is in the specified {@code range}.
     *
     * @param value  The {@code value} to constrain.
     * @param start  The starting {@code value}, also acting as the {@code minimum + 1}.
     * @param end    The ending {@code value}, also acting as the {@code maximum - 1}.
     * @param unsafe Whether it should be allowed to loop or not, if set to {@code false}, it will throw an {@code error}
     *               if the value is out of bounds.
     * @return An adjusted {@code value}, based on the supplied {@code start} and {@code end}.
     * @throws IndexOutOfBoundsException If {@code unsafe} is set to {@code false} and the supplied {@code value} is out
     *                                   of bounds.
     */
    @StatusMarkers.Experimental(since = "1.6.2", signatureChanged = true)
    static NumberType constrain(NumberType value, NumberType start, NumberType end, boolean unsafe) throws IndexOutOfBoundsException {
        try {
            inBounds(value, start, end);
            return max(min(end, value), start);
        } catch (IndexOutOfBoundsException exception) {
            if (unsafe) {
                NumberType difference = end.sub(start);
                if (compare(value, end) == 1)
                    //noinspection StatementWithEmptyBody
                    while (compare((value = value.sub(difference)), end) == 1) {
                    }
                if (compare(start, value) == 1)
                    //noinspection StatementWithEmptyBody
                    while (compare(start, (value = value.sum(difference))) == 1) {
                    }
                return value;
            }
            throw exception;
        }
    }
}
