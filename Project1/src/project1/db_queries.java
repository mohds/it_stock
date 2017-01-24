package project1;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

public class db_queries
{
  public List<String> get_types_names()
  {
    connect_to_db connect = new connect_to_db();
    Connection con = connect.connect();
    List<String> types_list = new ArrayList<String>();
    String sql_get_types = "SELECT NAME FROM TYPES";
    try
    {
      Statement stat = con.createStatement();
      ResultSet rs_types = stat.executeQuery(sql_get_types);
      while(rs_types.next()) 
      {
          types_list.add(rs_types.getString(1));
      }
      return types_list;
    }
    catch(Exception e)
    {
      System.out.println(e.toString());
      return null;
    }
  }
  
  public List<String> get_brands_names()
  {
    connect_to_db connect = new connect_to_db();
    Connection con = connect.connect();
    List<String> brands_list = new ArrayList<String>();
    String sql_get_brands = "SELECT NAME FROM BRANDS";
    try
    {
      Statement stat = con.createStatement();
      ResultSet rs_brands = stat.executeQuery(sql_get_brands);
      while(rs_brands.next()) 
      {
          brands_list.add(rs_brands.getString(1));
      }
      return brands_list;
    }
    catch(Exception e)
    {
      System.out.println(e.toString());
      return null;
    }
  }
  
  public List<String> get_locations_names()
  {
    connect_to_db connect = new connect_to_db();
    Connection con = connect.connect();
    List<String> locations_list = new ArrayList<String>();
    String sql_get_locations = "SELECT NAME FROM LOCATIONS";
    try
    {
      Statement stat = con.createStatement();
      ResultSet rs_locations = stat.executeQuery(sql_get_locations);
      while(rs_locations.next()) 
      {
          locations_list.add(rs_locations.getString(1));
      }
      return locations_list;
    }
    catch(Exception e)
    {
      System.out.println(e.toString());
      return null;
    }
  }
  
  public List<String> get_type_specs(String type_name)
  {
    connect_to_db connect = new connect_to_db();
    Connection con = connect.connect();
    List<String> type_specs_list = new ArrayList<String>();
    String sql_get_type_specs = "SELECT SPECS.NAME FROM SPECS,SPECS_TYPES,TYPES WHERE SPECS.ID = SPECS_TYPES.SPEC_ID AND SPECS_TYPES.TYPE_ID = TYPES.ID AND TYPES.NAME = '" + type_name +"'";
    try
    {
      Statement stat = con.createStatement();
      ResultSet rs_type_specs = stat.executeQuery(sql_get_type_specs);
      while(rs_type_specs.next()) 
      {
          type_specs_list.add(rs_type_specs.getString(1));
      }
      return type_specs_list;
    }
    catch(Exception e)
    {
      System.out.println(e.toString());
      return null;
    }
  }
}
