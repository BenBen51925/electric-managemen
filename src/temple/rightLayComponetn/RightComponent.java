package temple.rightLayComponetn;

import action.interfaceActionMatch.ChangeSizeActionFulsh;
import stcpream.AllBoundSetting;
import stcpream.AllStaticLocalSizeSet;
import temple.button.RightButton;
import temple.rightComponent.IndustryCategoryComponent;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

abstract public class RightComponent<T extends RightComponent> extends JPanel{

    //右侧最上层容器大小
    private static Integer sizeWidth;
    public static Integer getSizeWidth(){
        if (sizeWidth!=null){
            return sizeWidth;
        }
        return AllBoundSetting.getRightMiddleSizeWidth();
    }
    private static Integer sizeHeight;
    public static Integer getSizeHeight(){
        if (sizeHeight!=null){
            return sizeHeight;
        }
        return AllBoundSetting.getRightMiddleSizeHeight();
    }
    //位置
    private static Integer localX;
    public static Integer getLocalX(){
        if (localX!=null){
            return localX;
        }
        return AllBoundSetting.getRightComponentLocalX();
    }
    private static Integer localY;
    public static Integer getLocalY(){
        if (localY!=null){
            return localY;
        }
        return  AllBoundSetting.getRightComponentLocalY();
    }
    private static Integer buttonHgap;
    public static Integer getButtonHgap(){
        if (buttonHgap!=null){
            return buttonHgap;
        }
        return  AllBoundSetting.getButtonHgap();
    }
    private static Integer buttonVgap;
    public static Integer getButtonVgap(){
        if (buttonVgap!=null){
            return buttonVgap;
        }
        return  AllBoundSetting.getButtonVgap();
    }
    //获取key对应的value值
    public List<RightButton> jButtons =new ArrayList<RightButton>();
}
