package temple.mainComponent;

import action.MouseActionPush;
import action.interfaceActionMatch.ChangeSizeActionFulsh;
import frame.LoginJFrame;
import stcpream.AllBoundSetting;
import temple.LeftModule;
import temple.MainComponent;
import temple.button.ChangeSizeButton;
import temple.button.buttonFaction.ChangeSizeButtonFaction;
import temple.rightComponent.IndustryCategoryComponent;
import temple.rightComponent.MainSwitchComponent;
import temple.rightComponent.TechnicalAreaComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

public class TopIconLabel extends JLabel implements ChangeSizeActionFulsh {
    private  ImageIcon imageIcon;
    private Image scaledInstance;
    public TopIconLabel(){
        super();
        paintComponents();
    }

    public void paintComponents(){
        AllBoundSetting.mainIconUrl = TopIconLabel.class.getResource("/img/icon.png");
        imageIcon = new ImageIcon(AllBoundSetting.mainIconUrl);
        scaledInstance = imageIcon.getImage().getScaledInstance(AllBoundSetting.getMainComponentIconSizeWidth(),AllBoundSetting.getMainComponentIconSizeHeight(), Image.SCALE_DEFAULT);
        imageIcon.setImage(scaledInstance);
        this.setName(AllBoundSetting.TOP_ICO_COMPONENT_NAME);
        this.setBounds(AllBoundSetting.getMainComponentIconLocalX(), AllBoundSetting.getMainComponentIconLocalY(),AllBoundSetting.getMainComponentIconSizeWidth(),AllBoundSetting.getMainComponentIconSizeHeight());
        System.out.println("图标"+AllBoundSetting.getMainComponentIconSizeWidth()+"*"+AllBoundSetting.getMainComponentIconSizeHeight());
        this.setVisible(true);
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!MainComponent.editPattern){
                    LoginJFrame loginJFrame =new LoginJFrame();
                }else {
                    TopIconLabel.iniEditPattern(!MainComponent.editPattern);
                }
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

        //移动
        MouseActionPush.setPushAction(this,MainComponent.createComponent());
        //改变大小按钮
        ChangeSizeButton changeSizeButtonIcon = ChangeSizeButtonFaction.createChangeSizeButton(this, MainComponent.createComponent());
        this.add(changeSizeButtonIcon);

        this.setIcon(imageIcon);
    }
    public static void iniEditPattern( boolean editPattern){
        //隐藏&显示所有的拖拽按钮
        ChangeSizeButtonFaction.turnEdit(editPattern);
        //设定状态为编辑，普通模式
        MainComponent.editPattern = editPattern;
        //回复按钮显示&隐藏
        MainComponent.recoverButton.setVisible(editPattern);
        //删除按钮显示&隐藏
        IndustryCategoryComponent.createComponent().placeComponents();
        TechnicalAreaComponent.createComponent().placeComponents();
        MainSwitchComponent.createComponent().placeComponents();
        //刷新界面
        switch (LeftModule.mainType){
            case LeftModule.INDUSTRY_CATEGORYCOMPONENT:
                IndustryCategoryComponent.createComponent().placeComponents();
                break;
            case LeftModule.TECHNICAL_ALAREA:
                TechnicalAreaComponent.createComponent().placeComponents();
                break;
            case LeftModule.MAIN_SWITCH:
                MainSwitchComponent.createComponent().placeComponents();
                break;
        }
    }

    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
        AllBoundSetting.setMainComponentIconSizeHeight(height);
        AllBoundSetting.setMainComponentIconSizeWidth(width);
    }

    @Override
    public void flushComponent() {
        URL url = TopIconLabel.class.getResource("/img/icon.png");
        imageIcon = new ImageIcon(url);
        scaledInstance = imageIcon.getImage().getScaledInstance(AllBoundSetting.getMainComponentIconSizeWidth(),AllBoundSetting.getMainComponentIconSizeHeight(), Image.SCALE_DEFAULT);
        imageIcon.setImage(scaledInstance);
        this.setIcon(imageIcon);
    }
}
