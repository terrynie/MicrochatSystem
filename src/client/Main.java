package client;
import help.ChangePassword;
import help.MongodbLink;
import help.RegisterDemo;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;

import com.mongodb.MongoTimeoutException;


public class Main extends JFrame {

	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField loginAccountTextField;
	private JPasswordField loginPasswordField;
	static Main frame = new Main();
	
	
	public static void main(String[] args) {
		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public Main() {
		this.setTitle("WW -- Microchat System");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblWW = new JLabel("W W");
		lblWW.setHorizontalAlignment(SwingConstants.CENTER);
		lblWW.setBounds(128, 6, 200, 50);
		contentPane.add(lblWW);
		
		JLabel loginAccountLabel = new JLabel("Username");
		loginAccountLabel.setHorizontalAlignment(SwingConstants.CENTER);
		loginAccountLabel.setBounds(75, 62, 95, 50);
		contentPane.add(loginAccountLabel);
		
		JLabel loginPasswordLabel = new JLabel("Password");
		loginPasswordLabel.setHorizontalAlignment(SwingConstants.CENTER);
		loginPasswordLabel.setBounds(75, 118, 95, 50);
		contentPane.add(loginPasswordLabel);
		
		
		
		loginAccountTextField = new JTextField();
		loginAccountTextField.setColumns(10);
		loginAccountTextField.setBounds(222, 73, 134, 28);
		contentPane.add(loginAccountTextField);
		
		loginPasswordField = new JPasswordField();
		loginPasswordField.setBounds(222, 129, 134, 28);
		contentPane.add(loginPasswordField);
		
		//登陆按钮
		JButton loginButton = new JButton("Login");
		loginButton.setBounds(100, 204, 117, 29);
		contentPane.add(loginButton);
		loginButton.addActionListener(event->{
			
				String username = "";
				String loginPassword = new String(loginPasswordField.getPassword());//输入的密码
				String password = null;// 从数据库中得到的密码
				username = loginAccountTextField.getText(); //获取输入的用户名
				try{
					MongodbLink ml =new MongodbLink();
					if(ml.verifyUserExistOrNot(username)){
						//连接数据库，从数据库获取密码，并验证登陆
						password = ml.getPassword(username);
						if (loginPassword.compareTo(password)==0){
							frame.setVisible(false);
							frame.dispose();;
							new Thread(new MyFriendList(username)).start();					
						}else{
							Object[] options = { "OK"};
							JOptionPane.showOptionDialog(null, "username or password is wrong!", "Attention", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, null); 
						}
					}else{
						Object[] options = { "OK"};
						JOptionPane.showOptionDialog(null, "username isn't exist!", "Attention", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, null); 
					}
				}catch(MongoTimeoutException e){
					Object[] options = { "OK"};
					JOptionPane.showOptionDialog(null, "Server is busy!", "Attention", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, null); 
				}
				
						
		});
		
		
		//注册按钮
		JButton loginRegisterButton = new JButton("Sign up");
		loginRegisterButton.setBounds(239, 204, 117, 29);
		contentPane.add(loginRegisterButton);
		loginRegisterButton.addActionListener(event->{
				if(event.getSource()==loginRegisterButton){
					frame.setVisible(false);
					frame.dispose();
					new RegisterDemo();
				}
			
		});
		
		
		//change password button
		JButton changePassword = new JButton("Change password");
		changePassword.setForeground(Color.BLUE);
		changePassword.setBounds(225, 160, 131, 16);
		contentPane.add(changePassword);
		changePassword.addActionListener(event->{
			this.setVisible(false);
			this.dispose();
			new ChangePassword();
		});
			
	}
}
