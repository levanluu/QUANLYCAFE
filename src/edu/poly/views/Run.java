package edu.poly.views;

import javax.swing.UIManager;

public class Run {
    public static void main(String[] args) {
        LoginJFrame login = new LoginJFrame();
        login.setVisible(true);
        //import JTattoo
        try {
            // select Look and Feel
            UIManager.setLookAndFeel("com.jtattoo.plaf.fast.FastLookAndFeel");
            //start application
            new MainJFrame();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
