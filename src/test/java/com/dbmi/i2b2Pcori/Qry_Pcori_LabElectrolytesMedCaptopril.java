package com.dbmi.i2b2Pcori;

import org.testng.annotations.Test;

import static org.testng.Assert.fail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;

public class Qry_Pcori_LabElectrolytesMedCaptopril extends LoginI2b2Pcori {

	// ·         Laboratory Tests/Chemistry/Electrolytes/Sodium/Sodium in Serum
	// or Plasma
	@Test(groups = { "UI_query" })
	public String qryLabElectrolytes() throws Exception {
		testLogin();

		labs();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		// Chemistry
		driver.findElement(By.xpath(".//*[@title='\\LABS\\LP31388-9\\ - LOINC:LP31388-9']/div")).click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		// Electrolytes
		WebElement fromElement = driver.findElement(By.xpath(
				".//*[@title='\\LABS\\LP31388-9\\LP19403-2\\ - LOINC:LP19403-2']/div"));
		WebElement toElement = driver.findElement(By.id("QPD1"));

		Actions action = new Actions(driver);

		// drag and drop
		action.dragAndDrop(fromElement, toElement).build().perform();

//		Medications/ Drug products by VA Class/Cardiovascular Medications/Ace Inhibitors/Captopril
		
		// Scroll down
				js.executeScript("window.scrollBy(0, 1900);");
				Thread.sleep(2000);
				WebElement element = driver.findElement(By.xpath(
						".//*[@title='PATH Medications']/div"));
				js.executeScript("arguments[0].scrollIntoView(true);", element);
				Thread.sleep(2000);
				js.executeScript("window.scrollBy(0, -190);");
		
		MedDrugProduct();
//		Cardiovascular
		driver.findElement(By
				.xpath(".//*[@title='\\PATH\\Medications\\Drug Products by VA Class\\Cardiovascular medications - NUI:N0000029116']/div"))
				.click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		// Scroll down
				js.executeScript("window.scrollBy(0, 1900);");
				Thread.sleep(2000);
				WebElement element2 = driver.findElement(By.xpath(
						".//*[@title='\\PATH\\Medications\\Drug Products by VA Class\\Cardiovascular medications\\Ace inhibitors - NUI:N0000029130']/div"));
				js.executeScript("arguments[0].scrollIntoView(true);", element2);
				Thread.sleep(2000);
				js.executeScript("window.scrollBy(0, -190);");
		
//		Ace Inhibitors
		driver.findElement(By
				.xpath(".//*[@title='\\PATH\\Medications\\Drug Products by VA Class\\Cardiovascular medications\\Ace inhibitors - NUI:N0000029130']/div"))
				.click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		// Scroll down
		js.executeScript("window.scrollBy(0, 1900);");
		Thread.sleep(2000);
		WebElement element3 = driver.findElement(By.xpath(
				".//*[@title='\\PATH\\Medications\\Drug Products by VA Class\\Cardiovascular medications\\Ace inhibitors\\Captopril - RXNORM:1998']/div"));
		js.executeScript("arguments[0].scrollIntoView(true);", element3);
		Thread.sleep(2000);
		js.executeScript("window.scrollBy(0, -190);");

		WebElement fromElement2 = driver.findElement(By.xpath(
				".//*[@title='\\PATH\\Medications\\Drug Products by VA Class\\Cardiovascular medications\\Ace inhibitors\\Captopril - RXNORM:1998']/div"));
		WebElement toElement2 = driver.findElement(By.id("QPD2"));

		Actions action2 = new Actions(driver);

		// drag and drop
		action2.dragAndDrop(fromElement2, toElement2).build().perform();
		// storing current window
		String winHandleBefore = driver.getWindowHandle();

		driver.findElement(By.id("runBoxText")).click();
		Thread.sleep(2000);

		// Switch to new window opened
		for (String winHandle : driver.getWindowHandles()) {
			driver.switchTo().window(winHandle);
		}

		driver.findElement(By.xpath("//button[contains(@id, 'yui') and text()='OK']")).click();
		Thread.sleep(100000);

		driver.findElement(By.id("qtPrintImg")).click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		// Switch to new window opened
		for (String winHandle : driver.getWindowHandles()) {
			driver.switchTo().window(winHandle);
		}
		String string = driver.findElement(By.id("shrinePrintQueryPage")).getText();

		driver.findElement(By.id("shrinePrintQueryPage")).getText();
		System.out.println("UI Query Resulst: " + string);
		Reporter.log("UI Query Resulst:" + string);

		// Switch to new window opened
		for (String winHandle : driver.getWindowHandles()) {
			driver.switchTo().window(winHandle);
		}
		return string;

	}

	@AfterClass(alwaysRun = true)
	public void tearDown() throws Exception {
		driver.quit();
	}

	// Compare UI and DB counts
	@Test(groups = { "UI_DB_Comparison" })
	public void testCompare() throws Exception {
		if (!qryLabElectrolytes().contains(testDB())) {
			throw new Exception("Comparison Result: Numbers Do Not Match");
		} else {
			Reporter.log("Comparison Result: Numbers Matched!!!");
		}
	};

	// SQL query in database
	@Test(groups = { "DB_query" })
	public String testDB() throws ClassNotFoundException, SQLException {
		return Connect2DB("select count(distinct patient_num) from observation_fact "
				+ "where concept_cd IN (select CONCEPT_CD from concept_dimension "
				+ "where CONCEPT_PATH like '\\LABS\\LP31388-9\\LP19403-2\\%') "
				+ "AND"
				+ "CONCEPT_PATH like "
				+ "'\\PATH\\Medications\\N0000010574\\N0000029116\\N0000029130\\1998\\%')"
				);
	}

	// Record DB query duration
	@Test(groups = { "DB_duration" })
	public void DBduration() throws Exception {
		String[] qries = {
				"insert into dev02i2b2demodata.QUERY_GLOBAL_TEMP (patient_num, panel_count) "
				+ "with t as ( select  /*+ index(observation_fact fact_cnpt_pat_enct_idx) */ f.patient_num "
				+ "from dev02i2b2demodata.observation_fact f "
				+ "where "
				+ "f.concept_cd IN (select concept_cd from  dev02i2b2demodata.concept_dimension   "
				+ "where concept_path LIKE '\\PATH\\Medications\\N0000010574\\N0000029116\\N0000029130\\1998\\%') "
				+ "group by  f.patient_num ) "
				+ "select  t.patient_num, 0 as panel_count  from t ",
				 "update dev02i2b2demodata.QUERY_GLOBAL_TEMP set panel_count =1 where "
				 + "dev02i2b2demodata.QUERY_GLOBAL_TEMP.panel_count =  0 "
				 + "and exists ( select 1 from ( select  /*+ index(observation_fact fact_cnpt_pat_enct_idx) "
				 + "*/ f.patient_num "
				 + "from dev02i2b2demodata.observation_fact f "
				 + "where "
				 + "f.concept_cd IN (select concept_cd from  dev02i2b2demodata.concept_dimension   "
				 + "where concept_path LIKE '\\LABS\\LP31388-9\\LP19403-2\\%') "
				 + "group by  f.patient_num ) t where dev02i2b2demodata.QUERY_GLOBAL_TEMP.patient_num = t.patient_num    )",
				  "select * from ( select distinct  patient_num  from dev02i2b2demodata.QUERY_GLOBAL_TEMP where panel_count = 1 ) q"
				};
testDB_durationMulti(qries); 
	
}
}