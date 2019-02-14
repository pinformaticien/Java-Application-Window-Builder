import java.awt.EventQueue;
import java.sql.*;
import javax.swing.*;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Login {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	Connection connection = null;
	private JTextField textFieldUN;
	private JPasswordField passwordField;

	/**
	 * Create the application.
	 */
	public Login() {
		initialize();
		connection = sqliteConnection.dbConnector();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 833, 416);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("UserName");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setBounds(417, 58, 108, 35);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Password");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1.setBounds(402, 155, 93, 23);
		frame.getContentPane().add(lblNewLabel_1);
		
		textFieldUN = new JTextField();
		textFieldUN.setBounds(559, 55, 154, 43);
		frame.getContentPane().add(textFieldUN);
		textFieldUN.setColumns(10);
		
		JButton btnLogin = new JButton("Login");
		Image imgok = new ImageIcon(this.getClass().getResource("ok.png")).getImage();
		btnLogin.setIcon(new ImageIcon(imgok));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					String query = "SELECT * FROM employeeInfo WHERE Username=? AND Password=? ";
					PreparedStatement pst = connection.prepareStatement(query);
					pst.setString(1, textFieldUN.getText());
					pst.setString(2, passwordField.getText());
					ResultSet rs = pst.executeQuery();
					int count = 0;
					while(rs.next()) {
						
						count++;
					}
					if(count == 1) {
						JOptionPane.showMessageDialog(null, "Username and Password is correct.");
						frame.dispose();
						EmployeeInfo EmplInfo = new EmployeeInfo();
						EmplInfo.setVisible(true);
					} else if (count > 1) {
						JOptionPane.showMessageDialog(null, "Duplicate username and Password.");
					} else {
						JOptionPane.showMessageDialog(null, "Username and Password is not correct Try again.");
					}
					
					rs.close();
					pst.close();
					connection.close();
					
				} catch (Exception e2) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(null, "Connection failed.");
				}
			}
		});
		btnLogin.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnLogin.setBounds(593, 226, 120, 43);
		frame.getContentPane().add(btnLogin);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(559, 146, 154, 43);
		frame.getContentPane().add(passwordField);
		
		JLabel lblNewLabel_2 = new JLabel("");
		Image imglogin = new ImageIcon(this.getClass().getResource("login.png")).getImage();
		lblNewLabel_2.setIcon(new ImageIcon(imglogin));
		lblNewLabel_2.setBounds(45, 35, 298, 243);
		frame.getContentPane().add(lblNewLabel_2);
	}
}
