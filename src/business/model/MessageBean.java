package business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MessageBean {

    //消息时间
    Date msgDate;
    String msgDateStr;

    //消息上传时间
    Date gbDate;
    String gbDateStr;

    /*---报文开始---*/
    //起始符
    String beginSign;
    //命令标识
    int commandCode;
    //应答标志
    int responseCode;
    //唯一识别码
    String vin;
    //加密方式
    int encryptType;
    //数据单元长度
    int dataLen;

    TotalCarData totalCarData;
    List<DianJiData> dianJiDataList;
    EngineData engineData;
    CarPosition carPosition;
    ExtremeData extremeData;
    WarningData warningData;

    public EngineData getEngineData() {
        return engineData;
    }

    public void setEngineData(EngineData engineData) {
        this.engineData = engineData;
    }

    public CarPosition getCarPosition() {
        return carPosition;
    }

    public void setCarPosition(CarPosition carPosition) {
        this.carPosition = carPosition;
    }

    public ExtremeData getExtremeData() {
        return extremeData;
    }

    public void setExtremeData(ExtremeData extremeData) {
        this.extremeData = extremeData;
    }

    public WarningData getWarningData() {
        return warningData;
    }

    public void setWarningData(WarningData warningData) {
        this.warningData = warningData;
    }

    public Date getMsgDate() {
        return msgDate;
    }

    public void setMsgDate(Date msgDate) {
        this.msgDate = msgDate;
    }

    public String getMsgDateStr() {
        return msgDateStr;
    }

    public void setMsgDateStr(String msgDateStr) {
        this.msgDateStr = msgDateStr;
    }

    public Date getGbDate() {
        return gbDate;
    }

    public void setGbDate(Date gbDate) {
        this.gbDate = gbDate;
    }

    public String getGbDateStr() {
        return gbDateStr;
    }

    public void setGbDateStr(String gbDateStr) {
        this.gbDateStr = gbDateStr;
    }

    public String getBeginSign() {
        return beginSign;
    }

    public void setBeginSign(String beginSign) {
        this.beginSign = beginSign;
    }

    public int getCommandCode() {
        return commandCode;
    }

    public void setCommandCode(int commandCode) {
        this.commandCode = commandCode;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public int getEncryptType() {
        return encryptType;
    }

    public void setEncryptType(int encryptType) {
        this.encryptType = encryptType;
    }

    public int getDataLen() {
        return dataLen;
    }

    public void setDataLen(int dataLen) {
        this.dataLen = dataLen;
    }

    public TotalCarData getTotalCarData() {
        return totalCarData;
    }

    public void setTotalCarData(TotalCarData totalCarData) {
        this.totalCarData = totalCarData;
    }

    public List<DianJiData> getDianJiDataList() {
        return dianJiDataList;
    }

    public void setDianJiDataList(List<DianJiData> dianJiDataList) {
        this.dianJiDataList = dianJiDataList;
    }

    public boolean isLogin(){
        return commandCode == 0x01;
    }

    public boolean isLogout(){
        return commandCode == 0x04;
    }

}
