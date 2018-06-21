import java.io.*;
import java.awt.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.table.*;
import javax.swing.border.*;
import javax.swing.border.LineBorder;

class ViewQuotation extends JFrame implements ActionListener
{
	private Container Picture;
	private ImageIcon iPicture,iHome;
	
	private Connection con;
	private Statement st;
	private ResultSet rs;
	private int iResult;
	private Vector vecColumn,vecRow,vecData;
	
	private int iRow,iTemp=0;
	private DefaultTableModel dTable;
	private JTable tableInfo;
	
	private JLabel lPicture,lIntro,lInfo,lInfo2,lTotal,lDiscount,lPercent1,lPercent2,lPercent3,lAdjustment,lPacking;
	private JTextField tDiscount,tAdjustment,tPacking;
	private JButton bPrint,bProduceBill,bCancel,bChange,bHome;
	
	private Font f1,f2,f3,f4;
	
	private JSeparator sep,sep1;
	private Variables var;
	private String[] sTempPart, sTempQuantity,sTempDiscount;
	private Integer[] iTempRate;
	private float[] fTempAftDics;

	public ViewQuotation(String[] sSelectedParts, String[] sSelectedQuantity, String[] sSelectedDiscount)
	{
		// CREATING OBJECT OF Variables
		var = new Variables();
		
		// SETTING FRAME ASPECTS
		setTitle("View Quotation");
		show();
		setResizable(false);
		setLocation(var.d.width/10-getWidth()/2,var.d.height/10-getHeight()/2);
		setSize(800,700);
		
		// INITIALIZING and ADDING PICTURE
		Picture = getContentPane();
		getContentPane().setLayout(null);
		iPicture = new ImageIcon("C://b3//Auto Spares//Images//Horizon (2).jpg");
		lPicture = new JLabel(iPicture);
		Picture.add(lPicture);
		lPicture.setBounds(0,0,800,700);
		
		// DATE
		lPicture.add(var.lDate);
		var.lDate.setBounds(600,20,60,25);
		var.lDate.setForeground(Color.WHITE);
		lPicture.add(var.tDate);
		var.tDate.setBounds(665,20,110,25);
		
		// INITIALIZING ELEMENTS ON FRAME
		
		f1 = new Font("Times New Roman",Font.ITALIC+Font.BOLD,24);
		f2 = new Font("Brush Script MT",Font.PLAIN,30);
		f3 = new Font("Times New Roman",Font.ITALIC,24);
		f4 = new Font("Courier New",Font.PLAIN,20);
		
		lInfo = new JLabel("<html>The following table comprises of the spare parts you selected with the desired quantity, you may change your selection by pressing the \"Change\" button.");
		lInfo.setFont(f1);
		lInfo.setForeground(Color.BLACK);
		lInfo2 = new JLabel("<html>The above cost is exclusive of all extra charges and discounts, the amount after applying the specified discount and charges will be considered. If you leave the discount field blank, it will be considered as 0.");
		lInfo2.setFont(new Font("Times New Roman",Font.ITALIC+Font.BOLD,18));
		lInfo2.setForeground(Color.BLACK);

		lIntro = new JLabel("View Quotation");
		lIntro.setFont(new Font("Times New Roman",Font.PLAIN,40));
		lIntro.setForeground(Color.BLACK);
		
		lTotal = new JLabel("Total : Rs. ");
		lTotal.setFont(f2);
		lTotal.setForeground(Color.BLACK);
		
		lDiscount = new JLabel("Discount");
		lDiscount.setFont(f3);
		lDiscount.setForeground(Color.BLACK);
		lPercent1 = new JLabel("%");
		lPercent1.setFont(f3);
		lPercent1.setForeground(Color.BLACK);
		lPercent2 = new JLabel("%");
		lPercent2.setFont(f3);
		lPercent2.setForeground(Color.BLACK);
		lPercent3 = new JLabel("%");
		lPercent3.setFont(f3);
		lPercent3.setForeground(Color.BLACK);
		lAdjustment = new JLabel("<html>Miscellaneous<br>adjustment</html>");
		lAdjustment.setFont(f3);
		lAdjustment.setForeground(Color.BLACK);
		lPacking = new JLabel("<html>Packing /<br>Forwarding</html>");
		lPacking.setFont(f3);
		lPacking.setForeground(Color.BLACK);
		
		tDiscount = new JTextField();
		tDiscount.setFont(f4);
		tAdjustment = new JTextField();
		tAdjustment.setFont(f4);
		tAdjustment.setText("5.0");
		//tAdjustment.setEditable(false);
		tPacking = new JTextField();
		tPacking.setFont(f4);
		tPacking.setText("3.0");
		//tPacking.setEditable(false);
		
		bPrint = new JButton("Print");
		bPrint.setForeground(Color.GREEN);
		bPrint.setBackground(Color.BLACK);
		bPrint.setFont(f2);
		bProduceBill = new JButton("Produce Bill");
		bProduceBill.setForeground(Color.CYAN);
		bProduceBill.setBackground(Color.BLACK);
		bProduceBill.setFont(f2);
		bCancel = new JButton("Cancel");
		bCancel.setForeground(Color.RED);
		bCancel.setBackground(Color.BLACK);
		bCancel.setFont(f2);
		bChange = new JButton("Change");
		bChange.setForeground(Color.RED);
		bChange.setBackground(Color.BLACK);
		bChange.setFont(f2);
		
		bHome = new JButton("Home");
		iHome = new ImageIcon("C://b3//Auto Spares//Images//Home icon.gif");
		bHome = new JButton(iHome);
		
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
		
		// GETTING THE COSTS OF PARTS SELECTED
		int n = sSelectedParts.length;
		Integer iCost[] = new Integer[n];
		try
		{
			st=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			for(int i=0;i<sSelectedParts.length;i++)
			{
				rs=st.executeQuery("select * from Spare where Name='"+sSelectedParts[i]+"'");
				rs.first();
				iCost[i] = (Integer.parseInt(rs.getString("Sell")));
			}
		}
		catch(SQLException sql)
		{
			JOptionPane.showMessageDialog(this,"Unable to connect to Database");
			System.out.print(sql);			
		}
		// FINDING TOTAL COST FOR PER ITEM SELECTED
		Integer iTotalCost[] = new Integer[n];
		for(int i=0;i<n;i++)
		{
			iTotalCost[i] = iCost[i]*(Integer.parseInt(sSelectedQuantity[i]));
		}
		
		// INITIALIZING TABLE
		String[] sColumn = {"Sr. No.","Name","Quantity","Cost per piece","Total cost","After discount"};
		iRow = sSelectedParts.length;
		try
		{
			vecColumn = new Vector();
			vecData = new Vector();
			float fAfterDiscount[] = new float[sSelectedDiscount.length];
			int iValue=0;
			float fValue=0;
			for(int i=0;i<sColumn.length; i++)	// ADDS THE COLUMNS TO THE TABLE
            {
            	vecColumn.addElement(sColumn[i]);
            }
            var.fTotal = 0;
            while(iTemp<iRow)
            {
            	
            	iValue = Integer.parseInt(sSelectedDiscount[iTemp]);
            	fValue = iValue/(float)100;
            	fAfterDiscount[iTemp] = (float)iTotalCost[iTemp] - (fValue * (float)iTotalCost[iTemp]);
            	
            	vecRow = new Vector(iRow);
            	
            	vecRow.addElement((iTemp+1));
                vecRow.addElement(sSelectedParts[iTemp]);
                vecRow.addElement(sSelectedQuantity[iTemp]);
                vecRow.addElement(iCost[iTemp]);
                vecRow.addElement(iTotalCost[iTemp]);
                vecRow.addElement(fAfterDiscount[iTemp]);
                
                vecData.addElement(vecRow);
                
                var.fTotal += fAfterDiscount[iTemp]; 
                
                iTemp++;
            }
            sTempPart = sSelectedParts;
            sTempQuantity = sSelectedQuantity;
            iTempRate = iCost;
            sTempDiscount = sSelectedDiscount;
            fTempAftDics = fAfterDiscount;
            
            lTotal.setText("Total : Rs. "+var.fTotal+" /-");
		}
		catch(Exception sqle)
		{
			JOptionPane.showMessageDialog(this,"Unable to connect to the Database !");
			System.out.print(sqle);
		}
		tableInfo = new JTable(vecData,vecColumn);
		tableInfo.setAutoscrolls(true);
		JScrollPane scrollBar = new JScrollPane(tableInfo,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
		lPicture.add(scrollBar);
		scrollBar.setBounds(20,160,620,160);
		
		// ADDING ELEMENTS ON THE FRAME
		lPicture.add(lIntro);
		lIntro.setBounds(250,10,400,40);
		
		lPicture.add(lInfo);
		lInfo.setBounds(20,80,800,60);
		lPicture.add(lInfo2);
		lInfo2.setBounds(450,360,200,200);
		
		lPicture.add(lTotal);
		lTotal.setBounds(430,330,240,20);
		
		lPicture.add(lDiscount);
		lDiscount.setBounds(20,370,100,30);
		lPicture.add(lAdjustment);
		lAdjustment.setBounds(20,420,160,50);
		lPicture.add(lPacking);
		lPacking.setBounds(20,490,160,55);
		lPicture.add(lPercent1);
		lPercent1.setBounds(360,372,30,30);
		lPicture.add(lPercent2);
		lPercent2.setBounds(360,435,30,30);
		lPicture.add(lPercent3);
		lPercent3.setBounds(360,500,30,30);
		
		lPicture.add(tDiscount);
		tDiscount.setBounds(200,372,150,30);
		lPicture.add(tAdjustment);
		tAdjustment.setBounds(200,435,150,30);
		lPicture.add(tPacking);
		tPacking.setBounds(200,500,150,30);
		
		lPicture.add(bPrint);
		bPrint.setBounds(20,600,140,50);
		lPicture.add(bProduceBill);
		bProduceBill.setBounds(250,600,160,50);
		lPicture.add(bCancel);
		bCancel.setBounds(500,600,140,50);
		lPicture.add(bChange);
		bChange.setBounds(660,160,120,50);
		
		lPicture.add(bHome);
		bHome.setBounds(700,570,70,80);
		bHome.setToolTipText("Go to Home");
	
		setLayout(null);

		bHome.addActionListener(this);
		bCancel.addActionListener(this);
		bChange.addActionListener(this);
		bProduceBill.addActionListener(this);
		addWindowListener(new WindowClass());
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource() == bCancel)
		{
			int reply = JOptionPane.showConfirmDialog(null,"<html>Are you sure you want to cancel the quotation made?<br>The selected items will be removed !</html>","",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE);
			if(reply == JOptionPane.YES_OPTION)
			{
				new Quotation();
				dispose();
			}
			else
			{
				setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			}
		}
		
		if(ae.getSource() == bProduceBill)
		{
			
			String sDiscount = tDiscount.getText();
			String sAdjustment = tAdjustment.getText();
			String sPacking = tPacking.getText();
			
			if(sDiscount.equals(""))
			{
				tDiscount.setText("0");
			}
			if(sAdjustment.equals(""))
			{
				tAdjustment.setText("0");
			}
			if(sPacking.equals(""))
			{
				tPacking.setText("0");
			}
			var.sVarName = sTempPart;
			var.sVarQuantity = sTempQuantity;
			var.iVarRate = iTempRate;
			var.sVarDisc = sTempDiscount;
			var.fAdjustment = Float.parseFloat(sAdjustment);
			var.fPacking = Float.parseFloat(sPacking);
			var.fAmount = fTempAftDics;
			
			var.fDiscount = Float.parseFloat(tDiscount.getText());
			new CustomerDetails();
			dispose();
		}
		
		if(ae.getSource() == bChange)
		{
			int reply = JOptionPane.showConfirmDialog(null,"Are you sure you want to change the selection?","",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE);
			if(reply == JOptionPane.YES_OPTION)
			{
				Quotation Quot = new Quotation();
				Quot.setList(sTempPart,sTempQuantity,sTempDiscount);
				dispose();
			}
			else
			{
				setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			}
		}
		
		if(ae.getSource() == bHome)
		{
			int reply = JOptionPane.showConfirmDialog(null,"Are you sure you want to go Home?","",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE);
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
}