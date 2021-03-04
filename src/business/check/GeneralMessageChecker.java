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

  public static final String[] GENERAL_CHECK_ITEMS = new String[]{"����״̬", "���״̬", "����ģʽ", "����",
      "�ۼ����", "�ܵ�ѹ", "�ܵ���", "SOC", "DC-DC״̬", "��Ե����", "����������", "�������״̬", "��������������¶�", "�������ת��",
      "�������ת��", "��������¶�", "��������������ѹ", "���������ֱ��ĸ�ߵ���", "������״̬", "����ת��", "ȼ��������", "��λ״̬", "��ߵ�ѹ��ϵͳ��",
      "��ߵ�ѹ��ص������", "��ص����ѹ���ֵ", "��͵�ѹ�����ϵͳ��", "��͵�ѹ��ص������", "��ص����ѹ���ֵ", "����¶���ϵͳ��", "����¶�̽�����",
      "����¶�ֵ", "����¶���ϵͳ��", "����¶�̽�����", "����¶�ֵ",
  };
  int yiChangCount = 0;
  int wuXiaoCount = 0;
  int kongZhiCount = 0;
  int lingZhiCount = 0;
  int fanWeiYiChangCount = 0;
  //�����ֵ����ֵ������
  //{ "�ٶ�": [100, 10], "���": [100, 20] }
  //�������һ�����ִ�����������������ֵ�ĸ���
  //�򵥼���ɵã��ٶȿ�ֵ��Ϊ10% ��̿�ֵ��20%
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

    //����һЩ����ֵ
    boolean isYiChang = false;
    boolean isWuXiao = false;
    boolean isKongZhi = false;
    boolean isLingZhi = false;
    boolean isFanWeiYiChang = false;

    boolean isParking = true;

    //�ж���������
    if (bean.getTotalCarData() == null) {
      isKongZhi = true;
      //��ֵģ�����
      //��������Ϊ�գ������������������ж������ǿ�ֵ��
      increaseCheckMapValNum(kongZhiMap, "����״̬");
      increaseCheckMapValNum(kongZhiMap, "���״̬");
      increaseCheckMapValNum(kongZhiMap, "����ģʽ");
      increaseCheckMapValNum(kongZhiMap, "����");
      increaseCheckMapValNum(kongZhiMap, "�ۼ����");
      increaseCheckMapValNum(kongZhiMap, "�ܵ�ѹ");
      increaseCheckMapValNum(kongZhiMap, "�ܵ���");
      increaseCheckMapValNum(kongZhiMap, "SOC");
      increaseCheckMapValNum(kongZhiMap, "DC-DC״̬");
      increaseCheckMapValNum(kongZhiMap, "��Ե����");
    } else {
      //��ֵģ�����
      //�������ÿ��ֵ����������ע�����������׺��TotalNum����ValNum
      //��������lingZhiMap������kongZhiMap
      //������ֵ����������һ�����
      increaseCheckMapTotalNum(lingZhiMap, "����״̬");
      increaseCheckMapTotalNum(lingZhiMap, "���״̬");
      increaseCheckMapTotalNum(lingZhiMap, "����ģʽ");
      increaseCheckMapTotalNum(lingZhiMap, "����");
      increaseCheckMapTotalNum(lingZhiMap, "�ۼ����");
      increaseCheckMapTotalNum(lingZhiMap, "�ܵ�ѹ");
      increaseCheckMapTotalNum(lingZhiMap, "�ܵ���");
      increaseCheckMapTotalNum(lingZhiMap, "SOC");
      increaseCheckMapTotalNum(lingZhiMap, "DC-DC״̬");
      increaseCheckMapTotalNum(lingZhiMap, "��Ե����");
      //---�жϳ���״̬---
      int carStatus = bean.getTotalCarData().getCarStatus();
      if (carStatus == 0xFE) {
        isYiChang = true;
      }
      if (carStatus == 0xFF) {
        isWuXiao = true;
      }
      if (carStatus == 0) {
        //��ֵģ�����
        //���ÿ���ֶν����жϣ�Ϊ0����������ֵ��Ŀ
        increaseCheckMapValNum(lingZhiMap, "����״̬");
      }
      isParking = carStatus == 0x02;

      //---���״̬---
      int chargeStatus = bean.getTotalCarData().getChargeStatus();
      if (chargeStatus == 0xFE) {
        isYiChang = true;
      }
      if (chargeStatus == 0xFF) {
        isWuXiao = true;
      }
      if (chargeStatus == 0) {
        increaseCheckMapValNum(lingZhiMap, "���״̬");
      }

      //---����״̬---
      int runStatus = bean.getTotalCarData().getRunStatus();
      if (runStatus == 0xFE) {
        isYiChang = true;
      }
      if (runStatus == 0xFF) {
        isWuXiao = true;
      }
      if (runStatus == 0) {
        increaseCheckMapValNum(lingZhiMap, "����ģʽ");
      }

      //---����---
      TotalCarData totalCarData = bean.getTotalCarData();
      float speed = totalCarData.getVelocity();
      if (speed < 0 || speed > 220) {
        isFanWeiYiChang = true;
      }
      if (speed == 0) {
        increaseCheckMapValNum(lingZhiMap, "����");
      }
      if (speed == Integer.parseInt("FFFE", 16) * 0.1f) {
        isYiChang = true;
      }
      if (speed == Integer.parseInt("FFFF", 16) * 0.1f) {
        isWuXiao = true;
      }

      //---�ۼ����---
      float totalDistance = totalCarData.getTotalDistance();
      if (totalDistance < 0 || totalDistance > 9999999) {
        isFanWeiYiChang = true;
      }
      if (totalDistance == 0) {
        increaseCheckMapValNum(lingZhiMap, "�ۼ����");
      }
      if (totalDistance == Long.parseLong("FFFFFFFE", 16) * 0.1f) {
        isYiChang = true;
      }
      if (totalDistance == Long.parseLong("FFFFFFFF", 16) * 0.1f) {
        isWuXiao = true;
      }

      //---�ܵ�ѹ---
      float zongdianya = totalCarData.getZongDianYa();
      if (zongdianya < 0 || zongdianya > 10000) {
        isFanWeiYiChang = true;
      }
      if (zongdianya == 0) {
        increaseCheckMapValNum(lingZhiMap, "�ܵ�ѹ");
      }
      if (zongdianya == Integer.parseInt("FFFE", 16) * 0.1f) {
        isYiChang = true;
      }
      if (zongdianya == Integer.parseInt("FFFF", 16) * 0.1f) {
        isWuXiao = true;
      }

      //---�ܵ���---
      float zongdianliu = totalCarData.getZongDianLiu();
      if (zongdianliu < -1000 || zongdianliu > 1000) {
        isFanWeiYiChang = true;
      }
      if (zongdianliu == 0) {
        increaseCheckMapValNum(lingZhiMap, "�ܵ���");
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
        increaseCheckMapValNum(lingZhiMap, "DC-DC״̬");
      }
      if (dcdc == 0xFE) {
        isYiChang = true;
      }
      if (dcdc == 0xFF) {
        isWuXiao = true;
      }

      int jueYuanDianZu = bean.getTotalCarData().getJueYuanDianZu();
      if (jueYuanDianZu == 0) {
        increaseCheckMapValNum(lingZhiMap, "��Ե����");
      }
      if (jueYuanDianZu <= 0 || jueYuanDianZu >= 60000) {
        isFanWeiYiChang = true;
      }

    }

    //�ж������������
    if (bean.getDianJiDataList() == null) {
      isKongZhi = true;
    } else {
      for (DianJiData dianJiData : bean.getDianJiDataList()) {

        if (!isParking) {
          //---������---
          int dianJiSerialNum = dianJiData.getDianJiSerialNum();
          if (dianJiSerialNum < 1 || dianJiSerialNum > 254) {
            isFanWeiYiChang = true;
          }
          if (dianJiSerialNum == 0) {
            isLingZhi = true;
          }

          //---���״̬---
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

          //---��������������¶�---
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

          //---�������ת��---
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

          //---�������ת��---
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

          //---��������¶�---
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

          //---���������������ѹ---
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

          //---���������������ѹ---
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

        //---�������---
        int dianJiNum = bean.getDianJiDataList().size();
        if (dianJiNum < 1 || dianJiNum > 254) {
          isFanWeiYiChang = true;
        }
        if (dianJiNum == 0) {
          isLingZhi = true;
        }


      }
    }

    //�жϷ���������
    if (bean.getEngineData() == null) {
      isKongZhi = true;
    } else {
      if (!isParking) {
        //---������״̬---
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

        //---����ת��---
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

        //---ȼ��ȼ����---
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

    //�жϳ���λ��
    if (bean.getCarPosition() == null) {
      isKongZhi = true;
    } else {

      //---��λ״̬---
      int positionStatus = bean.getCarPosition().getPositionStatus();
      if (positionStatus == 0x01) {
        isYiChang = true;
      }


    }

    //�жϼ�ֵ����
    if (bean.getExtremeData() == null) {
      isKongZhi = true;
    } else {

      //---��ߵ�ѹϵͳ��---
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

      //---��ߵ����ѹ��---
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

      //---��ص����ѹ���ֵ---
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

      //---��͵�ѹϵͳ��---
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

      //---��͵����ѹ��---
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

      //---��ص����ѹ���ֵ---
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

      //---����¶���ϵͳ---
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

      //---����¶�̽���---
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

      //---����¶�ֵ---
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

      //---����¶���ϵͳ---
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

      //---����¶�̽���---
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

      //---����¶�ֵ---
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
    sb.append("�쳣ֵ��:").append(String.format("%.2f%%", 100f * yiChangCount / beans.size()))
        .append("<br>");
    sb.append("��Чֵ��:").append(String.format("%.2f%%", 100f * wuXiaoCount / beans.size()))
        .append("<br>");
    sb.append("��Χ�쳣ֵ��:").append(String.format("%.2f%%", 100f * fanWeiYiChangCount / beans.size()))
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
