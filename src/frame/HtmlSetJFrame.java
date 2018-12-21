package frame;


import entity.ButtonFunctionBean;
import exception.SendException;
import io.github.drxaos.browser.FxBrowser;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;

import javafx.scene.web.WebHistory;
import stcpream.AllBoundSetting;
import stcpream.StaticSet;
import temple.MainComponent;

import temple.rightLayComponetn.RightComponent;
import utils.DefaultStringUtils;
import utils.LoadProperties;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import java.io.File;
import java.io.IOException;



public class HtmlSetJFrame extends JFrame {
    public static final int sizeWidth = 1200;
    public static final int sizeHight = 800;

    /**
     * 主界面分类
     */
    private static JLabel mainJL = new JLabel("主界面分类");
    /**
     * 所属分类
     */
    private static JComboBox mainComboBox=new JComboBox();

    /**
     * 按键名称
     */
    private static JLabel buttonNameLabel = new JLabel("按键名称");
    /**
     * 创建文本域用于用户输入
     */
    private static JTextField buttonNameTextField = new JTextField(20);
    /**
     * open16位信息
     */
    private static JLabel internaitonOpen16 = new JLabel("关闭系统16位信息");
    /**
     * open创建文本域用于用户输入
     */
    private static JTextField internaitonOpen16TextField = new JTextField(20);

    /**
     * close16位信息
     */
    private static JLabel internaitonClose16 = new JLabel("开启系统16位信息");
    /**
     * close创建文本域用于用户输入
     */
    private static JTextField internaitonClose16TextField = new JTextField(20);

    /**
     *doc编辑器
     */
    private static JLabel editDoc =new JLabel("打开编辑器");

    private static FxBrowser fxBrowser;
    //信息反馈栏
    public static JLabel statusbar;
    //导航栏
    public static JTextField urlField;
    //工具栏
    public static JPanel toolbar;
    private static String url = "http://www.baidu.com";

    /**
     * 添加按钮
     */
    private static JButton jButton = new JButton();

    private static HtmlSetJFrame setJFrame;
    private HtmlSetJFrame(){
        super("设置");
        try {
            init();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static HtmlSetJFrame createJFrame(){
        if (setJFrame ==null){
            setJFrame = new HtmlSetJFrame();
        }
        return setJFrame;
    }

    Runnable sampleBrowser = ()->{
        {
            fxBrowser = new FxBrowser(url);
            fxBrowser.setBounds(10, 90, 800, 300);
            this.add(fxBrowser);
            fxBrowser.addOnLoaderStateChanged(new ChangeListener<Worker.State>() {
                @Override
                public void changed(ObservableValue<? extends Worker.State> observable, Worker.State oldValue, Worker.State newValue) {
                    statusbar.setText(newValue.name());
                    if (newValue == Worker.State.SUCCEEDED) {
                        HtmlSetJFrame.this.setTitle(fxBrowser.getTitle());
                    }
                }
            });
            fxBrowser.addOnRelocate((url)-> {
                urlField.setText(url);
            });
        }
        {
            statusbar = new JLabel("Loading...");
            statusbar.setBounds(10,120,130,25);
            this.add(statusbar, BorderLayout.SOUTH);
        }
        {
            toolbar = new JPanel();
            toolbar.setLayout(new BorderLayout());
            this.add(toolbar, BorderLayout.NORTH);
            toolbar.setBounds(10,60,1200,25);
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
                            url=DefaultStringUtils.serizationUrl(url);
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
                JButton chosseButton=new JButton("选择目录");
                buttons.add(chosseButton);
                chosseButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        JFileChooser chooser = new JFileChooser(StaticSet.getDetail());

                        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                        int i = chooser.showOpenDialog(null);
                        switch (i){
                            case JFileChooser.CANCEL_OPTION:
                                break;
                            case JFileChooser.APPROVE_OPTION:
                                File selectedFile = chooser.getSelectedFile();
                                urlField.setText(selectedFile.getPath());
                                break;
                            //如果发生错误或者该对话框已被解除
                            case JFileChooser.ERROR_OPTION :
                                break;
                        }
                    }
                });
                //this.add(chosseButton);
                urlField = new JTextField("about:blank");
                toolbar.add(urlField, BorderLayout.CENTER);
                urlField.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent e) {
                        Platform.runLater(()-> {
                            String url = urlField.getText();
                            url=DefaultStringUtils.serizationUrl(url);
                            urlField.setText(url);
                            fxBrowser.load(url);
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
                        });
                    }
                });
            }
        }
    };

    /**
     * 设置参数
     */
    public static void setViewModel(ButtonFunctionBean buttonFunctionBean){
        if (buttonFunctionBean==null){
            return;
        }
        HtmlSetJFrame.mainComboBox.setSelectedItem(buttonFunctionBean.getMainComboBox());
        HtmlSetJFrame.buttonNameTextField.setText(buttonFunctionBean.getRightButtonName());
        switch (buttonFunctionBean.getMainComboBox()){
            case "技术区":
                //设定地址
                urlField.setText(buttonFunctionBean.getUrl());
                //加载地址
                urlField.getActionListeners()[0].actionPerformed(null);
                break;
            case "行业分类":
                internaitonClose16TextField.setText(buttonFunctionBean.getInternaitonClose16());
                internaitonOpen16TextField.setText(buttonFunctionBean.getInternaitonOpen16());
                break;
        }
        comboBoxChange(buttonFunctionBean.getMainComboBox());



    }
    public static void comboBoxChange(String selectedItem){
        switch (selectedItem){
            case "技术区":
                internaitonOpen16.setVisible(false);
                internaitonOpen16TextField.setVisible(false);
                internaitonClose16.setVisible(false);
                internaitonClose16TextField.setVisible(false);
                if (fxBrowser!=null){
                    fxBrowser.setVisible(true);
                }
                if (toolbar!=null){
                    toolbar.setVisible(true);
                }
                break;
            case "行业分类":
                internaitonOpen16.setVisible(true);
                internaitonOpen16TextField.setVisible(true);
                internaitonClose16.setVisible(true);
                internaitonClose16TextField.setVisible(true);
                fxBrowser.setVisible(false);
                toolbar.setVisible(false);
                break;
        }
    }
    public void init() throws InterruptedException {
        //主界面
        GridLayout gridLayout=new GridLayout(5,2);
        gridLayout.setHgap(10);
        gridLayout.setVgap(10);
        setLayout(gridLayout);
        this.setBackground(null);
        setResizable(false);
        this.setSize(sizeWidth, sizeHight);
        //设置frame居于屏幕中央方式1
        Toolkit toolkit= Toolkit.getDefaultToolkit();
        Dimension screenSize=toolkit.getScreenSize();
        double screenWidth=screenSize.getWidth();
        double screenHeight=screenSize.getHeight();
        this.setLocation((int)(screenWidth-this.getWidth())/2,(int)(screenHeight-this.getHeight())/2);
        //设置关闭
        this.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e)
            {
               HtmlSetJFrame.this.setVisible(false);
            }});
        this.setLayout(null);
        mainComboBox.addItem("技术区");
        mainComboBox.addItem("行业分类");
        mainComboBox.addActionListener((e)-> {
            String selectedItem = ((JComboBox) e.getSource()).getSelectedItem().toString();
            comboBoxChange(selectedItem);
            System.out.println("Selected index=" + selectedItem);
        });
        mainJL.setBounds(10,20,130,25);
        this.add(mainJL);
        mainComboBox.setBounds(150,20,165,25);
        this.add(mainComboBox);

        buttonNameLabel.setBounds(325,20,130,25);
        buttonNameLabel.setText("按键名称");
        buttonNameLabel.setVisible(true);
        this.add(buttonNameLabel);
        buttonNameTextField.setBounds(465,20,165,25);
        this.add(buttonNameTextField);

        internaitonOpen16.setBounds(10,60,130,25);
        this.add(internaitonOpen16);
        internaitonOpen16.setVisible(false);
        internaitonOpen16TextField.setBounds(150,60,165,25);
        this.add(internaitonOpen16TextField);
        internaitonOpen16TextField.setVisible(false);
        internaitonClose16.setBounds(325,60,130,25);
        this.add(internaitonClose16);
        internaitonClose16.setVisible(false);
        internaitonClose16TextField.setBounds(465,60,165,25);
        this.add(internaitonClose16TextField);
        internaitonClose16TextField.setVisible(false);

        //调用
        SwingUtilities.invokeLater(sampleBrowser);
        while (fxBrowser!=null){
            System.err.println("未加载fxBrowser,1秒后重试");
            Thread.sleep(1200l);

        }


        JButton jButton =new JButton("确定添加");
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedItem = (String)mainComboBox.getSelectedItem();
                String mainButton =null;
                switch (selectedItem){
                    case "技术区":mainButton = "technicalAreaButtons";
                        //地址保存需要做数据转换，不然在读取时候不认识file:/以及https;//
                        //TODO it 当有无法读取的信息时，需要在保存的时候进行修改
                        String text = urlField.getText();
                        text=text.replace("file:/","");
                        text=text.replace("https://","http://");
                        LoadProperties.saveButton(mainButton,buttonNameTextField.getText(),null,null,text);
                        break;
                    case "行业分类":mainButton = "industryCategoryButtons";
                        //TODO it
                        LoadProperties.saveButton(mainButton,buttonNameTextField.getText(),internaitonOpen16TextField.getText(),internaitonOpen16TextField.getText(),null);
                        break;
                    case "总开关":mainButton = "mainSwitchButton";
                        throw new SendException("不支持当前主页类型",0);
                }
            }
        });
        jButton.setBounds(200,650,120,25);
        editDoc.setBounds(400,650,120,25);
        editDoc.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                XDocFrame xDocFrame =new XDocFrame();
                xDocFrame.init();
            }
        });
        this.add(editDoc);
        this.add(jButton);
        // 设置界面可见
        this.setVisible(true);
    }
    public static void addButtonSet(RightComponent rightComponent){
        jButton.addActionListener((actionEvent)->{
            //TODO it 添加界面
            HtmlSetJFrame setJFrame = HtmlSetJFrame.createJFrame();
            setJFrame.setVisible(true);

        });
        jButton.setBounds(AllBoundSetting.getRightComponentSizeWidth() -100,AllBoundSetting.getRightComponentSizeHeight()-50,70,40);
        jButton.setVisible(MainComponent.editPattern);
        jButton.setText("添加");
        rightComponent.add(jButton);
    }
}
