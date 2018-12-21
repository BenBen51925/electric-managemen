package frame;

import temple.LoginComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LoginJFrame extends JFrame {
    public LoginJFrame(){
        super("登录");
        init();
    }

    public void init(){
        // 创建 JFrame 实例
        JFrame frame = this;
        this.setLayout( new GridLayout(1, 2) );
        this.setBackground(Color.GREEN);
        // 创建及设置窗口
        frame.setSize(350, 200);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //设置关闭
        this.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e)
            {
                LoginJFrame.this.dispose();
            }});
        /* 创建面板，这个类似于 HTML 的 div 标签
         * 我们可以创建多个面板并在 JFrame 中指定位置
         * 面板中我们可以添加文本字段，按钮及其他组件。
         */
        LoginComponent loginComponent=new LoginComponent();
        // 添加面板
        frame.add(loginComponent);
        //设置frame居于屏幕中央方式
        java.awt.Toolkit toolkit=java.awt.Toolkit.getDefaultToolkit();
        Dimension screenSize=toolkit.getScreenSize();
        double screenWidth=screenSize.getWidth();
        double screenHeight=screenSize.getHeight();
        this.setLocation((int)(screenWidth-this.getWidth())/2,(int)(screenHeight-this.getHeight())/2);
        loginComponent.setLoginJFrame(frame);
        /*
         * 调用用户定义的方法并添加组件到面板
         */
        loginComponent.placeComponents();
        // 设置界面可见
        frame.setVisible(true);
    }

    @Override
    public Component add(Component comp) {
        addImpl(comp, null, -1);
        return comp;
    }
}
