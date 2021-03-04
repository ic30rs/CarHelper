package ui;

import business.HexDataResolver;
import business.MessageResolver;
import business.model.MessageBean;
import business.model.RawBean;
import ui.list.showlist.ListPanel;
import ui.pop.PopGeneralCheckFrame;
import ui.pop.PopTimeCheckFrame;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.List;

public class MainFrame extends BaseFrame {

    ListPanel listPanel;

    List<MessageBean> mList;

    public MainFrame(){

        JMenuBar menuBar = new JMenuBar();// 创建菜单栏对象
        setJMenuBar(menuBar);// 将菜单栏对象添加到窗体的菜单栏中

        JMenu menu = new JMenu("文件");// 创建菜单对象
        menuBar.add(menu);// 将菜单对象添加到菜单栏对象中

        JMenuItem menuItem = new JMenuItem("打开");// 创建菜单项对象
        menuItem.addActionListener(this::clickFile);// 为菜单项添加事件监听器
        menu.add(menuItem);// 将菜单项对象添加到菜单对象中

        JMenu menu1 = new JMenu("数据监测");// 创建菜单对象
        menuBar.add(menu1);// 将菜单对象添加到菜单栏对象中
        menuItem = new JMenuItem("字段总体检测");// 创建菜单项对象
        menuItem.addActionListener(this::clickGeneralCheck);// 为菜单项添加事件监听器
        menu1.add(menuItem);// 将菜单项对象添加到菜单对象中
        menuItem = new JMenuItem("字段时效性检测");// 创建菜单项对象
        menuItem.addActionListener(this::clickTimeCheck);// 为菜单项添加事件监听器
        menu1.add(menuItem);// 将菜单项对象添加到菜单对象中

        getContentPane().setLayout(new BorderLayout());
        listPanel = new ListPanel();

        getContentPane().add(listPanel, BorderLayout.CENTER);

        setVisible(true);
    }


    public void setData(List<MessageBean> list){
        mList = list;
        listPanel.setData(list);
        validate();
        repaint();
    }

    private void clickFile(ActionEvent e){
        JFileChooser jfc=new JFileChooser(new File("./"));
        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY );
        jfc.showDialog(new JLabel(), "选择");
        File file=jfc.getSelectedFile();
        if(file != null){
            List<RawBean> hexes = HexDataResolver.processRawLogFile(file);
            setData(MessageResolver.resolve(hexes));
        }
    }

    private void clickGeneralCheck(ActionEvent e) {
        if(mList == null){
            JOptionPane.showConfirmDialog(this, "请先读取数据");
            return;
        }
        new PopGeneralCheckFrame(mList);
    }

    private void clickTimeCheck(ActionEvent e) {
        if(mList == null){
            JOptionPane.showConfirmDialog(this, "请先读取数据");
            return;
        }
        new PopTimeCheckFrame(mList);
    }

}
