package com.hvtien.digitrecognition.gui;

public class BinaryData implements Cloneable {

    protected int grid[][];

    BinaryData(int width, int height) {
        grid = new int[width][height];
    }

    public void setData(int x, int y, int v) {
        grid[x][y] = v;
    }

    public int getData(int x, int y) {
        return grid[x][y];
    }

    public void clear() {
        for (int x = 0; x < grid.length; x++)
            for (int y = 0; y < grid[0].length; y++)
                grid[x][y] = 0;
    }

    public int getHeight() {
        return grid[0].length;
    }

    public int getWidth() {
        return grid.length;
    }

    @Override
    public Object clone() {
        BinaryData obj = new BinaryData(getWidth(), getHeight());
        for (int y = 0; y < getHeight(); y++)
            for (int x = 0; x < getWidth(); x++)
                obj.setData(x, y, getData(x, y));
        return obj;
    }
}

