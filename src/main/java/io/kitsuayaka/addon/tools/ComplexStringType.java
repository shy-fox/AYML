package io.kitsuayaka.addon.tools;

import io.kitsuayaka.addon.types.*;
import io.kitsuayaka.core.annotations.StatusMarkers;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * The {@code interface} {@code ComplexStringType} is used for formatting and other methods using other derivatives of {@code BaseType}.
 * Core of this {@code interface} is the function which behaves similar to {@link String#format(String, Object...)}, although using
 * different {@code prefixes} to denote a formatting. The different formatting options are:
 * <table style='width: 450px'>
 *     <thead>
 *         <tr>
 *             <td style='width: 70px; text-align: center'>Option</td>
 *             <td style='width: 100px; text-align: left'>Usage with</td>
 *             <td style='width: 280px; text-align: left'>Explanation</td>
 *
 *         </tr>
 *     </thead>
 *     <tbody>
 *         <tr>
 *             <td style='text-align: center'><code>$s</code></td>
 *             <td><code>Any field</code></td>
 *             <td>Will apply the method {@link java.util.Objects#toString(Object) Objects.toString(Object)} to the
 *             provided {@code Object}.</td>
 *         </tr>
 *         <tr>
 *             <td style='text-align: center'><code>$d.##</code></td>
 *             <td>{@link NumberType}, {@link Number}</td>
 *             <td>Will return a decimal representation of the provided {@code value}, with {@code ##} decimals.</td>
 *         </tr>
 *         <tr>
 *             <td style='text-align: center'><code>$h.##<sup>1</sup></code></td>
 *             <td><code>Any field</code></td>
 *             <td>Will return a hexadecimal representation of the provided {@code value}, with length {@code ##}</td>
 *         </tr>
 *         <tr>
 *             <td style='text-align: center'><code>$h.##<sup>2</sup></code></td>
 *             <td>{@link NumberType}</td>
 *             <td>Will return a hexadecimal representation using the method {@link NumberType#toHexadecimalString()}</td>
 *         </tr>
 *         <tr>
 *             <td style='text-align: center'><code>$c<sup>3</sup></code></td>
 *             <td>{@code Any field}</td>
 *             <td>Returns the simple name returned from {@code getClass()}</td>
 *           </tr>
 *           <tr>
 *               <td style='text-align: center'><code>$c<sup>4</sup></code></td>
 *               <td>{@code Any class}</td>
 *               <td>Returns the simple name returned from {@link Class#getSimpleName()}</td>
 *            </tr>
 *     </tbody>
 * </table>
 * <hr />
 * <blockquote>
 *     <p><sup>{@code 1}</sup> &mdash; See definition for {@link NumberType}.</p>
 *     <p><sup>{@code 2}</sup> &mdash; Alternate definition for {@link NumberType}</p>
 *     <p><sup>{@code 3}</sup> &mdash; This method will only use a {@code field} as parameter.</p>
 *     <p><sup>{@code 4}</sup> &mdash; This method will only use a {@code Class<?>} as a parameter.</p>
 * </blockquote>
 */
@StatusMarkers.Addon
@StatusMarkers.Experimental
@ApiStatus.Experimental
public final class ComplexStringType {
    private Object[] data;
    private StringType container;

    private ComplexStringType(Object[] data, StringType container) {
        this.data = data;
        this.container = container;
    }

    @Contract(value = "_, _ -> new", pure = true)
    public static @NotNull ComplexStringType newInstance(StringType container, Object... data) {
        return new ComplexStringType(data, container);
    }

    public StringType format() {
        int i;
        int j = 0;

        Range r;

        while ((i = container.indexOf('$')) != -1) {
            Object o = data[j];
            while ((r = container.find("$s")).isUndefined()) {
                replaceRange(container, r, o);
            }
        }
    }

    public static StringType format(StringType container, Object... data) {
        return newInstance(container, data).format();
    }

    // --------------------------------------------------------- Helper functions

    @Contract(mutates = "param1")
    private @NotNull StringType replaceRange(@NotNull StringType container, @NotNull Range r, Object o) {
        StringType start = container.substring(0, r.start());
        StringType end = container.substring(r.end());

        container = start;
        container.append(o);
        container.append(end);

        return container;
    }
}
