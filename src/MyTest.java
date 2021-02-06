import business.HexDataResolver;
import business.MessageResolver;
import business.model.MessageBean;
import business.model.RawBean;
import com.alibaba.fastjson.JSON;

import java.io.File;
import java.util.List;

public class MyTest {

    public static void main(String[] args) {

        File file = new File("ll.log");
        List<RawBean> hexes = HexDataResolver.processRawLogFile(file);
        for(int i =0; i < hexes.size(); i++){
            System.out.println(hexes.get(i).getHexData());
            MessageBean bean = MessageResolver.resolve(hexes.get(i));
            System.out.println(JSON.toJSONString(bean, true));
        }

    }

}
