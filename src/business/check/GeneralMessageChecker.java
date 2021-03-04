package business.check;

import business.model.DianJiData;
import business.model.EngineData;
import business.model.MessageBean;
import business.model.TotalCarData;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GeneralMessageChecker extends AbstractMessageChecker {

  public static final String[] GENERAL_CHECK_ITEMS = new String[]{"车辆状态", "充电状态", "运行模式", "车速",
      "累计里程", "总电压", "总电流", "SOC", "DC-DC状态", "绝缘电阻", "驱动电机序号", "驱动电机状态", "驱动电机控制器温度", "驱动电机转速",
      "驱动电机转矩", "驱动电机温度", "电机控制器输入电压", "电机控制器直流母线电流", "发动机状态", "曲轴转速", "燃料消耗率", "定位状态", "最高电压子系统号",
      "最高电压电池单体代号", "电池单体电压最高值", "最低电压电池子系统号", "最低电压电池单体代号", "电池单体电压最低值", "最高温度子系统号", "最高温度探针序号",
      "最高温度值", "最低温度子系统号", "最低温度探针序号", "最低温度值",
  };
  int yiChangCount = 0;
  int wuXiaoCount = 0;
  int kongZhiCount = 0;
  int lingZhiCount = 0;
  int fanWeiYiChangCount = 0;
  //计算空值率零值率所用
  //{ "速度": [100, 10], "里程": [100, 20] }
  //数组里第一个数字代表总数，后面代表空值的个数
  //简单计算可得，速度空值率为10% 里程空值率20%
  Map<String, int[]> kongZhiMap = new LinkedHashMap<>();
  Map<String, int[]> lingZhiMap = new LinkedHashMap<>();
  String checkResult;
  GeneralResult checkResultObj;

  @Override
  protected void preCheckAll(List<MessageBean> beans) {
    for (String s : GENERAL_CHECK_ITEMS) {
      kongZhiMap.put(s, new int[]{0, 0});
      lingZhiMap.put(s, new int[]{0, 0});
    }
  }

  @Override
  protected void preCheck(MessageBean bean) {
    for (String s : GENERAL_CHECK_ITEMS) {
      increaseCheckMapTotalNum(kongZhiMap, s);
    }
  }

  @Override
  public boolean isDataValid(MessageBean bean) {

    //定义一些布尔值
    boolean isYiChang = false;
    boolean isWuXiao = false;
    boolean isKongZhi = false;
    boolean isLingZhi = false;
    boolean isFanWeiYiChang = false;

    boolean isParking = true;

    //判断整车数据
    if (bean.getTotalCarData() == null) {
      isKongZhi = true;
      //空值模板举例
      //整车数据为空，整车数据所带的所有东西都是空值了
      increaseCheckMapValNum(kongZhiMap, "车辆状态");
      increaseCheckMapValNum(kongZhiMap, "充电状态");
      increaseCheckMapValNum(kongZhiMap, "运行模式");
      increaseCheckMapValNum(kongZhiMap, "车速");
      increaseCheckMapValNum(kongZhiMap, "累计里程");
      increaseCheckMapValNum(kongZhiMap, "总电压");
      increaseCheckMapValNum(kongZhiMap, "总电流");
      increaseCheckMapValNum(kongZhiMap, "SOC");
      increaseCheckMapValNum(kongZhiMap, "DC-DC状态");
      increaseCheckMapValNum(kongZhiMap, "绝缘电阻");
    } else {
      //零值模板举例
      //首先针对每个值增加总数，注意这个函数后缀是TotalNum不是ValNum
      //操作的是lingZhiMap而不是kongZhiMap
      //增加零值数量的在这一坨后面
      increaseCheckMapTotalNum(lingZhiMap, "车辆状态");
      increaseCheckMapTotalNum(lingZhiMap, "充电状态");
      increaseCheckMapTotalNum(lingZhiMap, "运行模式");
      increaseCheckMapTotalNum(lingZhiMap, "车速");
      increaseCheckMapTotalNum(lingZhiMap, "累计里程");
      increaseCheckMapTotalNum(lingZhiMap, "总电压");
      increaseCheckMapTotalNum(lingZhiMap, "总电流");
      increaseCheckMapTotalNum(lingZhiMap, "SOC");
      increaseCheckMapTotalNum(lingZhiMap, "DC-DC状态");
      increaseCheckMapTotalNum(lingZhiMap, "绝缘电阻");
      //---判断车辆状态---
      int carStatus = bean.getTotalCarData().getCarStatus();
      if (carStatus == 0xFE) {
        isYiChang = true;
      }
      if (carStatus == 0xFF) {
        isWuXiao = true;
      }
      if (carStatus == 0) {
        //零值模板举例
        //针对每个字段进行判断，为0，则增加零值数目
        increaseCheckMapValNum(lingZhiMap, "车辆状态");
      }
      isParking = carStatus == 0x02;

      //---充电状态---
      int chargeStatus = bean.getTotalCarData().getChargeStatus();
      if (chargeStatus == 0xFE) {
        isYiChang = true;
      }
      if (chargeStatus == 0xFF) {
        isWuXiao = true;
      }
      if (chargeStatus == 0) {
        increaseCheckMapValNum(lingZhiMap, "充电状态");
      }

      //---运行状态---
      int runStatus = bean.getTotalCarData().getRunStatus();
      if (runStatus == 0xFE) {
        isYiChang = true;
      }
      if (runStatus == 0xFF) {
        isWuXiao = true;
      }
      if (runStatus == 0) {
        increaseCheckMapValNum(lingZhiMap, "运行模式");
      }

      //---车速---
      TotalCarData totalCarData = bean.getTotalCarData();
      float speed = totalCarData.getVelocity();
      if (speed < 0 || speed > 220) {
        isFanWeiYiChang = true;
      }
      if (speed == 0) {
        increaseCheckMapValNum(lingZhiMap, "车速");
      }
      if (speed == Integer.parseInt("FFFE", 16) * 0.1f) {
        isYiChang = true;
      }
      if (speed == Integer.parseInt("FFFF", 16) * 0.1f) {
        isWuXiao = true;
      }

      //---累计里程---
      float totalDistance = totalCarData.getTotalDistance();
      if (totalDistance < 0 || totalDistance > 9999999) {
        isFanWeiYiChang = true;
      }
      if (totalDistance == 0) {
        increaseCheckMapValNum(lingZhiMap, "累计里程");
      }
      if (totalDistance == Long.parseLong("FFFFFFFE", 16) * 0.1f) {
        isYiChang = true;
      }
      if (totalDistance == Long.parseLong("FFFFFFFF", 16) * 0.1f) {
        isWuXiao = true;
      }

      //---总电压---
      float zongdianya = totalCarData.getZongDianYa();
      if (zongdianya < 0 || zongdianya > 10000) {
        isFanWeiYiChang = true;
      }
      if (zongdianya == 0) {
        increaseCheckMapValNum(lingZhiMap, "总电压");
      }
      if (zongdianya == Integer.parseInt("FFFE", 16) * 0.1f) {
        isYiChang = true;
      }
      if (zongdianya == Integer.parseInt("FFFF", 16) * 0.1f) {
        isWuXiao = true;
      }

      //---总电流---
      float zongdianliu = totalCarData.getZongDianLiu();
      if (zongdianliu < -1000 || zongdianliu > 1000) {
        isFanWeiYiChang = true;
      }
      if (zongdianliu == 0) {
        increaseCheckMapValNum(lingZhiMap, "总电流");
      }
      if (zongdianliu == -1000 + Integer.parseInt("FFFE", 16) * 0.1f) {
        isYiChang = true;
      }
      if (zongdianliu == -1000 + Integer.parseInt("FFFF", 16) * 0.1f) {
        isWuXiao = true;
      }

      //---SOC---
      int soc = bean.getTotalCarData().getSOC();
      if (soc < 0 || soc > 100) {
        isFanWeiYiChang = true;
      }
      if (soc == 0) {
        increaseCheckMapValNum(lingZhiMap, "SOC");

      }
      if (soc == 0xFE) {
        isYiChang = true;
      }
      if (soc == 0xFF) {
        isWuXiao = true;
      }

      //---DCDC---
      int dcdc = bean.getTotalCarData().getDC_DC_Status();
      if (dcdc == 0) {
        increaseCheckMapValNum(lingZhiMap, "DC-DC状态");
      }
      if (dcdc == 0xFE) {
        isYiChang = true;
      }
      if (dcdc == 0xFF) {
        isWuXiao = true;
      }

      int jueYuanDianZu = bean.getTotalCarData().getJueYuanDianZu();
      if (jueYuanDianZu == 0) {
        increaseCheckMapValNum(lingZhiMap, "绝缘电阻");
      }
      if (jueYuanDianZu <= 0 || jueYuanDianZu >= 60000) {
        isFanWeiYiChang = true;
      }

    }

    //判断驱动电机数据
    if (bean.getDianJiDataList() == null) {
      isKongZhi = true;
    } else {
      for (DianJiData dianJiData : bean.getDianJiDataList()) {

        if (!isParking) {
          //---电机序号---
          int dianJiSerialNum = dianJiData.getDianJiSerialNum();
          if (dianJiSerialNum < 1 || dianJiSerialNum > 254) {
            isFanWeiYiChang = true;
          }
          if (dianJiSerialNum == 0) {
            isLingZhi = true;
          }

          //---电机状态---
          int dianjiStatus = dianJiData.getDianJiStatus();
          if (dianjiStatus == 0) {
            isLingZhi = true;
          }
          if (dianjiStatus == 0xFE) {
            isYiChang = true;
          }
          if (dianjiStatus == 0xFF) {
            isWuXiao = true;
          }

          //---驱动电机控制器温度---
          int dianJiControllerTemp = dianJiData.getDianJiControllerTemp();
          if (dianJiControllerTemp < -40 || dianJiControllerTemp > 210) {
            isFanWeiYiChang = true;
          }
          if (dianJiControllerTemp == -40 + Integer.parseInt("FE", 16)) {
            isYiChang = true;
          }
          if (dianJiControllerTemp == -40 + Integer.parseInt("FE", 16)) {
            isWuXiao = true;
          }

          //---驱动电机转速---
          int dianJiZhuanSu = dianJiData.getDianJiZhuanSu();
          if (dianJiZhuanSu < -20000 || dianJiZhuanSu > 45531) {
            isFanWeiYiChang = true;
          }
          if (dianJiZhuanSu == -20000 + Integer.parseInt("FFFE", 16)) {
            isYiChang = true;
          }
          if (dianJiZhuanSu == -20000 + Integer.parseInt("FFFF", 16)) {
            isWuXiao = true;
          }

          //---驱动电机转距---
          float dianJiNiuJu = dianJiData.getDianJiNiuJu();
          if (dianJiNiuJu < -2000 || dianJiNiuJu > 4553.1f) {
            isFanWeiYiChang = true;
          }
          if (dianJiNiuJu == -2000 + Integer.parseInt("FFFE", 16) * 0.1f) {
            isYiChang = true;
          }
          if (dianJiNiuJu == -2000 + Integer.parseInt("FFFF", 16) * 0.1f) {
            isWuXiao = true;
          }

          //---驱动电机温度---
          int dianJiTemp = dianJiData.getDianJiTemp();
          if (dianJiTemp < -40 || dianJiTemp > 210) {
            isFanWeiYiChang = true;
          }
          if (dianJiTemp == -40 + Integer.parseInt("FE", 16)) {
            isYiChang = true;
          }
          if (dianJiTemp == -40 + Integer.parseInt("FF", 16)) {
            isWuXiao = true;
          }

          //---电气控制器输入电压---
          float dianJiControllerDianYa = dianJiData.getDianJiControllerDianYa();
          if (dianJiControllerDianYa < 0 || dianJiControllerDianYa > 6000) {
            isFanWeiYiChang = true;
          }
          if (dianJiControllerDianYa == Integer.parseInt("FFFE", 16) * 0.1f) {
            isYiChang = true;
          }
          if (dianJiControllerDianYa == Integer.parseInt("FFFF", 16) * 0.1f) {
            isWuXiao = true;
          }

          //---电气控制器输入电压---
          float dianJiControllerZhiLiuMuXianDianLiu = dianJiData
              .getDianJiControllerZhiLiuMuXianDianLiu();
          if (dianJiControllerZhiLiuMuXianDianLiu < -1000
              || dianJiControllerZhiLiuMuXianDianLiu > 1000) {
            isFanWeiYiChang = true;
          }
          if (dianJiControllerZhiLiuMuXianDianLiu
              == -1000 + Integer.parseInt("FFFE", 16) * 0.1f) {
            isYiChang = true;
          }
          if (dianJiControllerZhiLiuMuXianDianLiu
              == -1000 + Integer.parseInt("FFFF", 16) * 0.1f) {
            isWuXiao = true;
          }


        }

        //---电机个数---
        int dianJiNum = bean.getDianJiDataList().size();
        if (dianJiNum < 1 || dianJiNum > 254) {
          isFanWeiYiChang = true;
        }
        if (dianJiNum == 0) {
          isLingZhi = true;
        }


      }
    }

    //判断发动机数据
    if (bean.getEngineData() == null) {
      isKongZhi = true;
    } else {
      if (!isParking) {
        //---发动机状态---
        int engineStatus = bean.getEngineData().getEngineStatus();
        if (engineStatus == 0) {
          isLingZhi = true;
        }
        if (engineStatus == 0xFE) {
          isYiChang = true;
        }
        if (engineStatus == 0xFF) {
          isWuXiao = true;
        }

        //---曲轴转速---
        int axleSpeed = bean.getEngineData().getAxleSpeed();
        if (axleSpeed == 0) {
          isLingZhi = true;
        }
        if (axleSpeed == 0xFFFE) {
          isYiChang = true;
        }
        if (axleSpeed == 0xFFFF) {
          isWuXiao = true;
        }

        //---燃料燃烧率---
        EngineData engineData = bean.getEngineData();
        float fuelRatio = engineData.getFuelRatio();
        if (fuelRatio < 0 || fuelRatio > 60000) {
          isFanWeiYiChang = true;
        }
        if (fuelRatio == 0) {
          isLingZhi = true;
        }
        if (fuelRatio == Integer.parseInt("FFFE", 16) * 0.01f) {
          isYiChang = true;
        }
        if (fuelRatio == Integer.parseInt("FFFF", 16) * 0.01f) {
          isWuXiao = true;
        }

      }

    }

    //判断车辆位置
    if (bean.getCarPosition() == null) {
      isKongZhi = true;
    } else {

      //---定位状态---
      int positionStatus = bean.getCarPosition().getPositionStatus();
      if (positionStatus == 0x01) {
        isYiChang = true;
      }


    }

    //判断极值数据
    if (bean.getExtremeData() == null) {
      isKongZhi = true;
    } else {

      //---最高电压系统号---
      int highBatterySysNum = bean.getExtremeData().getHighBatterySysNum();
      if (highBatterySysNum < 0 || highBatterySysNum > 250) {
        isFanWeiYiChang = true;
      }
      if (highBatterySysNum == 0) {
        isLingZhi = true;
      }
      if (highBatterySysNum == 0xFE) {
        isYiChang = true;
      }
      if (highBatterySysNum == 0xFF) {
        isWuXiao = true;
      }

      //---最高单体电压号---
      int highBatteryNum = bean.getExtremeData().getHighBatteryNum();
      if (highBatteryNum < 0 || highBatteryNum > 250) {
        isFanWeiYiChang = true;
      }
      if (highBatteryNum == 0) {
        isLingZhi = true;
      }
      if (highBatteryNum == 0xFE) {
        isYiChang = true;
      }
      if (highBatteryNum == 0xFF) {
        isWuXiao = true;
      }

      //---电池单体电压最高值---
      float highBattery = bean.getExtremeData().getHighBattery();
      if (highBattery < 0 || highBattery > 15) {
        isFanWeiYiChang = true;
      }
      if (highBattery == 0) {
        isLingZhi = true;
      }
      if (highBattery == Integer.parseInt("FFFE", 16) * 0.001f) {
        isYiChang = true;
      }
      if (highBattery == Integer.parseInt("FFFF", 16) * 0.001f) {
        isWuXiao = true;
      }

      //---最低电压系统号---
      int lowBatterySysNum = bean.getExtremeData().getLowBatterySysNum();
      if (lowBatterySysNum < 0 || lowBatterySysNum > 250) {
        isFanWeiYiChang = true;
      }
      if (lowBatterySysNum == 0) {
        isLingZhi = true;
      }
      if (lowBatterySysNum == 0xFE) {
        isYiChang = true;
      }
      if (lowBatterySysNum == 0xFF) {
        isWuXiao = true;
      }

      //---最低单体电压号---
      int lowBatteryNum = bean.getExtremeData().getLowBatteryNum();
      if (lowBatteryNum < 0 || lowBatteryNum > 250) {
        isFanWeiYiChang = true;
      }
      if (lowBatteryNum == 0) {
        isLingZhi = true;
      }
      if (lowBatteryNum == 0xFE) {
        isYiChang = true;
      }
      if (lowBatteryNum == 0xFF) {
        isWuXiao = true;
      }

      //---电池单体电压最低值---
      float lowBattery = bean.getExtremeData().getLowBattery();
      if (lowBattery < 0 || lowBattery > 15) {
        isFanWeiYiChang = true;
      }
      if (lowBattery == 0) {
        isLingZhi = true;
      }
      if (lowBattery == Integer.parseInt("FFFE", 16) * 0.001f) {
        isYiChang = true;
      }
      if (lowBattery == Integer.parseInt("FFFF", 16) * 0.001f) {
        isWuXiao = true;
      }

      //---最高温度子系统---
      int highSysNum = bean.getExtremeData().getHighSysNum();
      if (highSysNum < 0 || highSysNum > 250) {
        isFanWeiYiChang = true;
      }
      if (highSysNum == 0) {
        isLingZhi = true;
      }
      if (highSysNum == 0xFE) {
        isYiChang = true;
      }
      if (highSysNum == 0xFF) {
        isWuXiao = true;
      }

      //---最高温度探针号---
      int highProbeNum = bean.getExtremeData().getHighProbeNum();
      if (highProbeNum < 0 || highProbeNum > 250) {
        isFanWeiYiChang = true;
      }
      if (highProbeNum == 0) {
        isLingZhi = true;
      }
      if (highProbeNum == 0xFE) {
        isYiChang = true;
      }
      if (highProbeNum == 0xFF) {
        isWuXiao = true;
      }

      //---最高温度值---
      int highTemp = bean.getExtremeData().getHighTemp();
      if (highTemp < -40 || highTemp > 210) {
        isFanWeiYiChang = true;
      }
      if (highTemp == 0) {
        isLingZhi = true;
      }
      if (highTemp == -40 + Integer.parseInt("FE", 16)) {
        isYiChang = true;
      }
      if (highTemp == -40 + Integer.parseInt("FF", 16)) {
        isWuXiao = true;
      }

      //---最低温度子系统---
      int lowSysNum = bean.getExtremeData().getLowSysNum();
      if (lowSysNum < 0 || highSysNum > 250) {
        isFanWeiYiChang = true;
      }
      if (lowSysNum == 0) {
        isLingZhi = true;
      }
      if (lowSysNum == 0xFE) {
        isYiChang = true;
      }
      if (lowSysNum == 0xFF) {
        isWuXiao = true;
      }

      //---最低温度探针号---
      int lowProbeNum = bean.getExtremeData().getLowProbeNum();
      if (lowProbeNum < 0 || highProbeNum > 250) {
        isFanWeiYiChang = true;
      }
      if (lowProbeNum == 0) {
        isLingZhi = true;
      }
      if (lowProbeNum == 0xFE) {
        isYiChang = true;
      }
      if (lowProbeNum == 0xFF) {
        isWuXiao = true;
      }

      //---最低温度值---
      int lowTemp = bean.getExtremeData().getLowTemp();
      if (lowTemp < -40 || lowTemp > 210) {
        isFanWeiYiChang = true;
      }
      if (lowTemp == 0) {
        isLingZhi = true;
      }
      if (lowTemp == -40 + Integer.parseInt("FE", 16)) {
        isYiChang = true;
      }
      if (lowTemp == -40 + Integer.parseInt("FF", 16)) {
        isWuXiao = true;
      }


    }

    if (isYiChang) {
      yiChangCount++;
    }
    if (isKongZhi) {
      kongZhiCount++;
    }
    if (isFanWeiYiChang) {
      fanWeiYiChangCount++;
    }
    if (isWuXiao) {
      wuXiaoCount++;
    }
    if (isLingZhi) {
      lingZhiCount++;
    }

    return false;
  }

  @Override
  protected void postCheckAll(List<MessageBean> beans, List<MessageBean> probBeans) {
    StringBuilder sb = new StringBuilder();
    sb.append("<html><body>");
    sb.append("异常值率:").append(String.format("%.2f%%", 100f * yiChangCount / beans.size()))
        .append("<br>");
    sb.append("无效值率:").append(String.format("%.2f%%", 100f * wuXiaoCount / beans.size()))
        .append("<br>");
    sb.append("范围异常值率:").append(String.format("%.2f%%", 100f * fanWeiYiChangCount / beans.size()))
        .append("<br>");
    sb.append("<body></html>");
    checkResult = sb.toString();
    checkResultObj = new GeneralResult(kongZhiMap, lingZhiMap, checkResult);
  }

  @Override
  public String getFinalResult() {
    return checkResult;
  }

  @Override
  public Object getFinalResultObj() {
    return checkResultObj;
  }

  private void increaseCheckMapTotalNum(Map<String, int[]> map, String key) {
    map.get(key)[0] = map.get(key)[0] + 1;
  }

  private void increaseCheckMapValNum(Map<String, int[]> map, String key) {
    map.get(key)[1] = map.get(key)[1] + 1;
  }

  public static class GeneralResult {

    private List<Float> kongZhiList = new ArrayList();
    private List<Float> lingZhiList = new ArrayList();
    private String result;

    GeneralResult(Map<String, int[]> kongZhiMap, Map<String, int[]> lingZhiMap, String result) {
      for (String s : GENERAL_CHECK_ITEMS) {
        int a = kongZhiMap.get(s)[0];
        int b = kongZhiMap.get(s)[1];
        float c = 0;
        if (a != 0) {
          c = b / (1.0f * a);
        }
        kongZhiList.add(c);

        a = lingZhiMap.get(s)[0];
        b = lingZhiMap.get(s)[1];
        c = 0;
        if (a != 0) {
          c = b / (1.0f * a);
        }
        lingZhiList.add(c);

      }
    }

    public List<Float> getKongZhiList() {
      return kongZhiList;
    }

    public List<Float> getLingZhiList() {
      return lingZhiList;
    }

    public String getResult() {
      return result;
    }


  }

}
