package com.cybercad.challan.util;

import java.util.Arrays;
import java.util.Collection;

public class CollectionUtil {

    public static String toString(Collection<?> coll) {
        return Arrays.toString(coll.toArray());
    }
}
