package io.kitsuayaka.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


public final class StatusMarkers {
    /**
     * The annotation {@code @Addon} is used to mark a class, method, field or constructor as being a portion which may get
     * removed or moved to a different library in the future.
     */
    @Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Addon {}

    @Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Experimental {}

    @Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Unstable {}

    @Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR})
    @Retention(RetentionPolicy.SOURCE)
    public @interface MarkedForRemoval {}
}
