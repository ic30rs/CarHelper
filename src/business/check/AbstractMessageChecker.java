package business.check;

import business.model.MessageBean;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractMessageChecker {

    /**
     * 检测数据是否符合要求
     * 返回不符合要求的数据
     * @param beans 要检测的数据
     * @return 不符合要求的数据
     */
    public List<MessageBean> check(List<MessageBean> beans){
        List<MessageBean> b1 = new ArrayList<>(beans);
        List<MessageBean> ret = new ArrayList<>();
        preCheckAll(b1);
        b1.forEach(bean->{
            preCheck(bean);
            boolean isValid = true;
            if(!isDataValid(bean)){
                ret.add(bean);
                isValid = false;
            }
            postCheck(bean, isValid);
        });
        postCheckAll(b1, ret);
        return ret;
    }

    /**
     * 这里定义检测单个bean的条件
     * 通过验证，则返回true
     * @param bean 要检测的bean
     * @return 是否通过检验
     */
    protected abstract boolean isDataValid(MessageBean bean);

    /**
     * 检测前清洗数据
     * @param beans
     */
    protected void filter(List<MessageBean> beans){

    }

    /**
     * bean被检测前调用
     * @param bean 要检测的bean
     */
    protected void preCheck(MessageBean bean){

    }

    /**
     * 检测单个bean之后调用
     * @param bean 被检测的bean
     * @param isValid 该bean是否通过检测
     */
    protected void postCheck(MessageBean bean, boolean isValid){

    }

    /**
     * 检测所有bean之前调用
     * @param beans
     */
    protected void preCheckAll(List<MessageBean> beans){

    }

    /**
     * 检测所有bean之后调用
     * @param beans 所有被检查的bean
     * @param probBeans 出问题的bean
     */
    protected void postCheckAll(List<MessageBean> beans, List<MessageBean> probBeans){

    }

    /**
     * 获取最终结果，字符串形式
     * @return 检查结果
     */
    public abstract String getFinalResult();

  /**
   * 获取最终结果，任意类型
   * @return 检查结果
   */
  public abstract Object getFinalResultObj();

}
