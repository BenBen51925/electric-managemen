import javafx.scene.web.WebView;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class FanxingTest{
    public static void main(String[] a) throws IOException {
        JFrame jFrame = new JFrame();
        jFrame.setBounds(0,0,1200,800);
        jFrame.setVisible(true);

        JEditorPane editorPane = new JEditorPane();
        editorPane.setLocation(0,0);
        editorPane.setSize(jFrame.getSize());
        editorPane.setVisible(true);
        jFrame.add(editorPane);
        URL resource = FanxingTest.class.getResource("details.html");
        if (resource==null){
            System.err.println("不存在");
        }
        editorPane.setEditable(false);     //请把editorPane设置为只读，不然显示就不整齐
        editorPane.setContentType("text/html");
        editorPane.setPage(resource);




    }
}