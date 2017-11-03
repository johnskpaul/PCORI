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

public class Qry_Pcori_ICD10DiagDisRespAsthmaAge5564 extends LoginI2b2Pcori {

	@Test(groups = { "UI_query" })
	public String qryICD10DiagDisRespAsthmaAge5564() throws Exception {
		testLogin();
		Reporter.log(
				"Query: ICD10 Diagnoses/Diseases of the respiratory system/Chronic lower respiratory disease /Asthma AND Age = 55-64 years old");
		diagnodesICD10();

		// Dieseae of respiratory system
		driver.findElement(By
				.xpath(".//*[contains(@title, 'ICD10CM_2015AA \\ Diseases of the respiratory system (j00-j99) - ICD10:J00-J99')]"))
				.click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		// Chronic lower respiratory diease

		driver.findElement(By
				.xpath(".//*[@title='ICD10CM_2015AA \\ Diseases of the respiratory system (j00-j99) \\ Chronic lower respiratory diseases (j40-j47) - ICD10:J40-J47']/div"))
				.click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		// Asthma
		WebElement fromElement = driver.findElement(By.xpath(
				".//*[@title='ICD10CM_2015AA \\ Diseases of the respiratory system (j00-j99) \\ Chronic lower respiratory diseases (j40-j47) \\ Asthma - ICD10:J45']/div"));

		WebElement toElement = driver.findElement(By.id("QPD1"));

		Actions action = new Actions(driver);

		// drag and drop
		action.dragAndDrop(fromElement, toElement).build().perform();

		// age
		// Demographics
		driver.findElement(By.xpath(".//*[@title='PATH Demographics']/div")).click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		driver.findElement(By.xpath(".//*[contains(@title, 'Age')]")).click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		WebElement fromElement2 = driver.findElement(By.xpath(".//*[contains(@title, '55-64')]"));
		WebElement toElement2 = driver.findElement(By.id("QPD2"));

		Actions action2 = new Actions(driver);

		// drag and drop
		action2.dragAndDrop(fromElement2, toElement2).build().perform();

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
		if (!qryICD10DiagDisRespAsthmaAge5564().contains(testDB())) {
			throw new Exception("Comparison Result: Numbers Do Not Match");
		} else {
			Reporter.log("Comparison Result: Numbers Matched!!!");
		}
	};

	// SQL query in database
//	Count query for Asthma patients of 55 - 64 age limit
	@Test(groups = { "DB_query" })
	public String testDB() throws ClassNotFoundException, SQLException {
		return Connect2DB(
				"select count(*) from ( "
				+ "select distinct (f.patient_num) "
				+ "from dev02i2b2demodata.observation_fact f, "
				+ "dev02i2b2demodata.patient_dimension p "
				+ "where f.patient_num=p.patient_num "
				+ "and p.BIRTH_DATE BETWEEN MIN_BIRTHDATE_FOR_AGE(64) AND MAX_BIRTHDATE_FOR_AGE(55) "
				+ "AND f.concept_cd IN (select concept_cd from  dev02i2b2demodata.concept_dimension "
				+ "where concept_path LIKE '\\ICD10CM_2015AA\\(J00-J99) Dise~45pn\\(J40-J47) Chro~fbox\\(J45) Asthma\\%' ) "
				+ "group by  f.patient_num)"
				);
	}

	// Record DB query duration
	@Test(groups = { "DB_duration" })
	public void DBduration() throws Exception {
		String[] qries = { "insert into dev02i2b2demodata.QUERY_GLOBAL_TEMP (patient_num, panel_count) "
				+ "with t as ( select  /*+ index(observation_fact fact_cnpt_pat_enct_idx) "
				+ "*/ f.patient_num from dev02i2b2demodata.observation_fact f where f.concept_cd "
				+ "IN (select concept_cd from  dev02i2b2demodata.concept_dimension "
				+ "where concept_path LIKE '\\ICD10CM?_2015AA\\(J00-J99) Dise~45pn\\(J40-J47) "
				+ "Chro~fbox\\(J45) Asthma\\%' {ESCAPE '?'} ) group by  f.patient_num) "
				+ "select  t.patient_num, 0 as panel_count  from t",

				"update dev02i2b2demodata.QUERY_GLOBAL_TEMP set panel_count =1 "
						+ "where dev02i2b2demodata.QUERY_GLOBAL_TEMP.panel_count =  0 "
						+ "and exists ( select 1 from ( select p.patient_num "
						+ "from dev02i2b2demodata.patient_dimension p where p.patient_num IN "
						+ "(select patient_num from  dev02i2b2demodata.patient_dimension "
						+ "where BIRTH_DATE BETWEEN MIN_BIRTHDATE_FOR_AGE(64) "
						+ "AND MAX_BIRTHDATE_FOR_AGE(55)) group by  p.patient_num ) t "
						+ "where dev02i2b2demodata.QUERY_GLOBAL_TEMP.patient_num = t.patient_num )",
				// final
				"select * from ( select distinct  patient_num  "
						+ "from dev02i2b2demodata.QUERY_GLOBAL_TEMP where panel_count = 1 ) q " };
		testDB_durationMulti(qries);

	}
}