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

public class Qry_Pcori_DemRaceWhiteAgeHispanicAgeRange extends LoginI2b2Pcori {


	@Test(groups = { "UI_query" })
	public String qryDemRaceWhiteAgeHispanicAgeRange() throws Exception {
		testLogin();

		Reporter.log("Query: Demographics/Race = White and Age = 18-34 and Ethnicity  = Hispanic");
		
		// Demographics
		driver.findElement(By.xpath(".//*[@title='PATH Demographics']/div")).click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		driver.findElement(By.xpath(".//*[contains(@title, 'Race')]")).click();
		// driver.findElement(By.xpath(".//*[@title='\\PATH\\Demographics\\Race\\']/div")).click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		WebElement fromElement = driver
//				.findElement(By.xpath(".//*[@title='Demographic \\ Race \\ White - DEM|RACE:W']/div"));
				.findElement(By.xpath(".//*[contains(@title, 'White')]"));
		WebElement toElement = driver.findElement(By.id("QPD1"));

		Actions action = new Actions(driver);

		// drag and drop
		action.dragAndDrop(fromElement, toElement).build().perform();
		
//		 Age
		
		driver.findElement(By.xpath(".//*[contains(@title, 'Age')]")).click();
		// driver.findElement(By.xpath(".//*[@title='\\PATH\\Demographics\\Race\\']/div")).click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		WebElement fromElement2 = driver
//				.findElement(By.xpath(".//*[@title='Demographic \\ Race \\ White - DEM|RACE:W']/div"));
				.findElement(By.xpath(".//*[contains(@title, '18-34')]"));
		WebElement toElement2 = driver.findElement(By.id("QPD2"));

		Actions action2 = new Actions(driver);

		// drag and drop
		action2.dragAndDrop(fromElement2, toElement2).build().perform();
		
		
//		Hispanic
		
		driver.findElement(By.xpath(".//*[contains(@title, 'Ethnicity')]")).click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		WebElement fromElement3 = driver
				.findElement(By.xpath(".//*[@title='A person of Cuban, Mexican, Puerto Rican, "
						+ "South or Central American, or other Spanish culture or origin, regardless of race. "
						+ "\\ Hisptanic - ETHNICITY:HISPANIC']/div"));
		WebElement toElement3 = driver.findElement(By.id("QPD3"));

		Actions action3 = new Actions(driver);

		// drag and drop
		action3.dragAndDrop(fromElement3, toElement3).build().perform();
		
		
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
		Thread.sleep(100000);

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
		if (!qryDemRaceWhiteAgeHispanicAgeRange().contains(testDB())) {
			throw new Exception("Comparison Result: Numbers Do Not Match");
		} else {
			Reporter.log("Comparison Result: Numbers Matched!!!");
		}
	}

	// SQL query in database
	@Test(groups = { "DB_query" })
	public String testDB() throws ClassNotFoundException, SQLException {
		return Connect2DB(
				"select count(distinct p.patient_num) from PATIENT_DIMENSION p left join "
				+ "OBSERVATION_FACT o on p.PATIENT_NUM = o.PATIENT_NUM "
				+ "where p.race_cd = 'DEM|RACE:white' "
				+ "and o.concept_cd IN (select concept_cd from  "
				+ "dev02i2b2demodata.CONCEPT_DIMENSION   "
				+ "where concept_path like '\\PATH\\Demographics\\HISPANIC\\Y\\%') "
				+ "and p.BIRTH_DATE BETWEEN MIN_BIRTHDATE_FOR_AGE(34) AND MAX_BIRTHDATE_FOR_AGE(18)"
				);
	}

	// Record DB query duration
	@Test(groups = { "DB_duration" })
	public void DBduration() throws Exception {
		
		String[] qries = {
							"insert into dev02i2b2demodata.QUERY_GLOBAL_TEMP (patient_num, panel_count) with t as "
							+ "( select  /*+ index(observation_fact fact_cnpt_pat_enct_idx) */ f.patient_num "
							+ "from dev02i2b2demodata.observation_fact f where f.concept_cd IN "
							+ "(select concept_cd from  dev02i2b2demodata.CONCEPT_DIMENSION "
							+ "where concept_path like '\\PATH\\Demographics\\HISPANIC\\Y\\%') "
							+ "group by  f.patient_num ) select  t.patient_num, 0 as panel_count  from t",
							"update dev02i2b2demodata.QUERY_GLOBAL_TEMP set panel_count =1 "
							+ "where dev02i2b2demodata.QUERY_GLOBAL_TEMP.panel_count =  0 and exists "
							+ "( select 1 from ( select p.patient_num from dev02i2b2demodata.patient_dimension p "
							+ "where p.patient_num IN (select patient_num from  dev02i2b2demodata.patient_dimension "
							+ "where race_cd = 'DEM|RACE:white') group by  p.patient_num ) t "
							+ "where dev02i2b2demodata.QUERY_GLOBAL_TEMP.patient_num = t.patient_num)",
							"update dev02i2b2demodata.QUERY_GLOBAL_TEMP set panel_count =2 "
							+ "where dev02i2b2demodata.QUERY_GLOBAL_TEMP.panel_count =  1 and exists ( select 1 from "
							+ "( select p.patient_num from dev02i2b2demodata.patient_dimension p where p.patient_num IN "
							+ "(select patient_num from  dev02i2b2demodata.patient_dimension "
							+ "where BIRTH_DATE BETWEEN MIN_BIRTHDATE_FOR_AGE(34) AND MAX_BIRTHDATE_FOR_AGE(18)) "
							+ "group by  p.patient_num ) t "
							+ "where dev02i2b2demodata.QUERY_GLOBAL_TEMP.patient_num = t.patient_num)",
							"select count(*) from ( select distinct  patient_num  "
							+ "from dev02i2b2demodata.QUERY_GLOBAL_TEMP where panel_count = 2 ) q"
						 };
		testDB_durationMulti(qries); 
				
			}

}