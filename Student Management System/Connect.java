package mini;

import java.sql.*;
public class Connect
{
    private static Connection co;
    public static Connection getConnect()
    {
        try
        {
            String url="jdbc:mysql://localhost:3306/java";
            String username="root";
            String password="Rony@4207";
            co=DriverManager.getConnection(url, username, password);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return co;
    }
    public static void connectClose()
    {
        try
        {
            co.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}