package FundPropeller;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class profile {
	static JButton genericButton;
	static JPanel genericProfileArea,commonArea;
	static String Role;
	public static void loadProfile(String name,String DOB,String Education,String imageBytecode,String university,String Address,String contact,String Role,JButton genericButton) throws IOException {
		if(Role.equals("Investor")) {
			fundRaiserIndex.removeExisting();
			commonArea = investorIndex.commonArea;
			genericProfileArea = fundRaiserIndex.genericProfileArea;
			investorIndex.setTitle(commonArea, Role+" Profile");
		}else {
			investorIndex.removeExisting();
			commonArea = investorIndex.commonArea;
			investorIndex.genericProfileArea = new JPanel();
			genericProfileArea = investorIndex.genericProfileArea;
		}
		profile.Role = Role;
		profile.genericButton = genericButton;
		commonArea.add(genericProfileArea);
		genericProfileArea.setLayout(null);
		genericProfileArea.setBounds(0,60,commonArea.getWidth(),commonArea.getHeight()-60);

        Image originalImage = myImage.byteStringToImage(imageBytecode);
        Image resizedImage = originalImage.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        // Create a new ImageIcon with the resized image
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        JLabel imageLabel = new JLabel(resizedIcon);
        imageLabel.setBorder(new LineBorder(Color.black,2));
        genericProfileArea.add(imageLabel);
        imageLabel.setBounds(10,10,300, 300);

        JLabel nameLabel = new JLabel("Name: "+name);
        genericProfileArea.add(nameLabel);
        nameLabel.setBounds(10,320,1000,30);
        nameLabel.setFont(new Font("Times New Roman", Font.PLAIN, 28));

        JLabel DOBLabel = new JLabel("DOB: "+DOB);
        genericProfileArea.add(DOBLabel);
        DOBLabel.setBounds(10,370,1000,30);
        DOBLabel.setFont(new Font("Times New Roman", Font.PLAIN, 28));

        JLabel eduLabel = new JLabel("Education: "+Education);
        genericProfileArea.add(eduLabel);
        eduLabel.setBounds(10,420,1000,30);
        eduLabel.setFont(new Font("Times New Roman", Font.PLAIN, 28));

        JLabel uniLabel = new JLabel("School/University: "+university);
        genericProfileArea.add(uniLabel);
        uniLabel.setBounds(10,470,1000,30);
        uniLabel.setFont(new Font("Times New Roman", Font.PLAIN, 28));

        JLabel addressLabel = new JLabel("Address: "+Address);
        genericProfileArea.add(addressLabel);
        addressLabel.setBounds(10,520,1000,30);
        addressLabel.setFont(new Font("Times New Roman", Font.PLAIN, 28));

        JLabel roleLabel = new JLabel("Role: "+Role);
        genericProfileArea.add(roleLabel);
        roleLabel.setBounds(10,570,1000,30);
        roleLabel.setFont(new Font("Times New Roman", Font.PLAIN, 28));

        JLabel contactLabel = new JLabel("Contact: "+contact);
        genericProfileArea.add(contactLabel);
        contactLabel.setBounds(10,620,1000,30);
        contactLabel.setFont(new Font("Times New Roman", Font.PLAIN, 28));

        JButton backButton = new JButton("Go Back");
        genericProfileArea.add(backButton);
        backButton.setBounds(150,10,100,30);
        backButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(Role.equals("Investor")) {
					fundRaiserIndex.removeExisting();
				} else {
					investorIndex.removeExisting();
				}
				genericButton.doClick();
			}

        });

	}
}
