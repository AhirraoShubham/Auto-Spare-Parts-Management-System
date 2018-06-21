import java.io.*;
import java.awt.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.table.*;
import javax.swing.border.*;
import javax.swing.border.LineBorder;

class Spare extends JFrame implements ActionListener, ItemListener
{
	private JTabbedPane tabSpare;
	
	private Connection con;
	private Statement st;
	private ResultSet rs;
	private int iResult;
	private Vector vecColumn,vecRow,vecData;
	private int cols;
	
	private DefaultTableModel dTable;
	private JTable tableInfo;
	
	private Container Picture;
	private ImageIcon iPicture,iHome;
	
	private int iID,iPurchase,iSell,iQuantity;
	private String sName,sType,sInput;
	private JLabel lIntro,lID,lName,lType,lPurchase,lSell,lQuantity,lPicture,lNote;
	private JTextField tID,tName,tPurchase,tSell,tQuantity;
	private ButtonGroup bg;
	private JRadioButton rAdd,rUpdate,rRemove,rAddType,rRemoveType;
	private JComboBox cType,cID;
	private Font f1,f2,f3;
	private JPanel pTable,pManage;
	
	private JSeparator sep,sep1;
	private JButton bRefresh,bSubmit,bClear,bHome;
	
	private Variables var;

	public Spare()
	{
		// CREATING OBJECT OF Variables
		var = new Variables();
		
		// SETTING FRAME ASPECTS
		setTitle("Spare parts");
		show();
		setResizable(false);
		setLocation(var.d.width/10-getWidth()/2,var.d.height/10-getHeight()/2);
		setSize(805,540);
		setJMenuBar(var.mb);
		
		// INITIALIZING and ADDING PICTURE
		Picture = getContentPane();
		getContentPane().setLayout(null);
		iPicture = new ImageIcon("C://b3//Auto Spares//Images//Horizon.jpg");
		lPicture = new JLabel(iPicture);
		
		Picture.add(lPicture);
		lPicture.setBounds(0,0,800,500);
		
		lIntro = new JLabel("Spare Parts");
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
			con = DriverManager.getConnection("jdbc:odbc:Auto spare","","");
			st = con.createStatement();
		}
		catch(SQLException se)
		{
			JOptionPane.showMessageDialog(this, "Sorry, unable to connect to database !!");
			dispose();
		}
		
		// INITIALIZING TABLE i.e. TAB "VIEW"
		bRefresh = new JButton("Refresh");
		bRefresh.setToolTipText("Refresh the above table");
		pTable = new JPanel();
		pTable.setLayout(null);
		try
		{
			st=con.createStatement();
			rs=st.executeQuery("select * from Spare");
			ResultSetMetaData md = rs.getMetaData();
			cols = md.getColumnCount();
			vecColumn = new Vector();
			vecData = new Vector();
			for(int i=1;i<=cols; i++)
            {
            	vecColumn.addElement(md.getColumnName(i));
            }
            while(rs.next())
            {
            	vecRow = new Vector(cols);
            	for (int i=1;i<=cols;i++)
                {
                	vecRow.addElement(rs.getObject(i));
                }
                vecData.addElement(vecRow);
            }
		}
		catch(SQLException sqle)
		{
			JOptionPane.showMessageDialog(this,"Unable to connect to the Database !");			
		}
		tableInfo = new JTable(vecData,vecColumn);
		tableInfo.setAutoscrolls(true);
        JScrollPane scrollBar = new JScrollPane(tableInfo,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
		pTable.add(scrollBar);
		scrollBar.setBounds(0,0,694,350);
		pTable.add(bRefresh);
		bRefresh.setBounds(2,355,100,40);
		
		// INITIALIZING COMPONENTS OF TAB "MANAGE"
		f1 = new Font("Times New Roman",Font.PLAIN,20);
		f2 = new Font("Courier New",Font.PLAIN,15);
		f3 = new Font("Brush Script MT",Font.PLAIN,30);
		
		lID = new JLabel("ID");
		lID.setBounds(50,30,50,20);
		lID.setFont(f1);
		lName = new JLabel("Name");
		lName.setBounds(50,80,50,20);
		lName.setFont(f1);
		lType = new JLabel("Type");
		lType.setBounds(50,130,50,20);
		lType.setFont(f1);
		lPurchase = new JLabel("Purchase rate");
		lPurchase.setBounds(50,180,120,20);
		lPurchase.setFont(f1);
		lSell = new JLabel("Sell rate");
		lSell.setBounds(50,230,120,20);
		lSell.setFont(f1);
		lQuantity = new JLabel("Quantity");
		lQuantity.setBounds(50,280,80,20);
		lQuantity.setFont(f1);
		
		lID.setEnabled(false);
		lName.setEnabled(false);
		lType.setEnabled(false);
		lPurchase.setEnabled(false);
		lSell.setEnabled(false);
		lQuantity.setEnabled(false);
		
		lNote = new JLabel("Please select an option from above !");
		lNote.setBounds(420,220,250,100);
		lNote.setFont(new Font("Times New Roman",Font.PLAIN,16));
		lNote.setForeground(Color.BLUE);
		
		tID = new JTextField(10);
		tID.setBounds(190,30,150,25);
		tID.setFont(f2);
		tName = new JTextField(10);
		tName.setBounds(190,80,150,25);
		tName.setFont(f2);
		tPurchase = new JTextField(10);
		tPurchase.setBounds(190,180,150,25);
		tPurchase.setFont(f2);
		tSell = new JTextField(10);
		tSell.setBounds(190,230,150,25);
		tSell.setFont(f2);
		tQuantity = new JTextField(10);
		tQuantity.setBounds(190,280,150,25);
		tQuantity.setFont(f2);
		
		tID.setEnabled(false);
		tName.setEnabled(false);
		tPurchase.setEnabled(false);
		tSell.setEnabled(false);
		tQuantity.setEnabled(false);
		
		cType = new JComboBox();
		cType.setBounds(190,130,150,25);
		cType.setEnabled(false);
		cType.setFont(f2);
		
		cID = new JComboBox();
		cID.setBounds(190,30,150,25);
		cID.setVisible(false);
		cID.setFont(f2);
		
		//Adding spare type to the Combo box
		try
		{	
			st=con.createStatement();
			rs=st.executeQuery("select * from Spare");
			while(rs.next())
			{
				cID.addItem(rs.getString("ID"));
			}				
		}
		catch(SQLException sql)
		{
			JOptionPane.showMessageDialog(this,"Unable to connect to Database");			
		}
		
		//Adding spare type to the Combo box
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
		
		bg = new ButtonGroup();
		
		rAdd = new JRadioButton("Add new Spare");
		rAdd.setBounds(450,20,150,25);
		rAdd.setFont(f1);
		rUpdate = new JRadioButton("Update Spare");
		rUpdate.setBounds(450,65,150,25);
		rUpdate.setFont(f1);
		rRemove = new JRadioButton("Remove Spare");
		rRemove.setBounds(450,110,150,25);
		rRemove.setFont(f1);
		rAddType = new JRadioButton("Add new spare type");
		rAddType.setBounds(450,155,200,25);
		rAddType.setFont(f1);
		rRemoveType = new JRadioButton("Remove spare type");
		rRemoveType.setBounds(450,200,200,25);
		rRemoveType.setFont(f1);
		
		bg.add(rAdd);
		bg.add(rUpdate);
		bg.add(rRemove);
		bg.add(rAddType);
		bg.add(rRemoveType);
		
		// DEFINING BUTTONS
		bSubmit = new JButton("Submit");
		bSubmit.setBounds(150,345,150,40);
		bSubmit.setFont(f3);
		
		bClear = new JButton("Clear");
		bClear.setBounds(400,345,150,40);
		bClear.setFont(f3);
		
		// INITILIZING PANEL AND ADDING COMPONENTS TO IT
		pManage = new JPanel();
		pManage.setLayout(null);
		pManage.add(lID);	pManage.add(tID);	pManage.add(cID);
		pManage.add(lName);	pManage.add(tName);
		pManage.add(lType);	pManage.add(cType);
		pManage.add(lPurchase);	pManage.add(tPurchase);
		pManage.add(lSell);	pManage.add(tSell);
		pManage.add(lQuantity);	pManage.add(tQuantity);
		
		pManage.add(rAdd);
		pManage.add(rUpdate);
		pManage.add(rRemove);
		pManage.add(rAddType);
		pManage.add(rRemoveType);
		
		pManage.add(lNote);
		
		pManage.add(bSubmit);
		pManage.add(bClear);
		
		iHome = new ImageIcon("C://b3//Auto Spares//Images//Home icon.gif");
		bHome = new JButton(iHome);
		lPicture.add(bHome);
		bHome.setBounds(725,396,70,80);
		bHome.setToolTipText("Go to Home");
		
		sep = new JSeparator();
    	sep.setBounds(0,330,800,10);
 	    sep.setForeground(Color.LIGHT_GRAY);
		pManage.add(sep);
		
		sep1 = new JSeparator(JSeparator.VERTICAL);
		sep1.setBounds(400,0,1,330);
		sep1.setForeground(Color.LIGHT_GRAY);
		pManage.add(sep1);
		
		// INITIALIZING TABS
		tabSpare = new JTabbedPane();
		tabSpare.setBorder(new LineBorder(new Color(255,0,0),0));
		tabSpare.addTab("View",pTable);
		tabSpare.addTab("Manage",pManage);
		
		lPicture.add(tabSpare);
		tabSpare.setBounds(20,52,700,425);
		lPicture.add(lIntro);
		lIntro.setBounds(315,15,200,45);

		setLayout(null);
		
		// ADDING EVENT LISTENER
		rAdd.addItemListener(this);
		rUpdate.addItemListener(this);
		rRemove.addItemListener(this);
		
		cID.addActionListener(this);
		bRefresh.addActionListener(this);
		bSubmit.addActionListener(this);
		rAddType.addActionListener(this);
		rRemoveType.addActionListener(this);
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
		if(ae.getSource() == bRefresh)
		{
			dispose();
			new Spare();
		}
		
		if(ae.getSource() == bSubmit)
		{
			if(rAdd.isSelected())
			{
				try
				{
					iID = Integer.parseInt(tID.getText());
					sName = tName.getText();
					iPurchase = Integer.parseInt(tPurchase.getText());
					iSell = Integer.parseInt(tSell.getText());
					iQuantity = Integer.parseInt(tQuantity.getText());
					sType = (String)cType.getSelectedItem();
					boolean check = true;
					
					for(int i=0;i<sName.length();i++)
					{
						if(Character.isDigit(sName.charAt(i)))
						{
							check = false;
						}
					}

					if(check == true)
					{
						if((sName.equals(""))||(iPurchase == 0)||(iSell == 0)||(iQuantity == 0))
						{
							JOptionPane.showMessageDialog(this,"Please enter the details properly !");
						}
						else
						{	
							//insert into Spare values(5,'Fan','Metal',250,270,5)
							iResult = st.executeUpdate("insert into Spare values("+iID+",'"+sName+"','"+sType+"',"+iPurchase+","+iSell+","+iQuantity+")");
							if(iResult==1)
							{
								// If Query succesful
								JOptionPane.showMessageDialog(this,"Record saved successfully");
								reset();
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
						JOptionPane.showMessageDialog(this,"Spare name should not contain any digits !!");
				}
				catch(NumberFormatException nfe)
				{
					JOptionPane.showMessageDialog(this,"Please enter the valid data");
					clear();
				}
				catch(SQLException sql)
				{
					System.out.print(sql);	
					JOptionPane.showMessageDialog(this,"Unable to connect to Database");	
					clear();	
				}
			}

			else if(rUpdate.isSelected())
			{
				try
				{
					iID = Integer.parseInt((String)cID.getSelectedItem());
					iPurchase = Integer.parseInt(tPurchase.getText());
					iSell = Integer.parseInt(tSell.getText());
					iQuantity = Integer.parseInt(tQuantity.getText());
					sName = tName.getText();
					sType = (String)cType.getSelectedItem();
					boolean check1 = true;
					
					for(int i=0;i<sName.length();i++)
					{
						if(Character.isDigit(sName.charAt(i)))
						{
							check1 = false;
						}
					}
					
					if(check1 == true)
					{
						if((sName.equals(""))||(iPurchase == 0)||(iSell == 0)||(iQuantity == 0))
						{
							JOptionPane.showMessageDialog(this,"Please enter the details properly !");
						}
						else
						{
							//update from Spare set Name='Tail Light',Type='Plastic',Purchase=100,Sell=120,Quantity=9 where ID=8
							iResult = st.executeUpdate("update Spare set Name='"+sName+"',Type='"+sType+"',Purchase="+iPurchase+",Sell="+iSell+",Quantity="+iQuantity+" where ID="+iID);
							if(iResult==1)
							{
								// If Query succesful
								JOptionPane.showMessageDialog(this,"Record updated successfully");
								reset();												
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
						JOptionPane.showMessageDialog(this,"Spare name should not contain any digits !!");
				}
				catch(NumberFormatException nfe)
				{
					System.out.print(nfe);
					JOptionPane.showMessageDialog(this,"Please enter the valid data");
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
					iID = Integer.parseInt(tID.getText());
	
					iResult = st.executeUpdate("delete * from Spare where ID="+iID+"");
					if(iResult==1)
					{
						// If Query succesful
						JOptionPane.showMessageDialog(this,"Record deleted successfully");
						reset();												
					}
					else
					{
						//  Query failed
						JOptionPane.showMessageDialog(this,"The spare part with provided ID does not exist");
						clear();
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
		
		if(ae.getSource() == rAddType)
		{
				iResult=0;
				try
				{
					lNote.setText("Please select an option from above !");
					boolean check2 = true;
			
					lID.setEnabled(false);	
					tID.setEnabled(false);
					clear();
					disable();
					
					sInput = JOptionPane.showInputDialog(null,"Please enter the name of new Spare Type");
					
					for(int i=0;i<sInput.length();i++)
					{
						if(Character.isDigit(sInput.charAt(i)))
						{
							check2 = false;
						}
					}

					if(check2 == true)
					{
						if((sInput == null)||(sInput.isEmpty()))
						{
							JOptionPane.showMessageDialog(this,"Spare type not added sucessfuly");
						}
						else
						{
							iResult = st.executeUpdate("insert into SpareType values('"+sInput+"')");
							
							if(iResult==1)
							{
								// If Query succesful
								JOptionPane.showMessageDialog(this,"Spare type added sucessfuly");
								try
								{	
									st=con.createStatement();
									rs=st.executeQuery("select * from SpareType");
									cType.removeAllItems();
									while(rs.next())
									{
										cType.addItem(rs.getString("Type"));
									}				
								}
								catch(SQLException sql)
								{
									JOptionPane.showMessageDialog(this,"Unable to connect to Database");			
								}
								clear();												
							}
							else
							{
								//  Query failed
								JOptionPane.showMessageDialog(this,"Spare type not added sucessfuly");
							}
						}
					}
					else
						JOptionPane.showMessageDialog(this,"Type name should not contain any digits !!");
				}
				catch(SQLException e)
				{
					JOptionPane.showMessageDialog(this,"This type of spare part already exist");
				}
		}
		
		if(ae.getSource() == rRemoveType)
		{
				iResult=0;
				try
				{
					boolean check3 = true;
					lNote.setText("Please select an option from above !");
					lID.setEnabled(false);
					tID.setEnabled(false);
					clear();
					disable();
					
					sInput = JOptionPane.showInputDialog(null,"Please enter the name of Spare Type to be deleted");
					
					for(int i=0;i<sInput.length();i++)
					{
						if(Character.isDigit(sInput.charAt(i)))
						{
							check3 = false;
						}
					}

					if(check3 == true)
					{
						if((sInput == null)||(sInput.isEmpty()))
						{
							JOptionPane.showMessageDialog(this,"Spare type not deleted sucessfuly");
						}
						else
						{
							iResult = st.executeUpdate("delete * from SpareType where Type='"+sInput+"'");
							
							if(iResult==1)
							{
								// If Query succesful
								JOptionPane.showMessageDialog(this,"Spare type deleted sucessfuly");
								try
								{	
									st=con.createStatement();
									rs=st.executeQuery("select * from SpareType");
									cType.removeAllItems();
									while(rs.next())
									{
										cType.addItem(rs.getString("Type"));
									}				
								}
								catch(SQLException sql)
								{
									JOptionPane.showMessageDialog(this,"Unable to connect to Database");			
								}
								clear();
							}
							else
							{
								//  Query failed
									JOptionPane.showMessageDialog(this,"Spare type not deleted sucessfuly");
							}
						}
					}
					else
						JOptionPane.showMessageDialog(this,"Type name should not contain any digits !!");
				}
				catch(SQLException e)
				{
					JOptionPane.showMessageDialog(this,"The provided spare type does not exist");
				}
		}
		
		if(ae.getSource() == cID)
		{
			try
			{	
				st=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
				rs=st.executeQuery("select * from Spare where ID="+(cID.getSelectedItem()));
				rs.first();
				tName.setText(""+rs.getString("Name"));
				cType.setSelectedItem(rs.getString("Type"));
				tPurchase.setText(""+rs.getInt("Purchase"));
				tSell.setText(""+rs.getInt("Sell"));
				tQuantity.setText(""+rs.getInt("Quantity"));
			}
			catch(SQLException sql)
			{
				System.out.print(sql);
				JOptionPane.showMessageDialog(this,"Unable to connect to Database");			
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
			new Home();
			dispose();
		}
	}
	
	public void itemStateChanged(ItemEvent ie)
	{
		if(ie.getItem() == rAdd)
		{
			lNote.setText("<html>Please enter the details of the new<br>Spare part !</br></html>");
			clear();
			enable();
			tID.setVisible(true);
			tID.setEditable(false);
			cID.setVisible(false);
			try
			{	
				st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
				rs=st.executeQuery("select * from Spare");
				rs.last();
				tID.setText(""+(1+rs.getInt("ID")));
			}
			catch(SQLException sql)
			{
				JOptionPane.showMessageDialog(this,"Unable to connect to Database");
				System.out.print(sql);			
			}
		}
		if(ie.getItem() == rUpdate)
		{
			lNote.setText("<html>Please enter the new details of the<br>Spare part to be updated !</br></html>");
			tID.setVisible(false);
			cID.setVisible(true);
			clear();
			enable();
		}
		if(ie.getItem() == rRemove)
		{
			lNote.setText("<html>Please enter the ID of the<br>Spare part to be removed !</br></html>");
			
			lID.setEnabled(true);	
			tID.setEnabled(true);
			tID.setEditable(true);
			tID.setVisible(true);
			cID.setVisible(false);
			clear();
			disable();
		}
	}
	
	public void enable()
	{
		lID.setEnabled(true);	
		lName.setEnabled(true);
		lType.setEnabled(true);
		lPurchase.setEnabled(true);
		lSell.setEnabled(true);
		lQuantity.setEnabled(true);
		
		tID.setEnabled(true);
		tID.setEditable(true);
		tName.setEnabled(true);
		cType.setEnabled(true);
		tPurchase.setEnabled(true);
		tSell.setEnabled(true);
		tQuantity.setEnabled(true);
	}
	
	public void disable()
	{
		lName.setEnabled(false);
		lType.setEnabled(false);
		lPurchase.setEnabled(false);
		lSell.setEnabled(false);
		lQuantity.setEnabled(false);
		
		tName.setEnabled(false);
		cType.setEnabled(false);
		tPurchase.setEnabled(false);
		tSell.setEnabled(false);
		tQuantity.setEnabled(false);
	}
	
	public void clear()
	{
		if(rAdd.isSelected())
		{
				try
				{	
					st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
					rs=st.executeQuery("select * from Spare");
					rs.last();
					tID.setText(""+(1+rs.getInt("ID")));
				}
				catch(SQLException sql)
				{
					JOptionPane.showMessageDialog(this,"Unable to connect to Database");
					System.out.print(sql);			
				}
		}
		else
		{
			tID.setText("");
		}
		tName.setText("");
		tPurchase.setText("");
		tSell.setText("");
		tQuantity.setText("");
	}
	
	public void reset()
	{
		bg.clearSelection();
		lNote.setText("Please select an option from above !");
		lID.setEnabled(false);
		tID.setEnabled(false);
		disable();
		clear();
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
		new Spare();
	}
	
}