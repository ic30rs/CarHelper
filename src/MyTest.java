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
        String s = "车辆状态\n"
            + "充电状态\n"
            + "运行模式\n"
            + "车速\n"
            + "累计里程\n"
            + "总电压\n"
            + "总电流\n"
            + "SOC\n"
            + "DC-DC状态\n"
            + "绝缘电阻\n"
            + "驱动电机序号\n"
            + "驱动电机状态\n"
            + "驱动电机控制器温度\n"
            + "驱动电机转速\n"
            + "驱动电机转矩\n"
            + "驱动电机温度\n"
            + "电机控制器输入电压\n"
            + "电机控制器直流母线电流\n"
            + "发动机状态\n"
            + "曲轴转速\n"
            + "燃料消耗率\n"
            + "定位状态\n"
            + "最高电压子系统号\n"
            + "最高电压电池单体代号\n"
            + "电池单体电压最高值\n"
            + "最低电压电池子系统号\n"
            + "最低电压电池单体代号\n"
            + "电池单体电压最低值\n"
            + "最高温度子系统号\n"
            + "最高温度探针序号\n"
            + "最高温度值\n"
            + "最低温度子系统号\n"
            + "最低温度探针序号\n"
            + "最低温度值";
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
