package business.check;

import business.model.MessageBean;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractMessageChecker {

    /**
     * ��������Ƿ����Ҫ��
     * ���ز�����Ҫ�������
     * @param beans Ҫ��������
     * @return ������Ҫ�������
     */
    public List<MessageBean> check(List<MessageBean> beans){
        List<MessageBean> b1 = new ArrayList<>(beans);
        List<MessageBean> ret = new ArrayList<>();
        b1.forEach(bean->{
            preCheck(bean);
            boolean isValid = true;
            if(!isDataValid(bean)){
                ret.add(bean);
                isValid = false;
            }

        });
        return ret;
    }

    /**
     * ���ﶨ���ⵥ��bean������
     * ͨ����֤���򷵻�true
     * @param bean Ҫ����bean
     * @return �Ƿ�ͨ������
     */
    protected abstract boolean isDataValid(MessageBean bean);

    /**
     * ���ǰ��ϴ����
     * @param beans
     */
    protected void filter(List<MessageBean> beans){

    }

    /**
     * bean�����ǰ����
     * @param bean Ҫ����bean
     */
    protected void preCheck(MessageBean bean){

    }

    /**
     * ��ⵥ��bean֮�����
     * @param bean ������bean
     * @param isValid ��bean�Ƿ�ͨ�����
     */
    protected void postCheck(MessageBean bean, boolean isValid){

    }

}