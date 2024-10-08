package mini;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Teacherlogin extends JFrame {

    Teacherlogin() {
        setLayout(null);
        setTitle("Teacher Login");

        JLabel heading = new JLabel("TEACHER LOGIN");
        heading.setBounds(120, 20, 200, 30);
        heading.setFont(new Font("Times New Roman", Font.BOLD, 20));
        add(heading);

        JLabel username = new JLabel("Phone number:");
        username.setBounds(50, 80, 100, 30);
        add(username);

        JTextField usrname = new JTextField();
        usrname.setBounds(150, 80, 200, 30);
        usrname.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (usrname.getText().length() > 10) 
                    e.consume();
            }
        });
        add(usrname);

        JLabel password = new JLabel("Password:");
        password.setBounds(50, 130, 100, 30);
        add(password);

        JPasswordField pswrd = new JPasswordField();
        pswrd.setBounds(150, 130, 200, 30);
        add(pswrd);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(70, 210, 100, 30);
        add(loginButton);

        JButton Back = new JButton("Back");
        Back.setBounds(240, 210, 100, 30);
        add(Back);

        Back.addActionListener(e -> {
            dispose();
            new Welcome();
        });

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                String phone = usrname.getText();
                String password = new String(pswrd.getPassword());
                if (authenticateTeacher(phone, password)) {
                    JOptionPane.showMessageDialog(null, "Login successful!");
                    dispose();
                    new TeacherInterface();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid phone number or password. Please try again.");
                }
            }
        });

        setSize(400, 350);
        setLocationRelativeTo(null);
        setVisible(true);

        JLabel newuser = new JLabel("New user? Click here");
        newuser.setBounds(150, 260, 150, 30);
        add(newuser);

        newuser.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        newuser.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                dispose();
                new NewTeacher();
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private boolean authenticateTeacher(String phoneNumber, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = Connect.getConnect();

            String query = "SELECT * FROM teacher WHERE phone_no = ? AND password = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, phoneNumber);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
            
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
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
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Teacherlogin();
    }
}
