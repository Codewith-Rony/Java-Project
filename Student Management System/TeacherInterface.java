package mini;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.Font;

public class TeacherInterface extends JFrame {
    private DefaultTableModel tableModel;

    public TeacherInterface() {
        setLayout(null);
        setSize(650, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JLabel heading = new JLabel("Teacher Dashboard");
        heading.setBounds(240, 20, 200, 30);
        heading.setFont(new Font("Arial", Font.BOLD, 20));
        add(heading);

        JButton add = new JButton("Add Student");
        add.setBounds(100, 100, 150, 30);
        add(add);

        add.addActionListener(e -> {
            //setVisible(false); 
            new AddStudent();
        });

        JButton delete = new JButton("Delete Student");
        delete.setBounds(100, 200, 150, 30);
        add(delete);

        delete.addActionListener(e -> {
           // setVisible(false); 
            new RemoveStudent();
        });
       

        JButton view = new JButton("View Students");
        view.setBounds(350, 100, 150, 30);
        add(view);

        view.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                populateStudentTable();
            }
        });
        
        
        JButton mark = new JButton("Add Mark");
        mark.setBounds(350, 200, 150, 30);
        add(mark);

        mark.addActionListener(e -> {
           // setVisible(false); 
            new Mark();
        });

        JButton Back = new JButton("Back");
        Back.setBounds(250, 250, 100, 30);
        add(Back);

        Back.addActionListener(e ->{
            dispose();
            new Welcome();
        });
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
            tableModel = new DefaultTableModel(columnNames, 0);

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

    public static void main(String[] args) {
        new TeacherInterface();
    }
}
