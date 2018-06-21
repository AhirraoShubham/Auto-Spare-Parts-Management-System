import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

class Reports extends JFrame implements ActionListener
{
	private Variables var;
	
	private Container Picture;
	private ImageIcon iPicture,iHome;
	
	private Font f1,f2;
	
	private JLabel lIntro,lPicture;
	private JButton bCust,bDist,bHome;
	
	public Reports()
	{
		// CREATING OBJECT OF Variables
		var = new Variables();
		
		// SETTING FRAME ASPECTS
		setTitle("Reports");
		show();
		setResizable(false);
		setLocation(var.d.width/10-getWidth()/2,var.d.height/10-getHeight()/2);
		setSize(600,300);
		
		// INITIALIZING and ADDING PICTURE
		Picture = getContentPane();
		getContentPane().setLayout(null);
		iPicture = new ImageIcon("C://b3//Auto Spares//Images//Horizon (3).jpg");
		lPicture = new JLabel(iPicture);
	
		iHome = new ImageIcon("C://b3//Auto Spares//Images//Home icon.gif");
		bHome = new JButton(iHome);
		bHome.setToolTipText("Go to Home");
		
		Picture.add(lPicture);
		lPicture.setBounds(0,0,600,300);
			
		// INITIALIZING ELEMENTS
		f1 = new Font("Times New Roman",Font.PLAIN,35);
		f2 = new Font("Rage Italic",Font.PLAIN,35);
		
		lIntro = new JLabel("Reports");
		lIntro.setFont(f1);
		
		bCust = new JButton("Bill Report");
		bCust.setFont(f2);
		bCust.setBackground(Color.BLACK);
		bCust.setForeground(Color.WHITE);
		bDist = new JButton("Order Report");
		bDist.setFont(f2);
		bDist.setBackground(Color.BLACK);
		bDist.setForeground(Color.WHITE);
		
		setLayout(null);
		
		// ADDING THE ELEMENTS ON FRAME
		lPicture.add(lIntro);
		lIntro.setBounds(230,10,150,50);
		
		lPicture.add(bCust);
		bCust.setBounds(50,90,250,50);
		lPicture.add(bDist);
		bDist.setBounds(50,180,250,50);
		
		lPicture.add(bHome);
		bHome.setBounds(400,120,70,80);
		
		// ADDING EVENT LISTENERS
		bCust.addActionListener(this);
		bDist.addActionListener(this);
		bHome.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource() == bCust)
		{
			new BillReport();
			dispose();
		}
		if(ae.getSource() == bDist)
		{
			new OrderReport();
			dispose();
		}
		if(ae.getSource() == bHome)
		{
			new Home();
			dispose();
		}
	}
	
	public static void main(String args[])
	{
		new Reports();
	}
}