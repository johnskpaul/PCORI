package com.dbmi.i2b2Pcori;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.testng.Reporter;
import org.testng.annotations.Test;

public class DBTestSQLPlus {
	@Test
	public void testSQLPlus() throws ClassNotFoundException, SQLException {
		
//		Load JDBC driver
		Class.forName("oracle.jdbc.driver.OracleDriver");
		
		System.out.println("Driver loaded");
		
//		Connection con = DriverManager.getConnection("jdbc:oracle:thin:@dbmi-db-dev-01.dbmi.pitt.edu:1521:dbmi02", "dev02i2b2demodata", "demouser");
		
//		connection to database
		String Url = "jdbc:oracle:thin:@";
		
		String serverName = "DBMI-DB-DEV-02.DBMI.PITT.EDU";
//		String serverName = "dbmi-db-dev-01.dbmi.pitt.edu";

		String portNumber = "1521";
		
		String sid = "dbmi09";
//		String sid = "dbmi02";
		
		String dbUrl = Url + serverName + ":" + portNumber + ":" + sid;
		
//		DB user name
		String userName = "ncats2i2b2demodata";
//		String userName = "dev02i2b2demodata";
		
//		DB password
		String password = "demouser";
		
	
		
//		Db Query 
		
//		•	Demographics/Hispanic = Yes ( Done )
//		String query ="insert into ncats2i2b2demodata.QUERY_GLOBAL_TEMP (patient_num, panel_count) with t as (select  /*+ index(observation_fact fact_cnpt_pat_enct_idx) */ f.patient_num from ncats2i2b2demodata.observation_fact f where f.concept_cd IN (select concept_cd from  ncats2i2b2demodata.concept_dimension   where concept_path LIKE '\\ACT\\Demographics\\Hispanic\\Yes\\%') group by  f.patient_num ) select  t.patient_num, 0 as panel_count  from t";
		
//		•	Demographics/Hispanic = Yes Date range: 1/1/2015 – 1/1/2017 ( Done )
		
//		String query = "insert into ncats2i2b2demodata.QUERY_GLOBAL_TEMP (patient_num, panel_count) with t as ( select  /*+ index(observation_fact fact_cnpt_pat_enct_idx) */ f.patient_num from ncats2i2b2demodata.observation_fact f where f.concept_cd IN (select concept_cd from  ncats2i2b2demodata.concept_dimension   where concept_path LIKE '\\ACT\\Demographics\\Hispanic\\Yes\\%') AND  ( f.start_date >= to_date('01-Jan-2015 00:00:00','DD-MON-YYYY HH24:MI:SS') AND f.start_date <= to_date('01-Jan-2017 00:00:00','DD-MON-YYYY HH24:MI:SS') ) group by  f.patient_num ) select  t.patient_num, 0 as panel_count  from t"; 
		
//		•	Demographics/Race = White ( Done )
		
//		String query = "insert into ncats2i2b2demodata.QUERY_GLOBAL_TEMP (patient_num, panel_count) with t as ( select  /*+ index(observation_fact fact_cnpt_pat_enct_idx) */ f.patient_num from ncats2i2b2demodata.observation_fact f where f.concept_cd IN (select concept_cd from  ncats2i2b2demodata.concept_dimension   where concept_path LIKE '\\ACT\\Demographics\\Race\\White\\%') group by  f.patient_num ) select  t.patient_num, 0 as panel_count  from t"; 
		
		
//		•	Demographics/Race = White Date range: 1/1/2015 – 1/1/2017 ( Done )
		
//		String query = "insert into ncats2i2b2demodata.QUERY_GLOBAL_TEMP (patient_num, panel_count) with t as ( select  /*+ index(observation_fact fact_cnpt_pat_enct_idx) */ f.patient_num from ncats2i2b2demodata.observation_fact f where f.concept_cd IN (select concept_cd from  ncats2i2b2demodata.concept_dimension   where concept_path LIKE '\\ACT\\Demographics\\Race\\White\\%') AND  ( f.start_date >= to_date('01-Jan-2015 00:00:00','DD-MON-YYYY HH24:MI:SS') AND f.start_date <= to_date('01-Jan-2017 00:00:00','DD-MON-YYYY HH24:MI:SS') ) group by  f.patient_num ) select  t.patient_num, 0 as panel_count  from t";
		
//		•	Diagnoses/Diseases and injuries/Diseases of the respiratory system/Chronic obstructive pulmonary disease and allied conditions/Asthma ( Done )
		
//		String query = "insert into ncats2i2b2demodata.QUERY_GLOBAL_TEMP (patient_num, panel_count) with t as ( select  /*+ index(observation_fact fact_cnpt_pat_enct_idx) */ f.patient_num from ncats2i2b2demodata.observation_fact f where f.concept_cd IN (select concept_cd from  ncats2i2b2demodata.concept_dimension   where concept_path LIKE '\\Diagnoses\\(J00-J99) Dise~45pn\\(J40-J47) Chro~fbox\\(J45) Asthma\\%') group by  f.patient_num ) select  t.patient_num, 0 as panel_count  from t";
		
//		•	Diagnoses/Diseases and injuries/Diseases of the respiratory system/Chronic obstructive pulmonary disease and allied conditions/Asthma Date range: 1/1/2015 – 1/1/2017 ( Done )
		
//		String query = "insert into ncats2i2b2demodata.QUERY_GLOBAL_TEMP (patient_num, panel_count) with t as ( select  /*+ index(observation_fact fact_cnpt_pat_enct_idx) */ f.patient_num from ncats2i2b2demodata.observation_fact f where f.concept_cd IN (select concept_cd from  ncats2i2b2demodata.concept_dimension   where concept_path LIKE '\\Diagnoses\\(J00-J99) Dise~45pn\\(J40-J47) Chro~fbox\\(J45) Asthma\\%') AND  ( f.start_date >= to_date('01-Jan-2015 00:00:00','DD-MON-YYYY HH24:MI:SS') AND f.start_date <= to_date('01-Jan-2017 00:00:00','DD-MON-YYYY HH24:MI:SS') ) group by  f.patient_num ) select  t.patient_num, 0 as panel_count  from t";
		
//		•	Diagnoses/Diseases and injuries/Diseases of the respiratory system ( Done )
		
//		String query = "insert into ncats2i2b2demodata.QUERY_GLOBAL_TEMP (patient_num, panel_count) with t as (select  /*+ index(observation_fact fact_cnpt_pat_enct_idx) */ f.patient_num from ncats2i2b2demodata.observation_fact f where f.concept_cd IN (select concept_cd from  ncats2i2b2demodata.concept_dimension   where concept_path LIKE '\\Diagnoses\\(J00-J99) Dise~45pn\\%') group by  f.patient_num ) select  t.patient_num, 0 as panel_count  from t";
		
//		•	Diagnoses/Diseases and injuries/Diseases of the respiratory system Date range: 1/1/2015 – 1/1/2017 ( Done )
		
//		String query = "insert into ncats2i2b2demodata.QUERY_GLOBAL_TEMP (patient_num, panel_count) with t as ( select  /*+ index(observation_fact fact_cnpt_pat_enct_idx) */ f.patient_num from ncats2i2b2demodata.observation_fact f where f.concept_cd IN (select concept_cd from  ncats2i2b2demodata.concept_dimension   where concept_path LIKE '\\Diagnoses\\(J00-J99) Dise~45pn\\%') AND  ( f.start_date >= to_date('01-Jan-2015 00:00:00','DD-MON-YYYY HH24:MI:SS') AND f.start_date <= to_date('01-Jan-2017 00:00:00','DD-MON-YYYY HH24:MI:SS') ) group by  f.patient_num ) select  t.patient_num, 0 as panel_count  from t";
		
//		•	Laboratory Tests/Chemistry/Electrolytes/Sodium/Sodium in Serum or Plasma ( Done )
		
//		String query = "insert into ncats2i2b2demodata.QUERY_GLOBAL_TEMP (patient_num, panel_count) with t as ( select  /*+ index(observation_fact fact_cnpt_pat_enct_idx) */ f.patient_num from ncats2i2b2demodata.observation_fact f where f.concept_cd IN (select concept_cd from  ncats2i2b2demodata.concept_dimension   where concept_path LIKE '\\ACT\\Labs\\LP40271-6\\LP19403-2\\LP48861-6\\2951-2\\%') group by  f.patient_num ) select  t.patient_num, 0 as panel_count  from t"; 
				
//		•	Laboratory Tests/Chemistry/Electrolytes/Sodium/Sodium in Serum or Plasma < 140 ( Done )
		
//		String query = "insert into ncats2i2b2demodata.QUERY_GLOBAL_TEMP (patient_num, panel_count) with t as ( select  /*+ index(observation_fact fact_cnpt_pat_enct_idx) */ f.patient_num from ncats2i2b2demodata.observation_fact f where f.concept_cd IN (select concept_cd from  ncats2i2b2demodata.concept_dimension   where concept_path LIKE '\\ACT\\Labs\\LP40271-6\\LP19403-2\\LP48861-6\\2951-2\\%') AND  (  modifier_cd = '@'  AND    (( valtype_cd = 'N' AND   nval_num  < 140 AND  tval_char IN ('E','LE')) OR ( valtype_cd = 'N' AND   nval_num  <= 140 AND  tval_char = 'L' ))  ) group by  f.patient_num ) select  t.patient_num, 0 as panel_count  from t";
		
		
//		•	Laboratory Tests/Chemistry/Electrolytes ( Done )
		
//		String query = "insert into ncats2i2b2demodata.QUERY_GLOBAL_TEMP (patient_num, panel_count) with t as ( select  /*+ index(observation_fact fact_cnpt_pat_enct_idx) */ f.patient_num from ncats2i2b2demodata.observation_fact f where f.concept_cd IN (select concept_cd from  ncats2i2b2demodata.concept_dimension   where concept_path LIKE '\\ACT\\Labs\\LP40271-6\\LP19403-2\\%') group by  f.patient_num ) select  t.patient_num, 0 as panel_count  from t"; 
		
//		•	Laboratory Tests/Chemistry/Electrolytes/Sodium/Sodium in Serum or Plasma Date range: 1/1/2015 – 1/1/2017 ( Done )
		
//		String query = "insert into ncats2i2b2demodata.QUERY_GLOBAL_TEMP (patient_num, panel_count) with t as ( select  /*+ index(observation_fact fact_cnpt_pat_enct_idx) */ f.patient_num from ncats2i2b2demodata.observation_fact f where f.concept_cd IN (select concept_cd from  ncats2i2b2demodata.concept_dimension   where concept_path LIKE '\\ACT\\Labs\\LP40271-6\\LP19403-2\\LP48861-6\\2951-2\\%') AND  ( f.start_date >= to_date('01-Jan-2015 00:00:00','DD-MON-YYYY HH24:MI:SS') AND f.start_date <= to_date('01-Jan-2017 00:00:00','DD-MON-YYYY HH24:MI:SS') ) group by  f.patient_num ) select  t.patient_num, 0 as panel_count  from t";  
						
 
//		•	Laboratory Tests/Chemistry/Electrolytes/Sodium/Sodium in Serum or Plasma < 140 Date range: 1/1/2015 – 1/1/2017 ( Done )
		
//		String query = "insert into ncats2i2b2demodata.QUERY_GLOBAL_TEMP (patient_num, panel_count) with t as ( select  /*+ index(observation_fact fact_cnpt_pat_enct_idx) */ f.patient_num from ncats2i2b2demodata.observation_fact f where f.concept_cd IN (select concept_cd from  ncats2i2b2demodata.concept_dimension   where concept_path LIKE '\\ACT\\Labs\\LP40271-6\\LP19403-2\\LP48861-6\\2951-2\\%') AND  (  modifier_cd = '@'  AND    (( valtype_cd = 'N' AND   nval_num  < 140 AND  tval_char IN ('E','LE')) OR ( valtype_cd = 'N' AND   nval_num  <= 140 AND  tval_char = 'L' ))  ) AND  ( f.start_date >= to_date('01-Jan-2015 00:00:00','DD-MON-YYYY HH24:MI:SS') AND f.start_date <= to_date('01-Jan-2017 00:00:00','DD-MON-YYYY HH24:MI:SS') ) group by  f.patient_num ) select  t.patient_num, 0 as panel_count  from t";
		
		
//		•	Laboratory Tests/Chemistry/Electrolytes Date range: 1/1/2015 – 1/1/2017 ( Done )
		
//		String query = "insert into ncats2i2b2demodata.QUERY_GLOBAL_TEMP (patient_num, panel_count) with t as ( select  /*+ index(observation_fact fact_cnpt_pat_enct_idx) */ f.patient_num from ncats2i2b2demodata.observation_fact f where f.concept_cd IN (select concept_cd from  ncats2i2b2demodata.concept_dimension   where concept_path LIKE '\\ACT\\Labs\\LP40271-6\\LP19403-2\\%') AND  ( f.start_date >= to_date('01-Jan-2015 00:00:00','DD-MON-YYYY HH24:MI:SS') AND f.start_date <= to_date('01-Jan-2017 00:00:00','DD-MON-YYYY HH24:MI:SS') ) group by  f.patient_num ) select  t.patient_num, 0 as panel_count  from t";
		

//		•	Medications/Cardiovascular Medications/Ace Inhibitors/Captopril/Captopril Oral Tablet/Captopril 50 Mg Oral Tablet ( Done )
		
//		String query = "insert into ncats2i2b2demodata.QUERY_GLOBAL_TEMP (patient_num, panel_count) with t as ( select  /*+ index(observation_fact fact_cnpt_pat_enct_idx) */ f.patient_num from ncats2i2b2demodata.observation_fact f where f.concept_cd IN (select concept_cd from  ncats2i2b2demodata.concept_dimension   where concept_path LIKE '\\ACT\\Medications\\N0000010574\\N0000029116\\N0000029130\\1998\\371254\\308964\\%') group by  f.patient_num ) select  t.patient_num, 0 as panel_count  from t";
		
		
//		•	Medications/Cardiovascular Medications/Ace Inhibitors/Captopril ( Done )
		
//		String query = "insert into ncats2i2b2demodata.QUERY_GLOBAL_TEMP (patient_num, panel_count) with t as ( select  /*+ index(observation_fact fact_cnpt_pat_enct_idx) */ f.patient_num from ncats2i2b2demodata.observation_fact f where f.concept_cd IN (select concept_cd from  ncats2i2b2demodata.concept_dimension   where concept_path LIKE '\\ACT\\Medications\\N0000010574\\N0000029116\\N0000029130\\1998\\%') group by  f.patient_num ) select  t.patient_num, 0 as panel_count  from t"; 
		
		
		
//		•	Medications/Cardiovascular Medications/Ace Inhibitors ( Done )
		
//		String query = "insert into ncats2i2b2demodata.QUERY_GLOBAL_TEMP (patient_num, panel_count) with t as ( select  /*+ index(observation_fact fact_cnpt_pat_enct_idx) */ f.patient_num from ncats2i2b2demodata.observation_fact f where f.concept_cd IN (select concept_cd from  ncats2i2b2demodata.concept_dimension   where concept_path LIKE '\\ACT\\Medications\\N0000010574\\N0000029116\\N0000029130\\%') group by  f.patient_num ) select  t.patient_num, 0 as panel_count  from t";
		
		
//		•	Medications/Cardiovascular Medications ( Done )
		
//		String query = "insert into ncats2i2b2demodata.QUERY_GLOBAL_TEMP (patient_num, panel_count) with t as ( select  /*+ index(observation_fact fact_cnpt_pat_enct_idx) */ f.patient_num from ncats2i2b2demodata.observation_fact f where f.concept_cd IN (select concept_cd from  ncats2i2b2demodata.concept_dimension   where concept_path LIKE '\\ACT\\Medications\\N0000010574\\N0000029116\\%') group by  f.patient_num ) select  t.patient_num, 0 as panel_count  from t";
		
//		•	Medications/Cardiovascular Medications/Ace Inhibitors/Captopril/Captopril Oral Tablet/Captopril 50 Mg Oral Tablet Date range: 1/1/2015 – 1/1/2017 ( Done )
		
//		String query ="insert into ncats2i2b2demodata.QUERY_GLOBAL_TEMP (patient_num, panel_count) with t as ( select  /*+ index(observation_fact fact_cnpt_pat_enct_idx) */ f.patient_num from ncats2i2b2demodata.observation_fact f where f.concept_cd IN (select concept_cd from  ncats2i2b2demodata.concept_dimension   where concept_path LIKE '\\ACT\\Medications\\N0000010574\\N0000029116\\N0000029130\\1998\\371254\\308964\\%') AND  ( f.start_date >= to_date('01-Jan-2015 00:00:00','DD-MON-YYYY HH24:MI:SS') AND f.start_date <= to_date('01-Jan-2017 00:00:00','DD-MON-YYYY HH24:MI:SS') ) group by  f.patient_num ) select  t.patient_num, 0 as panel_count  from t"; 
						
//		•	Medications/Cardiovascular Medications/Ace Inhibitors/Captopril Date range: 1/1/2015 – 1/1/2017 ( Done )
		
//		String query = "insert into ncats2i2b2demodata.QUERY_GLOBAL_TEMP (patient_num, panel_count) with t as ( select  /*+ index(observation_fact fact_cnpt_pat_enct_idx) */ f.patient_num from ncats2i2b2demodata.observation_fact f where f.concept_cd IN (select concept_cd from  ncats2i2b2demodata.concept_dimension   where concept_path LIKE '\\ACT\\Medications\\N0000010574\\N0000029116\\N0000029130\\1998\\%') AND  ( f.start_date >= to_date('01-Jan-2015 00:00:00','DD-MON-YYYY HH24:MI:SS') AND f.start_date <= to_date('01-Jan-2017 00:00:00','DD-MON-YYYY HH24:MI:SS') ) group by  f.patient_num ) select  t.patient_num, 0 as panel_count  from t";
		
//		•	Medications/Cardiovascular Medications/Ace Inhibitors Date range: 1/1/2015 – 1/1/2017 ( Done )
		
		
//		String query = "insert into ncats2i2b2demodata.QUERY_GLOBAL_TEMP (patient_num, panel_count) with t as ( select  /*+ index(observation_fact fact_cnpt_pat_enct_idx) */ f.patient_num from ncats2i2b2demodata.observation_fact f where f.concept_cd IN (select concept_cd from  ncats2i2b2demodata.concept_dimension   where concept_path LIKE '\\ACT\\Medications\\N0000010574\\N0000029116\\N0000029130\\%') AND  ( f.start_date >= to_date('01-Jan-2015 00:00:00','DD-MON-YYYY HH24:MI:SS') AND f.start_date <= to_date('01-Jan-2017 00:00:00','DD-MON-YYYY HH24:MI:SS') ) group by  f.patient_num ) select  t.patient_num, 0 as panel_count  from t";

//		•	Medications/Cardiovascular Medications Date range: 1/1/2015 – 1/1/2017 ( Done )

//		String query = "insert into ncats2i2b2demodata.QUERY_GLOBAL_TEMP (patient_num, panel_count) with t as ( select  /*+ index(observation_fact fact_cnpt_pat_enct_idx) */ f.patient_num from ncats2i2b2demodata.observation_fact f where f.concept_cd IN (select concept_cd from  ncats2i2b2demodata.concept_dimension   where concept_path LIKE '\\ACT\\Medications\\N0000010574\\N0000029116\\%') AND  ( f.start_date >= to_date('01-Jan-2015 00:00:00','DD-MON-YYYY HH24:MI:SS') AND f.start_date <= to_date('01-Jan-2017 00:00:00','DD-MON-YYYY HH24:MI:SS') ) group by  f.patient_num ) select  t.patient_num, 0 as panel_count  from t"; 


//		•	Procedures/Operations on the digestive system/Operations on liver/Liver transplant ( Done )
		
//		String query = "insert into ncats2i2b2demodata.QUERY_GLOBAL_TEMP (patient_num, panel_count) with t as ( select  /*+ index(observation_fact fact_cnpt_pat_enct_idx) */ f.patient_num from ncats2i2b2demodata.observation_fact f where f.concept_cd IN (select concept_cd from  ncats2i2b2demodata.CONCEPT_DIMENSION   where concept_path LIKE '\\ACT\\PROCEDURE\\09\\(42-54.99) Ope~bzgq\\(50) Operation~5uiv\\(50.5) Liver t~mdf7\\%') group by  f.patient_num ) select  t.patient_num, 0 as panel_count  from t"; 
		
		
//		•	Procedures/Operations on the digestive system ( Done )
		
//		String query = "insert into ncats2i2b2demodata.QUERY_GLOBAL_TEMP (patient_num, panel_count) with t as ( select  /*+ index(observation_fact fact_cnpt_pat_enct_idx) */ f.patient_num from ncats2i2b2demodata.observation_fact f where f.concept_cd IN (select concept_cd from  ncats2i2b2demodata.CONCEPT_DIMENSION   where concept_path LIKE '\\ACT\\PROCEDURE\09\\(42-54.99) Ope~bzgq\\%') group by  f.patient_num ) select  t.patient_num, 0 as panel_count  from t";
		
				
//		•	Procedures/Operations on the digestive system/Operations on liver/Liver transplant Date range: 1/1/2015 – 1/1/2017 ( Done )
		
//		String query = "insert into ncats2i2b2demodata.QUERY_GLOBAL_TEMP (patient_num, panel_count) with t as ( select  /*+ index(observation_fact fact_cnpt_pat_enct_idx) */ f.patient_num from ncats2i2b2demodata.observation_fact f where f.concept_cd IN (select concept_cd from  ncats2i2b2demodata.CONCEPT_DIMENSION   where concept_path LIKE '\\ACT\\PROCEDURE\\09\\(42-54.99) Ope~bzgq\\(50) Operation~5uiv\\(50.5) Liver t~mdf7\\%') AND  ( f.start_date >= to_date('01-Jan-2015 00:00:00','DD-MON-YYYY HH24:MI:SS') AND f.start_date <= to_date('01-Jan-2017 00:00:00','DD-MON-YYYY HH24:MI:SS') ) group by  f.patient_num ) select  t.patient_num, 0 as panel_count  from t"; 
		
//		•	Procedures/Operations on the digestive system Date range: 1/1/2015 – 1/1/2017 ( Done )
		
//		String query = "insert into ncats2i2b2demodata.QUERY_GLOBAL_TEMP (patient_num, panel_count) with t as ( select  /*+ index(observation_fact fact_cnpt_pat_enct_idx) */ f.patient_num from ncats2i2b2demodata.observation_fact f where f.concept_cd IN (select concept_cd from  ncats2i2b2demodata.CONCEPT_DIMENSION   where concept_path LIKE '\\ACT\\PROCEDURE\\09\\(42-54.99) Ope~bzgq\\%') AND  ( f.start_date >= to_date('01-Jan-2015 00:00:00','DD-MON-YYYY HH24:MI:SS') AND f.start_date <= to_date('01-Jan-2017 00:00:00','DD-MON-YYYY HH24:MI:SS') ) group by  f.patient_num ) select  t.patient_num, 0 as panel_count  from t";
		

//		•	Visit Details/Age at visit/Visit at ages 45-54 years old ( Done )
		
//		String query = "insert into ncats2i2b2demodata.QUERY_GLOBAL_TEMP (patient_num, panel_count) with t as ( select e.patient_num from ncats2i2b2demodata.visit_dimension e where e.encounter_num IN (select encounter_num from  ncats2i2b2demodata.visit_dimension   where start_date BETWEEN ((select birth_date  from PATIENT_DIMENSION where patient_num =VISIT_DIMENSION.PATIENT_NUM) + (365.25 * 45)-1) AND ((select birth_date  from PATIENT_DIMENSION where patient_num =VISIT_DIMENSION.PATIENT_NUM) + (365.25 * 55)-1)) group by  e.patient_num ) select  t.patient_num, 0 as panel_count  from t";
		
//		•	Visit Details / Length of stay/4 days ( Done )
		
//		String query = "insert into ncats2i2b2demodata.QUERY_GLOBAL_TEMP (patient_num, panel_count) with t as ( select e.patient_num from ncats2i2b2demodata.visit_dimension e where e.encounter_num IN (select encounter_num from  ncats2i2b2demodata.visit_dimension   where length_of_stay = 4) group by  e.patient_num ) select  t.patient_num, 0 as panel_count  from t";  
//		
//		•	Visit Details/Visit type/Ambulatory Visit ( Done )
		
//		String query = "insert into ncats2i2b2demodata.QUERY_GLOBAL_TEMP (patient_num, panel_count) with t as ( select e.patient_num from ncats2i2b2demodata.visit_dimension e where e.encounter_num IN (select encounter_num from  ncats2i2b2demodata.visit_dimension   where inout_cd = 'O') group by  e.patient_num ) select  t.patient_num, 0 as panel_count  from t";
		
//		•         Visit Details/Visit type/Ambulatory Visit Date range: 1/1/2015 – 1/12017 ( Done )
		
		
		String query = "insert into ncats2i2b2demodata.QUERY_GLOBAL_TEMP (patient_num, panel_count) with t as ( select e.patient_num from ncats2i2b2demodata.visit_dimension e where e.encounter_num IN (select encounter_num from  ncats2i2b2demodata.visit_dimension   where inout_cd = 'O') AND  ( e.start_date >= to_date('01-Jan-2015 00:00:00','DD-MON-YYYY HH24:MI:SS') AND e.start_date <= to_date('01-Jan-2017 00:00:00','DD-MON-YYYY HH24:MI:SS') ) group by  e.patient_num ) select  t.patient_num, 0 as panel_count  from t";
		
		
	
//		String query = "select count(distinct patient_num) from OBSERVATION_FACT where CONCEPT_CD IN (select concept_cd from CONCEPT_DIMENSION where concept_path like '\\ACT\\Demographics\\Vital Status\\Known Deceased\\%')";

//		String query2 =	"set timing on"; 
//				for (int second = 0;; second++) {

		
//		String query = "insert into ncats2i2b2demodata.QUERY_GLOBAL_TEMP (patient_num, panel_count) with t as (select /*+ index(observation_fact fact_cnpt_pat_enct_idx) */ f.patient_num from ncats2i2b2demodata.observation_fact f where f.concept_cd IN (select concept_cd from  ncats2i2b2demodata.concept_dimension where concept_path LIKE '\\ACT\\Labs\\LP40271-6\\LP19403-2\\%')  AND ( f.start_date >= to_date('01-Jan-2015 00:00:00','DD-MON-YYYY HH24:MI:SS') AND f.start_date <= to_date('01-Jan-2017 00:00:00','DD-MON-YYYY HH24:MI:SS')) AND ( f.start_date >= to_date('01-Jan-2015 00:00:00','DD-MON-YYYY HH24:MI:SS') AND f.start_date <= to_date('01-Jan-2017 00:00:00','DD-MON-YYYY HH24:MI:SS') ) group by  f.patient_num ) select  t.patient_num, 0 as panel_count  from t";
//		String query = "insert into ncats2i2b2demodata.QUERY_GLOBAL_TEMP (patient_num, panel_count) with t as (select /*+ index(observation_fact fact_cnpt_pat_enct_idx) */ f.patient_num from ncats2i2b2demodata.observation_fact f where f.concept_cd IN (select concept_cd from  ncats2i2b2demodata.concept_dimension   where concept_path LIKE '\\Diagnoses\\(J00-J99) Dise~45pn\\(J40-J47) Chro~fbox\\(J45) Asthma\\%') group by  f.patient_num ) select  t.patient_num, 0 as panel_count  from t";   

//		String query = "insert into ncats2i2b2demodata.QUERY_GLOBAL_TEMP (patient_num, panel_count) " +
//
//					   "with t as ( select  /*+ index(observation_fact fact_cnpt_pat_enct_idx) */ f.patient_num " +  
//					   "from ncats2i2b2demodata.observation_fact f " + 
//					   "where " +  
//					   "f.concept_cd IN (select concept_cd from  ncats2i2b2demodata.concept_dimension   where concept_path LIKE '\\ACT\\Demographics\\Hispanic\\Yes\\%') " +   
//					   "group by  f.patient_num ) " + 
//					   "select t.patient_num, 0 as panel_count from t";
		
//		String query = "insert into ncats2i2b2demodata.QUERY_GLOBAL_TEMP (patient_num, panel_count) " 
//
//					  + "with t as ( select  /*+ index(observation_fact fact_cnpt_pat_enct_idx) */ f.patient_num "  
//					  + "from ncats2i2b2demodata.observation_fact f " 
//					  + "where "  
//					  + "f.concept_cd IN (select concept_cd from  ncats2i2b2demodata.concept_dimension   where concept_path LIKE '\\ACT\\Demographics\\Hispanic\\Yes\\%')"   
//					  + "group by  f.patient_num ) "  
//					   + "select t.patient_num, 0 as panel_count from t";
					   
		
//		String query = "insert into ncats2i2b2demodata.QUERY_GLOBAL_TEMP (patient_num, panel_count)"
//				+ "with t as ( select  /*+ index(observation_fact fact_cnpt_pat_enct_idx) */ f.patient_num from ncats2i2b2demodata.observation_fact f"
//				+ " where f.concept_cd IN (select concept_cd from  ncats2i2b2demodata.concept_dimension"
//				+ " where concept_path LIKE '\\ACT\\Demographics\\Sex\\Male\\%') group by  f.patient_num ) "
//				+ " select  t.patient_num, 0 as panel_count  from t"; 
 

		
//		String query = "insert into ncats2i2b2demodata.QUERY_GLOBAL_TEMP (patient_num, panel_count) with t as (select  /*+ index(observation_fact fact_cnpt_pat_enct_idx) */ f.patient_num from ncats2i2b2demodata.observation_fact f where f.concept_cd IN (select concept_cd from  ncats2i2b2demodata.concept_dimension   where concept_path LIKE '\\ACT\\Labs\\LP40271-6\\LP19403-2\\%') AND  ( f.start_date >= to_date('01-Dec-2015 00:00:00','DD-MON-YYYY HH24:MI:SS') AND f.start_date <= to_date('01-Jan-2017 00:00:00','DD-MON-YYYY HH24:MI:SS') ) group by  f.patient_num ) select  t.patient_num, 0 as panel_count  from t";
//		String query = "insert into ncats2i2b2demodata.QUERY_GLOBAL_TEMP (patient_num, panel_count) with t as ( select  /*+ index(observation_fact fact_cnpt_pat_enct_idx) */ f.patient_num from ncats2i2b2demodata.observation_fact f where f.concept_cd IN (select concept_cd from  ncats2i2b2demodata.concept_dimension   where concept_path LIKE '\\ACT\\Medications\\N0000010574\\N0000029116\\%') AND  ( f.start_date >= to_date('01-Jan-2015 00:00:00','DD-MON-YYYY HH24:MI:SS') AND f.start_date <= to_date('01-Jan-2017 00:00:00','DD-MON-YYYY HH24:MI:SS') ) group by  f.patient_num ) select  t.patient_num, 0 as panel_count  from t";
//		String query = "insert into ncats2i2b2demodata.QUERY_GLOBAL_TEMP (patient_num, panel_count) with t as ( select  /*+ index(observation_fact fact_cnpt_pat_enct_idx) */ f.patient_num from ncats2i2b2demodata.observation_fact f where f.concept_cd IN (select concept_cd from  ncats2i2b2demodata.concept_dimension   where concept_path LIKE '\\ACT\\Labs\\LP40271-6\\LP19403-2\\LP48861-6\\2951-2\\%') group by  f.patient_num ) select  t.patient_num, 0 as panel_count  from t "; 			
//		
//		String query = "insert into ncats2i2b2demodata.QUERY_GLOBAL_TEMP (patient_num, panel_count) with t as ( select  /*+ index(observation_fact fact_cnpt_pat_enct_idx) */ f.patient_num from ncats2i2b2demodata.observation_fact f where f.concept_cd IN (select concept_cd from  ncats2i2b2demodata.concept_dimension   where concept_path LIKE '\\ACT\\Labs\\LP40271-6\\LP19403-2\\LP48861-6\\2951-2\\%') AND  (  modifier_cd = '@'  AND    (( valtype_cd = 'N') AND  ( f.start_date >= to_date('01-Jan-2015 00:00:00','DD-MON-YYYY HH24:MI:SS') AND f.start_date <= to_date('01-Jan-2017 00:00:00','DD-MON-YYYY HH24:MI:SS') ) group by  f.patient_num ) select  t.patient_num, 0 as panel_count  from t";
		
//		String query = "insert into ncats2i2b2demodata.QUERY_GLOBAL_TEMP (patient_num, panel_count) with t as ( select  /*+ index(observation_fact fact_cnpt_pat_enct_idx) */ f.patient_num from ncats2i2b2demodata.observation_fact f where f.concept_cd IN (select concept_cd from  ncats2i2b2demodata.concept_dimension   where concept_path LIKE '\\ACT\\Labs\\LP40271-6\\LP19403-2\\%') group by  f.patient_num ) select  t.patient_num, 0 as panel_count  from t"; 		
//		String query = "insert into ncats2i2b2demodata.QUERY_GLOBAL_TEMP (patient_num, panel_count) with t as ( select  /*+ index(observation_fact fact_cnpt_pat_enct_idx) */ f.patient_num from ncats2i2b2demodata.observation_fact f where f.concept_cd IN (select concept_cd from  ncats2i2b2demodata.concept_dimension   where concept_path LIKE '\\ACT\\Labs\\LP40271-6\\LP19403-2\\LP48861-6\\2951-2\\%') AND  (  modifier_cd = '@'  AND    (( valtype_cd = 'N' AND tval_char IN ('E','LE')) OR ( valtype_cd = 'N' AND  tval_char = 'L' ))  ) group by  f.patient_num ) select  t.patient_num, 0 as panel_count  from t ";		
		
//		String query = "insert into ncats2i2b2demodata.QUERY_GLOBAL_TEMP (patient_num, panel_count) with t as ( select  /*+ index(observation_fact fact_cnpt_pat_enct_idx) */ f.patient_num from ncats2i2b2demodata.observation_fact f where f.concept_cd IN (select concept_cd from  ncats2i2b2demodata.concept_dimension   where concept_path LIKE '\\ACT\\Labs\\LP40271-6\\LP19403-2\\%') AND  ( f.start_date >= to_date('01-Jan-2015 00:00:00','DD-MON-YYYY HH24:MI:SS') AND f.start_date <= to_date('01-Jan-2017 00:00:00','DD-MON-YYYY HH24:MI:SS') ) group by  f.patient_num ) select  t.patient_num, 0 as panel_count  from t "; 
		
//		String query = "insert into ncats2i2b2demodata.QUERY_GLOBAL_TEMP (patient_num, panel_count) with t as ( select  /*+ index(observation_fact fact_cnpt_pat_enct_idx) */ f.patient_num from ncats2i2b2demodata.observation_fact f where f.concept_cd IN (select concept_cd from  ncats2i2b2demodata.concept_dimension   where concept_path LIKE '\\ACT\\Labs\\LP40271-6\\LP19403-2\\LP48861-6\\2951-2\\%') AND  ( f.start_date >= to_date('01-Jan-2015 00:00:00','DD-MON-YYYY HH24:MI:SS') AND f.start_date <= to_date('01-Jan-2017 00:00:00','DD-MON-YYYY HH24:MI:SS') ) group by  f.patient_num ) select  t.patient_num, 0 as panel_count  from t "; 
		
//		Get Connection to DB
		Connection con = DriverManager.getConnection(dbUrl, userName, password);
		
		System.out.println("connected to database");
		
//		Statement for DB connection
		Statement stmt = con.createStatement();
//		Send SQL query to DB
		long st = System.currentTimeMillis();
		stmt.setQueryTimeout(180);
		stmt.executeQuery(query);
		
//		ResultSet rs2 = stmt.executeQuery(query2);
		long et = System.currentTimeMillis();
		long elps = et - st;
		Reporter.log("Query:" + query);
		Reporter.log(elps + " ms");
		
//		System.out.println("Query:" + query2);
//		while (rs.next()){
//			String where = rs.getString(1);
//			System.out.println(where);
//		}
//		System.out.println(query2);
		
//		close connection
		con.close();
		
	}
}