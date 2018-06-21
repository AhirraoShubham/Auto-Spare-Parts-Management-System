import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Variables extends JFrame
{
	public Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
	
	public Date date = new Date();
	public GregorianCalendar calendar=new GregorianCalendar();
	public static String StrDate,sCustomerName;
	public JLabel lDate;
	public JTextField tDate;
	
	public static float fAdjustment=5,fPacking=3;
	public static float fDiscount=0,fTotal=0,fTotalDiscount=0;
	public static String[] sVarPartNo={"7","10","20"};
	public static String[] sVarName={"Fan","Clutch","Battery"};
	public static String[] sVarQuantity={"1","1","1"};
	public static String[] sVarDisc={"10","5","2"};
	public static Integer[] iVarRate={220,50,600};
	
	public static float[] fAmount={220,50,600};
	public JMenuBar mb;
	public JMenu mGoTo,mAction;
	public JMenuItem miQuotInv,miOrder,miSpare,miStaff,miCustomer,miReports,miUser,miDistributor;
	public JMenuItem miLogoff,miExit;
	
	public Variables()
	{
		// DATE
		lDate = new JLabel("Date :");
		tDate = new JTextField(10);
		StrDate=calendar.get(Calendar.DATE)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.YEAR);
		tDate.setText(""+StrDate);
		tDate.setEditable(false);
		lDate.setFont(new Font("Courier New",Font.PLAIN,15));
		tDate.setFont(new Font("Courier New",Font.PLAIN,15));
		
		// MENU BAR
		mb = new JMenuBar();
		
		mGoTo = new JMenu("Go To");
		mAction = new JMenu("Action");
		
		miQuotInv = new JMenuItem("Quotation and Invoice");
		miOrder = new JMenuItem("Order");
		miSpare = new JMenuItem("Spare");
		miStaff = new JMenuItem("Staff");
		miCustomer = new JMenuItem("Customer");
		miReports = new JMenuItem("Reports");
		miUser = new JMenuItem("User");
		miDistributor = new JMenuItem("Distributor");
		
		miLogoff = new JMenuItem("Log off");
		miExit = new JMenuItem("Exit");
		
		mb.add(mGoTo);	mb.add(mAction);
		
		mGoTo.add(miQuotInv);
		mGoTo.add(miOrder);
		mGoTo.addSeparator();
		mGoTo.add(miSpare);
		mGoTo.add(miStaff);
		mGoTo.add(miCustomer);
		mGoTo.add(miReports);
		mGoTo.add(miUser);
		mGoTo.add(miDistributor);
		
		mAction.add(miLogoff);
		mAction.add(miExit);
	}
}