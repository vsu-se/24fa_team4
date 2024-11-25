package GUI;

import javax.swing.*;

public class test extends JFrame {

    public test () {
        setTitle("Bidsy");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 800);
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon("GUI\\bidsy_mascot.png").getImage());

        setVisible(true);
    }
    private JLabel labelTest;
    public static void main(String[] args) {

        new test();
    }
}
