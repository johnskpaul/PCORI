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

public class Qry_Pcori_LabElectroSodiumBloodArt extends LoginI2b2Pcori {

	// ·         Laboratory Tests/Chemistry/Electrolytes/Sodium/Sodium in Serum/ Sodium | Blood Arterial
	// or Plasma
	@Test(groups = { "UI_query" })
	public String qryLabElectroSodioSerum() throws Exception {
		testLogin();

		labs();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		// Chemistry
		driver.findElement(By.xpath(".//*[@title='\\LABS\\LP31388-9\\ - LOINC:LP31388-9']/div")).click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		// Electrolytes
		driver.findElement(By.xpath(".//*[@title='\\LABS\\LP31388-9\\LP19403-2\\ - LOINC:LP19403-2']/div")).click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		// Sodium
		driver.findElement(By.xpath(".//*[@title='\\LABS\\LP31388-9\\LP19403-2\\LP15099-2\\ - LOINC:LP15099-2']/div"))
				.click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		// Sodium | Blood Arterial
		WebElement fromElement = driver.findElement(By.xpath(
				".//*[@title='\\LABS\\LP31388-9\\LP19403-2\\LP15099-2\\LP50165-7\\ - LOINC:LP50165-7']/div"));
		WebElement toElement = driver.findElement(By.id("QPD1"));

		Actions action = new Actions(driver);

		// drag and drop
		action.dragAndDrop(fromElement, toElement).build().perform();

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
		if (!qryLabElectroSodioSerum().contains(testDB())) {
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
				+ "where CONCEPT_PATH like '\\LABS\\LP31388-9\\LP19403-2\\LP15099-2\\LP50165-7\\%')");
	}

	// Record DB query duration
	@Test(groups = { "DB_duration" })
	public void DBduration() throws Exception {
		testDB_duration("insert into dev02i2b2demodata.QUERY_GLOBAL_TEMP "
				+ "(patient_num, panel_count) with t as ( select  /*+ index(observation_fact "
				+ "fact_cnpt_pat_enct_idx) */ f.patient_num "
				+ "from dev02i2b2demodata.observation_fact f "
				+ "where f.concept_cd IN (select concept_cd from  "
				+ "dev02i2b2demodata.concept_dimension   "
				+ "where concept_path LIKE '\\LABS\\LP31388-9\\LP19403-2\\LP15099-2\\LP50165-7\\%') "
				+ "group by  f.patient_num ) "
				+ "select  t.patient_num, 0 as panel_count  from t ");

	}
}