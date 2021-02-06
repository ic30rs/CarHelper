package business.model;

import ui.MyUtils;

import java.security.InvalidParameterException;
import java.util.Date;

public class RawBean {

    //消息时间
    Date msgDate;

    String msgDateStr;

    //消息上传时间
    Date gbDate;

    String gbDateStr;

    String hexData;

    String[] splitData;

    public RawBean(String time, String time2, String hexData){
        this.hexData = hexData;
        msgDate = MyUtils.str2Date(time);
        msgDateStr = time;
        gbDate = MyUtils.str2Date(time2);
        gbDateStr = time2;
        splitData = hexData.trim().split(" ");
    }

    /**
     * 从十六进制串某个位置开始到结束，取出中间的若干个字节
     * 例如 AA BB CC DD EE FF
     * 调用getHex(0, 0) 返回AA
     * 调用getHex(0, 1) 返回AA BB
     * 调用getHex(2, 5) 返回CC DD EE FF
     * @param begin 开始位置
     * @param end 结束位置
     * @return 所取的若干个字节的16进制字符串
     */
    public String getHexAt(int begin, int end){
        if(end < 0 || begin < 0 || begin > end){
            throw new InvalidParameterException("取出十六进制数时参数错误 " + begin +" "+end);
        }
        if(begin >= splitData.length || end >= splitData.length){
            throw new InvalidParameterException("取出十六进制数时超出了范围 " + begin + " " + end + " len:"+splitData.length);
        }
        StringBuffer sb = new StringBuffer((end-begin+1)*2);
        for(int i = 0; i <= end-begin; i++){
            sb.append(splitData[begin + i]);
        }
        return sb.toString();
    }

    public int getHexDataByteCount(){
        return splitData.length;
    }

    public Date getMsgDate() {
        return msgDate;
    }

    public String getHexData() {
        return hexData;
    }

    public String getMsgDateStr() {
        return msgDateStr;
    }

    public Date getGbDate() {
        return gbDate;
    }

    public String getGbDateStr() {
        return gbDateStr;
    }
}
