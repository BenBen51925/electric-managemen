import java.awt.*;
import java.net.URL;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import javax.swing.*;

public class MainClass  extends JPanel{
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final String urlStart = "http://";
    private static final String fileStart = "file://";
    private static URL resource = FanxingTest.class.getResource("details.html");
    private static final String url = fileStart+resource.getPath(); //"http://blog.csdn.net/ml3947";
    private static MainClass mainClass = new MainClass();

    public static void main(String[] ar){

        mainClass.init();
        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        JFrame frame1 =new JFrame();
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.setLayout(null);
        frame1.setVisible(true);
        frame1.add(mainClass);
        frame1.setSize(WIDTH, HEIGHT);
        frame1.setLocation((screenWidth - WIDTH) / 2, (screenHeight - HEIGHT) / 2);
    }
    public void init() {

        MainClass frame = mainClass;
        JFXPanel webBrowser = new JFXPanel();
        frame.setBackground(null);
        frame.paintComponents(null);

        frame.setLayout(new BorderLayout());
        frame.add(webBrowser, BorderLayout.CENTER);
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                Group root = new Group();
                Scene scene = new Scene(root, WIDTH, HEIGHT);
                webBrowser.setScene(scene);
                Double widthDouble = new Integer(WIDTH).doubleValue();
                Double heightDouble = new Integer(HEIGHT).doubleValue();

                VBox box = new VBox(10);
                HBox urlBox = new HBox(10);
                final TextField urlTextField = new TextField();
                urlTextField.setText(url);
                Button go = new Button("go");
                urlTextField.setPrefWidth(WIDTH - 70);
                urlBox.getChildren().addAll(urlTextField, go);

                WebView view = new WebView();
                view.setMinSize(widthDouble, heightDouble);
                view.setPrefSize(widthDouble, heightDouble);
                final WebEngine eng = view.getEngine();

                eng.load(url);

                root.getChildren().add(view);

                box.getChildren().add(urlBox);
                box.getChildren().add(view);
                root.getChildren().add(box);

                go.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                        //String text = urlTextField.getText();
                        if (urlTextField.getText().startsWith(urlStart) || urlTextField.getText().startsWith(fileStart)) {
                            eng.load(urlTextField.getText());
                        } else {
                            if (urlTextField.getText().startsWith("www")) {
                                eng.load(urlStart + urlTextField.getText());
                            } else {
                                eng.load(fileStart + urlTextField.getText());
                            }
                        }
                    }
                });
            }
        };
        Platform.runLater(runnable);


        frame.setSize(WIDTH, HEIGHT);
        frame.setLocation(0,0);
        frame.setVisible(true);



    }

    @Override
    public void paintComponents(Graphics g) {
        //super.paintComponents(g);
        AlphaComposite composite = AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, 0.5f);
        Graphics2D g2 = (Graphics2D) g;
        if (g2!=null){
            g2.setComposite(composite);
        }
    }
}