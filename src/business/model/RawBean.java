package business.model;

import ui.MyUtils;

import java.security.InvalidParameterException;
import java.util.Date;

public class RawBean {

    //��Ϣʱ��
    Date msgDate;

    String msgDateStr;

    //��Ϣ�ϴ�ʱ��
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
     * ��ʮ�����ƴ�ĳ��λ�ÿ�ʼ��������ȡ���м�����ɸ��ֽ�
     * ���� AA BB CC DD EE FF
     * ����getHex(0, 0) ����AA
     * ����getHex(0, 1) ����AA BB
     * ����getHex(2, 5) ����CC DD EE FF
     * @param begin ��ʼλ��
     * @param end ����λ��
     * @return ��ȡ�����ɸ��ֽڵ�16�����ַ���
     */
    public String getHexAt(int begin, int end){
        if(end < 0 || begin < 0 || begin > end){
            throw new InvalidParameterException("ȡ��ʮ��������ʱ�������� " + begin +" "+end);
        }
        if(begin >= splitData.length || end >= splitData.length){
            throw new InvalidParameterException("ȡ��ʮ��������ʱ�����˷�Χ " + begin + " " + end + " len:"+splitData.length);
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
