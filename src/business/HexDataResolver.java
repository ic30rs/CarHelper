package business;

import business.model.RawBean;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HexDataResolver {

    public static final String TIME_REGEX = "message time :([0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9][\\s]+[0-9][0-9][:][0-9][0-9][:][0-9][0-9])";
    public static final String TIME_REGEX2 = "gbcenter time:([0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9][\\s]+[0-9][0-9][:][0-9][0-9][:][0-9][0-9])";
    public static final String REGEX = "hexString:([0-9a-fA-F]{2}[\\s]?)+";

    public static List<RawBean> processRawLogFile(File file){
        List<RawBean> ret = new ArrayList<>();
        Pattern r = Pattern.compile(REGEX);
        Pattern r1 = Pattern.compile(TIME_REGEX);
        Pattern r2 = Pattern.compile(TIME_REGEX2);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while((line = reader.readLine()) != null){
                Matcher m = r.matcher(line);
                if(m.find()){
                    Matcher m1 = r1.matcher(line);
                    String match = m.group(0);
                    String hex = match.split(":")[1];

                    if(m1.find()){
                        int index = m1.group(0).indexOf(":");
                        String time = m1.group(0).substring(index+1);
                        Matcher m2 = r2.matcher(line);
                        if(m2.find()){
                            index = m2.group(0).indexOf(":");
                            String time2 = m2.group(0).substring(index+1);
                            ret.add(new RawBean(time, time2, hex));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }


}
