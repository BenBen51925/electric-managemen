package temple.rightLayComponetn;
import stcpream.AllBoundSetting;
import stcpream.AllStaticLocalSizeSet;
import temple.rightComponent.IndustryCategoryComponent;
import temple.rightComponent.MainSwitchComponent;
import temple.rightComponent.TechnicalAreaComponent;
import temple.rightComponent.TechnicalDetailsComponent;


import javax.swing.*;
import java.awt.*;

@Deprecated
public class RightMiddleComponent extends JPanel {


    private static RightMiddleComponent leftOutComponent;

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
    public RightMiddleComponent(){
        super();
        this.placeComponents();
    }
    public static RightMiddleComponent createComponent(){
        if (leftOutComponent==null){
            leftOutComponent = new RightMiddleComponent();
        }
        return leftOutComponent;
    }
    public void placeComponents() {
        this.setBounds(AllBoundSetting.getRightMiddleLocalX(),AllBoundSetting.getRightMiddleLocalY(),AllBoundSetting.getRightMiddleSizeWidth(),AllBoundSetting.getRightMiddleSizeHeight());
        System.out.println("RightMiddleComponent像素大小:"+AllBoundSetting.getRightMiddleSizeWidth()+"*"+AllBoundSetting.getRightMiddleSizeHeight());
        this.setBackground(Color.magenta);
        this.setOpaque(true);
        this.setLayout(null);
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
    }
}
