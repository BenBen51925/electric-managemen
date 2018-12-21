package temple.rightComponent;


import frame.HtmlSetJFrame;
import frame.SetJFrame;
import temple.MainComponent;
import temple.button.RightButton;
import temple.rightLayComponetn.RightComponent;

import utils.LoadProperties;


import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * 总开关
 */
public class MainSwitchComponent extends RightComponent {
    private static MainSwitchComponent mainSwitchComponent;
    private Integer openAllButtonSizeWidth;
    public Integer getOpenAllButtonSizeWidth(){
        if (openAllButtonSizeWidth!=null){
            return openAllButtonSizeWidth;
        }
        return getSizeWidth()/4;
    }
    private Integer openAllButtonSizeHeight;
    public Integer getOpenAllButtonSizeHeight(){
        if (openAllButtonSizeHeight!=null){
            return openAllButtonSizeHeight;
        }
        return getSizeHeight()/6;
    }
    private Integer buttonLocalXLength;
    public Integer getButtonLocalXLength(){
        if (buttonLocalXLength!=null){
            return buttonLocalXLength;
        }
        return getOpenAllButtonSizeWidth()/4;
    }
    private Integer openAllButtonLocaly;
    public Integer getOpenAllButtonLocaly(){
        if (openAllButtonLocaly!=null){
            return openAllButtonLocaly;
        }
        return getSizeHeight()/2 -getOpenAllButtonSizeHeight()/2;
    }
    private Integer openAllButtonLocalX;
    public Integer getOpenAllButtonLocalX(){
        if (openAllButtonLocalX!=null){
            return openAllButtonLocalX;
        }
        return getSizeWidth()/2 -getButtonLocalXLength() - getOpenAllButtonSizeWidth();
    }


    private Integer closeAllButtonSizeWidth;
    public Integer getCloseAllButtonSizeWidth(){
        if (closeAllButtonSizeWidth!=null){
            return closeAllButtonSizeWidth;
        }
        return getOpenAllButtonSizeWidth();
    }
    private Integer closeAllButtonSizeHeight ;
    public Integer getCloseAllButtonSizeHeight(){
        if (closeAllButtonSizeHeight!=null){
            return closeAllButtonSizeHeight;
        }
        return getOpenAllButtonSizeHeight();
    }
    private Integer closeAllButtonLocalX;
    public Integer getCloseAllButtonLocalX(){
        if (closeAllButtonLocalX!=null){
            return closeAllButtonLocalX;
        }
        return getOpenAllButtonLocalX()+getOpenAllButtonSizeWidth()+2*getButtonLocalXLength();
    }
    private Integer closeAllButtonLocaly;
    public Integer getCloseAllButtonLocaly(){
        if (closeAllButtonLocaly!=null){
            return closeAllButtonLocaly;
        }
        return getOpenAllButtonLocaly();
    }

    public MainSwitchComponent(){
        super();
    }
    public static MainSwitchComponent createComponent(){
        if (mainSwitchComponent==null){
            mainSwitchComponent = new MainSwitchComponent();
        }
        return mainSwitchComponent;
    }

    public void placeComponents() {
        this.removeAll();
        this.setBounds(getLocalX(),getLocalY(),getSizeWidth(),getSizeHeight());
        System.out.println("MainSwitchComponent像素大小"+getSizeWidth()+"*"+getSizeHeight());
        this.setBackground(null);
        this.setOpaque(false);
        this.setLayout(null);
        jButtons = LoadProperties.inidButton(LoadProperties.MAIN_SWITCH_BUTTON);
        //将按钮加入technical容器
        for (Object o:jButtons){
            RightButton jButton= (RightButton)o;
            jButton.setDelButtonVisit(MainComponent.editPattern);
            switch (jButton.getButtonOrder()){
                case LoadProperties.OPEN_ALL://开启按钮
                    jButton.setBounds(getOpenAllButtonLocalX(),getOpenAllButtonLocaly(),getOpenAllButtonSizeWidth(),getOpenAllButtonSizeHeight());
                    //刷新行业类别的所有按钮
                    jButton.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            super.mouseClicked(e);
                            List jButtons = IndustryCategoryComponent.createComponent().jButtons;
                            for (Object o1:jButtons){
                                RightButton rightButton = (RightButton)o1;
                                rightButton.isOpen = true;
                            }
                        }
                    });
                    break;
                case LoadProperties.CLOSE_ALL://关闭按钮
                    jButton.setBounds(getCloseAllButtonLocalX(),getCloseAllButtonLocaly(),getCloseAllButtonSizeWidth(),getCloseAllButtonSizeHeight());
                    //刷新行业类别的所有按钮
                    jButton.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            super.mouseClicked(e);
                            List jButtons = IndustryCategoryComponent.createComponent().jButtons;
                            for (Object o1:jButtons){
                                RightButton rightButton = (RightButton)o1;
                                rightButton.isOpen = false;
                            }
                        }
                    });
                    break;
            }
            //jButton.setVisible(true);
            this.add(jButton);
        }
        //SetDocJFrame.addButtonSet(this);
        //SetJFrame.addButtonSet(this);
        HtmlSetJFrame.addButtonSet(this);
        repaint();
    }


}
