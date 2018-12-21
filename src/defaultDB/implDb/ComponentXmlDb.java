package defaultDB.implDb;

import defaultDB.XmlDb;
import entity.ComponentBean;
import exception.SendException;

import java.awt.*;
@Deprecated
public class ComponentXmlDb  extends XmlDb<Component> {
    public static final String BUTTON_TYPE = "BUTTON";
    public static final String LABEL_TYPE = "LABEL";

    private String fileName = "component_bounds.xml";

    public static void verifyBean(Component componentBean){
        if (componentBean ==null){
            throw new SendException("实体pojo不能为空",0);
        }
        if (componentBean.getName()==null){
            throw new SendException("实体componentType类型不能为空",0);
        }else {
            switch(componentBean.getName()){
                case BUTTON_TYPE:
                    break;
                case LABEL_TYPE:
                    break;
                default:
                    throw new SendException("实体类型不明确:"+componentBean.getName(),0);
            }
        }
        if (componentBean.getName()==null){
            throw new SendException("组件名称name不能为空",0);
        }
    }
    public void save(Component componentBean){
        verifyBean(componentBean);
        this.addNode(fileName,componentBean.getName(),componentBean.getName(),componentBean);
    }

    public void update(Component componentBean){
        verifyBean(componentBean);
        this.updateNode(fileName,componentBean.getName()+"//"+componentBean.getName(),componentBean.getName(),componentBean);
    }

    public Component getByName(String name,String type){
        if (name ==null){
            throw new SendException("不能为空",0);
        }
        Component byName = this.getByName(name,type, fileName);
        return byName;
    }
    public void saveAndUpdate(Component componentBean){
        verifyBean(componentBean);
        Component componentBean1 = getByName(componentBean.getName(), componentBean.getName());
        if (componentBean1==null){
            save(componentBean);
        }else {
            update(componentBean);
        }
    }
}
