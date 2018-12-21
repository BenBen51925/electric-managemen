package frame;


import stcpream.AllBoundSetting;

import temple.MainComponent;

import temple.rightComponent.TechnicalDetailsComponent;
import temple.rightLayComponetn.RightComponent;

import utils.LoadProperties;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.Position;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
@Deprecated
public class SetJFrame extends JFrame {
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
     * 技术区详情
     */
    private static JLabel detailsLabel = new JLabel();
    /**
     * 技术区详情
     */
    public static JLabel detailsTextField = new JLabel();
    private static JEditorPane editorPaneEdit = new JEditorPane();
    private static JScrollPane scrollPane_1 = new JScrollPane();
    private static JLabel addImage = new JLabel("添加图片");
    /**
     * 技术区详情展示
     */
    private static JLabel detailsLabelView = new JLabel("技术区详情展示");
    private static JEditorPane editorPaneView = new JEditorPane();
    /**
     * 添加按钮
     */
    private static JButton jButton = new JButton("添加按钮");

    HTMLEditorKit editorKit = new HTMLEditorKit();
    HTMLDocument  document = (HTMLDocument) editorKit.createDefaultDocument();

    private static SetJFrame setJFrame;
    private SetJFrame(){
        super("设置");
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static SetJFrame createJFrame(){
        if (setJFrame ==null){
            setJFrame = new SetJFrame();
        }
        return setJFrame;
    }


    public void init() throws IOException {
        //主界面
        GridLayout gridLayout=new GridLayout(5,2);
        gridLayout.setHgap(10);
        gridLayout.setVgap(10);
        setLayout(gridLayout);
        this.setBackground(null);
        setResizable(false);
        this.setSize(sizeWidth, sizeHight);
        //设置frame居于屏幕中央方式1
        java.awt.Toolkit toolkit=java.awt.Toolkit.getDefaultToolkit();
        Dimension screenSize=toolkit.getScreenSize();
        double screenWidth=screenSize.getWidth();
        double screenHeight=screenSize.getHeight();
        this.setLocation((int)(screenWidth-this.getWidth())/2,(int)(screenHeight-this.getHeight())/2);
        //设置关闭
        this.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e)
            {
               SetJFrame.this.setVisible(false);
            }});
        this.setLayout(null);
        comboBox.addItem("技术区");
        comboBox.addItem("行业分类");


        comboBox.addActionListener((ActionEvent e)-> {
            String selectedItem = ((JComboBox) e.getSource()).getSelectedItem().toString();
            switch (selectedItem){
                case "技术区":
                    internaitonOpen16.setVisible(false);
                    internaitonOpen16TextField.setVisible(false);
                    internaitonClose16.setVisible(false);
                    internaitonClose16TextField.setVisible(false);
                    detailsLabel.setVisible(true);
                    scrollPane_1.setVisible(true);
                    detailsLabelView.setVisible(true);
                    addImage.setVisible(true);
                    break;
                case "行业分类":
                    internaitonOpen16.setVisible(true);
                    internaitonOpen16TextField.setVisible(true);
                    internaitonClose16.setVisible(true);
                    internaitonClose16TextField.setVisible(true);
                    detailsLabel.setVisible(false);
                    scrollPane_1.setVisible(false);
                    detailsLabelView.setVisible(false);
                    addImage.setVisible(false);
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

        addImage.setBounds(10,60,130,25);
        this.add(addImage);
        addImage.setVisible(true);



        //detailsLabel.setBounds(0,100,800,200);
        //detailsLabel.setForeground(new Color(0xC4C4BD));
        //this.add(detailsLabel);
        detailsTextField.setBackground(new Color(39,146,97));
        detailsTextField.setBounds(10, 90, 800, 300);
        detailsTextField.setSize(TechnicalDetailsComponent.getTextLabelSizeWidth(),300);

        //detailsTextField.setTabSize(4);
        //detailsTextField.setFont(new Font("微软雅黑", Font.BOLD, 12));
        //detailsTextField.setLineWrap(true);// 激活自动换行功能
        //detailsTextField.setWrapStyleWord(true);// 激活断行不断字功能

        editorPaneEdit.setOpaque(false);
        editorPaneEdit.setBackground(null);
        editorPaneEdit.setVisible(true);
        editorPaneEdit.setEditable(true);     //请把editorPane设置为只读，不然显示就不整齐
        editorPaneEdit.setContentType("text/html");
        editorPaneEdit.setBounds(0,0,detailsTextField.getWidth(),detailsTextField.getHeight());
        //editorPaneEdit.setBounds(0,0,detailsLabelView.getWidth(),detailsLabelView.getHeight());
        detailsTextField.add(editorPaneEdit);

        editorPaneEdit.setDocument(document);

        scrollPane_1.setBounds(detailsTextField.getX(), detailsTextField.getY(), detailsTextField.getWidth(), detailsTextField.getHeight());
        scrollPane_1.setViewportView(detailsTextField);//添加滚动条
        this.add(scrollPane_1);
        File file = new File("F:\\Eclipse_workplace\\Swing中的常用组件\\src\\ch10\\error.html");
        editorPaneEdit.setPage(file.toURI().toURL());
        addImage.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                try {
                    //int offset = document.getStartPosition().getOffset();
                    Element[] rootElement = document.getRootElements();
                    document.insertBeforeEnd(rootElement[0],"<image src=\"file:"+LoadProperties.class.getResource("/img/back.jpg").getPath()+"\" width=\"120\" height=\"80\" alt=\"本地图片\"/>");
                } catch (BadLocationException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                //获取光标位置

                int caretPosition = editorPaneEdit.getCaretPosition();



                //String text = editorPaneEdit.getText();

                //StringBuffer sb = new StringBuffer(text);
                //sb.insert(caretPosition,"<image src=\"file:"+LoadProperties.class.getResource("/img/back.jpg").getPath()+"\" width=\"120\" height=\"80\" alt=\"本地图片\"/>").toString();
                //editorPaneEdit.setText(sb.toString());
                super.mouseClicked(e);
            }
        });
        editorPaneEdit.getDocument().addDocumentListener(new DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
                int offset = e.getOffset();
                //detailsLabelView.setText(detailsTextField.getText());
                //editorPaneView.setText(editorPaneEdit.getText());
                String replace = editorPaneEdit.getText().replace("\n", "<br/>");
                replace = replace.replace(" ","&ensp;");
                replace = replace.replace("<html><br/>&ensp;&ensp;<head><br/>&ensp;&ensp;&ensp;&ensp;<br/>&ensp;&ensp;</head><br/>&ensp;&ensp;<body><br/>&ensp;&ensp;&ensp;&ensp;","<html><head></head><body>");
                replace = replace.replace("<br/>&ensp;&ensp;</body><br/></html><br/>","</body></html>");
                replace.replace("<font&ensp;","<font ");
                replace.replace("<img&ensp;","<img ");
                editorPaneView.setText(replace);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                //detailsLabelView.setText(detailsTextField.getText());
                //editorPaneView.setText(editorPaneEdit.getText());
                String replace = editorPaneEdit.getText().replace("\n", "<br/>");
                replace = replace.replace(" ","&ensp;");
                replace = replace.replace("<html><br/>&ensp;&ensp;<head><br/>&ensp;&ensp;&ensp;&ensp;<br/>&ensp;&ensp;</head><br/>&ensp;&ensp;<body><br/>&ensp;&ensp;&ensp;&ensp;","<html><head></head><body>");
                replace = replace.replace("<br/>&ensp;&ensp;</body><br/></html><br/>","</body></html>");
                replace.replace("font&ensp;","font ");
                replace.replace("img&ensp;","img ");
                editorPaneView.setText(replace);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });
        /*detailsTextField.getDocument().addDocumentListener(new DocumentListener(){
        });*/
        detailsLabelView.setVerticalAlignment(SwingConstants.TOP);


        //detailsLabelView.setForeground(Color.WHITE);
        detailsLabelView.setBounds(10,450, detailsTextField.getWidth(),detailsTextField.getHeight());
        detailsLabelView.setBackground(new Color(39,146,97));
        detailsLabelView.setOpaque(true);
        detailsLabelView.setVisible(true);
        detailsLabelView.setText("");


        editorPaneView.setVisible(true);
        editorPaneView.setOpaque(false);
        editorPaneView.setBackground(null);
        editorPaneView.setEditable(false);     //请把editorPane设置为只读，不然显示就不整齐
        //editorPaneView.setContentType("text/html");
        editorPaneView.setBounds(0,0,detailsLabelView.getWidth(),detailsLabelView.getHeight());

        editorPaneView.addHyperlinkListener((hyperlinkEvent)->{

        });
        String defaultView = "<image src=\"http://www.sd.sgcc.com.cn/html/files/2018-09/06/20180906085519902734887.jpg\" alt=\"网络图片\"/>"+
                "   <font style=\"color: #FFFFFF; line-height: 200%;letter-spacing:3px;\">"+
                "       技术区详情展示文本内容"+
                "   </font>" +
                "   <image src=\"file:"+LoadProperties.class.getResource("/img/back.jpg").getPath()+"\" width=\"120\" height=\"80\" alt=\"本地图片\"/>";
        editorPaneEdit.setText(defaultView);
        editorPaneView.setText(editorPaneEdit.getText());
        detailsLabelView.add(editorPaneView);
        JScrollPane scrollPane_2 = new JScrollPane();
        scrollPane_2.setBounds(detailsLabelView.getX(), detailsLabelView.getY(), detailsLabelView.getWidth(), detailsLabelView.getHeight());
        scrollPane_2.setViewportView(detailsLabelView);//添加滚动条
        this.add(detailsLabelView);

        JButton jButton =new JButton("确定添加");
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedItem = (String)comboBox.getSelectedItem();
                String mainButton =null;
                switch (selectedItem){
                    case "技术区":mainButton = "technicalAreaButtons";
                        String text = detailsTextField.getText();
                        LoadProperties.saveButton(mainButton,buttonNameTextField.getText(),null,null,text);
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
        // 设置界面可见
        this.setVisible(true);
    }
    public static void addButtonSet(RightComponent rightComponent){
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO it 添加界面
                SetJFrame setJFrame = SetJFrame.createJFrame();
                setJFrame.setVisible(true);
            }
        });
        jButton.setBounds(AllBoundSetting.getRightComponentSizeWidth() -100,AllBoundSetting.getRightComponentSizeHeight()-50,70,40);
        jButton.setVisible(MainComponent.editPattern);
        rightComponent.add(jButton);
    }
}
