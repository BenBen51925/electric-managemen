package frame;


import com.hg.xdoc.*;

import stcpream.AllBoundSetting;


import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
@Deprecated
public class XDocFrame extends JFrame {
    private static XDocBuilder builder;
    public static XDocBuilder getBuilder() {
        return builder;
    }
    public static XDocViewer xDocViewer;

    public XDocFrame(){
        super();
    }
    public void init(){
        try {

            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            //获取XDocBuilder实例
            builder = new XDocBuilder();
            //将XDocBuilder放入面板中
            this.getContentPane().add(builder, BorderLayout.CENTER);
            JToolBar bar = new JToolBar("工具条");
            JButton btn = new JButton("新建");
            btn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    //新建
                    builder.create();
                }
            });
            btn = new JButton("打开");
            btn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    //打开
                    builder.open();
                }
            });
            bar.add(btn);
            btn = new JButton("查看XML");
            btn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    //显示XML内容
                    try {
                        XDoc xDoc = builder.getXDoc();
                        //String s = DocUtils.wordToHtml(doc);
                        JOptionPane.showMessageDialog(null, xDoc.toXml());
                    } catch (Exception e1) {
                        e1.printStackTrace();
                        JOptionPane.showMessageDialog(null, e1.getMessage());
                    }
                }
            });
            bar.add(btn);

            btn = new JButton("查看页面");
            btn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    try {

                        xDocViewer= new XDocViewer();

                        String s = builder.getXDoc(builder.activeIndex()).toXml();
                        System.out.println("内容:"+s);
                        xDocViewer.open(s);

                        xDocViewer.setVisible(true);
                        //获取展示栏
                        JSplitPane applet = (JSplitPane) xDocViewer.getComponent(1);
                        JScrollPane jScrollPane = (JScrollPane) applet.getComponent(2);


                        //SetJFrame.createJFrame().add(component);
                        JFrame jFrame = new JFrame();
                        jFrame.setBounds(0,0,AllBoundSetting.getMainSizeWidth(),AllBoundSetting.getMainSizeHeight());
                        jScrollPane.setBackground(Color.magenta);
                        jScrollPane.setBounds(0,0,jFrame.getWidth()/2,jFrame.getHeight()/2);
                        jFrame.add(jScrollPane);
                        jFrame.setVisible(true);


                        //JOptionPane.showMessageDialog(null, visibleRect);
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(null, e1.getMessage());
                    }
                }
            });
            bar.add(btn);
            btn = new JButton("保存");
            btn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    //保存当前文件
                    builder.save();
                }
            });
            bar.add(btn);
            btn = new JButton("全部保存");
            btn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    for (int i = 0; i < builder.getXDocCount(); i++) {
                        //选择第i个文件
                        builder.activeXDoc(i);
                        //保存
                        builder.save();
                        //builder.save("xdox" + i + ".rtf", XDocIO.WRITE_FORMAT_PDF);
                    }
                }
            });
            bar.add(btn);
            btn = new JButton("关闭");
            btn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    //关闭当前文件
                    builder.close();
                }
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
                public void windowClosing(WindowEvent e) {
                    //frame关闭时做处理
                    //String s = builder.getXDoc(builder.activeIndex()).toXml();
                    //SetJFrame.detailsTextField.setText(s);
                    //XDocFrame.this.add(xDocViewer);
                    for (int i = 0; i < builder.getXDocCount(); i++) {
                        try {
                        } catch (Exception e1) {
                            JOptionPane.showMessageDialog(null, e1.getMessage());
                        }
                    }
                    builder.close();
                    XDocFrame.this.dispose();
                    //System.exit(0);
                }
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
}
