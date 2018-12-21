package utils;


import entity.ButtonFunctionBean;
import exception.SendException;
import frame.HtmlJPanel;
import javafx.application.Platform;
import listener.com.PollingListener;
import temple.LeftModule;
import temple.MainComponent;
import temple.button.RightButton;
import temple.rightComponent.TechnicalDetailsComponent;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

/**
 * 读取properties
 */
public class LoadProperties {

    public static final String TECHNICAL_AREA_BUTTON="technicalAreaButtons";
    public static final String INDUSTRY_CATEGORY_BUTTON="industryCategoryButtons";
    public static final String MAIN_SWITCH_BUTTON="mainSwitchButton";
    private static Properties properties = new Properties();

    public static final String OPEN_VALUE = "open_";
    public static final String CLOSE_VALUE = "close_";
    public static final String DETAIL_VALUE = "detail_";
    public static final String OPEN_ALL = "openAll";
    public static final String CLOSE_ALL = "closeAll";

    public static final String pre_read_order = "4F 4B 21";
    // 使用ClassLoader加载properties配置文件生成对应的输入流

    private static InputStream in;
    //private static FileOutputStream outputFile;
    static {
        in = LoadProperties.class.getClassLoader().getResourceAsStream("button.properties");
        try {
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取子按钮
     * @param name
     * @return
     */
    public static String[] loadProperties(String name) {

        // 使用properties对象加载输入流
        String[] buttonnames = null;
        try {
            properties.load(in);
            String buttons = properties.getProperty(name);
            if (buttons != null) {
                buttonnames = buttons.split(",");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (buttonnames == null) {
            buttonnames = new String[]{};
        }
        return buttonnames;
    }

    /**
     * 添加按钮指令
     * @param buttonName
     * @param name
     * @param jButtons
     */
    public static void setOrder(String buttonName,String name,List<RightButton> jButtons){
        RightButton button=new RightButton(buttonName,name);

        jButtons.add(button);
        switch (name){
            //技术区
            case TECHNICAL_AREA_BUTTON:
                button.addActionListener((e) ->{
                    String order = properties.getProperty(DETAIL_VALUE + buttonName);
                    System.out.println("按钮：" + buttonName + "发送指令:" + order);
                    TechnicalDetailsComponent component = TechnicalDetailsComponent.createComponent(buttonName, order);
                    //设置
                    //component.placeComponents(buttonName,order);
                    //页面变为详情页
                    LeftModule.mainType = LeftModule.TECHNICAL_DETAIL;
                    component.setVisible(true);
                });
                button.addActionListener(e->{
                    Platform.runLater(()-> {
                        //获取指令信息
                        String order = properties.getProperty(DETAIL_VALUE + buttonName);
                        //格式化url
                        order=DefaultStringUtils.serizationUrl(order);
                        HtmlJPanel.chanText(order);
                        String url = HtmlJPanel.urlField.getText();
                        HtmlJPanel.fxBrowser.load(url);
                    });
                });
                break;
            //行业分类发送指令
            case INDUSTRY_CATEGORY_BUTTON:
                button.addActionListener((e) -> {
                    new Thread(){
                        @Override
                        public void run() {
                            super.run();
                            try {
                                if (button.isOpen){
                                    String order=properties.getProperty(CLOSE_VALUE+buttonName);
                                    System.out.println("按钮关闭："+buttonName+"发送指令:"+order);
                                    String s = PollingListener.sendData(DefaultStringUtils.hax16ByStr(order));
                                    if (s!=null&&s.startsWith(pre_read_order)){
                                        button.isOpen = false;
                                    }
                                }else {
                                    String order=properties.getProperty(OPEN_VALUE+buttonName);
                                    System.out.println("按钮开启："+buttonName+"发送指令:"+order);
                                    String s = PollingListener.sendData(DefaultStringUtils.hax16ByStr(order));
                                    if (s!=null&&s.startsWith(pre_read_order)){
                                        button.isOpen = true;
                                    }
                                }
                            }catch (Exception e1){
                                MainComponent.createComponent().showMessage("连接超时,请联系管理员", Color.magenta);
                                throw new SendException("指令解析失败,失败指令:"+buttonName,0);
                            }
                        }
                    }.start();
                });
                break;
            //总开关
            case MAIN_SWITCH_BUTTON:
                //总开关的设置按键指令
                String order=properties.getProperty(DETAIL_VALUE+buttonName);
                button.setButtonOrder(order);
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            String[] IndustryCategorys = LoadProperties.loadProperties(INDUSTRY_CATEGORY_BUTTON);
                            if (OPEN_ALL.equals(order)){
                                //关闭所有按钮的亮
                                new Thread(){
                                    @Override
                                    public void run() {
                                        super.run();
                                        Stream.of(IndustryCategorys)
                                                .map(button->OPEN_VALUE+button)
                                                .map(button->properties.getProperty(button))
                                                .peek(System.out::println)
                                                .forEach(button->{
                                                    try {
                                                        PollingListener.sendData(DefaultStringUtils.hax16ByStr(button));
                                                    }catch (SendException e1){
                                                        e1.printStackTrace();
                                                        MainComponent.createComponent().showMessage("连接超时,请联系管理员", Color.magenta);
                                                        return;
                                                    }  catch (Exception e1) {
                                                        e1.printStackTrace();
                                                        MainComponent.createComponent().showMessage(e1.getMessage(), Color.magenta);
                                                        return;
                                                    }
                                                });
                                    }
                                }.start();

                            }
                            if (CLOSE_ALL.equals(order)){
                                new Thread(){
                                    @Override
                                    public void run() {
                                        super.run();
                                        Arrays.stream(IndustryCategorys)
                                                .map(button->CLOSE_VALUE+button)
                                                .map(button->properties.getProperty(button))
                                                .peek(System.out::println)
                                                .forEach(button->{
                                                    try {
                                                        PollingListener.sendData(DefaultStringUtils.hax16ByStr(button));
                                                    } catch (SendException e1){
                                                        e1.printStackTrace();
                                                        MainComponent.createComponent().showMessage("连接超时,请联系管理员", Color.magenta);
                                                        return;
                                                    } catch (Exception e1) {
                                                        e1.printStackTrace();
                                                        MainComponent.createComponent().showMessage(e1.getMessage(), Color.magenta);
                                                        return;
                                                    }
                                                });
                                    }
                                }.start();

                            }
                        }catch (Exception e1){
                            MainComponent.createComponent().showMessage(e1.getMessage(), Color.magenta);
                            throw new SendException("指令解析失败,失败指令:"+buttonName,0);
                        }
                    }
                });
                break;
        }
    }
    //获取右侧按钮
    public static List<RightButton> inidButton(String name){
        List<RightButton> jButtons = new ArrayList<>();
        String[] buttonnames = loadProperties(name);
        //获取key对应的value值
        Arrays.stream(buttonnames).forEach(p->LoadProperties.setOrder(p,name,jButtons));
        return jButtons;
    }

    /**
     * 刷新流链接
     */
    private static  synchronized  void fulshAndClose(){
        try {
            FileOutputStream outputFile = new FileOutputStream("resourse/button.properties");
            properties.store(outputFile,"wh");
            outputFile.flush();
            outputFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //删除按钮
    public static synchronized void deleteButton(String mainButtonName,String leftButtonName){

        String leftButtonNames = properties.getProperty(mainButtonName);
        leftButtonNames = leftButtonNames.replaceAll(leftButtonName,"");
        leftButtonNames = leftButtonNames.replaceAll(",,",",");
        if (leftButtonNames.startsWith(",")){
            leftButtonNames = leftButtonNames.substring(1,leftButtonNames.length());
        }
        if (leftButtonNames.endsWith(",")){
            leftButtonNames = leftButtonNames.substring(0,leftButtonNames.length()-1);
        }
        properties.setProperty(mainButtonName,leftButtonNames);
        properties.remove(OPEN_VALUE+leftButtonName);
        properties.remove(CLOSE_VALUE+leftButtonName);
        properties.remove(DETAIL_VALUE+leftButtonName);
        fulshAndClose();
    }
    //添加按钮
    public static synchronized void saveButton(String mainButtonName,String leftButtonName,String openValue,String closeValue,String detailValue){
        if (DefaultStringUtils.isSpecialChar(mainButtonName)){
            throw new SendException("含有特殊字符",0);
        }
        if (DefaultStringUtils.isSpecialChar(leftButtonName)){
            throw new SendException("含有特殊字符",0);
        }
        //检测是否有重复
        AtomicReference<Boolean> flag = new AtomicReference<>(false);
        String[] strings = loadProperties(mainButtonName);
        if (strings==null){
            throw new SendException("没有子按钮",0);
        }
        Arrays.stream(strings)
                .filter(p->p.equals(leftButtonName))
                .forEach(p->{
                    flag.set(true);
                 });
        String leftButtonNames = properties.getProperty(mainButtonName);
        //第一次添加
        if (leftButtonNames==null){
            leftButtonNames = leftButtonName;
            //没有重复
        }else if (!flag.get()){
            leftButtonNames =leftButtonNames+","+ leftButtonName;
        }
        properties.setProperty(mainButtonName,leftButtonNames);
        if (openValue !=null){
            properties.setProperty(OPEN_VALUE+leftButtonName,openValue);
        }
        if (closeValue !=null){
            properties.setProperty(CLOSE_VALUE+leftButtonName,closeValue);
        }
        if (detailValue!=null){
            properties.setProperty(DETAIL_VALUE+leftButtonName,detailValue);
        }
        fulshAndClose();
    }
    //获取按钮对象
    public static ButtonFunctionBean getButtonFunctionBean(String mainName,String rightName){
        if (mainName==null){
            throw new SendException("不存在的按钮类型",0);
        }
        if (rightName==null){
            throw new SendException("不存在当前按钮",0);
        }
        ButtonFunctionBean functionBean =new ButtonFunctionBean();
        functionBean.setRightButtonName(rightName);
        switch (mainName){
            case TECHNICAL_AREA_BUTTON:
                functionBean.setMainComboBox("技术区");
                String detailValue = properties.getProperty(DETAIL_VALUE + rightName);
                detailValue = DefaultStringUtils.serizationUrl(detailValue);
                functionBean.setUrl(detailValue);
                break;

            case INDUSTRY_CATEGORY_BUTTON:
                functionBean.setMainComboBox("行业分类");
                //16位开启
                String openValue = properties.getProperty(OPEN_VALUE + rightName);
                //16位关闭
                String closeValue = properties.getProperty(CLOSE_VALUE + rightName);
                functionBean.setInternaitonClose16(closeValue);
                functionBean.setInternaitonOpen16(openValue);
                break;
            case MAIN_SWITCH_BUTTON:
                MainComponent.createComponent().showMessage("暂不支持修改",Color.magenta);
                throw new SendException("不支持的按钮类型",0);
            default:
                MainComponent.createComponent().showMessage("暂不支持修改",Color.magenta);
                throw new SendException("不存在的按钮类型",0);
        }
        return functionBean;
    }
    public static String[] technicalArea= {"分散式电采暖","皮带廊","轨道交通","储热锅炉","工业窑炉","电动汽车","热泵","电烤烟","机场桥载设备代替APU","电蓄冷空调","电制茶"
            ,"港口岸电","油田钻机\"油改电\"","农业电排灌","家庭电气化"};
    public static String[] industryCategory = {"农业","石油化工","电子电工","房地产","办公","电动汽车","水利水电","安全防护","家居用品","交通运输","环保绿化","物资","信息产业","办公文教"};
    public static String[] mainSwitch = {"全部开启","全部关闭"};

    static String html =
            "<html>基本原理：电热膜采暖技术，系统采用一种通电后能发热的半透明聚酯薄膜，" +
                    "有可导电的特制油墨、金属载流条经加工、热压在绝缘聚酯薄膜间制成。工作时以电热膜为发热体，" +
                    "将热量以辐射的形式送入空间，使人体和物体首先得到温暖，器综合效果优于传统的对流供暖方式。" +
                    "<img  src=\"img/back.jpg\"></img>"+
                    "<br/>特点：身体舒服，安全可靠、只能控制、无排放。" +
                    "<br/>使用领域：由于费用较高，目前推广范围集中在部分城市集中供暖无法覆盖的场所或不服高档小区</html>";
    static String html1 = /*"<html>\n" +
            "<meta charset=\"UTF-8\">\n" +
            "<title>Title</title>\n" +*/
            "    <div style=\"color: #FFFFFF; line-height: 200%;letter-spacing:3px;\">\n" +
            "        基本原理：电热膜采暖技术，系统采用一种通电后能发热的半透明聚酯薄膜，\n" +
            "        有可导电的特制油墨、金属载流条经加工、热压在绝缘聚酯薄膜间制成。工作时以电热膜为发热体，\n" +
            "        将热量以辐射的形式送入空间，使人体和物体首先得到温暖，器综合效果优于传统的对流供暖方式。\n" +
            "<image src=\"file:"+LoadProperties.class.getResource("/img/back.jpg").getPath()+"\" width=\"120\" height=\"80\"/>"+
            "        <br/>特点：身体舒服，安全可靠、只能控制、无排放。\n" +
            "        <br/>使用领域：由于费用较高，目前推广范围集中在部分城市集中供暖无法覆盖的场所或不服高档小区\n" +
            "    </div>\n";
            /* +
            "</html>";*/
    public static void main(String[] ar) throws IOException {
        for (String indust:technicalArea){
            saveButton(TECHNICAL_AREA_BUTTON,indust,null,null,"电热膜");
        }
        int i = 1;
        for (String indust:industryCategory){
            saveButton(INDUSTRY_CATEGORY_BUTTON,indust,"A5 5A 0 "+i,"A5 5A 1 "+i,null);
            i++;
        }
        saveButton(MAIN_SWITCH_BUTTON,"全部开启",null,null,OPEN_ALL);
        saveButton(MAIN_SWITCH_BUTTON,"全部关闭",null,null,CLOSE_ALL);
    }
}
