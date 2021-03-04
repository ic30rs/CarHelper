package ui.list.checklist;

import business.check.GeneralMessageChecker;
import business.check.GeneralMessageChecker.GeneralResult;

import javax.swing.table.AbstractTableModel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TimeCheckLongTableModel extends AbstractTableModel {


  private List<Long> times;

  SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日hh时mm分ss秒");

  @Override
  public int getRowCount() {
    return times == null ? 0 : times.size();
  }

  @Override
  public int getColumnCount() {
    return 1;
  }

  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {
    if(columnIndex == 0){
      return format.format(new Date(times.get(rowIndex)));
    }
    return "";
  }

  @Override
  public String getColumnName(int column) {
    if (column == 0) {
      return name;
    }
    return "";
  }

  private String name = "11";

  public void setResult(List<Long> times, String name) {
    this.times = times;
    this.name = name;
    fireTableStructureChanged();
  }
}
