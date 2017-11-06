package com.dbmi.i2b2Pcori;

import static org.testng.Assert.fail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class LoginI2b2Pcori {

	  public WebDriver driver;
	  private String baseUrl;
	  public JavascriptExecutor js;
	  public GenericMethods gm;

	  @BeforeClass(alwaysRun = true)
	  public void setUp() throws Exception {
		  System.setProperty("webdriver.gecko.driver", "geckodriver.exe");
			DesiredCapabilities capabilities=DesiredCapabilities.firefox();
			capabilities.setCapability("marionette", true);
			driver = new FirefoxDriver(capabilities);
		  
		  //System.setProperty("webdriver.firefox.marionette", "geckodriver.exe");
	    driver = new FirefoxDriver();
//	    baseUrl = "https://dbmi-ncats-dev01.dbmi.pitt.edu/";
	    baseUrl = "http://dbmi-i2b2-dev02.dbmi.pitt.edu/";
	    
	    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	    driver.manage().window().maximize();
	    js = (JavascriptExecutor) driver;
	    gm = new GenericMethods(driver);
	  }

//	  @Test
	  public void testLogin() throws Exception {
	    driver.get(baseUrl + "webclient/");
	    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
//	    driver.switchTo().frame(0);
	    
	    driver.findElement(By.id("loginusr")).clear();
	    driver.findElement(By.id("loginusr")).sendKeys("demo");
//	    driver.findElement(By.id("loginusr")).sendKeys("demo");
	    driver.findElement(By.id("loginpass")).clear();
//	    driver.findElement(By.id("loginpass")).sendKeys("demouser");
	    driver.findElement(By.id("loginpass")).sendKeys("demouser");
	      
		 // Store the current window handle
	    String winHandleBefore = driver.getWindowHandle();
	    
	    driver.findElement(By.cssSelector("input[type=\"button\"]")).click();
//	    Thread.sleep(3000);

	    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	    
		 // Switch to new window opened
//	    for(String winHandle : driver.getWindowHandles()){
//	        driver.switchTo().window(winHandle);
	    
//	    driver.findElement(By.cssSelector("input[type=\"button\"]")).click();
//	        Boolean isPresent = driver.findElements(By.id("i2b2_projects_modal_dialog_h")).size()!=0;
//	        isPresent.booleanValue();
	        	        

//	    driver.findElement(By.xpath("//div[@id='i2b2_projects_modal_dialog']/div[2]/form/div/input")).click();
//	  }
	    
	 // Switch back to original browser (first window)
//	    driver.switchTo().window(winHandleBefore);
//	    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	    
	    for (int second = 0;; second++) {
	    	if (second >= 60) fail("timeout");
	    	try { if ("Navigate Terms".equals(driver.findElement(By.xpath("//div[@id='tabNavigate']/div")).getText())) break; } catch (Exception e) {}
	    	Thread.sleep(1000); 
	    }
		driver.findElement(By.id("ontZoomImg")).click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	  }
	  protected void diagnodesICD10()
	  {
			driver.findElement(By.xpath(".//*[@title='ICD10 Diagnoses']/div")).click();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	  }
	  protected void diagnodesICD9()
	  {
			driver.findElement(By.xpath(".//*[@title='ICD9 Diagnoses']/div")).click();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	  }
	  protected void labs()
	  {
			driver.findElement(By.xpath(".//*[@title='PATH Laboratory Tests']/div")).click();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	  }
	  
	  protected void MedDrugProduct(){
		    driver.findElement(By.xpath(".//*[@title='PATH Medications']/div")).click();
		    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		    driver.findElement(By.xpath(".//*[@title='\\NCATS\\Medications\\Drug products by va class - NUI:N0000010574']/div")).click();
		    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	  }
	  
	  protected String Connect2DB(String q) throws ClassNotFoundException, SQLException{
//			Load JDBC driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			System.out.println("Driver loaded");
			
//			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@dbmi-db-dev-01.dbmi.pitt.edu:1521:dbmi02", "dev02i2b2demodata", "demouser");
			
//			connection to database
			String Url = "jdbc:oracle:thin:@";
			
			String serverName = "dbmi-db-dev-01.dbmi.pitt.edu";

			String portNumber = "1521";
			
			String sid = "dbmi02";
			
			String dbUrl = Url + serverName + ":" + portNumber + ":" + sid;
			
//			DB user name
//			String userName = RandomSelectActi2b2.getSchema()+"i2b2demodata";
			String userName = "dev02i2b2demodata";
			
//			DB password
			String password = "demouser";
			
			
//			Get Connection to DB
			Connection con = DriverManager.getConnection(dbUrl, userName, password);
			
			System.out.println("connected to database");
			Reporter.log("connected to database");
//			Db Query
//			String query1 = "set timing on";
//			String dbUrl2 = "jdbc:mysql:///test?allowMultiQueries=true"; 
			String query = q; //"select count(distinct patient_num) from OBSERVATION_FACT where CONCEPT_CD IN (select concept_cd from CONCEPT_DIMENSION where concept_path like '\\PATH\\Demographics\\HISPANIC\\Y\\%')";

			
//			Statement for DB connection
			Statement stmt = con.createStatement();
			
//			Send SQL query to DB
//			ResultSet rs1 = stmt.executeQuery(query1);
			long st = System.currentTimeMillis();
			ResultSet rs = stmt.executeQuery(query);
			long et = System.currentTimeMillis();
			long elps = et - st;
			System.out.println("DB Query:" + query);
			System.out.println(elps + " ms");
			Reporter.log("DB Query:" + query);
			String where = null;
			while (rs.next()){
				where = rs.getString(1);
				
				System.out.println(where);
				Reporter.log(where);
			}
//			System.out.println(query2);
			
//			close connection
			con.close();
			
			return where;
		}
	  
	  

	  
	  
	  
//	  Running single sql in JDBC
		  protected String testDB_duration(String qry) throws ClassNotFoundException, SQLException {
			
//			Load JDBC driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			System.out.println("Driver loaded");
			
//			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@dbmi-db-dev-01.dbmi.pitt.edu:1521:dbmi02", "dev02i2b2demodata", "demouser");
			
//			connection to database
			String Url = "jdbc:oracle:thin:@";
			
//			String serverName = "DBMI-DB-DEV-02.DBMI.PITT.EDU";
			String serverName = "dbmi-db-dev-01.dbmi.pitt.edu";

			String portNumber = "1521";
			
//			String sid = "dbmi09";
			String sid = "dbmi02";
			
			String dbUrl = Url + serverName + ":" + portNumber + ":" + sid;
			
//			DB user name
//			String userName = RandomSelectActi2b2.getSchema()+"i2b2demodata";
			String userName = "dev02i2b2demodata";
			
//			DB password
			String password = "demouser";
			
//			Query
			String query = qry;
			
			Connection con = DriverManager.getConnection(dbUrl, userName, password);
			
			System.out.println("connected to database");
			
//			Statement for DB connection
			Statement stmt = con.createStatement();
//			Send SQL query to DB
			long st = System.currentTimeMillis();
//			stmt.setQueryTimeout(180);
			stmt.setQueryTimeout(1800);
			stmt.executeQuery(query);
			
//			to get the number of raws inserted add the end line of insert command and activate Reporter.log("Total raws inserted = "+ rs.getRow());
//			ResultSet rs = stmt.executeQuery(query);
			long et = System.currentTimeMillis();
			long elps = et - st;
			Reporter.log("Query:" + query);
			Reporter.log("Query Duration: " + elps + " ms");
//			Reporter.log("Total raws inserted = "+ rs.getRow());
			String where = null;
//			System.out.println("Query:" + query2);
//			while (rs.next()){
//				String where = rs.getString(1);
//				System.out.println(where);
//			}
//			System.out.println(query2);
			
//			close connection
			con.close();
			return where;
		  }
		  
		  
//		  Running multiple sql in JDBC
		  
		  String testDB_durationMulti(String[] qries) throws ClassNotFoundException, SQLException {
				
//				Load JDBC driver
				Class.forName("oracle.jdbc.driver.OracleDriver");
				
				System.out.println("Driver loaded");
				
//				Connection con = DriverManager.getConnection("jdbc:oracle:thin:@dbmi-db-dev-01.dbmi.pitt.edu:1521:dbmi02", "dev02i2b2demodata", "demouser");
				
//				connection to database
				String Url = "jdbc:oracle:thin:@";
				
//				String serverName = "DBMI-DB-DEV-02.DBMI.PITT.EDU";
				String serverName = "dbmi-db-dev-01.dbmi.pitt.edu";

				String portNumber = "1521";
				
//				String sid = "dbmi09";
				String sid = "dbmi02";
				
				String dbUrl = Url + serverName + ":" + portNumber + ":" + sid;
				
//				DB user name
//				String userName = RandomSelectActi2b2.getSchema()+"i2b2demodata";
				String userName = "dev02i2b2demodata";
				
//				DB password
				String password = "demouser";
				
//				Query
				String[] queries = qries;
				
				Connection con = DriverManager.getConnection(dbUrl, userName, password);
				
				System.out.println("connected to database");
				
				
//				Statement for DB connection
				Statement stmt = con.createStatement();
//				Send SQL query to DB
				long st = System.currentTimeMillis();
				stmt.setQueryTimeout(180);
				String finalQuery = "";
				for(String query : queries){
					stmt.executeQuery(query);
					finalQuery += query;
				}
				
//				ResultSet rs2 = stmt.executeQuery(query2);
				long et = System.currentTimeMillis();
				long elps = et - st;
				Reporter.log("Query:" + finalQuery);
				Reporter.log("Query Duration: " + elps + " ms");
				
				String where = null;
//				System.out.println("Query:" + query2);
//				while (rs.next()){
//					String where = rs.getString(1);
//					System.out.println(where);
//				}
//				System.out.println(query2);
				
//				close connection
				con.close();
				return where;
			  }
	  
	  @AfterClass(alwaysRun = true)
		public void tearDown() throws Exception {
			driver.quit();
		}
}	  
