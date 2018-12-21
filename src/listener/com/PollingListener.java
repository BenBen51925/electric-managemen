package listener.com;

import com.henghao.comSerialPort.exception.ReadDataFromSerialPortFailure;
import com.henghao.comSerialPort.exception.SendDataToSerialPortFailure;
import com.henghao.comSerialPort.exception.SerialPortInputStreamCloseFailure;
import com.henghao.comSerialPort.exception.SerialPortOutputStreamCloseFailure;
import com.henghao.comSerialPort.main.InitiateComPort;
import com.henghao.comSerialPort.portManager.ByteUtils;
import com.henghao.comSerialPort.portManager.SerialPortManager;
import exception.SendException;
import frame.FristLoadFrame;
import frame.LodingJFrame;
import gnu.io.SerialPort;
import temple.MainComponent;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class PollingListener {

    private static SerialPort serialPort;

    private static boolean isOpen = false;
    private static String stcReadStr;

    public static String getStcReadStr() {
        return stcReadStr;
    }

    public static void setStcReadStr(String stcReadStr) {
        PollingListener.stcReadStr = stcReadStr;
    }

    public static SerialPort getSerialPort(){
        return serialPort;
    }
    static {
        long millis = 1200;
        while (serialPort==null){
            try {
                opSerialPort();
            }catch (Exception e){
                e.printStackTrace();
            }
            //串口打开失败
            if (serialPort == null){
                try {
                    System.err.println("串口打开失败"+millis/1000+"秒后将会重试");
                    MainComponent.createComponent().showMessage("串口打开失败"+millis/1000+"秒后将会重试",Color.RED);
                    Thread.sleep(millis);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
    //开启端口
    public static synchronized void opSerialPort(){
        if (isOpen){
            return;
        }
        // 获取当前所有串口
        ArrayList<String> portName = null;
        boolean flagWin = false;
        while (!flagWin){
            try{
                portName = SerialPortManager.findPort();
                flagWin = true;
            }catch (UnsatisfiedLinkError e){
                //加载dll失败
                e.printStackTrace();
                try {
                    //重新加载dll
                    FristLoadFrame.selectDllInit();
                } catch (IOException e1) {
                    e1.printStackTrace();
                    System.exit(0);
                } catch (Exception e2){
                    e2.printStackTrace();
                    System.exit(0);
                }

            }
        }
        String portNameString = null;
        if (portName.size() > 0) {
            for (int i = 0, length = portName.size(); i < length; i++) {
                portNameString = portName.get(i);
                System.out.println("获取串口成功--> 串口" + (i + 1) + "为："
                        + portNameString);
            }
        } else {
            System.out.println("<-----没有可用串口----->");
        }
        // 打开串口
        serialPort = InitiateComPort.openPort(portNameString);
        if (serialPort!=null){
            isOpen = true;
        }
    }
    //关闭端口
    @Deprecated
    public static synchronized void clSerialPort(){
        if (isOpen){
            serialPort.close();
            isOpen = false;
        }
    }
    // 往串口发送消息并接收数据
    public static String sendData(byte[] date) {
        System.out.println("正在发送.....");
        String readStr = null;
        LodingJFrame lodingJFrame =new LodingJFrame(1000*12);
        lodingJFrame.init();
        try {
            if (serialPort==null){
                throw new SendException("串口连接失败,请检查线路是否连接正常",0);
            }
            SerialPortManager.sendToPort(serialPort, date);
            System.out.println("校验后数据:" + date+"--->发送成功");

            long time =0;
            while(stcReadStr == null){
                if (time>1000*12){
                    System.err.println("接收数据超时:"+time);
                    throw new SendException("接收数据超时"+time,0);
                }
                Thread.sleep(50l);
                time +=50l;
                lodingJFrame.current +=50;
                if (stcReadStr!=null){
                    readStr = stcReadStr;
                    lodingJFrame.current = lodingJFrame.getAmount()-1;
                    MainComponent.createComponent().showMessage("发送成功",Color.green);
                    System.out.println("接收数据:" + readStr+",耗时:"+time);
                    //延迟一下
                    Thread.sleep(300l);
                    lodingJFrame.current++;
                }
            }
        } catch (SendDataToSerialPortFailure e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SerialPortOutputStreamCloseFailure e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            stcReadStr =null;
        }
        return readStr;
    }
    public static byte[][] lingt ={
            {90,90,0,1}, {90,90,0,2},{90,90,0,3},{90,90,0,4},
            {90,90,0,5}, {90,90,0,6},{90,90,0,7},{90,90,0,8},
            {90,90,0,9}, {90,90,0,10},{90,90,0,11},{90,90,0,12},
            {90,90,0,13}, {90,90,0,14},{90,90,0,15},{90,90,0,16},
            {90,90,0,17}, {90,90,0,18},{90,90,0,19},{90,90,0,20},
            {90,90,0,21},{90,90,0,22}
    };

    public static byte[][] black ={
            {90,90,1,1}, {90,90,1,2},{90,90,1,3},{90,90,1,4},
            {90,90,1,5}, {90,90,1,6},{90,90,1,7},{90,90,1,8},
            {90,90,1,9}, {90,90,1,10},{90,90,1,11},{90,90,1,12},
            {90,90,1,13}, {90,90,1,14},{90,90,1,15},{90,90,1,16},
            {90,90,1,17}, {90,90,1,18},{90,90,1,19},{90,90,1,20},
            {90,90,1,21},{90,90,1,22},{90,90,1,23}
    };
    public static void main(String[] args) {
        // 发送数据
        try {
            while (true){
                for (byte i = 0;i<23;i++){
                    if (i%2!=0){
                       //System.out.println("发送第:"+i);
                        //opSerialPort();
                        sendData(new byte[]{90,90,1,i});
                    }
                    if (i%2==0){
                        sendData(new byte[]{90,90,0,i});
                    }
                }
                for (byte i = 0;i<23;i++){
                    if (i%2==0){
                        System.out.println("发送第:"+i);
                        sendData(new byte[]{90,90,1,i});
                    }
                    if (i%2!=0){
                        System.out.println("发送第:"+i);
                        sendData(new byte[]{90,90,0,i});
                    }
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            SerialPortManager.closePort(serialPort);
            e.printStackTrace();
        }
    }
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        if (serialPort!=null){
            //关闭所有的设备
            //TODO it
            serialPort.close();
        }
    }
}
