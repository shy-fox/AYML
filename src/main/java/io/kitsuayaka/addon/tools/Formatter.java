package io.kitsuayaka.addon.tools;

import io.kitsuayaka.addon.types.NumberType;
import io.kitsuayaka.addon.types.StringType;

import org.jetbrains.annotations.NotNull;

public interface Formatter {
    static @NotNull String numberFormat(double d, int rightFormat, int leftFormat) {
        boolean neg = d < 0;
        String _n = new NumberType(d).valueToString();
        String n = neg ? _n.substring(1) : _n;

        int di = n.indexOf('.');

        String ip = di != -1 ? n.substring(0, di) : n;
        String dp = di != -1 ? n.substring(di + 1) : "";

        StringBuilder sb = new StringBuilder(neg ? "-" : "");

        StringType t = new StringType(ip);
        if (leftFormat > 0) {
            while (t.isShorterThan(leftFormat)) t.push('0');
        }
        sb.append(ip);
        t = new StringType(dp);
        if (rightFormat > 0) {
            while (t.isShorterThan(rightFormat)) t.append('0');
        }
        sb.append(t);

        return sb.toString();
    }
}
