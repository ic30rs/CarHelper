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

        //定义一些布尔值
        boolean isYiChang = false;
        boolean isWuXiao = false;
        boolean isKongZhi = false;
        boolean isLingZhi = false;
        boolean isFanWeiYiChang = false;

        //判断整车数据
        if(bean.getTotalCarData() == null){
            isKongZhi = true;
        }else{
            //---判断车辆状态---
            int carStatus = bean.getTotalCarData().getCarStatus();
            if(carStatus == 0xFE) isYiChang = true;
            if(carStatus == 0xFF) isWuXiao = true;

            //---充电状态---
            int chargeStatus = bean.getTotalCarData().getChargeStatus();
            if(chargeStatus == 0xFE) isYiChang = true;
            if(chargeStatus == 0xFF) isWuXiao = true;

            //---运行状态---
            int runStatus = bean.getTotalCarData().getRunStatus();
            if(runStatus == 0xFE) isYiChang = true;
            if(runStatus == 0xFF) isWuXiao = true;

            //---车速---
            TotalCarData totalCarData = bean.getTotalCarData();
            float speed = totalCarData.getVelocity();
            if(speed < 0 || speed > 220) isFanWeiYiChang = true;
            if(speed == 0) isLingZhi = true;
            if(speed == Integer.parseInt("FFFE", 16) * 0.1f) isYiChang = true;
            if(speed == Integer.parseInt("FFFF", 16) * 0.1f) isWuXiao = true;

            //---累计里程---
            float totalDistance = totalCarData.getTotalDistance();
            if(totalDistance < 0 || totalDistance > 9999999) isFanWeiYiChang = true;
            if(speed == 0) isLingZhi = true;
            if(speed == Integer.parseInt("FFFFFFFE", 16) * 0.1f) isYiChang = true;
            if(speed == Integer.parseInt("FFFFFFFF", 16) * 0.1f) isWuXiao = true;

            //---总电压---
            float zongdianya = totalCarData.getZongDianYa();
            if(zongdianya < 0 || zongdianya > 10000) isFanWeiYiChang = true;
            if(zongdianya == 0) isLingZhi = true;
            if(zongdianya == Integer.parseInt("FFFFFFFE", 16) * 0.1f) isYiChang = true;
            if(zongdianya == Integer.parseInt("FFFFFFFF", 16) * 0.1f) isWuXiao = true;

            //---总电流---
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

        //判断驱动电机数据
        if(bean.getDianJiDataList() == null){
            isKongZhi = true;
        }else{
            for(DianJiData dianJiData : bean.getDianJiDataList()){



                //---电机序号---
                int dianJiSerialNum = dianJiData.getDianJiSerialNum();
                if(dianJiSerialNum < 1 || dianJiSerialNum > 254) isFanWeiYiChang = true;
                if(dianJiSerialNum == 0) isLingZhi = true;

                //---电机个数---
                int dianJiNum = dianJiData.getDianjiNum();
                if(dianJiNum < 1 || dianJiNum > 254) isFanWeiYiChang = true;
                if(dianJiNum == 0) isLingZhi = true;

                //---电机状态---
                int dianjiStatus = dianJiData.getDianJiStatus();
                if(dianjiStatus == 0) isLingZhi = true;
                if(dianjiStatus == 0xFE) isYiChang = true;
                if(dianjiStatus == 0xFF) isWuXiao = true;

                //---驱动电机控制器温度---
                int dianJiControllerTemp = dianJiData.getDianJiControllerTemp();
                if(dianJiControllerTemp < -40 || dianJiControllerTemp > 210) isFanWeiYiChang = true;
                if(dianJiControllerTemp == 0xFE) isYiChang = true;
                if(dianJiControllerTemp == 0xFF) isWuXiao = true;

                //---驱动电机转速---
                int dianJiZhuanSu = dianJiData.getDianJiZhuanSu();
                if(dianJiZhuanSu < -20000 || dianJiZhuanSu > 45531) isFanWeiYiChang = true;
                if(dianJiZhuanSu == Integer.parseInt("FFFE", 16) ) isYiChang = true;
                if(dianJiZhuanSu == Integer.parseInt("FFFF", 16) ) isWuXiao = true;

                //---驱动电机转距---
                float dianJiNiuJu = dianJiData.getDianJiNiuJu();
                if(dianJiNiuJu < -2000 || dianJiZhuanSu > 4553.1) isFanWeiYiChang = true;
                if(dianJiZhuanSu == Integer.parseInt("FFFE", 16) *0.1f) isYiChang = true;
                if(dianJiZhuanSu == Integer.parseInt("FFFF", 16) *0.1f) isWuXiao = true;

                //---驱动电机温度---
                int dianJiTemp = dianJiData.getDianJiTemp();
                if(dianJiTemp < -40 || dianJiTemp > 210) isFanWeiYiChang = true;
                if(dianJiTemp == 0xFE) isYiChang = true;
                if(dianJiTemp == 0xFF) isWuXiao = true;

                //---电气控制器输入电压---
                float dianJiControllerDianYa = dianJiData.getDianJiControllerDianYa();
                if(dianJiControllerDianYa < 0 || dianJiControllerDianYa > 6000) isFanWeiYiChang = true;
                if(dianJiControllerDianYa == Integer.parseInt("FFFE", 16) *0.1f) isYiChang = true;
                if(dianJiControllerDianYa == Integer.parseInt("FFFF", 16) *0.1f) isWuXiao = true;

                //---电气控制器输入电压---
                float dianJiControllerZhiLiuMuXianDianLiu = dianJiData.getDianJiControllerZhiLiuMuXianDianLiu();
                if(dianJiControllerZhiLiuMuXianDianLiu < -1000 || dianJiControllerZhiLiuMuXianDianLiu > 1000) isFanWeiYiChang = true;
                if(dianJiControllerZhiLiuMuXianDianLiu == Integer.parseInt("FFFE", 16) *0.1f) isYiChang = true;
                if(dianJiControllerZhiLiuMuXianDianLiu == Integer.parseInt("FFFF", 16) *0.1f) isWuXiao = true;






            }
        }

        //判断发动机数据
        if(bean.getEngineData() == null){
            isKongZhi = true;
        }else{

            //---发动机状态---
            int engineStatus = bean.getEngineData().getEngineStatus();
            if(engineStatus == 0) isLingZhi = true;
            if(engineStatus == 0xFE) isYiChang = true;
            if(engineStatus == 0xFF) isWuXiao = true;

            //---曲轴转速---
            int axleSpeed = bean.getEngineData().getAxleSpeed();
            if(axleSpeed == 0) isLingZhi = true;
            if(axleSpeed == 0xFFFE) isYiChang = true;
            if(axleSpeed == 0xFFFF) isWuXiao = true;

            //---燃料燃烧率---
            EngineData engineData = bean.getEngineData();
            float fuelRatio = engineData.getFuelRatio();
            if(fuelRatio < 0 || fuelRatio > 60000) isFanWeiYiChang = true;
            if(fuelRatio == 0) isLingZhi = true;
            if(fuelRatio == Integer.parseInt("FFFE", 16) * 0.01f) isYiChang = true;
            if(fuelRatio == Integer.parseInt("FFFF", 16) * 0.01f) isWuXiao = true;


        }

        //判断车辆位置
        if(bean.getCarPosition() == null){
            isKongZhi = true;
        }else{

            //---定位状态---
            int positionStatus = bean.getCarPosition().getPositionStatus();
            if(positionStatus == 0x01) isYiChang = true;


        }

        //判断极值数据
        if(bean.getExtremeData() == null){
            isKongZhi = true;
        }else{

            //---最高电压系统号---
            int highBatterySysNum = bean.getExtremeData().getHighBatterySysNum();
            if(highBatterySysNum < 0 || highBatterySysNum > 250) isFanWeiYiChang = true;
            if(highBatterySysNum == 0) isLingZhi = true;
            if(highBatterySysNum == 0xFE) isYiChang = true;
            if(highBatterySysNum == 0xFF) isWuXiao = true;

            //---最高单体电压号---
            int highBatteryNum = bean.getExtremeData().getHighBatteryNum();
            if(highBatteryNum < 0 || highBatteryNum > 250) isFanWeiYiChang = true;
            if(highBatteryNum == 0) isLingZhi = true;
            if(highBatteryNum == 0xFE) isYiChang = true;
            if(highBatteryNum == 0xFF) isWuXiao = true;

            //---电池单体电压最高值---
            float highBattery = bean.getExtremeData().getHighBattery();
            if(highBattery < 0 || highBattery > 15) isFanWeiYiChang = true;
            if(highBattery == 0) isLingZhi = true;
            if(highBattery == Integer.parseInt("FFFE", 16) * 0.001f) isYiChang = true;
            if(highBattery == Integer.parseInt("FFFF", 16) * 0.001f) isWuXiao = true;

            //---最低电压系统号---
            int lowBatterySysNum = bean.getExtremeData().getLowBatterySysNum();
            if(lowBatterySysNum < 0 || lowBatterySysNum > 250) isFanWeiYiChang = true;
            if(lowBatterySysNum == 0) isLingZhi = true;
            if(lowBatterySysNum == 0xFE) isYiChang = true;
            if(lowBatterySysNum == 0xFF) isWuXiao = true;

            //---最低单体电压号---
            int lowBatteryNum = bean.getExtremeData().getLowBatteryNum();
            if(lowBatteryNum < 0 || lowBatteryNum > 250) isFanWeiYiChang = true;
            if(lowBatteryNum == 0) isLingZhi = true;
            if(lowBatteryNum == 0xFE) isYiChang = true;
            if(lowBatteryNum == 0xFF) isWuXiao = true;

            //---电池单体电压最低值---
            float lowBattery = bean.getExtremeData().getLowBattery();
            if(lowBattery < 0 || lowBattery > 15) isFanWeiYiChang = true;
            if(lowBattery == 0) isLingZhi = true;
            if(lowBattery == Integer.parseInt("FFFE", 16) * 0.001f) isYiChang = true;
            if(lowBattery == Integer.parseInt("FFFF", 16) * 0.001f) isWuXiao = true;

            //---最高温度子系统---
            int highSysNum = bean.getExtremeData().getHighSysNum();
            if(highSysNum < 0 || highSysNum > 250) isFanWeiYiChang = true;
            if(highSysNum == 0) isLingZhi = true;
            if(highSysNum == 0xFE) isYiChang = true;
            if(highSysNum == 0xFF) isWuXiao = true;

            //---最高温度探针号---
            int highProbeNum = bean.getExtremeData().getHighProbeNum();
            if(highProbeNum < 0 || highProbeNum > 250) isFanWeiYiChang = true;
            if(highProbeNum == 0) isLingZhi = true;
            if(highProbeNum == 0xFE) isYiChang = true;
            if(highProbeNum == 0xFF) isWuXiao = true;

            //---最高温度值---
            int highTemp = bean.getExtremeData().getHighTemp();
            if(highTemp < -40 || highTemp > 210) isFanWeiYiChang = true;
            if(highTemp == 0) isLingZhi = true;
            if(highTemp == 0xFE) isYiChang = true;
            if(highTemp == 0xFF) isWuXiao = true;

            //---最低温度子系统---
            int lowSysNum = bean.getExtremeData().getLowSysNum();
            if(lowSysNum < 0 || highSysNum > 250) isFanWeiYiChang = true;
            if(lowSysNum == 0) isLingZhi = true;
            if(lowSysNum == 0xFE) isYiChang = true;
            if(lowSysNum == 0xFF) isWuXiao = true;

            //---最低温度探针号---
            int lowProbeNum = bean.getExtremeData().getLowProbeNum();
            if(lowProbeNum < 0 || highProbeNum > 250) isFanWeiYiChang = true;
            if(lowProbeNum == 0) isLingZhi = true;
            if(lowProbeNum == 0xFE) isYiChang = true;
            if(lowProbeNum == 0xFF) isWuXiao = true;

            //---最低温度值---
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
