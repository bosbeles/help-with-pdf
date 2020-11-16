package com;

import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;
import java.awt.*;

public class Main {


    public static void main(String[] args) {

        EventQueue.invokeLater(Main::test);

    }

    private static void test() {
        FlatDarkLaf.install();
        JFrame frame = new JFrame("Help Test App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        MilsoftHelp milsoftHelp = new MilsoftHelp();


        frame.getContentPane().add(milsoftHelp.getHelpChooser());
        //frame.getContentPane().add(panel);
        frame.setSize(800, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }


}
