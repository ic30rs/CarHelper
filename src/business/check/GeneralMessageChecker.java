package business.check;

import business.model.DianJiData;
import business.model.EngineData;
import business.model.MessageBean;
import business.model.TotalCarData;

public class GeneralMessageChecker extends AbstractMessageChecker{

    int yiChangCount = 0;
    int wuXiaoCount = 0;
    int kongZhiCount = 0;
    int lingZhiCount = 0;
    int fanWeiYiChangCount = 0;

    @Override
    public boolean isDataValid(MessageBean bean) {

        //����һЩ����ֵ
        boolean isYiChang = false;
        boolean isWuXiao = false;
        boolean isKongZhi = false;
        boolean isLingZhi = false;
        boolean isFanWeiYiChang = false;

        //�ж���������
        if(bean.getTotalCarData() == null){
            isKongZhi = true;
        }else{
            //---�жϳ���״̬---
            int carStatus = bean.getTotalCarData().getCarStatus();
            if(carStatus == 0xFE) isYiChang = true;
            if(carStatus == 0xFF) isWuXiao = true;

            //---���״̬---
            int chargeStatus = bean.getTotalCarData().getChargeStatus();
            if(chargeStatus == 0xFE) isYiChang = true;
            if(chargeStatus == 0xFF) isWuXiao = true;

            //---����״̬---
            int runStatus = bean.getTotalCarData().getRunStatus();
            if(runStatus == 0xFE) isYiChang = true;
            if(runStatus == 0xFF) isWuXiao = true;

            //---����---
            TotalCarData totalCarData = bean.getTotalCarData();
            float speed = totalCarData.getVelocity();
            if(speed < 0 || speed > 220) isFanWeiYiChang = true;
            if(speed == 0) isLingZhi = true;
            if(speed == Integer.parseInt("FFFE", 16) * 0.1f) isYiChang = true;
            if(speed == Integer.parseInt("FFFF", 16) * 0.1f) isWuXiao = true;

            //---�ۼ����---
            float totalDistance = totalCarData.getTotalDistance();
            if(totalDistance < 0 || totalDistance > 9999999) isFanWeiYiChang = true;
            if(speed == 0) isLingZhi = true;
            if(speed == Integer.parseInt("FFFFFFFE", 16) * 0.1f) isYiChang = true;
            if(speed == Integer.parseInt("FFFFFFFF", 16) * 0.1f) isWuXiao = true;

            //---�ܵ�ѹ---
            float zongdianya = totalCarData.getZongDianYa();
            if(zongdianya < 0 || zongdianya > 10000) isFanWeiYiChang = true;
            if(zongdianya == 0) isLingZhi = true;
            if(zongdianya == Integer.parseInt("FFFFFFFE", 16) * 0.1f) isYiChang = true;
            if(zongdianya == Integer.parseInt("FFFFFFFF", 16) * 0.1f) isWuXiao = true;

            //---�ܵ���---
            float zongdianliu = totalCarData.getZongDianLiu();
            if(zongdianliu < -1000 || zongdianliu > 1000) isFanWeiYiChang = true;
            if(zongdianliu == 0) isLingZhi = true;
            if(zongdianliu == Integer.parseInt("FFFE", 16) * 0.1f) isYiChang = true;
            if(zongdianliu == Integer.parseInt("FFFF", 16) * 0.1f) isWuXiao = true;

            //---SOC---
            int soc = bean.getTotalCarData().getSOC();
            if(soc < 0 || soc > 100) isFanWeiYiChang = true;
            if(soc == 0) isLingZhi = true;
            if(soc == 0xFE) isYiChang = true;
            if(soc == 0xFF) isWuXiao = true;

            //---SOC---
            int dcdc = bean.getTotalCarData().getDC_DC_Status();
            if(dcdc == 0) isLingZhi = true;
            if(dcdc == 0xFE) isYiChang = true;
            if(dcdc == 0xFF) isWuXiao = true;

        }

        //�ж������������
        if(bean.getDianJiDataList() == null){
            isKongZhi = true;
        }else{
            for(DianJiData dianJiData : bean.getDianJiDataList()){
                int dianJiTemp = dianJiData.getDianJiTemp();
                //...�����ж�...
            }
        }

        //�жϷ���������
        if(bean.getEngineData() == null){
            isKongZhi = true;
        }else{

            //---������״̬---
            int engineStatus = bean.getEngineData().getEngineStatus();
            if(engineStatus == 0) isLingZhi = true;
            if(engineStatus == 0xFE) isYiChang = true;
            if(engineStatus == 0xFF) isWuXiao = true;

            //---����ת��---
            int axleSpeed = bean.getEngineData().getAxleSpeed();
            if(axleSpeed == 0) isLingZhi = true;
            if(axleSpeed == 0xFFFE) isYiChang = true;
            if(axleSpeed == 0xFFFF) isWuXiao = true;

            //---ȼ��ȼ����---
            EngineData engineData = bean.getEngineData();
            float fuelRatio = engineData.getFuelRatio();
            if(fuelRatio < 0 || fuelRatio > 60000) isFanWeiYiChang = true;
            if(fuelRatio == 0) isLingZhi = true;
            if(fuelRatio == Integer.parseInt("FFFE", 16) * 0.01f) isYiChang = true;
            if(fuelRatio == Integer.parseInt("FFFF", 16) * 0.01f) isWuXiao = true;


        }

        //�жϳ���λ��
        if(bean.getCarPosition() == null){
            isKongZhi = true;
        }else{

            //---��λ״̬---
            int positionStatus = bean.getCarPosition().getPositionStatus();
            if(positionStatus == 0x01) isYiChang = true;


        }

        //�жϼ�ֵ����
        if(bean.getExtremeData() == null){
            isKongZhi = true;
        }else{

            //---��ߵ�ѹϵͳ��---
            int highBatterySysNum = bean.getExtremeData().getHighBatterySysNum();
            if(highBatterySysNum < 0 || highBatterySysNum > 250) isFanWeiYiChang = true;
            if(highBatterySysNum == 0) isLingZhi = true;
            if(highBatterySysNum == 0xFE) isYiChang = true;
            if(highBatterySysNum == 0xFF) isWuXiao = true;

            //---��ߵ����ѹ��---
            int highBatteryNum = bean.getExtremeData().getHighBatteryNum();
            if(highBatteryNum < 0 || highBatteryNum > 250) isFanWeiYiChang = true;
            if(highBatteryNum == 0) isLingZhi = true;
            if(highBatteryNum == 0xFE) isYiChang = true;
            if(highBatteryNum == 0xFF) isWuXiao = true;

            //---��ص����ѹ���ֵ---
            float highBattery = bean.getExtremeData().getHighBattery();
            if(highBattery < 0 || highBattery > 15) isFanWeiYiChang = true;
            if(highBattery == 0) isLingZhi = true;
            if(highBattery == Integer.parseInt("FFFE", 16) * 0.001f) isYiChang = true;
            if(highBattery == Integer.parseInt("FFFF", 16) * 0.001f) isWuXiao = true;

            //---��͵�ѹϵͳ��---
            int lowBatterySysNum = bean.getExtremeData().getLowBatterySysNum();
            if(lowBatterySysNum < 0 || lowBatterySysNum > 250) isFanWeiYiChang = true;
            if(lowBatterySysNum == 0) isLingZhi = true;
            if(lowBatterySysNum == 0xFE) isYiChang = true;
            if(lowBatterySysNum == 0xFF) isWuXiao = true;

            //---��͵����ѹ��---
            int lowBatteryNum = bean.getExtremeData().getLowBatteryNum();
            if(lowBatteryNum < 0 || lowBatteryNum > 250) isFanWeiYiChang = true;
            if(lowBatteryNum == 0) isLingZhi = true;
            if(lowBatteryNum == 0xFE) isYiChang = true;
            if(lowBatteryNum == 0xFF) isWuXiao = true;

            //---��ص����ѹ���ֵ---
            float lowBattery = bean.getExtremeData().getLowBattery();
            if(lowBattery < 0 || lowBattery > 15) isFanWeiYiChang = true;
            if(lowBattery == 0) isLingZhi = true;
            if(lowBattery == Integer.parseInt("FFFE", 16) * 0.001f) isYiChang = true;
            if(lowBattery == Integer.parseInt("FFFF", 16) * 0.001f) isWuXiao = true;

            //---����¶���ϵͳ---
            int highSysNum = bean.getExtremeData().getHighSysNum();
            if(highSysNum < 0 || highSysNum > 250) isFanWeiYiChang = true;
            if(highSysNum == 0) isLingZhi = true;
            if(highSysNum == 0xFE) isYiChang = true;
            if(highSysNum == 0xFF) isWuXiao = true;

            //---����¶�̽���---
            int highProbeNum = bean.getExtremeData().getHighProbeNum();
            if(highProbeNum < 0 || highProbeNum > 250) isFanWeiYiChang = true;
            if(highProbeNum == 0) isLingZhi = true;
            if(highProbeNum == 0xFE) isYiChang = true;
            if(highProbeNum == 0xFF) isWuXiao = true;

            //---����¶�ֵ---
            int highTemp = bean.getExtremeData().getHighTemp();
            if(highTemp < -40 || highTemp > 210) isFanWeiYiChang = true;
            if(highTemp == 0) isLingZhi = true;
            if(highTemp == 0xFE) isYiChang = true;
            if(highTemp == 0xFF) isWuXiao = true;

            //---����¶���ϵͳ---
            int lowSysNum = bean.getExtremeData().getLowSysNum();
            if(lowSysNum < 0 || highSysNum > 250) isFanWeiYiChang = true;
            if(lowSysNum == 0) isLingZhi = true;
            if(lowSysNum == 0xFE) isYiChang = true;
            if(lowSysNum == 0xFF) isWuXiao = true;

            //---����¶�̽���---
            int lowProbeNum = bean.getExtremeData().getLowProbeNum();
            if(lowProbeNum < 0 || highProbeNum > 250) isFanWeiYiChang = true;
            if(lowProbeNum == 0) isLingZhi = true;
            if(lowProbeNum == 0xFE) isYiChang = true;
            if(lowProbeNum == 0xFF) isWuXiao = true;

            //---����¶�ֵ---
            int lowTemp = bean.getExtremeData().getLowTemp();
            if(lowTemp < -40 || lowTemp > 210) isFanWeiYiChang = true;
            if(lowTemp == 0) isLingZhi = true;
            if(lowTemp == 0xFE) isYiChang = true;
            if(lowTemp == 0xFF) isWuXiao = true;




        }






        if(isYiChang) yiChangCount++;
        if(isKongZhi) kongZhiCount++;
        if(isFanWeiYiChang) fanWeiYiChangCount++;
        if(isWuXiao) wuXiaoCount++;
        if(isLingZhi) lingZhiCount++;



        return false;
    }

    @Override
    protected void preCheck(MessageBean bean) {

    }

    @Override
    public String getFinalResult() {
        return "";
    }
}
