package temple;

import frame.MainJframe;
import stcpream.AllBoundSetting;
import stcpream.AllStaticLocalSizeSet;
import temple.button.minButton.IndustryCategoryButton;
import temple.button.minButton.MainSwitchButton;
import temple.button.minButton.TechnicalAreaButton;
import temple.rightComponent.TechnicalDetailsComponent;
import temple.rightLayComponetn.RightOutComponent;
import temple.rightLayComponetn.RightOutComponent;

import javax.swing.*;
import javax.swing.plaf.PanelUI;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * 主菜单界面
 */
@Deprecated
public class LeftModule extends JPanel {

    private  JPanel technicalAreaButton;
    /**
     * 创建 行业类别 JButton
     */
    private  JPanel industryCategoryButton ;
    /**
     * 创建 总开关 JButton
     */
    private  JPanel mainSwitchButton;

    //单利
    private static LeftModule leftModule;

    public JPanel getTechnicalAreaButton() {
        return technicalAreaButton;
    }

    public void setTechnicalAreaButton(JPanel technicalAreaButton) {
        this.technicalAreaButton = technicalAreaButton;
    }

    public JPanel getIndustryCategoryButton() {
        return industryCategoryButton;
    }

    public void setIndustryCategoryButton(JPanel industryCategoryButton) {
        this.industryCategoryButton = industryCategoryButton;
    }

    public JPanel getMainSwitchButton() {
        return mainSwitchButton;
    }

    public void setMainSwitchButton(JPanel mainSwitchButton) {
        this.mainSwitchButton = mainSwitchButton;
    }

    //主界面按键位置 1 为技术区 , 2 行业类别 ,3总开关,4为技术区详情
    public static Integer mainType = 1;

    public static final int INDUSTRY_CATEGORYCOMPONENT = 2;
    public static final int TECHNICAL_ALAREA = 1;
    public static final int MAIN_SWITCH = 3;
    public static final int TECHNICAL_DETAIL = 4;

    public static LeftModule createMainModule(){
        if (leftModule ==null){
            leftModule = new LeftModule();
        }
        return leftModule;
    }
    public LeftModule(){
        super();
        placeComponents();
    }
    public void placeComponents(){
        //添加左侧主要布局
        this.setBackground(null);
        //TODO it
        this.setOpaque(false);
        this.setLayout(null);
        this.setSize(AllBoundSetting.getLeftSizeWidthModel(),AllBoundSetting.getLeftSizeHighModel());
        /**
         * 创建 技术区 JButton
         */
        technicalAreaButton = new TechnicalAreaButton("技术区");
        technicalAreaButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //关闭详情页
                TechnicalDetailsComponent.createComponent("","").setVisible(false);
                if (mainType == TECHNICAL_ALAREA){
                    RightOutComponent.getTechnicalAreaComponent().setVisible(true);
                    return;
                }
                //将另外两个按钮隐藏
                RightOutComponent.getTechnicalAreaComponent().setVisible(true);
                RightOutComponent.getIndustryCategory().setVisible(false);
                RightOutComponent.getMainSwitchComponent().setVisible(false);
                //刷新页面按钮
                MainComponent.createComponent().repaint();
                mainType = TECHNICAL_ALAREA;
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        MainComponent.createComponent().add(technicalAreaButton);

        industryCategoryButton = new IndustryCategoryButton("行业类别");
        industryCategoryButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //关闭详情页
                TechnicalDetailsComponent.createComponent("","").setVisible(false);
                if (mainType ==INDUSTRY_CATEGORYCOMPONENT){
                    RightOutComponent.getIndustryCategory().setVisible(true);
                    return;
                }
                //将另外两个按钮隐藏
                RightOutComponent.getTechnicalAreaComponent().setVisible(false);
                RightOutComponent.getIndustryCategory().setVisible(true);
                RightOutComponent.getMainSwitchComponent().setVisible(false);
                //刷新页面按钮
                MainComponent.createComponent().repaint();
                mainType = INDUSTRY_CATEGORYCOMPONENT;
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }


        });
        MainComponent.createComponent().add(industryCategoryButton);


        mainSwitchButton = new MainSwitchButton("总开关");
        mainSwitchButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //将另外两个按钮隐藏
                RightOutComponent.getTechnicalAreaComponent().setVisible(false);
                RightOutComponent.getIndustryCategory().setVisible(false);
                RightOutComponent.getMainSwitchComponent().placeComponents();
                RightOutComponent.getMainSwitchComponent().setVisible(true);
                //关闭详情页
                TechnicalDetailsComponent.createComponent("","").setVisible(false);
                if (mainType == MAIN_SWITCH){
                    RightOutComponent.getMainSwitchComponent().setVisible(true);
                    return;
                }
                //刷新页面按钮
                MainComponent.createComponent().repaint();
                mainType = MAIN_SWITCH;
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }


        });
        MainComponent.createComponent().add(mainSwitchButton);
    }
}
