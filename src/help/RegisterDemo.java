package help;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;

import client.Main;


public class RegisterDemo extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField registerAccountTextField;
	private JPasswordField registerAccountPasswordField;
	private JPasswordField registerVerifyPasswordField;
	
	
	public RegisterDemo() {
		this.setVisible(true);
		this.setTitle("注册");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(500, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel registerAccountLabel = new JLabel("账号");
		registerAccountLabel.setHorizontalAlignment(SwingConstants.CENTER);
		registerAccountLabel.setBounds(62, 62, 112, 42);
		contentPane.add(registerAccountLabel);
		
		JLabel lblWW = new JLabel("W W");
		lblWW.setHorizontalAlignment(SwingConstants.CENTER);
		lblWW.setBounds(111, 0, 200, 50);
		contentPane.add(lblWW);
		
		registerAccountTextField = new JTextField();
		registerAccountTextField.setBounds(201, 69, 134, 28);
		contentPane.add(registerAccountTextField);
		registerAccountTextField.setColumns(10);
		
		JLabel registerPasswordLabel = new JLabel("密码");
		registerPasswordLabel.setHorizontalAlignment(SwingConstants.CENTER);
		registerPasswordLabel.setBounds(62, 114, 112, 42);
		contentPane.add(registerPasswordLabel);
		
		registerAccountPasswordField = new JPasswordField();
		registerAccountPasswordField.setBounds(201, 121, 134, 28);
		contentPane.add(registerAccountPasswordField);
		
		JLabel registerVerifyPasswordLabel = new JLabel("验证密码");
		registerVerifyPasswordLabel.setHorizontalAlignment(SwingConstants.CENTER);
		registerVerifyPasswordLabel.setBounds(62, 162, 112, 42);
		contentPane.add(registerVerifyPasswordLabel);
		
		registerVerifyPasswordField = new JPasswordField();
		registerVerifyPasswordField.setBounds(201, 169, 134, 28);
		contentPane.add(registerVerifyPasswordField);
		
		JButton registerRegister = new JButton("注册");
		registerRegister.setBounds(161, 216, 117, 35);
		contentPane.add(registerRegister);
		
		registerRegister.addActionListener(event->{
				String username = registerAccountTextField.getText();
				String password1 = new String(registerAccountPasswordField.getPassword());
				String password2 = new String(registerVerifyPasswordField.getPassword());
				MongodbLink ml = new MongodbLink();
				//判断用户名是否存在
				if(!ml.verifyUserExistOrNot(username)){
					//密码一致且密码不为空
					if(password1.compareTo(password2)==0 && password1.compareTo("") != 0){
						
						ml.setUserInfo(username, password1);
						Object[] options = { "OK"};
						JOptionPane.showOptionDialog(null, "注册成功,返回登陆", "提示", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, null); 
						this.setVisible(false);
						this.dispose();
						new Main().setVisible(true);
					}
					//密码为空
					else if(password1.compareTo("")==0){
						Object[] options = { "OK"};
						JOptionPane.showOptionDialog(null, "密码不能为空！", "警告", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, null);
					}
					//密码不一致
					else if(password1.compareTo(password2)!=0){
						Object[] options = { "OK"};
						JOptionPane.showOptionDialog(null, "密码不一致，返回修改！", "警告", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, null);
					}
				}else{
					Object[] options = { "OK"};
					JOptionPane.showOptionDialog(null, "用户名已存在！", "警告", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, null);
				}
							
		});
	}
}
