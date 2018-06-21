import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

class Welcome extends JFrame implements ActionListener
{
	Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
	
	private Container Picture;
	private ImageIcon iPicture;
	private Font fNext;
	private JLabel lPicture;
	private JButton bNext;
	
	Welcome()
	{
		Variables var = new Variables();
		
		setTitle("Welcome");
		show();
		setResizable(false);
		setLocation(var.d.width/10-getWidth()/2,var.d.height/10-getHeight()/2);
		setSize(675,506);
		setFocusable(true);
		
		Picture = getContentPane();
		getContentPane().setLayout(null);
		iPicture = new ImageIcon("C://b3//Auto Spares//Images//Welcome.jpg");
		lPicture = new JLabel(iPicture);
		fNext = new Font("Brush Script MT",Font.PLAIN,40);
		bNext = new JButton("Proceed");
		bNext.setBackground(Color.BLACK);
		bNext.setForeground(Color.WHITE);
		
		setLayout(null);
		
		Picture.add(lPicture);
		lPicture.setBounds(0,0,675,506);
		lPicture.add(bNext);	bNext.setBounds(440,326,170,45);
		bNext.setFont(fNext);
		
		bNext.addActionListener(this);
		addWindowListener(new WindowClass());
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		dispose();
		new Home();
	}
	
	public class WindowClass extends WindowAdapter
	{
		public void windowClosing(WindowEvent we)
		{
			int reply = JOptionPane.showConfirmDialog(null,"Are you sure you want to exit?","",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE);
			if(reply == JOptionPane.YES_OPTION)
			{
				dispose();
			}
			else
			{
				setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			}
		}
	}
	
	public static void main(String args[])
	{
		new Welcome();
	}
}