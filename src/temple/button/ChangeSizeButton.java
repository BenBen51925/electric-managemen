package temple.button;

import action.ChangeSizeAction;
import action.interfaceActionMatch.ChangeSizeActionFulsh;
import exception.SendException;
import stcpream.StaticSet;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Logger;

import static java.lang.Thread.sleep;

public class ChangeSizeButton extends JLabel {

    int sizeWeith;
    int sizeHeight;
    Component component;
    Component outComponent;
    ChangeSizeAction changeSizeAction;
    Logger logger =Logger.getLogger("改变大小");
    public ChangeSizeButton(){
        super();
        placeComponents();
    }
    public void placeComponents(){
        if (component!=null){
            sizeWeith = component.getWidth()/10;
            sizeHeight = component.getWidth()/10;
            this.setBounds(component.getWidth()-sizeWeith,component.getHeight()-sizeHeight,sizeWeith,sizeHeight);
        }


        changeSizeAction = ChangeSizeAction.setChangeSizeAction(this, component, outComponent);
        this.setBackground(Color.magenta);
        this.setOpaque(true);
        this.setVisible(false);
    }

    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {

        if (component!=null){
            if (component.getWidth()==0||component.getHeight()==0){
                new Thread(){
                    @Override
                    public void run() {
                        while (component.getWidth()==0||component.getHeight()==0){
                            try {
                                sleep(1000l);
                                logger.info("未设定大小,1秒后重新获取");
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        sizeWeith = component.getWidth()/10;
                        sizeHeight = component.getHeight()/10;

                        if (sizeWeith>sizeHeight){
                            sizeWeith = sizeHeight;
                        }else {
                            sizeHeight = sizeWeith;
                        }
                        ChangeSizeButton.this.setSize(sizeWeith,sizeHeight);
                        ChangeSizeButton.this.setLocation(component.getWidth()-sizeWeith,component.getHeight()-sizeHeight);
                        super.run();
                    }
                }.start();
            }
            sizeWeith = component.getWidth()/10;
            sizeHeight = component.getHeight()/10;

            if (sizeWeith>sizeHeight){
                sizeWeith = sizeHeight;
            }else {
                sizeHeight = sizeWeith;
            }
            this.setSize(sizeWeith,sizeHeight);
            this.setLocation(component.getWidth()-sizeWeith,component.getHeight()-sizeHeight);

            //this.setBounds(component.getWidth()-sizeWeith,component.getHeight()-sizeHeight,sizeWeith,sizeHeight);
        }
        //Image image = new ImageIcon("/img/90a799ca37ae809c76b438890b99a3c0.png").getImage();
        Image image = new ImageIcon(getClass().getResource(StaticSet.CHANGE_SIZE_MOUSE)).getImage();
        Point point = new Point(10, 20);
        Cursor coursor = Toolkit.getDefaultToolkit().createCustomCursor(image,point, "箭头");
        setCursor(coursor);
        changeSizeAction.setAddActComponent(component);
        this.component = component;

    }

    public Component getOutComponent() {
        return outComponent;
    }

    public void setOutComponent(Component outComponent) {
        changeSizeAction.setOutComponent(outComponent);
        this.outComponent = outComponent;
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        //TODO it
        //g.drawImage(new ImageIcon(getClass().getResource(StaticSet.CHANGE_SIZE_BUTTON)).getImage(),0,0,this.getWidth(),this.getHeight(),this);
    }
}
