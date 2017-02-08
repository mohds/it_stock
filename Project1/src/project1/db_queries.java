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
  
  public List<String> get_item_conditions()
  {
    connect_to_db connect = new connect_to_db();
    Connection con = connect.connect();
    List<String> conditions_list = new ArrayList<String>();
    String sql_get_conditions = "SELECT NAME FROM ITEM_CONDITIONS";
    try
    {
      Statement stat = con.createStatement();
      ResultSet rs_conditions = stat.executeQuery(sql_get_conditions);
      while(rs_conditions.next()) 
      {
          conditions_list.add(rs_conditions.getString(1));
      }
      return conditions_list;
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
    System.out.println(sql_get_type_specs);
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
  
  public int get_type_id_from_name(String type_name)
  {
    connect_to_db connect = new connect_to_db();
    Connection con = connect.connect();
    String sql_get_id = "Select ID FROM TYPES WHERE NAME = '" + type_name +"'";
    try
    {
      Statement stat = con.createStatement();
      ResultSet rs_type_id = stat.executeQuery(sql_get_id);
      rs_type_id.next();
      return rs_type_id.getInt(1);
    }
    catch(Exception e)
    {
      System.out.println(e.toString());
      return 0;
    }
  }
  
  public int get_brand_id_from_name(String brand_name)
  {
    connect_to_db connect = new connect_to_db();
    Connection con = connect.connect();
    String sql_get_id = "Select ID FROM BRANDS WHERE NAME = '" + brand_name +"'";
    try
    {
      Statement stat = con.createStatement();
      ResultSet rs_brand_id = stat.executeQuery(sql_get_id);
      rs_brand_id.next();
      return rs_brand_id.getInt(1);
    }
    catch(Exception e)
    {
      System.out.println(e.toString());
      return 0;
    }
  }
  
  public int get_location_id_from_name(String location_name)
  {
    connect_to_db connect = new connect_to_db();
    Connection con = connect.connect();
    String sql_get_id = "Select ID FROM LOCATIONS WHERE NAME = '" + location_name +"'";
    try
    {
      Statement stat = con.createStatement();
      ResultSet rs_location_id = stat.executeQuery(sql_get_id);
      rs_location_id.next();
      return rs_location_id.getInt(1);
    }
    catch(Exception e)
    {
      System.out.println(e.toString());
      return 0;
    }
  }
  
  public int get_condition_id_from_name(String condition_name)
  {
    connect_to_db connect = new connect_to_db();
    Connection con = connect.connect();
    String sql_get_id = "Select ID FROM ITEM_CONDITIONS WHERE NAME = '" + condition_name +"'";
    try
    {
      Statement stat = con.createStatement();
      ResultSet rs_condition_id = stat.executeQuery(sql_get_id);
      rs_condition_id.next();
      return rs_condition_id.getInt(1);
    }
    catch(Exception e)
    {
      System.out.println(e.toString());
      return 0;
    }
  }
  
  public int get_spec_id_from_name(String spec_name)
  {
    connect_to_db connect = new connect_to_db();
    Connection con = connect.connect();
    String sql_get_id = "Select ID FROM SPECS WHERE NAME = '" + spec_name +"'";
    try
    {
      Statement stat = con.createStatement();
      ResultSet rs_spec_id = stat.executeQuery(sql_get_id);
      rs_spec_id.next();
      return rs_spec_id.getInt(1);
    }
    catch(Exception e)
    {
      System.out.println(e.toString());
      return 0;
    }
  }
  
  public String get_type_name_from_id(String id)
  {
    connect_to_db connect = new connect_to_db();
    Connection con = connect.connect();
    String sql_get_type= "SELECT NAME FROM TYPES WHERE ID = '" + id +"'";
    try
    {
      Statement stat = con.createStatement();
      ResultSet rs_type_name= stat.executeQuery(sql_get_type);
      rs_type_name.next();
      return rs_type_name.getString(1);
    }
    catch(Exception e)
    {
      System.out.println(e.toString());
      String returned = "0";
      return returned;
    }
  }
  public String get_brand_name_from_id(String id)
  {
    connect_to_db connect = new connect_to_db();
    Connection con = connect.connect();
    String sql_get_brand= "SELECT NAME FROM BRANDS WHERE ID = '" + id +"'";
    try
    {
      Statement stat = con.createStatement();
      ResultSet rs_brand_name= stat.executeQuery(sql_get_brand);
      rs_brand_name.next();
      return rs_brand_name.getString(1);
    }
    catch(Exception e)
    {
      System.out.println(e.toString());
      String returned = "0";
      return returned;
    }
  }
  public String get_location_name_from_id(String id)
  {
    connect_to_db connect = new connect_to_db();
    Connection con = connect.connect();
    String sql_get_location= "SELECT NAME FROM LOCATIONS WHERE ID = '" + id +"'";
    try
    {
      Statement stat = con.createStatement();
      ResultSet rs_location_name= stat.executeQuery(sql_get_location);
      rs_location_name.next();
      return rs_location_name.getString(1);
    }
    catch(Exception e)
    {
      System.out.println(e.toString());
      String returned = "0";
      return returned;
    }
  }
  public String get_item_condition_name_from_id(String id)
  {
    connect_to_db connect = new connect_to_db();
    Connection con = connect.connect();
    String sql_get_condition= "SELECT NAME FROM ITEM_CONDITIONS WHERE ID = '" + id +"'";
    try
    {
      Statement stat = con.createStatement();
      ResultSet rs_condition_name= stat.executeQuery(sql_get_condition);
      rs_condition_name.next();
      return rs_condition_name.getString(1);
    }
    catch(Exception e)
    {
      System.out.println(e.toString());
      String returned = "0";
      return returned;
    }
  }
  public List<String> get_clients_names()
  {
    connect_to_db connect = new connect_to_db();
    Connection con = connect.connect();
    List<String> clients_list = new ArrayList<String>();
    String sql_get_clients = "SELECT NAME FROM CLIENTS";
    try
    {
      Statement stat = con.createStatement();
      ResultSet rs_clients = stat.executeQuery(sql_get_clients);
      while(rs_clients.next()) 
      {
          clients_list.add(rs_clients.getString(1));
      }
      return clients_list;
    }
    catch(Exception e)
    {
      System.out.println(e.toString());
      return null;
    }
  }
  
  public int get_client_id_from_name(String client_name)
  {
    connect_to_db connect = new connect_to_db();
    Connection con = connect.connect();
    
    int client_id = 0;
    String sql_get_id = "SELECT ID FROM CLIENTS WHERE NAME = '" + client_name + "'";
    try
    {
      Statement stat = con.createStatement();
      ResultSet rs_id = stat.executeQuery(sql_get_id);
      rs_id.next();
      client_id = rs_id.getInt(1);
      return client_id;
    }
    catch(Exception e)
    {
      System.out.println(e.toString());
      return 0;
    }
  }
  
  public List<String> get_type_specs_for_item(String type_name,String item_id)
  {
    connect_to_db connect = new connect_to_db();
    Connection con = connect.connect();
    
    List<String> return_list  = new ArrayList<String>();
    List<String> item_specs_list = new ArrayList<String>();
    String sql_get_type_specs = "SELECT SPECS.ID,SPECS.NAME FROM SPECS,SPECS_TYPES,TYPES WHERE SPECS.ID = SPECS_TYPES.SPEC_ID AND SPECS_TYPES.TYPE_ID = TYPES.ID AND TYPES.NAME = '" + type_name +"'";
    System.out.println(sql_get_type_specs);
    String sql_get_item_specs = "SELECT ITEMSPECVALUES.SPEC_ID FROM ITEMSPECVALUES WHERE ITEMSPECVALUES.ITEM_ID = '" + item_id +"'";
    try
    {
      Statement stat1 = con.createStatement();
      Statement stat2 = con.createStatement();
      ResultSet rs_type_specs = stat1.executeQuery(sql_get_type_specs);
      ResultSet rs_item_specs = stat2.executeQuery(sql_get_item_specs);
      while(rs_item_specs.next()) 
      {
          item_specs_list.add(rs_item_specs.getString(1));
      }
      while(rs_type_specs.next())
      {
        if(!item_specs_list.contains(rs_type_specs.getString(1)))
        {
          return_list.add(rs_type_specs.getString(2));
        }
      }
      return return_list;
    }
    catch(Exception e)
    {
      System.out.println(e.toString());
      return null;
    }
  }
}
