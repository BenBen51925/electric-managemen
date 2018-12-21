package utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class DrawUtils {
    /**
     * 按钮底色绘制
     *
     * @param g2
     *            画布
     * @param bt
     *            面板
     * @param c1
     *            渐变颜色1
     * @param c2
     *            渐变颜色2
     * @param c3
     *            渐变颜色3
     * @param c4
     *            渐变颜色4
     * @param frameColor
     *            边框颜色
     */
    public void drawButtonBackground(Graphics2D g2, Component bt,
                                             Color c1, Color c2, Color c3, Color c4, Color frameColor) {


        if (frameColor!=null){
            g2.setColor(frameColor);
        }
        g2.drawRoundRect(1, 1, bt.getWidth() - 4, bt.getHeight() - 2, 3, 3);

        // 渐变背景
        g2.setPaint(new GradientPaint(2, 2, c1, 1, bt.getHeight() / 3, c2));
        g2.fillRect(2, 2, bt.getWidth() - 5, bt.getHeight() / 3);
        // 渐变二段
        g2.setPaint(new GradientPaint(1, bt.getHeight() / 3, c3, 1, bt
                .getHeight(), c4));
        g2.fillRect(2, bt.getHeight() / 3, bt.getWidth() - 5,
                bt.getHeight() / 3 * 2 - 1);
    }

    public  void drawButtonBackground(Graphics2D g2, JButton bt,
                                             Color c1, Color c2, Color c3, Color c4) {
        drawButtonBackground(g2, bt, c1, c2, c3, c4, c1);
    }

    /**
     * 按钮底色绘制
     *
     * @param g2
     *            画布
     * @param bt
     *            面板
     * @param c1
     *            渐变颜色1
     * @param c2
     *            渐变颜色2
     */
    public void drawButtonBackground(Graphics2D g2, Component bt,
                                     Color c1, Color c2) {
        // 渐变背景
        g2.setPaint(new GradientPaint(0, 0, c1, 0, bt.getHeight(), c2));
        //g2.fillRect(0, 0, bt.getWidth(), bt.getHeight());
        g2.fillRoundRect(0,0,bt.getSize().width , bt.getSize().height  ,10,10);
    }

    /**
     * 按钮底色绘制
     *
     * @param g2
     *            画布
     * @param width 面板宽
     * @param height 画板高
     * @param c1
     *            渐变颜色1
     * @param c2
     *            渐变颜色2
     */
    public void drawButtonBackground(Graphics2D g2, int width,int height,
                                     Color c1, Color c2) {
        // 渐变背景
        g2.setPaint(new GradientPaint(0, 0, c1, 0,height, c2));
        //g2.fillRect(0, 0, bt.getWidth(), bt.getHeight());
        g2.fillRoundRect(0,0,width ,height ,10,10);
    }

    /**
     *
     * @param g2
     * @param bt
     * @param c1
     * @param c2
     */
    public void drawWithBackground(Graphics2D g2, Component bt,
                                     Color c1, Color c2) {
        // 渐变背景
        g2.setPaint(new GradientPaint(0, 0, c1, bt.getWidth(), bt.getHeight(), c2));
        //g2.fillRect(0, 0, bt.getWidth(), bt.getHeight());
        //边角圆度
        g2.fillRoundRect(0,0,bt.getSize().width -1, bt.getSize().height -1 ,0,0);
    }

    /**
     * 图片设置圆角
     * @param srcImage
     * @param radius
     * @param border
     * @param padding
     * @return
     * @throws IOException
     */
    public static BufferedImage setRadius(BufferedImage srcImage, int radius, int border, int padding) throws IOException {
        int width = srcImage.getWidth();
        int height = srcImage.getHeight();
        int canvasWidth = width + padding * 2;
        int canvasHeight = height + padding * 2;

        BufferedImage image = new BufferedImage(canvasWidth, canvasHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gs = image.createGraphics();
        gs.setComposite(AlphaComposite.Src);
        gs.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gs.setColor(Color.WHITE);
        gs.fill(new RoundRectangle2D.Float(0, 0, canvasWidth, canvasHeight, radius, radius));
        gs.setComposite(AlphaComposite.SrcAtop);
        gs.drawImage(setClip(srcImage, radius), padding, padding, null);
        if(border !=0){
            gs.setColor(Color.GRAY);
            gs.setStroke(new BasicStroke(border));
            gs.drawRoundRect(padding, padding, canvasWidth - 2 * padding, canvasHeight - 2 * padding, radius, radius);
        }
        gs.dispose();
        return image;
    }

    /**
     * 图片设置圆角
     * @param srcImage
     * @return
     * @throws IOException
     */
    public static BufferedImage setRadius(BufferedImage srcImage) throws IOException{
        int radius = (srcImage.getWidth() + srcImage.getHeight()) / 6;
        return setRadius(srcImage, radius, 2, 5);
    }

    /**
     * 图片切圆角
     * @param srcImage
     * @param radius
     * @return
     */
    public static BufferedImage setClip(BufferedImage srcImage, int radius){
        int width = srcImage.getWidth();
        int height = srcImage.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gs = image.createGraphics();

        gs.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gs.setClip(new RoundRectangle2D.Double(0, 0, width, height, radius, radius));
        gs.drawImage(srcImage, 0, 0, null);
        gs.dispose();
        return image;
    }

    /***
     *
     * @param srcFilePath 源图片文件路径
     * @param circularImgSavePath 新生成的图片的保存路径，需要带有保存的文件名和后缀
     * @param width 文件的边长，单位：像素，最终得到的是一张正方形的图，所以要求targetSize<=源文件的最小边长
     * @param height 文件的边长，单位：像素，最终得到的是一张正方形的图，所以要求targetSize<=源文件的最小边长
     * @param cornerRadius 圆角半径，单位：像素。如果=targetSize那么得到的是圆形图
     * @return  文件的保存路径
     * @throws IOException
     */
    public static String makeCircularImg(String srcFilePath, String circularImgSavePath, int width,int height, int cornerRadius) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(new File(srcFilePath));
        BufferedImage circularBufferImage = roundImage(bufferedImage,width,height,cornerRadius);
        ImageIO.write(circularBufferImage, "png", new File(circularImgSavePath));
        return circularImgSavePath;
    }

    public static BufferedImage roundImage(BufferedImage image, int width, int height, int cornerRadius) {
        BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = outputImage.createGraphics();
        g2.setComposite(AlphaComposite.Src);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.fill(new RoundRectangle2D.Float(0, 0, width, height, cornerRadius, cornerRadius));
        g2.setComposite(AlphaComposite.SrcAtop);

        g2.drawImage(image, 0, 0, null);
        g2.dispose();
        return outputImage;
    }

    public static void MyDrawString(String str,int x,int y,double rate,Graphics g){
        String tempStr=new String();
        int orgStringWight=g.getFontMetrics().stringWidth(str);
        int orgStringLength=str.length();
        int tempx=x;
        int tempy=y;
        while(str.length()>0)
        {
            tempStr=str.substring(0, 1);
            str=str.substring(1, str.length());
            g.drawString(tempStr, tempx, tempy);
            tempx=(int)(tempx+(double)orgStringWight/(double)orgStringLength*rate);
        }
    }
}
