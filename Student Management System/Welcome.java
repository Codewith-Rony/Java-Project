package mini;

import javax.swing.*;
import java.awt.Font;

public class Welcome extends JFrame{
    Welcome(){
        setLayout(null);

        JLabel heading = new JLabel("STUDENT MANAGEMENT SYSTEM");
        heading.setBounds(100, 30, 450, 60);
        heading.setFont(new Font("Times New Roman", Font.BOLD, 25));
        add(heading);

        setSize(600, 300);
        setLocationRelativeTo(null);
        setVisible(true);

        JButton Admin = new JButton("Admin");
        Admin.setBounds(100, 150, 100, 30);
        add(Admin);
        Admin.addActionListener(e ->{
            dispose();
            new Adminlogin();
        });

        JButton Teacher = new JButton("Teacher");
        Teacher.setBounds(250, 150, 100, 30);
        add(Teacher);
        Teacher.addActionListener(e ->{
            dispose();
            new Teacherlogin();
        });

        JButton Student = new JButton("Student");
        Student.setBounds(400, 150, 100, 30);
        add(Student);
        Student.addActionListener(e ->{
            dispose();
            new StudentLogin();
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new Welcome();
    }
}
