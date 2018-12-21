import defaultDB.implDb.ComplateXmlDb;

import java.io.File;
import java.net.URL;

public class UserInfo {
    public static void main(String[] sd){
        URL resource = UserInfo.class.getResource(ComplateXmlDb.fileName);
        if (resource != null){
            File file =new File(resource.getPath());
            if (file.exists()){
                if (file.delete()){
                    System.out.println("个人配置已经删除:"+file.getPath());
                    System.exit(0);
                }
            }
        }
        System.err.println("个人配置已经删除失败,未配置个人配置");
    }
}