package frame;

import stcpream.AllBoundSetting;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LodingJFrame extends Frame {

    private int sizeWeith = AllBoundSetting.getMainSizeWidth()/3;
    private int sizeHeight = AllBoundSetting.getMainSizeHeight()/5;
    // 任务的当前完成量
    private Integer amount;
    // 总任务量
    public Integer current = 0;

    public LodingJFrame(int amount){
        super();
        this.amount = amount;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }
    public Timer timer;

    // 创建一条垂直进度条
    JProgressBar bar = new JProgressBar(JProgressBar.HORIZONTAL);
    JLabel tipLabel = new JLabel("提示：", JLabel.LEFT);
    JLabel contentLabel = new JLabel("正在执行操作...", JLabel.LEFT);
    JLabel statusLabel = new JLabel(" ", JLabel.CENTER);
    public void init(){
        //鼠标形状为等待，告知用户程序已经在很努力的加载了，此时不可操作
        setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));

        tipLabel.setFont(new Font("Serif", Font.PLAIN, 14));
        contentLabel.setFont(new Font("Serif", Font.PLAIN, 14));
        statusLabel.setFont(new Font("Serif", Font.PLAIN, 14));
        this.setResizable(false);
        //隐藏上窗口
        this.setUndecorated(true);
        this.setSize(sizeWeith,sizeHeight);
        this.setLocation((AllBoundSetting.getMainSizeWidth()-sizeWeith)/2,(AllBoundSetting.getMainSizeHeight()-sizeHeight)/2);
        JPanel panel = new JPanel();
        // fr5.setBorder(new TitledBorder("BoxLayout - Y"));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(tipLabel);
        panel.add(Box.createVerticalStrut(2));
        panel.add(contentLabel);
        panel.add(Box.createVerticalStrut(7));
        panel.add(bar);
        // panel.add(Box.createVerticalGlue());
        panel.add(Box.createVerticalStrut(2));
        panel.add(statusLabel);
        this.add(panel, 0);

        // 以启动一条线程的方式来执行一个耗时的任务
        /*final Thread thread = new Thread(target);
        thread.start();*/
        // 设置在进度条中绘制完成百分比
        bar.setStringPainted(true);
        // bar.setPreferredSize(new Dimension(100, 18));
        // 设置进度条的最大值和最小值,
        bar.setMinimum(0);
        // 以总任务量作为进度条的最大值
        bar.setMaximum(this.getAmount());
        timer = new Timer(300, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 以任务的当前完成量设置进度条的value
                Integer current = LodingJFrame.this.getCurrent();
                bar.setValue(current);
                if (LodingJFrame.this.getAmount() <= LodingJFrame.this.getCurrent()) {
                    //statusLabel.setText("处理完成,oh yes!");
                    timer.stop();
                    LodingJFrame.this.dispose();
                }

            }
        });
        timer.start();
        this.setLocationRelativeTo(null);
        //this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       /* this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //main.interrupt();
                timer.stop();
                // 系统退出
                System.exit(0);
            }
        });*/
        // 该代码依据放置的组件设定窗口的大小使之正好能容纳你放置的所有组件
        this.pack();

        this.setVisible(true);
    }
    public static void main(String[] ar){
        LodingJFrame lodingJFrame = new LodingJFrame(1200);
        lodingJFrame.init();
        while (lodingJFrame.current<lodingJFrame.amount){
            try {
                Thread.sleep(100l);
                lodingJFrame.current++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
