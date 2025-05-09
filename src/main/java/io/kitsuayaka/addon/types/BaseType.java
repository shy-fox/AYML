package io.kitsuayaka.addon.types;

import io.kitsuayaka.core.annotations.StatusMarkers;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;

import java.util.Objects;

// Version 1.0
// Edit: Apr.1.2025
@StatusMarkers.Addon
abstract class BaseType<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1;

    protected T value;
    protected Class<T> tClass;

    @SuppressWarnings("unchecked")
    protected BaseType(T value) {
        this.value = value;
        tClass = (Class<T>) ((value == null) ? Object.class : value.getClass());
    }

    protected BaseType() {
        this(null);
    }

    /**
     * Updates the value of this object to the new given value.
     *
     * @param value the new value to set.
     * @since <code>1.0.0</code>
     */
    public void set(T value) {
        this.value = value;
    }

    /**
     * Returns the value of this object, can then be checked against or stored.
     *
     * @return the value of this object.
     * @see #isValue(Object)
     * @see #isNull()
     * @since <code>1.0.0</code>
     */
    public T getValue() {
        return value;
    }

    /**
     * Compares the value of this object to the given value, and returns if they are the same.
     *
     * @param o the object to compare against, can be {@code null}.
     * @return if the given object matches the value of this object.
     * @see #isNull()
     * @since <code>1.0.0</code>
     */
    public boolean isValue(@Nullable Object o) {
        if (o == null) return isNull();
        return o.getClass() == tClass && Objects.equals(o, value);
    }

    /**
     * Checks if this object's value is equal to {@code null}.
     *
     * @return if this object's value is equal to {@code null}.
     * @see #isValue(Object)
     * @since <code>1.0.2</code>
     */
    public boolean isNull() {
        return value == null;
    }

    /**
     * Checks if the class of this object's value is equal to the given class.
     *
     * @param tClass the class to check against.
     * @return if the class of this object's value is equal to the given class.
     * @since <code>1.0.0</code>
     */
    public boolean isClassOfValue(Class<?> tClass) {
        return this.tClass == tClass;
    }

    /**
     * Returns the class of this object's value.
     *
     * @return the class of this object's value.
     * @since <code>1.0.0</code>
     */
    public Class<T> getClassOfValue() {
        return tClass;
    }

    /**
     * Returns the string representation of this object's value.
     *
     * @return the string representation of this object's value.
     * @since <code>1.0.0</code>
     */
    public String valueToString() {
        return value.toString();
    }

    // --------------------------------------------------------- Overrides

    /**
     * Returns a string representation of this object.
     *
     * @return a string representation of this object.
     * @since <code>1.0.0</code>
     */
    @Override
    public @NotNull String toString() {
        return String.format("%s { value: %s }", getClass().getSimpleName(), value);
    }

    /**
     * Returns the hash value of this object.
     *
     * @return the hash value of this object.
     * @since <code>1.0.0</code>
     */
    @Override
    public int hashCode() {
        return 31 * getClass().getSimpleName().length() << serialVersionUID;
    }

    /**
     * Creates and returns a copy of this object.
     *
     * @return a copy of this object, but not the same as this object.
     * @throws CloneNotSupportedException if the object's class does not support the {@code Cloneable} interface.
     *                                    Subclasses that override the {@code clone} method can also throw this
     *                                    exception to indicate that an instance cannot be cloned.
     * @see #copy()
     * @since <code>1.0.0</code>
     */
    @SuppressWarnings("unchecked")
    @Override
    public BaseType<T> clone() throws CloneNotSupportedException {
        return (BaseType<T>) super.clone();
    }

    /**
     * Checks if both the class of this object's value and the value are equal to the given object's values,
     * the given object has to be a subclass of {@code BaseType} for this to work, thus will return {@code false} if
     * such an object is not provided.
     *
     * @param other the object to compare against.
     * @return if both this object's value and the class of the value are equal to the value and class of the value of the
     * passed in object.
     * @since <code>1.0.0</code>
     */
    @Override
    public boolean equals(Object other) {
        if (other == null ||
                other.getClass().getSuperclass() != BaseType.class) return false;

        BaseType<?> baseType = (BaseType<?>) other;

        Class<?> otherTClass = baseType.tClass;
        Class<T> tClass = this.tClass;

        if (tClass != otherTClass) return false;

        return Objects.equals(value, baseType.value) &&
                hashCode() == baseType.hashCode();
    }

    // --------------------------------------------------------- Serial stuff

    @Serial
    private void writeObject(@NotNull ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeObject(value);
        objectOutputStream.writeObject(tClass);
    }

    @Serial
    @SuppressWarnings("unchecked")
    private void readObject(@NotNull ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();

        value = (T) objectInputStream.readObject();

        if (value == null) tClass = (Class<T>) Object.class;
        else tClass = (Class<T>) objectInputStream.readObject();
    }

    // --------------------------------------------------------- Abstract/Native methods

    /**
     * A copy of this object, making sure it is the exact same
     * @return a copy of this object
     *
     * @see #clone()
     * @since <code>1.0.0</code>
     */
    public abstract BaseType<T> copy();

    /**
     * Returns a buffer ({@code char[]}) representation of this object's value,
     * can be:
     * <blockquote>
     * <pre>valueToString().toCharArray()</pre>
     * </blockquote>
     * @return a buffer ({@code char[]}) representation of this object's value
     * @since <code>1.0.0</code>
     */
    public abstract char[] toBuffer();

    // --------------------------------------------------------- Helper methods

    protected static @NotNull String toHexString(long l) {
        if (l < 0) l *= -1L;
        int r;
        StringBuilder h = new StringBuilder();
        char[] hex = {
                '0', '1', '2', '3',
                '4', '5', '6', '7',
                '8', '9', 'A', 'B',
                'C', 'D', 'E', 'F'
        };

        while (l > 0) {
            r = (int) (l % 16);
            h.append(hex[r]);
            l /= 16;
        }

        return h.toString();
    }
}
