package mini;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class AdminInterface extends JFrame {
	AdminInterface(){
		
		
        JLabel heading = new JLabel("Admin Dashboard");
        heading.setBounds(200, 20, 200, 30);
        heading.setFont(new Font("Arial", Font.BOLD, 20));
        add(heading);
        
        JButton viewt = new JButton("View Teachers");
        viewt.setBounds(100, 100, 150, 30);
        add(viewt);
        viewt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {               
                populateTeacherTable();
            }
        });
        
        JButton views = new JButton("View Students");
        views.setBounds(350, 100, 150, 30);
        add(views);
        
        views.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { 
                populateStudentTable();
            }
        });
        
        JButton Back = new JButton("Back");
        Back.setBounds(250, 160, 100, 30);
        add(Back);

        Back.addActionListener(e ->{
            dispose();
            new Welcome();
        });
        setLayout(null);
        setSize(600, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setVisible(true);
        
	}
	
	private void populateStudentTable() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = Connect.getConnect();          
            String query = "SELECT * FROM student";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            String[] columnNames = {"ID", "Name", "Phone Number"};
            DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

            while (resultSet.next()) {
                Object[] rowData = {
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("phone_number")
                };
                tableModel.addRow(rowData);
            }

            JTable table = new JTable(tableModel);
            JOptionPane.showMessageDialog(null, new JScrollPane(table), "Student Details", JOptionPane.PLAIN_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error while closing database connection: " + ex.getMessage());
            }
        }
    }
	
	private void populateTeacherTable() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // Establish database connection
            connection = Connect.getConnect();

            // Prepare SQL query to select all student details from the database
            String query = "SELECT * FROM teacher";
            preparedStatement = connection.prepareStatement(query);

            // Execute the query
            resultSet = preparedStatement.executeQuery();

            // Create table model with column names
            String[] columnNames = {"ID", "Name", "Phone Number"};
            DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

            // Populate table model with student data
            while (resultSet.next()) {
                Object[] rowData = {
                		resultSet.getString("name"),
                        resultSet.getString("department"),
                        resultSet.getString("phone_no")
                        
                };
                tableModel.addRow(rowData);
            }

            // Create and display JTable with the populated table model
            JTable table = new JTable(tableModel);
            JOptionPane.showMessageDialog(null, new JScrollPane(table), "Teacher Details", JOptionPane.PLAIN_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        } finally {
            // Close resources
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error while closing database connection: " + ex.getMessage());
            }
        }
    }
public static void main(String args[]) {
	new AdminInterface();
}
	
}
