package FundPropeller;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.LineBorder;

class newProject extends JPanel implements ActionListener{
	String name;
	String ID;
	int requiredFund;
	int equity;
	int currentFund;
	String status;
	String details;
	JButton investButton,detailButton,viewProfileBtn;
	newProject(JPanel parent,String ID,String name,int requiredFund,int equity,int currentFund,String status,String details){
		this.ID=ID;
		this.requiredFund=requiredFund;
		this.currentFund=currentFund;
		this.equity=equity;
		this.status=status;
		this.details=details;
		this.setPreferredSize(new Dimension(1200,120));
		this.setBorder(new LineBorder(Color.black,2));
		this.setLayout(new FlowLayout(FlowLayout.LEFT,30,10));


		parent.add(this);

		JLabel pnameLabel = new JLabel("Name:"+name);
		pnameLabel.setPreferredSize(new Dimension(300,30));
		this.add(pnameLabel);
		pnameLabel.setFont(new Font("Times New Roman", Font.PLAIN, 28));

		int temp = requiredFund>=currentFund?requiredFund:currentFund;

		JLabel rFundLabel = new JLabel("Ask: Rs."+temp);
		rFundLabel.setPreferredSize(new Dimension(250,30));
		this.add(rFundLabel);
		rFundLabel.setFont(new Font("Times New Roman", Font.PLAIN, 28));

		JLabel equityLabel = new JLabel("Equity:"+equity+"%");
		equityLabel.setPreferredSize(new Dimension(150,30));
		this.add(equityLabel);
		equityLabel.setFont(new Font("Times New Roman", Font.PLAIN, 28));

		detailButton = new JButton("Project Details");
		this.add(detailButton);
		detailButton.setFont(new Font("Times New Roman", Font.PLAIN, 28));
		detailButton.addActionListener(this);

		investButton = new JButton("invest");
		investButton.setPreferredSize(new Dimension(130,35));
		this.add(investButton);
		investButton.setFont(new Font("Times New Roman", Font.PLAIN, 28));
		investButton.setBackground(Color.green);
		investButton.addActionListener(this);

		viewProfileBtn = new JButton("View Profile");
		viewProfileBtn.setPreferredSize(new Dimension(200,35));
		this.add(viewProfileBtn);
		viewProfileBtn.setFont(new Font("Times New Roman", Font.PLAIN, 28));
		viewProfileBtn.addActionListener(this);

	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == investButton) {
			String temp = JOptionPane.showInputDialog("Enter the amount you want to invest:");
			if(temp!=null) {
			try {
				int amount = Integer.parseInt(temp);
				dbConnection db =new dbConnection();
				String query="insert into investorproject values('"+investorIndex.email+"','"+ID+"','"+amount+"','Pending')";
				db.s.executeUpdate(query);
				JOptionPane.showMessageDialog(null, "Investment Successful!\nAdded to 'MyInvestments'");
				updateCurrentFund(amount);
				investorIndex.miBtn.doClick();
			} catch(Exception b) {
				JOptionPane.showMessageDialog(null, "Invalid Amount!");
			}
			} else {
				JOptionPane.showMessageDialog(null, "Investment Amount can't be Empty!");
			}
		}else if(e.getSource() == detailButton) {
			addDetailSection();
		}else if(e.getSource() == viewProfileBtn) {
				viewProfileDetails(fundRaiserIndex.npBtn);
		}
	}
	protected void viewProfileDetails(JButton btn) {
		try {
			dbConnection db = new dbConnection();

			String query="select profiledata.* from profiledata join fundraiserproject on profiledata.Email = fundraiserproject.fundRaiserID where fundraiserproject.projectID = '"+this.ID+"'";
			ResultSet rs=db.s.executeQuery(query);
			while(rs.next()) {
				String name = rs.getString("Name");
				String DOB = rs.getString("DOB");
				String Education = rs.getString("Education");
				String university = rs.getString("University");
				String Address = rs.getString("Address");
				String imageBytecode = rs.getString("photo");
				String contact = rs.getString("contact");
				profile.loadProfile(name,DOB,Education,imageBytecode,university,Address,contact,"FundRaiser",btn);
			}


		}catch (Exception b) {
			System.out.println("Caught an exception in getting profiledata of fundRaiser in investorIndex page\n"+b);
	}
	}
	protected void addDetailSection() {
		JPanel textPanel = new JPanel();
		investorIndex.ref.add(textPanel);
		textPanel.setBounds(investorIndex.ref.getWidth()/2-100,investorIndex.ref.getHeight()/4,400,430);
		textPanel.setBackground(Color.gray);

		textPanel.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
		JPanel textAreaTitlePanel = new JPanel();
		textAreaTitlePanel.setPreferredSize(new Dimension(400,50));
		textAreaTitlePanel.setBackground(Color.GREEN);
		textAreaTitlePanel.setLayout(null);

		JLabel textAreaTitle = new JLabel(" Project Details ");
		textAreaTitlePanel.add(textAreaTitle);
		textAreaTitle.setBounds(0,0,400,50);
		textAreaTitle.setFont(new Font("Times New Roman", Font.PLAIN, 28));

		textPanel.add(textAreaTitlePanel);
        JTextArea textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setText(this.details);
        textArea.setFont(textAreaTitle.getFont());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(400,350));
        textPanel.add(scrollPane);

        JButton closeBtn = new JButton("close");
        textPanel.add(closeBtn);
        closeBtn.setFont(new Font("Times New Roman", Font.PLAIN, 28));
        closeBtn.setPreferredSize(new Dimension(textPanel.getWidth(),30));
        closeBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				investorIndex.ref.remove(textPanel);
				investorIndex.ref.repaint();
			}

        });
        investorIndex.ref.revalidate();

	}
	protected void updateCurrentFund(int amount) {
		try {
			dbConnection db = new dbConnection();
			String query="SELECT myFunding from investorProject where projectID = '"+ID+"'";

			ResultSet rs=db.s.executeQuery(query);
			while(rs.next()) {
				int temp = Integer.parseInt(rs.getString("myFunding"));
				if(amount<temp) {
					amount = temp;
				}
			}
			query = "update projectdetails set currentFund = '"+amount+"' where ID = '"+ID+"'";
			db.s.executeUpdate(query);

		}catch (Exception b) {
			System.out.println("Caught an exception in updating currentFund in updateCurrentFund Function\n"+b);
		}
	}
}

class myProject extends newProject{
	int myFunding;
	JButton cancelButton;
	myProject(JPanel parent,String ID,String name,int requiredFund,int equity,int currentFund,String status,int myFunding,String details){
		super(parent,ID,name,requiredFund,equity,currentFund,status,details);
		this.myFunding = myFunding;
		investButton.setText("Raise");

		this.setPreferredSize(new Dimension(1200,150));

		JLabel myFundingLabel = new JLabel("MyFunding: Rs."+myFunding);
		myFundingLabel.setPreferredSize(new Dimension(530,30));
		this.add(myFundingLabel);
		myFundingLabel.setFont(new Font("Times New Roman", Font.PLAIN, 28));

		JLabel statusLabel = new JLabel("Status: "+status);
	    statusLabel.setPreferredSize(new Dimension(200,30));
		this.add(statusLabel);
		statusLabel.setFont(new Font("Times New Roman", Font.PLAIN, 28));

		cancelButton = new JButton("cancel");
		cancelButton.setPreferredSize(new Dimension(130,35));
		if(status.equals("Accepted")) {
			cancelButton.setVisible(false);
			investButton.setVisible(false);
		}
		this.add(cancelButton);
		cancelButton.setFont(new Font("Times New Roman", Font.PLAIN, 28));
		cancelButton.setBackground(Color.RED);
		cancelButton.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()== cancelButton) {
			int result = JOptionPane.showConfirmDialog(
	                null,
	                "Do you want to remove this project?",
	                "Confirmation",
	                JOptionPane.YES_NO_OPTION
	        );

	        if (result == JOptionPane.YES_OPTION) {
	        	try {
					dbConnection db =new dbConnection();
					String query="Delete from investorproject where projectID = '"+ID+"' and investorID = '"+investorIndex.email+"'";
					db.s.execute(query);
					JOptionPane.showMessageDialog(null, "Removed Successfully!\nAdded to 'NewInvestments'");
					updateCurrentFund(0);
					investorIndex.miBtn.doClick();
				} catch(Exception b) {
					System.out.println("Caught an exception while removing investorInvested Project\n"+b);
				}
	        }
		}
		else if(e.getSource()==investButton) {
			String temp = JOptionPane.showInputDialog("Enter the new amount you want to invest:");
			if(temp!=null) {
			try {
				int amount = Integer.parseInt(temp);
				if(amount>myFunding) {
					try {
						dbConnection db =new dbConnection();
						String query="update investorproject set myFunding='"+amount+"' where projectID = '"+ID+"' and investorID = '"+investorIndex.email+"'";
						db.s.executeUpdate(query);
						JOptionPane.showMessageDialog(null, "Amount raised Successful!");
						updateCurrentFund(amount);
						investorIndex.miBtn.doClick();
					} catch(Exception b) {
						System.out.println("Caught an exception while rasing amount.\n"+b);
					}
				}else {
					JOptionPane.showMessageDialog(null, "Sorry! New Investment should be greater than your Previous Investment");
				}
			}catch(Exception a) {
				JOptionPane.showMessageDialog(null, "Invalid Input!");
			}
		}
		}else if(e.getSource()==detailButton) {
			super.addDetailSection();
		}else if(e.getSource() == viewProfileBtn) {
			if(status.equals("Accepted")) {
				viewProfileDetails(fundRaiserIndex.libBtn);
			} else if(status.equals("Pending")) {
				viewProfileDetails(fundRaiserIndex.miBtn);
			}
		}
	}

}

public class investorIndex extends JFrame implements ActionListener{
	static JButton optionBtn,editButton,saveButton,pfButton,npBtn,miBtn,loBtn,libBtn;
	static JPanel optionPanel,profileArea,commonArea,myInvestmentArea,newProjectArea,titlePanel,myLibraryArea,genericProfileArea;
	JLabel imageLabel,nameLabel,DOBLabel,eduLabel,uniLabel,addressLabel,roleLabel,contactLabel;
	Timer t1;
	String Name,DOB,education,SU,Role,contact,Address,byteString;
	static String email;
	static investorIndex ref;
	boolean activeML = false;
	boolean button = false;
	investorIndex(String email) throws IOException{
		investorIndex.email=email;
		this.setUndecorated(true);
		this.setLayout(null);
		this.setExtendedState(Frame.MAXIMIZED_BOTH);
		this.setVisible(true);
		ref = this;
        JPanel titleBar = new JPanel();
		titleBar.setLayout(new BorderLayout());
		titleBar.setBackground(new Color(44, 152, 178));
		titleBar.setBounds(0,0,this.getWidth(),60);

		JLabel title = new JLabel("   FundPropeller");
		titleBar.add(title,BorderLayout.WEST);
		title.setFont(new Font("Times New Roman", Font.BOLD, 30));
		title.setForeground(new Color(255,255,255));

		JLabel cross = new JLabel("x  ");
		cross.setFont(new Font("Arial", Font.PLAIN, 32));
		cross.setForeground(Color.WHITE);

		titleBar.add(cross,BorderLayout.EAST);

		this.add(titleBar);
		cross.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(t1 != null) {
                	t1.stop();
                }
                dispose();
            }
        });
		this.getContentPane().setBackground(new Color(144,255,144));

		optionBtn = new JButton("|||");
		optionBtn.setLayout(null);
		this.add(optionBtn);
		optionBtn.setBounds(0,60,50,30);
		optionBtn.setBackground(new Color(44, 152, 178));
		optionBtn.setForeground(Color.white);
		optionBtn.addActionListener(this);

		optionPanel = new JPanel();
		this.add(optionPanel);
		optionPanel.setBounds(-310,90,this.getWidth()/5,450);
		optionPanel.setLayout(new FlowLayout(FlowLayout.CENTER,0,1));

		pfButton = new JButton("My Profile        ");
		pfButton.setBorder(null);
		pfButton.setPreferredSize(new Dimension(optionPanel.getWidth(),40));
		optionPanel.add(pfButton);
		pfButton.setFont(new Font("Times New Roman",Font.BOLD,22));
		pfButton.setBackground(new Color(50, 50, 50));
		pfButton.setForeground(Color.white);
		pfButton.addActionListener(this);

		npBtn = new JButton("New Projects    ");
		optionPanel.add(npBtn);
		npBtn.setBorder(null);
		npBtn.setPreferredSize(new Dimension(optionPanel.getWidth(),40));
		npBtn.setFont(new Font("Times New Roman",Font.BOLD,22));
		npBtn.setBackground(new Color(50, 50, 50));
		npBtn.setForeground(Color.white);
		npBtn.addActionListener(this);

		miBtn = new JButton("My Investment ");
		optionPanel.add(miBtn);
		miBtn.setBorder(null);
		miBtn.setPreferredSize(new Dimension(optionPanel.getWidth(),40));
		miBtn.setFont(new Font("Times New Roman",Font.BOLD,22));
		miBtn.setBackground(new Color(50, 50, 50));
		miBtn.setForeground(Color.white);
		miBtn.addActionListener(this);

		libBtn = new JButton("My Library      ");
		optionPanel.add(libBtn);
		libBtn.setBorder(null);
		libBtn.setPreferredSize(new Dimension(optionPanel.getWidth(),40));
		libBtn.setFont(new Font("Times New Roman",Font.BOLD,22));
		libBtn.setBackground(new Color(50, 50, 50));
		libBtn.setForeground(Color.white);
		libBtn.addActionListener(this);

		JLabel fillgap = new JLabel();
		fillgap.setPreferredSize(new Dimension(optionPanel.getWidth(),200));
		optionPanel.add(fillgap);


		loBtn = new JButton("Log Out           ");
		optionPanel.add(loBtn);
		loBtn.setBorder(null);
		loBtn.setPreferredSize(new Dimension(optionPanel.getWidth(),40));
		loBtn.setFont(new Font("Times New Roman",Font.BOLD,22));
		loBtn.setBackground(new Color(50, 50, 50));
		loBtn.setForeground(Color.white);
		loBtn.addActionListener(this);

		setupProfileData(email);

		commonArea = new JPanel();
		this.add(commonArea);
		commonArea.setLayout(null);
		commonArea.setBounds(50,90,this.getWidth(),this.getHeight());

		miBtn.doClick();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==optionBtn) {
			if(optionPanel.getX() < 0) {
				pullout();
			}else {
				pushin();
			}
		}else if(e.getSource() == editButton) {
			editButton.setEnabled(false);
			editButton.setVisible(false);
			JOptionPane.showMessageDialog(null, "Click on the element You want to edit.");
			saveButton.setVisible(true);
			saveButton.setEnabled(true);
			editProfile();
		}
		else if(e.getSource()==saveButton) {
			saveButton.setEnabled(false);
			try {
				updateProfile();
			} catch (IOException e1) {
				System.out.println("Caught an exception in updateProfile function\n"+e1);
			}
		}else if(e.getSource()==pfButton) {
			try {
				callProfileArea();
			} catch (IOException e1) {
				System.out.println("Caught an exception in callProfileArea function\n"+e1);
			}
		}else if(e.getSource()==npBtn) {
			callNewProjectArea();
		}else if(e.getSource()==miBtn) {
			callMyInvestmentArea();
		}else if(e.getSource()==libBtn) {
			callMyLibraryArea();
		}else if(e.getSource()==loBtn) {
			int result = JOptionPane.showConfirmDialog(
	                null,
	                "Do you want to proceed?",
	                "Confirmation",
	                JOptionPane.YES_NO_OPTION
	        );

	        if (result == JOptionPane.YES_OPTION) {
	            new choice();
	            SwingUtilities.invokeLater(()->{
	            	dispose();
	            	JOptionPane.showMessageDialog(null,"Logged Out Successfully");
	            });
	        }
		}
	}


	protected void pullout() {
		t1 = new Timer(1,new ActionListener() {
			int i = optionPanel.getX();
			@Override
			public void actionPerformed(ActionEvent e) {
				if(i == 0) {
					t1.stop();
				}else {
					optionPanel.setBounds(i+=10,90,getWidth()/5,getHeight()/2);
				}
			}
		});
		t1.start();
	}
	protected void pushin() {
		t1 = new Timer(1,new ActionListener() {
			int i = optionPanel.getX();
			@Override
			public void actionPerformed(ActionEvent e) {
				if(i == -310) {
					t1.stop();
				}else {
					optionPanel.setBounds(i-=10,90,getWidth()/5,getHeight()/2);
				}
			}
		});
		t1.start();
	}
	protected void setupPhoto() throws IOException {
		JFileChooser chooseImage = new JFileChooser();
		int result = chooseImage.showOpenDialog(this);
		if(result == 0) {
			String name = chooseImage.getSelectedFile().getName();
			String extension=getFileExtension(name);
			if(!(extension.equals("jpeg")||extension.equals("jpg")||extension.equals("png"))) {
				throw new IOException();
			} else {
			String temp = chooseImage.getSelectedFile().getAbsolutePath();
			temp = temp.replace('\\', '/');
			byteString = myImage.imageToByteString(temp);
			Image originalImage = myImage.byteStringToImage(byteString);
			Image resizedImage = originalImage.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
	        // Create a new ImageIcon with the resized image
	        ImageIcon resizedIcon = new ImageIcon(resizedImage);
	        imageLabel.setIcon(resizedIcon);
	        profileArea.add(imageLabel);
	        imageLabel.setBounds(10,10,300, 300);
			}
		}

	}
	private static String getFileExtension(String filename) {
		int index = filename.lastIndexOf(".");
		if(index>0 && index<filename.length()) {
			return filename.substring(index+1).toLowerCase();
		}
		return "";
	}
	protected void setupProfileData(String email) {
		try {
			dbConnection db = new dbConnection();
			String query="select * from userdetails where Email='"+email+"'";

			ResultSet rs1=db.s.executeQuery(query);
			if(rs1.next()) {
				Name = rs1.getString("Name");
				DOB = rs1.getString("DOB");
				education = rs1.getString("education");
				Role = rs1.getString("role");
				try {
					String query2="select * from profiledata where Email='"+email+"'";
					ResultSet rs2=db.s.executeQuery(query2);
					if(rs2.next()) {
						SU = rs2.getString("University");
						contact = rs2.getString("contact");
						Address = rs2.getString("Address");
						byteString = rs2.getString("photo");
					}
				}catch(Exception e) {
					System.out.println("Caught an exception in setupProfileData function(profiledata table)\n"+e);
				}
			}
		}catch(Exception e) {
			System.out.println("Caught an exception in setupProfileData function(userdetails table)\n"+e);
		}


	}
	protected void editProfile() {
		activeML = true;
		imageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	if(activeML) {
					try {
						setupPhoto();
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(null,"PLease select image file!");
					}
            	}
            }
        });
		nameLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	if(activeML) {
            		boolean verify = true;
            		String temp = JOptionPane.showInputDialog("Enter Your Name:");
            		if(!(temp == null || temp.equals(""))) {
            			for(int i=0;i<temp.length();i++) {
            				if(!((temp.charAt(i)>='A' && temp.charAt(i)<='Z')||(temp.charAt(i)>='a' && temp.charAt(i)<='z')||temp.charAt(i)==' ')) {
            					verify = false;
            					JOptionPane.showMessageDialog(null,"Invalid Name");
            					break;
            				}
            			}
            			if(verify) {
							Name = temp;
						}
            		}
            	}
            }
        });
		DOBLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	if(activeML) {
            		String temp = JOptionPane.showInputDialog("Enter Your Date Of Birth(DD-MM-YYYY):");
            		if(!(temp == null || temp.equals(""))) {
            			try {if(temp.length()!=10) {
							throw new Exception();
						}
            				String parts[]=temp.split("-");
                			if((parts.length != 3) || !(signUpInvestor.verifyDate(parts[2], parts[1], parts[0]))) {
                				throw new Exception();
                			}
	            			DOB = temp;
            			}catch(Exception b) {
            				JOptionPane.showMessageDialog(null,"Invalid Date!!");
            			}

            		}

            	}
            }
        });
		addressLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	if(activeML) {
            		String temp = JOptionPane.showInputDialog("Enter Your Address:");
            		if(!(temp == null || temp.equals(""))) {
						Address = temp;
					}

            	}
            }
        });
		eduLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	if(activeML) {

            		String temp = JOptionPane.showInputDialog("Enter Your Education:");
            		if(!(temp == null || temp.equals(""))) {
						education = temp;
					}
            	}
            }
        });
		uniLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	if(activeML) {

            		String temp = JOptionPane.showInputDialog("Enter Your School/University:");
            		if(!(temp == null || temp.equals(""))) {
						SU = temp;
					}
            	}
            }
        });
		contactLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	if(activeML) {
            		boolean verify = false;
            		String temp = JOptionPane.showInputDialog("Enter your Phone Number or contact Email:");
            		if(!(temp == null || temp.equals(""))) {
            			int i;
            			if(temp.length()==10) {
            				for(i=0;i<temp.length();i++) {
            					if(!(temp.charAt(i)>='0'&&temp.charAt(i)<='9')) {
									break;
								}
                			}
            				if(i == 10) {
            					verify = true;
            				}
            			}
            			if(!verify) {

            			    if(Email.isValidEmail(temp)) {
            			    	verify = true;
            			    }
            			}
            			if(!verify) {
            				JOptionPane.showMessageDialog(null,"Invalid Email or Phone number!!");
            			}else {
            				contact = temp;
            			}
            		}
            	}
            }
        });
	}
	protected void updateProfile() throws IOException {
		activeML = false;
		try {
			dbConnection db = new dbConnection();

			//updating profiledata table
			String query = "update profiledata set Name='"+Name+"', DOB='"+DOB+"', Education='"+signUpInvestor.createText(education)+"',University='"+signUpInvestor.createText(SU)+"',Address = '"+signUpInvestor.createText(Address)+"', photo='"+byteString+"', contact='"+contact+"'  where Email = '"+email+"'";
			db.s.executeUpdate(query);

			//updating  userdetails table
			query = "update userdetails set Name='"+Name+"',Education='"+signUpInvestor.createText(education)+"',DOB = '"+DOB+"' where Email = '"+email+"'";
			db.s.executeUpdate(query);
			saveButton.setVisible(false);
			callProfileArea();
			JOptionPane.showMessageDialog(null,"Profile Updated Successfully");
		}catch(Exception e) {
			System.out.println("Could not update profile\n"+e);
		}

	}
	protected void callProfileArea() throws IOException {
		removeExisting();
		profileArea = new JPanel();
		commonArea.add(profileArea);
		profileArea.setLayout(null);
		profileArea.setBounds(0,60,commonArea.getWidth(),commonArea.getHeight()-60);
		setTitle(commonArea,"My Profile");
        Image originalImage = myImage.byteStringToImage(byteString);
        Image resizedImage = originalImage.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        // Create a new ImageIcon with the resized image
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        imageLabel = new JLabel(resizedIcon);
        imageLabel.setBorder(new LineBorder(Color.black,2));
        profileArea.add(imageLabel);
        imageLabel.setBounds(10,10,300, 300);

        nameLabel = new JLabel("Name: "+Name);
        profileArea.add(nameLabel);
        nameLabel.setBounds(10,320,1000,30);
        nameLabel.setFont(new Font("Times New Roman", Font.PLAIN, 28));

        DOBLabel = new JLabel("DOB: "+DOB);
        profileArea.add(DOBLabel);
        DOBLabel.setBounds(10,370,1000,30);
        DOBLabel.setFont(new Font("Times New Roman", Font.PLAIN, 28));

        eduLabel = new JLabel("Education: "+education);
        profileArea.add(eduLabel);
        eduLabel.setBounds(10,420,1000,30);
        eduLabel.setFont(new Font("Times New Roman", Font.PLAIN, 28));

        uniLabel = new JLabel("School/University: "+SU);
        profileArea.add(uniLabel);
        uniLabel.setBounds(10,470,1000,30);
        uniLabel.setFont(new Font("Times New Roman", Font.PLAIN, 28));

        addressLabel = new JLabel("Address: "+Address);
        profileArea.add(addressLabel);
        addressLabel.setBounds(10,520,1000,30);
        addressLabel.setFont(new Font("Times New Roman", Font.PLAIN, 28));

        roleLabel = new JLabel("Role: "+Role);
        profileArea.add(roleLabel);
        roleLabel.setBounds(10,570,1000,30);
        roleLabel.setFont(new Font("Times New Roman", Font.PLAIN, 28));

        contactLabel = new JLabel("Contact: "+contact);
        profileArea.add(contactLabel);
        contactLabel.setBounds(10,620,1000,30);
        contactLabel.setFont(new Font("Times New Roman", Font.PLAIN, 28));

        editButton = new JButton("Edit Profile");
        profileArea.add(editButton);
        editButton.setBounds(1000,10,100,30);
        editButton.addActionListener(this);

        saveButton = new JButton("Save");
        profileArea.add(saveButton);
        saveButton.setBounds(1000,10,100,30);
        saveButton.addActionListener(this);
        saveButton.setEnabled(false);
        saveButton.setVisible(false);
	}
	private void callMyInvestmentArea() {
		removeExisting();
		myInvestmentArea = new JPanel();
		commonArea.add(myInvestmentArea);
		myInvestmentArea.setLayout(new FlowLayout(FlowLayout.LEFT,0,5) );
		myInvestmentArea.setBounds(0,60,commonArea.getWidth(),commonArea.getHeight()-60);

		setTitle(commonArea,"My Investments");
		try {
			dbConnection db = new dbConnection();
			String query="SELECT projectdetails.*,investorproject.myFunding FROM projectdetails JOIN investorproject ON projectdetails.ID = investorproject.projectID AND investorproject.investorID = '"+email+"' and projectdetails.Status = 'Pending'";

			ResultSet rs=db.s.executeQuery(query);
			while(rs.next()) {

				String id = rs.getString("ID");
				String name = rs.getString("Name");
				String status = rs.getString("Status");
				int currentFund = Integer.parseInt(rs.getString("CurrentFund"));
				int requiredFund = Integer.parseInt(rs.getString("RequiredFund"));
				int equity = Integer.parseInt(rs.getString("Equity"));
				int myFunding = Integer.parseInt(rs.getString("myFunding"));
				String details = rs.getString("Details");
				new myProject(myInvestmentArea,id,name,requiredFund,equity,currentFund,status,myFunding,details);
			}


		}catch (Exception b) {
			System.out.println("Caught Exception in callMyInvestArea function\n"+b);
	}

	}

	private void callMyLibraryArea() {
		removeExisting();
		myLibraryArea = new JPanel();
		commonArea.add(myLibraryArea);
		myLibraryArea.setLayout(new FlowLayout(FlowLayout.LEFT,0,5) );
		myLibraryArea.setBounds(0,60,commonArea.getWidth(),commonArea.getHeight()-60);

		setTitle(commonArea,"My Library");
		try {
			dbConnection db = new dbConnection();
			String query="SELECT projectdetails.*,investorproject.myFunding FROM projectdetails JOIN investorproject ON projectdetails.ID = investorproject.projectID AND investorproject.investorID = '"+email+"' and projectdetails.Status = 'Accepted'";

			ResultSet rs=db.s.executeQuery(query);
			while(rs.next()) {

				String id = rs.getString("ID");
				String name = rs.getString("Name");
				String status = rs.getString("Status");
				int currentFund = Integer.parseInt(rs.getString("CurrentFund"));
				int requiredFund = Integer.parseInt(rs.getString("RequiredFund"));
				int equity = Integer.parseInt(rs.getString("Equity"));
				int myFunding = Integer.parseInt(rs.getString("myFunding"));
				String details = rs.getString("Details");
				new myProject(myLibraryArea,id,name,requiredFund,equity,currentFund,status,myFunding,details);
			}


		}catch (Exception b) {
			System.out.println("Caught Exception in callMyLibraryArea function\n"+b);
	}

	}

	private void callNewProjectArea() {
		removeExisting();
		newProjectArea = new JPanel();
		commonArea.add(newProjectArea);
		newProjectArea.setLayout(new FlowLayout(FlowLayout.LEFT,5,5) );
		newProjectArea.setBounds(0,60,commonArea.getWidth(),commonArea.getHeight()-60);

		setTitle(commonArea,"New Projects");


		try {
			dbConnection db = new dbConnection();
			String query="SELECT projectdetails.* FROM projectdetails LEFT JOIN investorproject ON projectdetails.ID = investorproject.projectID AND investorproject.investorID = '"+email+"' WHERE projectdetails.Status = 'Pending' and investorproject.projectID IS NULL";

			ResultSet rs=db.s.executeQuery(query);
			while(rs.next()) {

				String id = rs.getString("ID");
				String name = rs.getString("Name");
				String status = rs.getString("Status");
				int currentFund = Integer.parseInt(rs.getString("CurrentFund"));
				int requiredFund = Integer.parseInt(rs.getString("RequiredFund"));
				int equity = Integer.parseInt(rs.getString("Equity"));
				String details = rs.getString("Details");
				new newProject(newProjectArea,id,name,requiredFund,equity,currentFund,status,details);
			}


		}catch (Exception b) {
			System.out.println("Caught Exception in callNewProjectArea function\n"+b);
	}
	}
	static void removeExisting() {
		if(titlePanel != null) {
		commonArea.remove(titlePanel);
		}
		if(myInvestmentArea != null) {
			commonArea.remove(myInvestmentArea);
		}
		if(profileArea != null) {
			commonArea.remove(profileArea);
		}
		if(newProjectArea != null) {
			commonArea.remove(newProjectArea);
		}
		if(myLibraryArea!=null) {
			commonArea.remove(myLibraryArea);
		}
		if(genericProfileArea!=null) {
			commonArea.remove(genericProfileArea);
		}
	}
	static void setTitle(JPanel Area,String text) {
		titlePanel = new JPanel();
		Area.add(titlePanel);
	    titlePanel.setBounds(0,0,Area.getWidth(),60);
	    titlePanel.setBackground(new Color(50, 50, 50));
	    titlePanel.setLayout(null);

		JLabel titleLabel = new JLabel(text);
		titleLabel.setFont(new Font("Times New Roman", Font.PLAIN, 28));
		titleLabel.setForeground(Color.white);
		titlePanel.add(titleLabel);
		titleLabel.setBounds(50,10,200,30);
	}
 }

