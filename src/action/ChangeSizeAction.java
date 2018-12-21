package action;

import action.interfaceActionMatch.ChangeSizeActionFulsh;
import exception.SendException;
import temple.LeftModule;
import temple.MainComponent;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ChangeSizeAction  extends MouseAdapter {
    //默认鼠标位置
    public int intx;
    public int inty;
    //当前鼠标位置
    public int r0x;
    public int r0y;
    //是否拖动
    public boolean isPressed = false;
    //注册组件
    private Component addActComponent;
    //触发组件
    private Component trigger;
    //外部组件(设定超期)
    private Component outComponent;

    public Component getAddActComponent() {
        return addActComponent;
    }

    public void setAddActComponent(Component addActComponent) {
        this.addActComponent = addActComponent;
    }

    public Component getTrigger() {
        return trigger;
    }

    public void setTrigger(Component trigger) {
        this.trigger = trigger;
    }

    public Component getOutComponent() {
        return outComponent;
    }

    public void setOutComponent(Component outComponent) {
        this.outComponent = outComponent;
    }

    public ChangeSizeAction (Component trigger, Component component, Component outComponent){
        super();
        addActComponent = component;
        this.outComponent = outComponent;
        this.trigger = trigger;
    }
    @Override
    public void mousePressed(MouseEvent e) {
        if (!MainComponent.editPattern){
            return;
        }
        super.mousePressed(e);
        intx = e.getXOnScreen();
        inty = e.getYOnScreen();
        isPressed = true;
        LeftModule.createMainModule().repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e){
        super.mouseDragged(e);
        if (!MainComponent.editPattern){
            return;
        }
        if (isPressed){
            r0x=e.getXOnScreen();
            r0y=e.getYOnScreen();
            //当鼠标移动
            String str="鼠标所在的坐标:("+r0x+","+r0y+")";
            //判定超限
            if(r0x-outComponent.getX()>(addActComponent.getX()+7)&&r0x-outComponent.getX() < outComponent.getWidth()){
                addActComponent.setSize(addActComponent.getWidth() +(r0x-intx),addActComponent.getHeight());
            }
            if(r0y-outComponent.getY()>(addActComponent.getY()+7)&&r0y-outComponent.getY() < outComponent.getHeight()){
                addActComponent.setSize(addActComponent.getWidth(),addActComponent.getHeight()+(r0y-inty));
            }
            System.out.println(str);
            //设置按钮永远在右下方
            int width = this.addActComponent.getWidth() / 10;
            int height = this.addActComponent.getHeight() / 10;
            if (width>height){
                height = width;
            }else {
                width = height;
            }
            this.trigger.setSize(width,height);
            int x = this.addActComponent.getWidth() - this.trigger.getWidth();
            int y = this.addActComponent.getHeight() - this.trigger.getHeight();
            this.trigger.setLocation(x,y);
            //初始化鼠标位置
            intx = e.getXOnScreen();
            inty = e.getYOnScreen();
            //组件刷新
            if (addActComponent instanceof ChangeSizeActionFulsh){
                try {
                    Method flushComponent = addActComponent.getClass().getMethod("flushComponent");
                    flushComponent.invoke(addActComponent);
                } catch (NoSuchMethodException e1) {
                    e1.printStackTrace();
                } catch (IllegalAccessException e1) {
                    e1.printStackTrace();
                } catch (InvocationTargetException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        super.mousePressed(e);
        if (!MainComponent.editPattern){
            return;
        }
        isPressed = false;
    }

    /**
     *
     * @param trigger 事件注册组件
     * @param performer 事件触发组件
     *                  outComponent外部容器
     */
    public static ChangeSizeAction setChangeSizeAction(Component trigger,Component performer,Component outComponent){
        /*if (outComponent ==null){
            throw new SendException("没有外部容器",0);
        }*/
        //注册事件
        ChangeSizeAction changeSizeAction = new ChangeSizeAction(trigger,performer,outComponent);
        //注册鼠标拖拽监听
        trigger.addMouseMotionListener(changeSizeAction);
        //添加拖拽监听
        trigger.addMouseListener(changeSizeAction);

        return changeSizeAction;
    }
}
