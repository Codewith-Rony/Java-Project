package mini;

import javax.swing.*;

import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class AddStudent extends JFrame {

    JTextField nameField, idField, phoneField, passwordField;

    AddStudent() {
        setLayout(null);

        setTitle("Student Registration Form");
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(100, 50, 100, 30);
        add(nameLabel);
        nameField = new JTextField();
        nameField.setBounds(220, 50, 200, 30);
        add(nameField);

        JLabel phoneLabel = new JLabel("Phone Number:");
        phoneLabel.setBounds(100, 100, 100, 30);
        add(phoneLabel);
        phoneField = new JTextField();
        phoneField.setBounds(220, 100, 200, 30);
        phoneLabel.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (phoneLabel.getText().length() > 10) 
                    e.consume();
            }
        });
        add(phoneField);
        
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(100, 150, 100, 30); 
        add(passwordLabel);
        
        passwordField = new JTextField();
        passwordField.setBounds(220, 150, 200, 30); 
        add(passwordField);
        
        JButton addButton = new JButton("Add Student");
        addButton.setBounds(100, 200, 150, 30);
        add(addButton);
        
        JButton Back = new JButton("Back");
        Back.setBounds(300, 200, 150, 30);
        add(Back);
        
        Back.addActionListener(e ->{
            dispose();
            new TeacherInterface();
        });
        
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String phoneNumber = phoneField.getText();
                String password = passwordField.getText(); 

                addStudent(name, phoneNumber, password);
            }
        });

        setSize(600, 300);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void addStudent(String name, String phoneNumber, String password) {
        Connection connection = null;
        PreparedStatement insertStatement = null;
        ResultSet resultSet = null;

        try {
            connection = Connect.getConnect();

            String insertQuery = "INSERT INTO student (name, phone_number, password) VALUES (?, ?, ?)";
            insertStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setString(1, name);
            insertStatement.setString(2, phoneNumber);
            insertStatement.setString(3, password); // Set password parameter

            int rowsAffected = insertStatement.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Student added successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "Failed to add student.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (insertStatement != null) {
                    insertStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error while closing database connection: " + ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new AddStudent();
    }
}
