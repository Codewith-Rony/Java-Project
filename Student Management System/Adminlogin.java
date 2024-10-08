package mini;

import javax.swing.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Adminlogin extends JFrame {

    Adminlogin() {
        setLayout(null);

        JLabel heading = new JLabel("ADMIN LOGIN");
        heading.setBounds(130, 20, 200, 30);
        heading.setFont(new Font("Times New Roman", Font.BOLD, 20));
        add(heading);

        JLabel username = new JLabel("Username:");
        username.setBounds(50, 60, 100, 30);
        add(username);

        JTextField usrname = new JTextField();
        usrname.setBounds(150, 60, 200, 30);
        add(usrname);

        JLabel password = new JLabel("Password:");
        password.setBounds(50, 110, 100, 30);
        add(password);

        JPasswordField pswrd = new JPasswordField();
        pswrd.setBounds(150, 110, 200, 30);
        add(pswrd);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(70, 160, 100, 30);
        add(loginButton);

        JButton Back = new JButton("Back");
        Back.setBounds(250, 160, 100, 30);
        add(Back);

        Back.addActionListener(e ->{
            dispose();
            new Welcome();
        });

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String enteredUsername = usrname.getText();
                String enteredPassword = new String(pswrd.getPassword());

                if (authenticateUser(enteredUsername, enteredPassword)) {
                    JOptionPane.showMessageDialog(null, "Login successful!");
                    dispose();
                    new AdminInterface();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password. Please try again.");
                }
            }
        });

        setSize(450, 250);
        setLocationRelativeTo(null); 
        setVisible(true);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private boolean authenticateUser(String username, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = Connect.getConnect();

            String query = "SELECT * FROM admin WHERE username = ? AND password = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            resultSet = preparedStatement.executeQuery();

            return resultSet.next(); 

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
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
    }

    public static void main(String[] args) {
        new Adminlogin();
    }
}
