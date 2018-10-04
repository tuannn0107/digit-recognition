package com.hvtien.digitrecognition.data;

import com.hvtien.utils.Constants;

import java.util.ArrayList;

public class GoodOutputs {

    private static GoodOutputs instance;

    private ArrayList<ArrayList<Double>> goodValues;

    public static GoodOutputs getInstance() {
        if (instance == null)
            instance = new GoodOutputs();

        return instance;
    }

    public GoodOutputs() {
        this.goodValues = new ArrayList<>();
        addGoodOutputs();
    }


    /**
     * create list good outputs
     */
    private void addGoodOutputs() {
        for (int i = 0; i < Constants.DIGIT_LIST.length; i++) {
            ArrayList<Double> list = new ArrayList<>();
            for (int j = 0; j < Constants.DIGIT_LIST.length; j++) {
                if (i == j) {
                    list.add(1.0);
                } else {
                    list.add(0.0);
                }
            }
            goodValues.add(list);
        }
    }


    /**
     * get list good output base on letter
     *
      * @param letter
     * @return
     */
    public ArrayList<Double> getGoodOutput(String letter) {
        for (int i = 0; i < Constants.DIGIT_LIST.length; i++) {
            if (Constants.DIGIT_LIST[i].equals(letter))
            {
                return goodValues.get(i);
            }
        }
        return null;
    }

}
