package mini;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ChangePassword extends JFrame {
    private JTextField idField;
    private JTextField phoneNumberField;
    private JPasswordField newPasswordField;
    private JButton changePasswordButton;

    public ChangePassword() {
        setTitle("Change Password");
        setLayout(null);

        JLabel idLabel = new JLabel("ID:");
        idLabel.setBounds(50, 30, 100, 30);
        add(idLabel);

        idField = new JTextField();
        idField.setBounds(150, 30, 200, 30);
        add(idField);

        JLabel phoneNumberLabel = new JLabel("Phone Number:");
        phoneNumberLabel.setBounds(50, 70, 100, 30);
        add(phoneNumberLabel);

        phoneNumberField = new JTextField();
        phoneNumberField.setBounds(150, 70, 200, 30);
        add(phoneNumberField);

        JLabel newPasswordLabel = new JLabel("New Password:");
        newPasswordLabel.setBounds(50, 110, 100, 30);
        add(newPasswordLabel);

        newPasswordField = new JPasswordField();
        newPasswordField.setBounds(150, 110, 200, 30);
        add(newPasswordField);

        changePasswordButton = new JButton("Change Password");
        changePasswordButton.setBounds(70, 150, 150, 30);
        add(changePasswordButton);
        
        JButton Back = new JButton("Back");
        Back.setBounds(230, 150, 100, 30);
        add(Back);
        
        Back.addActionListener(e ->{
            dispose();
            new StudentLogin();
        });

        changePasswordButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText();
                String phoneNumber = phoneNumberField.getText();
                String newPassword = new String(newPasswordField.getPassword());

                boolean success = changePassword(id, phoneNumber, newPassword);
                if (success) {
                    JOptionPane.showMessageDialog(null, "Password changed successfully!");
                    dispose();
                    new StudentLogin();
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to change password. Please check your ID and phone number.");
                }
            }
        });
        
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private boolean changePassword(String id, String phoneNumber, String newPassword) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = Connect.getConnect();

            String query = "SELECT phone_number FROM student WHERE ID = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String dbPhoneNumber = resultSet.getString("phone_number");
                if (dbPhoneNumber.equals(phoneNumber)) {
                    String updateQuery = "UPDATE student SET password = ? WHERE ID = ?";
                    preparedStatement = connection.prepareStatement(updateQuery);
                    preparedStatement.setString(1, newPassword);
                    preparedStatement.setString(2, id);

                    int rowsAffected = preparedStatement.executeUpdate();
                    if (rowsAffected > 0) {
                        return true; 
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            // Close resources
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return false; 
    }

    public static void main(String[] args) {
        new ChangePassword();
    }
}
