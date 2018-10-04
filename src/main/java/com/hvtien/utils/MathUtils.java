package com.hvtien.utils;

public class MathUtils {

    /**
     * convert to sigmoid value
     *
     * @param arg
     * @return
     */
    public static double sigmoidValue(Double arg) {
        return (1 / (1 + Math.exp(-arg)));
    }

}
