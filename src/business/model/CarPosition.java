package business.model;

public class CarPosition {

    //��λ״̬
    int PositionStatus;
    //����
    long Longitude;
    //γ��
    long latitude;

    public int getPositionStatus() {
        return PositionStatus;
    }

    public void setPositionStatus(int positionStatus) {
        PositionStatus = positionStatus;
    }

    public long getLongitude() {
        return Longitude;
    }

    public void setLongitude(long longitude) {
        Longitude = longitude;
    }

    public long getLatitude() {
        return latitude;
    }

    public void setLatitude(long latitude) {
        this.latitude = latitude;
    }
}
