package temple.button.buttonFaction;

import exception.SendException;
import temple.button.ChangeSizeButton;

import java.awt.*;
import java.util.ArrayList;

/**
 * change工厂
 */
public class ChangeSizeButtonFaction {
    /**
     * 初始加载数量
     */
    private static int initNum = 7;

    /**
     * 最小数量(剩余数量小于这个值就会调用append方法)
     */
    private static int MAIN_NUM = 10;

    /**
     * 每次工厂产生的数量
     */
    private static int APPEND_NUM = 20;
    //工程交标位置
    private static int useIndex = 0;
    private static ArrayList<ChangeSizeButton> changeSizeButtons =new ArrayList<>();
    static {
        for (int i = 0;i<initNum; i++){
            ChangeSizeButton changeSizeButton =new ChangeSizeButton();
            changeSizeButtons.add(changeSizeButton);
        }
    }
    public static synchronized ChangeSizeButton createChangeSizeButton(Component component,Component outComponent){
        if (component==null){
            throw new SendException("没有注册组件",0);
        }
        if (outComponent==null){
            throw new SendException("没有外部组件",0);
        }
        ChangeSizeButton changeSizeButton = changeSizeButtons.get(useIndex++);
        changeSizeButton.setComponent(component);
        changeSizeButton.setOutComponent(outComponent);
        append();
        return changeSizeButton;
    }
    public static synchronized void append(){
        if (changeSizeButtons.size()-useIndex<MAIN_NUM){
            for (int i = 0;i<APPEND_NUM; i++){
                ChangeSizeButton changeSizeButton =new ChangeSizeButton();
                changeSizeButtons.add(changeSizeButton);
            }
        }
    }
    //显示/隐藏所有按钮
    public static void turnEdit(boolean whetherVisit){
        for (ChangeSizeButton changeSizeButton :changeSizeButtons){
            changeSizeButton.setVisible(whetherVisit);
        }
    }
}
