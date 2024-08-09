package FundPropeller;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class choice extends JFrame implements ActionListener{
	Login LI;
	static JButton loginAsIn, loginAsR;
	static JLabel cross;
	static int buttonID;
	static choice c;
	choice(){
		this.setUndecorated(true);
		this.setLayout(null);
		this.setExtendedState(Frame.MAXIMIZED_BOTH);
		this.setVisible(true);

        JPanel titleBar = new JPanel();
		titleBar.setLayout(new BorderLayout());
		titleBar.setBackground(new Color(44, 152, 178));
		titleBar.setBounds(0,0,this.getWidth(),60);

		JLabel title = new JLabel("   FundPropeller");
		titleBar.add(title,BorderLayout.WEST);
		title.setFont(new Font("Times New Roman", Font.BOLD, 30));
		title.setForeground(new Color(255,255,255));

		cross = new JLabel("x  ");
		cross.setFont(new Font("Arial", Font.PLAIN, 32));
		cross.setForeground(Color.WHITE);

		titleBar.add(cross,BorderLayout.EAST);

		this.add(titleBar);
		cross.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
               close();
            }
        });
		loginAsR =new JButton("Login As FundRaiser");
		this.add(loginAsR);

		ImageIcon originalIcon = new ImageIcon("src/logo.jpg");

        Image originalImage = originalIcon.getImage();
        Image resizedImage = originalImage.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH);

        // Create a new ImageIcon with the resized image
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        JLabel imageLabel = new JLabel(resizedIcon);
        this.add(imageLabel);
        imageLabel.setBounds(0,0,this.getWidth(), this.getHeight());

        loginAsR.setBounds(this.getWidth()/2+100,this.getHeight()*3/4-60,300,60);
		loginAsR.setFont(new Font("Times New Roman",Font.BOLD,22));
		loginAsR.setBackground(new Color(44, 152, 178));
		loginAsR.setForeground(Color.white);
		loginAsR.addActionListener(this);

		loginAsIn = new JButton("Login As Investor");
		this.add(loginAsIn);
		loginAsIn.setBounds(this.getWidth()/2-400,this.getHeight()*3/4-60,300,60);
		loginAsIn.setFont(new Font("Times New Roman",Font.BOLD,22));
		loginAsIn.setBackground(new Color(44, 152, 178));
		loginAsIn.setForeground(Color.white);
		loginAsIn.addActionListener(this);

		c=this;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == loginAsIn || e.getSource() == loginAsR)
		{
			if(LI != null) {
				LI.dispose();
			}
			if(Login.SUI != null) {
				Login.SUI.dispose();
			}
			LI = new Login();
			buttonID = (e.getSource() == loginAsIn)?1:2;
		}
	}
	public  void close() {
		dispose();
        if(LI != null) {
        	LI.dispose();
        }
        if(Login.SUI != null) {
        	Login.SUI.dispose();
        }
    }
}


