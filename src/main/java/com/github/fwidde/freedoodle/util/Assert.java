package com.github.fwidde.freedoodle.util;

import java.util.Collection;
import java.util.List;

public class Assert {

    public static void notBlank(String string, String name){
        notNull(string, name);
        notEmpty(string, name);
        if(string.isBlank())
            throw new IllegalArgumentException(name + " is not allowed to be Blank.");
    }

    public static void notEmpty(String string, String name){
        notNull(string, name);
        if(string.isEmpty())
            throw new IllegalArgumentException(name + " is not allowed to be Empty.");
    }

    public static void notEmpty(Collection collection, String name){
        notNull(collection, name);
        if(collection.isEmpty())
            throw new IllegalArgumentException(name + " is not allowed to be Empty.");
    }

    public static void notNull(Object object, String name){
        if(object == null)
            throw new IllegalArgumentException(name + " is not allowed to be Null.");
    }
}
