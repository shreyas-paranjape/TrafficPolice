package com.cybercad.challan.util;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by shreyas on 19/6/15.
 */
public class CollectionUtil {

    public static String toString(Collection<?> coll){
        return Arrays.toString(coll.toArray());
    }
}
