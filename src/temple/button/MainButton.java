package temple.button;

import action.MouseActionPush;
import action.interfaceActionMatch.ChangeSizeActionFulsh;
import defaultDB.implDb.ComplateXmlDb;
import frame.MainJframe;
import stcpream.AllBoundSetting;
import stcpream.AllStaticLocalSizeSet;
import temple.LeftModule;
import temple.MainComponent;
import temple.button.buttonFaction.ChangeSizeButtonFaction;
import utils.DrawUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.logging.Logger;


public class MainButton extends JPanel implements ChangeSizeActionFulsh {
    protected Logger logger =Logger.getLogger("主按钮");
    //按钮大小宽度
    //public int mainButtonSizeWidth = AllStaticLocalSizeSet.mainButtonSizeWidth;
    //按钮大小高度
    //public int mainButtonSizeHight = AllStaticLocalSizeSet.mainButtonSizeHight;
    //按钮x位置
    //public int mainButtonPathX = AllStaticLocalSizeSet.mainButtonPathX;
    //按钮y轴位置
    //public int mainButtonPathY = AllStaticLocalSizeSet.mainButtonPathY;
    //后两个按钮y轴距离
    //public int mainButtonPathInterval =AllStaticLocalSizeSet.mainButtonPathInterval;
    //public int iconLabelSizeWidth = AllStaticLocalSizeSet.iconLabelSizeWidth;
    //public int iconLabelSizeHeight = AllStaticLocalSizeSet.iconLabelSizeHeight;
    //public int iconLabelLocalX = AllStaticLocalSizeSet.iconLabelLocalX;
    //public int iconLabelLocalY = AllStaticLocalSizeSet.iconLabelLocalY;
    protected Integer mainButtonPathX = null;
    protected Integer mainButtonPathY = null;
    protected Integer mainButtonSizeWidth = null;
    protected Integer mainButtonSizeHeight = null;
    protected Integer iconLabelLocalX;
    protected Integer iconLabelLocalY;
    protected Integer iconLabelSizeWidth;
    protected Integer iconLabelSizeHeight;
    //改变大小按钮
    protected ChangeSizeButton changeSizeButton;
    //图标
    protected URL resource;
    protected ImageIcon imageIcon;
    protected Image scaledInstance;
    protected boolean isMouseEntered = false;// 鼠标是否进入按钮
    public JLabel iconLabel = new JLabel();

    protected float alpha = 1f; // 底色的透明度，默认为不透明

    //按钮类型
    public int mainType;
    protected ComplateXmlDb complateXmlDb =new ComplateXmlDb();
    public MainButton(String buttonText,int mainType) {
        super();
        this.mainType = mainType;
        this.setName(buttonText);
        initStyle();
        //鼠标监听事件
        MouseAdapter mouseAdapter=new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mousePressed(e);
                //当鼠标进入时,鼠标进入状态改为TRUE，并重绘按钮
                isMouseEntered = true;
                MainComponent.createComponent().repaint();
            }
            @Override
            public void mouseExited(MouseEvent e) {
                super.mousePressed(e);
                //当鼠标进入时,鼠标离开状态改为TRUE，并重绘按钮
                isMouseEntered = false;
               // LeftModule.createMainModule().repaint();
                MainComponent.createComponent().repaint();
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mousePressed(e);
                //LeftModule.createMainModule().repaint();
                MainComponent.createComponent().repaint();
            }
        };

        //添加鼠标监听
        addMouseListener(mouseAdapter);

    }

    /**
     * 初始化按钮样式
     */
    private void initStyle() {
        //去边框变爪子
        //this.setBorderPainted(false);
        //this.setSize(AllBoundSetting.getMainButtonSizeWidth(), AllBoundSetting.getMainButtonSizeHight());
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.setLayout(null);
        if (iconLabelSizeWidth==null){
            iconLabelSizeWidth = AllBoundSetting.getIconLabelSizeWidth();
        }
        if (iconLabelSizeHeight ==null){
            iconLabelSizeHeight = AllBoundSetting.getIconLabelSizeHeight();
        }
        if (iconLabelLocalX ==null){
            iconLabelLocalX = AllBoundSetting.getIconLabelLocalX();
        }
        if (iconLabelLocalY ==null){
            iconLabelLocalY = AllBoundSetting.getIconLabelLocalY();
        }

        this.add(iconLabel);

        //拖拽事件
        MainJframe mainJframe = MainJframe.createMainJframe();
        MouseActionPush.setPushAction(this, mainJframe);
        //改变大小
        changeSizeButton = ChangeSizeButtonFaction.createChangeSizeButton(this,mainJframe);
        this.add(changeSizeButton);
    }

    @Override
    public void paintComponent(Graphics g) {
        Color c1=new Color(39, 255, 140);
        Color c2=new Color(36, 213, 183);
        Graphics2D g2 = (Graphics2D) g;
        if (!this.isMouseEntered&&this.mainType != LeftModule.mainType) {
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
        if (this.isMouseEntered||this.mainType == LeftModule.mainType){
            AlphaComposite composite = AlphaComposite.getInstance(
                    AlphaComposite.SRC_OVER, 1f);
            g2.setComposite(composite);
            new DrawUtils().drawButtonBackground(g2, this, c1, c2);
        }
    }

    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
        AllBoundSetting.setMainButtonSizeWidth(width);
        AllBoundSetting.setMainButtonSizeHight(height);
    }

    @Override
    public void flushComponent() {
        imageIcon = new ImageIcon(resource);
        iconLabel.setBounds(AllBoundSetting.getIconLabelLocalX(),AllBoundSetting.getIconLabelLocalY(),AllBoundSetting.getIconLabelSizeWidth(),AllBoundSetting.getIconLabelSizeHeight());
        scaledInstance = imageIcon.getImage().getScaledInstance(AllBoundSetting.getIconLabelSizeWidth(),AllBoundSetting.getIconLabelSizeHeight(), Image.SCALE_DEFAULT);
        imageIcon.setImage(scaledInstance);
        iconLabel.setIcon(imageIcon);
    }
}
