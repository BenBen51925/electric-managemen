package temple.rightComponent;


import stcpream.AllBoundSetting;
import temple.MainComponent;
import temple.button.RightButton;
import temple.rightLayComponetn.RightComponent;

import utils.LoadProperties;

import java.util.List;

/**
 * 科技分类界面
 */
public class IndustryCategoryComponent extends RightComponent {
    private static IndustryCategoryComponent industryCategoryComponent;

    public IndustryCategoryComponent(){
        super();
    }
    public static IndustryCategoryComponent createComponent(){
        if (industryCategoryComponent==null){
            industryCategoryComponent = new IndustryCategoryComponent();
        }
        industryCategoryComponent.placeComponents();
        return industryCategoryComponent;
    }
    public void placeComponents() {
        this.removeAll();
        this.setBounds(getLocalX(),getLocalY(),getSizeWidth(),getSizeHeight());
        System.out.println("IndustryCategoryComponent像素大小"+getSizeWidth()+"*"+getSizeHeight());
        AllBoundSetting.gridLayout.setHgap(getButtonHgap());
        AllBoundSetting.gridLayout.setVgap(getButtonVgap());
        setLayout(AllBoundSetting.gridLayout);
        this.setBackground(null);
        this.setOpaque(false);
        //获取key对应的value值
        jButtons =LoadProperties.inidButton(LoadProperties.INDUSTRY_CATEGORY_BUTTON);
        //将按钮加入technical容器
        for (Object o:jButtons){
            RightButton jButton = (RightButton)o;
            jButton.setDelButtonVisit(MainComponent.editPattern);
            this.add(jButton);
        }
        repaint();
    }

}
