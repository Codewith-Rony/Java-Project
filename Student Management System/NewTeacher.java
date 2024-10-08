package mini;

import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class NewTeacher extends JFrame {

    JTextField nameField = new JTextField();
    JComboBox<String> departmentComboBox = new JComboBox<>(new String[]{"Information Technology", "Computer Science", "Artificial Intelligence", "Mechanical", "Electronics and Communication"});
    JTextField phoneField = new JTextField();
    JTextField passwordField = new JTextField();

    NewTeacher() {
        setTitle("Teacher Registration Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 30, 100, 30);
        add(nameLabel);
        nameField.setBounds(150, 30, 200, 30);
        add(nameField);

        JLabel departmentLabel = new JLabel("Department:");
        departmentLabel.setBounds(50, 80, 100, 30);
        add(departmentLabel);
        departmentComboBox.setBounds(150, 80, 200, 30);
        add(departmentComboBox);

        JLabel phoneLabel = new JLabel("Phone Number:");
        phoneLabel.setBounds(50, 130, 100, 30);
        add(phoneLabel);
        phoneField.setBounds(150, 130, 200, 30);
        phoneField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
               
                if (!Character.isDigit(e.getKeyChar()) || phoneField.getText().length() > 10) {
                    e.consume(); 
                }
            }
        });

        add(phoneField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 180, 100, 30);
        add(passwordLabel);
        passwordField.setBounds(150, 180, 200, 30);
        add(passwordField);

        JButton saveButton = new JButton("Save");
        saveButton.setBounds(70, 230, 100, 30);
        add(saveButton);

        JButton Back = new JButton("Back");
        Back.setBounds(220, 230, 100, 30);
        add(Back);

        Back.addActionListener(e -> {
            dispose();
            new Teacherlogin();
        });

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String department = (String) departmentComboBox.getSelectedItem();
                String phone = phoneField.getText();
                String password = passwordField.getText();

                saveToDatabase(name, department, phone, password);
            }
        });

        setSize(400, 350);
        setLocationRelativeTo(null);
        setLayout(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new NewTeacher();
    }

    public static void saveToDatabase(String name, String department, String phoneNumber, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = Connect.getConnect();

            String query = "INSERT INTO teacher (name, department, phone_no, password) VALUES (?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, department);
            preparedStatement.setString(3, phoneNumber);
            preparedStatement.setString(4, password);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "New teacher details saved to database successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "Failed to save new teacher details to database.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error while closing database connection: " + e.getMessage());
            }
        }
    }
}
