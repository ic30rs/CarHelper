package ui.list.checklist;

import business.check.GeneralMessageChecker;
import business.check.GeneralMessageChecker.GeneralResult;
import business.model.MessageBean;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class GeneralCheckTableModel extends AbstractTableModel {


  private GeneralResult result;

  @Override
  public int getRowCount() {
    return result == null ? 0 : result.getKongZhiList().size();
  }

  @Override
  public int getColumnCount() {
    return 3;
  }

  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {
    if(columnIndex == 0){
      return GeneralMessageChecker.GENERAL_CHECK_ITEMS[rowIndex];
    }

    if(columnIndex == 1){
      return String.format("%.2f%%",100*result.getKongZhiList().get(rowIndex));
    }

    if(columnIndex == 2){
      return String.format("%.2f%%",100*result.getLingZhiList().get(rowIndex));
    }

    return "";
  }

  @Override
  public String getColumnName(int column) {
    if (column == 0) {
      return "数据项";
    }
    if (column == 1) {
      return "空值率";
    }
    if (column == 2) {
      return "零值率";
    }
    return "";
  }

  public void setResult(GeneralResult result) {
    this.result = result;
    fireTableDataChanged();
  }
}
