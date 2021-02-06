package ui;

import javax.swing.*;

public class BaseFrame extends JFrame {

    public BaseFrame(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(920, 660);
        MyUtils.centerComponentOnScreen(this);
    }




}
