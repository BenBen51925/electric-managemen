package stcpream;

import action.interfaceActionMatch.ChangeSizeActionFulsh;
import defaultDB.implDb.ComplateXmlDb;
import entity.ComponentBean;
import frame.MainJframe;
import temple.MainComponent;
import temple.mainComponent.TopIconLabel;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class AllBoundSetting   {

    private static ComplateXmlDb<ComponentBean> complateXmlDb =new ComplateXmlDb();
    //主页面
    private static ComponentBean mainComponent;
    //上部图标
    public static String TOP_ICO_COMPONENT_NAME="上部图标";
    private static ComponentBean topIcoLabelComponent;
    //上部标题
    public static String TOP_TEXT_COMPONENT_NAME="上部标题";
    private static ComponentBean topTextLabelComponent;
    //下部标题
    public static String DOWN_TEXT_COMPONENT_NAME="下部标题";
    private static ComponentBean downTextLabelComponent;
    //右侧窗口
    public static String RIGHT_OUT_COMPONENT_NAME="右侧外层主界面";
    private static ComponentBean rightOutComponent;
    //加载xml数据
    static {
        mainComponent = complateXmlDb.getByName("mainComponent", complateXmlDb.MAIN_COMPONENT_TYPE);
        topIcoLabelComponent = complateXmlDb.getByName(TOP_ICO_COMPONENT_NAME, ComplateXmlDb.TOP_ICON_LABEL_COMPONENT_TYPE);
        topTextLabelComponent = complateXmlDb.getByName(TOP_TEXT_COMPONENT_NAME, ComplateXmlDb.TOP_TEXT_LABEL_COMPONENT_TYPE);
        downTextLabelComponent = complateXmlDb.getByName(DOWN_TEXT_COMPONENT_NAME, ComplateXmlDb.DOWN_TEXT_LABEL_COMPONENT_TYPE);
        rightOutComponent = complateXmlDb.getByName(RIGHT_OUT_COMPONENT_NAME, ComplateXmlDb.RIGHT_COMPONENT_TYPE);

        if (mainComponent!=null){
            mainSizeWidth = mainComponent.getSizeWidth();
            mainSizeHeight = mainComponent.getSizeHeight();
        }
        if (topIcoLabelComponent!=null){
            mainComponentIconLocalX =topIcoLabelComponent.getLocalX();
            mainComponentIconLocalY = topIcoLabelComponent.getLocalY();
            mainComponentIconSizeHeight=topIcoLabelComponent.getSizeHeight();
            mainComponentIconSizeWidth=topIcoLabelComponent.getSizeWidth();
        }
        if (topTextLabelComponent!=null){
            mainComponentTextLocalX = topTextLabelComponent.getLocalX();
            mainComponentTextLocalY = topTextLabelComponent.getLocalY();
        }
        if (downTextLabelComponent!=null){
            downTextLocalX = downTextLabelComponent.getLocalX();
            downTextLocalY = downTextLabelComponent.getLocalY();
        }
        if (rightOutComponent!=null){
            rightOutComponentLocalX = rightOutComponent.getLocalX();
            rightOutComponentLocalY = rightOutComponent.getLocalY();
            rightOutComponentSizeHeight = rightOutComponent.getSizeHeight();
            rightOutComponentSizeWidth = rightOutComponent.getSizeWidth();
        }
    }
    //第一次加载dll
    public static int fristLoadSizeWidth =400;
    public static int fristLoadSizeHeight =300;
    //主页面
    public static MainJframe mainJframe;
    private static Toolkit toolkit= java.awt.Toolkit.getDefaultToolkit();
    private static Dimension screenSize=toolkit.getScreenSize();
    public static final double screenWidth=screenSize.width;
    public static final double screenHeight=screenSize.height;
    //------------------------------------------------------------------------------

    private static Integer mainSizeWidth;
    public static Integer getMainSizeWidth(){
        if (mainSizeWidth!=null){
            return mainSizeWidth;
        }
        return (int) screenWidth;
    }
    public static void setMainSizeWidth(Integer mainSizeWidth){
        AllBoundSetting.mainSizeWidth = mainSizeWidth;
    }
    private static Integer mainSizeHeight;
    public static Integer getMainSizeHeight(){
        if (mainSizeHeight!=null){
            return mainSizeHeight;
        }
        return (int) screenHeight;
    }
    public static void setMainSizeHeight(Integer mainSizeHeight){
        AllBoundSetting.mainSizeHeight = mainSizeHeight;
    }
    private static Integer mainJFrameScreamX;
    public static Integer getMainJFrameScreamX (){
        if (mainJFrameScreamX!=null){
            return mainJFrameScreamX;
        }
        return (int)(AllBoundSetting.screenWidth-getMainSizeWidth())/2;
    }
    public static void setMainJFrameScreamX(Integer mainJFrameScreamX){
        AllBoundSetting.mainJFrameScreamX = mainJFrameScreamX;
    }
    private static int mainJFrameScreamY;
    public static Integer getMainJFrameScreamY (){
        if (mainJFrameScreamX!=null){
            return mainJFrameScreamX;
        }
        return (int)(AllBoundSetting.screenHeight-getMainSizeHeight())/2;
    }
    public static void setMainJFrameScreamY(Integer mainJFrameScreamY){
        AllBoundSetting.mainJFrameScreamY = mainJFrameScreamY;
    }
    //MainJframe.mainSizeWidth 整个页面宽度
    //左侧主页面大小
    private static Integer leftSizeWidthModel;
    public static Integer getLeftSizeWidthModel(){
        if (leftSizeWidthModel!=null){
            return leftSizeWidthModel;
        }
        return getMainSizeWidth()*22/100;
    }
    private static Integer leftSizeHighModel;
    public static Integer getLeftSizeHighModel(){
        if (leftSizeHighModel!=null){
            return leftSizeHighModel;
        }
        return getMainSizeHeight();
    }

    //右侧容器大小
    private static Integer rightOutComponentSizeWidth;
    public static Integer getRightOutComponentSizeWidth(){
        if (rightOutComponentSizeWidth!=null){
            return rightOutComponentSizeWidth;
        }
        return getMainSizeWidth()*70/100;
    }
    public static void setRightOutComponentSizeWidth(Integer rightOutComponentSizeWidth){
        AllBoundSetting.rightOutComponentSizeWidth = rightOutComponentSizeWidth;
    }
    private static Integer rightOutComponentSizeHeight;
    public static Integer getRightOutComponentSizeHeight(){
        if (rightOutComponentSizeHeight!=null){
            return rightOutComponentSizeHeight;
        }
        return getMainSizeHeight() * 65/100;
    }
    public static void setRightOutComponentSizeHeight(Integer rightOutComponentSizeHeight){
        AllBoundSetting.rightOutComponentSizeHeight = rightOutComponentSizeHeight;
    }
    //位置
    private static Integer rightOutComponentLocalX;
    public static Integer getRightOutComponentLocalX(){
        if (rightOutComponentLocalX!=null){
            return rightOutComponentLocalX;
        }
        return getLeftSizeWidthModel();
    }
    public static void setRightOutComponentLocalX(Integer rightOutComponentLocalX){
        AllBoundSetting.rightOutComponentLocalX = rightOutComponentLocalX;
    }
    private static Integer rightOutComponentLocalY;
    public static Integer getRightOutComponentLocalY(){
        if (rightOutComponentLocalY!=null){
            return rightOutComponentLocalY;
        }
        return getLeftSizeHighModel() * 18/100;
    }
    public static void setRightOutComponentLocalY(Integer rightOutComponentLocalY){
        AllBoundSetting.rightOutComponentLocalY = rightOutComponentLocalY;
    }
    /**
     * 返回按钮---------------------------------------
     */


    private static Integer returnButtonSizeHeight;
    public static Integer getReturnButtonSizeHeight(){
        if (returnButtonSizeHeight !=null){
            return returnButtonSizeHeight;
        }
        return getRightOutComponentSizeHeight() * 6/100;

    }
    private static Integer returnButtonSizeWidth;
    public static Integer getReturnButtonSizeWidth(){
        if (returnButtonSizeWidth!=null){
            return returnButtonSizeWidth;
        }

        return getReturnButtonSizeHeight() * 110/36;
    }
    private static Integer returnButtonLocalX;
    public static Integer getReturnButtonLocalX(){
        if (returnButtonLocalX !=null){
            return returnButtonLocalX;
        }
        return getRightOutComponentSizeWidth()*95 / 100 - getReturnButtonSizeWidth();
    }
    private static Integer returnButtonLocalY;
    public static Integer getReturnButtonLocalY(){
        if (returnButtonLocalY !=null){
            return returnButtonLocalY;
        }
        return getReturnButtonSizeHeight() * 50/100;
    }

    //右侧主窗口
    //商标
    //public static TopIconLabel mainComponentIconLabel;
    public static URL mainIconUrl=MainComponent.class.getResource("/img/icon.png");
    public static Icon mainIcon = new ImageIcon(mainIconUrl);
    private static Integer mainComponentIconLocalX;
    public static Integer getMainComponentIconLocalX(){
        if (mainComponentIconLocalX !=null){
            return mainComponentIconLocalX;
        }
        return AllBoundSetting.getMainSizeWidth() * 7/100;
    }
    private static Integer mainComponentIconLocalY;
    public static Integer getMainComponentIconLocalY(){
        if (mainComponentIconLocalY !=null){
            return mainComponentIconLocalY;
        }
        return getRightOutComponentLocalY()/3;
    }
    private static Integer mainComponentIconSizeWidth;
    public static Integer getMainComponentIconSizeWidth(){
        if (mainComponentIconSizeWidth !=null){
            return mainComponentIconSizeWidth;
        }
        return mainIcon.getIconWidth();
    }
    public static void setMainComponentIconSizeWidth(Integer mainComponentIconSizeWidth){
        AllBoundSetting.mainComponentIconSizeWidth = mainComponentIconSizeWidth;
    }
    private static Integer mainComponentIconSizeHeight;
    public static Integer getMainComponentIconSizeHeight(){
        if (mainComponentIconSizeHeight !=null){
            return mainComponentIconSizeHeight;
        }
        return mainIcon.getIconHeight();
    }
    public static void setMainComponentIconSizeHeight(Integer mainComponentIconSizeHeight){
        AllBoundSetting.mainComponentIconSizeHeight = mainComponentIconSizeHeight;
    }
    //上部标题
    public static JLabel mainComponentTextLabel = new JLabel();
    private static String fountName;
    public static String getFountName(){
        if (fountName!=null){
            return fountName;
        }
        return "微软雅黑";
    }
    private static Integer fountStyle;
    public static Integer getFountStyle(){
        if (fountStyle!=null){
            return fountStyle;
        }
        return 0;
    }
    private static Integer fountSize;
    public static Integer getFountSize(){
        if (fountSize!=null){
            return fountSize;
        }
        return 32;
    }
    private static Integer mainComponentTextLocalY;
    public static Integer getMainComponentTextLocalY(){
        if (mainComponentTextLocalY!=null){
            return mainComponentTextLocalY;
        }
        return null;
    }
    public static void setMainComponentTextLocalY(Integer mainComponentTextLocalY){
        AllBoundSetting.mainComponentTextLocalY = mainComponentTextLocalY;
    }
    private static Integer mainComponentTextLocalX;
    public static Integer getMainComponentTextLocalX(){
        if (mainComponentTextLocalX!=null){
            return mainComponentTextLocalX;
        }
        return null;
    }
    public static void setMainComponentTextLocalX(Integer mainComponentTextLocalX){
        AllBoundSetting.mainComponentTextLocalX = mainComponentTextLocalX;
    }
    //下部标题
    //上部标题
    public static JLabel downTextLabel = new JLabel();
    private static Integer downTextLocalX;
    public static Integer getDownTextLocalX(){
        if (downTextLocalX!=null){
            return downTextLocalX;
        }
        return null;
    }
    public static void setDownTextLocalX(Integer downTextLocalX){
        AllBoundSetting.downTextLocalX = downTextLocalX;
    }
    private static Integer downTextLocalY;
    public static Integer getDownTextLocalY(){
        if (downTextLocalY!=null){
            return downTextLocalY;
        }
        return null;
    }
    public static void setDownTextLocalY(Integer downTextLocalY){
        AllBoundSetting.downTextLocalY = downTextLocalY;
    }
    //右侧中层容器大小
    @Deprecated
    private static Integer rightMiddleSizeWidth;
    @Deprecated
    public static Integer getRightMiddleSizeWidth(){
        if (rightMiddleSizeWidth !=null){
            return rightMiddleSizeWidth;
        }
        return  getRightOutComponentSizeWidth() * 90/100;
    }
    @Deprecated
    private static Integer rightMiddleSizeHeight;
    @Deprecated
    public static Integer getRightMiddleSizeHeight(){
        if (rightMiddleSizeHeight !=null){
            return rightMiddleSizeHeight;
        }
        return getRightOutComponentSizeHeight() * 90/100;
    }
    //位置
    @Deprecated
    private static Integer rightMiddleLocalX;
    @Deprecated
    public static Integer getRightMiddleLocalX(){
        if (rightMiddleLocalX !=null){
            return rightMiddleLocalX;
        }
        return getRightOutComponentSizeWidth() * 5/100;
    }
    @Deprecated
    private static Integer rightMiddleLocalY;
    @Deprecated
    public static Integer getRightMiddleLocalY(){
        if (rightMiddleLocalY !=null){
            return rightMiddleLocalY;
        }
        return getRightOutComponentSizeHeight() * 5/100;
    }

    //右侧最上层容器大小
    private static Integer rightComponentSizeWidth;
    public static Integer getRightComponentSizeWidth(){
        if (rightComponentSizeWidth !=null){
            return rightComponentSizeWidth;
        }
        return getRightMiddleSizeWidth();
    }
    private static Integer rightComponentSizeHeight;
    public static Integer getRightComponentSizeHeight(){
        if (rightComponentSizeHeight !=null){
            return rightComponentSizeHeight;
        }
        return getRightMiddleSizeHeight();
    }
    //位置
    private static Integer rightComponentLocalX;
    public static Integer getRightComponentLocalX(){
        if (rightComponentLocalX !=null){
            return rightComponentLocalX;
        }
        return getRightOutComponentSizeWidth() * 5/100;
    }
    private static Integer rightComponentLocalY;
    public static Integer getRightComponentLocalY(){
        if (rightComponentLocalY !=null){
            return rightComponentLocalY;
        }
        return getRightOutComponentSizeHeight() * 5/100;
    }

    //布局行数量
    private static Integer buttonRowNum;
    public static Integer getButtonRowNum(){
        if (buttonRowNum !=null){
            return buttonRowNum;
        }
        return 5;
    }
    //布局列数量
    private static Integer buttonColNum;
    public static Integer getButtonColNum(){
        if (buttonColNum !=null){
            return buttonColNum;
        }
        return 3;
    }
    //组件之间的水平间距
    private static Integer buttonHgap;
    public static Integer getButtonHgap(){
        if (buttonHgap !=null){
            return buttonHgap;
        }
        return getRightComponentSizeWidth() /(getButtonColNum()*3-1);
    }
    //组件之间的垂直间距
    private static Integer buttonVgap;
    public static Integer getButtonVgap(){
        if (buttonVgap !=null){
            return buttonVgap;
        }
        return getRightComponentSizeHeight() / (getButtonRowNum()*2-1);
    }
    //左侧界面布局
    public static GridLayout gridLayout=new GridLayout(getButtonRowNum(),getButtonColNum());
    //主按钮大小位置
    //按钮大小宽度
    private static Integer mainButtonSizeWidth;
    public static Integer getMainButtonSizeWidth(){
        if (mainButtonSizeWidth !=null){
            return mainButtonSizeWidth;
        }
        return getLeftSizeWidthModel() * 3/5;
    }
    public static void setMainButtonSizeWidth(Integer mainButtonSizeWidth){
        AllBoundSetting.mainButtonSizeWidth =mainButtonSizeWidth;
    }
    //按钮大小高度
    private static Integer mainButtonSizeHight;
    public static Integer getMainButtonSizeHight(){
        if (mainButtonSizeHight !=null){
            return mainButtonSizeHight;
        }
        return getLeftSizeHighModel() * 18/100;
    }
    public static void setMainButtonSizeHight(Integer mainButtonSizeHight){
        AllBoundSetting.mainButtonSizeHight =mainButtonSizeHight;
    }
    //按钮x位置
    private static Integer mainButtonPathX;
    public static Integer getMainButtonPathX(){
        if (mainButtonPathX !=null){
            return mainButtonPathX;
        }
        return getMainSizeWidth() * 7/100;
    }
    //按钮y轴位置
    private static Integer mainButtonPathY;
    public static Integer getMainButtonPathY(){
        if (mainButtonPathY !=null){
            return mainButtonPathY;
        }
        return getMainSizeHeight()* 18/100;
    }
    //后两个按钮y轴距离
    private static Integer mainButtonPathInterval;
    public static Integer getMainButtonPathInterval(){
        if (mainButtonPathInterval !=null){
            return mainButtonPathInterval;
        }
        return (getRightOutComponentSizeHeight() - (3*getMainButtonSizeHight()))/2;
    }
    //主按钮图标
    private static Integer iconLabelSizeWidth;
    public static Integer getIconLabelSizeWidth(){
        if (iconLabelSizeWidth !=null){
            return iconLabelSizeWidth;
        }
        return  getMainButtonSizeWidth()*2/3;
    }
    private static Integer iconLabelSizeHeight;
    public static Integer getIconLabelSizeHeight(){
        if (iconLabelSizeHeight !=null){
            return iconLabelSizeHeight;
        }
        return  getMainButtonSizeHight()*2/3;
    }
    private static Integer iconLabelLocalX;
    public static Integer getIconLabelLocalX(){
        if (iconLabelLocalX !=null){
            return iconLabelLocalX;
        }
        return  (getMainButtonSizeWidth()-getIconLabelSizeWidth())/2;
    }
    private static Integer iconLabelLocalY;
    public static Integer getIconLabelLocalY(){
        if (iconLabelLocalY !=null){
            return iconLabelLocalY;
        }
        return  (getMainButtonSizeHight()-getIconLabelSizeHeight())/2;
    }
    public static void main(String[] ar){

    }
    public static void bounds(){
        ComplateXmlDb complateXmlDb = new ComplateXmlDb();
    }
}
