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
@Deprecated
public class MainSwitchButton extends MainButton {
    private String name = "总开关";
    public MainSwitchButton(String buttonText) {
        super(buttonText, LeftModule.MAIN_SWITCH);
        paintComponents();
    }
    public void paintComponents() {
        resource = getClass().getResource("/img/zongkaiguan_world.png");
        imageIcon = new ImageIcon(resource);
        ComponentBean complateXmlDbByName = complateXmlDb.getByName(name, complateXmlDb.MAIN_BUTTON_TYPE);
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
            mainButtonPathY = AllBoundSetting.getMainButtonPathY()+2*(AllBoundSetting.getMainButtonPathInterval()+AllBoundSetting.getMainButtonSizeHight());
        }
        if (mainButtonSizeWidth==null){
            mainButtonSizeWidth = AllBoundSetting.getMainButtonSizeWidth();
        }
        if (mainButtonSizeHeight==null){
            mainButtonSizeHeight = AllBoundSetting.getMainButtonSizeHight();
        }
        setLocation(mainButtonPathX, mainButtonPathY);
        setSize(mainButtonSizeWidth,mainButtonSizeHeight);
        iconLabel.setBounds(iconLabelLocalX,iconLabelLocalY,iconLabelSizeWidth,iconLabelSizeHeight);
        scaledInstance = imageIcon.getImage().getScaledInstance(iconLabelSizeWidth, iconLabelSizeHeight, Image.SCALE_DEFAULT);
        imageIcon.setImage(scaledInstance);
        iconLabel.setIcon(imageIcon);
    }
}
