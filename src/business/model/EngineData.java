package business.model;

public class EngineData {

    //������״̬
    int EngineStatus;
    //����ת��
    int AxleSpeed;
    //ȼ��������
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
