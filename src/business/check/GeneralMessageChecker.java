package business.check;

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
        if(bean.getTotalCarData() == null){
            return false;
        }

        boolean isYiChang = false;
        boolean isWuXiao = false;
        boolean isKongZhi = false;
        boolean isLingZhi = false;
        boolean isFanWeiYiChang = false;

        //---判断车辆状态---
        int carStatus = bean.getTotalCarData().getCarStatus();
        if(carStatus == 0xFE) isYiChang = true;
        if(carStatus == 0xFF) isWuXiao = true;
        //------------------------------
        int chargeStatus = bean.getTotalCarData().getChargeStatus();
        if(chargeStatus == 0xFE) isYiChang = true;
        if(chargeStatus == 0xFF) isWuXiao = true;

        if(carStatus != 0x02){//不是停车状态
            //判断空值的方法
            if(bean.getTotalCarData() == null){
                isKongZhi = true;
            }else{
                TotalCarData totalCarData = bean.getTotalCarData();
                float speed = totalCarData.getVelocity();
                //判断范围异常
                if(speed < 0 || speed > 220) isFanWeiYiChang = true;
                //判断零值
                if(speed == 0) isLingZhi = true;
                //对于之前转换过的小数，我们把FE FF按照一样的方法转换，判断是否相等
                if(speed == Integer.parseInt("FFFE", 16) * 0.1f) isYiChang = true;
                if(speed == Integer.parseInt("FFFF", 16) * 0.1f) isWuXiao = true;
            }
        }else{//是停车状态

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
}
