package frame;


import com.hg.xdoc.XDocBuilder;

import stcpream.AllBoundSetting;

import temple.MainComponent;

import temple.rightLayComponetn.RightComponent;
import utils.LoadProperties;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

@Deprecated
public class SetDocJFrame extends JFrame {
    public static final int sizeWidth = 1200;
    public static final int sizeHight = 800;

    /**
     * 主界面分类
     */
    private static JLabel mainJL = new JLabel("主界面分类");
    /**
     * 所属分类
     */
    private static JComboBox comboBox=new JComboBox();

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
     * 添加按钮
     */
    private static JButton jButton = new JButton("添加按钮");

    /**
     * 文本编辑器
     */
    private static XDocFrame xDocFrame =new XDocFrame();
    private static XDocBuilder builder;
    public static XDocBuilder getBuilder() {
        return builder;
    }

    private static SetDocJFrame setJFrame;
    private SetDocJFrame(){
        super("设置");
        init();
    }
    public static SetDocJFrame createJFrame(){
        if (setJFrame ==null){
            setJFrame = new SetDocJFrame();
        }
        return setJFrame;
    }

    public void initDoc(){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            //获取XDocBuilder实例
            builder = new XDocBuilder();
            //将XDocBuilder放入面板中
            this.getContentPane().add(builder, BorderLayout.CENTER);
            JToolBar bar = new JToolBar("工具条");
            JButton btn = new JButton("新建");
            btn.addActionListener((e)->{
                //新建
                builder.create();
            });
            btn = new JButton("打开");
            btn.addActionListener((e)->{
                //打开
                builder.open();

            });
            bar.add(btn);
            btn = new JButton("保存");
            btn.addActionListener((e)->{
                //保存当前文件
                builder.save();
            });
            bar.add(btn);
            btn = new JButton("全部保存");
            btn.addActionListener( (e)->{
                    for (int i = 0; i < builder.getXDocCount(); i++) {
                        //选择第i个文件
                        builder.activeXDoc(i);
                        //保存
                        builder.save();
                    }
            });
            bar.add(btn);
            btn = new JButton("关闭");
            btn.addActionListener((e)-> {
                    //关闭当前文件
                    builder.close();
            });

            bar.add(btn);
            btn = new JButton("关于");
            btn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    builder.about();
                }
            });
            bar.add(btn);
            Container content = this.getContentPane();
            content.add(bar, BorderLayout.NORTH);
            this.setSize(800, 600);
            this.setVisible(true);
            this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            this.addWindowListener(new WindowListener() {
                public void windowOpened(WindowEvent e) {}
                public void windowClosing(WindowEvent e) {}
                public void windowClosed(WindowEvent e) {}
                public void windowIconified(WindowEvent e) {}
                public void windowDeiconified(WindowEvent e) {}
                public void windowActivated(WindowEvent e) {}
                public void windowDeactivated(WindowEvent e) {}
            });
            this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void init(){
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
               SetDocJFrame.this.setVisible(false);
            }});
        this.setLayout(null);
        comboBox.addItem("行业分类");
        comboBox.addItem("技术区");
        initDoc();
        comboBox.addActionListener((ActionEvent e)-> {
            String selectedItem = ((JComboBox) e.getSource()).getSelectedItem().toString();
            switch (selectedItem){
                case "技术区":
                    internaitonOpen16.setVisible(false);
                    internaitonOpen16TextField.setVisible(false);
                    internaitonClose16.setVisible(false);
                    internaitonClose16TextField.setVisible(false);
                    builder.setVisible(true);
                    break;
                case "行业分类":

                    internaitonOpen16.setVisible(true);
                    internaitonOpen16TextField.setVisible(true);
                    internaitonClose16.setVisible(true);
                    internaitonClose16TextField.setVisible(true);
                    builder.setVisible(false);
                    break;
            }
            System.out.println("Selected index=" + selectedItem);
        });
        mainJL.setBounds(10,20,130,25);
        this.add(mainJL);
        comboBox.setBounds(150,20,165,25);
        this.add(comboBox);

        buttonNameLabel.setBounds(325,20,130,25);
        buttonNameLabel.setText("按键名称");
        buttonNameLabel.setVisible(true);
        this.add(buttonNameLabel);
        buttonNameTextField.setBounds(465,20,165,25);
        this.add(buttonNameTextField);

        internaitonOpen16.setBounds(10,60,130,25);
        this.add(internaitonOpen16);
        internaitonOpen16TextField.setBounds(150,60,165,25);
        this.add(internaitonOpen16TextField);
        internaitonClose16.setBounds(325,60,130,25);
        this.add(internaitonClose16);
        internaitonClose16TextField.setBounds(465,60,165,25);
        this.add(internaitonClose16TextField);

        builder.setBounds(10,60,800,500);
        builder.setVisible(false);
        this.add(builder);
        //获取展示栏
        /*JSplitPane applet = (JSplitPane) xDocViewer.getComponent(1);
        JScrollPane jScrollPane = (JScrollPane) applet.getComponent(2);
        jScrollPane.setBackground(Color.magenta);
        jScrollPane.setOpaque(false);
        jScrollPane.setBounds(10,300, TechnicalDetailsComponent.getTextLabelSizeWidth(),300);
        this.add(jScrollPane);*/



        JButton jButton =new JButton("确定添加");
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedItem = (String)comboBox.getSelectedItem();
                String mainButton =null;
                switch (selectedItem){
                    case "技术区":mainButton = "technicalAreaButtons";
                        LoadProperties.saveButton(mainButton,buttonNameTextField.getText(),null,null,builder.getXDoc().toXml());
                        break;
                    case "行业分类":mainButton = "industryCategoryButtons";
                        //TODO it
                        LoadProperties.saveButton(mainButton,buttonNameTextField.getText(),internaitonOpen16TextField.getText(),internaitonOpen16TextField.getText(),null);
                        break;
                    case "总开关":mainButton = "mainSwitchButton";
                        break;
                }
            }
        });
        jButton.setBounds(200,650,120,25);
        this.add(jButton);

        JButton openDoc =new JButton("打开富文本编辑器");
        openDoc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               xDocFrame.init();
            }
        });
        openDoc.setBounds(400,650,120,25);
        this.add(openDoc);
        // 设置界面可见
        this.setVisible(true);
    }
    public static void addButtonSet(RightComponent rightComponent){
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO it 添加界面
                SetDocJFrame setJFrame = SetDocJFrame.createJFrame();
                setJFrame.setVisible(true);
            }
        });
        jButton.setBounds(AllBoundSetting.getRightComponentSizeWidth() -100,AllBoundSetting.getRightComponentSizeHeight()-50,70,40);
        jButton.setVisible(MainComponent.editPattern);
        rightComponent.add(jButton);
    }
}
