import frame.FristLoadFrame;

import frame.HtmlJPanel;
import frame.MainJframe;

import stcpream.StaticSet;

import java.io.File;

import java.util.Properties;

public class Main {
    public static String osName;
    public static String osArch;
    public static String osVersion;
    static {
        try
        {
            Properties props=System.getProperties(); //获得系统属性集
            osName = props.getProperty("os.name"); //操作系统名称
            osArch = props.getProperty("os.arch"); //操作系统构架
            osVersion = props.getProperty("os.version"); //操作系统版本
            System.err.println("操作系统名称:"+osName);
            System.err.println("操作系统架构:"+osArch);
            System.err.println("操作系统版本:"+osVersion);

            //初始化浏览器
            HtmlJPanel htmlJPanel = HtmlJPanel.createComponent();
            htmlJPanel.init();
            //htmlJPanel.loadUrl("www.baidu.com");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        try {

            File rxtxParallel = new File(StaticSet.getRxtxParallelPath());
            File rxtxSerial = new File(StaticSet.getRxtxSerialPath());
            String java_home = System.getProperty("java.home");
            System.out.println("java环境:"+java_home);
            if (rxtxParallel.exists()&&rxtxSerial.exists()){
                MainJframe mainJframe = MainJframe.createMainJframe();
                mainJframe.init();
            }else {
                FristLoadFrame fristLoadFrame =new FristLoadFrame();
            }
        }catch (Exception e){
            e.printStackTrace();
            System.exit(0);
        }

    }
}