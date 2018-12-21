package temple.rightComponent;



import frame.HtmlJPanel;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.apache.poi.util.StringUtil;
import stcpream.StaticSet;
import temple.rightLayComponetn.RightComponent;
import temple.rightLayComponetn.RightOutComponent;
import utils.DrawUtils;
import javax.swing.*;

import java.awt.*;
import java.net.URL;

/**
 *详情页面
 */
public class TechnicalDetailsComponent extends RightComponent {
    private static TechnicalDetailsComponent technicalDetailsComponent;

    //标题
    private static JPanel titleLabel = new JPanel(){
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponents(g);
            Graphics2D g2 = (Graphics2D) g;
            new DrawUtils().drawWithBackground(g2, this, new Color(31, 200, 171), new Color(60, 239, 209));
        }
    };
    //标题文字
    private static JLabel textTitlejLabel = new JLabel();
    private static  Font fontTitle = new Font("微软雅黑", 0, StaticSet.FONT_SIZE);
    //内容LAN html解析
    private static JEditorPane editorPane = new JEditorPane();
    //内容
    private static JLabel textLabel = new JLabel("",JLabel.LEFT);
    private static String title;
    private static String text;

    private static final String urlStart = "http://";
    private static final String fileStart = "file://";

    private static HtmlJPanel htmlJFrame = HtmlJPanel.createComponent();

    public static String getTitle() {
        return title;
    }

    public static void setTitle(String title) {
        TechnicalDetailsComponent.title = title;
    }

    public static String getText() {
        return text;
    }

    public static void setText(String text) {
        TechnicalDetailsComponent.text = text;
    }

    private static Integer titleLabelLocalX;
    public static Integer getTitleLabelLocalX(){
        if (titleLabelLocalX !=null){
            return titleLabelLocalX;
        }
        return 0;
    }
    private static Integer titleLabelLocalY;
    public static Integer getTitleLabelLocalY(){
        if (titleLabelLocalY !=null){
            return titleLabelLocalY;
        }
        return getSizeHeight() * 6/100;
    }
    private static Integer titleLabelSizeWidth;
    public static Integer getTitleLabelSizeWidth(){
        if (titleLabelSizeWidth !=null){
            return titleLabelSizeWidth;
        }
        return getSizeWidth();
    }
    private static Integer titleLabelSizeHeight;
    public static Integer getTitleLabelSizeHeight(){
        if (titleLabelSizeHeight !=null){
            return titleLabelSizeHeight;
        }
        return getSizeHeight() * 8/100;
    }
    private static Integer textLabelSizeHeight;
    public static Integer getTextLabelSizeHeight(){
        if (textLabelSizeHeight !=null){
            return textLabelSizeHeight;
        }
        return getSizeHeight() - getTitleLabelSizeHeight();
    }
    private static Integer textLabelLocalX;
    public static Integer getTextLabelLocalX(){
        if (textLabelLocalX !=null){
            return textLabelLocalX;
        }
        return getTitleLabelLocalX()+getTitleLabelSizeWidth()*1/100;
    }
    private static Integer textLabelSizeWidth;
    public static Integer getTextLabelSizeWidth(){
        if (textLabelSizeWidth !=null){
            return textLabelSizeWidth;
        }
        return getSizeWidth() -getTextLabelLocalX()*2;
    }
    private static Integer textLabelLocalY;
    public static Integer getTextLabelLocalY(){
        if (textLabelLocalY !=null){
            return textLabelLocalY;
        }
        return getTitleLabelLocalY()+getTitleLabelSizeHeight()*5/3;
    }

    @Override
    public void removeAll() {
        super.removeAll();
    }

    private TechnicalDetailsComponent(){
        super();
    }
    public static TechnicalDetailsComponent createComponent(String title,String text){
        if (technicalDetailsComponent==null){
            technicalDetailsComponent = new TechnicalDetailsComponent();
            technicalDetailsComponent.placeComponents(title,text);
        }
        technicalDetailsComponent.flushSize(title,text);
        return technicalDetailsComponent;
    }
    public void flushSize(String title,String text){
        this.setVisible(true);
        TechnicalAreaComponent.createComponent().setVisible(false);
        this.setBounds(getLocalX(),getLocalY(),getSizeWidth(),getSizeHeight());
        titleLabel.setBounds(getTitleLabelLocalX(),getTitleLabelLocalY(),getTitleLabelSizeWidth(),getTitleLabelSizeHeight());
        if (title!=null){
            textTitlejLabel.setText("    "+title);
        }
        //editorPane.setBounds(0,0,getTextLabelSizeWidth(),getTextLabelSizeHeight());
        htmlJFrame.setBounds(getTextLabelLocalX(),getTextLabelLocalY(),getTextLabelSizeWidth(),getTextLabelSizeHeight());
    }
    public void placeComponents(String title,String text) {
        this.setBounds(getLocalX(),getLocalY(),getSizeWidth(),getSizeHeight());
        this.title = title;
        this.text = text;
        //将technicalAreaComponent隐藏
        TechnicalAreaComponent.createComponent().setVisible(false);
        System.out.println("TechnicalDetailsComponent像素大小"+getSizeWidth()+"*"+getSizeHeight());

        this.setBackground(null);
        this.setOpaque(false);
        this.setLayout(null);



        titleLabel.setLayout(new BorderLayout());
        if (title!=null){
            textTitlejLabel.setText("    "+title);
        }

        textTitlejLabel.setFont(fontTitle);
        textTitlejLabel.setForeground(Color.WHITE);
        textTitlejLabel.setVisible(true);
        titleLabel.add(textTitlejLabel,BorderLayout.WEST);
        RightOutComponent.returnButton.setVisible(true);
        titleLabel.setBounds(getTitleLabelLocalX(),getTitleLabelLocalY(),getTitleLabelSizeWidth(),getTitleLabelSizeHeight());
        titleLabel.setVisible(true);
        this.add(titleLabel);

        //垂直不居中，顶头
        textLabel.setVerticalAlignment(SwingConstants.TOP);
        textLabel.setBackground(null);
        textLabel.setVisible(true);
        textLabel.setBounds(getTextLabelLocalX(),getTextLabelLocalY(),getTextLabelSizeWidth(),getTextLabelSizeHeight());
        textLabel.setOpaque(false);
        //this.add(textLabel);


        editorPane.setVisible(true);
        editorPane.setOpaque(false);
        editorPane.setBackground(null);
        editorPane.setEditable(false);     //请把editorPane设置为只读，不然显示就不整齐
        editorPane.setContentType("text/html");
        editorPane.setBounds(0,0,getTextLabelSizeWidth(),getTextLabelSizeHeight());
        editorPane.setText(text);
        //textLabel.add(editorPane);

        //javaFx

        this.add(htmlJFrame);

        this.setBackground(null);
        this.setOpaque(false);

        repaint();
    }


    //关闭详情页
    @Override
    public void setVisible(boolean aFlag) {
        super.setVisible(aFlag);
        //返回按钮
        RightOutComponent.returnButton.setVisible(aFlag);
    }

    //深度浸染
    public static int deep = 23;
    public void setAlter(JComponent jComponent){
        if (deep<0){
            return;
        }
        jComponent.setVisible(false);
        jComponent.setBackground(null);
        deep--;
        for (int i =0;i<jComponent.getComponentCount();i++){
            JComponent component = (JComponent)jComponent.getComponent(i);
            setAlter(component);
        }
    }
}
