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
        Connection con = DriverManager.getConnection("jdbc:oracle:thin:C##ITSTOCK/itst0ck@10.10.10.5:1521:orcl");
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