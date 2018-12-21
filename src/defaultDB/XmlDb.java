package defaultDB;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.*;
import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import com.sun.beans.finder.FieldFinder;
import com.sun.webkit.dom.NodeImpl;
import exception.SendException;
import org.w3c.dom.*;
import org.xml.sax.SAXException;


/**
 * xml文件读取，增删改节点处理
 *
 */
abstract public class XmlDb<T>{


    Class<T> entityClass;
    private List<String> commonNodeArr = new ArrayList<String>();
    public String[] fieldNames;
    public Field[] fields;

    {
        Class<T> tClass = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        entityClass = tClass;
        try {
            if (entityClass!=null){
                T model = (T) entityClass.newInstance();
                Field[] fields=model.getClass().getDeclaredFields();
                this.fields = fields;
                fieldNames=new String[fields.length];
                for(int i=0;i<fields.length;i++){
                    //System.out.println(fields[i].getType());
                    fieldNames[i]=fields[i].getName();
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    /**
     * @description 获取文件路径
     * @return String
     */
    public  String getPath(String flieName){
        String path = "";
        path= XmlDb.class.getProtectionDomain().getCodeSource().getLocation().getFile();
        return path +flieName;
    }
    /**
     * @description 从XML文件中读取Map
     * @return String
     */
    public  String getMapByXML(String fileName){
        DocumentBuilderFactory df = DocumentBuilderFactory.newInstance();
        File f = new File(getPath(fileName));
        DocumentBuilder db = null;
        Element element = null;
        String jsonStr = "";
        try{
            db=df.newDocumentBuilder();
            Document dt = db.parse(f);
            element = dt.getDocumentElement();
            NodeList childNodes = element.getChildNodes();
            StringBuffer json = new StringBuffer("[");
            XmlDb<T> p = new XmlDb<T>(){};
            json = p.iterationNode(childNodes, true, "0",json);
            jsonStr = json.substring(0, json.length()-1)+"]";
        }catch(Exception e){
            e.printStackTrace();
        }
        return jsonStr;
    }
    /**
     * 迭代获取节点
     * childNodes 子节点集合
     * hasChildren 是否有子节点
     * id id值
     * json 字符串
     */
    private StringBuffer iterationNode(NodeList childNodes,boolean hasChildren,String id,StringBuffer json){
        if(hasChildren){
            for( int i=1;i<childNodes.getLength() && childNodes!=null;i+=2){
                Node node = childNodes.item(i);
                json.append("{id:\""+id+"_"+i+"\",");
                json.append("pId:\""+id+"\",");
                json.append("name:\""+node.getNodeName()+"\",");
                json.append("open:0,");
                for (String fieldName:fieldNames){
                    json.append("lt:\""+node.getAttributes().getNamedItem(fieldName).getNodeValue()+"\",");//经度
                }
                /*json.append("la:\""+node.getAttributes().getNamedItem(LABEL_2).getNodeValue()+"\",");//纬度
                json.append("la:\""+node.getAttributes().getNamedItem(LABEL_3).getNodeValue()+"\",");//纬度
                json.append("la:\""+node.getAttributes().getNamedItem(LABEL_4).getNodeValue()+"\",");//纬度
                json.append("la:\""+node.getAttributes().getNamedItem(LABEL_5).getNodeValue()+"\",");//纬度*/
                if((node.getChildNodes()).getLength()>1 && node.getChildNodes()!=null){
                    json.append("isParent:1},");
                    json = iterationNode(node.getChildNodes(),true,id+"_"+i,json);
                }else{
                    json.append("isParent:0},");
                }
            }
        }
        return json;
    }


    /**
     * 根据节点参数查询节点
     * @param express 节点路径
     * @param source 搜索节点源
     * @return 查询到的第一个节点
     */
    public  Node selectSingleNode(String express, Element source) {
        Node result = null;
        //创建XPath工厂
        XPathFactory xPathFactory = XPathFactory.newInstance();
        //创建XPath对象
        XPath xpath = xPathFactory.newXPath();
        try {
            result = (Node) xpath.evaluate(express, source,XPathConstants.NODE);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
            //System.out.println(e.getMessage());
            return null;
        }
        return result;
    }


    /**
     * 添加节点
     * @param fileName 文件名
     * @param nodeFullName 添加节点相对路径
     * @param name 节点名
     * @return 操作结果
     */
    public  String addNode(String fileName, String nodeFullName,String name,T label) {
        //提示信息
        String msg = null;
        DocumentBuilderFactory df = DocumentBuilderFactory.newInstance();
        File f = new File(getPath(fileName));
        DocumentBuilder db = null;
        try{
            db=df.newDocumentBuilder();
            Document xmlDoc = db.parse(f);
            Element root = xmlDoc.getDocumentElement();
            Element node = xmlDoc.createElement(name);
            for (Field field:fields){
                String strValue = getValue(field,label);
                node.setAttribute(field.getName(), strValue);
            }

            if("".equals(nodeFullName) || nodeFullName == null){
                //获取同级节点
                NodeList preNode = root.getChildNodes();
                for (int i = 0; i < preNode.getLength(); i++) {
                    if(name.equals(preNode.item(i).getNodeName())){
                        throw new Exception("存在相同名称");
                    };
                }
                root.appendChild(node);
            } else {
                Element nodeParent = (Element) selectSingleNode(nodeFullName, root);
                if (nodeParent==null){
                    Element element = xmlDoc.createElement(nodeFullName);
                    root.appendChild(element);
                    nodeParent = element;
                }
                //获取同级节点
                NodeList preNode = nodeParent.getChildNodes();
                for (int i = 0; i < preNode.getLength(); i++) {
                    if(name.equals(preNode.item(i).getNodeName())){
                        throw new Exception("存在相同名称");
                    };
                }
                nodeParent.appendChild(node);
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty("encoding", "utf-8");
            transformer.setOutputProperty("indent", "yes");
            transformer.transform(new DOMSource(xmlDoc), new StreamResult(new File(getPath(fileName))));
            //output(xmlDoc);
        }catch(Exception e){
            e.printStackTrace();
            msg = e.getMessage();
        }
        return msg;
    }
    /**
     * 修改节点
     * @param fileName 文件名
     * @param nodeFullName 添加节点相对路径
     * @param name 节点名
     * @return 操作结果
     */
    public String updateNode(String fileName, String nodeFullName, String name,T label) {
        //提示信息
        String msg = null;
        DocumentBuilderFactory df = DocumentBuilderFactory.newInstance();
        File f = new File(getPath(fileName));
        DocumentBuilder db = null;
        try{
            db=df.newDocumentBuilder();
            Document xmlDoc = db.parse(f);
            Element root = xmlDoc.getDocumentElement();
            Element node = (Element) selectSingleNode(nodeFullName, root);
            if (node==null){
                Element element = xmlDoc.createElement(nodeFullName);
                root.appendChild(element);
                node = element;
            }
            //获取同级节点
            xmlDoc.renameNode(node, null, name);
            for (Field field:fields){
                String strValue = getValue(field,label);
                if (strValue!=null){
                    node.setAttribute(field.getName(),strValue);
                }
            }
			/*Element newElement = xmlDoc.createElement(name);
			newElement.setAttribute("经度",jd);
			newElement.setAttribute("纬度",wd);
			node.getParentNode().replaceChild(newElement, node);*/
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty("encoding", "utf-8");
            transformer.setOutputProperty("indent", "yes");
            transformer.transform(new DOMSource(xmlDoc), new StreamResult(new File(getPath(fileName))));
            //output(xmlDoc);
        }catch(Exception e){
            e.printStackTrace();
            msg = e.getMessage();;
        }
        return msg;
    }
    /**
     * 删除节点
     * @param fileName 文件名
     * @param nodeFullName 相对位置
     * @return 操作结果
     */
    public  String delNode(String fileName, String nodeFullName) {
        //提示信息
        String msg = null;
        DocumentBuilderFactory df = DocumentBuilderFactory.newInstance();
        df.setIgnoringElementContentWhitespace(true);
        File f = new File(getPath(fileName));
        DocumentBuilder db = null;
        try{
            db=df.newDocumentBuilder();
            Document xmlDoc = db.parse(f);
            //获取根节点
            Element root = xmlDoc.getDocumentElement();
            //定位节点
            Element node = (Element) selectSingleNode(nodeFullName, root);
            if(node == null){
                throw new Exception("已删除");
            }
            //删除节点
            Element nodeParent = (Element) node.getParentNode();
            nodeParent.removeChild(node);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty("encoding", "utf-8");
            transformer.setOutputProperty("indent", "yes");
            transformer.transform(new DOMSource(xmlDoc), new StreamResult(new File(getPath(fileName))));
            //output(xmlDoc);
        }catch(Exception e){
            e.printStackTrace();
            msg = e.getMessage();
        }
        return msg;
    }

    /**
     * 移除所有
     * @param fileName 文件名
     * @return 操作结果
     */
    public  String removeAll(String fileName) {
        //提示信息
        String msg = null;
        DocumentBuilderFactory df = DocumentBuilderFactory.newInstance();
        df.setIgnoringElementContentWhitespace(true);
        File f = new File(getPath(fileName));
        DocumentBuilder db = null;
        try{
            db=df.newDocumentBuilder();
            Document xmlDoc = db.parse(f);
            //获取根节点
            Element root = xmlDoc.getDocumentElement();
            //Node parentNode = root.getParentNode();
            //root.removeChild(parentNode);
            //定位节点
            NodeList childNodes = root.getChildNodes();
            Node parentNode = root.getParentNode();
            //删除节点
            for(int i =0;i<childNodes.getLength();){
                Node item = childNodes.item(i);
                root.removeChild(item);
            }
            NodeList childNodes1 = root.getChildNodes();
            System.out.println(childNodes1.getLength());
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty("encoding", "utf-8");
            transformer.setOutputProperty("indent", "yes");
            transformer.transform(new DOMSource(xmlDoc), new StreamResult(new File(getPath(fileName))));
            output(xmlDoc);
        }catch(Exception e){
            e.printStackTrace();
            msg = e.getMessage();
        }
        return msg;
    }
    /**
     * 移动节点
     * @param fileName 文件名
     * @param nodeFullName 节点相对路径
     * @param targetNodeFullname 目标节点相对路径
     * @return
     */
    public  String moveNode(String fileName, String nodeFullName, String targetNodeFullname,String moveType) {
        //提示信息
        String msg = null;
        DocumentBuilderFactory df = DocumentBuilderFactory.newInstance();
        File f = new File(getPath(fileName));
        DocumentBuilder db = null;
        try{
            db=df.newDocumentBuilder();
            Document xmlDoc = db.parse(f);
            Element root = xmlDoc.getDocumentElement();
            Element node = (Element) selectSingleNode(nodeFullName, root);
            Element moveNode = (Element) node.cloneNode(true);
            node.getParentNode().removeChild(node);
            Element targetNode = null;
            if("".equals(targetNodeFullname)){
                targetNode = root;
            }else {
                targetNode = (Element) selectSingleNode(targetNodeFullname, root);
            }
            if("inner".equals(moveType)){
                targetNode.appendChild(moveNode);
            }else if("prev".equals(moveType)){

            }else if("next".equals(moveType)){

            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty("encoding", "utf-8");
            transformer.setOutputProperty("indent", "yes");
            transformer.transform(new DOMSource(xmlDoc), new StreamResult(new File(getPath(fileName))));
            //output(xmlDoc);
        }catch(Exception e){
            e.printStackTrace();
            msg = e.getMessage();
        }
        return msg;
    }

    /**
     * 控制台输出节点
     * @param node 节点
     */
    public  void output(Node node){
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer;
        try {
            transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty("encoding", "utf-8");
            transformer.setOutputProperty("indent", "yes");
            DOMSource source = new DOMSource();
            source.setNode(node);
            StreamResult result = new StreamResult();
            result.setOutputStream(System.out);
            transformer.transform(source, result);
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取常用地址
     * @param fileName 文件名
     * @return 操作结果
     */
    public  Map<String, Object> getCommonNode(String fileName) {
        //提示信息
        String msg = null;
        DocumentBuilderFactory df = DocumentBuilderFactory.newInstance();
        File f = new File(getPath(fileName));
        DocumentBuilder db = null;
        try{
            db=df.newDocumentBuilder();
            Document xmlDoc = db.parse(f);
            Element root = xmlDoc.getDocumentElement();
            NodeList nodes = root.getChildNodes();
            commonNodeArr.clear();
            if(nodes!= null){
                for (int i = 1; i < nodes.getLength(); i+=2) {
                    getNodeList(nodes.item(i),"3","常用","1");
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            msg = e.getMessage();;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("msg", msg);
        map.put("commonNodeArr", commonNodeArr);
        return map;
    }

    /**
     * 修改地址  常用属性
     * @param fileName 文件名
     * @param fullPathArr 修改节点相对路径数组    常用属性置为1
     * @return 操作结果
     */
    public  String updateCommonNode(String fileName, String[] fullPathArr) {
        //提示信息
        String msg = null;
        DocumentBuilderFactory df = DocumentBuilderFactory.newInstance();
        File f = new File(getPath(fileName));
        DocumentBuilder db = null;
        try{
            db=df.newDocumentBuilder();
            Document xmlDoc = db.parse(f);
            Element root = xmlDoc.getDocumentElement();
            NodeList nodes = root.getChildNodes();
            if(nodes!= null){
                for (int i = 1; i < nodes.getLength(); i+=2) {
                    getNodeList(nodes.item(i),"1","常用","0");
                }
            }
            for (String fullPath : fullPathArr) {
                Node n = selectSingleNode(fullPath, root);
                getNodeList(n,"2","常用","1");
            }
            //保存操作
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty("encoding", "utf-8");
            transformer.setOutputProperty("indent", "yes");
            transformer.transform(new DOMSource(xmlDoc), new StreamResult(new File(getPath(fileName))));
        }catch(Exception e){
            e.printStackTrace();
            msg = e.getMessage();;
        }
        return msg;
    }

    /**
     * 遍历修改节点属性
     * @param node 操作节点
     * @param type 1-移出属性 2-添加属性  3-根据属性值获取节点名
     * @param attr 属性名
     */
    public  void getNodeList(Node node,String type,String attr,String val){
        Element eNode = (Element)node;
        if("1".equals(type)){
            if(eNode.hasAttribute(attr)){
                eNode.setAttribute(attr, val);
            }
            NodeList nodes = node.getChildNodes();
            if(nodes!= null){
                for (int i = 1; i < nodes.getLength(); i+=2) {
                    getNodeList(nodes.item(i),type,attr,val);
                }
            }
        }else if("2".equals(type)){
            eNode.setAttribute(attr, val);
        }else if("3".equals(type)){
            if( val.equals(node.getAttributes().getNamedItem(attr).getNodeValue()) ){
                commonNodeArr.add(node.getNodeName());
            }
            NodeList nodes = node.getChildNodes();
            if(nodes!= null){
                for (int i = 1; i < nodes.getLength(); i+=2) {
                    getNodeList(nodes.item(i),type,attr,val);
                }
            }
        }

    }
    /**
     * 获取反射到的值
     * @param field
     * @param label
     * @return
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public String  getValue(Field field,T label) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String methodName = "get"+(field.getName().substring(0,1).toUpperCase())+field.getName().substring(1);
        //获取方法
        Method method = label.getClass().getMethod(methodName);
        Object value=method.invoke(label);
        String strValue = null;
        //反射的返回值类型进行定义
        //TODO id
        if (value ==null){
            return null;
        }
        switch (field.getType().toString()){
            case "class java.lang.String":
                strValue = (String) value;
                break;
            case "int"://支持int类型的属性
                strValue=String.valueOf((int)(value));
                break;
            case "class java.lang.Integer":
                strValue=String.valueOf((int)(value));
                break;
            default:
                throw new SendException("未知的类型:\""+field.getType().toString()+"\"请自定义",0);
        }
        return strValue;
    }
    /**
     * 放射设定值
     * @param field
     * @param label
     * @return
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public void setValue(Field field,T label,String value) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String methodName = "set"+(field.getName().substring(0,1).toUpperCase())+field.getName().substring(1);
        Class<?> type = field.getType();
        //获取方法
        Method method = label.getClass().getMethod(methodName,type);
        Object args = null;
        //TODO it
        if (value == null||"".equals(value)){
            return;
        }
        switch (type.toString()){
            case "int":
                args = Integer.parseInt(value);
                break;
            case "class java.lang.String":
                args = value;
                break;
            case "class java.lang.Integer":
                args = Integer.parseInt(value);
                break;
            default:
                throw new SendException("未知的类型:\""+field.getType().toString()+"\"请自定义",0);
        }
        method.invoke(label,args);
    }

    /**
     * 根据名称和类别获取数据
     * @param name
     * @param type
     * @param fileName
     * @return
     */
    public T getByName(String name,String type,String fileName){
        T t = null;
        DocumentBuilder db = null;
        DocumentBuilderFactory df = DocumentBuilderFactory.newInstance();
        File f = new File(getPath(fileName));
        try {
            t = entityClass.newInstance();
            db = df.newDocumentBuilder();
            Document xmlDoc = db.parse(f);
            Element root = xmlDoc.getDocumentElement();
            Element node = (Element) selectSingleNode(type, root);
            if (node==null){
                return null;
            }
            NodeList elementsByTagName = node.getElementsByTagName(name);
            Node item = elementsByTagName.item(0);
            if (item == null){
                return null;
            }
            NamedNodeMap attributes = item.getAttributes();
            for (Field field:fields){
                Node namedItem = attributes.getNamedItem(field.getName());
                if (namedItem!=null){
                    setValue(field,t,namedItem.getNodeValue());
                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return t;
    }


}