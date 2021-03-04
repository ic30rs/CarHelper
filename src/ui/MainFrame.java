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

        JMenuBar menuBar = new JMenuBar();// �����˵�������
        setJMenuBar(menuBar);// ���˵���������ӵ�����Ĳ˵�����

        JMenu menu = new JMenu("�ļ�");// �����˵�����
        menuBar.add(menu);// ���˵�������ӵ��˵���������

        JMenuItem menuItem = new JMenuItem("��");// �����˵������
        menuItem.addActionListener(this::clickFile);// Ϊ�˵�������¼�������
        menu.add(menuItem);// ���˵��������ӵ��˵�������

        JMenu menu1 = new JMenu("���ݼ��");// �����˵�����
        menuBar.add(menu1);// ���˵�������ӵ��˵���������
        menuItem = new JMenuItem("�ֶ�������");// �����˵������
        menuItem.addActionListener(this::clickGeneralCheck);// Ϊ�˵�������¼�������
        menu1.add(menuItem);// ���˵��������ӵ��˵�������
        menuItem = new JMenuItem("�ֶ�ʱЧ�Լ��");// �����˵������
        menuItem.addActionListener(this::clickTimeCheck);// Ϊ�˵�������¼�������
        menu1.add(menuItem);// ���˵��������ӵ��˵�������

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
        jfc.showDialog(new JLabel(), "ѡ��");
        File file=jfc.getSelectedFile();
        if(file != null){
            List<RawBean> hexes = HexDataResolver.processRawLogFile(file);
            setData(MessageResolver.resolve(hexes));
        }
    }

    private void clickGeneralCheck(ActionEvent e) {
        if(mList == null){
            JOptionPane.showConfirmDialog(this, "���ȶ�ȡ����");
            return;
        }
        new PopGeneralCheckFrame(mList);
    }

    private void clickTimeCheck(ActionEvent e) {
        if(mList == null){
            JOptionPane.showConfirmDialog(this, "���ȶ�ȡ����");
            return;
        }
        new PopTimeCheckFrame(mList);
    }

}
