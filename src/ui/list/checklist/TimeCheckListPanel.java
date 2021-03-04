package ui.list.checklist;

import business.check.GeneralMessageChecker.GeneralResult;
import business.check.TimeMessageChecker;

import javax.swing.*;
import java.awt.*;

public class TimeCheckListPanel extends JPanel {

    JTable table1;
    JTable table2;
    JTable table3;
    JTable table4;
    TimeCheckLongTableModel model1;
    TimeCheckLongTableModel model2;
    TimeCheckLongTableModel model3;
    TimeCheckStringTableModel model4;

    public TimeCheckListPanel(){
        setLayout(new GridLayout(0,2));

        table1 = new JTable();
        model1 = new TimeCheckLongTableModel();
        table1.setModel(model1);
        addTable(table1);

        table2 = new JTable();
        model2 = new TimeCheckLongTableModel();
        table2.setModel(model2);
        addTable(table2);

        table3 = new JTable();
        model3 = new TimeCheckLongTableModel();
        table3.setModel(model3);
        addTable(table3);

        table4 = new JTable();
        model4 = new TimeCheckStringTableModel();
        table4.setModel(model4);
        addTable(table4);


    }
    
    private void addTable(JTable table){
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setViewportView(table);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        table.setRowHeight(30);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        add(scrollPane);
    }

    public void setData(TimeMessageChecker.TimeResult result){
        model1.setResult(result.getCaiJiShangBaoList(), "采集时间间隔大于30s采集时间");
        model2.setResult(result.getJianGeList(), "时间间隔大于3s的采集时间及数据上报时间");
        model3.setResult(result.getChongFuList(), "重复数据的数据采集时间");
        model4.setResult(result.getYiChangList(), "异常的数据采集时间");
    }



}
