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

public class Qry_Pcori_ICD10DiagDisMuskSkelDeformScoliosisLumbarDateRange extends LoginI2b2Pcori {

	@Test(groups = { "UI_query" })
	public String qryICD10DiagDisMuskSkelDeformScoliosisLumbarDateRange() throws Exception {
		testLogin();
		Reporter.log(
				"Query: ICD10CM_2015AA \\ Diseases of the musculoskeletal system "
				+ "and connective tissue (m00-m99) \\ Deforming dorsopathies (m40-m43) "
				+ "\\ Scoliosis \\ Other idiopathic scoliosis \\ "
				+ "Other idiopathic scoliosis, lumbar region"
				+ "Date Range ('01-JAN-15') AND to_date('01-JAN-17')"
				);
		diagnodesICD10();
		
		// Diseases of the musculoskeletal system and connective tissue (m00-m99) 

		driver.findElement(By
				.xpath(".//*[contains(@title, 'ICD10CM_2015AA \\ "
						+ "Diseases of the musculoskeletal system and connective tissue (m00-m99) - ICD10:M00-M99')]"))
				.click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		// Scroll down

		js.executeScript("window.scrollBy(0, 1900);");
		Thread.sleep(2000);
		WebElement element2 = driver.findElement(By.xpath(
				".//*[@title='ICD10CM_2015AA \\ "
				+ "Diseases of the musculoskeletal system and connective tissue (m00-m99) "
				+ "\\ Deforming dorsopathies (m40-m43) - ICD10:M40-M43']/div"));
		js.executeScript("arguments[0].scrollIntoView(true);", element2);
		Thread.sleep(2000);
		js.executeScript("window.scrollBy(0, -190);");
		
		
		// Deforming dorsopathies (m40-m43)
		driver.findElement(By
				.xpath(".//*[contains(@title, 'ICD10CM_2015AA \\ "
						+ "Diseases of the musculoskeletal system and connective tissue (m00-m99) "
						+ "\\ Deforming dorsopathies (m40-m43) - ICD10:M40-M43')]"))
				.click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		// Scroll down
		js.executeScript("window.scrollBy(0, 1900);");
		Thread.sleep(2000);
		WebElement element = driver.findElement(By.xpath(
				".//*[@title='ICD10CM_2015AA \\ Diseases of the musculoskeletal system and connective tissue (m00-m99) "
						+ "\\ Deforming dorsopathies (m40-m43) \\ Scoliosis - ICD10:M41']/div"));
		js.executeScript("arguments[0].scrollIntoView(true);", element);
		Thread.sleep(2000);
		js.executeScript("window.scrollBy(0, -190);");
		
//		Scoliosis
		driver.findElement(By
				.xpath(".//*[contains(@title, 'ICD10CM_2015AA \\ Diseases of the musculoskeletal system and connective tissue (m00-m99) "
						+ "\\ Deforming dorsopathies (m40-m43) \\ Scoliosis - ICD10:M41')]"))
				.click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
//		Other idiopathic scoliosis
		driver.findElement(By
				.xpath(".//*[contains(@title, 'ICD10CM_2015AA \\ "
						+ "Diseases of the musculoskeletal system and connective tissue (m00-m99) \\ "
						+ "Deforming dorsopathies (m40-m43) \\ Scoliosis \\ "
						+ "Other idiopathic scoliosis - ICD10:M41.2')]"))
				.click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
//		Other idiopathic scoliosis, lumbar region
		WebElement fromElement = driver.findElement(By.xpath(
				".//*[@title='ICD10CM_2015AA \\ Diseases of the musculoskeletal "
				+ "system and connective tissue (m00-m99) \\ Deforming dorsopathies (m40-m43) "
				+ "\\ Scoliosis \\ Other idiopathic scoliosis \\ Other idiopathic scoliosis, "
				+ "lumbar region - ICD10:M41.26']/div"));

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
		if (!qryICD10DiagDisMuskSkelDeformScoliosisLumbarDateRange().contains(testDB())) {
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
				+ "where CONCEPT_PATH like '\\ICD10CM_2015AA\\(M00-M99) Dise~6mvn\\"
				+ "(M40-M43) Defo~l404\\(M41) Scoliosis\\(M41.2) Other~iumu\\"
				+ "(M41.26) Other~wjla\\%')"
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
				+ "where concept_path LIKE '\\ICD10CM?_2015AA\\(M00-M99) Dise~6mvn\\(M40-M43) Defo~l404\\"
				+ "(M41) Scoliosis\\(M41.2) Other~iumu\\(M41.26) Other~wjla\\%' {ESCAPE '?'} )  "
				+ "AND  ( f.start_date >= to_date('01-Jan-2015 00:00:00','DD-MON-YYYY HH24:MI:SS') "
				+ "AND f.start_date <= to_date('01-Jan-2017 00:00:00','DD-MON-YYYY HH24:MI:SS') ) "
				+ "group by  f.patient_num ) "
				+ "select  t.patient_num, 0 as panel_count  from t"
				); 
		}
}