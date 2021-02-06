package ui.pop;

import business.check.AbstractMessageChecker;
import business.model.MessageBean;
import ui.MyUtils;

import javax.swing.*;
import java.util.List;

public abstract class PopResultFrame extends JFrame {

    JLabel text = new JLabel();

    public void PopResultFrame(List<MessageBean> beans){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 400);
        MyUtils.centerComponentOnScreen(this);

        setVisible(true);
    }

    protected abstract AbstractMessageChecker getChecker();



}
