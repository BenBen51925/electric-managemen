package temple.button;

import defaultDB.implDb.ComplateXmlDb;
import stcpream.AllBoundSetting;
import stcpream.StaticSet;

import utils.DrawUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class RecoverButton extends JLabel {
    private Boolean isMouseEntered = false;
    private ComplateXmlDb complateXmlDb =new ComplateXmlDb();
    public RecoverButton(){
        painComponents();
    }
    public void painComponents(){
        this.setSize(AllBoundSetting.getMainSizeWidth()/10,AllBoundSetting.getMainSizeHeight()/10);
        this.setLocation(AllBoundSetting.getMainSizeWidth()-this.getWidth(),AllBoundSetting.getMainSizeHeight()-this.getHeight());
        this.setLayout(new BorderLayout());
        this.setBackground(null);
        this.setOpaque(false);
        this.setVisible(false);
        //设置字体
        JLabel label=new JLabel("复\t原" , SwingConstants.CENTER);
        label.setFont(new Font("微软雅黑", Font.CENTER_BASELINE, StaticSet.FONT_SIZE));
        label.setForeground(Color.WHITE);
        label.setVisible(true);
        this.add(label, SwingConstants.CENTER);
        Color color =new Color(1882511000,true);
        this.setBackground(color);
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
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                complateXmlDb.removeAll();
                System.exit(0);
            }
        });
    }
    @Override
    public void paintComponent(Graphics g) {
        Color c1=new Color(39, 255, 140);
        Color c2=new Color(36, 213, 183);
        Graphics2D g2 = (Graphics2D) g;
        if (!this.isMouseEntered) {
            ImageIcon imageIcon = new ImageIcon(getClass().getResource("/img/toumingtu.png"));
            Image image = imageIcon.getImage();
            int width = this.getWidth();
            int height = this.getHeight();
            Image scaledInstance;
            synchronized (this){
                scaledInstance = image.getScaledInstance(width,height ,Image.SCALE_DEFAULT );
            }
            imageIcon.setImage(scaledInstance);
            g.drawImage(imageIcon.getImage(),0,0,width,height,this);
        }
        if (this.isMouseEntered){
            AlphaComposite composite = AlphaComposite.getInstance(
                    AlphaComposite.SRC_OVER, 1f);
            g2.setComposite(composite);
            new DrawUtils().drawButtonBackground(g2, this, c1, c2);
        }
    }
}
