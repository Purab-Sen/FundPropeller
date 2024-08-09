package FundPropeller;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class signUpInvestor extends JFrame implements ActionListener{
	JButton submit,cancel;
	JTextField nameTextField,ddTextField,mmTextField,yyTextField,emailTextField,passTextField,eduTextField,sqTextField,sqAnsTextField;

	signUpInvestor(){
		this.setSize(500,600);
		this.setUndecorated(true);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setLayout(null);
		this.setVisible(true);
		this.getContentPane().setBackground(new Color(1,90,30));

		JLabel nameText = new JLabel("Name");
		this.add(nameText);
		nameText.setBounds(20,30,105,22);
		nameText.setFont(new Font("Times New Roman",Font.PLAIN,22));
		nameText.setForeground(new Color(255,255,255));

		nameTextField = new JTextField();
		this.add(nameTextField);
		nameTextField.setBounds(140,30,300,22);
		nameTextField.setFont(nameText.getFont());
		nameTextField.setBorder(BorderFactory.createEmptyBorder());

		JLabel DOBText = new JLabel("Date Of Birth:");
		this.add(DOBText);
		DOBText.setBounds(20,80,200,22);
		DOBText.setFont(new Font("Times New Roman",Font.PLAIN,22));
		DOBText.setForeground(new Color(255,255,255));

		JLabel ddText = new JLabel("Day");
		this.add(ddText);
		ddText.setBounds(20,110,40,22);
		ddText.setFont(new Font("Times New Roman",Font.PLAIN,22));
		ddText.setForeground(new Color(255,255,255));

		ddTextField = new JTextField();
		this.add(ddTextField);
		ddTextField.setBounds(60,110,22,22);
		ddTextField.setFont(nameText.getFont());
		ddTextField.setBorder(BorderFactory.createEmptyBorder());

		JLabel mmText = new JLabel("Mon");
		this.add(mmText);
		mmText.setBounds(140,110,50,22);
		mmText.setFont(new Font("Times New Roman",Font.PLAIN,22));
		mmText.setForeground(new Color(255,255,255));

		mmTextField = new JTextField();
		this.add(mmTextField);
		mmTextField.setBounds(190,110,22,22);
		mmTextField.setFont(nameText.getFont());
		mmTextField.setBorder(BorderFactory.createEmptyBorder());

		JLabel yyText = new JLabel("Year");
		this.add(yyText);
		yyText.setBounds(270,110,60,22);
		yyText.setFont(new Font("Times New Roman",Font.PLAIN,22));
		yyText.setForeground(new Color(255,255,255));

		yyTextField = new JTextField();
		this.add(yyTextField);
		yyTextField.setBounds(325,110,45,22);
		yyTextField.setFont(nameText.getFont());
		yyTextField.setBorder(BorderFactory.createEmptyBorder());

		JLabel emailText = new JLabel("Email");
		this.add(emailText);
		emailText.setBounds(20,160,105,22);
		emailText.setFont(new Font("Times New Roman",Font.PLAIN,22));
		emailText.setForeground(new Color(255,255,255));

		emailTextField = new JTextField();
		this.add(emailTextField);
		emailTextField.setBounds(140,160,300,22);
		emailTextField.setFont(nameText.getFont());
		emailTextField.setBorder(BorderFactory.createEmptyBorder());

		JLabel passText = new JLabel("SetPassword");
		this.add(passText);
		passText.setBounds(20,210,120,22);
		passText.setFont(new Font("Times New Roman",Font.PLAIN,22));
		passText.setForeground(new Color(255,255,255));

		passTextField = new JTextField();
		this.add(passTextField);
		passTextField.setBounds(140,210,300,22);
		passTextField.setFont(nameText.getFont());
		passTextField.setBorder(BorderFactory.createEmptyBorder());

		JLabel eduText = new JLabel("Education");
		this.add(eduText);
		eduText.setBounds(20,260,120,22);
		eduText.setFont(new Font("Times New Roman",Font.PLAIN,22));
		eduText.setForeground(new Color(255,255,255));

		eduTextField = new JTextField();
		this.add(eduTextField);
		eduTextField.setBounds(140,260,300,22);
		eduTextField.setFont(nameText.getFont());
		eduTextField.setBorder(BorderFactory.createEmptyBorder());

		JLabel sqText = new JLabel("Enter Your Security Question");
		this.add(sqText);
		sqText.setBounds(20,310,400,22);
		sqText.setFont(new Font("Times New Roman",Font.PLAIN,22));
		sqText.setForeground(new Color(255,255,255));

		sqTextField = new JTextField();
		this.add(sqTextField);
		sqTextField.setBounds(20,350,420,22);
		sqTextField.setFont(nameText.getFont());
		sqTextField.setBorder(BorderFactory.createEmptyBorder());

		JLabel sqAnsText = new JLabel("Enter Your Answer");
		this.add(sqAnsText);
		sqAnsText.setBounds(20,400,400,22);
		sqAnsText.setFont(new Font("Times New Roman",Font.PLAIN,22));
		sqAnsText.setForeground(new Color(255,255,255));

		sqAnsTextField = new JTextField();
		this.add(sqAnsTextField);
		sqAnsTextField.setBounds(20,440,420,22);
		sqAnsTextField.setFont(nameText.getFont());
		sqAnsTextField.setBorder(BorderFactory.createEmptyBorder());


		submit = new JButton("submit");
		this.add(submit);
		submit.setBounds(20,530,105,30);
		submit.setFont(new Font("Times New Roman",Font.PLAIN,22));
		submit.setBackground(new Color(44, 152, 178));
		submit.setForeground(Color.white);
		submit.addActionListener(this);

		cancel = new JButton("cancel");
		this.add(cancel);
		cancel.setBounds(335,530,105,30);
		cancel.setFont(new Font("Times New Roman",Font.PLAIN,22));
		cancel.setBackground(new Color(44, 152, 178));
		cancel.setForeground(Color.white);
		cancel.addActionListener(this);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==cancel) {
			this.dispose();
		}else if(e.getSource()==submit){
			boolean verify = true;
			String errorMessage = "";
			String name = nameTextField.getText();
			for(int i=0;i<name.length();i++) {
				if(!((name.charAt(i)>='A' && name.charAt(i)<='Z')||(name.charAt(i)>='a' && name.charAt(i)<='z')||name.charAt(i)==' ')) {
					errorMessage+="Name can only have Alphabets\n\n";
					verify = false;
					break;
				}
			}
			String year = yyTextField.getText();
			String month = mmTextField.getText();
			String day = ddTextField.getText();
			if(!verifyDate(year,month,day)) {
				errorMessage += "Invalid Date\nPlease write correct date with this Format: DD MM YYYY\n\n";
				verify = false;
			}
			String question = sqTextField.getText();
			String ans = sqAnsTextField.getText();

			String email = emailTextField.getText();
			if(!(verifyEmail(email).equals(""))) {
				errorMessage += verifyEmail(email);
				verify = false;
			}
			String password = passTextField.getText();
			if(!verifyPassword(password)) {
				errorMessage+="Password Should have at least an Uppercase,\na lowercase,a number, a special character and should be of 8 characters\n\n";
				verify = false;
			}
			if(password.equals(email)) {
				errorMessage+="Password and Email should not match!";
				verify = false;
			}
			String education = eduTextField.getText();

			if(verify) {
				String DOB = day+"-"+month+"-"+year;
				String role="";
				if(choice.buttonID == 1) {
					role = "Investor";
				} else if(choice.buttonID == 2) {
					role = "FundRaiser";
				}
				try {


					dbConnection db =new dbConnection();
					String query="insert into userdetails values('"+name+"','"+DOB+"','"+email+"','"+createText(password)+"','"+createText(question)+"','"+createText(ans)+"','"+createText(education)+"','"+role+"')";
					db.s.executeUpdate(query);
					query = "insert into profiledata(Name,DOB,Education,Email,Role,Photo) values('"+name+"','"+DOB+"','"+createText(education)+"','"+email+"','"+role+"','"+myImage.imageToByteString("src/generalProfilelogo.jpg")+"')";
					db.s.executeUpdate(query);
					JOptionPane.showMessageDialog(null, "Registration Successful");
					dispose();
					if(choice.buttonID == 1) {
						choice.loginAsIn.doClick();
					} else if(choice.buttonID == 2) {
						choice.loginAsR.doClick();
					}
				} catch(Exception b) {
					System.out.println("Caught an exception in while inserting data of registration page\n"+b);
				}

			}else {
				JOptionPane.showMessageDialog(null, errorMessage);
			}
		}

	}
	private String verifyEmail(String email) {

	    try {
			dbConnection db =new dbConnection();
			String query="select Email from userdetails where Email='"+email+"'";

			ResultSet rs=db.s.executeQuery(query);
			if(rs.next()) {
				return "Email Already Exist\n\n";
			}
		} catch(Exception b) {
			System.out.println("Caught an exception in while searching given mail of registration page\n"+b);
		}


	    if(Email.isValidEmail(email)) {
	    	return "";
	    } else {
			return "Invalid Email\n\n";
		}


	}
	static boolean verifyPassword(String password) {
		//lowercase check
		boolean bool1 = false;boolean bool2 = false;boolean bool3=false;boolean bool4=false;
		for(int i=0;i<password.length();i++) {
			//lowercase check
			if((password.charAt(i)>='a' && password.charAt(i)<='z')) {
				bool1 = true;
			} else if((password.charAt(i)>='A' && password.charAt(i)<='Z')) {
				bool2 = true;
			} else if(password.charAt(i)>=32 && password.charAt(i)<=47) {
				bool3 = true;
			} else if(password.charAt(i)>=58 && password.charAt(i)<=64) {
				bool3 = true;
			} else if(password.charAt(i)>=91 && password.charAt(i)<=96) {
				bool3 = true;
			} else if(password.charAt(i)>=123 && password.charAt(i)<=126) {
				bool3 = true;
			} else if(password.charAt(i)>=48 && password.charAt(i)<=57) {
				bool4 = true;
			}
			if(bool1 && bool2 && bool3 && bool4 && password.length() >=8) {
				return true;
			}
		}
		return false;
	}
	public static boolean verifyDate(String year,String month,String day) {
		if(!(year.length() == 4 && month.length() == 2 && day.length()== 2)) {
			return false;
		}
		for(int i = 0;i<year.length();i++) {
			if(year.charAt(i)>='0' && year.charAt(i)<='9') {} else {
				return false;
			}
		}
		if(year.startsWith("19") || year.startsWith("20")) {
		    if (month.charAt(0) == '0' || month.charAt(0) == '1') {
		        if (month.charAt(1) >= '0' && month.charAt(1) <= '9') {
		            if (day.charAt(1) >= '0' && day.charAt(1) <= '9') {
		                if (day.charAt(0) >= '0' && day.charAt(0) <= '3') {
		                } else {
							return false;
						}
		            } else {
						return false;
					}
		        } else {
					return false;
				}
		    } else {
				return false;
			}
		} else {
			return false;
		}

		int yr = Integer.parseInt(year);
		int mon = Integer.parseInt(month);
		int dy = Integer.parseInt(day);

		if(mon == 0 || dy == 0 || (yr>=2015)) {
			return false;
		}
		if((mon%2 != 0 && mon<8) || (mon%2 == 0 && mon>=8 && mon<=12)) {
			if(!(dy>=1 && dy<=31)) {
				return false;
			}
		}else if(((mon == 4 || mon == 6)||(mon>7 && mon%2 == 0))) {
			if(!(dy>=1 && dy<=30)) {
				return false;
			}
		}else if(mon == 2) {
			if(isLeapYear(yr)) {
				if(!(dy>=1 && dy<=29)) {
					return false;
				}
			}else {
				if(!(dy>=1 && dy<=28)) {
					return false;
				}
			}
		}
		return true;
	}
	public static boolean isLeapYear(int year) {

        if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
			return true;
		} else {
			return false;
		}
    }
	public static String createText(String str) {
		String[] strArray = str.split("'");
		String newStr="";
		for(int i=0;i<strArray.length;i++) {
			if(i<=strArray.length-2) {
				newStr+=strArray[i]+"\\'";
			} else {
				newStr+=strArray[i];
			}
		}
		return newStr;
	}
}
