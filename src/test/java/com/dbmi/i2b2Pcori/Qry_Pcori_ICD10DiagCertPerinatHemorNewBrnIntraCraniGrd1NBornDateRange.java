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

public class Qry_Pcori_ICD10DiagCertPerinatHemorNewBrnIntraCraniGrd1NBornDateRange extends LoginI2b2Pcori {

	@Test(groups = { "UI_query" })
	public String qryICD10DiagCertPerinatHemorNewBrnIntraCraniGrd1NBornDateRange() throws Exception {
		testLogin();
		driver.manage().window().maximize();
		Reporter.log(
				"************************************Query: ICD10 Diagnoses \\ "
				+ "Certain conditions originating in the perinatal period (p00-p96) \\ "
				+ "Hemorrhagic and hematological disorders of newborn (p50-p61) \\ "
				+ "Intracranial nontraumatic hemorrhage of newborn \\ "
				+ "Intraventricular (nontraumatic) hemorrhage, grade 1, of newborn + Date Range *****************************");
		
//		ICD10 Diagnoses
		diagnodesICD10();
		
		
//		Certain conditions originating in the perinatal period (p00-p96) 
		driver.findElement(By
				.xpath(".//*[contains(@title, 'ICD10CM_2015AA \\ "
						+ "Certain conditions originating in the perinatal period (p00-p96) - ICD10:P00-P96')]"))
				.click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		// Hemorrhagic and hematological disorders of newborn (p50-p61)
		driver.findElement(By
				.xpath(".//*[contains(@title, 'ICD10CM_2015AA \\ "
						+ "Certain conditions originating in the perinatal period (p00-p96) \\ "
						+ "Hemorrhagic and hematological disorders of newborn (p50-p61) - ICD10:P50-P61')]"))
				.click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
//		Intracranial nontraumatic hemorrhage of newborn
		driver.findElement(By
				.xpath(".//*[contains(@title, 'ICD10CM_2015AA \\ Certain conditions originating in the perinatal period (p00-p96) \\ "
						+ "Hemorrhagic and hematological disorders of newborn (p50-p61) \\ "
						+ "Intracranial nontraumatic hemorrhage of newborn - ICD10:P52')]"))
				.click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
//		Intraventricular (nontraumatic) hemorrhage, grade 1, of newborn
		
		WebElement fromElement = driver.findElement(By.xpath(
				".//*[@title='ICD10CM_2015AA \\ Certain conditions originating in the perinatal period (p00-p96) \\ "
				+ "Hemorrhagic and hematological disorders of newborn (p50-p61) \\ "
				+ "Intracranial nontraumatic hemorrhage of newborn \\ "
				+ "Intraventricular (nontraumatic) hemorrhage, grade 1, of newborn - ICD10:P52.0']/div"));

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
		if (!qryICD10DiagCertPerinatHemorNewBrnIntraCraniGrd1NBornDateRange().contains(testDB())) {
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
				+ "where CONCEPT_PATH like '\\ICD10CM_2015AA\\(P00-P96) Cert~6976\\"
				+ "(P50-P61) Hemo~c7pu\\(P52) Intracra~0unq\\(P52.0) Intrav~shx9\\%')"
				+ "AND START_DATE between to_date('01-JAN-15') AND to_date('01-JAN-17')"
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
				+ "f.concept_cd IN (select concept_cd from  dev02i2b2demodata.concept_dimension   "
				+ "where concept_path LIKE '\\ICD10CM?_2015AA\\(P00-P96) Cert~6976\\"
				+ "(P50-P61) Hemo~c7pu\\(P52) Intracra~0unq\\(P52.0) Intrav~shx9\\%' {ESCAPE '?'} ) "
				+ "AND  ( f.start_date >= to_date('01-Jan-2015 00:00:00','DD-MON-YYYY HH24:MI:SS') "
				+ "AND f.start_date <= to_date('01-Jan-2017 00:00:00','DD-MON-YYYY HH24:MI:SS') ) "
				+ "group by  f.patient_num ) "
				+ "select  t.patient_num, 0 as panel_count  from t"
				); 
		
		}
}