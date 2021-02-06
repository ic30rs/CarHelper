package ui.list;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

import business.model.MessageBean;
import business.model.RawBean;

public class TableModel extends AbstractTableModel {

    private static List<String> columns = new ArrayList<>();

    static {
        //加列就在这里加
        columns.add("消息时间");
        columns.add("消息上传时间");
        columns.add("唯一识别码");
        columns.add("命令标识");
        columns.add("应答标志");
        columns.add("车辆状态");
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
        if(columns.get(columnIndex).equals("消息时间")) return bean.getMsgDateStr();
        if(columns.get(columnIndex).equals("消息上传时间")) return bean.getGbDateStr();
        if(columns.get(columnIndex).equals("唯一识别码")) return bean.getVin();
        if(columns.get(columnIndex).equals("命令标识")) {
            int code = bean.getCommandCode();
            if(code == 0x01) return "车辆登入";
            if(code == 0x02) return "实时信息上报";
            if(code == 0x03) return "补发信息上报";
            if(code == 0x04) return "车辆登出";
        }
        if(columns.get(columnIndex).equals("应答标志")) {
            int code = bean.getResponseCode();
            if(code == 0x01) return "成功";
            if(code == 0x02) return "错误";
            if(code == 0x03) return "VIN重复";
            if(code == 0xFE) return "命令";
        }
        if(columns.get(columnIndex).equals("车辆状态")) {
            //对于可能为空的类型，一定要先判断是否为空
            if(bean.getTotalCarData() == null) return "";
            int code = bean.getTotalCarData().getCarStatus();
            if(code == 0x01) return "车辆启动状态";
            if(code == 0x02) return "熄火";
            if(code == 0x03) return "其他状态";
            if(code == 0xFE) return "异常";
            if(code == 0xFF) return "无效";
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
