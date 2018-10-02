package com.hvtien.digitrecognition;

public class BinaryData implements Cloneable {

    protected boolean grid[][];

    BinaryData(int width, int height) {
        grid = new boolean[width][height];
    }

    public void setData(int x, int y, boolean v) {
        grid[x][y] = v;
    }

    public boolean getData(int x, int y) {
        return grid[x][y];
    }

    public void clear() {
        for (int x = 0; x < grid.length; x++)
            for (int y = 0; y < grid[0].length; y++)
                grid[x][y] = false;
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

