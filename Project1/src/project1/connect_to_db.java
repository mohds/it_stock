package project1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connect_to_db
{
    public Connection connect()
    {
        try
        {   
        Connection con = DriverManager.getConnection("jdbc:oracle:thin:ITSTOCK/itst0ck@140.125.4.30:1522:xe");
        return con;
        }
        catch(SQLException e)
        {
        System.out.println(e.toString());
        return null;
        }
        //super();
    }
}