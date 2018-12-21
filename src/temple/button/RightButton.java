package temple.button;

import entity.ButtonFunctionBean;
import frame.HtmlSetJFrame;
import stcpream.StaticSet;
import temple.MainComponent;
import temple.rightComponent.IndustryCategoryComponent;
import temple.rightComponent.MainSwitchComponent;
import temple.rightComponent.TechnicalAreaComponent;

import utils.DrawUtils;
import utils.LoadProperties;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class RightButton extends JButton {


    protected float alpha = 1f; // 底色的透明度，默认为不透明
    protected boolean isMouseEntered = false;// 鼠标是否进入按钮
    private String buttonOrder;//按钮指令
    public Boolean isOpen = false;
    //设置删除安健
    DelButButton delButButton = new DelButButton();
    //设置删除安健
    UpdateButButton updateButButton = new UpdateButButton();
    //上级按钮名称
    public String mainButtonName;
    public RightButton(String buttonText,String mainButtonName) {
        super(buttonText);
        this.mainButtonName = mainButtonName;
        painComponents(buttonText);
        //去边框
        this.setBorderPainted(false);
        //变爪子
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        //添加鼠标监听
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                //当鼠标进入时,鼠标进入状态改为TRUE，并重绘按钮
                isMouseEntered = true;
                repaint();
            }
            @Override
            public void mouseExited(MouseEvent e) {
                isMouseEntered = false;
                repaint();
            }
        });
    }

    public DelButButton getDelButButton(){
        return delButButton;
    }

    public void painComponents(String textView){
        this.setLayout(new BorderLayout());
        this.setBackground(null);
        this.setOpaque(false);
        this.setVisible(true);
        //设置字体
        JLabel label=new JLabel(textView , SwingConstants.CENTER);
        label.setFont(new Font("微软雅黑", Font.CENTER_BASELINE, StaticSet.FONT_SIZE));
        label.setForeground(Color.WHITE);
        label.setVisible(true);
        this.add(label, SwingConstants.CENTER);
        //设置删除安健
        delButButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                LoadProperties.deleteButton(mainButtonName,textView);
                RightButton.this.setVisible(false);
                switch (mainButtonName){
                    case "technicalAreaButtons": TechnicalAreaComponent.createComponent().remove(RightButton.this);
                        break;
                    case "mainSwitchButton":MainSwitchComponent.createComponent().remove(RightButton.this);
                        break;
                    case "industryCategoryButtons":IndustryCategoryComponent.createComponent().remove(RightButton.this);
                        break;
                    default:
                        break;
                }
            }
        });
        //delButButton.setText("删除");
        delButButton.setBackground(null);
        delButButton.setOpaque(false);
        delButButton.setVisible(MainComponent.editPattern);
        this.add(delButButton, BorderLayout.EAST);
        //修改按钮
        updateButButton.addActionListener((event)->{
            //TODO it 添加界面
            //加载设置界面并将参数传递过去
            HtmlSetJFrame setJFrame = HtmlSetJFrame.createJFrame();
            ButtonFunctionBean buttonFunctionBean = LoadProperties.getButtonFunctionBean(mainButtonName, textView);
            HtmlSetJFrame.setViewModel(buttonFunctionBean);
            setJFrame.setVisible(true);
        });
        updateButButton.setBackground(null);
        updateButButton.setOpaque(false);
        updateButButton.setVisible(MainComponent.editPattern);
        this.add(updateButButton, BorderLayout.WEST);
        Color color =new Color(1882511000,true);
        this.setBackground(color);
    }

    public void setDelButtonVisit(Boolean buttonVisit){
        MainComponent.editPattern = buttonVisit;
        delButButton.setVisible(MainComponent.editPattern);
        updateButButton.setVisible(MainComponent.editPattern);
    }

    @Override
    public void paintComponent(Graphics g) {
        if (this.isMouseEntered||isOpen) {
            AlphaComposite composite = AlphaComposite.getInstance(
                    AlphaComposite.SRC_OVER, alpha);
            Graphics2D g2 = (Graphics2D) g;
            g2.setComposite(composite);
            new DrawUtils().drawButtonBackground(g2, this, new Color(254, 176, 66), new Color(255, 146, 117));
        }else {
            Color color =new Color(22, 76, 43, 100);
            Graphics2D g2 = (Graphics2D) g;
            new DrawUtils().drawButtonBackground(g2, this, color,color);
        }
    }

    public String getButtonOrder() {
        return buttonOrder;
    }

    public void setButtonOrder(String buttonOrder) {
        this.buttonOrder = buttonOrder;
    }
}
