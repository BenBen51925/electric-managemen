package temple.rightLayComponetn;


import action.MouseActionPush;
import action.interfaceActionMatch.ChangeSizeActionFulsh;
import frame.MainJframe;
import stcpream.AllBoundSetting;
import stcpream.AllStaticLocalSizeSet;
import stcpream.StaticSet;
import temple.LeftModule;
import temple.MainComponent;
import temple.button.ChangeSizeButton;
import temple.button.buttonFaction.ChangeSizeButtonFaction;
import temple.rightComponent.IndustryCategoryComponent;
import temple.rightComponent.MainSwitchComponent;
import temple.rightComponent.TechnicalAreaComponent;
import temple.rightComponent.TechnicalDetailsComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RightOutComponent extends JPanel implements ChangeSizeActionFulsh {

    //技术区com
    private static TechnicalAreaComponent technicalAreaComponent;
    //行业类别com
    private static IndustryCategoryComponent industryCategory;
    //总开关com
    private static MainSwitchComponent mainSwitchComponent;
    //技术局详情com
    private static TechnicalDetailsComponent technicalDetailsComponent;


    public static TechnicalAreaComponent getTechnicalAreaComponent(){
        if (technicalAreaComponent==null){
            technicalAreaComponent = TechnicalAreaComponent.createComponent();
        }
        return technicalAreaComponent;
    }
    public static IndustryCategoryComponent getIndustryCategory(){
        if (industryCategory ==null){
            industryCategory = IndustryCategoryComponent.createComponent();
        }
        return industryCategory;
    }
    public static MainSwitchComponent getMainSwitchComponent(){
        if (mainSwitchComponent==null){
            mainSwitchComponent = MainSwitchComponent.createComponent();
        }
        return mainSwitchComponent;
    }
    /**返回按钮
     *
     */
    public static JButton returnButton =new JButton(){
        @Override
        public void paintComponent(Graphics g) {
            //super.paintComponent(g);
            g.drawImage(new ImageIcon(getClass().getResource("/img/return_button.png")).getImage(),0,0,this.getWidth(),this.getHeight(),this);
        }
    };

    private static RightOutComponent rightOutComponent;

    private RightOutComponent(){
        super();
        this.placeComponents();

    }
    public static RightOutComponent createComponent(){
        if (rightOutComponent==null){
            rightOutComponent = new RightOutComponent();
        }
        return rightOutComponent;
    }
    public void placeComponents() {
        this.removeAll();
        this.setName(AllBoundSetting.RIGHT_OUT_COMPONENT_NAME);
        this.setBounds(AllBoundSetting.getRightOutComponentLocalX(),
                AllBoundSetting.getRightOutComponentLocalY(),
                AllBoundSetting.getRightOutComponentSizeWidth(),
                AllBoundSetting.getRightOutComponentSizeHeight());
        System.out.println("RightOutComponent像素大小:"+AllBoundSetting.getRightOutComponentSizeWidth()+"*"+AllBoundSetting.getRightOutComponentSizeHeight());

        this.setBackground(null);
        this.setOpaque(false);

        setLayout(null);

        //加载三个页面
        if (technicalAreaComponent==null){
            technicalAreaComponent = TechnicalAreaComponent.createComponent();
        }
        if (industryCategory ==null){
            industryCategory = IndustryCategoryComponent.createComponent();
        }
        if (mainSwitchComponent==null){
            mainSwitchComponent = MainSwitchComponent.createComponent();
        }
        if (technicalDetailsComponent==null){
            technicalDetailsComponent = TechnicalDetailsComponent.createComponent(null,null);
        }
        //技术区界面
        this.add(technicalAreaComponent, BorderLayout.CENTER);
        technicalAreaComponent.setVisible(true);

        this.add(industryCategory, BorderLayout.CENTER);
        industryCategory.setVisible(false);

        this.add(mainSwitchComponent, BorderLayout.CENTER);
        mainSwitchComponent.setVisible(false);

        this.add(technicalDetailsComponent, BorderLayout.CENTER);
        technicalDetailsComponent.setVisible(false);

        this.setVisible(true);




        //拖拽事件
        MouseActionPush.setPushAction(this,AllBoundSetting.getMainSizeWidth(),AllBoundSetting.getMainSizeHeight());

        //改变大小按钮
        //ChangeSizeButton changeSizeButton =new ChangeSizeButton(this, MainJframe.createMainJframe());
        ChangeSizeButton changeSizeButton = ChangeSizeButtonFaction.createChangeSizeButton(this,MainJframe.createMainJframe());
        this.add(changeSizeButton);

        //返回按钮
        returnButton.setSize(AllBoundSetting.getReturnButtonSizeWidth(),AllBoundSetting.getReturnButtonSizeHeight());
        returnButton.setLocation(AllBoundSetting.getReturnButtonLocalX(),AllBoundSetting.getReturnButtonLocalY());
        //变爪子
        returnButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        //去边框
        returnButton.setBorder(null);
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TechnicalDetailsComponent.createComponent("","").setVisible(false);
                TechnicalAreaComponent.createComponent().setVisible(true);
            }
        });
        returnButton.setBackground(null);
        returnButton.setOpaque(false);
        returnButton.setVisible(false);
        this.add(returnButton);
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        AllBoundSetting.setRightOutComponentLocalX(x);
        AllBoundSetting.setRightOutComponentLocalY(y);
        AllBoundSetting.setRightOutComponentSizeWidth(width);
        AllBoundSetting.setRightOutComponentSizeHeight(height);
    }

    /**
     *  背景图
     */
    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(new ImageIcon(getClass().getResource(StaticSet.OUT_COMPONENT_BACKETGROUND)).getImage(),0,0,this.getWidth(),this.getHeight(),this);
    }

    @Override
    public void flushComponent() {
        //刷新里层容器大小
        switch (LeftModule.mainType){
            case LeftModule.INDUSTRY_CATEGORYCOMPONENT:
                industryCategory.placeComponents();
                break;
            case LeftModule.MAIN_SWITCH:
                mainSwitchComponent.placeComponents();
                break;
            case LeftModule.TECHNICAL_ALAREA:
                technicalAreaComponent.placeComponents();
                break;
            case LeftModule.TECHNICAL_DETAIL:
                //technicalDetailsComponent.placeComponents(technicalDetailsComponent.getTitle(),technicalDetailsComponent.getText());
                technicalDetailsComponent.flushSize(technicalDetailsComponent.getTitle(),technicalDetailsComponent.getText());
                returnButton.setBounds(AllBoundSetting.getReturnButtonLocalX(),AllBoundSetting.getReturnButtonLocalY(),
                        AllBoundSetting.getReturnButtonSizeWidth(),AllBoundSetting.getReturnButtonSizeHeight());
                break;
        }


    }
}
