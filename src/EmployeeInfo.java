import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.sql.*;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class EmployeeInfo extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JComboBox comboBoxName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EmployeeInfo frame = new EmployeeInfo();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	Connection connection = null;
	private JTextField textFieldEID;
	private JTextField textFieldName;
	private JTextField textFieldSurname;
	private JTextField textFieldAge;
	
	
	public void refreshTable() {
		
		
		try {
			
			String query = "SELECT EID, Name, Surname, Username, Age FROM EmployeeInfo";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			
			
			
			table.setModel(DbUtils.resultSetToTableModel(rs));
			
			pst.close();
			rs.close();
			
		} catch (Exception e5) {
			// TODO: handle exception
			e5.printStackTrace();
		}
		
	}
	
	
	public void fillComboBox() {
		

		try {
			
			String query = "SELECT * FROM EmployeeInfo";
			
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				
				comboBoxName.addItem(rs.getString("Name"));
				
			}
			
		} catch (Exception e6) {
			// TODO: handle exception
			e6.printStackTrace();
		}
		
	}
	
	/**
	 * Create the frame.
	 */
	public EmployeeInfo() {
		connection = sqliteConnection.dbConnector();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1019, 624);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnLoadTable = new JButton("Load Employee Data");
		btnLoadTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					String query = "SELECT EID, Name, Surname, Username, Age FROM EmployeeInfo";
					PreparedStatement pst = connection.prepareStatement(query);
					ResultSet rs = pst.executeQuery();
					
					
					
					table.setModel(DbUtils.resultSetToTableModel(rs));
					
					pst.close();
					rs.close();
					
				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
			}
		});
		btnLoadTable.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnLoadTable.setBounds(666, 36, 193, 48);
		contentPane.add(btnLoadTable);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(250, 101, 731, 358);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				
				try {
					
					int row = table.getSelectedRow();
					String EID_ = table.getModel().getValueAt(row, 0).toString();
					
					String query = "SELECT * FROM EmployeeInfo WHERE EID='"+EID_+"' ";

					PreparedStatement pst = connection.prepareStatement(query);
					ResultSet rs = pst.executeQuery();

					while (rs.next()) {

						textFieldEID.setText(rs.getString("EID"));
						textFieldName.setText(rs.getString("Name"));
						textFieldSurname.setText(rs.getString("Surname"));
						textFieldAge.setText(rs.getString("Age"));

					}

					pst.close();

				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
			}
		});
		scrollPane.setViewportView(table);
		
		JLabel lblEid = new JLabel("EID");
		lblEid.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblEid.setBounds(24, 126, 66, 35);
		contentPane.add(lblEid);
		
		JLabel lblName = new JLabel("Name");
		lblName.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblName.setBounds(24, 197, 46, 14);
		contentPane.add(lblName);
		
		JLabel lblSurname = new JLabel("Surname");
		lblSurname.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblSurname.setBounds(24, 240, 66, 14);
		contentPane.add(lblSurname);
		
		JLabel lblAge = new JLabel("Age");
		lblAge.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblAge.setBounds(24, 284, 66, 14);
		contentPane.add(lblAge);
		
		textFieldEID = new JTextField();
		textFieldEID.setBounds(100, 127, 86, 35);
		contentPane.add(textFieldEID);
		textFieldEID.setColumns(10);
		
		textFieldName = new JTextField();
		textFieldName.setBounds(100, 188, 86, 35);
		contentPane.add(textFieldName);
		textFieldName.setColumns(10);
		
		textFieldSurname = new JTextField();
		textFieldSurname.setBounds(100, 238, 86, 33);
		contentPane.add(textFieldSurname);
		textFieldSurname.setColumns(10);
		
		textFieldAge = new JTextField();
		textFieldAge.setBounds(100, 282, 86, 35);
		contentPane.add(textFieldAge);
		textFieldAge.setColumns(10);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {

					String query = "INSERT INTO EmployeeInfo (EID, Name, Surname, Age) VALUES (?, ?, ?, ?)";
					PreparedStatement pst = connection.prepareStatement(query);
					
					pst.setString(1, textFieldEID.getText());
					pst.setString(2, textFieldName.getText());
					pst.setString(3, textFieldSurname.getText());
					pst.setString(4, textFieldAge.getText());
					
					pst.execute();
					
					JOptionPane.showMessageDialog(null, "Data saved");
					
					pst.close();
					

				} catch (Exception e3) {
					// TODO: handle exception
					e3.printStackTrace();
					
				}
				
				refreshTable();

			}
		});
		btnSave.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnSave.setBounds(24, 350, 107, 35);
		contentPane.add(btnSave);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					String query = "UPDATE EmployeeInfo SET EID='"+textFieldEID.getText()+"', Name='"+textFieldName.getText()+"', Surname='"+textFieldSurname.getText()+"', Age='"+textFieldAge.getText()+"' WHERE EID='"+textFieldEID.getText()+"' ";
					PreparedStatement pst = connection.prepareStatement(query);
					
					pst.execute();
					
					JOptionPane.showMessageDialog(null, "Data Updated.");
					
					pst.close();
					
				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
				
				refreshTable();
			}
		});
		btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnUpdate.setBounds(24, 401, 107, 35);
		contentPane.add(btnUpdate);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {

					String query = "DELETE FROM EmployeeInfo WHERE EID='"+textFieldEID.getText()+"' ";
					PreparedStatement pst = connection.prepareStatement(query);

					pst.execute();

					JOptionPane.showMessageDialog(null, "Data Deleted.");

					pst.close();

				} catch (Exception e4) {
					// TODO: handle exception
					e4.printStackTrace();
				}
				
				refreshTable();
			}
		});
		btnDelete.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnDelete.setBounds(24, 447, 107, 35);
		contentPane.add(btnDelete);
		
		comboBoxName = new JComboBox();
		comboBoxName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					String query = "SELECT * FROM EmployeeInfo WHERE Name=? ";
					
					PreparedStatement pst = connection.prepareStatement(query);
					pst.setString(1, comboBoxName.getSelectedItem().toString());
					ResultSet rs = pst.executeQuery();
					
					while(rs.next()) {
						
						textFieldEID.setText(rs.getString("EID"));
						textFieldName.setText(rs.getString("Name"));
						textFieldSurname.setText(rs.getString("Surname"));
						textFieldAge.setText(rs.getString("Age"));
						
					}
					
					pst.close();
					
				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
			}
		});
		comboBoxName.setFont(new Font("Tahoma", Font.BOLD, 12));
		comboBoxName.setBounds(24, 36, 162, 48);
		contentPane.add(comboBoxName);
		
		refreshTable();
		fillComboBox();
	}
}
