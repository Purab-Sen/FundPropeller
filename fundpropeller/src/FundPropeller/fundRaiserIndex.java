package FundPropeller;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

class myNewProject extends JPanel implements ActionListener{
	String name;
	String ID;
	String details;
	int requiredFund;
	int equity;
	int currentFund;
	String status;
	JButton removeButton,showAllButton,detailButton;
	myNewProject(String id,String name,int requiredFund,int equity,String details,String status){
		this.name=name;
		this.requiredFund=requiredFund;
		this.equity=equity;
		this.details=details;
		this.status = status;
		this.currentFund = 0;
		if(id.equals("")) {
			this.ID=generateID();
		} else {
			this.ID = id;
		}
	}
	void addnewProject(){
		try {
			dbConnection db =new dbConnection();
			String query="insert into projectdetails values('"+this.ID+"','"+signUpInvestor.createText(this.name)+"','"+signUpInvestor.createText(this.details)+"','"+this.requiredFund+"','"+this.equity+"','"+this.currentFund+"','"+this.status+"')";
			db.s.executeUpdate(query);
			query = "insert into fundraiserproject values('"+investorIndex.email+"','"+this.ID+"')";
			db.s.executeUpdate(query);
		} catch(Exception b) {
			System.out.println("Caught exception in inserting new Project"+b);
		}
	}
	void generateGUI() {
		this.setPreferredSize(new Dimension(1200,60));
		this.setBorder(new LineBorder(Color.black,2));
		this.setLayout(new FlowLayout(FlowLayout.LEFT,30,10));
		fundRaiserIndex.myProjectArea.add(this);

		JLabel pnameLabel = new JLabel("Name:"+name);
		pnameLabel.setPreferredSize(new Dimension(300,30));
		this.add(pnameLabel);
		pnameLabel.setFont(new Font("Times New Roman", Font.PLAIN, 28));


		JLabel rFundLabel = new JLabel("Ask: Rs."+requiredFund);
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

		removeButton = new JButton("remove");
		removeButton.setPreferredSize(new Dimension(130,35));
		this.add(removeButton);
		removeButton.setFont(new Font("Times New Roman", Font.PLAIN, 28));
		removeButton.setBackground(Color.RED);
		removeButton.addActionListener(this);
	}
	void getInvestedProjects(JPanel panelArea) {
		this.setPreferredSize(new Dimension(1200,60));
		this.setBorder(new LineBorder(Color.black,2));
		this.setLayout(new FlowLayout(FlowLayout.LEFT,15,10));
		panelArea.add(this);

		JLabel pnameLabel = new JLabel("Name:"+name);
		pnameLabel.setPreferredSize(new Dimension(200,30));
		this.add(pnameLabel);
		pnameLabel.setFont(new Font("Times New Roman", Font.PLAIN, 28));


		JLabel rFundLabel = new JLabel("Ask: Rs."+requiredFund);
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

		if(status.equals("Accepted")) {
			showAllButton = new JButton("Investor Detail");
		} else if(status.equals("Pending")) {
			showAllButton = new JButton("Show All Investors");
		}
		showAllButton.setPreferredSize(new Dimension(300,40));
		this.add(showAllButton);
		showAllButton.setFont(new Font("Times New Roman", Font.PLAIN, 28));
		showAllButton.addActionListener(this);
	}

	void showAllInvestors() {
		fundRaiserIndex.removeExisting();
		fundRaiserIndex.myInvestorsArea = new JPanel();
		investorIndex.commonArea.add(fundRaiserIndex.myInvestorsArea);
		fundRaiserIndex.myInvestorsArea.setBounds(0,60,investorIndex.commonArea.getWidth(),investorIndex.commonArea.getHeight()-60);
		fundRaiserIndex.myInvestorsArea.setLayout(new FlowLayout(FlowLayout.LEFT,0,5) );

		investorIndex.setTitle(investorIndex.commonArea,"Investors");


		JButton backBtn = new JButton("Go Back");
		investorIndex.titlePanel.add(backBtn);
		backBtn.setBounds(1000,10,150,40);
		backBtn.setFont(new Font("Times New Roman",Font.PLAIN,28));
		backBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(status.equals("Pending")) {
					investorIndex.miBtn.doClick();
				} else if(status.equals("Accepted")) {
					investorIndex.libBtn.doClick();
				}
			}

		});

		try {
			dbConnection db = new dbConnection();
			String query;
			if(status.equals("Pending")) {
				query="select * from profiledata join investorproject on profiledata.Email = investorproject.investorID where investorproject.projectID = '"+this.ID+"'";
			} else {
				query="select * from profiledata join investorproject on profiledata.Email = investorproject.investorID where investorproject.projectID = '"+this.ID+"' and investorproject.status = 'Accepted'";
			}
			ResultSet rs=db.s.executeQuery(query);
			while(rs.next()) {

				String Email = rs.getString("Email");
				String name = rs.getString("Name");
				String DOB = rs.getString("DOB");
				String Education = rs.getString("Education");
				String university = rs.getString("University");
				String Address = rs.getString("Address");
				String imageBytecode = rs.getString("photo");
				String contact = rs.getString("contact");
				String status = rs.getString("status");
				int myFunding = Integer.parseInt(rs.getString("myFunding"));
				new investorDetails(this.ID,this.showAllButton,name, myFunding,imageBytecode,DOB,Email,Address,university,contact,Education,status);
			}


		}catch (Exception b) {
			System.out.println("Could not retrieve data of investors in showAllInvestors function");
	}

	}
	private String generateID() {
		String id="";
		try {
			dbConnection db =new dbConnection();
			String query="select ID from projectdetails";
			ResultSet rs=db.s.executeQuery(query);
			if(rs.next()) {
				int temp=0;
				String num="";
				do {
					num = rs.getString("ID");
				}while(rs.next());
				num = num.substring(3);
				temp = Integer.parseInt(num)+1;
				id = "id_"+temp;
			}else {
				id = "id_1";
			}
			this.ID = id;

		} catch(Exception b) {
			System.out.println("Could not generate Id in generateID function"+b);
		}
		return id;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == removeButton) {
			int result = JOptionPane.showConfirmDialog(
	                null,
	                "Do you want to remove this project?",
	                "Confirmation",
	                JOptionPane.YES_NO_OPTION
	        );

	        if (result == JOptionPane.YES_OPTION) {
	        	try {
					dbConnection db =new dbConnection();
					String query="Delete from projectdetails where ID = '"+this.ID+"'";
					db.s.execute(query);
					investorIndex.npBtn.doClick();
					JOptionPane.showMessageDialog(null, "Project Removed Successful!");
				} catch(Exception b) {
					System.out.println("Could not remove project from projectDetails at line 218 in fundRaiserIndex.");
				}
	        }
		}else if(e.getSource() == showAllButton) {
				showAllInvestors();
		}else if(e.getSource() == detailButton) {
			JPanel textPanel = new JPanel();
			fundRaiserIndex.ref.add(textPanel);
			textPanel.setBounds(fundRaiserIndex.ref.getWidth()/2-190,fundRaiserIndex.ref.getHeight()/4,400,430);
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
					fundRaiserIndex.ref.remove(textPanel);
					fundRaiserIndex.ref.repaint();
				}

	        });
	        fundRaiserIndex.ref.revalidate();

		}

	}
}
class investorDetails extends JPanel{
	String name;
	int myFunding;
	String imageBytecode;
	String DOB;
	String Email;
	String Address;
	String university;
	String Education;
	String contact;
	String PID;
	String status;
	JButton showAllButton;
	investorDetails(String PID,JButton showAllButton,String name,int myFunding,String imageBytecode,String DOB,String Email,String Address,String university,String contact,String Education,String status){
		this.PID = PID;
		this.showAllButton = showAllButton;
		this.name = name;
		this.myFunding = myFunding;
		this.imageBytecode = imageBytecode;
		this.DOB = DOB;
		this.Email = Email;
		this.Address = Address;
		this.university=university;
		this.Education = Education;
		this.contact=contact;
		this.status = status;

		this.setPreferredSize(new Dimension(1200,60));
		this.setBorder(new LineBorder(Color.black,2));
		this.setLayout(new FlowLayout(FlowLayout.LEFT,30,10));
		fundRaiserIndex.myInvestorsArea.add(this);

		JLabel pnameLabel = new JLabel("Name:"+name);
		pnameLabel.setPreferredSize(new Dimension(400,30));
		this.add(pnameLabel);
		pnameLabel.setFont(new Font("Times New Roman", Font.PLAIN, 28));

		JLabel fundLabel = new JLabel("Funding: Rs."+myFunding);
		fundLabel.setPreferredSize(new Dimension(350,30));
		this.add(fundLabel);
		fundLabel.setFont(new Font("Times New Roman", Font.PLAIN, 28));

		JButton pfButton = new JButton("View profile");
		this.add(pfButton);
		pfButton.setFont(new Font("Times New Roman", Font.PLAIN, 28));
		pfButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					fundRaiserIndex.genericProfileArea = new JPanel();
					profile.loadProfile(name, DOB, Education, imageBytecode, university, Address, contact, "Investor", showAllButton);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					System.out.println("Could not load profile of investor in fundRaiserIndex page"+e1);
				}
			}

		});

		if(status.equals("Pending")) {
		JButton acceptButton = new JButton("Accept");
		this.add(acceptButton);
		acceptButton.setBackground(Color.green);
		acceptButton.setFont(new Font("Times New Roman", Font.PLAIN, 28));
		acceptButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					dbConnection db = new dbConnection();

					String query="update investorproject set status = 'Accepted' where investorID = '"+Email+"' and projectID = '"+PID+"'";
					db.s.executeUpdate(query);

					query="update projectdetails set status = 'Accepted' where ID = '"+PID+"'";
					db.s.executeUpdate(query);

					JOptionPane.showMessageDialog(null, "Accepted!!");
					investorIndex.libBtn.doClick();
				}catch (Exception b) {
					System.out.println("Couldn't update project at line 351"+b);
			}
			}

		});

		}
	}
}

public class fundRaiserIndex extends investorIndex {
	static JPanel myProjectArea,newInvestmentArea,textPanel,myInvestorsArea,myLibraryArea,genericProfileArea;
	String prName,prDetails;
	int prEquity,prRequiredFund;
	JButton apBtn,uploadBtn;
	JTextArea textArea;
	static fundRaiserIndex ref;
	fundRaiserIndex(String email) throws IOException {
		super(email);
		ref = this;
		miBtn.setText("New Investment");
		npBtn.setText("My Projects      ");
		npBtn.doClick();
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
				e1.printStackTrace();
			}
		}else if(e.getSource()==pfButton) {
			try {
				fundRaiserIndex.removeExisting();
				callProfileArea();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}else if(e.getSource()==npBtn) {
			callMyProjectArea();
		}else if(e.getSource()==miBtn) {
			callNewInvestmentArea();
		}else if(e.getSource()==libBtn) {
			callMyLibraryArea();
		}else if(e.getSource()==apBtn) {
			apBtn.setEnabled(false);
			apBtn.setVisible(false);
			addProject();
		}else if(e.getSource()==uploadBtn){
			if(textArea.getText().equals("")||textArea.getText() == null) {
				JOptionPane.showMessageDialog(null,"Please Add Details");
			}else {
				ref.remove(textPanel);
				ref.repaint();
				prDetails = textArea.getText();
				new myNewProject("",prName,prRequiredFund,prEquity,prDetails,"Pending").addnewProject();
				npBtn.doClick();
				JOptionPane.showMessageDialog(null,"Project Added Successfully!!");
			}
		}
		else if(e.getSource()==loBtn) {
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

	private void callMyProjectArea() {
		removeExisting();
		myProjectArea = new JPanel();
		commonArea.add(myProjectArea);
		myProjectArea.setLayout(new FlowLayout(FlowLayout.LEFT,0,5) );
		myProjectArea.setBounds(0,60,commonArea.getWidth(),commonArea.getHeight()-60);

		setTitle(commonArea,"My Projects");

		apBtn = new JButton("Add Project");
		titlePanel.add(apBtn);
		apBtn.setBounds(700,10,150,40);
		apBtn.setFont(new Font("Times New Roman",Font.PLAIN,22));
		apBtn.addActionListener(this);

		try {
			dbConnection db = new dbConnection();

			String query="SELECT projectdetails.* FROM projectdetails JOIN fundraiserproject ON projectdetails.ID = fundraiserproject.projectID AND fundraiserproject.fundRaiserID = '"+email+"' LEFT JOIN investorproject on fundraiserproject.projectID = investorproject.projectID where investorproject.projectID IS NULL";
			ResultSet rs=db.s.executeQuery(query);
			while(rs.next()) {

				String id = rs.getString("ID");
				String name = rs.getString("Name");
				String details = rs.getString("Details");
				int requiredFund = Integer.parseInt(rs.getString("RequiredFund"));
				int equity = Integer.parseInt(rs.getString("Equity"));
				new myNewProject(id,name,requiredFund,equity,details,"Pending").generateGUI();
			}


		}catch (Exception b) {
			System.out.println("Caught Exception in callMyProjectArea function"+b);
	}
	}

	private void callNewInvestmentArea() {
		removeExisting();
		newInvestmentArea = new JPanel();
		commonArea.add(newInvestmentArea);
		newInvestmentArea.setLayout(new FlowLayout(FlowLayout.LEFT,5,5) );
		newInvestmentArea.setBounds(0,60,commonArea.getWidth(),commonArea.getHeight()-60);

		setTitle(commonArea,"New Investments");
		try {
			dbConnection db = new dbConnection();
			String query="SELECT DISTINCT projectdetails.* FROM projectdetails JOIN fundraiserproject ON projectdetails.ID = fundraiserproject.projectID JOIN investorproject ON projectdetails.ID = investorproject.projectID where fundraiserproject.fundRaiserID = '"+email+"' and projectdetails.status = 'Pending'";


			ResultSet rs=db.s.executeQuery(query);
			while(rs.next()) {

				String id = rs.getString("ID");
				String name = rs.getString("Name");
				String details = rs.getString("Details");
				int requiredFund = Integer.parseInt(rs.getString("RequiredFund"));
				int equity = Integer.parseInt(rs.getString("Equity"));
				new myNewProject(id,name,requiredFund,equity,details,"Pending").getInvestedProjects(newInvestmentArea);
			}


		}catch (Exception b) {
			System.out.println("Caught Exception in callNewInvestmentArea function"+b);
	 }
	}
	private void callMyLibraryArea() {
		removeExisting();
		newInvestmentArea = new JPanel();
		commonArea.add(newInvestmentArea);
		newInvestmentArea.setLayout(new FlowLayout(FlowLayout.LEFT,5,5) );
		newInvestmentArea.setBounds(0,60,commonArea.getWidth(),commonArea.getHeight()-60);
		setTitle(commonArea,"My Library");

		try {
			dbConnection db = new dbConnection();
			String query="SELECT DISTINCT projectdetails.* FROM projectdetails JOIN fundraiserproject ON projectdetails.ID = fundraiserproject.projectID JOIN investorproject ON projectdetails.ID = investorproject.projectID where fundraiserproject.fundRaiserID = '"+email+"' and investorproject.status = 'Accepted'";


			ResultSet rs=db.s.executeQuery(query);
			while(rs.next()) {

				String id = rs.getString("ID");
				String name = rs.getString("Name");
				String details = rs.getString("Details");
				int requiredFund = Integer.parseInt(rs.getString("RequiredFund"));
				int equity = Integer.parseInt(rs.getString("Equity"));
				new myNewProject(id,name,requiredFund,equity,details,"Accepted").getInvestedProjects(newInvestmentArea);
			}


		}catch (Exception b) {
			System.out.println("Caught Exception in callMyLibraryArea function"+b);
	 }
	}
	static void removeExisting() {
		if(titlePanel != null) {
			commonArea.remove(titlePanel);
		}
		if(myProjectArea != null) {
			commonArea.remove(myProjectArea);
		}
		if(profileArea != null) {
			commonArea.remove(profileArea);
		}
		if(newInvestmentArea != null) {
			commonArea.remove(newInvestmentArea);
		}
		if(myInvestorsArea!=null) {
			commonArea.remove(myInvestorsArea);
		}
		if(myLibraryArea!=null) {
			commonArea.remove(myLibraryArea);
		}
		if(genericProfileArea!=null) {
			commonArea.remove(genericProfileArea);
		}
	}

	private void addProject() {
		int i= 1;
		while(i!=4) {
			String temp = null;
			try {
			if(i==1) {
				temp = JOptionPane.showInputDialog("Enter Name of Your Project:");
				if(temp != null && !temp.equals("")) {prName = temp;i++;}
			}else if(i==2) {
				temp = JOptionPane.showInputDialog("Enter fund required for Your Project:");
				if(temp!=null) {
					prRequiredFund = Integer.parseInt(temp);
					if(prRequiredFund<=0) {
						throw new Exception("Amount");
					}
					i++;
					}
			}else if(i==3) {
				temp = JOptionPane.showInputDialog("Enter the equity% that you will share with investor:");
				if(temp!=null) {
					prEquity = Integer.parseInt(temp);
					if(prEquity<=0 || prEquity>100) {
						throw new Exception("Equity");
					}
					i++;
				}
			}
			if(temp == null || temp.equals("")) {
				int result = JOptionPane.showConfirmDialog(
		                null,
		                "Do you want to cancel the addition of new project?",
		                "Confirmation",
		                JOptionPane.YES_NO_OPTION
		        );

		        if (result == JOptionPane.YES_OPTION) {
		        	JOptionPane.showMessageDialog(null,"Project Cancelled!!");
		        	apBtn.setEnabled(true);
					apBtn.setVisible(true);
		            return;
		        }
			}
			}catch(Exception e) {
				if(temp.equals("")) {
					JOptionPane.showMessageDialog(null,"Can't Remain Empty");
				} else {
					JOptionPane.showMessageDialog(null,"Invalid Entry");
				}
			}
		}

		textPanel = new JPanel();
		ref.add(textPanel);
		textPanel.setBounds(fundRaiserIndex.ref.getWidth()/2-100,fundRaiserIndex.ref.getHeight()/4,400,430);
		textPanel.setBackground(Color.gray);

		textPanel.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
		JPanel textAreaTitlePanel = new JPanel();
		textAreaTitlePanel.setPreferredSize(new Dimension(400,50));
		textAreaTitlePanel.setBackground(Color.GREEN);
		textAreaTitlePanel.setLayout(null);

		JLabel textAreaTitle = new JLabel("Write Your project details Here");
		textAreaTitlePanel.add(textAreaTitle);
		textAreaTitle.setBounds(0,0,400,50);
		textAreaTitle.setFont(new Font("Times New Roman", Font.PLAIN, 28));

		textPanel.add(textAreaTitlePanel);
        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(textAreaTitle.getFont());
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(400,350));
        textPanel.add(scrollPane);

        uploadBtn = new JButton("Upload");
        uploadBtn.setPreferredSize(new Dimension(textPanel.getWidth(),30));
        uploadBtn.setFont(new Font("Times New Roman", Font.PLAIN, 28));
        textPanel.add(uploadBtn);
        uploadBtn.addActionListener(this);
        ref.revalidate();
	}

}
