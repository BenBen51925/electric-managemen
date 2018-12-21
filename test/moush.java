import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

class mouseh extends JFrame
{
    public void mousein ()
    {
        mouse panel = new mouse ();
        setLayout (new BorderLayout ());
        add (panel, BorderLayout.CENTER);
    }

    public static void main ( String[] args )
    {
        mouseh frame = new mouseh ();
        frame.setTitle ("shubiaohuodong");
        frame.setSize (500, 500);
        frame.setLocationRelativeTo (null);
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        frame.mousein ();
        frame.setVisible (true);
    }
}

class mouse extends JPanel
{
    int x = 20;
    int y = 30;
    String n = null;

    public mouse ()
    {
        n = JOptionPane.showInputDialog ("请输入数值");
        addMouseMotionListener (new MouseAdapter ()
        {
            @Override
            public void mouseMoved ( MouseEvent e )
            {
                /*x = e.getX ();
                y = e.getY ();
                repaint ();*/
            }
        });
    }

    @Override
    public void paint ( Graphics g )
    {
        super.paint (g);
        g.drawString (n, x, y);
        g.dispose ();
    }
}