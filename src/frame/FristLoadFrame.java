package frame;

import exception.SendException;
import stcpream.AllBoundSetting;
import stcpream.AllStaticLocalSizeSet;
import stcpream.StaticSet;
import utils.LoadProperties;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * 第一次加载需要确认dll文件的安装
 */
public class FristLoadFrame extends JFrame {
    private static Logger logger = Logger.getLogger("加载dll");
    public FristLoadFrame(){
        super();
        this.init();
    }
    public void init(){
        this.setLayout(null);
        this.setBackground(Color.CYAN);
        this.setSize(AllBoundSetting.fristLoadSizeWidth, AllBoundSetting.fristLoadSizeHeight);
        //设置frame居于屏幕中央方式1
        this.setLocation((int) ((AllBoundSetting.screenWidth-this.getWidth())/2), (int) ((AllBoundSetting.screenHeight-this.getHeight())/2));
        //设置关闭
        this.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });
        //取消缩放
        setResizable(false);
        //隐藏上窗口
        this.setUndecorated(true);
        // 设置界面可见
        this.setVisible(true);
        JLabel label = new JLabel("检测到您java环境位置为:"+ StaticSet.JAVA_HOME);
        label.setBounds(20,20,600,30);
        this.add(label);

        JButton chosseButton=new JButton("更改");
        chosseButton.setBounds(200,50,60,30);
        chosseButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFileChooser chooser = new JFileChooser(StaticSet.JAVA_HOME);
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int i = chooser.showOpenDialog(null);
                switch (i){
                    case JFileChooser.CANCEL_OPTION:
                        break;
                    case JFileChooser.APPROVE_OPTION:
                        File selectedFile = chooser.getSelectedFile();
                        StaticSet.JAVA_HOME = selectedFile.getPath();
                        label.setText("您更改的路径为:"+StaticSet.JAVA_HOME);
                        break;
                    //如果发生错误或者该对话框已被解除
                    case JFileChooser.ERROR_OPTION :
                        break;
                }
            }
        });
        this.add(chosseButton);

        JButton button=new JButton("确定");
        button.setBounds(100,200,60,20);
        button.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //添加按钮
                try {
                    selectDllInit();
                    FristLoadFrame.this.setVisible(false);
                    MainJframe mainJframe = MainJframe.createMainJframe();
                    mainJframe.init();
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                    System.exit(0);
                } catch (IOException e1) {
                    e1.printStackTrace();
                    System.exit(0);
                } catch (Exception e2){
                    e2.printStackTrace();
                    System.exit(0);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                MainJframe mainJframe = MainJframe.createMainJframe();
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        this.add(button);
        this.setAlwaysOnTop(true);
    }

    /**
     * 拷贝文件
     * @param inpath 输入文件全路径
     * @param outpath 输出文件全路径
     * @throws IOException
     */
    public static void cpDll(String inpath,String outpath) throws IOException {
        //File file =new File(inpath);
        //加载文件
        //FileInputStream inputStream = new FileInputStream(file);
        InputStream inputStream = FristLoadFrame.class.getClassLoader().getResourceAsStream(inpath);
        File outFile = new File(outpath);
        File parentFile = outFile.getParentFile();
        if (parentFile.mkdirs()){
            logger.info("创建父类文件夹成功:"+parentFile.getPath());
        }
        if (outFile.createNewFile()){
            logger.info("创建文件成功:"+outpath);
        }
        //安装dll
        FileOutputStream outputFile = new FileOutputStream(outFile);
        while (true){
            byte[] data = new byte[999];
            int read = inputStream.read(data);
            if (read<=0){
                break;
            }
            outputFile.write(data);
        }
        outputFile.flush();
        inputStream.close();
        outputFile.close();
    }

    /**查询系统加载dll
     *
     * @throws IOException
     */
    public synchronized static void selectDllInit() throws IOException {

            Properties props=System.getProperties();
            String osName = props.getProperty("os.name"); //操作系统名称
            String osArch = props.getProperty("os.arch"); //操作系统构架
            String osVersion = props.getProperty("os.version"); //操作系统版本
            //64位系统
            switch (osArch){
                case "amd64":
                    cpDll(StaticSet.WINDOWS_64_RXTX_PARALLEK_PATH,StaticSet.getRxtxParallelPath());
                    cpDll(StaticSet.WINDOWS_64_RXTX_SERIAL_PATH,StaticSet.getRxtxSerialPath());
                    break;
                case "amd32":
                case "x86":
                    cpDll(StaticSet.WINDOWS_86_RXTX_PARALLEK_PATH,StaticSet.getRxtxParallelPath());
                    cpDll(StaticSet.WINDOWS_86_RXTX_SERIAL_PATH,StaticSet.getRxtxSerialPath());
                    break;
                default:
                    throw new SendException("不识别的系统，系统为:"+osName+",架构:"+osArch+",系统版本:"+osVersion,0);
            }


    }
}
