package io.kitsuayaka.addon.types;

import io.kitsuayaka.addon.tools.Math;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.io.Serial;
import java.io.Serializable;

public final class NumberType extends BaseType<Number> implements Serializable {
    @Serial
    private static final long serialVersionUID = 3;

    private double internalValue;

    // default no support
    private static boolean supportsZeroDiv = false;

    // --------------------------------------------------------- Static values

    /**
     * This {@code NumberType} carries the value of {@code NaN} (see {@link Double#NaN}), the result of
     * {@code 0.0 / 0.0}, or simply put {@code Not-a-Number}, will return {@code false} if checked against itself.
     *
     * @see Math#NaN
     */
    @SuppressWarnings("divzero")
    public static final NumberType NaN = createWithBypass(0 / 0.0d);

    /**
     * This {@code NumberType} carries the value of {@code POSITIVE INFINITY};
     * <br> (see {@link Double#POSITIVE_INFINITY}, or &infin;),
     * the result of {@code 1.0 / 0.0}.
     *
     * @see #NEGATIVE_INFINITY
     * @see Math#POSITIVE_INFINITY
     * @see Math#NEGATIVE_INFINITY
     */
    @SuppressWarnings({"divzero", "NumericOverflow"})
    public static final NumberType POSITIVE_INFINITY = createWithBypass(1.0d / 0.0d);

    /**
     * This {@code NumberType} carries the value of {@code NEGATIVE INFINITY}
     * <br>
     * (see {@link Double#NEGATIVE_INFINITY}, or -&infin;), the result of {@code -1.0 / 0.0}.
     *
     * @see #POSITIVE_INFINITY
     * @see Math#POSITIVE_INFINITY
     * @see Math#NEGATIVE_INFINITY
     */
    @SuppressWarnings({"divzero", "NumericOverflow"})
    public static final NumberType NEGATIVE_INFINITY = createWithBypass(-1.0d / 0.0d);

    /**
     * This {@code NumberType} stores the maximum double value {@code 0x1.FFFFFFFFFFFFFP1023},
     * <br>or in integer notation (2-2<sup>-52</sup>)&times;2<sup>127</sup>, equal to {@link Double#MAX_VALUE}
     *
     * @see #MIN_VALUE
     * @see io.kitsuayaka.addon.tools.Math#NT_DOUBLE_MAX
     * @see io.kitsuayaka.addon.tools.Math#DOUBLE_MAX
     */
    public static final NumberType MAX_VALUE = new NumberType(0x1.FFFFFFFFFFFFFP1023);

    /**
     * This {@code NumberType} stores the minimum double value {@code 0x0.0000000000001P-1022},
     * <br>
     * or in integer notation 2<sup>-1074</sup>, equal to {@link Double#MIN_VALUE}
     *
     * @see #MAX_VALUE
     * @see io.kitsuayaka.addon.tools.Math#NT_DOUBLE_MIN
     * @see io.kitsuayaka.addon.tools.Math#DOUBLE_MIN
     */
    public static final NumberType MIN_VALUE = new NumberType(0x0.0000000000001P-1022);

    /**
     * This {@code NumberType} carries the value of {@code 0}, actually: {@code 0.0000000000000d}.
     */
    public static final NumberType ZERO = new NumberType(0.0000000000000d);

    // --------------------------------------------------------- Zero Division check stuff

    private static double zeroDivisionCheck(double d, boolean bypass) throws UnsupportedOperationException {
        boolean a = d != d || ((d < 0 ? -d : d) > 0x1.FFFFFFFFFFFFFP1023);
        if (bypass || (a && supportsZeroDiv) || !a) return d;
        throw new UnsupportedOperationException("Zero Division Support disabled.");
    }

    /**
     * Globally set the value for <em>Zero Division Support</em> to the given value.
     *
     * @param a_flag the value to set {@code Zero Division Support} to
     * @see #enableZeroDivisionSupport()
     * @see #disableZeroDivisionSupport()
     * @see #toggleZeroDivisionSupport()
     */
    public static void setZeroDivisionSupport(boolean a_flag) {
        supportsZeroDiv = a_flag;
    }

    /**
     * Globally enable <em>Zero Division Support</em>.
     *
     * @see #setZeroDivisionSupport(boolean)
     * @see #disableZeroDivisionSupport()
     * @see #toggleZeroDivisionSupport()
     */
    public static void enableZeroDivisionSupport() {
        setZeroDivisionSupport(true);
    }

    /**
     * Globally disable <em>Zero Division Support</em>.
     *
     * @see #setZeroDivisionSupport(boolean)
     * @see #enableZeroDivisionSupport()
     * @see #toggleZeroDivisionSupport()
     */
    public static void disableZeroDivisionSupport() {
        setZeroDivisionSupport(false);
    }

    /**
     * Globally toggle <em>Zero Division Support</em>.
     *
     * @see #setZeroDivisionSupport(boolean)
     * @see #enableZeroDivisionSupport()
     * @see #disableZeroDivisionSupport()
     */
    public static void toggleZeroDivisionSupport() {
        setZeroDivisionSupport(!supportsZeroDiv);
    }

    /**
     * Returns whether <em>Zero Division Support</em> is enabled or not.
     *
     * @return Whether <em>Zero Division Support</em> is enabled or not.
     */
    public static boolean supportsZeroDivision() {
        return supportsZeroDiv;
    }

    // --------------------------------------------------------- Constructors

    private NumberType(@NotNull Number n, boolean b) {
        super(zeroDivisionCheck(n.doubleValue(), b));
    }

    private NumberType(Number n) {
        this(n, false);
    }

    /**
     * Creates a new instance of {@code NumberType} from a {@code char} by taking its integer value and casting it
     * to {@code double}
     *
     * @param c the character to take the integer value of
     * @see #NumberType()
     * @see #NumberType(byte)
     * @see #NumberType(short)
     * @see #NumberType(int)
     * @see #NumberType(long)
     * @see #NumberType(float)
     * @see #NumberType(double)
     */
    public NumberType(char c) {
        this((int) c);
    }

    /**
     * Creates a new instance of {@code NumberType} from a {@code byte} by taking its value and casting it
     * to {@code double}
     *
     * @param b the {@code byte} to take the value of
     * @see #NumberType()
     * @see #NumberType(char)
     * @see #NumberType(short)
     * @see #NumberType(int)
     * @see #NumberType(long)
     * @see #NumberType(float)
     * @see #NumberType(double)
     */
    public NumberType(byte b) {
        this((double) b);
    }

    /**
     * Creates a new instance of {@code NumberType} from a {@code short} by taking its value and casting it
     * to {@code double}
     *
     * @param s the {@code short} to take the value of
     * @see #NumberType()
     * @see #NumberType(char)
     * @see #NumberType(byte)
     * @see #NumberType(int)
     * @see #NumberType(long)
     * @see #NumberType(float)
     * @see #NumberType(double)
     */
    public NumberType(short s) {
        this((double) s);
    }

    /**
     * Creates a new instance of {@code NumberType} from an {@code int} by taking its value and casting it
     * to {@code double}
     *
     * @param i the {@code int} to take the value of
     * @see #NumberType()
     * @see #NumberType(char)
     * @see #NumberType(byte)
     * @see #NumberType(short)
     * @see #NumberType(long)
     * @see #NumberType(float)
     * @see #NumberType(double)
     */
    public NumberType(int i) {
        this((double) i);
    }

    /**
     * Creates a new instance of {@code NumberType} from a {@code long} by taking its value and casting it
     * to {@code double}
     *
     * @param l the {@code long} to take the value of
     * @see #NumberType()
     * @see #NumberType(char)
     * @see #NumberType(byte)
     * @see #NumberType(short)
     * @see #NumberType(int)
     * @see #NumberType(float)
     * @see #NumberType(double)
     */
    public NumberType(long l) {
        this((double) l);
    }

    /**
     * Creates a new instance of {@code NumberType} from a {@code float} by taking its value and casting it
     * to {@code double}
     *
     * @param f the {@code float} to take the value of
     * @see #NumberType()
     * @see #NumberType(char)
     * @see #NumberType(byte)
     * @see #NumberType(short)
     * @see #NumberType(int)
     * @see #NumberType(long)
     * @see #NumberType(double)
     */
    public NumberType(float f) {
        this((double) f);
    }

    /**
     * Creates a new instance of {@code NumberType} from a {@code double}
     *
     * @param d the {@code double} to take the value of
     * @see #NumberType()
     * @see #NumberType(char)
     * @see #NumberType(byte)
     * @see #NumberType(short)
     * @see #NumberType(int)
     * @see #NumberType(long)
     * @see #NumberType(float)
     */
    public NumberType(double d) {
        this((Number) d);
    }

    /**
     * Creates a new instance of {@code NumberType} with a default value of {@code 0}
     *
     * @see #NumberType(char)
     * @see #NumberType(byte)
     * @see #NumberType(short)
     * @see #NumberType(int)
     * @see #NumberType(long)
     * @see #NumberType(float)
     * @see #NumberType(double)
     */
    public NumberType() {
        this(0);
    }

    // --------------------------------------------------------- Core

    /**
     * Resets the value of this object to {@code 0}.
     */
    public void reset() {
        value = 0;
        internalValue = 0;
    }

    public boolean isDecimal() {
        return value instanceof Double || value instanceof Float;
    }

    @Contract(" -> new")
    public @NotNull @Unmodifiable NumberType floor() {
        return new NumberType(Math.intValue(internalValue));
    }

    @Contract(" -> new")
    public @NotNull @Unmodifiable NumberType ceil() {
        return new NumberType(Math.intValue(internalValue) + 1);
    }

    /**
     * Returns the {@code internal value} stored in this object, a value of type {@code double}.
     *
     * @return the {@code internal value} store in this object.
     */
    public double doubleValue() {
        return internalValue;
    }

    // --------------------------------------------------------- Operators

    /**
     * Adds {@code this object} to the {@code other object}, essentially acting as the operator {@code +}.
     *
     * @param other The {@code value} to add.
     * @return The sum of {@code this object} and the {@code other object}.
     * @see #sum(NumberType, NumberType, NumberType...)
     */
    @Contract("_ -> new")
    public @NotNull NumberType sum(@NotNull NumberType other) {
        double internalValue = this.internalValue + other.internalValue;
        return new NumberType(internalValue);
    }

    /**
     * Adds the values {@code a} and {@code b} together, adding any subsequent values to the {@code sum}.
     *
     * @param a A {@code value}.
     * @param b Another {@code value}.
     * @param c A list of {@code values}. <strong>(Optional)</strong>
     * @return A sum of value {@code a} and {@code b}, and if supplied any following {@code values}.
     * @see #sum(NumberType)
     */
    @Contract("_, _, _ -> new")
    public static @NotNull NumberType sum(@NotNull NumberType a, @NotNull NumberType b, NumberType @NotNull ... c) {
        double internalValue = a.internalValue + b.internalValue;
        for (NumberType d : c) internalValue += d.internalValue;
        return new NumberType(internalValue);
    }


    /**
     * Subtracts the given {@code value} from this {@code object}, essentially acting as the operator {@code -}.
     *
     * @param other The {@code value} to subtract.
     * @return The difference of {@code this object} and the {@code other object}.
     * @see #sub(NumberType, NumberType, NumberType...)
     */
    @Contract("_ -> new")
    public @NotNull NumberType sub(@NotNull NumberType other) {
        double internalValue = this.internalValue - other.internalValue;
        return new NumberType(internalValue);
    }

    @Contract("_, _, _ -> new")
    public static @NotNull NumberType sub(@NotNull NumberType a, @NotNull NumberType b, NumberType @NotNull ... c) {
        double internalValue = a.internalValue - b.internalValue;
        for (NumberType d : c) internalValue -= d.internalValue;
        return new NumberType(internalValue);
    }

    @Contract("_ -> new")
    public @NotNull NumberType mult(@NotNull NumberType other) {
        double internalValue = this.internalValue * other.internalValue;
        return new NumberType(internalValue);
    }

    @Contract("_ -> new")
    public @NotNull NumberType div(@NotNull NumberType other) {
        double internalValue = this.internalValue / other.internalValue;
        return new NumberType(internalValue);
    }

    public @NotNull @Unmodifiable NumberType floorDiv(@NotNull NumberType other) {
        double internalValue = this.internalValue / other.internalValue;
        return new NumberType(internalValue).floor();
    }

    @Contract(pure = true)
    public @NotNull @Unmodifiable NumberType ceilDiv(@NotNull NumberType other) {
        double internalValue = this.internalValue / other.internalValue;
        return new NumberType(internalValue).ceil();
    }

    @Contract("_ -> new")
    public @NotNull NumberType pow(@NotNull NumberType exponent) {
        double internalValue = 1;
        for (int i = 0; i < exponent.internalValue; i++) internalValue *= this.internalValue;
        return new NumberType(internalValue);
    }

    @Contract(pure = true)
    public @NotNull @Unmodifiable NumberType sqrt() {
        return new NumberType(java.lang.Math.sqrt(internalValue));
    }

    @Contract("_ -> new")
    public @NotNull NumberType fact(@NotNull NumberType exponent) {
        double internalValue = 1;
        for (double d = exponent.internalValue; d >= 1; d--) internalValue *= d;
        return new NumberType(internalValue);
    }

    // --------------------------------------------------------- Formatting

    public @NotNull String toHexadecimalString(int length) {
        String hex = toHexString(Math.longValue(this));
        if (length <= hex.length()) return hex;
        StringType t = new StringType(hex);
        while (t.isShorterThan(length)) t.push('0');
        return t.getValue();
    }

    public @NotNull String toHexadecimalString() {
        return toHexString(Math.longValue(this));
    }

    // --------------------------------------------------------- Tools

    @Contract("_ -> new")
    private static @NotNull NumberType createWithBypass(double d) {
        return new NumberType(d, true);
    }

    // --------------------------------------------------------- Overrides

    @Contract(pure = true)
    @Override
    public @Nullable NumberType copy() {
        return null;
    }

    @Contract(value = " -> new", pure = true)
    @Override
    public char @NotNull [] toBuffer() {
        return new char[0];
    }

    /**
     * Returns the {@code String} representation of this object's numeric value by calling {@link Double#toString(double)}.
     *
     * @return The {@code String} representation of this object's numeric value.
     */
    @Override
    public String valueToString() {
        return Double.toString(internalValue);
    }
}
