package entity;


public class ButtonFunctionBean {
    //主界面分类
    private String mainComboBox;
    //按钮名称
    private String rightButtonName;
    /**
     * open开机16进制字符
     */
    private String internaitonOpen16;
    /**
     * close16位信息
     */
    private  String internaitonClose16;
    /**
     * url
     */
    private  String url;

    public String getMainComboBox() {
        return mainComboBox;
    }

    public void setMainComboBox(String mainComboBox) {
        this.mainComboBox = mainComboBox;
    }

    public String getRightButtonName() {
        return rightButtonName;
    }

    public void setRightButtonName(String rightButtonName) {
        this.rightButtonName = rightButtonName;
    }

    public String getInternaitonOpen16() {
        return internaitonOpen16;
    }

    public void setInternaitonOpen16(String internaitonOpen16) {
        this.internaitonOpen16 = internaitonOpen16;
    }

    public String getInternaitonClose16() {
        return internaitonClose16;
    }

    public void setInternaitonClose16(String internaitonClose16) {
        this.internaitonClose16 = internaitonClose16;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
