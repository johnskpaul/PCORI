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

public class Qry_Pcori_ICD10ProcNucMedMuscSkelCervicalSpineDateRange extends LoginI2b2Pcori {

	@Test(groups = { "UI_query" })
	public String qryICD10ProcNucMedMuscSkelCervicalSpineDateRange() throws Exception {
		testLogin();
		Reporter.log(
				"Query: ICD10 Procedures/Nuclear medicine (procedure)/musculoskeletal system (procedure)/cervical spine + Date Range");
		
//		ICD10 Procedures
		driver.findElement(By
				.xpath(".//*[contains(@title, 'ICD10 Procedures')]"))
				.click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		// Nuclear medicine (procedure)
		driver.findElement(By
				.xpath(".//*[contains(@title, 'ICD10PCS_2015AA \\ Nuclear medicine (procedure) - ICD10PCS:C')]"))
				.click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//		Musculoskeletal system
		
		driver.findElement(By
				.xpath(".//*[contains(@title, 'ICD10PCS_2015AA \\ Nuclear medicine (procedure) \\ "
						+ "musculoskeletal system (procedure) - ICD10PCS:CP')]"))
				.click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
//		Musculoskeletal system
		
		driver.findElement(By
				.xpath(".//*[contains(@title, 'ICD10PCS_2015AA \\ Nuclear medicine (procedure) "
						+ "\\ musculoskeletal system (procedure) \\ tomographic (tomo) nuclear medicine imaging - ICD10PCS:CP2')]"))
				.click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		
		WebElement fromElement = driver.findElement(By.xpath(
				".//*[@title='ICD10PCS_2015AA \\ Nuclear medicine (procedure) "
				+ "\\ musculoskeletal system (procedure) \\ tomographic (tomo) nuclear medicine imaging "
				+ "\\ cervical spine - ICD10PCS:CP22']/div"));

		WebElement toElement = driver.findElement(By.id("QPD1"));

		Actions action = new Actions(driver);

		// drag and drop
		action.dragAndDrop(fromElement, toElement).build().perform();

		// Date Range
		driver.findElement(By.id("queryPanelDatesB1")).click();

		// Switch to new window opened
		for (String winHandle : driver.getWindowHandles()) {
			driver.switchTo().window(winHandle);
		}
		for (int second = 0;; second++) {
			if (second >= 60)
				fail("timeout");
			try {
				if ("Constrain Group by Date Range".equals(driver.findElement(By.id("constraintDates_h")).getText()))
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}

		driver.findElement(By.id("checkboxDateStart")).click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		driver.findElement(By.id("constraintDateStart")).sendKeys("1/1/2015");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		driver.findElement(By.id("checkboxDateEnd")).click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		driver.findElement(By.id("constraintDateEnd")).sendKeys("1/1/2017");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		driver.findElement(By.xpath("//button[contains(@id, 'yui') and text()='OK']")).click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		// *****************************

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
		if (!qryICD10ProcNucMedMuscSkelCervicalSpineDateRange().contains(testDB())) {
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
				+ "where CONCEPT_PATH like '\\ICD10PCS_2015AA\\(C) Nuclear me~bmw7\\(CP) "
				+ "Nuclear m~uso4\\(CP2) Nuclear~vlcm\\(CP22) Nuclear~4muk\\%') "
				+ "AND START_DATE " 
				+ "between to_date('01-JAN-15') AND to_date('01-JAN-17')");
	}

	// Record DB query duration
	@Test(groups = { "DB_duration" })
	public void DBduration() throws Exception {
		testDB_duration(
				"insert into dev02i2b2demodata.QUERY_GLOBAL_TEMP (patient_num, panel_count) "
				+ "with t as ( "
				+ "select  /*+ index(observation_fact fact_cnpt_pat_enct_idx) */ f.patient_num "
				+ "from dev02i2b2demodata.observation_fact f "
				+ "where "
				+ "f.concept_cd IN (select concept_cd "
				+ "from  dev02i2b2demodata.concept_dimension   "
				+ "where concept_path LIKE '\\ICD10PCS?_2015AA\\(C) "
				+ "Nuclear me~bmw7\\(CP) Nuclear m~uso4\\(CP2) Nuclear~vlcm\\"
				+ "(CP22) Nuclear~4muk\\%' {ESCAPE '?'} ) "
				+ " AND  ( f.start_date >= to_date('01-Jan-2015 00:00:00','DD-MON-YYYY HH24:MI:SS') "
				+ "AND f.start_date <= to_date('01-Jan-2017 00:00:00','DD-MON-YYYY HH24:MI:SS') ) "
				+ "group by  f.patient_num ) "
				+ "select  t.patient_num, 0 as panel_count  from t"
				);
	}
}