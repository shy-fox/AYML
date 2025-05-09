package io.kitsuayaka.addon.functions;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public interface ArrayTools {
    static void reverse(Object @NotNull [] array, int start, int end) {
        Objects.checkFromIndexSize(start, end, array.length);
        while (start < end) {
            Object tmp = array[start];
            array[start] = array[end];
            array[end] = tmp;
            start++;
            end--;
        }
    }

    static void reverse(Object @NotNull [] array) {
        reverse(array, 0, array.length);
    }

    static void reverse(char[] array, int start, int end) {
        reverse(convert(array), start, end);
    }

    static void reverse(char[] array) {
        reverse(convert(array));
    }

    static Object @NotNull [] convert(char @NotNull [] array) {
        Object[] objects = new Object[array.length];
        for (int i = 0; i < array.length; i++) objects[i] = array[i];
        return objects;
    }
}
