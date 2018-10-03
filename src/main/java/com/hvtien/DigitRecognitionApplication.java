package com.hvtien;

import com.hvtien.digitrecognition.gui.PictureClassification;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.*;

@SpringBootApplication
public class DigitRecognitionApplication {

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(
                UIManager.getSystemLookAndFeelClassName()); }
        catch (Exception e) { e.printStackTrace(); }
        new PictureClassification();
    }
}
