package ui.list.checklist;

import javax.swing.table.AbstractTableModel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TimeCheckStringTableModel extends AbstractTableModel {


  private List<String> times;


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
      return (times.get(rowIndex));
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

  private String name = "";

  public void setResult(List<String> times, String name) {
    this.times = times;
    this.name = name;
    fireTableStructureChanged();
  }
}
