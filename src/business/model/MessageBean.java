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

    //采集时间
    int year;
    int month;
    int day;
    int hour;
    int minute;
    int second;
    Date collectTime;

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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public Date getCollectTime() {
        return collectTime;
    }

    public void setCollectTime(Date collectTime) {
        this.collectTime = collectTime;
    }

    public boolean isTimeValid(){
      return year+2000 >= 2011 && month >= 1 && month <= 12 && day >= 1 && day <= 31 &&
          hour >= 0 && hour <= 23 && minute >=0 && minute < 60 && second >= 0 && second < 60;

    }
}
