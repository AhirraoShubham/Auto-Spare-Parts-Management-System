import java.io.*;
import java.awt.*;
import java.sql.*;
import javax.swing.*;
import java.awt.event.*;

class Login extends JFrame implements ActionListener
{
	private Font f1,f2,f3;
	private JLabel lUser, lPass,lPicture;
	private JTextField tUser;
	private JPasswordField pPass;
	private JButton bOk, bCancel;
	
	private Connection con;

	private Container Picture;
	private ImageIcon iPicture;
	
	private boolean verify = false;
	
	public Login()
	{
		Variables var = new Variables();
		
		setTitle("Login");
		show();
		setSize(570,330);
		setResizable(false);
		setLocation(var.d.width/3-getWidth()/2,var.d.height/3-getHeight()/2);
		
		// INITALIZING COMPONENTS
		f1 = new Font("Rage Italic",Font.PLAIN,40);
		f2 = new Font("Rage Italic",Font.PLAIN,30);
		f3 = new Font("Courier New",Font.PLAIN,19);
		
		lUser = new JLabel("Username   :");
		lUser.setFont(f1);
		
		lPass = new JLabel("Password    :");
		lPass.setFont(f1);
		
		tUser = new JTextField(10);
		tUser.setFont(f3);
		
		pPass = new JPasswordField(10);
		pPass.setFont(f3);
		
		bOk = new JButton("Ok");
		bOk.setFont(f2);
		bCancel = new JButton("Cancel");
		bCancel.setFont(f2);
		
		Picture = getContentPane();
		getContentPane().setLayout(null);
		iPicture = new ImageIcon("C://b3//Auto Spares//Images//Login.jpg");
		lPicture = new JLabel("",iPicture,JLabel.LEFT);
		
		setLayout(null);
		
		// ADDING COMPONENTS
		Picture.add(lPicture);
		lPicture.setBounds(-5,-5,570,330);
		
		lPicture.add(lUser);				// USERNAME LABEL
		lUser.setBounds(100,120,200,40);
		lUser.setForeground(Color.WHITE);
		
		add(tUser);							// USERNAME TEXT
		tUser.setBounds(300,120,160,30);
		
		lPicture.add(lPass);				// PASSWORD LABEL
		lPass.setBounds(100,180,200,40);
		lPass.setForeground(Color.WHITE);
		
		add(pPass);							// PASSWORD TEXT
		pPass.setBounds(300,180,160,30);
		
		lPicture.add(bOk);					// BUTTON OK
		bOk.setBounds(110,240,160,35);
		bOk.setForeground(Color.BLACK);
		
		lPicture.add(bCancel);				// BUTTON CANCEL
		bCancel.setBounds(300,240,160,35);
		bCancel.setForeground(Color.BLACK);
		
		bOk.addActionListener(this);
		bCancel.addActionListener(this);
		
		try
		{
			//Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			con = DriverManager.getConnection("jdbc:odbc:Auto spare","","");
		}
		catch(SQLException se)
		{
			JOptionPane.showMessageDialog(this, "Sorry, unable to connect to database !!");
			dispose();
		}
		
		addWindowListener(new WindowClass());
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource() == bOk)
		{
			if(tUser.getText().equals(""))		// If user leaves the Username field blank
			{
				JOptionPane.showMessageDialog(this, "Please provide Username to login");
				tUser.requestFocus();
			}
			else if(pPass.getText().equals(""))	// If user leaves the Password field blank
			{
				JOptionPane.showMessageDialog(this, "Please provide Password to login");
				pPass.requestFocus();
			}
			else								// If user provides the Username & Password field
			{
				String User, Pass;
				Boolean check = true;
				User = tUser.getText();
				Pass = pPass.getText();
				verify = false;
				
				for(int i=0;i<User.length();i++)
				{
					if(Character.isDigit(User.charAt(i)))
					{
						check = false;
					}
				}
				
				if(check == true)
				{
					try
					{
						Statement st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
						ResultSet rs = st.executeQuery("select * from User where Username = '"+User+"'");
						
						if(rs.next())										// Correct usrename
						{
							if((Pass.equals(rs.getString("Password"))))		// Correct password
							{
								new Welcome();
								dispose();
								verify = true;
							}
							else											// Wrong password
							{
								JOptionPane.showMessageDialog(this, "Wrong Information !!");
								verify = false;
								pPass.setText("");
							}
						}
						else												// Wrong usrename
						{
							JOptionPane.showMessageDialog(this, "Wrong Information !!");
							verify = false;
							tUser.setText("");
							pPass.setText("");
						}
					}
					catch(SQLException se)
					{
						if(verify == false)
						{
							JOptionPane.showMessageDialog(this, "Wrong Information !!");
							tUser.setText("");
							pPass.setText("");
							tUser.requestFocus();
						}
					}
				}
				else
				{
					JOptionPane.showMessageDialog(this, "Username should not contain any digits");
				}	
			}
		}
		
		if(ae.getSource() == bCancel)
		{
			int reply = JOptionPane.showConfirmDialog(null,"Are you sure you want to quit?","",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE);
			if(reply == JOptionPane.YES_OPTION)
			{
				System.exit(0);
				dispose();
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
		Login l = new Login();
	}
}