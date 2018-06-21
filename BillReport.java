import java.io.*;
import java.awt.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.table.*;
import javax.swing.border.*;
import javax.swing.border.LineBorder;

class BillReport extends JFrame implements ActionListener
{
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
	private JLabel lPicture,lIntro;

	private JButton bPrint,bExit,bHome;
	
	private Variables var;

	public BillReport()
	{
		// CREATING OBJECT OF Variables
		var = new Variables();
		
		// SETTING FRAME ASPECTS
		setTitle("Bill Report");
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
		
		lIntro = new JLabel("Bill Report");
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
		
		// INTITIALIZING THE TABLE
		try
		{
			st=con.createStatement();
			rs=st.executeQuery("select * from BillReport");
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
		lPicture.add(scrollBar);
		scrollBar.setBounds(60,70,715,300);
		
		// DEFINING BUTTONS
		iHome = new ImageIcon("C://b3//Auto Spares//Images//Home icon.gif");
		bHome = new JButton(iHome);
		lPicture.add(bHome);
		bHome.setBounds(725,396,70,80);
		bHome.setToolTipText("Go to Home");
		bPrint = new JButton("Print");
		bPrint.setForeground(Color.WHITE);
		bPrint.setBackground(Color.black);
		bPrint.setFont(new Font("Rage Italic",Font.PLAIN,35));
		bExit = new JButton("Exit");
		bExit.setForeground(Color.WHITE);
		bExit.setBackground(Color.black);
		bExit.setFont(new Font("Rage Italic",Font.PLAIN,35));
		
		lPicture.add(lIntro);
		lIntro.setBounds(300,15,250,40);
		lPicture.add(bPrint);
		bPrint.setBounds(100,415,200,60);
		lPicture.add(bExit);
		bExit.setBounds(400,415,200,60);
		lPicture.add(bHome);
		
		setLayout(null);
		
		// ADDING EVENT LISTENER
		
		bPrint.addActionListener(this);
		bExit.addActionListener(this);
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
		if(ae.getSource() == bPrint)
		{
			JOptionPane.showMessageDialog(this,"Printer not connected !!");
		}
		if(ae.getSource() == bHome)
		{
			dispose();
			new Home();
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
			new BillReport();
			dispose();
		}
		if(ae.getSource() == var.miDistributor)
		{
			new Distributor();
			dispose();
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
		new BillReport();
	}
	
}