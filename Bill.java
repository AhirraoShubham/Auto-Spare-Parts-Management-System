import java.io.*;
import java.awt.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.table.*;
import javax.swing.border.*;
import javax.swing.border.LineBorder;

class Bill extends JFrame implements ActionListener
{
	private JPanel backInv;
	private JDialog invo;
	private Variables var;
	
	private Container cpane;
	private JLabel blno,dat,cname,phon,addr;
	private java.awt.List sno1,desc1,qnt1,rat1,disc1,netamt1;
	private JLabel blno1,cname1,phon1,addr1,head1,head2,tabs;
	private JLabel tot,pic,vat,disnt,disc,total,pacfor,totsal,lTotal;
	private JTextField disnt1,total1,pacfor1;
	private JButton print,save,home;
	private Font f1,f2;
	private JSeparator s1,s2,s3,s4;
	
	private int iCustID=0;
		
	private float fFinalCost=0,temp,temp1,temp2;
	
	public Bill()
	{
		// CREATING OBJECT OF Variables
		var = new Variables();
		
		// DATE
		var.lDate.setBounds(520,152,150,30);
		var.lDate.setForeground(Color.BLACK);
		var.lDate.setText(var.lDate.getText()+" "+var.tDate.getText());

		backInv=new JPanel();
		invo=new JDialog();

		cpane=invo.getContentPane();
	       add(backInv);
	       cpane.setLayout(null);
	       invo.setTitle("Invoice : Delivery Chalon");
	       invo.setResizable(false);
	       invo.setLocation(var.d.width/20-getWidth()/2,var.d.height/20-getHeight()/2);
	       
	       head1=new JLabel("Invoice Bill");
	       head1.setFont(new Font("Times New Roman",Font.BOLD,35));
	       head1.setForeground(Color.DARK_GRAY);
	       head1.setBounds(250,70,300,40);
	       head2=new JLabel("First Choice Auto Spare Parts");
	       head2.setBounds(110,0,700,50);
	       head2.setFont(new Font("Times New Roman",Font.BOLD+Font.ITALIC,35));
	       head2.setForeground(Color.DARK_GRAY);
	       
	       f1=new Font("Times New Roman",Font.BOLD,16);
	       blno=new JLabel("Invoice no. : ");
	       blno.setFont(f1);
	       blno.setBounds(10,150,120,30);
	       try
	       {
	       		Connection con = DriverManager.getConnection("jdbc:odbc:Auto spare","","");
				Statement st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
				ResultSet rs=st.executeQuery("select * from BillReport");
				rs.last();
				blno.setText(blno.getText()+" "+(1+rs.getInt("Bill ID")));
	       }
	       catch(Exception e)
	       {
	       		System.out.print(e);
	       }
	       
	       dat=new JLabel("Date : ");
	       dat.setFont(f1);
	       dat.setBounds(550,150,80,30);
	      
	       
	       s1=new JSeparator();
	       s1.setBounds(10,180,680,10);
	       s1.setForeground(Color.BLACK);
	       cname=new JLabel("Name : ");
	       cname.setFont(f1);
	       cname.setBounds(10,185,150,30);
	       cname1=new JLabel("");
	       cname1.setFont(f1);
	       cname1.setBounds(160,185,250,30);
	       cname1.setForeground(Color.DARK_GRAY);
	       phon=new JLabel("Ph. no. : ");
	       phon.setFont(f1);
	       phon.setBounds(450,185,100,30);
	       phon1=new JLabel("");
	       phon1.setFont(f1);
	       phon1.setBounds(550,185,150,30);
	       phon1.setForeground(Color.DARK_GRAY);
	       addr=new JLabel("Address : ");
	       addr.setFont(f1);
	       addr.setBounds(10,210,150,30);
	       addr1=new JLabel("");
	       addr1.setFont(f1);
	       addr1.setBounds(160,210,200,30);
	       addr1.setForeground(Color.DARK_GRAY);
	       lTotal = new JLabel("Total : "+var.fTotal);
	       lTotal.setFont(f1);
	       lTotal.setBounds(545,502,150,30);
	       
	       
	       
	       s2=new JSeparator();
	       s2.setBounds(10,235,680,10);
	       s2.setForeground(Color.BLACK);
	       tabs=new JLabel("  S.No	                   "+  "            Description	        "+ "	                  Quantity        Rate       Discount% "+"   Net Amount");
	       tabs.setBounds(5,240,695,30);                                       
	       tabs.setFont(f1);
	       
	       sno1=new java.awt.List();
		   sno1.setBounds(5,270,50,230);
		   
		   desc1=new java.awt.List();
		   desc1.setBounds(55,270,295,230);
		   
		   qnt1=new java.awt.List();
		   qnt1.setBounds(350,270,70,230);
	       
	       rat1=new java.awt.List();
		   rat1.setBounds(420,270,80,230);
		   
		   disc1=new java.awt.List();
		   disc1.setBounds(500,270,85,230);
		   
		   netamt1=new java.awt.List();
		   netamt1.setBounds(585,270,100,230);

		   s3=new JSeparator();
	       s3.setBounds(10,535,680,10);
	       s3.setForeground(Color.BLACK);
	       
	       disc = new JLabel("Discount : ");
	       disc.setFont(f1);
	       disc.setBounds(20,540,190,30);
	       
		   disnt=new JLabel("Miscellaneous Adjustment : ");
	       disnt.setFont(f1);
	       disnt.setBounds(20,580,250,30);
	       
	       pacfor=new JLabel("Packing & Forwarding : ");
	       pacfor.setFont(f1);
	       pacfor.setBounds(20,620,300,30);
	       
	       totsal=new JLabel("Total Cost : ");
	       totsal.setForeground(Color.DARK_GRAY);
	       totsal.setBounds(330,580,300,30);
	       totsal.setFont(new Font("Times New Roman",Font.BOLD,35));
	      
	      s4=new JSeparator();
	      s4.setBounds(10,665,680,10);
	      s4.setForeground(Color.BLACK);
	      
	      print=new JButton("Print");
	      print.setBounds(175,675,120,40);
	      print.setFont(new Font("Rage Italic",Font.PLAIN,35));
	      
	      home=new JButton("Home");
	      home.setBounds(400,675,120,40);
	      home.setFont(new Font("Rage Italic",Font.PLAIN,35));
	      
	      
	      // DISPLAYING THE TRANSACTIONS
	      for(int i=0;i<var.sVarName.length;i++)
	      {
	      	sno1.addItem(""+(i+1));
	      	desc1.addItem(""+var.sVarName[i]);
	      	qnt1.addItem(""+var.sVarQuantity[i]);
	      	rat1.addItem(""+var.iVarRate[i]);
	      	disc1.addItem(""+var.sVarDisc[i]);
	      	netamt1.addItem(""+var.fAmount[i]);
	      }

	      disc.setText(disc.getText()+" "+var.fDiscount+"%");
	      pacfor.setText(pacfor.getText()+" "+var.fPacking+"%");
	      disnt.setText(disnt.getText()+" "+var.fAdjustment+"%");
	      
	      temp = var.fDiscount/100;
	      temp1 = ((float)var.fPacking)/100;
	      temp2 = ((float)var.fAdjustment)/100;

	      fFinalCost = var.fTotal - (temp * var.fTotal);
	      fFinalCost = fFinalCost + (temp1 * var.fTotal);
	      fFinalCost = fFinalCost + (temp2 * var.fTotal);

	      totsal.setText(totsal.getText()+" "+(int)fFinalCost);

	      cpane.add(var.lDate);
	      cpane.add(tabs);
	      cpane.add(sno1);
	      cpane.add(desc1);
	      cpane.add(qnt1);
	      cpane.add(rat1);
	      cpane.add(disc1);
	      cpane.add(netamt1);
	      cpane.add(s3);
	      cpane.add(disnt);
	      cpane.add(disc);
	      cpane.add(pacfor);
	      cpane.add(totsal);
	      cpane.add(lTotal);
	      cpane.add(s4);

	      cpane.add(print);
	      
	      cpane.add(home);
	      cpane.add(head2);
	      cpane.add(head1);
	      cpane.add(blno);

	      cpane.add(s1);
	      cpane.add(cname);
	      cpane.add(phon);
	      cpane.add(cname1);
	      cpane.add(phon1);
	      cpane.add(addr);
	      cpane.add(addr1);
	      cpane.add(s2);

	      invo.setSize(700,750);
	      invo.setVisible(true);
	      
	      print.addActionListener(this);
	      home.addActionListener(this);
	      
	      invo.addWindowListener(new WindowClass());
	}
		
	
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource() == home)
		{
			JOptionPane.showMessageDialog(this,"<html>Record will be saved,<br>Thank you!</html>");
			saveRecord();
			for(int i=0;i<var.sVarName.length;i++)
			{
				updateDatabase(var.sVarName[i],Integer.parseInt(var.sVarQuantity[i]));
			}
			new Home();
			invo.dispose();
		}
		
		if(ae.getSource() == print)
		{
			JOptionPane.showMessageDialog(this,"Printer not connected !!");
		}
	}
	
	public void setCustomer()
	{
		// CONNECTING TO DATABASE
			try
			{
				Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
				Connection con = DriverManager.getConnection("jdbc:odbc:Auto spare","","");
				Statement st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
				ResultSet rs = st.executeQuery("select * from Customer where Name='"+var.sCustomerName+"'");
				rs.first();
				
				iCustID = rs.getInt("ID");
				cname1.setText(cname1.getText()+" "+rs.getString("Name"));
				phon1.setText(phon1.getText()+" "+rs.getInt("Phone"));
				addr1.setText(addr1.getText()+" "+rs.getString("Address"));
			}
			catch(SQLException se)
			{
				System.out.print(se);
				JOptionPane.showMessageDialog(this, "Sorry, unable to connect to database !!");
				invo.dispose();
			}
	}

	public void setCustomer(String sName,String sAdd,String sPhNo)
	{
		cname1.setText(cname1.getText()+" "+sName);
		phon1.setText(phon1.getText()+" "+sPhNo);
		addr1.setText(addr1.getText()+" "+sAdd);
	}
	
	public void saveRecord()
	{
		// SAVING THE RECORD TO DATABASE
		  try
		  {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			Connection con = DriverManager.getConnection("jdbc:odbc:Auto spare","","");
			Statement st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			ResultSet rs=st.executeQurery("select * from BillReport");
			rs.last();
			int iBillID = (1+rs.getInt("Bill ID"));
			iCustID = getID();
			int iResult = st.executeUpdate("insert into BillReport values("+iBillID+","+iCustID+","+fFinalCost+",'"+var.tDate.getText()+"')");
			if(iResult==1)
			{
				// If Query succesful
				System.out.print("pass");
			}
			else
			{
				//  Query failed
				System.out.print("fail");
			}
	   	  }
	      catch(Exception e)
	      {
	      	System.out.print(e);
	      	JOptionPane.showMessageDialog(this, "Sorry, unable to connect to database !!");
	      	invo.dispose();
	      }
	}
	
	public int getID()
	{
		try
		{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			Connection con = DriverManager.getConnection("jdbc:odbc:Auto spare","","");
			Statement st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			ResultSet rs=st.executeQuery("select * from Customer");
			rs.last();
			int iTemp = rs.getInt(1);
			return (iTemp);
	   	}
	    catch(Exception e)
	    {
	      	System.out.print(e);
	      	JOptionPane.showMessageDialog(this, "Sorry, unable to connect to database !!");
	      	invo.dispose();
	      	return 0;
	    }
	}
	
	public void updateDatabase(String sTempPart,int iTempQuantity)
	{
		try
		{
			Connection con = DriverManager.getConnection("Jdbc:Odbc:Auto spare");
			Statement st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = st.executeQuery("select * from Spare where Name='"+sTempPart+"'");

			rs.first();
			int iQuantity = rs.getInt("Quantity");
			int temp = iQuantity-iTempQuantity;
			
			int iResult = st.executeUpdate("update Spare set Quantity="+temp+" where Name='"+sTempPart+"'");
			if(iResult==1)
			{
				// If Query succesful
				//System.out.print(" : Pass : ");
			}
			else
			{
				//  Query failed
				//System.out.print(" : Fail : "+iResult);
			}
		}
		catch(SQLException e)
		{
			System.out.print(e);
			JOptionPane.showMessageDialog(this,"Unable to connect to database");
		}
	}
	
	public class WindowClass extends WindowAdapter
	{
		public void windowClosing(WindowEvent we)
		{
			int reply = JOptionPane.showConfirmDialog(null,"<html>Record will be saved<br>Are you sure you want to exit?</html>","",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE);
			if(reply == JOptionPane.YES_OPTION)
			{
				saveRecord();
				for(int i=0;i<var.sVarName.length;i++)
				{
					updateDatabase(var.sVarName[i],Integer.parseInt(var.sVarQuantity[i]));
				}
				invo.dispose();
			}
			else
			{
				invo.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			}
		}
	}
	
	public static void main(String args[])
	{
		new Bill();
	}
}