package com.hvtien.digitrecognition;

import org.springframework.boot.SpringApplication;
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
