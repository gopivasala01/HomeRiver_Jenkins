package Florida;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class GetDataFromDB 
{
	public static  void  getChargesFromConfigurationTable() throws Exception
	//public static void main(String[] args)  throws Exception
	{
		String connectionUrl = "jdbc:sqlserver://azrsrv001.database.windows.net;databaseName=HomeRiverDB;user=service_sql02;password=xzqcoK7T";
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
            //Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(connectionUrl);
            String SQL = "Select Charge from [Automation].[ChargeCodesConfiguration]";
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
           // stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);
            int rows =0;
            if (rs.last()) {
            	rows = rs.getRow();
            	// Move to beginning
            	rs.beforeFirst();
            }
            System.out.println("No of Rows - Charges = "+rows);
            InsertDataIntoPropertyWare.charges = new String[rows];
           int  i=0;
            while(rs.next())
            {
            	
            	String 	charge =  (String) rs.getObject(1);
                //String portfolio = (String) rs.getObject(2);
               // String buildingName = (String) rs.getObject(3);
                //String ownerName = (String) rs.getObject(4);
                //System.out.println(charge);
    				//Company
                InsertDataIntoPropertyWare.charges[i] = charge;
    				//Port folio
    				//inProgressLeases[i][1] = portfolio;
    				//Lease Name
    				//inProgressLeases[i][2] = buildingName;
    				//Lease Owner 
    				//inProgressLeases[i][3] = ownerName;
    				i++;
            }	
            System.out.println("Total Pending Leases  = " +InsertDataIntoPropertyWare.charges.length);
            //for(int j=0;j<inProgressLeases.length;j++)
            //{
            //	System.out.println(inProgressLeases[j][j]);
           // }
            rs.close();
            stmt.close();
            con.close();
            
	}
	public static  void  getMoveInCharges() throws Exception
	//public static void main(String[] args)  throws Exception
	{
		String connectionUrl = "jdbc:sqlserver://azrsrv001.database.windows.net;databaseName=HomeRiverDB;user=service_sql02;password=xzqcoK7T";
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
            //Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(connectionUrl);
            String SQL = "Select Charge, ChargeCode,Description,StartDate,Amount from [Automation].[ChargeCodesConfiguration] where MoveInCharge = 1";
            
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
           // stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);
            int rows =0;
            if (rs.last()) {
            	rows = rs.getRow();
            	// Move to beginning
            	rs.beforeFirst();
            }
            System.out.println("No of Rows - Move In Charges = "+rows);
            InsertDataIntoPropertyWare.moveInCharges = new String[rows][5];
           int  i=0;
            while(rs.next())
            {
            	
            	String 	charge =  (String) rs.getObject(1);
                String chargeCode = (String) rs.getObject(2);
                String Description = (String) rs.getObject(3);
                String startDate = (String) rs.getObject(4);
                String amount = (String) rs.getObject(5);
                System.out.println(charge);
    				//Charge
                InsertDataIntoPropertyWare.moveInCharges[i][0] = charge;
    				//Charge Code
                InsertDataIntoPropertyWare.moveInCharges[i][1] = chargeCode;
    				//Description
                InsertDataIntoPropertyWare.moveInCharges[i][2] = Description;
    				//Start Date
                InsertDataIntoPropertyWare.moveInCharges[i][3] = startDate;
                //Amount
                InsertDataIntoPropertyWare.moveInCharges[i][4] = amount;
    				i++;
            }	
            System.out.println("Total Pending Leases  = " +InsertDataIntoPropertyWare.moveInCharges.length);
            //for(int j=0;j<inProgressLeases.length;j++)
            //{
            //	System.out.println(inProgressLeases[j][j]);
           // }
            rs.close();
            stmt.close();
            con.close();
            
	}
	
	public static  void  getAutoCharges() throws Exception
	//public static void main(String[] args)  throws Exception
	{
		String connectionUrl = "jdbc:sqlserver://azrsrv001.database.windows.net;databaseName=HomeRiverDB;user=service_sql02;password=xzqcoK7T";
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
            //Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(connectionUrl);
            String SQL = "Select Charge, ChargeCode,Description,autoCharge_StartDate,Amount,endDate from [Automation].[ChargeCodesConfiguration] where AutoCharge = 1";
            
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
           // stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);
            int rows =0;
            if (rs.last()) {
            	rows = rs.getRow();
            	// Move to beginning
            	rs.beforeFirst();
            }
            System.out.println("No of Rows - Auto Charges = "+rows);
            InsertDataIntoPropertyWare.autoCharges = new String[rows][6];
           int  i=0;
            while(rs.next())
            {
            	
            	String 	charge =  (String) rs.getObject(1);
                String chargeCode = (String) rs.getObject(2);
                String Description = (String) rs.getObject(3);
                String autoCharge_staDate = (String) rs.getObject(4);
                String amount = (String) rs.getObject(5);
                String endDate = (String) rs.getObject(6);
                //System.out.println(charge);
    				//Charge
                InsertDataIntoPropertyWare.autoCharges[i][0] = charge;
    				//Charge Code
                InsertDataIntoPropertyWare.autoCharges[i][1] = chargeCode;
    				//Description
                InsertDataIntoPropertyWare.autoCharges[i][2] = Description;
    				//Start Date
                InsertDataIntoPropertyWare.autoCharges[i][3] = autoCharge_staDate;
                //Amount
                InsertDataIntoPropertyWare.autoCharges[i][4] = amount;
                //End Date
                InsertDataIntoPropertyWare.autoCharges[i][5] = endDate;
    				i++;
            }	
            //System.out.println("Total Pending Leases  = " +InsertDataIntoPropertyWare.moveInCharges.length);
            //for(int j=0;j<inProgressLeases.length;j++)
            //{
            //	System.out.println(inProgressLeases[j][j]);
           // }
            rs.close();
            stmt.close();
            con.close();
            
	}
	

}
