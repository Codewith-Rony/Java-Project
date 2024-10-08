package mini;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Mark extends JFrame {
    private JTextField studentIdField;
    private JTextField coMarkField;
    private JTextField javaMarkField;
    private JTextField dbmsMarkField;

    public Mark() {
    	setLayout(null);
        setTitle("Add Marks");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel studentIdLabel = new JLabel("Student ID:");
        studentIdLabel.setBounds(50, 30, 100, 30);
        add(studentIdLabel);

        studentIdField = new JTextField();
        studentIdField.setBounds(150, 30, 150, 30);
        add(studentIdField);

        JLabel coMarkLabel = new JLabel("CO Mark:");
        coMarkLabel.setBounds(50, 80, 100, 30);
        add(coMarkLabel);

        coMarkField = new JTextField();
        coMarkField.setBounds(150, 80, 150, 30);
        add(coMarkField);

        JLabel javaMarkLabel = new JLabel("Java Mark:");
        javaMarkLabel.setBounds(50, 130, 100, 30);
        add(javaMarkLabel);

        javaMarkField = new JTextField();
        javaMarkField.setBounds(150, 130, 150, 30);
        add(javaMarkField);

        JLabel dbmsMarkLabel = new JLabel("DBMS Mark:");
        dbmsMarkLabel.setBounds(50, 180, 100, 30);
        add(dbmsMarkLabel);

        dbmsMarkField = new JTextField();
        dbmsMarkField.setBounds(150, 180, 150, 30);
        add(dbmsMarkField);

        JButton saveButton = new JButton("Save");
        saveButton.setBounds(70, 230, 100, 30);
        add(saveButton);

        saveButton.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                saveMarksToDatabase();
                dispose();
                new TeacherInterface();
            }
        });
        JButton Back = new JButton("Back");
        Back.setBounds(200, 230, 100, 30);
        add(Back);
        
        Back.addActionListener(e ->{
            dispose();
            new TeacherInterface();
        });

        setVisible(true);
    }

    private void saveMarksToDatabase() {
        int studentId = Integer.parseInt(studentIdField.getText());
        int coMark = Integer.parseInt(coMarkField.getText());
        int javaMark = Integer.parseInt(javaMarkField.getText());
        int dbmsMark = Integer.parseInt(dbmsMarkField.getText());

        addMarksToDatabase(studentId, coMark, javaMark, dbmsMark);
    }

    private void addMarksToDatabase(int studentId, int coMark, int javaMark, int dbmsMark) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = Connect.getConnect();

            String query = "UPDATE student SET co_mark = ?, java_mark = ?, dbms_mark = ? WHERE id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, coMark);
            preparedStatement.setInt(2, javaMark);
            preparedStatement.setInt(3, dbmsMark);
            preparedStatement.setInt(4, studentId);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Marks added successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "Failed to add marks.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        } finally {
            try {
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
        new Mark();
    }
}
