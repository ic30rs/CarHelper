package business.model;

public class WarningData {

    //最高报警等级
    int HighWarningLevel;
    //通用报警标志
    String  WarningSign;

    public int getHighWarningLevel() {
        return HighWarningLevel;
    }

    public void setHighWarningLevel(int highWarningLevel) {
        HighWarningLevel = highWarningLevel;
    }

    public String getWarningSign() {
        return WarningSign;
    }

    public void setWarningSign(String  warningSign) {
        WarningSign = warningSign;
    }
}
