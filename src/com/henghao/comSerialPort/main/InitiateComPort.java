package com.henghao.comSerialPort.main;

import com.henghao.comSerialPort.exception.*;
import com.henghao.comSerialPort.portManager.ByteUtils;
import com.henghao.comSerialPort.portManager.CRC16;
import com.henghao.comSerialPort.portManager.SerialPortManager;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import listener.com.PollingListener;

import java.util.ArrayList;

public class InitiateComPort {

    private static SerialPort serialPort;

    public static void main(String[] args) throws InterruptedException {
        // 获取当前所有串口
        ArrayList<String> portName = SerialPortManager.findPort();
        String portNameString = null;
        if (portName.size() > 0) {
            for (int i = 0, length = portName.size(); i < length; i++) {
                portNameString = portName.get(i);
                System.out.println("获取串口成功--> 串口" + (i + 1) + "为："
                        + portNameString);
            }
        } else {
            System.out.println("<-----没有可用串口----->");
            return;
        }
        // 打开串口
        serialPort = InitiateComPort.openPort(portNameString);
        if (serialPort == null) {
            return;
        }
        // 发送数据
        try {
            // 查询设备开关
            // String msgString = "01450100";
            // 打开所有设备开关
//            String msgString = "0147080101010101010101";
            // 关闭所有设备开关
            //String msgString = "5A A5 01 01";
            //System.out.println("发送到串口的消息为：" + msgString);
            byte[] chars  = {0x5A,0x05,0x00,0x01};
            InitiateComPort.sendData(chars);
            //InitiateComPort.sendData(chars);
            //InitiateComPort.sendData(msgString.toCharArray().toString());

            InitiateComPort.readData();
            InitiateComPort.readData();
            InitiateComPort.readData();
            InitiateComPort.readData();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            SerialPortManager.closePort(serialPort);
            e.printStackTrace();
        }finally {
            Thread.sleep(2000l);
            serialPort.close();
        }
    }


    // 打开串口
    public static SerialPort openPort(String portNameString) {
        try {
            serialPort = SerialPortManager.openPort(portNameString, 9600);
            SerialListener serialListener = new SerialListener();

            if (serialPort != null) {
                try {
                    SerialPortManager.addListener(serialPort,
                            serialListener);
                } catch (TooManyListeners e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        } catch (SerialPortParameterFailure e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NotASerialPort e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchPort e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (PortInUse e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return serialPort;
    }
    // 往串口发送消息
    public static void sendData(byte[] date) {
        System.out.println("正在发送.....");
        try {
            SerialPortManager.sendToPort(serialPort, date);
            System.out.println("校验后数据" + date + "发送成功");
        } catch (SendDataToSerialPortFailure e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SerialPortOutputStreamCloseFailure e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // 往串口发送消息
    public static void sendData(String str) {
        System.out.println("正在发送.....");
        byte[] data = ByteUtils.hexStr2Byte(str);
        String str1 = CRC16.Make_CRC(data);
        String data1 = (str + str1).toUpperCase();
        byte[] data2 = ByteUtils.hexStr2Byte(data1);
        try {
            SerialPortManager.sendToPort(serialPort, data2);
            System.out.println("校验后数据" + data1 + "发送成功");
        } catch (SendDataToSerialPortFailure e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SerialPortOutputStreamCloseFailure e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // 接收串口中的数据
    public static void readData() {
        try {
            byte[] bytes = SerialPortManager.readFromPort(serialPort);
            System.out.println("接收数据:"+bytes);
        } catch (ReadDataFromSerialPortFailure e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SerialPortInputStreamCloseFailure e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static class SerialListener implements SerialPortEventListener {
        /**
         * 处理监控到的串口事件
         */
        @Override
        public void serialEvent(SerialPortEvent serialPortEvent) {

            switch (serialPortEvent.getEventType()) {

                case SerialPortEvent.BI: // 10 通讯中断
                    System.out.println("与串口设备通讯中断");
                    break;

                case SerialPortEvent.OE: // 7 溢位（溢出）错误
                    System.out.println("串口溢出");

                case SerialPortEvent.FE: // 9 帧错误
                    System.out.println("串口帧错误");

                case SerialPortEvent.PE: // 8 奇偶校验错误
                    System.out.println("串口奇偶校验错误");

                case SerialPortEvent.CD: // 6 载波检测
                    System.out.println("串口载波检测");

                case SerialPortEvent.CTS: // 3 清除待发送数据
                    System.out.println("清除待发送数据");

                case SerialPortEvent.DSR: // 4 待发送数据准备好了
                    System.out.println("待发送数据准备好了");
                case SerialPortEvent.RI: // 5 振铃指示
                    System.out.println("振铃指示");

                case SerialPortEvent.OUTPUT_BUFFER_EMPTY: // 2 输出缓冲区已清空
                    System.out.println("输出缓冲区已清空");
                    break;

                case SerialPortEvent.DATA_AVAILABLE: // 1 串口存在可用数据
                    byte[] data = null;
                    try {
                        if (serialPort == null) {
                            System.out.println("串口对象为空！监听失败！");
                        } else {
                            // 读取串口数据
                            System.out.println("正在接收串口中的数据......");
                            Thread.sleep(100);
                            data = SerialPortManager.readFromPort(serialPort);
                            String data1 = ByteUtils.byteArrayToHexString(data,
                                    true);
                            System.out.println("返回数据为：" + data1 + "    ");
                            //设置接收数据
                            PollingListener.setStcReadStr(data1);
                        }
                    } catch (Exception e) {
                        System.out.println(e.toString());
                        // 发生读取错误时显示错误信息后退出系统
                        System.exit(0);
                    }
                    break;
            }
        }
    }
}
