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

public class Qry_Pcori_ICD9OperLiverTransPlant extends LoginI2b2Pcori {

	@Test(groups = { "UI_query" })
	public String qryOperLiverTransPlant() throws Exception {
		testLogin();

		Reporter.log("Query: Procedures/Operations on the digestive system/Operations on liver/Liver transplant");
		// Demographics
		driver.findElement(By.xpath(".//*[@title='ICD9 Procedures']/div")).click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		driver.findElement(By
				.xpath(".//*[@title='ICD9CM \\ Procedures \\ Operations on the digestive system - ICD9:42-54.99']/div"))
				.click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		driver.findElement(By
				.xpath(".//*[@title='ICD9CM \\ Procedures \\ Operations on the digestive system \\ Operations on liver - ICD9:50']/div"))
				.click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		WebElement fromElement = driver.findElement(By.xpath(
				".//*[@title='ICD9CM \\ Procedures \\ Operations on the digestive system \\ Operations on liver \\ Liver transplant - ICD9:50.5']/div"));
		WebElement toElement = driver.findElement(By.id("QPD1"));

		Actions action = new Actions(driver);

		// drag and drop
		action.dragAndDrop(fromElement, toElement).build().perform();

		// storing current window
		String winHandleBefore = driver.getWindowHandle();

		driver.findElement(By.id("runBoxText")).click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		// Switch to new window opened
		for (String winHandle : driver.getWindowHandles()) {
			driver.switchTo().window(winHandle);
		}
		for (int second = 0;; second++) {
			if (second >= 60)
				fail("timeout");
			try {
				if ("Run Query".equals(driver.findElement(By.id("dialogQryRun_h")).getText()))
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}
		driver.findElement(By.xpath("//button[contains(@id, 'yui') and text()='OK']")).click();
		Thread.sleep(200000);

		// Switch back to original browser (first window)
		driver.switchTo().window(winHandleBefore);

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
		if (!qryOperLiverTransPlant().contains(testDB())) {
			throw new Exception("Comparison Result: Numbers Do Not Match");
		} else {
			Reporter.log("Comparison Result: Numbers Matched!!!");
		}
	}

	// SQL query in database
	@Test(groups = { "DB_query" })
	public String testDB() throws ClassNotFoundException, SQLException {
		return Connect2DB("select count(distinct patient_num) from observation_fact "
				+ "where concept_cd IN (select CONCEPT_CD from concept_dimension "
				+ "where CONCEPT_PATH like '\\PATH\\PROCEDURE\\09\\(42-54.99) Ope~bzgq\\(50) "
				+ "Operation~5uiv\\(50.5) Liver t~mdf7\\%')"
				);
	}

	// Record DB query duration
	@Test(groups = { "DB_duration" })
	public void DBduration() throws Exception {
		testDB_duration("insert into " + "dev02i2b2demodata.QUERY_GLOBAL_TEMP (patient_num, panel_count) with t as "
				+ "( select  /*+ index(observation_fact fact_cnpt_pat_enct_idx) */ f.patient_num from "
				+ "i2b2demodata.observation_fact f where f.concept_cd IN (select concept_cd from  "
				+ "dev02i2b2demodata.CONCEPT_DIMENSION   "
				+ "where concept_path LIKE '\\PATH\\PROCEDURE\\09\\(42-54.99) Ope~bzgq\\(50) "
				+ "Operation~5uiv\\(50.5) Liver t~mdf7\\%') group by  f.patient_num ) "
				+ "select  t.patient_num, 0 as panel_count  from t");
	}
}