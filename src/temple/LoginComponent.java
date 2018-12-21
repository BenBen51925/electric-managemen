package temple;

import frame.MainJframe;
import temple.mainComponent.TopIconLabel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * 登录界面
 */
public class LoginComponent extends JPanel {
    /**
     * 创建 JLabel
     */
    private static JLabel userLabel = new JLabel("User:");
    /**
     * 创建文本域用于用户输入
     */
    private static JTextField userText = new JTextField(20);

    /**
     *输入密码的文本域
     */
    private static JLabel passwordLabel = new JLabel("Password:");

    /**
     *这个类似用于输入的文本域
     * 但是输入的信息会以点号代替，用于包含密码的安全性
     */
    private static JPasswordField passwordText = new JPasswordField(20);

    private static JFrame loginJFrame;

    public void setLoginJFrame(JFrame loginJFrame) {
        LoginComponent.loginJFrame = loginJFrame;
    }


    public void placeComponents(){
        JPanel panel = this;
        /* 布局部分我们这边不多做介绍
         * 这边设置布局为 null
         */
        panel.setLayout(null);
        /* 这个方法定义了组件的位置。
         * setBounds(x, y, width, height)
         * x 和 y 指定左上角的新位置，由 width 和 height 指定新的大小。
         */
        userLabel.setBounds(10,20,80,25);
        panel.add(userLabel);


        userText.setBounds(100,20,165,25);
        panel.add(userText);


        passwordLabel.setBounds(10,50,80,25);
        panel.add(passwordLabel);


        passwordText.setBounds(100,50,165,25);
        panel.add(passwordText);

        // 创建登录按钮
        JButton loginButton = new JButton("登录");
        loginButton.setName("login");
        JLabel messLabel = new JLabel();
        messLabel.setBounds(100,80,165,25);
        this.add(messLabel);
        loginButton.setBounds(10, 80, 80, 25);
        ActionListener action =new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user=userText.getText();
                userText.setText("admin");
                String password=passwordText.getText();
                passwordText.setText("123");
                System.out.println("用户名:"+user);
                System.out.println("密码:"+password);
                if (MainComponent.editPattern||("admin".equals(user)&&"123".equals(password))){
                    TopIconLabel.iniEditPattern(!MainComponent.editPattern);
                    /**
                     * 取消登录界面
                     */
                    LoginComponent.this.setVisible(false);
                    loginJFrame.remove(LoginComponent.this);
                    loginJFrame.dispose();
                }else {
                    messLabel.setText("密码或账户错误");
                }

            }
        };
        System.out.println(loginButton.getText());
        loginButton.setText("登录");
        panel.add(loginButton);
        loginButton.addActionListener(action);


    }
}
