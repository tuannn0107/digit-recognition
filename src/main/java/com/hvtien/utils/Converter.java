package com.hvtien.utils;

import java.util.ArrayList;

public final class Converter {
    /**
     * convert array 2 dimension to single array
     *
     * @param inputs
     * @return
     */
    public static ArrayList<Integer> convertToSingleDimension(int[][] inputs) {
        ArrayList<Integer> output = new ArrayList<>();
        for(int i = 0; i < inputs.length; i++) {
            for (int j = 0; j < inputs[i].length; j++) {
                output.add(inputs[i][j]);
            }
        }
        return output;
    }
}
