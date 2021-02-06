package ui.list;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

import business.model.MessageBean;
import business.model.RawBean;

public class TableModel extends AbstractTableModel {

    private static List<String> columns = new ArrayList<>();

    static {
        //���о��������
        columns.add("��Ϣʱ��");
        columns.add("��Ϣ�ϴ�ʱ��");
        columns.add("Ψһʶ����");
        columns.add("�����ʶ");
        columns.add("Ӧ���־");
        columns.add("����״̬");
    }

    private List<MessageBean> list = new ArrayList<>();

    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public int getColumnCount() {
        return columns.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        MessageBean bean = list.get(rowIndex);
        if(columns.get(columnIndex).equals("��Ϣʱ��")) return bean.getMsgDateStr();
        if(columns.get(columnIndex).equals("��Ϣ�ϴ�ʱ��")) return bean.getGbDateStr();
        if(columns.get(columnIndex).equals("Ψһʶ����")) return bean.getVin();
        if(columns.get(columnIndex).equals("�����ʶ")) {
            int code = bean.getCommandCode();
            if(code == 0x01) return "��������";
            if(code == 0x02) return "ʵʱ��Ϣ�ϱ�";
            if(code == 0x03) return "������Ϣ�ϱ�";
            if(code == 0x04) return "�����ǳ�";
        }
        if(columns.get(columnIndex).equals("Ӧ���־")) {
            int code = bean.getResponseCode();
            if(code == 0x01) return "�ɹ�";
            if(code == 0x02) return "����";
            if(code == 0x03) return "VIN�ظ�";
            if(code == 0xFE) return "����";
        }
        if(columns.get(columnIndex).equals("����״̬")) {
            //���ڿ���Ϊ�յ����ͣ�һ��Ҫ���ж��Ƿ�Ϊ��
            if(bean.getTotalCarData() == null) return "";
            int code = bean.getTotalCarData().getCarStatus();
            if(code == 0x01) return "��������״̬";
            if(code == 0x02) return "Ϩ��";
            if(code == 0x03) return "����״̬";
            if(code == 0xFE) return "�쳣";
            if(code == 0xFF) return "��Ч";
        }

        return  "";
    }

    @Override
    public String getColumnName(int column) {
        return columns.get(column);
    }

    public void setList(List<MessageBean> list) {
        this.list = list;
        fireTableDataChanged();
    }
}
