package business.model;

public class DianJiData {

    //电机序号
    int DianJiSerialNum;
    //电机状态
    int DianJiStatus;
    //电机控制器温度
    int DianJiControllerTemp;
    //电机转速
    int DianJiZhuanSu;
    //电机扭矩
    float DianJiNiuJu;
    //电机温度
    int DianJiTemp;
    //电机控制器输入电压
    float DianJiControllerDianYa;
    //电机控制器直流母线电流
    float DianJiControllerZhiLiuMuXianDianLiu;


    public int getDianJiSerialNum() {
        return DianJiSerialNum;
    }

    public void setDianJiSerialNum(int dianJiSerialNum) {
        DianJiSerialNum = dianJiSerialNum;
    }

    public int getDianJiStatus() {
        return DianJiStatus;
    }

    public void setDianJiStatus(int dianJiStatus) {
        DianJiStatus = dianJiStatus;
    }

    public int getDianJiControllerTemp() {
        return DianJiControllerTemp;
    }

    public void setDianJiControllerTemp(int dianJiControllerTemp) {
        DianJiControllerTemp = dianJiControllerTemp;
    }

    public int getDianJiZhuanSu() {
        return DianJiZhuanSu;
    }

    public void setDianJiZhuanSu(int dianJiZhuanSu) {
        DianJiZhuanSu = dianJiZhuanSu;
    }

    public float getDianJiNiuJu() {
        return DianJiNiuJu;
    }

    public void setDianJiNiuJu(float dianJiNiuJu) {
        DianJiNiuJu = dianJiNiuJu;
    }

    public int getDianJiTemp() {
        return DianJiTemp;
    }

    public void setDianJiTemp(int dianJiTemp) {
        DianJiTemp = dianJiTemp;
    }

    public float getDianJiControllerDianYa() {
        return DianJiControllerDianYa;
    }

    public void setDianJiControllerDianYa(float dianJiControllerDianYa) {
        DianJiControllerDianYa = dianJiControllerDianYa;
    }

    public float getDianJiControllerZhiLiuMuXianDianLiu() {
        return DianJiControllerZhiLiuMuXianDianLiu;
    }

    public void setDianJiControllerZhiLiuMuXianDianLiu(float dianJiControllerZhiLiuMuXianDianLiu) {
        DianJiControllerZhiLiuMuXianDianLiu = dianJiControllerZhiLiuMuXianDianLiu;
    }
}
