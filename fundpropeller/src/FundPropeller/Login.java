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
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class Login extends JFrame implements ActionListener {
	JButton loginButton,fpButton,caButton,cButton;
	JTextField unTextField;
	JPasswordField pwTextField;
	static signUpInvestor SUI;
	Login(){
		this.setSize(500,300);
		this.setUndecorated(true);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setLayout(null);
		this.setVisible(true);
		this.getContentPane().setBackground(new Color(1,90,30));

		JLabel unText = new JLabel("Email");
		this.add(unText);
		unText.setBounds(10,30,105,22);
		unText.setFont(new Font("Times New Roman",Font.PLAIN,22));
		unText.setForeground(new Color(255,255,255));

		unTextField = new JTextField();
		this.add(unTextField);
		unTextField.setBounds(120,30,300,22);
		unTextField.setFont(unText.getFont());
		unTextField.setBorder(BorderFactory.createEmptyBorder());

		JLabel pwText = new JLabel("Password");
		this.add(pwText);
		pwText.setBounds(10,80,105,22);
		pwText.setFont(new Font("Times New Roman",Font.PLAIN,22));
		pwText.setForeground(new Color(255,255,255));

		pwTextField = new JPasswordField();
		this.add(pwTextField);
		pwTextField.setBounds(120,80,300,22);
		pwTextField.setFont(pwText.getFont());
		pwTextField.setBorder(BorderFactory.createEmptyBorder());

		loginButton = new JButton("sign in");
		this.setUpButton(loginButton);
		loginButton.setBounds(10,140,150,30);


		fpButton = new JButton("Forgot Password?");
		this.setUpButton(fpButton);
		fpButton.setBounds(260,140,220,30);

		JLabel naText = new JLabel("Create New Account?");
		this.add(naText);
		naText.setBounds(10,210,250,22);
		naText.setFont(new Font("Times New Roman",Font.PLAIN,22));
		naText.setForeground(new Color(255,255,255));

		caButton = new JButton("create");
		this.setUpButton(caButton);
		caButton.setBounds(10,250,150,30);

		cButton = new JButton("cancel");
		this.setUpButton(cButton);
		cButton.setBounds(330,250,150,30);


	}

	public void setUpButton(JButton button) {
		this.add(button);
		button.setFont(new Font("Times New Roman",Font.PLAIN,22));
		button.setBackground(new Color(44, 152, 178));
		button.setForeground(Color.white);
		button.addActionListener(this);
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==cButton) {
			this.dispose();
		}
		else if(e.getSource()==caButton) {
				SUI = new signUpInvestor();
				this.dispose();
		}
		else if(e.getSource()==loginButton) {
			login();
		}else if(e.getSource()==fpButton) {
				changePassword();
		}
	}

	private void login() {
		String userName = unTextField.getText();
		String passWord = pwTextField.getText();
		try {

			dbConnection c = new dbConnection();
			String role = choice.buttonID ==1?"Investor":"FundRaiser";
			String query="select * from userdetails where Email='"+userName+"'and Password='"+signUpInvestor.createText(passWord)+"' and Role = '"+role+"'";

			ResultSet rs=c.s.executeQuery(query);
			if(rs.next()) {
				JOptionPane.showMessageDialog(null,"Login Successfull");
				if(choice.buttonID==1) {
					new investorIndex(userName);

				}else if(choice.buttonID == 2) {
					new fundRaiserIndex(userName);
				}
					choice.c.close();

			}
			else {
				if(userName.equals("") || passWord.equals("")) {
					JOptionPane.showMessageDialog(null,"Please provide Email and Password!");
				}
				else {

					JOptionPane.showMessageDialog(null,"Invalid Username or Password");
				}
			}

		}catch (Exception e) {
			System.out.println("Caught an exception in login verification\n"+e);
		}
	}
	private void changePassword() {
		String email = unTextField.getText();
		if(email.equals("")) {
			JOptionPane.showMessageDialog(null, "Please enter your email first!");
		}else{
			try {
				dbConnection db = new dbConnection();
				String role = "";
				if(choice.buttonID == 1) {
					role="Investor";
				} else if(choice.buttonID==2) {
					role="FundRaiser";
				}

				String query="select * from userdetails where Email='"+email+"' and Role='"+role+"'";

				ResultSet rs=db.s.executeQuery(query);

				if(rs.next()) {
					while(true) {
						String ans = JOptionPane.showInputDialog(rs.getString("SQ"));
						if(ans == null){
							break;
						}
						else if(ans.equals(rs.getString("SA"))){
							String newPass = JOptionPane.showInputDialog("Enter new Password");
							String errorMessage = "";
							while(true) {
								if(email.equals(newPass)) {
									errorMessage+="Email and Password Should not be same\n\n";
								}
								else if(!signUpInvestor.verifyPassword(newPass)) {
									errorMessage+="Password Should have at least an Uppercase,\na lowercase,a number, a special character and should be of 8 characters\n\n";
								}else {
									break;
								}
								JOptionPane.showMessageDialog(null,errorMessage);
								newPass = JOptionPane.showInputDialog("Enter new Password");
							}

							String uquery = "update userdetails set Password='"+signUpInvestor.createText(newPass)+"' where Email = '"+email+"'";
							db.s.executeUpdate(uquery);
							JOptionPane.showMessageDialog(null, "Password Updated Successfully");
							break;
						}
						else {
							JOptionPane.showMessageDialog(null,"Wrong Answer");
						}
					}
				}
				else {
					JOptionPane.showMessageDialog(null,"Invalid Email");
				}

			}catch (Exception b) {
				System.out.println("Caught an exception while changing password\n"+b);
		}
		}
	}
}
