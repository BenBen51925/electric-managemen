package utils;

import exception.SendException;
import temple.rightComponent.TechnicalDetailsComponent;

import java.awt.*;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefaultStringUtils {

    public static final String urlStart = "http://";
    public static final String fileStart = "file:///";
    public static final String urlsStart="https://";

    public static byte[] hax16ByStr(String a) throws Exception{
        //String a="01 FF FC 84";
        String va[]=a.split(" ");
        byte vd[]=new byte[va.length];
        for(int i=0; i<va.length; i++){
            vd[i]=(byte)Integer.parseInt(va[i], 16);
        }
        //输出结果看看——负数因为超出范围，成了补数了
        for(int i=0; i<vd.length; i++){
            System.out.println(vd[i]+" ");
        }
        return vd;
    }
    public static String selectedString(ItemSelectable is) {
        Object selected[] = is.getSelectedObjects();
        return ((selected.length == 0) ? "null" : (String) selected[0]);
    }

    /**
     * 判断是否含有特殊字符
     *
     * @param str
     * @return true为包含，false为不包含
     */
    public static boolean isSpecialChar(String str) {
        if (str==null){
            throw new SendException("字符不能为空",0);
        }
        if ("".equals(str)){
            return true;
        }
        // String regEx = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        String regEx = "[ _.`~!@#$%^&*()+=|{}':;',\\[\\]<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }

    /**
     * 格式化url
     * @param order
     * @return
     */
    public static String serizationUrl(String order){
        if (order!=null&&!"".equals(order)){
            //路径为相对路径则加载数据
            URL resource = TechnicalDetailsComponent.class.getResource("/detail/" + order+".html");
            if (resource!=null){
                order = resource.getPath();
                order = "file://"+order;
            }
        }
        boolean isUrlStart = order.startsWith(urlStart);
        boolean isFileStart = order.startsWith(fileStart);
        boolean isurlsStart = order.startsWith(urlsStart);
        if (isUrlStart || isFileStart||isurlsStart) {

        } else {
            if (order.startsWith("www")) {
                order=urlStart + order;
            } else {
                order=fileStart + order;
            }
        }
        return order;
    }

}
