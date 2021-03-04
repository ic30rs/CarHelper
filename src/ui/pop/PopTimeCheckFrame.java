package ui.pop;

import business.check.AbstractMessageChecker;
import business.check.GeneralMessageChecker;
import business.check.GeneralMessageChecker.GeneralResult;
import business.check.TimeMessageChecker;
import business.model.MessageBean;
import ui.list.checklist.GeneralCheckListPanel;
import ui.list.checklist.TimeCheckListPanel;

import java.awt.*;
import java.util.List;

public class PopTimeCheckFrame extends PopResultFrame {

  public PopTimeCheckFrame(List<MessageBean> beans) {
    super(beans);

    setTitle("×Ö¶ÎÊ±Ð§ÐÔ¼ì²â");

    getContentPane().removeAll();
    getContentPane().setLayout(new BorderLayout());
    getContentPane().add(text, BorderLayout.SOUTH);

    TimeCheckListPanel listPanel = new TimeCheckListPanel();
    getContentPane().add(listPanel, BorderLayout.CENTER);

    listPanel.setData((TimeMessageChecker.TimeResult) checker.getFinalResultObj());

  }

  @Override
  protected AbstractMessageChecker getChecker() {
    return new TimeMessageChecker();
  }

}
