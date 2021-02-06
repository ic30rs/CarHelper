package ui.pop;

import business.check.AbstractMessageChecker;
import business.model.MessageBean;
import ui.MyUtils;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public abstract class PopResultFrame extends JFrame {

    JLabel text = new JLabel();

    public PopResultFrame(List<MessageBean> beans){
        setSize(400, 400);
        MyUtils.centerComponentOnScreen(this);

        getContentPane().setLayout(new GridLayout(1,1));
        getContentPane().add(text);

        AbstractMessageChecker checker = getChecker();
        checker.check(beans);
        text.setText(checker.getFinalResult());

        setVisible(true);
    }

    protected abstract AbstractMessageChecker getChecker();



}
