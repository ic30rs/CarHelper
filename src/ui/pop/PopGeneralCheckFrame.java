package ui.pop;

import business.check.AbstractMessageChecker;
import business.check.GeneralMessageChecker;
import business.check.GeneralMessageChecker.GeneralResult;
import business.model.MessageBean;
import java.awt.BorderLayout;
import java.util.List;
import ui.list.checklist.GeneralCheckListPanel;

public class PopGeneralCheckFrame extends PopResultFrame {

  public PopGeneralCheckFrame(List<MessageBean> beans) {
    super(beans);

    setTitle("�ֶ�������");

    getContentPane().removeAll();
    getContentPane().setLayout(new BorderLayout());
    getContentPane().add(text, BorderLayout.SOUTH);

    GeneralCheckListPanel listPanel = new GeneralCheckListPanel();
    getContentPane().add(listPanel, BorderLayout.CENTER);

    listPanel.setData((GeneralResult) checker.getFinalResultObj());

  }

  @Override
  protected AbstractMessageChecker getChecker() {
    return new GeneralMessageChecker();
  }

}
