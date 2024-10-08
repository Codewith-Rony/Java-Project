package mini;

import javax.swing.*;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentLogin extends JFrame {
    StudentLogin() {
        setLayout(null);

        JLabel heading = new JLabel("STUDENT LOGIN");
        heading.setBounds(130, 20, 200, 30);
        heading.setFont(new Font("Times New Roman", Font.BOLD, 20));
        add(heading);

        JLabel idLabel = new JLabel("ID:");
        idLabel.setBounds(50, 60, 100, 30);
        add(idLabel);

        JTextField idField = new JTextField();
        idField.setBounds(150, 60, 200, 30);
        add(idField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 110, 100, 30);
        add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(150, 110, 200, 30);
        add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(70, 160, 100, 30);
        add(loginButton);
        
        JButton backButton = new JButton("Back");
        backButton.setBounds(250, 160, 100, 30);
        add(backButton);

        backButton.addActionListener(e -> {
            dispose();
            new Welcome();
        });

        JLabel changePasswordLabel = new JLabel("Change password? Click here");
        changePasswordLabel.setBounds(130, 190, 170, 50);
        add(changePasswordLabel);

        changePasswordLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        changePasswordLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                dispose();
                new ChangePassword();
            }
        });

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText();
                String password = new String(passwordField.getPassword());

                if (authenticate(id, password)) {
                    JOptionPane.showMessageDialog(null, "Login successful!");
                    dispose();
                    new StudentInterface(id);
                    
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid ID or password. Please try again.");
                }
            }
        });

        setSize(450, 300);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private boolean authenticate(String id, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = Connect.getConnect();

            String query = "SELECT * FROM student WHERE ID = ? AND password = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, password);

            resultSet = preparedStatement.executeQuery();

            return resultSet.next();
        } catch (SQLException ex) {
            ex.printStackTrace();
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
                ex.printStackTrace();
            }
        }

        return false; 
    }

    public static void main(String args[]) {
        new StudentLogin();
    }
}
