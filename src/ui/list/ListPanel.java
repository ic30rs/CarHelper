package ui.list;

import business.model.MessageBean;
import business.model.RawBean;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ListPanel extends JPanel {

    JTable table;
    TableModel model;

    public ListPanel(){
        setLayout(new BorderLayout());

        table = new JTable();
        model = new TableModel();
        table.setModel(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setViewportView(table);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        table.setRowHeight(80);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        add(scrollPane, BorderLayout.CENTER);

        //这里调整宽度
        table.getColumn("消息时间").setMinWidth(150);
        table.getColumn("消息上传时间").setMinWidth(150);
        table.getColumn("唯一识别码").setMinWidth(150);

    }

    public void setData(List<MessageBean> list){
        model.setList(list);
    }



}
