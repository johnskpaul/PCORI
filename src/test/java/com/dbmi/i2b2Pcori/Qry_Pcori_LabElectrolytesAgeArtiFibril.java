package com.dbmi.i2b2Pcori;

import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

public class Qry_Pcori_LabElectrolytesAgeArtiFibril extends LoginI2b2Pcori {

	@Test(groups = { "UI_query" })
	public String qryLabElectrolytesAgeArtiFibril() throws Exception {
		testLogin();
		Reporter.log("");
		labs();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		// Chemistry
		driver.findElement(By.xpath(".//*[@title='\\LABS\\LP31388-9\\ - LOINC:LP31388-9']/div")).click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		// Electrolytes
		WebElement fromElement = driver.findElement(By.xpath(
				".//*[@title='\\LABS\\LP31388-9\\LP19403-2\\ - LOINC:LP19403-2']/div"));
		WebElement toElement = driver.findElement(By.id("QPD1"));

		Actions action = new Actions(driver);

		// drag and drop
		action.dragAndDrop(fromElement, toElement).build().perform();
		
//		age
		// Demographics
		driver.findElement(By.xpath(".//*[@title='PATH Demographics']/div")).click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		driver.findElement(By.xpath(".//*[contains(@title, 'Age')]")).click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		WebElement fromElement2 = driver
				.findElement(By.xpath(".//*[contains(@title, '45-54')]"));
		WebElement toElement2 = driver.findElement(By.id("QPD2"));

		Actions action2 = new Actions(driver);

		// drag and drop
		action2.dragAndDrop(fromElement2, toElement2).build().perform();
		
		
//		Atrial fibrillation
		
		// Scroll down
				js.executeScript("window.scrollBy(0, 1900);");
				Thread.sleep(2000);
				WebElement element = driver.findElement(By.xpath(
						".//*[@title='PaTH Cohorts']/div"));
				js.executeScript("arguments[0].scrollIntoView(true);", element);
				Thread.sleep(2000);
				js.executeScript("window.scrollBy(0, -190);");
		
		driver.findElement(By.xpath(".//*[(@title='PaTH Cohorts')]")).click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		WebElement fromElement3 = driver
				.findElement(By.xpath(".//*[@title='\\Path Network\\Cohorts\\Atrial Fibrillation Cohort\\']/div"));
		WebElement toElement3 = driver.findElement(By.id("QPD3"));

		Actions action3 = new Actions(driver);

		// drag and drop
		action3.dragAndDrop(fromElement3, toElement3).build().perform();
		
		

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
		if (!qryLabElectrolytesAgeArtiFibril().contains(testDB())) {
			throw new Exception("Comparison Result: Numbers Do Not Match");
		} else {
			Reporter.log("Comparison Result: Numbers Matched!!!");
		}
	};

//	 follow the AsthmaAge5564
	// SQL query in database
	@Test(groups = { "DB_query" })
	public String testDB() throws ClassNotFoundException, SQLException {
		return Connect2DB(
				"select count(*) from ( "
						+ "select distinct (f.patient_num) "
						+ "from dev02i2b2demodata.observation_fact f, "
						+ "dev02i2b2demodata.patient_dimension p "
						+ "where f.patient_num=p.patient_num "
						+ "and p.BIRTH_DATE BETWEEN MIN_BIRTHDATE_FOR_AGE(64) AND MAX_BIRTHDATE_FOR_AGE(55) "
						+ "AND f.concept_cd IN (select concept_cd from  dev02i2b2demodata.concept_dimension"
						+ "where concept_path LIKE '\\ICD10CM_2015AA\\(J00-J99) Dise~45pn\\(J40-J47) Chro~fbox\\(J45) Asthma\\%' )"
						+ "group by  f.patient_num)"
		);
	}

	// Record DB query duration
	@Test(groups = { "DB_duration" })
	public void DBduration() throws Exception {
		String[] qries = {
				"insert into dev02i2b2demodata.QUERY_GLOBAL_TEMP (patient_num, panel_count) "
				+ "with t as ( select  /*+ index(observation_fact fact_cnpt_pat_enct_idx) */ f.patient_num "
				+ "from dev02i2b2demodata.observation_fact f "
				+ "where "
				+ "f.concept_cd IN (select concept_cd from  dev02i2b2demodata.concept_dimension   "
				+ "where concept_path LIKE '\\Path Network\\Cohorts\\Atrial Fibrillation Cohort\\%') "
				+ "group by  f.patient_num )"
				+ "select  t.patient_num, 0 as panel_count  from t",
				"update dev02i2b2demodata.QUERY_GLOBAL_TEMP set panel_count =1 "
				+ "where dev02i2b2demodata.QUERY_GLOBAL_TEMP.panel_count =  0 "
				+ "and exists ( select 1 from ( select  /*+ index(observation_fact fact_cnpt_pat_enct_idx)"
				+ " */ f.patient_num  "
				+ "from dev02i2b2demodata.observation_fact f "
				+ "where f.concept_cd IN (select concept_cd from  dev02i2b2demodata.concept_dimension   "
				+ "where concept_path LIKE '\\LABS\\LP31388-9\\LP19403-2\\%')"
				+ "group by  f.patient_num ) t where dev02i2b2demodata.QUERY_GLOBAL_TEMP.patient_num = t.patient_num )",
				 "update dev02i2b2demodata.QUERY_GLOBAL_TEMP set panel_count =2 "
				 + "where dev02i2b2demodata.QUERY_GLOBAL_TEMP.panel_count =  1 "
				 + "and exists ( select 1 from ( select p.patient_num "
				 + "from dev02i2b2demodata.patient_dimension p "
				 + "where "
				 + "p.patient_num IN (select patient_num from  dev02i2b2demodata.patient_dimension   "
				 + "where BIRTH_DATE BETWEEN MIN_BIRTHDATE_FOR_AGE(54) AND MAX_BIRTHDATE_FOR_AGE(45)) "
				 + "group by  p.patient_num ) t where dev02i2b2demodata.QUERY_GLOBAL_TEMP.patient_num = t.patient_num )",
				  "select count(*) from ( select distinct  patient_num  "
				  + "from dev02i2b2demodata.QUERY_GLOBAL_TEMP "
				  + "where panel_count = 2 ) q"
				};
testDB_durationMulti(qries); 
	
}
}