package temple.button;

import utils.DrawUtils;

import javax.swing.*;
import java.awt.*;


public class UpdateButButton extends JButton {

    public UpdateButButton(){
        super();
        painComponents();
    }
    @Override
    public void paintComponent(Graphics g) {
        Color color1=new Color(182, 23, 25);
        Color color2=new Color(182, 161, 28);
        Graphics2D g2 = (Graphics2D) g;
        new DrawUtils().drawButtonBackground(g2, this, color1, color2);
        //去边框
        this.setBorderPainted(false);
    }
    public void painComponents(){
        //变爪子
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        //设置样式
        this.setLayout(new BorderLayout());
        this.setBackground(null);
        this.setOpaque(false);
        this.setVisible(true);
        this.setText("修改");
        //设置字体
        JLabel label=new JLabel("修改" , SwingConstants.CENTER);
        label.setFont(new Font("微软雅黑", Font.CENTER_BASELINE, 14));
        label.setForeground(Color.WHITE);
        label.setVisible(true);
        this.add(label, SwingConstants.CENTER);
    }
}
