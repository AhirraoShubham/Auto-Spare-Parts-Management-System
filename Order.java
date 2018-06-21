import java.io.*;
import java.awt.*;
import java.sql.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.table.*;
import javax.swing.border.*;
import javax.swing.border.LineBorder;

class Order extends JFrame implements ActionListener
{
	private Container Picture;
	private ImageIcon iPicture,iHome;
	
	private Connection con;
	private Statement st;
	private ResultSet rs;
	private int iResult;
	
	private String sType,sPart;
	private JLabel lIntro,lPicture,lDistributor,lType,lPart,lQuantity,lPart1,lQuantity1,lInfo,lNote,lNote2;
	private JComboBox cDistributor,cType,cPart;
	private JTextField tQuantity;
	private java.awt.List liPart,liQuantity;
	private int iFlag,iTrack=0,iTempSelectedID=1;
	private String[] sSelectedParts,sSelectedQuantity;
	private String[] sTempPart = {"Fan","Break"};
	private Integer[] iTempQuantity = {1,2};

	private Font f1,f2,f3,f4;

	private JSeparator sep;
	private JButton bAdd,bUpdate,bHome,bRemove,bRemoveAll;
	
	private Variables var;
	private ViewQuotation VQuot;

	public Order()
	{
		// CREATING OBJECT OF Variables
		var = new Variables();
		
		// SETTING FRAME ASPECTS
		setTitle("Order");
		show();
		setResizable(false);
		setLocation(var.d.width/10-getWidth()/2,var.d.height/10-getHeight()/2);
		setSize(800,600);
		setJMenuBar(var.mb);
		
		// INITIALIZING and ADDING PICTURE
		Picture = getContentPane();
		getContentPane().setLayout(null);
		iPicture = new ImageIcon("C://b3//Auto Spares//Images//Horizon (2).jpg");
		lPicture = new JLabel(iPicture);
		
		Picture.add(lPicture);
		lPicture.setBounds(0,0,800,700);
		
		lIntro = new JLabel("Order");
		lIntro.setFont(new Font("Times New Roman",Font.PLAIN,40));
		lIntro.setForeground(Color.BLACK);
		
		lNote = new JLabel("Above is the list of all selected items !");
		lNote.setFont(new Font("Times New Roman",Font.ITALIC,16));
		lNote.setForeground(Color.BLACK);
		lNote2 = new JLabel("<html>Select the desirable parts and press \"Add\" button to add it to the list given aside, these parts with the specified quantity will be added to the stock.</html>");
		lNote2.setFont(new Font("Times New Roman",Font.ITALIC,16));
		lNote2.setForeground(Color.BLACK);
		
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
		
		// INITIALIZING COMPONENTS
		f1 = new Font("Times New Roman",Font.PLAIN,20);
		f2 = new Font("Courier New",Font.PLAIN,15);
		f3 = new Font("Brush Script MT",Font.PLAIN,30);
		f4 = new Font("Times New Roman",Font.PLAIN,17);
		
		bRemove = new JButton("<html>Remove<br>selected</html>");
		bRemove.setForeground(Color.CYAN);
		bRemove.setBackground(Color.BLACK);
		bRemove.setFont(new Font("Times New Roman",Font.PLAIN,16));
		bRemoveAll = new JButton("<html>Remove<br>all</html>");
		bRemoveAll.setForeground(Color.CYAN);
		bRemoveAll.setBackground(Color.BLACK);
		bRemoveAll.setFont(new Font("Times New Roman",Font.PLAIN,16));
	
		lDistributor = new JLabel("Distributor");
		lDistributor.setFont(f1);
		lDistributor.setForeground(Color.BLACK);
		lType = new JLabel("Type");
		lType.setFont(f1);
		lType.setForeground(Color.BLACK);
		lPart = new JLabel("Part");
		lPart.setFont(f1);
		lPart.setForeground(Color.BLACK);
		lQuantity = new JLabel("Quantity");
		lQuantity.setFont(f1);
		lQuantity.setForeground(Color.BLACK);
		
		lPart1 = new JLabel("Part");
		lPart1.setFont(f2);
		lPart1.setForeground(Color.BLACK);
		lQuantity1 = new JLabel("Quantity");
		lQuantity1.setFont(f2);
		lQuantity1.setForeground(Color.BLACK);
		
		lInfo = new JLabel("");
		lInfo.setFont(new Font("Times New Roman",Font.PLAIN,16));
		lInfo.setForeground(Color.BLACK);
		
		cDistributor = new JComboBox();
		cDistributor.setFont(f2);
		cType = new JComboBox();
		cType.setFont(f2);
		cPart = new JComboBox();
		cPart.setFont(f2);
		tQuantity = new JTextField();
		tQuantity.setFont(f2);
		
		//Adding elements to the List
		try
		{	
			st=con.createStatement();
			rs=st.executeQuery("select * from SpareType");
			while(rs.next())
			{
				cType.addItem(rs.getString("Type"));
			}
		}
		catch(SQLException sql)
		{
			JOptionPane.showMessageDialog(this,"Unable to connect to Database");			
		}
		
		try
		{
			st=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			rs=st.executeQuery("select * from Distributor");
			rs.first();
			cDistributor.addItem(rs.getInt("ID")+" - "+rs.getString("Name"));
			while(rs.next())
			{
				cDistributor.addItem(rs.getInt("ID")+" - "+rs.getString("Name"));
			}
			rs.close();
			st.close();
		}
		catch(SQLException sql)
		{
			System.out.print(sql);
			JOptionPane.showMessageDialog(this,"Please add the Distributor first!");
			new Distributor();
			dispose();
		}
		
		
		liPart = new List();
		liQuantity = new List();
		liQuantity.setEnabled(false);
		
		bAdd = new JButton("Add");
		bAdd.setFont(f3);
		bUpdate = new JButton("Update Database");
		bUpdate.setFont(f3);
		iHome = new ImageIcon("C://b3//Auto Spares//Images//Home icon.gif");
		bHome = new JButton(iHome);
		bHome.setToolTipText("Go to Home");
			
		sep = new JSeparator();
		
		sep.setForeground(Color.BLACK);
		
		// ADDING ELEMENTS TO FRAME
		lPicture.add(lIntro);
		lIntro.setBounds(320,15,200,40);
		lPicture.add(lNote);
		lNote.setBounds(450,270,250,20);
		lPicture.add(lNote2);
		lNote2.setBounds(450,320,300,100);
		
		lPicture.add(bRemove);
		bRemove.setBounds(700,110,80,60);
		lPicture.add(bRemoveAll);
		bRemoveAll.setBounds(700,180,80,60);
		
		lPicture.add(lDistributor);
		lDistributor.setBounds(50,110,100,20);
		lPicture.add(lType);
		lType.setBounds(50,185,80,20);
		lPicture.add(lPart);
		lPart.setBounds(50,240,80,20);
		lPicture.add(lQuantity);
		lQuantity.setBounds(50,290,80,30);
		
		lPicture.add(lInfo);
		lInfo.setBounds(180,330,200,40);
		
		lPicture.add(lPart1);
		lPart1.setBounds(500,90,80,15);
		lPicture.add(lQuantity1);
		lQuantity1.setBounds(580,90,80,15);
		
		lPicture.add(liPart);
		liPart.setBounds(470,110,100,145);
		lPicture.add(liQuantity);
		liQuantity.setBounds(572,110,100,145);
		
		lPicture.add(cDistributor);
		cDistributor.setBounds(180,110,150,25);
		lPicture.add(cType);
		cType.setBounds(180,185,150,25);
		lPicture.add(cPart);
		cPart.setBounds(180,235,150,25);
		lPicture.add(tQuantity);
		tQuantity.setBounds(180,290,150,25);
		
		lPicture.add(bAdd);
		bAdd.setBounds(175,470,150,40);
		lPicture.add(bUpdate);
		bUpdate.setBounds(425,470,225,40);
		lPicture.add(bHome);
		bHome.setBounds(700,450,70,80);
		
		lPicture.add(sep);
    	sep.setBounds(2,451,702,1);
		
		setLayout(null);
		
		cType.addActionListener(this);
		bAdd.addActionListener(this);
		bUpdate.addActionListener(this);
		bRemove.addActionListener(this);
		bRemoveAll.addActionListener(this);
		cDistributor.addActionListener(this);
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
		if(ae.getSource() == cType)
		{
			cDistributor.setEnabled(false);
			String sTempType = (String)cType.getSelectedItem();
			cPart.removeActionListener(this);
			try
			{
	                        st=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
				rs=st.executeQuery("select * from Spare where Type='"+sTempType+"'");
				cPart.removeAllItems();
				tQuantity.setText("");
				while(rs.next())
				{
					cPart.addItem(rs.getString("Name"));
				}
				rs.first();

				String sCost = rs.getString("Sell");
				lInfo.setText("Cost per piece : Rs. "+sCost);
			}
			catch(SQLException sql)
			{
				//JOptionPane.showMessageDialog(this,"Unable to connect to Database");
				//The above statement is commented because, if there is a Spare type which don't have any spare part,
				//it gives "invalid cursor state" exception !!
				System.out.print(sql);			
			}
			cPart.addActionListener(this);
		}
		
		if(ae.getSource() == cPart)
		{
			String sQuantity,sCost;
			int iQuantity;
			tQuantity.setText("");
			String sTempPart = (String) cPart.getSelectedItem();
			try
			{
				st=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
				rs=st.executeQuery("select * from Spare where Name='"+sTempPart+"'");
				rs.first();
				sQuantity = rs.getString("Quantity");
				sCost = rs.getString("Purchase");
				lInfo.setText("Cost per piece : Rs. "+sCost+"");
				iQuantity = Integer.parseInt(sQuantity);
			}
			catch(SQLException sql)
			{
				JOptionPane.showMessageDialog(this,"Unable to connect to Database");
				System.out.print(sql);
			}
		}
		
		if(ae.getSource() == bAdd)
		{
			iFlag = 0;
			try
			{
				String sTempPart = (String) cPart.getSelectedItem();
				String sTempQuantity = tQuantity.getText();
				
				for(int i=0;i<liPart.getItemCount();i++)
				{
					if(sTempPart.equals(liPart.getItem(i)))
					{
						JOptionPane.showMessageDialog(this,"You cannot add same parts to the list, set the quantity you desire at once.");
						iFlag = 1;
						break;
					}
				}
				if(iFlag == 0)
				{
					if((cPart.getSelectedIndex() == -1) || (sTempQuantity.equals("")))
					{
						JOptionPane.showMessageDialog(this,"Please select Type, Part and Quantity !");
					}
					else
					{
						liPart.addItem(sTempPart);
						liQuantity.addItem(sTempQuantity);
					}
				}
			}
			catch (Exception e)
			{
				System.out.print(e);
				JOptionPane.showMessageDialog(this,"Please select Type, Part and Quantity");
			}
		}
		
		if(ae.getSource() == bUpdate)
		{
			if(liPart.getItemCount() == 0)
			{
				JOptionPane.showMessageDialog(this,"Please select atleast one item !");
			}
			else
			{
				for(int i=0;i<liPart.getItemCount();i++)
				{
					sTempPart[i] = liPart.getItem(i);
					iTempQuantity[i] = Integer.parseInt(liQuantity.getItem(i));
					updateDatabase(sTempPart[i],iTempQuantity[i]);
					updateDatabase();
				}
			}
			new Home();
			dispose();
		}
		
		
		if(ae.getSource() == bRemove)
		{
			try
			{
				int iTempPartIndex = liPart.getSelectedIndex();
				liPart.remove(iTempPartIndex);
				liQuantity.remove(iTempPartIndex);
			}
			catch(Exception e)
			{
				JOptionPane.showMessageDialog(this,"Please select an item to remove");
			}
		}
		
		if(ae.getSource() == cDistributor)
		{
			String sTemp = (String)cDistributor.getSelectedItem();
			String[] sTemp1 = sTemp.split(" ");
			iTempSelectedID = Integer.parseInt(sTemp1[0]);	
		}
		
		if(ae.getSource() == bRemoveAll)
		{
			liPart.removeAll();
			liQuantity.removeAll();
		}
		
		// MENU ITEMS
		if(ae.getSource() == var.miQuotInv)
		{
			new Order();
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
		
		
		// HOME
		if(ae.getSource() == bHome)
		{
			int reply = JOptionPane.showConfirmDialog(null,"Are you sure you want leave ?","",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE);
			if(reply == JOptionPane.YES_OPTION)
			{
				//dispose();
				new Home();
				dispose();
			}
			else
			{
				setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			}
			
		}
	}
	
	public void setList(String[] sTempPart, String[] sTempQuantity)
	{
		for(int i=0;i<sTempPart.length;i++)
		{
			liPart.addItem(sTempPart[i]);
			liQuantity.addItem(sTempQuantity[i]);
		}
	}
	
	public void updateDatabase(String sPart, Integer iQuantity)
	{
		try
		{
		  Connection con = DriverManager.getConnection("Jdbc:Odbc:Auto spare");
		 Statement st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = st.executeUpdate("select * from Spare where Name='"+sPart+"'");
			rs.first();
			int iTemp = rs.getInt("Quantity");
			int iAdd = iQuantity+iTemp;
			int iResult = st.executeUpdate("update Spare set Quantity="+iAdd+" where Name='"+sPart+"'");
			if(iResult == 1)
			{
				//Query successful
				//System.out.print("Pass : ");
				JOptionPane.showMessageDialog(this,"Database updated !!");
				liPart.removeAll();
				liQuantity.removeAll();
				tQuantity.setText("");
			}
		}
		catch(Exception e)
		{
			System.out.print(" "+e);
			JOptionPane.showMessageDialog(this,"Unable to connect to database");
		}
	}

	public void updateDatabase()
	{
		try
		{
			Connection con = DriverManager.getConnection("Jdbc:Odbc:Auto spare");
		 	Statement st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = st.executeQuery("select * from OrderReport");
			rs.last();
			int iTemp = rs.getInt("ID");
			iTemp++;
			int iResult = st.executeUpdate("insert into OrderReport values("+iTemp+","+iTempSelectedID+",'"+var.tDate.getText()+"')");
			if(iResult == 1)
			{
				//Query successful
			}
			else
			{
				//Query failed
				System.out.print("Fail : "+iResult);
			}
		}
		catch(Exception e)
		{
			System.out.print(" "+e);
			JOptionPane.showMessageDialog(this,"Unable to connect to database");
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
		new Order();
	}
	
}