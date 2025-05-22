package io.kitsuayaka.core.annotations;

import org.intellij.lang.annotations.Language;

import java.lang.annotation.*;


public final class StatusMarkers {
    /**
     * The annotation {@code @Addon} is used to mark a class, method, field or constructor as being a portion which may get
     * removed or moved to a different library in the future. Does not mean it is {@link Experimental} or {@link Unstable}.
     */
    @Documented
    @Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Addon {
    }

    /**
     * The annotation {@code Experimental} is used to mark a class, method, field or constructor as still being experimental,
     * this means:
     * <ul style='list-style-type: none'>
     * <li>a) The annotated member is automatically considered as am {@link Addon} and {@link Unstable}</li>
     * <li>b) The annotated member could be removed, changed or moved somewhere else.</li>
     * <li>c) The annotated member's signature may change.</li>
     * <li>d) The annotated member's {@code Contract} may change.</li>
     * </ul>
     *
     * @see Unstable
     */
    @Documented
    @Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Experimental {
        /**
         * The member {@code since} is used to mark that the annotated member is unstable since version {@code X}, used
         * to indicate that this member used to be stable, or work differently since said version.
         */
        String since() default "";

        /**
         * This member is used to indicate that the signature of the method, meaning its body was changed before it
         * was annotated as {@code Experimental}, can be used in combination with {@link #since()} to denote that
         * the signature was changed in this version.
         */
        boolean signatureChanged() default false;

        /**
         * This member is the default member to use to denote that the annotated member is {@code Experimental} since it
         * was added or created, not that it signature change made it unstable or experimental.
         */
        boolean sinceAdded() default true;
    }

    /**
     * The annotation {@code Unstable} is used to mark that the annotated member may not work as expected or intended,
     * this does not mean it is still {@link Experimental}. Using it, will show that the member may possibly break pieces
     * here and there.
     *
     * @see Experimental
     */
    @Documented
    @Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Unstable {
    }

    /**
     * The annotation {@code MarkedForRemoval} is used to mark that the annotated member is {@link Deprecated} and
     * marked for removal in version {@code X}. It does substitute {@link Deprecated#forRemoval()} and allows for denoting
     * <em>since</em> when and <em>when</em> it will be removed.
     *
     * @see Deprecated
     */
    @Documented
    @Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR})
    @Retention(RetentionPolicy.SOURCE)
    public @interface MarkedForRemoval {
        /**
         * This member denotes the version in which the annotated member was first annotated as {@link Deprecated} and
         * simultaneously marked for removal.
         */
        String since() default "";

        /**
         * This member denotes the version in which the annotated member will be removed, or when its support will end.
         */
        String when() default "";

        /**
         * This member denotes the substitute method or method that may inherit the functionality of the annotated member,
         * default value is {@code "none"}.
         */
        @SubstituteMethod String substitute() default "none";
    }

    @Language("jvm-method-name")
    private @interface SubstituteMethod {}
}
