package com.dbmi.i2b2Pcori;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.testng.annotations.AfterClass;

public class DatabaseTest {
	 
	 private Connection connection;    
	 private String serverName;     
	 private String portNumber;     
	 private String sid;      
	 private String url;      
	 private String username;     
	 private String password;     
	 private Statement stmt;
	 public ResultSet rset;
	  
	   /* 
	   This constructor initializes the database variables that requires to connect to the database
	     */
	 @Test
	   public DatabaseTest(String serName, String portNum,
	     String osid, String dbUrl, String dbUserName, String dbPassword ) throws IOException
	   {
			   connection = null;
			   serverName = "dbmi-db-dev-01.dbmi.pitt.edu"; //"dbmi-db-dev-01.dbmi.pitt.edu"; // pass server name of IP from the calling class
			     portNumber = "1521"; // // pass port number from the calling classs
			     sid = "dbmi02";  //   // pass oracle service name from the calling classs
			     url = "jdbc:oracle:thin:@" + serverName + ":" + portNumber + ":" + sid;   // "jdbc:oracle:thin:@" + serverName + ":" + portNumber + ":" + sid;
			     username = "dev02i2b2demodata"; //"dev02i2b2demodata";   // pass oracle database name from the calling classs
			     password = "demouser"; //"demouser";   // pass oracle database password (plaintext) from the calling classs
			        
			   }
	   
	   public void OpenDBConnection()
	   {
	     try 
	   {
	   
	     // Load the JDBC driver
	     String driverName = "oracle.jdbc.driver.OracleDriver";
	     Class.forName(driverName);

	     // Create a connection to the database
	       connection = DriverManager.getConnection(url, username, password);
	     }
	     
	      catch (ClassNotFoundException e) 
	    {
	     System.out.println("Class not found from database" );
	     e.printStackTrace();
	    } 
	    catch (SQLException e1) 
	    {
	     System.out.println("ORACLE Connection error " );
	     e1.printStackTrace();
	    }
	         
	     
	   }
	   
	   /* This Function execute the query on the connected database and return the ResultSet collection 
	   Upon callling from the test case class where actual verification is being done on UI and Database.
	     */
	     public ResultSet RunQuery(String Query) throws IOException
	     {
	     
	      try{
	     stmt = connection.createStatement();
	     rset = stmt.executeQuery(Query);
	     
	     }
	      catch(SQLException e1)
	      {
	       System.out.println("Query Execution Error" );
	     e1.printStackTrace();
	      }
	      
	      return rset;
	     }
	         
	         
	          
	   /* This Function closes the database connection from the Oracle Database Connection
	   Upon callling from the test case class where actual verification is being done.
	     */
	     public void OracleCloseConnection() throws IOException
	     {
	      try{
	     connection.close();
	      }
	      
	      catch(SQLException e1)
	      {
	       System.out.println("Query Execution Error" );
	     e1.printStackTrace();
	       
	      }
	      
	   } 
	      
	}