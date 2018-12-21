package temple.rightComponent;


import stcpream.AllBoundSetting;

import temple.MainComponent;
import temple.button.RightButton;
import temple.rightLayComponetn.RightComponent;

import utils.LoadProperties;


import java.util.List;

/**
 * 技术区界面
 */
public class TechnicalAreaComponent extends RightComponent {

    private static TechnicalAreaComponent technicalAreaComponent;
    public TechnicalAreaComponent(){
        super();
    }
    public static TechnicalAreaComponent createComponent(){
        if (technicalAreaComponent==null){
            technicalAreaComponent = new TechnicalAreaComponent();
        }
        technicalAreaComponent.placeComponents();
        return technicalAreaComponent;
    }
    public void placeComponents() {
        this.removeAll();
        this.setBounds(getLocalX(),getLocalY(),getSizeWidth(),getSizeHeight());
        System.out.println("TechnicalAreaComponent像素大小"+getSizeWidth()+"*"+getSizeHeight());

        AllBoundSetting.gridLayout.setHgap(getButtonHgap());
        AllBoundSetting.gridLayout.setVgap(getButtonVgap());
        setLayout(AllBoundSetting.gridLayout);
        this.setBackground(null);
        this.setOpaque(false);
        //获取key对应的value值
        List<RightButton> jButtons =LoadProperties.inidButton(LoadProperties.TECHNICAL_AREA_BUTTON);
        //将按钮加入technical容器
        for (RightButton jButton:jButtons){
            jButton.setDelButtonVisit(MainComponent.editPattern);
            this.add(jButton);
        }
        //SetJFrame.addButtonSet(this);
        repaint();
    }
}
