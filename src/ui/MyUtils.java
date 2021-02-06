package ui;

import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyUtils {

    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    public static void centerComponentOnScreen(Component component){
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        component.setLocation((screenSize.width-component.getWidth())/2, (screenSize.height-component.getHeight())/2);
    }

    public static Date str2Date(String str){
        try {
            return format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            System.err.println(str);
        }
        return new Date(0);
    }

    public static int dateSecondDiff(Date d1, Date d2){
        return (int) ((d1.getTime() - d2.getTime())/1000);
    }

    public static boolean isValidTime(Date d){
        return d.getTime() > 0;
    }

}
