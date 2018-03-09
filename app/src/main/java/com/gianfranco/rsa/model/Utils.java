package com.gianfranco.rsa.model;

final class Utils {
    // https://github.com/square/retrofit/blob/master/retrofit/src/main/java/retrofit2/Utils.java
    static <T> T checkNotNull(T object, String message) {
        if (object == null) {
            throw new NullPointerException(message);
        }
        return object;
    }
}
