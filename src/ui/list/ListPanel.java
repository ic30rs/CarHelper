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

        //����������
        table.getColumn("��Ϣʱ��").setMinWidth(150);
        table.getColumn("��Ϣ�ϴ�ʱ��").setMinWidth(150);
        table.getColumn("Ψһʶ����").setMinWidth(150);

    }

    public void setData(List<MessageBean> list){
        model.setList(list);
    }



}
