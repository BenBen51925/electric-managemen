package defaultDB.implDb;

import defaultDB.XmlDb;
import entity.ComponentBean;
import exception.SendException;
import frame.FristLoadFrame;
import stcpream.StaticSet;

import java.io.*;
import java.net.URL;

public class ComplateXmlDb<T extends ComponentBean> extends XmlDb<ComponentBean>{
    public static final String MAIN_BUTTON_TYPE = "MAIN_BUTTON";
    public static final String MAIN_COMPONENT_TYPE = "mainComponent";
    public static final String TOP_ICON_LABEL_COMPONENT_TYPE = "topIcoLabelComponent";
    public static final String TOP_TEXT_LABEL_COMPONENT_TYPE = "topTextLabelComponent";
    public static final String DOWN_TEXT_LABEL_COMPONENT_TYPE = "downTextLabelComponent";
    public static final String RIGHT_COMPONENT_TYPE = "rightComponent";
    public static String fileName = "component_bounds.xml";

    static {
        //检测文件

        System.err.println("获取到的项目根目录:"+ StaticSet.getRootPath());
        File file =new File(StaticSet.getRootPath()+fileName);
        boolean exists = file.exists();
        if (!exists){
            try {
                if (file.createNewFile()){
                    System.err.println("创建文件:"+file.getPath());
                }
                FileOutputStream out =new FileOutputStream(file);
                String wirteStr="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                        "<bound/>";
                out.write(wirteStr.getBytes());
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void verifyBean(ComponentBean componentBean){
        if (componentBean ==null){
            throw new SendException("实体pojo不能为空",0);
        }
        if (componentBean.getComponentType()==null){
            throw new SendException("实体componentType类型不能为空",0);
        }else {
        }
        if (componentBean.getName()==null){
            throw new SendException("组件名称name不能为空",0);
        }
    }
    public void save(ComponentBean componentBean){
        verifyBean(componentBean);
        this.addNode(fileName,componentBean.getComponentType(),componentBean.getName(),componentBean);
    }

    public void update(ComponentBean componentBean){
        verifyBean(componentBean);
        this.updateNode(fileName,componentBean.getComponentType()+"//"+componentBean.getName(),componentBean.getName(),componentBean);
    }

    public ComponentBean getByName(String name,String type){
        if (name ==null){
           throw new SendException("不能为空",0);
        }
        ComponentBean byName = this.getByName(name,type, fileName);
        return byName;
    }
    public void saveAndUpdate(ComponentBean componentBean){
        verifyBean(componentBean);
        ComponentBean componentBean1 = getByName(componentBean.getName(), componentBean.getComponentType());
        if (componentBean1==null){
            save(componentBean);
        }else {
            update(componentBean);
        }
    }
    public void removeAll(){
        this.removeAll(fileName);
    }

}
