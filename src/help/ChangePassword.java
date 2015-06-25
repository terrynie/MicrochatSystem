package help;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;

import client.Main;

import com.mongodb.BasicDBObject;


public class ChangePassword extends JFrame {
	

	private JPanel contentPane;
	private JTextField username;
	private JPasswordField oldPassword;
	private JPasswordField newPassword;
	private JPasswordField passwordVerify;


	public ChangePassword() {
		this.setVisible(true);
		this.setTitle("WW -- Microchat System");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblChangePassword = new JLabel("Change Password");
		lblChangePassword.setBounds(171, 6, 110, 16);
		contentPane.add(lblChangePassword);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(86, 57, 73, 16);
		contentPane.add(lblUsername);
		
		JLabel lblOldPassword = new JLabel("Old Password");
		lblOldPassword.setBounds(86, 98, 85, 16);
		contentPane.add(lblOldPassword);
		
		JLabel lblPasswordVerify = new JLabel("Password Verify");
		lblPasswordVerify.setBounds(86, 193, 110, 16);
		contentPane.add(lblPasswordVerify);
		
		JLabel lblNewPassword = new JLabel("New Password");
		lblNewPassword.setBounds(86, 147, 96, 16);
		contentPane.add(lblNewPassword);
		
		username = new JTextField();
		username.setBounds(214, 51, 134, 28);
		contentPane.add(username);
		username.setColumns(10);
		
		oldPassword = new JPasswordField();
		oldPassword.setBounds(214, 92, 134, 28);
		contentPane.add(oldPassword);
		
		passwordVerify = new JPasswordField();
		passwordVerify.setBounds(214, 187, 134, 28);
		contentPane.add(passwordVerify);
		
		newPassword = new JPasswordField();
		newPassword.setBounds(214, 141, 134, 28);
		contentPane.add(newPassword);
		
		JButton change = new JButton("Change");
		change.setBounds(164, 221, 117, 29);
		contentPane.add(change);
		change.addActionListener(event->{
			MongodbLink ml = new MongodbLink();
			String password = ml.getPassword(username.getText());
			String password1 = new String(oldPassword.getPassword());
			String password2 = new String(passwordVerify.getPassword());
			String password3 = new String(newPassword.getPassword());
			//verify that if the old password is right or not
			//if the old password is right,then verify the first new password and the second password right or not
			if(password1.equals(password) && password2.equals(password3)  ){
				ml.changePassword(username.getText(), password3);
				Object[] options = { "OK"};
				JOptionPane.showOptionDialog(null, "Change Successful!", "Attention", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, null);
				this.setVisible(false);
				new Main().setVisible(true);
			}else if(!password1.equals(password)){
				Object[] options = { "OK"};
				JOptionPane.showOptionDialog(null, "Eorro Password!", "Attention", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, null); 
			}else if(!password2.equals(password3)){
				Object[] options = { "OK"};
				JOptionPane.showOptionDialog(null, "Verify Password Is Wrong!", "Attention", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, null);
			}
		});
		
		
		
	}
}
