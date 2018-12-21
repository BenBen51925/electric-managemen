package action;

import exception.SendException;
import frame.MainJframe;
import temple.LeftModule;
import temple.MainComponent;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Logger;

public class MouseActionPush extends MouseAdapter {
    Logger logger =Logger.getLogger("鼠标拖拽事件");
    public boolean isPress = false;
    int xinit = 0;	 //鼠标按下时所处的X坐标
    int yinit = 0;   //鼠标按下时所处的Y坐标
    int x0 = 0;		//拖拽过程中鼠标的位置
    int y0 = 0;
    Integer maxX;
    Integer maxY;

    private Component addActComponent;
    private Component maxOutCompoent;

    /**
     *
     * @param component 添加事件的组件
     * @param maxX 最大x轴
     * @param maxY  最小x轴
     */
    public MouseActionPush (Component component,Integer maxX,Integer maxY){
        super();
        addActComponent = component;
        this.maxX = maxX;
        this.maxY = maxY;
    }

    /**
     *
     * @param component 添加事件的组件
     * @param maxOutCompoent 最大不超过的组件大小
     */
    @Deprecated
    public MouseActionPush (Component component,Component maxOutCompoent){
        super();
        this.addActComponent = component;
        this.maxOutCompoent = maxOutCompoent;
    }
    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        if (!MainComponent.editPattern){
            return;
        }
        isPress = true;
        xinit = e.getXOnScreen();
        yinit = e.getYOnScreen();
        logger.info("初始点击位置:("+xinit+","+yinit+")");
        LeftModule.createMainModule().repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e){
        if (!MainComponent.editPattern){
            return;
        }
        x0=e.getXOnScreen();
        y0=e.getYOnScreen();
        int x = addActComponent.getX();
        int y = addActComponent.getY();
        if (maxOutCompoent !=null){
            if (maxX==null||maxOutCompoent.getWidth()!=0){
                maxX = maxOutCompoent.getWidth();
            }
            if (maxY==null||maxOutCompoent.getHeight()!=0){
                maxY = maxOutCompoent.getHeight();
            }
        }else {
            if (maxX==0){
                maxX = MainJframe.createMainJframe().getWidth();
            }
            if (maxY==0){
                maxY = MainJframe.createMainJframe().getHeight();
            }
        }
        //当鼠标移动
        if (isPress){
            String a = "鼠标所在的坐标: ("+x0+","+y0+")";
            String b = "初始点击位置:("+xinit+","+yinit+")";
            String d = "拖拽距离:("+(x0- xinit)+","+(y0- yinit)+")";
            String c = "组件位置("+(x+(x0- xinit))+","+(y+(y0- yinit))+")";
            logger.info(a+"-"+b+"-"+d+"-"+c);

            if (x+(x0- xinit)<=maxX-addActComponent.getWidth()&&x+(x0- xinit)>=0){
                addActComponent.setLocation(x+(x0- xinit), y);
                x = x+(x0- xinit);
                //初始化鼠标位置
                xinit = e.getXOnScreen();
            }else {
                logger.info("x轴超出界限");
            }
            if (y+(y0- yinit)<=maxY-addActComponent.getHeight()&&y+(y0- yinit)>=0){
                addActComponent.setLocation(x,y+(y0- yinit));
                y= y+(y0- yinit);
                //初始化鼠标位置
                yinit = e.getYOnScreen();
            }else {
                logger.info("y轴超出界限");
            }


        }
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        super.mousePressed(e);
        if (!MainComponent.editPattern){
            return;
        }
        isPress = false;
    }
    public static void setPushAction(Component addActComponent,Integer maxX,Integer maxY){
        //注册事件
        MouseActionPush IconMouseActionPush = new MouseActionPush(addActComponent,maxX,maxY);
        //注册鼠标拖拽监听
        addActComponent.addMouseMotionListener(IconMouseActionPush);
        //添加拖拽监听
        addActComponent.addMouseListener(IconMouseActionPush);
    }
    @Deprecated
    public static void setPushAction(Component addActComponent,Component outMaxCom){
        if (addActComponent ==null){
            throw new SendException("拖拽注册组件不能为空",0);
        }
        if (outMaxCom ==null){
            throw new SendException("最大组件容量不能为空",0);
        }
        //注册事件
        MouseActionPush IconMouseActionPush = new MouseActionPush(addActComponent,outMaxCom);
        //注册鼠标拖拽监听
        addActComponent.addMouseMotionListener(IconMouseActionPush);
        //添加拖拽监听
        addActComponent.addMouseListener(IconMouseActionPush);
    }
}
