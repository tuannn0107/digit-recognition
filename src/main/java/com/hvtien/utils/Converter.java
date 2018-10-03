package com.hvtien.utils;

import java.lang.reflect.Array;
import java.util.ArrayList;

public final class Converter {
    public static ArrayList<Integer> convertToSingleDemension(int[][] inputs) {
        ArrayList<Integer> output = new ArrayList<>();
        for(int i = 0; i < inputs.length; i++) {
            for (int j = 0; j < inputs[i].length; j++) {
                output.add(inputs[i][j]);
            }
        }
        return output;
    }
}
