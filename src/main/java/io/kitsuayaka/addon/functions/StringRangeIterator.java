package io.kitsuayaka.addon.functions;

/**
 * This interface is used solely to iterate through a {@link io.kitsuayaka.addon.types.StringType StringType}, and specifically used
 * in the methods {@link io.kitsuayaka.addon.types.StringType#forEachChar(StringRangeIterator) StringType.forEachChar(StringRangeIterator)} and
 * {@link io.kitsuayaka.addon.types.StringType#forRangeRange(int, StringRangeIterator) StringType.forRangeRange(int, StringRangeIterator)}. In the prior package
 * <a href="https://github.com/shy-fox/Yaml-Reader"><em>SAML (ex Yaml-Reader)</em></a> it was called
 * <a href="https://github.com/shy-fox/Yaml-Reader/blob/February-2025-Preview/src/main/java/io/shiromi/saml/functions/StringIterator.java"><em>StringIterator</em></a>.
 * Changed in the rewrite to {@code StringRangeIterator}
 */
public interface StringRangeIterator {
    /**
     * This method is called on the specified target string, or possibly each character, if used in {@code forEachChar}.
     *
     * @param string The target string, can also be a single character.
     */
    void apply(String string);
}
