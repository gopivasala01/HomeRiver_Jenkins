package mainPackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

  public class InsertDataIntoDatabase
  {
	  public static void insertData(String buildingName, String status, int statusID) throws Exception
	  {

		  String connectionUrl = "jdbc:sqlserver://azrsrv001.database.windows.net;databaseName=HomeRiverDB;user=service_sql02;password=xzqcoK7T";
		  String sql;
		  if(statusID==1)
		   sql = "Update [Automation].[LeaseInfo] Set Status ='"+status+"', StatusID="+statusID+",NotAutomatedFields=NULL where BuildingName like '%"+buildingName+"%'";
		  else 
			sql = "Update [Automation].[LeaseInfo] Set Status ='"+status+"', StatusID="+statusID+" where BuildingName like '%"+buildingName+"%'";
            //String sql = "Update [Automation].[LeaseInfo] Set Status = 'Completed', StatusID =4 where OwnerName='Duff, V.'";
		    try (Connection conn = DriverManager.getConnection(connectionUrl);
		        Statement stmt = conn.createStatement();) 
		    {
		      stmt.executeUpdate(sql);
		      System.out.println("Database updated successfully ");
		    } catch (SQLException e) {
		      e.printStackTrace();
		    }
	  }
	  public static void notAutomatedFields(String buildingName, String nextValue) throws Exception
	  {

		  String connectionUrl = "jdbc:sqlserver://azrsrv001.database.windows.net;databaseName=HomeRiverDB;user=service_sql02;password=xzqcoK7T";
		  String sql = "Update [Automation].[LeaseInfo] Set NotAutomatedFields =CONCAT((Select top 1 NotAutomatedFields from Automation.LeaseInfo where  BuildingName like '%"+buildingName+"%'),',"+nextValue+"') where BuildingName like '%"+buildingName+"%'";
		    

		    try (Connection conn = DriverManager.getConnection(connectionUrl);
		        Statement stmt = conn.createStatement();) 
		    {
		      stmt.executeUpdate(sql);
		      System.out.println("Not Automated Field Updates = "+nextValue);
		    } catch (SQLException e) {
		      e.printStackTrace();
		    }
		    RunnerClass.leaseCompletedStatus = 3;
	  }
	  public static void insertPropertyWareURL(String buildingName, String URL) throws Exception
	  {

		  String connectionUrl = "jdbc:sqlserver://azrsrv001.database.windows.net;databaseName=HomeRiverDB;user=service_sql02;password=xzqcoK7T";
		    String sql = "update [Automation].[LeaseInfo] Set PropertyWareURL ='"+URL+"' where BuildingName like  '%"+buildingName+"%'";

		    try (Connection conn = DriverManager.getConnection(connectionUrl);
		        Statement stmt = conn.createStatement();) 
		    {
		      stmt.executeUpdate(sql);
		      System.out.println("URL Added in the column");
		    } catch (SQLException e) {
		      e.printStackTrace();
		    }
	  }
	 
  }
