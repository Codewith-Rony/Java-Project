package mini;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RemoveStudent extends JFrame {
    private JTextField idField;

    public RemoveStudent() {
        setLayout(null);

        JLabel idLabel = new JLabel("Enter ID:");
        idLabel.setBounds(50, 50, 100, 30);
        add(idLabel);

        idField = new JTextField();
        idField.setBounds(150, 50, 200, 30);
        add(idField);

        JButton removeButton = new JButton("Remove");
        removeButton.setBounds(150, 100, 100, 30);
        add(removeButton);
        
        JButton Back = new JButton("Back");
        Back.setBounds(300, 100, 100, 30);
        add(Back);
        
        Back.addActionListener(e ->{
            dispose();
            new TeacherInterface();
        });

        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText();
                if (!id.isEmpty()) {
                    if (deleteStudent(id)) {
                        JOptionPane.showMessageDialog(null, "Student removed successfully!");
                        idField.setText(""); 
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to remove student.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter a student ID.");
                }
            }
        });

        setSize(500, 200);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private boolean deleteStudent(String id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = Connect.getConnect();

            String query = "DELETE FROM student WHERE id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, id);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; 
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            return false;
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
                JOptionPane.showMessageDialog(null, "Error while closing database connection: " + ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new RemoveStudent();
    }
}

