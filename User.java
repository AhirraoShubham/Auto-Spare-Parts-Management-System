import java.io.*;
import java.awt.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.table.*;
import javax.swing.border.*;
import javax.swing.border.LineBorder;

class User extends JFrame implements ActionListener, ItemListener
{
	private Connection con;
	private Statement st;
	private ResultSet rs;
	private int iResult;
	
	private Container Picture;
	private ImageIcon iPicture,iHome;
	
	private String sUser,sPass,sCPass,sNUser,sNPass,sCNPass;
	private JLabel lIntro,lUser,lPass,lCPass,lNUser,lNPass,lCNPass,lPicture,lNote;
	private JTextField tUser,tNUser;
	private JPasswordField pPass,pCPass,pNPass,pCNPass;
	private ButtonGroup bg;
	private JRadioButton rAdd,rUpdate,rRemove;
	private Font f1,f2,f3,f4;
	private JPanel pManage;
	
	private JSeparator sep,sep1;
	private JButton bSubmit,bClear,bHome;
	
	private Variables var;

	public User()
	{
		// CREATING OBJECT OF Variables
		var = new Variables();
		
		// SETTING FRAME ASPECTS
		setTitle("User");
		show();
		//setResizable(false);
		setLocation(var.d.width/10-getWidth()/2,var.d.height/10-getHeight()/2);
		setSize(816,553);
		setJMenuBar(var.mb);
		
		// INITIALIZING and ADDING PICTURE
		Picture = getContentPane();
		getContentPane().setLayout(null);
		iPicture = new ImageIcon("C://b3//Auto Spares//Images//Horizon.jpg");
		lPicture = new JLabel(iPicture);
		
		Picture.add(lPicture);
		lPicture.setBounds(0,0,800,500);
		
		lIntro = new JLabel("User");
		lIntro.setFont(new Font("Times New Roman",Font.PLAIN,40));
		lIntro.setForeground(Color.WHITE);
		
		// DATE
		lPicture.add(var.lDate);
		var.lDate.setBounds(600,20,60,25);
		var.lDate.setForeground(Color.WHITE);
		
		lPicture.add(var.tDate);
		var.tDate.setBounds(665,20,110,25);
		
		// CONNECTING TO DATABASE
		try
		{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			con = DriverManager.getConnection("jdbc:odbc:Auto Spare","","");
			st = con.createStatement();
		}
		catch(SQLException se)
		{
			JOptionPane.showMessageDialog(this, "Sorry, unable to connect to database !!");
			dispose();
		}
		
		// INITIALIZING COMPONENTS OF TAB "MANAGE"
		f1 = new Font("Times New Roman",Font.PLAIN,20);
		f2 = new Font("Courier New",Font.PLAIN,15);
		f3 = new Font("Brush Script MT",Font.PLAIN,30);
		f4 = new Font("Times New Roman",Font.PLAIN,17);
		

		lUser = new JLabel("Username");
		lUser.setBounds(50,50,80,20);
		lUser.setFont(f1);
		lPass = new JLabel("Password");
		lPass.setBounds(50,100,80,20);
		lPass.setFont(f1);
		lCPass = new JLabel("<html>Confirm<br>Password</br></html>");
		lCPass.setBounds(50,150,80,40);
		lCPass.setFont(f1);
		lNUser = new JLabel("<html>New<br>Username</br></html>");
		lNUser.setBounds(50,150,80,40);
		lNUser.setFont(f4);
	    lNPass = new JLabel("<html>New<br>password</br></html>");
		lNPass.setBounds(50,200,80,40);
		lNPass.setFont(f4);
		lCNPass = new JLabel("<html>Confirm new<br>password</br></html>");
		lCNPass.setBounds(50,250,80,70);
		lCNPass.setFont(f4);
		
		lNote = new JLabel("Please select an option from above !");
		lNote.setBounds(420,150,250,100);
		lNote.setFont(new Font("Times New Roman",Font.PLAIN,16));
		lNote.setForeground(Color.BLUE);
		
		tUser = new JTextField();
		tUser.setBounds(180,50,150,25);
		tUser.setFont(f2);
		tNUser = new JTextField();
		tNUser.setBounds(180,155,150,25);
		tNUser.setFont(f2);
		tNUser.setText("New user name");
		
		pPass = new JPasswordField();
		pPass.setBounds(180,100,150,25);
		pPass.setFont(f2);
		pCPass = new JPasswordField();
		pCPass.setBounds(180,150,150,25);
		pCPass.setFont(f2);
		pNPass = new JPasswordField();
		pNPass.setBounds(180,205,150,25);
		pNPass.setFont(f2);
		pCNPass = new JPasswordField();
		pCNPass.setBounds(180,270,150,25);
		pCNPass.setFont(f2);
		
		bg = new ButtonGroup();
		
		rAdd = new JRadioButton("Add new User");
		rAdd.setBounds(450,20,150,25);
		rAdd.setFont(f1);
		rUpdate = new JRadioButton("Update User");
		rUpdate.setBounds(450,65,150,25);
		rUpdate.setFont(f1);
		rRemove = new JRadioButton("Remove User");
		rRemove.setBounds(450,110,150,25);
		rRemove.setFont(f1);
		
		bg.add(rAdd);
		bg.add(rUpdate);
		bg.add(rRemove);
		
		// DEFINING BUTTONS
		bSubmit = new JButton("Submit");
		bSubmit.setBounds(150,355,150,40);
		bSubmit.setFont(f3);
		
		bClear = new JButton("Clear");
		bClear.setBounds(400,355,150,40);
		bClear.setFont(f3);
		
		// INITILIZING PANEL AND ADDING COMPONENTS TO IT
		pManage = new JPanel();
		pManage.setLayout(null);
		pManage.setBorder(new CompoundBorder(new TitledBorder(null,"User",TitledBorder.LEFT,TitledBorder.TOP),new EmptyBorder(15,15,15,15)));
		pManage.add(lUser);	pManage.add(tUser);
		pManage.add(lPass);	pManage.add(pPass);
		pManage.add(lCPass);pManage.add(pCPass);
		pManage.add(lNUser);pManage.add(tNUser);
		pManage.add(lNPass);pManage.add(pNPass);
		pManage.add(lCNPass);pManage.add(pCNPass);
		
		reset();
		
		pManage.add(rAdd);
		pManage.add(rUpdate);
		pManage.add(rRemove);
		
		pManage.add(lNote);
		
		pManage.add(bSubmit);
		pManage.add(bClear);
		
		iHome = new ImageIcon("C://b3//Auto Spares//Images//Home icon.gif");
		bHome = new JButton(iHome);
		lPicture.add(bHome);
		bHome.setBounds(725,396,70,80);
		bHome.setToolTipText("Go to Home");
		
		sep = new JSeparator();
    	sep.setBounds(2,335,696,10);
 	    sep.setForeground(Color.LIGHT_GRAY);
		pManage.add(sep);
		
		sep1 = new JSeparator(JSeparator.VERTICAL);
		sep1.setBounds(400,8,1,329);
		sep1.setForeground(Color.LIGHT_GRAY);
		pManage.add(sep1);
		
		lPicture.add(pManage);
		pManage.setBounds(20,60,700,415);
		lPicture.add(lIntro);
		lIntro.setBounds(320,15,200,40);

		setLayout(null);
		
		// ADDING EVENT LISTENER
		rAdd.addItemListener(this);
		rUpdate.addItemListener(this);
		rRemove.addItemListener(this);
		
		bSubmit.addActionListener(this);
		bClear.addActionListener(this);
		bHome.addActionListener(this);
		
		// MENU ITEMS
		var.miQuotInv.addActionListener(this);
		var.miOrder.addActionListener(this);
		var.miSpare.addActionListener(this);
		var.miStaff.addActionListener(this);
		var.miCustomer.addActionListener(this);
		var.miReports.addActionListener(this);
		var.miUser.addActionListener(this);
		var.miDistributor.addActionListener(this);
		var.miLogoff.addActionListener(this);
		var.miExit.addActionListener(this);
		
		addWindowListener(new WindowClass());
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		iResult=0;
		boolean check = true;
		if(ae.getSource() == bSubmit)
		{
			if(rAdd.isSelected())
			{
				try
				{
					sUser = tUser.getText();
					sPass = pPass.getText();
					sCPass = pCPass.getText();
					
					for(int i=0;i<sUser.length();i++)
					{
						if(Character.isDigit(sUser.charAt(i)))
						{
							check = false;
						}
					}
					
					if(check == true)
					{
						if((sUser.equals(""))||(sPass.equals("")||(sCPass.equals(""))))
						{
							JOptionPane.showMessageDialog(this,"Please enter all details properly !");
						}
						else
						{
							if(sPass.equals(sCPass))
							{
								//insert into User values('Vicky','Vicky')
								iResult = st.executeUpdate("insert into User values('"+sUser+"','"+sPass+"')");
							}
							else
							{
								JOptionPane.showMessageDialog(this,"Please enter same password in both fields !");
								pPass.setText("");
								pCPass.setText("");
							}
							if(iResult==1)
							{
								// If Query succesful
								JOptionPane.showMessageDialog(this,"Record saved successfully !");
								clear();
							}
							else
							{
								//  Query failed
								JOptionPane.showMessageDialog(this,"Record not saved successfully !");
								clear();
							}
						}
					}
					else
						JOptionPane.showMessageDialog(this,"Username should not contain any digits !!");
				}
				catch(NumberFormatException nfe)
				{
					JOptionPane.showMessageDialog(this,"Please enter the valid data");
					System.out.print(nfe);
					clear();
				}
				catch(SQLException sql)
				{
					JOptionPane.showMessageDialog(this,"Username already exists !");
					System.out.print("\n"+sql);
					clear();
				}
			}
			else if(rUpdate.isSelected())
			{
				try
				{
					sUser = tUser.getText();
					sPass = pPass.getText();
					sNUser = tNUser.getText();
					sNPass = pNPass.getText();
					sCNPass = pCNPass.getText();
					boolean check1 = true, check2 = true;
					
					for(int i=0;i<sUser.length();i++)
					{
						if(Character.isDigit(sUser.charAt(i)))
						{
							check1 = false;
						}
					}
					
					for(int i=0;i<sNUser.length();i++)
					{
						if(Character.isDigit(sNUser.charAt(i)))
						{
							check2 = false;
						}
					}
					
					if((check1 == true) && (check2 == true))
					{
						if((sUser.equals(""))||(sPass.equals(""))||(sNUser.equals(""))||(sNPass.equals(""))||(sCNPass.equals("")))
						{
							JOptionPane.showMessageDialog(this,"Please enter all details properly !");	
						}
						else
						{
							if(sNPass.equals(sCNPass))
							{
								//update User set values Username='harshal',Password='harshal' where Username='harsh' and Password='harsh'
								iResult = st.executeUpdate("update User set Username='"+sNUser+"',Password='"+sNPass+"' where Username='"+sUser+"' and Password='"+sPass+"'");
							}
							else
							{
								JOptionPane.showMessageDialog(this,"Please enter same password in both fields !");
							}
							if(iResult==1)
							{
								// If Query succesful
								JOptionPane.showMessageDialog(this,"Record updated successfully");
								clear();
							}
							else
							{
								//  Query failed
								JOptionPane.showMessageDialog(this,"Record not saved successfully");
								clear();
							}
						}
					}
					else
					{
						JOptionPane.showMessageDialog(this,"Username should not contain any digits !!");
					}
				}
				catch(NumberFormatException nfe)
				{
					JOptionPane.showMessageDialog(this,"Please enter the valid data");
					System.out.print(nfe);
					clear();
				}
				catch(SQLException sql)
				{
					JOptionPane.showMessageDialog(this,"Unable to connect to Database");
					System.out.print(sql);
					clear();
				}	
			}
			
			else if(rRemove.isSelected())
			{
				try
				{
					sUser = tUser.getText();
					sPass = pPass.getText();
				
					if((sUser.equals(""))||(sPass.equals("")))
					{
						JOptionPane.showMessageDialog(this,"Please enter all details properly !");
					}
					else
					{
						iResult = st.executeUpdate("delete * from User where Username='"+sUser+"' and Password='"+sPass+"'");
						if(iResult==1)
						{
							// If Query succesful
							JOptionPane.showMessageDialog(this,"Record deleted successfully !");
							clear();												
						}
						else
						{
							//  Query failed
							JOptionPane.showMessageDialog(this,"Record not deleted successfully !");
							clear();
						}
					}
				}
				catch(NumberFormatException nfe)
				{
					JOptionPane.showMessageDialog(this,"Please enter the valid data");
					System.out.print(nfe);
					clear();
				}
				catch(SQLException sql)
				{
					JOptionPane.showMessageDialog(this,"Unable to connect to database");
					System.out.print(sql);
					clear();
				}	
			}
			else
			{
				JOptionPane.showMessageDialog(this,"Please select an option !");
			}
		}
		
		if(ae.getSource() == bClear)
		{
			clear();
		}
		
		// MENU ITEMS
		if(ae.getSource() == var.miQuotInv)
		{
			new Quotation();
			dispose();
		}
		if(ae.getSource() == var.miOrder)
		{
			new Order();
			dispose();
		}
		if(ae.getSource() == var.miSpare)
		{
			new Spare();
			dispose();
		}
		if(ae.getSource() == var.miStaff)
		{
			new Staff();
			dispose();
		}
		if(ae.getSource() == var.miCustomer)
		{
			new Customer();
			dispose();
		}
		if(ae.getSource() == var.miReports)
		{
			new Reports();
			dispose();
		}
		if(ae.getSource() == var.miUser)
		{
			new User();
			dispose();
		}
		if(ae.getSource() == var.miDistributor)
		{
			new Distributor();
			dispose();
		}
		if(ae.getSource() == var.miLogoff)
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
		if(ae.getSource() == var.miExit)
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
		
		if(ae.getSource() == bHome)
		{
			dispose();
			new Home();
		}
	}
	
	public void itemStateChanged(ItemEvent ie)
	{
		if(ie.getItem() == rAdd)
		{
			lNote.setText("<html>Please enter the details of the new<br>user.</br></html>");
			tUser.setEnabled(true);
			pPass.setEnabled(true);
			pCPass.setEnabled(true);
			
			lCPass.setVisible(true);
			pCPass.setVisible(true);
			clear();
			disable();
			
		}
		if(ie.getItem() == rUpdate)
		{
			lNote.setText("<html>Please enter the old username<br>and password of the user<br>to be updated,<br>then enter the new username<br>and password to be set.</html>");
			tUser.setEnabled(true);
			pPass.setEnabled(true);
			lCPass.setVisible(false);
			pCPass.setVisible(false);
			
			lNUser.setVisible(true);
			tNUser.setVisible(true);
			lNPass.setVisible(true);
			pNPass.setVisible(true);
			lCNPass.setVisible(true);
			pCNPass.setVisible(true);
			clear();
			
		}
		if(ie.getItem() == rRemove)
		{
			lNote.setText("<html>Please enter the username and password of the user to be deleted.</br></html>");
			tUser.setEnabled(true);
			pPass.setEnabled(true);
			lCPass.setVisible(false);
			pCPass.setVisible(false);
			clear();
			disable();
		}
	}
	
	public void reset()
	{
		tUser.setText("");
		pPass.setText("");
		pCPass.setText("");
		
		lCPass.setVisible(true);
		pCPass.setVisible(true);

		tUser.setEnabled(false);
		pPass.setEnabled(false);
		pCPass.setEnabled(false);

		disable();
		bg.clearSelection();
		lNote.setText("Please select an option from above !");
	}
	
	public void disable()
	{
		lNUser.setVisible(false);
		tNUser.setVisible(false);
		lNPass.setVisible(false);
		pNPass.setVisible(false);
		lCNPass.setVisible(false);
		pCNPass.setVisible(false);
	}
	
	public void clear()
	{
		tUser.setText("");
		pPass.setText("");
		pCPass.setText("");
		tNUser.setText("");
		pNPass.setText("");
		pCNPass.setText("");
	}

	class WindowClass extends WindowAdapter
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
		new User();
	}
	
}