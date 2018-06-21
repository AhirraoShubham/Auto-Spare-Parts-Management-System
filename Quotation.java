import java.io.*;
import java.awt.*;
import java.sql.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.table.*;
import javax.swing.border.*;
import javax.swing.border.LineBorder;

class Quotation extends JFrame implements ActionListener
{
	private Container Picture;
	private ImageIcon iPicture,iHome;
	
	private Connection con;
	private Statement st;
	private ResultSet rs;
	private int iResult;
	
	private String sType,sPart;
	private JLabel lIntro,lPicture,lType,lPart,lQuantity,lDiscount,lPercent,lPart1,lQuantity1,lDiscount1,lInfo,lNote,lNote2;
	private JComboBox cType,cPart,cQuantity;
	private JTextField tDiscount;
	private java.awt.List liPart,liQuantity,liDiscount;
	private int iFlag,iTrack=0;
	private String[] sSelectedParts,sSelectedQuantity,sSelectedDiscount;
	private Font f1,f2,f3,f4;

	private JSeparator sep,sep1;
	private JButton bAdd,bView,bHome,bRemove,bRemoveAll;
	
	private Variables var;
	private ViewQuotation VQuot;

	public Quotation()
	{
		// CREATING OBJECT OF Variables
		var = new Variables();
		
		// SETTING FRAME ASPECTS
		setTitle("Quotation");
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
		
		lIntro = new JLabel("Quotation");
		lIntro.setFont(new Font("Times New Roman",Font.PLAIN,40));
		lIntro.setForeground(Color.BLACK);
		
		lNote = new JLabel("Above is the list of all selected items !");
		lNote.setFont(new Font("Times New Roman",Font.ITALIC,16));
		lNote.setForeground(Color.BLACK);
		lNote2 = new JLabel("<html>Select the desirable parts and press \"Add\" button to add it to the list given aside,<br>if the discount field is left blank,<br>it will be considered as 0.</html>");
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
	
		lType = new JLabel("Type");
		lType.setFont(f1);
		lType.setForeground(Color.BLACK);
		lPart = new JLabel("Part");
		lPart.setFont(f1);
		lPart.setForeground(Color.BLACK);
		lQuantity = new JLabel("Quantity");
		lQuantity.setFont(f1);
		lQuantity.setForeground(Color.BLACK);
		lDiscount = new JLabel("Discount");
		lDiscount.setFont(f1);
		lDiscount.setForeground(Color.BLACK);
		lPercent = new JLabel("%");
		lPercent.setFont(f1);
		lPercent.setForeground(Color.BLACK);
		
		lPart1 = new JLabel("Part");
		lPart1.setFont(f2);
		lPart1.setForeground(Color.BLACK);
		lQuantity1 = new JLabel("Quantity");
		lQuantity1.setFont(f2);
		lQuantity1.setForeground(Color.BLACK);
		lDiscount1 = new JLabel("Discount");
		lDiscount1.setFont(f2);
		lDiscount1.setForeground(Color.BLACK);
		
		lInfo = new JLabel("");
		lInfo.setFont(new Font("Times New Roman",Font.PLAIN,16));
		lInfo.setForeground(Color.BLACK);
		
		cType = new JComboBox();
		cType.setFont(f2);
		cPart = new JComboBox();
		cPart.setFont(f2);
		cQuantity = new JComboBox();
		cQuantity.setFont(f2);
		
		tDiscount = new JTextField();
		tDiscount.setFont(f2);
		
		//Adding elements to the List
		try
		{	
			st=con.createStatement();
			rs=st.executeUpdate("select * from SpareType");
			while(rs.next())
			{
				cType.addItem(rs.getString("Type"));
			}
		}
		catch(SQLException sql)
		{
			JOptionPane.showMessageDialog(this,"Unable to connect to Database");			
		}
		
		liPart = new List();
		liQuantity = new List();
		liQuantity.setEnabled(false);
		liDiscount = new List();
		liDiscount.setEnabled(false);
		
		bAdd = new JButton("Add");
		bAdd.setFont(f3);
		bView = new JButton("View");
		bView.setFont(f3);
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
		
		lPicture.add(lType);
		lType.setBounds(50,135,80,20);
		lPicture.add(lPart);
		lPart.setBounds(50,190,80,20);
		lPicture.add(lQuantity);
		lQuantity.setBounds(50,240,80,30);
		lPicture.add(lDiscount);
		lDiscount.setBounds(50,300,80,30);
		lPicture.add(lPercent);
		lPercent.setBounds(335,305,20,20);
		
		lPicture.add(lInfo);
		lInfo.setBounds(180,330,200,40);
		
		lPicture.add(lPart1);
		lPart1.setBounds(470,90,80,15);
		lPicture.add(lQuantity1);
		lQuantity1.setBounds(535,90,80,15);
		lPicture.add(lDiscount1);
		lDiscount1.setBounds(620,90,80,15);
		
		lPicture.add(liPart);
		liPart.setBounds(450,110,80,145);
		lPicture.add(liQuantity);
		liQuantity.setBounds(532,110,80,145);
		lPicture.add(liDiscount);
		liDiscount.setBounds(614,110,80,145);
		
		lPicture.add(cType);
		cType.setBounds(180,135,150,25);
		lPicture.add(cPart);
		cPart.setBounds(180,185,150,25);
		lPicture.add(cQuantity);
		cQuantity.setBounds(180,240,150,25);
		
		lPicture.add(tDiscount);
		tDiscount.setBounds(180,303,150,25);
		
		lPicture.add(bAdd);
		bAdd.setBounds(200,470,150,40);
		lPicture.add(bView);
		bView.setBounds(450,470,150,40);
		lPicture.add(bHome);
		bHome.setBounds(700,450,70,80);
		
		lPicture.add(sep);
    	sep.setBounds(2,451,702,1);
		
		setLayout(null);
		
		cType.addActionListener(this);
		cPart.addActionListener(this);
		bAdd.addActionListener(this);
		bView.addActionListener(this);
		bRemove.addActionListener(this);
		bRemoveAll.addActionListener(this);
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
			String sTempType = (String)cType.getSelectedItem();
			cPart.removeActionListener(this);
			cQuantity.removeAllItems();
			tDiscount.setText("");
			try
			{
				st=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
				rs=st.executeQuery("select * from Spare where Type='"+sTempType+"'");
				cPart.removeAllItems();
				while(rs.next())
				{
					cPart.addItem(rs.getString("Name"));
				}
				rs.first();
				String sQuantity = rs.getString("Quantity");
				String sCost = rs.getString("Sell");
				int iQuantity = Integer.parseInt(sQuantity);
				for(int i=1;i<=iQuantity;i++)
				{
					cQuantity.addItem(Integer.toString(i));
				}
				lInfo.setText("<html>Available Quantity : "+sQuantity+"<br>Cost per piece : Rs. "+sCost+"</html>");
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
			String sTempPart = (String) cPart.getSelectedItem();
			cQuantity.removeAllItems();
			tDiscount.setText("");
			try
			{
				st=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
				rs=st.executeQuery("select * from Spare where Name='"+sTempPart+"'");
				rs.first();
				sQuantity = rs.getString("Quantity");
				sCost = rs.getString("Sell");
				lInfo.setText("<html>Available Quantity : "+sQuantity+"<br>Cost per piece : Rs. "+sCost+"</html>");
				iQuantity = Integer.parseInt(sQuantity);
				for(int i=1;i<=iQuantity;i++)
				{
					cQuantity.addItem(Integer.toString(i));
				}
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
				String sTempQuantity = (String) cQuantity.getSelectedItem();
				
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
					if((cPart.getSelectedIndex() == -1) || (cQuantity.getSelectedIndex() == -1))
					{
						JOptionPane.showMessageDialog(this,"Please select Type, Part and Quantity !");
					}
					else
					{
						if(tDiscount.getText().equals(""))
						{
							tDiscount.setText("0");
						}
						liPart.addItem(sTempPart);
						liQuantity.addItem(sTempQuantity);
						liDiscount.addItem(tDiscount.getText());
					}
				}
			}
			catch (Exception e)
			{
				System.out.print(e);
				JOptionPane.showMessageDialog(this,"Please select Type, Part and Quantity");
			}
		}
		
		if(ae.getSource() == bView)
		{
			if(liPart.getItemCount() == 0)
			{
				JOptionPane.showMessageDialog(this,"Please select atleast one item !");
			}
			else
			{
				sSelectedParts = liPart.getItems();
				sSelectedQuantity = liQuantity.getItems();
				sSelectedDiscount = liDiscount.getItems();
				VQuot = new ViewQuotation(sSelectedParts,sSelectedQuantity,sSelectedDiscount);
				dispose();
			}
		}
		
		if(ae.getSource() == bRemove)
		{
			try
			{
				int iTempPartIndex = liPart.getSelectedIndex();
				liPart.remove(iTempPartIndex);
				liQuantity.remove(iTempPartIndex);
				liDiscount.remove(iTempPartIndex);
			}
			catch(Exception e)
			{
				JOptionPane.showMessageDialog(this,"Please select an item to remove");
			}
		}
		
		if(ae.getSource() == bRemoveAll)
		{
			liPart.removeAll();
			liQuantity.removeAll();
			liDiscount.removeAll();
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
		
		
		// HOME
		if(ae.getSource() == bHome)
		{
			//dispose();
			new Home();
			dispose();
					
		}
	}
	
	public void setList(String[] sTempPart, String[] sTempQuantity, String[] sTempDiscount)
	{
		for(int i=0;i<sTempPart.length;i++)
		{
			liPart.addItem(sTempPart[i]);
			liQuantity.addItem(sTempQuantity[i]);
			liDiscount.addItem(sTempDiscount[i]);
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
		new Quotation();
	}
	
}