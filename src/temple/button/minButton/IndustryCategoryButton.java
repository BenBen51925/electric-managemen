package temple.button.minButton;

import defaultDB.implDb.ComplateXmlDb;
import entity.ComponentBean;
import frame.MainJframe;
import stcpream.AllBoundSetting;
import temple.LeftModule;
import temple.button.ChangeSizeButton;
import temple.button.MainButton;
import temple.button.buttonFaction.ChangeSizeButtonFaction;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.logging.Logger;

@Deprecated
public class IndustryCategoryButton extends MainButton {
    private String name = "行业类别";

    public IndustryCategoryButton(String buttonText) {
        super(buttonText, LeftModule.INDUSTRY_CATEGORYCOMPONENT);
        paintComponents();
    }
    public void paintComponents() {
        //图标
        resource = getClass().getResource("/img/hangyeleibie_world.png");
        imageIcon = new ImageIcon(resource);
        ComponentBean complateXmlDbByName = complateXmlDb.getByName(name, complateXmlDb.MAIN_BUTTON_TYPE);
        Integer mainButtonPathX = null;
        Integer mainButtonPathY = null;
        Integer mainButtonSizeWidth = null;
        Integer mainButtonSizeHeight = null;
        if (complateXmlDbByName != null){
            mainButtonPathX = complateXmlDbByName.getLocalX();
            mainButtonPathY = complateXmlDbByName.getLocalY();
            mainButtonSizeWidth = complateXmlDbByName.getSizeWidth();
            mainButtonSizeHeight = complateXmlDbByName.getSizeHeight();
            if (mainButtonSizeWidth!=null&&mainButtonSizeHeight!=null){
                iconLabelSizeWidth=mainButtonSizeWidth*2/3;
                iconLabelSizeHeight=mainButtonSizeHeight*2/3;
                iconLabelLocalX = (mainButtonSizeWidth - iconLabelSizeWidth)/2;
                iconLabelLocalY = (mainButtonSizeHeight-iconLabelSizeHeight)/2;
            }
        }
        if (mainButtonPathX == null){
            mainButtonPathX = AllBoundSetting.getMainButtonPathX();
        }
        if (mainButtonPathY==null){
            mainButtonPathY = AllBoundSetting.getMainButtonPathY() + AllBoundSetting.getMainButtonPathInterval() + AllBoundSetting.getMainButtonSizeHight();
        }
        if (mainButtonSizeWidth==null){
            mainButtonSizeWidth = AllBoundSetting.getMainButtonSizeWidth();
        }
        if (mainButtonSizeHeight==null){
            mainButtonSizeHeight = AllBoundSetting.getMainButtonSizeHight();
        }
        setLocation(mainButtonPathX, mainButtonPathY);
        setSize(mainButtonSizeWidth, mainButtonSizeHeight);
        iconLabel.setBounds(AllBoundSetting.getIconLabelLocalX(),AllBoundSetting.getIconLabelLocalY(),AllBoundSetting.getIconLabelSizeWidth(),AllBoundSetting.getIconLabelSizeHeight());
        scaledInstance = imageIcon.getImage().getScaledInstance(mainButtonSizeWidth*2/3, mainButtonSizeHeight*2/3, Image.SCALE_DEFAULT);

        imageIcon.setImage(scaledInstance);
        iconLabel.setIcon(imageIcon);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        //析构没用
    }
}
