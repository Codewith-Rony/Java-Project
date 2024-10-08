package mini;

import javax.swing.*;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentInterface extends JFrame {
    private String studentID;

    public StudentInterface(String studentID) {
        this.studentID = studentID; 

        setLayout(null);
        
        JLabel heading = new JLabel("STUDENT DASHBOARD");
        heading.setBounds(180, 7, 250, 100);
        heading.setFont(new Font("Times New Roman", Font.BOLD, 20));
        add(heading);
        
        JButton viewMarksButton = new JButton("View Marks");
        viewMarksButton.setBounds(100, 100, 150, 30);
        add(viewMarksButton);
        viewMarksButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewMarks();
            }
        });

        JButton changePasswordButton = new JButton("Change Password");
        changePasswordButton.setBounds(300, 100, 150, 30);
        add(changePasswordButton);
        changePasswordButton.addActionListener(e -> {
            setVisible(false); 
            new ChangePassword();
        });
        
        JButton backButton = new JButton("Back");
        backButton.setBounds(250, 160, 100, 30);
        add(backButton);

        backButton.addActionListener(e -> {
            dispose();
            new StudentLogin();
        });

        setSize(600, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void viewMarks() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = Connect.getConnect();

            String query = "SELECT co_mark, java_mark, dbms_mark FROM student WHERE ID = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, studentID);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int coMark = resultSet.getInt("co_mark");
                int javaMark = resultSet.getInt("java_mark");
                int dbmsMark = resultSet.getInt("dbms_mark");
                String message = "CO Mark: " + coMark + "\nJava Mark: " + javaMark + "\nDBMS Mark: " + dbmsMark;

                if (coMark < 50 || javaMark < 50 || dbmsMark < 50) {
                    message += "\nGrade: Fail";
                } else {
                    message += "\nGrade: Pass";
                }
                JOptionPane.showMessageDialog(null, message);
            } else {
                JOptionPane.showMessageDialog(null, "No marks found for the student ID: " + studentID);
            }
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

    public static void main(String[] args) {      
        String studentID =""; 
        new StudentInterface(studentID);
    }
}
