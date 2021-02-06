package business;

import business.model.*;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class MessageResolver {

    public static List<MessageBean> resolve(List<RawBean> rawBeans){
        List<MessageBean> beans = new ArrayList<>();
        rawBeans.forEach(rawBean -> beans.add(resolve(rawBean)));
        return beans;
    }

    public static MessageBean resolve(RawBean rawBean){
        MessageBean bean = new MessageBean();

        bean.setMsgDate(rawBean.getMsgDate());
        bean.setMsgDateStr(rawBean.getMsgDateStr());
        bean.setGbDate(rawBean.getGbDate());
        bean.setGbDateStr(rawBean.getGbDateStr());


        //����ͷ��

        //����2323 ��仰��������
        //��һ�� ȡ��rawBean.getHexAt(0,1)����Ϊ ��2323�� ����ַ���
        //�ڶ��� hexStr2Byte �� 2323 ת��Ϊ��������ʽ
        //������ ��������ת���� String ��##�� ���� bean
        //����תΪString�Ķ������պ�«��ư
        bean.setBeginSign(new String(hexStr2Byte(rawBean.getHexAt(0,1))));
        //���������ʶ
        //����ת�����Ŀ����պ�«��ư
        bean.setCommandCode(Integer.parseInt(rawBean.getHexAt(2,2), 16));
        bean.setResponseCode(Integer.parseInt(rawBean.getHexAt(3,3), 16));
        bean.setVin(new String(hexStr2Byte(rawBean.getHexAt(4,20))));
        bean.setEncryptType(Integer.parseInt(rawBean.getHexAt(21,21), 16));
        bean.setDataLen(Integer.parseInt(rawBean.getHexAt(22,23), 16));

        if(bean.getCommandCode() == 0x01){//��������

        }else if(bean.getCommandCode() == 0x04){//�����ǳ�

        }else if(bean.getCommandCode() == 0x02 || bean.getCommandCode() == 0x03){//�ϱ�
            int nowOffset = 24 + 6;//��ǰ��ȡ���ڼ����ֽ��ˣ���6����Ϊ��Ҫ�ܿ����ݲɼ�ʱ�䣬��7.2.1��7
            while(nowOffset < rawBean.getHexDataByteCount() - 1){//��һ��Ϊ�����У����
                int dataType = Integer.parseInt(rawBean.getHexAt(nowOffset,nowOffset), 16);
                nowOffset ++;
                //���ݽ���������dataType �жϱ��Ĵ�����ʲô���� ��7.2.2��8
                if(dataType == 0x01){//��������
                    TotalCarData totalCarData = new TotalCarData();
                    totalCarData.setCarStatus(Integer.parseInt(rawBean.getHexAt(nowOffset,nowOffset), 16));
                    totalCarData.setChargeStatus(Integer.parseInt(rawBean.getHexAt(nowOffset+1,nowOffset+1), 16));
                    totalCarData.setRunStatus(Integer.parseInt(rawBean.getHexAt(nowOffset+2,nowOffset+2), 16));
                    //���٣�����С����
                    totalCarData.setVelocity(0.1f * Integer.parseInt(rawBean.getHexAt(nowOffset+3,nowOffset+4), 16));
                    totalCarData.setTotalDistance(0.1f * Long.parseLong(rawBean.getHexAt(nowOffset+5,nowOffset+8), 16));
                    totalCarData.setZongDianYa(0.1f * Integer.parseInt(rawBean.getHexAt(nowOffset+9,nowOffset+10), 16));
                    totalCarData.setZongDianLiu(-1000+0.1f * Integer.parseInt(rawBean.getHexAt(nowOffset+11,nowOffset+12), 16));
                    totalCarData.setSOC(Integer.parseInt(rawBean.getHexAt(nowOffset+13,nowOffset+13), 16));
                    totalCarData.setDC_DC_Status(Integer.parseInt(rawBean.getHexAt(nowOffset+14,nowOffset+14), 16));
                    totalCarData.setDangWei(Integer.parseInt(rawBean.getHexAt(nowOffset+15,nowOffset+15), 16));
                    totalCarData.setJueYuanDianZu(Integer.parseInt(rawBean.getHexAt(nowOffset+16,nowOffset+17), 16));
                    totalCarData.setReserve(Integer.parseInt(rawBean.getHexAt(nowOffset+18,nowOffset+19), 16));
                    bean.setTotalCarData(totalCarData);
                    nowOffset += 19;
                }else if(dataType == 0x02){//�����������
                    int dianJiCount = Integer.parseInt(rawBean.getHexAt(nowOffset,nowOffset), 16);
                    List<DianJiData> dianJiDataList = new ArrayList<>();
                    for(int i = 0; i < dianJiCount; i++){
                        DianJiData data = new DianJiData();
                        data.setDianJiSerialNum(Integer.parseInt(rawBean.getHexAt(nowOffset+1,nowOffset+1), 16));
                        data.setDianJiStatus(Integer.parseInt(rawBean.getHexAt(nowOffset+2,nowOffset+2), 16));
                        data.setDianJiControllerTemp(-40+Integer.parseInt(rawBean.getHexAt(nowOffset+3,nowOffset+3), 16));
                        data.setDianJiZhuanSu(-20000+Integer.parseInt(rawBean.getHexAt(nowOffset+4,nowOffset+5), 16));
                        data.setDianJiNiuJu(-2000+0.1f * Integer.parseInt(rawBean.getHexAt(nowOffset+6,nowOffset+7), 16));
                        data.setDianJiTemp(-40+Integer.parseInt(rawBean.getHexAt(nowOffset+8,nowOffset+8), 16));
                        data.setDianJiControllerDianYa(0.1f * Integer.parseInt(rawBean.getHexAt(nowOffset+9,nowOffset+10), 16));
                        data.setDianJiControllerZhiLiuMuXianDianLiu(-1000+0.1f * Integer.parseInt(rawBean.getHexAt(nowOffset+11,nowOffset+12), 16));
                        dianJiDataList.add(data);
                        nowOffset += 12;
                    }
                    bean.setDianJiDataList(dianJiDataList);
                }else if(dataType == 0x04){//����������
                    EngineData engineData = new EngineData();
                    engineData.setEngineStatus(Integer.parseInt(rawBean.getHexAt(nowOffset,nowOffset),16));
                    engineData.setAxleSpeed(Integer.parseInt(rawBean.getHexAt(nowOffset+1,nowOffset+2),16));
                    engineData.setFuelRatio(0.01f * Integer.parseInt(rawBean.getHexAt(nowOffset+3,nowOffset+4), 16));
                    nowOffset += 4;
                    bean.setEngineData(engineData);

                }else if(dataType == 0x05){//����λ������
                    CarPosition carPosition = new CarPosition();
                    carPosition.setPositionStatus(Integer.parseInt(rawBean.getHexAt(nowOffset,nowOffset),16));
                    carPosition.setLatitude(Long.parseLong(rawBean.getHexAt(nowOffset+1,nowOffset+4), 16));
                    carPosition.setLongitude(Long.parseLong(rawBean.getHexAt(nowOffset+5,nowOffset+8), 16));//δ��10��6�η�
                    nowOffset += 8;
                    bean.setCarPosition(carPosition);

                }else if(dataType == 0x06){//��ֵ����
                    ExtremeData extremeData = new ExtremeData();
                    extremeData.setHighBatterySysNum(Integer.parseInt(rawBean.getHexAt(nowOffset,nowOffset),16));
                    extremeData.setHighBatteryNum(Integer.parseInt(rawBean.getHexAt(nowOffset+1,nowOffset+1),16));
                    extremeData.setHighBattery(0.001f * Integer.parseInt(rawBean.getHexAt(nowOffset+2,nowOffset+3), 16));
                    extremeData.setLowBatterySysNum(Integer.parseInt(rawBean.getHexAt(nowOffset+4,nowOffset+4),16));
                    extremeData.setLowBatteryNum(Integer.parseInt(rawBean.getHexAt(nowOffset+5,nowOffset+5),16));
                    extremeData.setLowBattery(0.001f * Integer.parseInt(rawBean.getHexAt(nowOffset+6,nowOffset+7), 16));
                    extremeData.setHighSysNum(Integer.parseInt(rawBean.getHexAt(nowOffset+8,nowOffset+8),16));
                    extremeData.setHighProbeNum(Integer.parseInt(rawBean.getHexAt(nowOffset+9,nowOffset+9),16));
                    extremeData.setHighTemp(-40+Integer.parseInt(rawBean.getHexAt(nowOffset+10,nowOffset+10),16));
                    extremeData.setLowSysNum(Integer.parseInt(rawBean.getHexAt(nowOffset+11,nowOffset+11),16));
                    extremeData.setLowProbeNum(Integer.parseInt(rawBean.getHexAt(nowOffset+12,nowOffset+12),16));
                    extremeData.setLowTemp(-40+Integer.parseInt(rawBean.getHexAt(nowOffset+13,nowOffset+13),16));
                    nowOffset += 13;
                    bean.setExtremeData(extremeData);

                }else if(dataType == 0x07){//�������ݣ�ֻ�����涨�ģ���������
                    WarningData warningData = new WarningData();
                    warningData.setHighWarningLevel(Integer.parseInt(rawBean.getHexAt(nowOffset,nowOffset),16));
                    warningData.setWarningSign(new String(hexStr2Byte(rawBean.getHexAt(nowOffset+1,nowOffset+4))));
                    nowOffset += 5;
                    
                    int n1 = Integer.parseInt(rawBean.getHexAt(nowOffset,nowOffset),16);
                    if(n1 == 0xFE || n1 == 0xFF){
                        n1 = 0;
                    }
                    nowOffset += (4 * n1 + 1);

                    int n2 = Integer.parseInt(rawBean.getHexAt(nowOffset,nowOffset),16);
                    if(n2 == 0xFE || n2 == 0xFF){
                        n2 = 0;
                    }
                    nowOffset += (4 * n2 + 1);

                    int n3 = Integer.parseInt(rawBean.getHexAt(nowOffset,nowOffset),16);
                    if(n3 == 0xFE || n3 == 0xFF){
                        n3 = 0;
                    }
                    nowOffset += (4 * n3 + 1);

                    int n4 = Integer.parseInt(rawBean.getHexAt(nowOffset,nowOffset),16);
                    if(n4 == 0xFE || n4 == 0xFF){
                        n4 = 0;
                    }
                    nowOffset += (4 * n4);
                    
                    bean.setWarningData(warningData);

                }else if(dataType == 0x08){//����08�Զ������ݣ����ǲ�ȥ������ֱ������
                    int size = Integer.parseInt(rawBean.getHexAt(nowOffset,nowOffset+1),16);
                    nowOffset += (size +1);
                } else {
                    System.out.println("���ڲ�֧�ֵ����ݸ�ʽ "+dataType);
                }
                nowOffset ++;
            }
        }



        return bean;
    }

    private static byte[] hexStr2Byte(String hex) {
        ByteBuffer bf = ByteBuffer.allocate(hex.length() / 2);
        for (int i = 0; i < hex.length(); i++) {
            String hexStr = hex.charAt(i) + "";
            i++;
            hexStr += hex.charAt(i);
            byte b = (byte) Integer.parseInt(hexStr, 16);
            bf.put(b);
        }
        return bf.array();
    }

}
