package ui.list.checklist;

import business.check.GeneralMessageChecker.GeneralResult;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class GeneralCheckListPanel extends JPanel {

    JTable table;
    GeneralCheckTableModel model;

    public GeneralCheckListPanel(){
        setLayout(new BorderLayout());

        table = new JTable();
        model = new GeneralCheckTableModel();
        table.setModel(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setViewportView(table);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        table.setRowHeight(30);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        add(scrollPane, BorderLayout.CENTER);


    }

    public void setData(GeneralResult result){
        model.setResult(result);
    }



}
