import business.HexDataResolver;
import business.MessageResolver;
import business.model.MessageBean;
import business.model.RawBean;
import com.alibaba.fastjson.JSON;

import java.io.File;
import java.util.List;

public class MyTest {

    public static void main(String[] args) {

//        File file = new File("ll.log");
//        List<RawBean> hexes = HexDataResolver.processRawLogFile(file);
//        for(int i =0; i < hexes.size(); i++){
//            System.out.println(hexes.get(i).getHexData());
//            MessageBean bean = MessageResolver.resolve(hexes.get(i));
//            System.out.println(JSON.toJSONString(bean, true));
//        }
        String s = "����״̬\n"
            + "���״̬\n"
            + "����ģʽ\n"
            + "����\n"
            + "�ۼ����\n"
            + "�ܵ�ѹ\n"
            + "�ܵ���\n"
            + "SOC\n"
            + "DC-DC״̬\n"
            + "��Ե����\n"
            + "����������\n"
            + "�������״̬\n"
            + "��������������¶�\n"
            + "�������ת��\n"
            + "�������ת��\n"
            + "��������¶�\n"
            + "��������������ѹ\n"
            + "���������ֱ��ĸ�ߵ���\n"
            + "������״̬\n"
            + "����ת��\n"
            + "ȼ��������\n"
            + "��λ״̬\n"
            + "��ߵ�ѹ��ϵͳ��\n"
            + "��ߵ�ѹ��ص������\n"
            + "��ص����ѹ���ֵ\n"
            + "��͵�ѹ�����ϵͳ��\n"
            + "��͵�ѹ��ص������\n"
            + "��ص����ѹ���ֵ\n"
            + "����¶���ϵͳ��\n"
            + "����¶�̽�����\n"
            + "����¶�ֵ\n"
            + "����¶���ϵͳ��\n"
            + "����¶�̽�����\n"
            + "����¶�ֵ";
        StringBuilder sb = new StringBuilder();
        for(String sp: s.split("\n")){
            sb.append("\"");
            sb.append(sp);
            sb.append("\"");
            sb.append(",");
        }

        System.out.println(sb.toString());


    }

}
