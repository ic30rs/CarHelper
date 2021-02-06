package ui.pop;

import business.check.AbstractMessageChecker;
import business.check.GeneralMessageChecker;
import business.model.MessageBean;

import java.util.List;

public class PopGeneralCheckFrame extends PopResultFrame{

    public PopGeneralCheckFrame(List<MessageBean> beans) {
        super(beans);
    }

    @Override
    protected AbstractMessageChecker getChecker() {
        return new GeneralMessageChecker();
    }

}
