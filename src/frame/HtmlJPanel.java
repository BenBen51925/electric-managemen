package frame;

import io.github.drxaos.browser.FxBrowser;
import io.github.drxaos.browser.WebAppURLStreamHandlerFactory;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.scene.web.WebHistory;
import temple.rightComponent.TechnicalDetailsComponent;
import utils.DefaultStringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.net.URL;



public class HtmlJPanel extends JPanel {
    private static int WIDTH = TechnicalDetailsComponent.getTextLabelSizeWidth();
    private static int HEIGHT = TechnicalDetailsComponent.getTextLabelSizeHeight();
    public static final String urlStart = "http://";
    public static final String fileStart = "file:///";
    public static final String urlsStart="https://";
    public static FxBrowser fxBrowser;

    private static HtmlJPanel htmlJFrame;
    public static JLabel statusbar;
    public static JTextField urlField;
    public static boolean fristLoadFrameFlag = true;

    private HtmlJPanel(){
        super();
    }
    public static HtmlJPanel createComponent(){
        if (htmlJFrame==null){
            htmlJFrame = new HtmlJPanel();
        }
        return htmlJFrame;
    }
    {
        URL.setURLStreamHandlerFactory(new WebAppURLStreamHandlerFactory(HtmlJPanel.class));
    }
    public static synchronized void chanText(String url){
        urlField.setText(url);
    }

    /**
     * 初始化浏览器组件
     */
    public void init() {
        this.setBackground(null);
        this.paintComponents(null);
        this.setLayout(new BorderLayout());
        //调用
        //第一次加载需要加载浏览器
        if(fristLoadFrameFlag){
            SwingUtilities.invokeLater(sampleBrowser);
            fristLoadFrameFlag = false;
        }
        this.setSize(WIDTH, HEIGHT);
        this.setLocation(0,0);
        this.setVisible(true);
    }
    Runnable sampleBrowser = ()->{
        {
            fxBrowser = new FxBrowser("http://www.baidu.com");
            this.add(fxBrowser, BorderLayout.CENTER);
            fxBrowser.addOnLoaderStateChanged(new ChangeListener<Worker.State>() {
                @Override
                public void changed(ObservableValue<? extends Worker.State> observable, Worker.State oldValue, Worker.State newValue) {
                    statusbar.setText(newValue.name());
                    if (newValue == Worker.State.SUCCEEDED) {
                        //frame.setTitle(fxBrowser.getTitle());
                    }
                }
            });
            fxBrowser.addOnRelocate((url)-> {
                urlField.setText(url);
            });
        }
        {
            statusbar = new JLabel("Loading...");
            this.add(statusbar, BorderLayout.SOUTH);
        }
        {
            JPanel toolbar = new JPanel();
            toolbar.setLayout(new BorderLayout());
            this.add(toolbar, BorderLayout.NORTH);
            {
                JPanel buttons = new JPanel();
                buttons.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
                toolbar.add(buttons, BorderLayout.WEST);

                JButton back = new JButton("<<");
                back.setVisible(false);
                JButton forward = new JButton(">>");
                forward.setVisible(false);
                buttons.add(back);
                back.addActionListener((e)->{
                    Platform.runLater(()->{
                        WebHistory webHistory = fxBrowser.getWebHistory();
                        webHistory.go(-1);
                        //判断还有没有下一页
                        int index = webHistory.getCurrentIndex() - 1;
                        ObservableList<WebHistory.Entry> entries = webHistory.getEntries();
                        if (index < 0 || index >= entries.size()) {
                            back.setVisible(false);
                        }else {
                            back.setVisible(true);
                        }
                        //设定上一页显示
                        forward.setVisible(true);
                    });

                });
                buttons.add(forward);
                forward.addActionListener((e)-> {
                    Platform.runLater(() -> {
                        WebHistory webHistory = fxBrowser.getWebHistory();
                        webHistory.go(1);
                        //判断还有没有上一页
                        int index = webHistory.getCurrentIndex() + 1;
                        ObservableList<WebHistory.Entry> entries = webHistory.getEntries();
                        if (index < 0 || index >= entries.size()) {
                            forward.setVisible(false);
                        }else {
                            forward.setVisible(true);
                        }
                        //设定下一页显示
                        back.setVisible(true);
                    });
                });
                JButton reload = new JButton("刷新");
                buttons.add(reload);
                reload.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent e) {
                        Platform.runLater(()-> {
                            fxBrowser.reload();
                        });
                    }
                });
                JButton go = new JButton("前往");
                buttons.add(go);
                go.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent e) {
                        Platform.runLater(()-> {
                            String url = urlField.getText();
                            url= DefaultStringUtils.serizationUrl(url);
                            urlField.setText(url);
                            fxBrowser.load(url);
                        });
                        //判断还有没有上一页
                        int index = fxBrowser.getWebHistory().getCurrentIndex() + 1;
                        ObservableList<WebHistory.Entry> entries = fxBrowser.getWebHistory().getEntries();
                        if (index < 0 || index >= entries.size()) {
                            forward.setVisible(false);
                        }else {
                            forward.setVisible(true);
                        }
                        //判断还有没有下一页
                        index = fxBrowser.getWebHistory().getCurrentIndex() - 1;
                        if (index < 0 || index >= entries.size()) {
                            back.setVisible(false);
                        }else {
                            back.setVisible(true);
                        }
                    }
                });

                urlField = new JTextField("about:blank");
                toolbar.add(urlField, BorderLayout.CENTER);
                urlField.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent e) {
                        Platform.runLater(()-> {
                            boolean isUrlStart = urlField.getText().startsWith(urlStart);
                            boolean isFileStart = urlField.getText().startsWith(fileStart);
                            boolean isurlsStart = urlField.getText().startsWith(urlsStart);
                            if (isUrlStart || isFileStart||isurlsStart) {
                                String text = urlField.getText();
                                fxBrowser.load(text);
                            } else {
                                if (urlField.getText().startsWith("www")) {
                                    fxBrowser.load(urlStart + urlField.getText());
                                } else {
                                    fxBrowser.load(fileStart + urlField.getText());
                                }
                            }
                        });
                    }
                });
            }
        }
    };
    @Override
    public void paintComponents(Graphics g) {
        super.paintComponents(g);
        AlphaComposite composite = AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, 1f);
        Graphics2D g2 = (Graphics2D) g;
        if (g2!=null){
            g2.setComposite(composite);
        }
    }
}
