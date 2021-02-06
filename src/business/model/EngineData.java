package business.model;

public class EngineData {

    //发动机状态
    int EngineStatus;
    //曲轴转速
    int AxleSpeed;
    //燃料消耗率
    float FuelRatio;

    public int getEngineStatus() {
        return EngineStatus;
    }

    public void setEngineStatus(int engineStatus) {
        EngineStatus = engineStatus;
    }

    public int getAxleSpeed() {
        return AxleSpeed;
    }

    public void setAxleSpeed(int axleSpeed) {
        AxleSpeed = axleSpeed;
    }

    public float getFuelRatio() {
        return FuelRatio;
    }

    public void setFuelRatio(float fuelRatio) {
        FuelRatio = fuelRatio;
    }
}
