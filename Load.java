import java.awt.*;
import javax.swing.*;
import java.applet.*;
import java.awt.event.*;

class Load extends JWindow implements Runnable
{
	private Thread t;
	private Button b1;
	private ImageIcon i;
	private Image im;
	private JLabel l1;
	private int x=0;
	
	private Load()
	{
		Variables var = new Variables();
		t = new Thread(this,"");
		
		i = new ImageIcon("C://b3//Auto Spares//Images//Introduction.jpg");
		im = i.getImage();
		l1 = new JLabel(i);
	
		setLayout(new FlowLayout());
		
		add(l1);
		setBounds(var.d.width/10-getWidth()/2,var.d.height/10-getHeight()/2,675,504);
		setVisible(true);
		t.start();
		
	}	
		
	public void run()
	{
		try
		{
			while (x<300)
			{
				
				x+=5;
				t.sleep(100);
				repaint();				
			}
			dispose();
			new Login();
			
		}
		
		catch(Exception e)
		{
			System.out.println(" "+e);
		}
	}
	
	public void paint(Graphics g)
	{
		g.drawImage(im,0,0,this);
		
		String per = "Loading :  " + (x*100)/300 + " % Completed";
		g.drawString(per,150,175);
		g.fillRect(20,200,x,10);
		
	}
	
	public static void main(String args[])
	{
		new Load();
	}
}