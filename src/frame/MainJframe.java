package frame;

import stcpream.AllBoundSetting;
import stcpream.AllStaticLocalSizeSet;
import stcpream.StaticSet;
import temple.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainJframe extends Frame {



    public static MainJframe createMainJframe(){
        if (AllBoundSetting.mainJframe ==null){
            AllBoundSetting.mainJframe = new MainJframe();
        }
        return AllBoundSetting.mainJframe;
    }

    private MainJframe(){
        super(StaticSet.MAIN_FRAME_TITLE);
    }


    public void init(){
        //主界面
        MainComponent mainComponent = MainComponent.createComponent();
        mainComponent.paintComponents();
        ImageIcon icon=new ImageIcon(getClass().getResource(StaticSet.OUT_ICON));

        this.setIconImage(icon.getImage());
        this.add(mainComponent);
        mainComponent.setVisible(true);


        this.setLayout(null);
        this.setBackground(null);
        this.setSize(AllBoundSetting.getMainSizeWidth(), AllBoundSetting.getMainSizeHeight());
        //设置frame居于屏幕中央方式1
        this.setLocation(AllBoundSetting.getMainJFrameScreamX(),AllBoundSetting.getMainJFrameScreamY());
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
    }
}
