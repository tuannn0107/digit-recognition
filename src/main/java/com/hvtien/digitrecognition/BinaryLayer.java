package com.hvtien.digitrecognition;

import javax.swing.*;

import java.awt.*;

public class BinaryLayer extends JPanel {

    BinaryData data;

    BinaryLayer(int width, int height) {
        data = new BinaryData(width, height);
    }

    BinaryData getData() {
        return data;
    }

    void setData(BinaryData data) {
        this.data = data;
    }

    @Override
    public void paint(Graphics g) {
        if (data == null)
            return;

        int x, y;
        int vcell = getHeight() / data.getHeight();
        int hcell = getWidth() / data.getWidth();

        g.setColor(Color.white); //Background downSample
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.black); //downSampleIntoBounds
        for (y = 0; y < data.getHeight(); y++)
            g.drawLine(0, y * vcell, getWidth(), y * vcell);
        for (x = 0; x < data.getWidth(); x++)
            g.drawLine(x * hcell, 0, x * hcell, getHeight());

        for (y = 0; y < data.getHeight(); y++) {
            for (x = 0; x < data.getWidth(); x++) {
                if (data.getData(x, y))
                    g.fillRect(x * hcell, y * vcell, hcell, vcell);
            }
        }

        g.setColor(Color.black); //downSampleBounds
        g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);

    }
}
