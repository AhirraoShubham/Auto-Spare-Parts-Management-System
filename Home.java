import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

class Home extends JFrame implements ActionListener
{
	private Container Picture;
	
	private Font fButton;
	private ImageIcon iPicture;
	private JLabel lPicture;
	private JButton bSpare,bOrder,bStaff,bCustomer,bDistributor,bReports,bUser,bQuotInv,bLogoff,bExit;
	
	
	public Home()
	{
		// CREATING OBJECT OF Variables
		Variables var = new Variables();
		
		// SETTING FRAME ASPECTS
		setTitle("Home");
		show();
		setResizable(false);
		setLocation(var.d.width/10-getWidth()/2,var.d.height/10-getHeight()/2);
		setSize(845,642);
		
		// DEFINING FONT
		fButton = new Font("Rage Italic",Font.PLAIN,30);
		
		// INITIALIZING PICTURE
		Picture = getContentPane();
		getContentPane().setLayout(new FlowLayout());
		iPicture = new ImageIcon("C://b3//Auto Spares//Images//Home.jpg");
		lPicture = new JLabel(iPicture);
		
		// DATE
		lPicture.add(var.lDate);
		var.lDate.setBounds(625,20,60,25);
		var.lDate.setForeground(Color.WHITE);
		
		lPicture.add(var.tDate);
		var.tDate.setBounds(690,20,110,25);
		
		
		
		// INITIALIZING BUTTONS
		bQuotInv = new JButton(new ImageIcon("C://b3//Auto Spares//Images//QuotInv button.gif"));
		bOrder = new JButton(new ImageIcon("C://b3//Auto Spares//Images//Order button.gif"));
		bSpare = new JButton(new ImageIcon("C://b3//Auto Spares//Images//Spare button.gif"));
		bStaff = new JButton(new ImageIcon("C://b3//Auto Spares//Images//Staff button.gif"));
		bCustomer = new JButton(new ImageIcon("C://b3//Auto Spares//Images//Customer button.gif"));
		bReports = new JButton(new ImageIcon("C://b3//Auto Spares//Images//Reports button.gif"));
		bUser = new JButton(new ImageIcon("C://b3//Auto Spares//Images//User button.gif"));
		bDistributor = new JButton(new ImageIcon("C://b3//Auto Spares//Images//Distributor button.gif"));
		bLogoff = new JButton(new ImageIcon("C://b3//Auto Spares//Images//Log off button.gif"));
		bExit = new JButton(new ImageIcon("C://b3//Auto Spares//Images//Exit button.gif"));
		
		setLayout(null);	// ADDING LAYOUT MANAGER
		
		// ADDING IMAGE
		Picture.add(lPicture);
		lPicture.setBounds(0,0,840,614);
		
		// ADDING BUTTONS
		lPicture.add(bQuotInv);	bQuotInv.setBounds(54,200,442,60);
		lPicture.add(bOrder);	bOrder.setBounds(624,200,160,60);
		
		lPicture.add(bSpare);	bSpare.setBounds(54,300,160,40);
		lPicture.add(bStaff);	bStaff.setBounds(334,300,160,40);
		lPicture.add(bCustomer);bCustomer.setBounds(624,300,160,40);
		
		lPicture.add(bReports);	bReports.setBounds(54,400,160,40);
		lPicture.add(bUser);	bUser.setBounds(334,400,160,40);
		lPicture.add(bDistributor);bDistributor.setBounds(624,400,160,40);
		
		lPicture.add(bLogoff);	bLogoff.setBounds(184,500,160,40);
		lPicture.add(bExit);	bExit.setBounds(464,500,160,40);
		
		bSpare.addActionListener(this);
		bOrder.addActionListener(this);
		
		bStaff.addActionListener(this);
		bCustomer.addActionListener(this);
		bDistributor.addActionListener(this);
		
		bReports.addActionListener(this);
		bUser.addActionListener(this);
		bQuotInv.addActionListener(this);
		
		bLogoff.addActionListener(this);
		bExit.addActionListener(this);
		
		addWindowListener(new WindowClass());
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource() == bQuotInv)
		{
			dispose();
			new Quotation();
		}
		if(ae.getSource() == bOrder)
		{
			dispose();
			new Order();
		}
		if(ae.getSource() == bSpare)
		{
			dispose();
			new Spare();
		}
		if(ae.getSource() == bStaff)
		{
			dispose();
			new Staff();	
		}
		if(ae.getSource() == bCustomer)
		{
			dispose();
			new Customer();
		}
		if(ae.getSource() == bReports)
		{
			new Reports();
			dispose();
		}
		if(ae.getSource() == bUser)
		{
			dispose();
			new User();	
		}
		if(ae.getSource() == bDistributor)
		{
			dispose();
			new Distributor();
		}
		if(ae.getSource() == bLogoff)
		{
			int reply = JOptionPane.showConfirmDialog(null,"Are you sure you want to log off?","",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE);
			if(reply == JOptionPane.YES_OPTION)
			{
				dispose();
				new Login();
			}
			else
			{
				setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			}
		}
		
		if(ae.getSource() == bExit)
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
		new Home();
	}
	
}