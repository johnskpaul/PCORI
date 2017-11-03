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

public class Qry_Pcori_ICD10DiagDisNervMyoneuralMyopathInflamatoryOtherInflmatoryElseClassified extends LoginI2b2Pcori {

	@Test(groups = { "UI_query" })
	public String qryICD10DiagDisNervMyoneuralMyopathInflamatoryOtherInflmatoryElseClassified() throws Exception {
		testLogin();
		driver.manage().window().maximize();
		Reporter.log(
				"************************************Query: ICD10 Diagnoses \\ "
				+ "Diseases of the nervous system (g00-g99) \\ "
				+ "Diseases of myoneural junction and muscle (g70-g73) \\ "
				+ "Other and unspecified myopathies \\ "
				+ "Inflammatory and immune myopathies, not elsewhere classified \\ "
				+ "Other inflammatory and immune myopathies, not elsewhere classified *****************************");
		
//		ICD10 Diagnoses
		diagnodesICD10();
		
//		Diseases of the nervous system (g00-g99)
		driver.findElement(By
				.xpath(".//*[contains(@title, 'ICD10CM_2015AA \\ "
						+ "Diseases of the nervous system (g00-g99) - ICD10:G00-G99')]"))
				.click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		

		
		// Diseases of myoneural junction and muscle (g70-g73) 
		driver.findElement(By
				.xpath(".//*[contains(@title, 'ICD10CM_2015AA \\ Diseases of the nervous system (g00-g99) \\ "
						+ "Diseases of myoneural junction and muscle (g70-g73) - ICD10:G70-G73')]"))
				.click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
//		Scroll down

		js.executeScript("window.scrollBy(0, 1900);");
		Thread.sleep(2000);
		WebElement element2 = driver.findElement(By.xpath(
				".//*[@title='ICD10CM_2015AA \\ Diseases of the nervous system (g00-g99) \\ "
						+ "Diseases of myoneural junction and muscle (g70-g73) \\ Other and unspecified myopathies - ICD10:G72']/div"));
		js.executeScript("arguments[0].scrollIntoView(true);", element2);
		Thread.sleep(2000);
		js.executeScript("window.scrollBy(0, -190);");
		
//		Other and unspecified myopathies 
		driver.findElement(By
				.xpath(".//*[contains(@title, 'ICD10CM_2015AA \\ Diseases of the nervous system (g00-g99) \\ "
						+ "Diseases of myoneural junction and muscle (g70-g73) \\ Other and unspecified myopathies - ICD10:G72')]"))
				.click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
//		Inflammatory and immune myopathies, not elsewhere classified
		driver.findElement(By
				.xpath(".//*[contains(@title, 'ICD10CM_2015AA \\ Diseases of the nervous system (g00-g99) \\ "
						+ "Diseases of myoneural junction and muscle (g70-g73) \\ Other and unspecified myopathies \\ "
						+ "Inflammatory and immune myopathies, not elsewhere classified - ICD10:G72.4')]"))
				.click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//		Other inflammatory and immune myopathies, not elsewhere classified
		WebElement fromElement = driver.findElement(By.xpath(
				".//*[@title='ICD10CM_2015AA \\ Diseases of the nervous system (g00-g99) \\ "
				+ "Diseases of myoneural junction and muscle (g70-g73) \\ Other and unspecified myopathies \\ "
				+ "Inflammatory and immune myopathies, not elsewhere classified \\ "
				+ "Other inflammatory and immune myopathies, not elsewhere classified - ICD10:G72.49']/div"));

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
		if (!qryICD10DiagDisNervMyoneuralMyopathInflamatoryOtherInflmatoryElseClassified().contains(testDB())) {
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
				+ "where CONCEPT_PATH like '\\ICD10CM_2015AA\\(G00-G99) Dise~j8kq\\(G70-G73) Dise~366v\\"
				+ "(G72) Other an~fdx8\\(G72.4) Inflam~lv2q\\(G72.49) Other~w7hl\\%')"
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
				+ "where concept_path LIKE '\\ICD10CM?_2015AA\\(G00-G99) Dise~j8kq\\(G70-G73) Dise~366v\\"
				+ "(G72) Other an~fdx8\\(G72.4) Inflam~lv2q\\(G72.49) Other~w7hl\\%' {ESCAPE '?'} ) "
				+ "group by  f.patient_num )  "
				+ "select  t.patient_num, 0 as panel_count  from t  "
				); 
		
		}
}