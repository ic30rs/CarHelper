package business.model;

public class TotalCarData {

    /*---01整车数据---*/
    //车辆状态
    int carStatus;
    //充电状态
    int chargeStatus;
    //运行状态
    int runStatus;
    //速度
    float velocity;
    //总里程
    float totalDistance;
    //总电压
    float ZongDianYa;
    //总电流
    float ZongDianLiu;
    //SOC
    int SOC;
    //DC-DC状态
    int DC_DC_Status;
    //挡位
    int DangWei;
    //绝缘电阻
    int JueYuanDianZu;
    //预留
    int reserve;

    public int getCarStatus() {
        return carStatus;
    }

    public void setCarStatus(int carStatus) {
        this.carStatus = carStatus;
    }

    public int getChargeStatus() {
        return chargeStatus;
    }

    public void setChargeStatus(int chargeStatus) {
        this.chargeStatus = chargeStatus;
    }

    public int getRunStatus() {
        return runStatus;
    }

    public void setRunStatus(int runStatus) {
        this.runStatus = runStatus;
    }

    public float getVelocity() {
        return velocity;
    }

    public void setVelocity(float velocity) {
        this.velocity = velocity;
    }

    public float getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(float totalDistance) {
        this.totalDistance = totalDistance;
    }

    public float getZongDianYa() {
        return ZongDianYa;
    }

    public void setZongDianYa(float zongDianYa) {
        ZongDianYa = zongDianYa;
    }

    public float getZongDianLiu() {
        return ZongDianLiu;
    }

    public void setZongDianLiu(float zongDianLiu) {
        ZongDianLiu = zongDianLiu;
    }

    public int getSOC() {
        return SOC;
    }

    public void setSOC(int SOC) {
        this.SOC = SOC;
    }

    public int getDC_DC_Status() {
        return DC_DC_Status;
    }

    public void setDC_DC_Status(int DC_DC_Status) {
        this.DC_DC_Status = DC_DC_Status;
    }

    public int getDangWei() {
        return DangWei;
    }

    public void setDangWei(int dangWei) {
        DangWei = dangWei;
    }

    public int getJueYuanDianZu() {
        return JueYuanDianZu;
    }

    public void setJueYuanDianZu(int jueYuanDianZu) {
        JueYuanDianZu = jueYuanDianZu;
    }

    public int getReserve() {
        return reserve;
    }

    public void setReserve(int reserve) {
        this.reserve = reserve;
    }

}
