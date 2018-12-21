package stcpream;

import defaultDB.implDb.ComplateXmlDb;

import java.net.URL;

public class StaticSet {

    public static final String MAIN_FRAME_TITLE = "国家电网";    //标题
    public static final String OUT_ICON = "/img/out_icon.png";                  //窗口标题icon
    public static final String CHANGE_SIZE_BUTTON = "/img/箭头.png";                  //改变大小图形
    public static final String CHANGE_SIZE_MOUSE = "/img/箭头.gif";                  //改变大小鼠标图形
    public static final String FRAME_BACKETGROUND = "/img/frame_backgroup.png";//大背景图
    public static final String OUT_COMPONENT_BACKETGROUND = "/img/out_component_backetground.png"; //外层图片
    public static final int FONT_SIZE = 16; //字体大小
    private static URL resourceAsStream = ComplateXmlDb.class.getClassLoader().getResource("");
    private static String rootPath = resourceAsStream.getPath();

    public static String JAVA_HOME = System.getProperty("java.home");
    //获取javahome/bing文件夹
    public static String getJaveHomeBin(){
        String outpath = StaticSet.JAVA_HOME + "\\bin\\";
        return outpath;
    }
    public static final String WINDOWS_64_RXTX_PARALLEK_PATH ="x64/rxtxParallel.dll";
    public static final String WINDOWS_64_RXTX_SERIAL_PATH ="x64/rxtxSerial.dll";

    public static final String WINDOWS_86_RXTX_PARALLEK_PATH ="x86/rxtxParallel.dll";
    public static final String WINDOWS_86_RXTX_SERIAL_PATH ="x86/rxtxSerial.dll";
    //获取windos rxtxParallel.dll文件位置
    public static String getRxtxParallelPath(){
        return getJaveHomeBin()+"rxtxParallel.dll";
    }
    //获取windos rxtxSerial.dll文件位置
    public static String getRxtxSerialPath(){
        return getJaveHomeBin()+"rxtxSerial.dll";
    }
    //文件根目录
    public static String getRootPath(){
        return rootPath;
    }
    //详情页地址
    public static String getDetail(){
        return rootPath+"/detail/";
    }
}
