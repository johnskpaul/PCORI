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

public class Qry_Pcori_ICD10ProcMeasurePhysioloMonitorProductsExtRate extends LoginI2b2Pcori {

	@Test(groups = { "UI_query" })
	public String qryICD10ProcMeasurePhysioloMonitorProductsExtRate() throws Exception {
		driver.manage().window().maximize();
		testLogin();
		Reporter.log(
				"*******************************************Query: ICD10 Procedures \\ Measurement and monitoring (procedure) \\ "
				+ "physiological systems (procedure) \\ monitoring \\ products of conception, cardiac \\ "
				+ "external \\ rate *******************************************");
		
//		ICD10 Procedures
		driver.findElement(By
				.xpath(".//*[contains(@title, 'ICD10 Procedures')]"))
				.click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		// Measurement and monitoring (procedure)
		driver.findElement(By
				.xpath(".//*[contains(@title, 'ICD10PCS_2015AA \\ Measurement and monitoring (procedure) - ICD10PCS:4')]"))
				.click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//		physiological systems (procedure)		
		driver.findElement(By
				.xpath(".//*[contains(@title, 'ICD10PCS_2015AA \\ Measurement and monitoring (procedure) \\ "
						+ "physiological systems (procedure) - ICD10PCS:4A')]"))
				.click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
//		monitoring		
		driver.findElement(By
				.xpath(".//*[contains(@title, 'ICD10PCS_2015AA \\ Measurement and monitoring (procedure) \\ "
						+ "physiological systems (procedure) \\ monitoring - ICD10PCS:4A1')]"))
				.click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
//		products of conception, cardiac 
		driver.findElement(By
				.xpath(".//*[contains(@title, 'ICD10PCS_2015AA \\ Measurement and monitoring (procedure) \\ "
						+ "physiological systems (procedure) \\ monitoring \\ products of conception, cardiac - ICD10PCS:4A1H')]"))
				.click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		// Scroll down
		js.executeScript("window.scrollBy(0, 1900);");
		Thread.sleep(2000);
		WebElement element = driver.findElement(By.xpath(
				".//*[@title='ICD10PCS_2015AA \\ Measurement and monitoring (procedure) \\ "
						+ "physiological systems (procedure) \\ monitoring \\ products of conception, cardiac \\ "
						+ "external - ICD10PCS:4A1HX']/div"));
		js.executeScript("arguments[0].scrollIntoView(true);", element);
		Thread.sleep(2000);
		js.executeScript("window.scrollBy(0, -190);");

//		External
		driver.findElement(By
				.xpath(".//*[contains(@title, 'ICD10PCS_2015AA \\ Measurement and monitoring (procedure) \\ "
						+ "physiological systems (procedure) \\ monitoring \\ products of conception, cardiac \\ "
						+ "external - ICD10PCS:4A1HX')]"))
				.click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		
//		Rate
		WebElement fromElement = driver.findElement(By.xpath(
				".//*[@title='ICD10PCS_2015AA \\ Measurement and monitoring (procedure) \\ "
				+ "physiological systems (procedure) \\ monitoring \\ products of conception, cardiac \\ "
				+ "external \\ rate - ICD10PCS:4A1HXC']/div"));

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
		Thread.sleep(180000);

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
		if (!qryICD10ProcMeasurePhysioloMonitorProductsExtRate().contains(testDB())) {
			throw new Exception("Comparison Result: Numbers Do Not Match");
		} else {
			Reporter.log("Comparison Result: Numbers Matched!!!");
		}
	};

	// SQL query in database
	@Test(groups = { "DB_query" })
	public String testDB() throws ClassNotFoundException, SQLException {
		return Connect2DB(
				"select count(distinct patient_num) from observation_fact "
				+ "where concept_cd IN (select CONCEPT_CD from concept_dimension "
				+ "where CONCEPT_PATH like '\\ICD10PCS_2015AA\\(4) Measuremen~ge9w\\"
				+ "(4A) Measureme~dxgz\\(4A1) Measurem~49bf\\(4A1H) Measure~3za2\\"
				+ "(4A1HX) Measur~u533\\(4A1HXC) Measu~b03c\\%')" 
				);
	}

	// Record DB query duration
	@Test(groups = { "DB_duration" })
	public void DBduration() throws Exception {
		testDB_duration(
				"insert into dev02i2b2demodata.QUERY_GLOBAL_TEMP (patient_num, panel_count) "
				+ "with t as ( select  /*+ index(observation_fact fact_cnpt_pat_enct_idx) */ f.patient_num "
				+ "from dev02i2b2demodata.observation_fact f "
				+ "where "
				+ "f.concept_cd IN (select concept_cd from  "
				+ "dev02i2b2demodata.concept_dimension   "
				+ "where concept_path LIKE '\\ICD10PCS?_2015AA\\(4) Measuremen~ge9w\\(4A) Measureme~dxgz\\"
				+ "(4A1) Measurem~49bf\\(4A1H) Measure~3za2\\(4A1HX) Measur~u533\\(4A1HXC) Measu~b03c\\%' {ESCAPE '?'} ) "
				+ "group by  f.patient_num ) "
				+ "select  t.patient_num, 0 as panel_count  from t"
				);
	}
}