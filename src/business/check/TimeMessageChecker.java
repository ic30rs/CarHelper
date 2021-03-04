package business.check;

import business.model.MessageBean;

import java.util.*;

public class TimeMessageChecker extends AbstractMessageChecker {

  Set<Long> timeSet = new HashSet<>();

  List<Long> jianGeList = new ArrayList<>();
  List<Long> caiJiShangBaoList = new ArrayList<>();
  List<Long> chongFuList = new ArrayList<>();
  List<String> yiChangList = new ArrayList<>();

  @Override
  protected boolean isDataValid(MessageBean bean) {
    //时间异常
    if(!bean.isTimeValid()){
      yiChangList.add(String.format("%d年%2d月%2d日%2d时%2d分%2d秒", bean.getYear(), bean.getMonth(), bean.getDay(),
          bean.getHour(), bean.getMinute(), bean.getSecond()));
      return false;
    }
    //重复
    if(timeSet.contains(bean.getCollectTime().getTime())){
      chongFuList.add(bean.getCollectTime().getTime());
    }else{
      timeSet.add(bean.getCollectTime().getTime());
    }
    return false;
  }

  @Override
  protected void preCheckAll(List<MessageBean> beans) {
//    beans.sort(Comparator.comparing(MessageBean::getCollectTime));
  }

  @Override
  protected void postCheckAll(List<MessageBean> beans, List<MessageBean> probBeans) {
    Set<Long> s = new HashSet<>();
    chongFuList.removeIf(o ->{
      if(!s.contains(o)){
        s.add(o);
        return false;
      }else{
        return true;
      }
    });
    Collections.sort(chongFuList);
  }

  @Override
  public String getFinalResult() {
    return null;
  }

  @Override
  public Object getFinalResultObj() {
    return new TimeResult(jianGeList, caiJiShangBaoList, chongFuList, yiChangList);
  }

  public static class TimeResult{

    List<Long> jianGeList ;
    List<Long> caiJiShangBaoList ;
    List<Long> chongFuList ;
    List<String> yiChangList ;

    public TimeResult(List<Long> jianGeList, List<Long> caiJiShangBaoList, List<Long> chongFuList, List<String> yiChangList) {
      this.jianGeList = jianGeList;
      this.caiJiShangBaoList = caiJiShangBaoList;
      this.chongFuList = chongFuList;
      this.yiChangList = yiChangList;
    }

    public List<Long> getJianGeList() {
      return jianGeList;
    }

    public List<Long> getCaiJiShangBaoList() {
      return caiJiShangBaoList;
    }

    public List<Long> getChongFuList() {
      return chongFuList;
    }

    public List<String> getYiChangList() {
      return yiChangList;
    }
  }

}
