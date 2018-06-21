import java.io.*;
import java.awt.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.table.*;
import javax.swing.border.*;
import javax.swing.border.LineBorder;

class CustomerDetails extends JFrame implements ActionListener
{
	private Container Picture;
	private ImageIcon iPicture,iHome;
	
	private Connection con;
	private Statement st;
	private ResultSet rs;
	
	private Font f1,f2,f3,f4;
	
	private ButtonGroup bg1;
	private JRadioButton rbList,rbAdd;
	private JComboBox cCustomer;
	private JLabel lPicture,lIntro,lInfo,lInfo1,lInfo2,lID,lName,lAdd,lPhNo;
	private JTextField tID,tName,tAdd,tPhNo;
	
	private JButton bHome,bAdd,bViewBill;
		
	private JSeparator sep,sep1;
	private Variables var;

	public CustomerDetails()
	{
		// CREATING OBJECT OF Variables
		var = new Variables();
		
		// SETTING FRAME ASPECTS
		setTitle("Customer details");
		show();
		setResizable(false);
		setLocation(var.d.width/10-getWidth()/2,var.d.height/10-getHeight()/2);
		setSize(800,500);
		
		// INITIALIZING and ADDING PICTURE
		Picture = getContentPane();
		getContentPane().setLayout(null);
		iPicture = new ImageIcon("C://b3//Auto Spares//Images//Horizon.jpg");
		lPicture = new JLabel(iPicture);
		
		Picture.add(lPicture);
		lPicture.setBounds(0,0,800,500);
		
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
			con = DriverManager.getConnection("jdbc:odbc:Auto spare","","");
			st = con.createStatement();
		}
		catch(SQLException se)
		{
			JOptionPane.showMessageDialog(this, "Sorry, unable to connect to database !!");
			dispose();
		}
		
		// INITIALIZING ELEMENTS ON FRAME
		f1 = new Font("Times New Roman",Font.PLAIN,24);
		f2 = new Font("Times New Roman",Font.PLAIN,20);
		f3 = new Font("Brush Script MT",Font.PLAIN,30);
		f4 = new Font("Times New Roman",Font.ITALIC+Font.BOLD,25);
		
		lIntro = new JLabel("Customer Details");
		lIntro.setFont(new Font("Times New Roman",Font.PLAIN,40));
		lIntro.setForeground(Color.BLACK);
		
		lInfo = new JLabel("<html>You need to specify the customer details for the transaction, you can select one among the existing customers or you can add one.</html>");
		lInfo.setFont(f4);
		lInfo.setForeground(Color.DARK_GRAY);
		
		rbList = new JRadioButton("Select from the existing customers : ");
		rbList.setFont(f1);
		rbList.setForeground(Color.DARK_GRAY);
		rbList.setOpaque(false);
		rbAdd = new JRadioButton("Add new customer");
		rbAdd.setFont(f1);
		rbAdd.setForeground(Color.DARK_GRAY);
		rbAdd.setOpaque(false);
		
		cCustomer = new JComboBox();
		cCustomer.setFont(new Font("Times New Roman",Font.PLAIN,20));
		cCustomer.setEnabled(false);
		// ADDING CUSTOMER NAMES TO THE LIST
		try
		{
			st=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			rs=st.executeQuery("select * from Customer");
			rs.first();
			cCustomer.addItem(rs.getInt("ID")+" - "+rs.getString("Name"));
			while(rs.next())
			{
				cCustomer.addItem(rs.getInt("ID")+" - "+rs.getString("Name"));
			}
		}
		catch(SQLException sql)
		{
			JOptionPane.showMessageDialog(this,"Unable to connect to Database");			
		}
		
		bg1 = new ButtonGroup();
		bg1.add(rbList);
		bg1.add(rbAdd);
		
		lID = new JLabel("ID");
		lID.setFont(f2);
		lName = new JLabel("Name");
		lName.setFont(f2);
		lAdd = new JLabel("Address");
		lAdd.setFont(f2);
		lPhNo = new JLabel("Phone no.");
		lPhNo.setFont(f2);
		
		tID = new JTextField();
		tID.setFont(f2);
		tID.setEnabled(false);
		tName = new JTextField();
		tName.setFont(f2);
		tName.setEnabled(false);
		tAdd = new JTextField();
		tAdd.setFont(f2);
		tAdd.setEnabled(false);
		tPhNo = new JTextField();
		tPhNo.setFont(f2);
		tPhNo.setEnabled(false);
		
		bAdd = new JButton("Add and View");
		bAdd.setFont(f3);
		bAdd.setEnabled(false);
		bViewBill = new JButton("View Bill");
		bViewBill.setFont(f3);
		bViewBill.setEnabled(false);
		
		bHome = new JButton("Home");
		iHome = new ImageIcon("C://b3//Auto Spares//Images//Home icon.gif");
		bHome = new JButton(iHome);
		bHome.setToolTipText("Go to Home");
		
		// ADDING THE ELEMENTS TO FRAME
		lPicture.add(lIntro);
		lIntro.setBounds(200,20,300,30);
		
		lPicture.add(lInfo);
		lInfo.setBounds(50,80,700,60);
		
		lPicture.add(rbList);
		rbList.setBounds(50,170,420,30);
		lPicture.add(rbAdd);
		rbAdd.setBounds(50,220,300,30);
		lPicture.add(cCustomer);
		cCustomer.setBounds(480,170,150,40);
		
		lPicture.add(lID);
		lID.setBounds(50,280,50,20);
		lPicture.add(lName);
		lName.setBounds(50,320,50,20);
		lPicture.add(lAdd);
		lAdd.setBounds(380,280,80,20);
		lPicture.add(lPhNo);
		lPhNo.setBounds(380,320,120,20);
		
		lPicture.add(tID);
		tID.setBounds(130,280,150,25);
		lPicture.add(tName);
		tName.setBounds(130,320,150,25);
		lPicture.add(tAdd);
		tAdd.setBounds(480,280,150,25);
		lPicture.add(tPhNo);
		tPhNo.setBounds(480,320,150,25);
		
		lPicture.add(bAdd);
		bAdd.setBounds(150,400,180,40);
		lPicture.add(bViewBill);
		bViewBill.setBounds(400,400,150,40);
		
		lPicture.add(bHome);
		bHome.setBounds(700,370,70,80);
		
		setLayout(null);

		rbList.addActionListener(this);
		rbAdd.addActionListener(this);
		bAdd.addActionListener(this);
		bViewBill.addActionListener(this);
		
		bHome.addActionListener(this);

		addWindowListener(new WindowClass());
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource() == rbList)
		{
			cCustomer.setEnabled(true);
			
			tID.setEnabled(false);
			tName.setEnabled(false);
			tAdd.setEnabled(false);
			tPhNo.setEnabled(false);
			
			bAdd.setEnabled(false);
			bViewBill.setEnabled(true);
		}
		if(ae.getSource() == rbAdd)
		{
			cCustomer.setEnabled(false);
			
			tID.setEnabled(true);
			tName.setEnabled(true);
			tAdd.setEnabled(true);
			tPhNo.setEnabled(true);
			
			bAdd.setEnabled(true);
			bViewBill.setEnabled(false);
			tID.setEditable(false);
			try
			{	
				st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
				rs=st.executeUpdate("select * from Customer");
				rs.last();
				tID.setText(""+(1+rs.getInt("ID")));
			}
			catch(SQLException sql)
			{
				JOptionPane.showMessageDialog(this,"Unable to connect to Database");
				System.out.print(sql);			
			}
		}
		if(ae.getSource() == bAdd)
		{
			try
			{
				int iID = Integer.parseInt(tID.getText());
				String sName = tName.getText();
				String sAdd = tAdd.getText();
				Long liPhNo = Long.parseLong(tPhNo.getText());
				int iResult;
				boolean check1 = true, check2 = true;
				
				for(int i=0;i<sName.length();i++)
				{
					if(Character.isDigit(sName.charAt(i)))
					{
						check1 = false;
					}
				}

				if(check1 == true)
				{
					if((sName.equals(""))||(sAdd.equals(""))||(liPhNo == 0))
					{
						JOptionPane.showMessageDialog(this,"Please enter the details properly !");
					}
					else
					{	
						iResult = st.executeUpdate("insert into Customer values("+iID+",'"+sName+"','"+sAdd+"',"+liPhNo+")");
						if(iResult==1)
						{
							// If Query succesful
							JOptionPane.showMessageDialog(this,"<html>Record saved successfully,<br>The bill will be produced in the name of new customer</html>");
							Bill bill = new Bill();
							bill.setCustomer(tName.getText(),tAdd.getText(),tPhNo.getText());
							dispose();
						}
						else
						{
							//  Query failed
							JOptionPane.showMessageDialog(this,"Record not saved successfully");
							dispose();
						}
					}
				}
				else
					JOptionPane.showMessageDialog(this,"Customer name should not contain any digits !!");
			}
			catch(NumberFormatException nfe)
			{
				JOptionPane.showMessageDialog(this,"Please enter the valid data");
			}
			catch(SQLException sql)
			{
			JOptionPane.showMessageDialog(this,"Unable to connect to Database");
				System.out.print("\n"+sql);			
			}
		}
		if(ae.getSource() == bViewBill)
		{
			var.sCustomerName = (String) cCustomer.getSelectedItem();
			String sTemp[] = var.sCustomerName.split(" ");
			if(sTemp.length == 3)
				var.sCustomerName = sTemp[2];
			else
				var.sCustomerName = sTemp[2] +" "+ sTemp[3];
			Bill bill = new Bill();
			bill.setCustomer();
			dispose();
		}
		
		if(ae.getSource() == bHome)
		{
			int reply = JOptionPane.showConfirmDialog(null,"<html>Are you sure you want to go Home?<br>All the selected Parts will be lost</html>","",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE);
			if(reply == JOptionPane.YES_OPTION)
			{
				new Home();
				dispose();
			}
			else
			{
				setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			}
		}
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
		new CustomerDetails();
	}
}