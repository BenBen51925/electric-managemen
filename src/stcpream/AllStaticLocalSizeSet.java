package stcpream;

import defaultDB.XmlDb;
import defaultDB.implDb.ComplateXmlDb;
import frame.MainJframe;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import temple.MainComponent;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.File;
import java.io.IOException;

@Deprecated
public class AllStaticLocalSizeSet {
    //第一次加载dll
    private static int fristLoadSizeWidth =400;
    private static int fristLoadSizeHeight =300;
    //主页面
    private static MainJframe mainJframe;
    private static java.awt.Toolkit toolkit=java.awt.Toolkit.getDefaultToolkit();
    private static Dimension screenSize=toolkit.getScreenSize();
    private static double screenWidth=screenSize.getWidth();
    private static double screenHeight=screenSize.getHeight();
    private static Integer mainSizeWidth = (int) screenWidth *2/3;
    private static Integer mainSizeHeight = (int) screenHeight*2/3;
    private static int mainJFrameScreamX = (int)(AllStaticLocalSizeSet.screenWidth-mainSizeWidth)/2;
    private static int mainJFrameScreamY = (int)(AllStaticLocalSizeSet.screenHeight-mainSizeHeight)/2;

    //MainJframe.mainSizeWidth 整个页面宽度
    //左侧主页面大小
    private static int leftSizeWidthModel = mainSizeWidth*22/100;
    private static int leftSizeHighModel = mainSizeHeight;

    //右侧容器大小
    private static int rightOutComponentSizeWidth = mainSizeWidth * 70/100;
    private static int rightOutComponentSizeHeight = mainSizeHeight * 65/100;
    //位置
    private static int rightOutComponentLocalX = leftSizeWidthModel;
    private static int rightOutComponentLocalY = leftSizeHighModel * 18/100;
    private static int returnButtonSizeWidth = rightOutComponentSizeWidth * 20/100;
    private static int returnButtonSizeHeight =rightOutComponentSizeHeight * 9/100;
    private static int returnButtonLocalX = rightOutComponentSizeWidth - returnButtonSizeWidth;
    private static int returnButtonLocalY = 0;

    //右侧主窗口
    //商标
    private static JLabel mainComponentIconLabel = new JLabel();
    private static Icon mainIcon = new ImageIcon(MainComponent.class.getResource("/img/icon.png"));
    private static int mainComponentIconLocalX = AllStaticLocalSizeSet.mainSizeWidth * 7/100;
    private static int mainComponentIconLocalY = rightOutComponentLocalY/3;
    private static int mainComponentIconSizeWidth=mainIcon.getIconWidth();
    private static int mainComponentIconSizeHeight=mainIcon.getIconHeight();

    //右侧中层容器大小
    private static int rightMiddleSizeWidth = rightOutComponentSizeWidth * 90/100;
    private static int rightMiddleSizeHeight =rightOutComponentSizeHeight * 90/100;
    //位置
    private static int rightMiddleLocalX = rightOutComponentSizeWidth * 5/100;
    private static int rightMiddleLocalY = rightOutComponentSizeHeight * 5/100;

    //右侧最上层容器大小
    private static int rightComponentSizeWidth = rightMiddleSizeWidth;
    private static int rightComponentSizeHeight = rightMiddleSizeHeight;
    //位置
    private static int rightComponentLocalX = 0;
    private static int rightComponentLocalY = 0;

    //布局行数量
    private static int buttonRowNum = 5;
    //布局列数量
    private static int buttonColNum = 3;
    //组件之间的水平间距
    private static int buttonHgap = rightComponentSizeWidth / (buttonColNum*3-1);
    //组件之间的垂直间距
    private static int buttonVgap = rightComponentSizeHeight / (buttonRowNum*2-1);
    //左侧界面布局
    private static GridLayout gridLayout=new GridLayout(buttonRowNum,buttonColNum);

    //主按钮大小位置
    //按钮大小宽度
    private static int mainButtonSizeWidth = leftSizeWidthModel * 3/5;
    //按钮大小高度
    private static int mainButtonSizeHight = leftSizeHighModel * 18/100;
    //按钮x位置
    private static int mainButtonPathX = mainSizeWidth * 7/100;
    //按钮y轴位置
    private static int mainButtonPathY = mainSizeHeight * 18/100;
    //后两个按钮y轴距离
    private static int mainButtonPathInterval = (rightOutComponentSizeHeight - (3*mainButtonSizeHight))/2;
    //主按钮图标
    private static int iconLabelSizeWidth = mainButtonSizeWidth*2/3;
    private static int iconLabelSizeHeight = mainButtonSizeHight*2/3;
    private static int iconLabelLocalX =(mainButtonSizeWidth-iconLabelSizeWidth)/2;
    private static int iconLabelLocalY =(mainButtonSizeHight-iconLabelSizeHeight)/2;

    public static void main(String[] ar){

    }
    public static void bounds(){
        ComplateXmlDb complateXmlDb = new ComplateXmlDb();
    }
}
