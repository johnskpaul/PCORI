package com.dbmi.i2b2Pcori;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.testng.annotations.AfterClass;

public class DBTest {
	@Test
	public void testDB() throws ClassNotFoundException, SQLException {
		
//		Load JDBC driver
		Class.forName("oracle.jdbc.driver.OracleDriver");
		
		System.out.println("Driver loaded");
		
//		Connection con = DriverManager.getConnection("jdbc:oracle:thin:@dbmi-db-dev-01.dbmi.pitt.edu:1521:dbmi02", "dev02i2b2demodata", "demouser");
		
//		connection to database
		String Url = "jdbc:oracle:thin:@";
		
		String serverName = "dbmi-db-dev-02.dbmi.pitt.edu";
//		String serverName = "dbmi-db-dev-01.dbmi.pitt.edu";

		String portNumber = "1521";
		
		String sid = "dbmi09";
//		String sid = "dbmi02";
		
		String dbUrl = Url + serverName + ":" + portNumber + ":" + sid;
		
//		DB user name
		String userName = "ncats2i2b2demodata";
//		String userName = "dev02i2b2demodata";
		
//		DB password
		String password = "demouser";
		
//		Db Query
		
//		String query = "select count(distinct patient_num) from OBSERVATION_FACT where CONCEPT_CD IN (select concept_cd from CONCEPT_DIMENSION where concept_path like '\\ACT\\Demographics\\Vital Status\\Known Deceased\\%')";

//		String query2 =	"set timing on"; 
		String query = 
//				"select count (distinct patient_num)from observation_fact where concept_cd IN (select CONCEPT_CD from concept_dimension where CONCEPT_PATH like '\\Diagnoses\\(J00-J99) Dise~45pn\\(J40-J47) Chro~fbox\\(J45) Asthma\\%') AND  START_DATE between to_date('01-JAN-15') AND to_date('01-JAN-17')"
//				"select count(distinct patient_num) from observation_fact where concept_cd IN (select CONCEPT_CD from concept_dimension where CONCEPT_PATH like '\\ICD10PCS_2015AA\\(4) Measuremen~ge9w\\ (4A) Measureme~dxgz\\ (4A1) Measurem~49bf\\(4A1H) Measure~3za2\\(4A1HX) Measur~u533\\(4A1HXC) Measu~b03c\\%')"
				"select count(distinct patient_num ) from observation_fact "
				+ "where concept_cd IN (select CONCEPT_CD from concept_dimension "
				+ "where CONCEPT_PATH like '\\PATH\\PROCEDURE\\09\\(42-54.99) Ope~bzgq\\(50) Operation~5uiv\\(50.5) Liver t~mdf7\\%'"
				+ "AND CONCEPT_PATH like '\\ICD10CM_2015AA\\(J00-J99) Dise~45pn\\(J40-J47) Chro~fbox\\(J45) Asthma\\%')"
				;
		
//		Load JDBC driver
//		Class.forName("com.mysql.jdbc.Driver");
		
//		Get Connection to DB
		Connection con = DriverManager.getConnection(dbUrl, userName, password);
		
		System.out.println("connected to database");
		
//		Statement for DB connection
		Statement stmt = con.createStatement();
		
//		Send SQL query to DB
		ResultSet rs = stmt.executeQuery(query);
//		ResultSet rs2 = stmt.executeQuery(query2);
		System.out.println("Query:" + query);
//		System.out.println("Query:" + query2);
		while (rs.next()){
			String where = rs.getString(1);
			System.out.println(where);
		}
//		System.out.println(query2);
		
//		close connection
		con.close();
		
	}
}
