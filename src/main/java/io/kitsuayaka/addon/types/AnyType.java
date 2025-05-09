package io.kitsuayaka.addon.types;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Objects of the class {@code AnyType} carry a value of a not defined {@code Any} type,
 * allowing for wild casting, using {@link #castTo(Class)} and allowing for casting from other {@code BaseTypes}
 * to this class using {@link #castToAnyType(BaseType)}
 * <hr>
 *
 * @version <code>1.0.0</code>
 * @author Ayaka (<a href="https://github.com/shy-fox">GitHub</a>)
 */
public final class AnyType extends BaseType<Object> implements Serializable {
    @Serial
    private static final long serialVersionUID = 2L;

    private Class<?> anyClass;

    /**
     * The default constructor for this object, creating a new instance carrying a {@code null} value.
     *
     * @see #AnyType(Object)
     * @since <code>1.0.0</code>
     */
    public AnyType() {
        super(null);
        anyClass = Object.class;
    }

    /**
     * The constructor for a new object, letting you cast an {@code Object} to this object's value, allowing for
     * dynamic casting.
     *
     * @param value the value of this object.
     *
     * @see #AnyType()
     * @since <code>1.0.0</code>
     */
    public AnyType(Object value) {
        super(value);
        anyClass = value.getClass();
    }

    // --------------------------------------------------------- Public casting

    /**
     * Allows for dynamic casting of this object, to the passed in argument. Will throw an error if something goes wrong.
     * @param target the target {@code class} to cast this object to.
     * @return This object cast to the passed in {@code class}.
     * @param <T> The target type.
     *
     * @see #castTo()
     * @see #castToAnyType(BaseType)
     * @since <code>1.0.2</code>
     */
    @SuppressWarnings("unchecked")
    public <T> @NotNull T castTo(Class<T> target) {
        return (T) castTo(this, target);
    }

    /**
     * Casts this object to the type of the value this object carries.
     * @return A new object of the type of the value this object carries.
     *
     * @see #castTo(Class)
     * @see #castToAnyType(BaseType)
     * @since <code>1.0.3</code>
     */
    public @NotNull Object castTo() {
        return castTo(anyClass);
    }

    /**
     * Cast the passed in type to an {@link AnyType} using the default constructor, and then setting a dynamic class to
     * the type of the passed in object.
     * @param baseType the type to convert to {@code AnyType}
     * @return An {@code AnyType} derived from the passed in argument.
     * @since <code>1.0.2</code>
     */
    public static AnyType castToAnyType(BaseType<?> baseType) {
        if (baseType instanceof AnyType a) return a;

        AnyType a = new AnyType(baseType.value);
        a.anyClass = baseType.value.getClass();
        return a;
    }

    // --------------------------------------------------------- Overrides

    /**
     * A copy of this object, making sure it is the exact same
     * @return a copy of this object
     *
     * @see #clone()
     * @since <code>1.0.0</code>
     */
    @Contract(" -> new")
    @Override
    public @NotNull AnyType copy() {
        return new AnyType(value);
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
    @Override
    public @NotNull AnyType clone() throws CloneNotSupportedException {
        AnyType a = (AnyType) super.clone();
        a.tClass = tClass;
        a.anyClass = anyClass;
        a.value = value;
        return a;
    }

    /**
     * Returns a buffer ({@code char[]}) representation of this object's value,
     * can be:
     * <blockquote>
     * <pre>
     * valueToString().toCharArray()
     * </pre>
     * </blockquote>
     * @return a buffer ({@code char[]}) representation of this object's value
     * @since <code>1.0.0</code>
     */
    @Contract(pure = true)
    @Override
    public char @NotNull [] toBuffer() {
        if (anyClass == String.class) return ((String) value).toCharArray();
        return String.valueOf(value).toCharArray();
    }

    /**
     * Checks if both the class of this object's value and the value are equal to the given object's values,
     * the given object has to be a subclass of {@code BaseType} for this to work, thus will return {@code false} if
     * such an object is not provided.
     *
     * @param o the object to compare against.
     * @return if both this object's value and the class of the value are equal to the value and class of the value of the
     * passed in object.
     * @since <code>1.0.0</code>
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof AnyType a) {
            if (a.anyClass != anyClass) return false;
        }
        return super.equals(o);
    }

    // --------------------------------------------------------- Serial stuff

    @Serial
    private void writeObject(@NotNull ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeObject(value);
        objectOutputStream.writeObject(tClass);
        objectOutputStream.writeObject(anyClass);
    }

    @SuppressWarnings("unchecked")
    @Serial
    private void readObject(@NotNull ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();

        value = objectInputStream.readObject();
        tClass = (Class<Object>) objectInputStream.readObject();
        anyClass = (Class<?>) objectInputStream.readObject();
    }

    // --------------------------------------------------------- Casting from Any

    private static @NotNull Object castTo(BaseType<?> baseType, Class<?> target) throws ClassCastException {
        if (baseType instanceof AnyType anyType) {
            Constructor<?> constructor;
            int cType;

            try {
                constructor = target.getConstructor(Object.class);
                cType = 1;
            } catch (NoSuchMethodException e) {
                try {
                    constructor = target.getConstructor();
                    cType = 0;
                } catch (NoSuchMethodException e1) {
                    throw new IllegalCallerException(String.format("Class %s has no default constructor using either no" +
                            "parameter or Object.", target.getSimpleName()));
                }
            }

            try {
                return cType == 1 ? constructor.newInstance(anyType.value) : constructor.newInstance();
            } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
                throw new InternalError(String.format("An error occurred, tried to either access a private constructor" +
                                "using Object as parameter, for class %s.",
                        target));
            } catch (IllegalArgumentException e) {
                throw new ClassCastException(String.format("Cannot cast value of %s to %s, as value of this class is" +
                        "not used as constructor argument for class %s.",
                        baseType.getClass().getSimpleName(),
                        target.getSimpleName(),
                        target.getSimpleName()));
            }
        }
            Constructor<?> constructor;

            try {
                constructor = target.getConstructor(baseType.tClass);
            } catch (NoSuchMethodException e) {
                throw new IllegalCallerException(String.format("Class %s has no constructor with %s as parameter.",
                        target, baseType.tClass));
            }

            try {
                return constructor.newInstance(baseType.value);
            } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
                throw new InternalError(String.format("Cannot cast value of %s to %s. Constructor error.",
                        baseType.getClass(), target));
            } catch (IllegalArgumentException e) {
                throw new ClassCastException(String.format("Cannot cast value of %s to %s, as %s does not take %s as" +
                        "parameter for constructor.", baseType.value.getClass(), target,
                        target, baseType.getClassOfValue()));

        }

    }
}