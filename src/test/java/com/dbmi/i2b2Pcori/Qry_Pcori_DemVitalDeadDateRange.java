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

public class Qry_Pcori_DemVitalDeadDateRange extends LoginI2b2Pcori {

	@Test(groups = { "UI_query" })

	public String qryPcori_DemVitalDead() throws Exception {
		testLogin();
		Reporter.log(
				"Query: Demographics  \\ Vital Status \\ Dead ");
		
		// Demographics
		driver.findElement(By.xpath(".//*[@title='PATH Demographics']/div")).click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//		Vital Status 
		driver.findElement(By.xpath(".//*[@title='\\PATH\\Demographics\\Vital Status\\']/div")).click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//		Dead
		WebElement fromElement = driver.findElement(
				By.xpath(".//*[@title='\\PATH\\Demographics\\Vital Status\\Dead\\ - DEM|VITAL:D']/div"));
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

		// Switch to new window opened
		for (String winHandle : driver.getWindowHandles()) {
			driver.switchTo().window(winHandle);
		}
		return string;
		// return string1;

	}

	@AfterClass(alwaysRun = true)
	public void tearDown() throws Exception {
		driver.quit();
	}

	// Compare UI and DB counts

	@Test(groups = { "UI_DB_Comparison" })
	public void testCompare() throws Exception {
		if (!qryPcori_DemVitalDead().contains(testDB())) {
			throw new Exception("Comparison Result: Numbers Do Not Match");
		} else {
			Reporter.log("Comparison Result: Numbers Matched!!!");
		}
	}

	// SQL query in database
	@Test(groups = { "DB_query" })

	public String testDB() throws ClassNotFoundException, SQLException {
		return Connect2DB(
				"select count(distinct patient_num) from PATIENT_DIMENSION "
				+ "where PATIENT_NUM IN "
				+ "(select PATIENT_NUM from PATIENT_DIMENSION "
				+ "where VITAL_STATUS_CD = 'D')");
	}

	// Record DB query duration
	@Test(groups = { "DB_duration" })
	public void DBduration() throws Exception {
		testDB_duration(
				"insert into dev02i2b2demodata.QUERY_GLOBAL_TEMP (patient_num, panel_count) "
				+ "with t as ( select p.patient_num "
				+ "from dev02i2b2demodata.patient_dimension p "
				+ "where "
				+ "p.patient_num IN (select patient_num from  "
				+ "dev02i2b2demodata.patient_dimension   "
				+ "where vital_status_cd = 'D')   "
				+ "group by  p.patient_num ) "
				+ "select  t.patient_num, 0 as panel_count  from t "
				);
	}
}