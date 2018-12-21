package temple;

import action.MouseActionPush;
import action.interfaceActionMatch.ChangeSizeActionFulsh;
import defaultDB.implDb.ComplateXmlDb;
import defaultDB.implDb.ComponentXmlDb;
import entity.ComponentBean;
import frame.MainJframe;
import stcpream.AllBoundSetting;
import stcpream.AllStaticLocalSizeSet;
import stcpream.StaticSet;
import temple.button.ChangeSizeButton;
import temple.button.RecoverButton;
import temple.button.buttonFaction.ChangeSizeButtonFaction;
import temple.button.minButton.IndustryCategoryButton;
import temple.button.minButton.MainSwitchButton;
import temple.button.minButton.TechnicalAreaButton;
import temple.mainComponent.TopIconLabel;
import temple.rightComponent.IndustryCategoryComponent;
import temple.rightComponent.MainSwitchComponent;
import temple.rightComponent.TechnicalAreaComponent;
import temple.rightComponent.TechnicalDetailsComponent;
import temple.rightLayComponetn.RightOutComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


/**
 * 主窗口
 */
public class MainComponent extends JPanel implements ChangeSizeActionFulsh {

    private static MainComponent mainComponent;
    private static ComplateXmlDb complateXmlDb =new ComplateXmlDb();
    private static LeftModule leftModule;
    private static RightOutComponent rightOutComponent;
    private static JLabel mainComponentIconLabel = new TopIconLabel();
    public static float alpha = 1.0f;
    private static JLabel messageLabel = new JLabel(){
        protected void paintComponent(Graphics g) {

            Graphics2D messageGraphics2D = (Graphics2D)g;
            if (alpha>1.0f||alpha<0.01f){
                alpha = 1.0f;
            }
            AlphaComposite composite = AlphaComposite.getInstance(
                    AlphaComposite.SRC_OVER, alpha);
            messageGraphics2D.setComposite(composite);

            super.paintComponent(messageGraphics2D);
        }
    };
    //恢复按件
    public static JLabel recoverButton = new RecoverButton();
    //右上方标题文字
    private static JLabel mainComponentTextLabel=AllBoundSetting.mainComponentTextLabel;
    //底部标题文字
    private static JLabel downTextLabel=AllBoundSetting.downTextLabel;
    //编辑模式
    public static Boolean editPattern = false;
    // 在该BufferedImage对象中绘制颜色
    //private BufferedImage bufImg = null;
    //单例模式
    public static MainComponent createComponent(){
        if (mainComponent ==null){
            mainComponent = new MainComponent();
        }
        return mainComponent;
    }
    //获取mainComponent有可能为空
    public static MainComponent getMainComponent(){
        return mainComponent;
    }
    private MainComponent(){
        super();
    }

    /**
     *  背景图
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        g.drawImage(new ImageIcon(getClass().getResource(StaticSet.FRAME_BACKETGROUND)).getImage(),0,0,this.getWidth(),this.getHeight(),null);
    }
    public void paintComponents(){
        this.removeAll();
        // 确保一个漂亮的外观风格
        JFrame.setDefaultLookAndFeelDecorated(true);
        this.setLayout(null);
        this.setSize(AllBoundSetting.getMainSizeWidth(), AllBoundSetting.getMainSizeHeight());
        this.setVisible(true);
        this.setOpaque(true);
        /*
         * 调用用户定义的方法并添加组件到面板
         */
        leftModule = LeftModule.createMainModule();
        this.add(leftModule,BorderLayout.WEST);
        //加载右侧外层页面
        rightOutComponent= RightOutComponent.createComponent();
        this.add(rightOutComponent,BorderLayout.EAST);

        this.add(mainComponentIconLabel);
        //字体
        //mainComponentTextLabel = new JLabel();
        mainComponentTextLabel.setLayout(null);
        mainComponentTextLabel.setName(AllBoundSetting.TOP_TEXT_COMPONENT_NAME);
        Font font = new Font(AllBoundSetting.getFountName(), AllBoundSetting.getFountStyle(), AllBoundSetting.getFountSize());
        mainComponentTextLabel.setFont(font);
        String titleText = "智慧营业厅管控系统";
        mainComponentTextLabel.setText(titleText);
        mainComponentTextLabel.setForeground(Color.WHITE);
        mainComponentTextLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                //技术区按钮
                JPanel technicalAreaButton = leftModule.getTechnicalAreaButton();
                ComponentBean technicalAreaButtonComponentBean = new ComponentBean();
                technicalAreaButtonComponentBean.setName(technicalAreaButton.getName());
                technicalAreaButtonComponentBean.setComponentType(complateXmlDb.MAIN_BUTTON_TYPE);
                technicalAreaButtonComponentBean.setLocalX(technicalAreaButton.getX());
                technicalAreaButtonComponentBean.setLocalY(technicalAreaButton.getY());
                technicalAreaButtonComponentBean.setSizeHeight(technicalAreaButton.getHeight());
                technicalAreaButtonComponentBean.setSizeWidth(technicalAreaButton.getWidth());
                complateXmlDb.saveAndUpdate(technicalAreaButtonComponentBean);
                //分类
                JPanel industryCategoryButton = leftModule.getIndustryCategoryButton();
                ComponentBean industryCategoryButtonComponentBean = new ComponentBean();
                industryCategoryButtonComponentBean.setName(industryCategoryButton.getName());
                industryCategoryButtonComponentBean.setComponentType(complateXmlDb.MAIN_BUTTON_TYPE);
                industryCategoryButtonComponentBean.setLocalX(industryCategoryButton.getX());
                industryCategoryButtonComponentBean.setLocalY(industryCategoryButton.getY());
                industryCategoryButtonComponentBean.setSizeHeight(industryCategoryButton.getHeight());
                industryCategoryButtonComponentBean.setSizeWidth(industryCategoryButton.getWidth());
                complateXmlDb.saveAndUpdate(industryCategoryButtonComponentBean);
                //总开关
                JPanel mainSwitchButton = leftModule.getMainSwitchButton();
                ComponentBean mainSwitchButtonComponentBean = new ComponentBean();
                mainSwitchButtonComponentBean.setName(mainSwitchButton.getName());
                mainSwitchButtonComponentBean.setComponentType(complateXmlDb.MAIN_BUTTON_TYPE);
                mainSwitchButtonComponentBean.setLocalX(mainSwitchButton.getX());
                mainSwitchButtonComponentBean.setLocalY(mainSwitchButton.getY());
                mainSwitchButtonComponentBean.setSizeHeight(mainSwitchButton.getHeight());
                mainSwitchButtonComponentBean.setSizeWidth(mainSwitchButton.getWidth());
                complateXmlDb.saveAndUpdate(mainSwitchButtonComponentBean);
                //右侧主界面
                ComponentBean mainComponentBean = new ComponentBean();
                mainComponentBean.setName(rightOutComponent.getName());
                mainComponentBean.setComponentType(complateXmlDb.RIGHT_COMPONENT_TYPE);
                mainComponentBean.setLocalX(rightOutComponent.getX());
                mainComponentBean.setLocalY(rightOutComponent.getY());
                mainComponentBean.setSizeHeight(rightOutComponent.getHeight());
                mainComponentBean.setSizeWidth(rightOutComponent.getWidth());
                complateXmlDb.saveAndUpdate(mainComponentBean);
                //上部图标
                ComponentBean topIconComponentBean = new ComponentBean();
                topIconComponentBean.setName(mainComponentIconLabel.getName());
                topIconComponentBean.setComponentType(complateXmlDb.TOP_ICON_LABEL_COMPONENT_TYPE);
                topIconComponentBean.setLocalX(mainComponentIconLabel.getX());
                topIconComponentBean.setLocalY(mainComponentIconLabel.getY());
                topIconComponentBean.setSizeHeight(mainComponentIconLabel.getHeight());
                topIconComponentBean.setSizeWidth(mainComponentIconLabel.getWidth());
                complateXmlDb.saveAndUpdate(topIconComponentBean);
                //上部标题
                ComponentBean topTextComponentBean = new ComponentBean();
                topTextComponentBean.setName(mainComponentTextLabel.getName());
                topTextComponentBean.setComponentType(complateXmlDb.TOP_TEXT_LABEL_COMPONENT_TYPE);
                topTextComponentBean.setLocalX(mainComponentTextLabel.getX());
                topTextComponentBean.setLocalY(mainComponentTextLabel.getY());
                topTextComponentBean.setSizeHeight(mainComponentTextLabel.getHeight());
                topTextComponentBean.setSizeWidth(mainComponentTextLabel.getWidth());
                complateXmlDb.saveAndUpdate(topTextComponentBean);
                //下部标题
                ComponentBean downTextComponentBean = new ComponentBean();
                downTextComponentBean.setName(downTextLabel.getName());
                downTextComponentBean.setComponentType(complateXmlDb.DOWN_TEXT_LABEL_COMPONENT_TYPE);
                downTextComponentBean.setLocalX(downTextLabel.getX());
                downTextComponentBean.setLocalY(downTextLabel.getY());
                downTextComponentBean.setSizeHeight(downTextLabel.getHeight());
                downTextComponentBean.setSizeWidth(downTextLabel.getWidth());
                complateXmlDb.saveAndUpdate(downTextComponentBean);
                if (editPattern){
                    System.exit(0);
                }
            }
        });

        //移动
        MouseActionPush.setPushAction(mainComponentTextLabel,this);
        //改变大小按钮
        /*ChangeSizeButton changeSizeButtonText = ChangeSizeButtonFaction.createChangeSizeButton(mainComponentTextLabel, MainJframe.createMainJframe());
        mainComponentTextLabel.add(changeSizeButtonText);*/

        float size = mainComponentTextLabel.getFont().getSize2D();
        int length = mainComponentTextLabel.getText().length();
        int labelSizeWidth = (int) (length*size*1.1);
        int labelSizeHight = (int) (size*1.1);

        if (AllBoundSetting.getMainComponentTextLocalX()==null){
            AllBoundSetting.setMainComponentTextLocalX(AllBoundSetting.getRightOutComponentLocalX()+AllBoundSetting.getRightOutComponentSizeWidth()-labelSizeWidth);
        }
        if (AllBoundSetting.getMainComponentTextLocalY()==null){
            AllBoundSetting.setMainComponentTextLocalY( AllBoundSetting.getRightOutComponentLocalY()/2);
        }
        mainComponentTextLabel.setBounds(AllBoundSetting.getMainComponentTextLocalX(),AllBoundSetting.getMainComponentTextLocalY(), labelSizeWidth,labelSizeHight );
        mainComponentTextLabel.setVisible(true);
        this.add(mainComponentTextLabel);
        this.add(recoverButton);
        //添加文字提醒
        this.add(messageLabel);

        //下方文字
        //downTextLabel= new JLabel();
        downTextLabel.setLayout(null);
        //设置名称可在xml中查询
        downTextLabel.setName(AllBoundSetting.DOWN_TEXT_COMPONENT_NAME);
        downTextLabel.setFont(font);
        String downText = "泰斗网络科技有限公司  电话:";
        String phone = "400-048-5399";
        downTextLabel.setText(downText+phone);
        downTextLabel.setForeground(Color.WHITE);


        int dowTextLength = downTextLabel.getText().length();
        float size2D = downTextLabel.getFont().getSize2D();
        int downTextLabelWidth = (int) (dowTextLength*size2D-(phone.length()+2)*size2D/2);
        int downTextLabelHight = (int) (size2D*1.1);
        downTextLabel.setSize(downTextLabelWidth,downTextLabelHight);
        Integer downTextLocalX =mainComponentTextLabel.getX()-(downTextLabel.getWidth()-mainComponentTextLabel.getWidth());
        Integer downTextLocalY=this.getHeight()-downTextLabel.getHeight()*2;
        if (AllBoundSetting.getDownTextLocalX()==null){
            AllBoundSetting.setDownTextLocalX(downTextLocalX);
        }
        if (AllBoundSetting.getDownTextLocalY()==null){
            AllBoundSetting.setDownTextLocalY(downTextLocalY);
        }

        downTextLocalX = AllBoundSetting.getDownTextLocalX();
        downTextLocalY = AllBoundSetting.getDownTextLocalY();
        downTextLabel.setLocation(downTextLocalX,downTextLocalY);
        //移动
        MouseActionPush.setPushAction(downTextLabel,this);
        downTextLabel.setBackground(Color.magenta);
        downTextLabel.setOpaque(false);
        this.add(downTextLabel);
        // 设置界面可见
        this.setVisible(true);


    }
    public void showMessage(String message,Color color){
        messageLabel.setText(message);

        Font font =new Font(AllBoundSetting.getFountName(), 0, 24);
        float size = font.getSize2D();
        int length = message.length();
        int labelSizeWidth = (int) (length*size*1.1);
        int labelSizeHight = (int) (size*1.1);
        messageLabel.setSize(labelSizeWidth,labelSizeHight);
        messageLabel.setLocation((this.getWidth()-messageLabel.getWidth())/2,AllBoundSetting.getMainComponentTextLocalY());
        messageLabel.setFont(font);
        messageLabel.setForeground(color);
        messageLabel.setVisible(true);
        MainComponent.alpha = 1.0f;
        new Thread(){
            @Override
            public void run() {
                super.run();
                //延后变淡
                try {
                    sleep(2400l);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //透明度开始变化
                float addFload = 0.01f;
                while (MainComponent.alpha > 0.0f){
                    MainComponent.alpha =MainComponent.alpha - addFload;
                    messageLabel.repaint();
                    try {
                        sleep(12l);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                messageLabel.setVisible(false);

            }
        }.start();

    }

    @Override
    public void flushComponent() {
        this.paintComponents();
    }
}
