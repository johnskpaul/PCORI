package com.dbmi.i2b2Pcori;

import static org.testng.Assert.fail;

import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

public class Qry_Pcori_VisitTypeAmbulatory extends LoginI2b2Pcori {
	// Visit Details/Visit type/Ambulatory Visit
	@Test(groups = { "UI_query" })
	public String qryVisitTypeAmbulatory() throws Exception {

		testLogin();
		Reporter.log("Query: Encounter Details\\Visit Type\\Ambulatory");

		// storing current window
		String winHandleBefore = driver.getWindowHandle();

		// Increase the timeout
//		driver.findElement(By.xpath("(//img[@alt='Show Options'])[3]")).click();

//		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		// Switch to new window opened
//		for (String winHandle : driver.getWindowHandles()) {
//			driver.switchTo().window(winHandle);
//		}
//
//		driver.findElement(By.id("QryTimeout")).clear();
//		driver.findElement(By.id("QryTimeout")).sendKeys("1800");
//		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
//
//		driver.findElement(By.xpath("//button[contains(@id, 'yui') and text()='OK']")).click();
//		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		// Switch back to original browser (first window)
//		driver.switchTo().window(winHandleBefore);

		// Encounter Details
		driver.findElement(By.xpath(".//*[@title='Path ENC Details']/div")).click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//		Visit Type
		driver.findElement(By.xpath(".//*[@title='\\PCORI\\ENCOUNTER\\VisitType\\']/div")).click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//		Ambulatory
		WebElement fromElement = driver
				.findElement(By.xpath(".//*[@title='\\PCORI\\ENCOUNTER\\VisitType\\AmbulatoryVisit\\ - AV']/div"));
		WebElement toElement = driver.findElement(By.id("QPD1"));

		Actions action = new Actions(driver);

		// drag and drop
		action.dragAndDrop(fromElement, toElement).build().perform();

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
		if (!qryVisitTypeAmbulatory().contains(testDB())) {
			throw new Exception("Comparison Result: Numbers Do Not Match");
		} else {
			Reporter.log("Comparison Result: Numbers Matched!!!");
		}
	}

	// SQL query in database
	@Test(groups = { "DB_query" })
	public String testDB() throws ClassNotFoundException, SQLException {
		return Connect2DB("select count(distinct patient_num) from visit_dimension "
				+ "where INOUT_CD = 'AV'");
				}

	// Record DB query duration
	@Test(groups = { "DB_duration" })
	public void DBduration() throws Exception {
		testDB_duration("insert into dev02i2b2demodata.QUERY_GLOBAL_TEMP (patient_num, panel_count) "
				+ "with t as ( select e.patient_num "
				+ "from dev02i2b2demodata.visit_dimension e "
				+ "where "
				+ "e.encounter_num IN (select encounter_num from  dev02i2b2demodata.visit_dimension   "
				+ "where InOut_cd = 'AV') "
				+ "group by  e.patient_num ) "
				+ "select  t.patient_num, 0 as panel_count  from t");
	}

}