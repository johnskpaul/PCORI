package com.dbmi.i2b2Pcori;

//import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

//import org.junit.Test;

public class RandomSelecti2b2PCORI {


	//Data base schema
		public static String getSchema()
		{
				String List [] = { 
						"ncats2"
//						"ncats3"
						
				};
				Random Dice = new Random(); 
				int random = Dice.nextInt(1);
					return (List[random]);
	}
				//Radio button 4
				public static int getRandomRadOpt4()
				{
		int random = new Random().nextInt(3) + 1;
		return(random);
				}
				
				//Radio button 6
				public static int getRandomRadOpt6()
				{
		int random = new Random().nextInt(5) + 1;
		return(random);
				}
				
				
				//Radio button 7
				public static int getRandomRadOpt7()
				{
		int random = new Random().nextInt(6) + 1;
		return(random);
				}
				//Radio button 8
				public static int getRandomRadOpt8()
				{
		int random = new Random().nextInt(7) + 1;
		return(random);
				}
				//Radio button 9
				public static int getRandomRadOpt9()
				{
		int random = new Random().nextInt(8) + 1;
		return(random);
				}
		
				//Radio button 10
				public static int getRandomRadOpt10()
				{
		int random = new Random().nextInt(9) + 1;
		return(random);
				}
				//Radio button 11
				public static int getRandomRadOpt11()
				{
		int random = new Random().nextInt(10) + 1;
		return(random);
				}
				
				//Radio button 11
				public static int getRandomRadOpt12()
				{
		int random = new Random().nextInt(11) + 1;
		return(random);
				}
				//Radio button 13
				public static int getRandomRadOpt13()
				{
		int random = new Random().nextInt(12) + 1;
		return(random);
				}
				//Radio button 14
				public static int getRandomRadOpt14()
				{
		int random = new Random().nextInt(13) + 1;
		return(random);
				}
				//Radio button 16
				public static int getRandomRadOpt16()
				{
		int random = new Random().nextInt(15) + 1;
		return(random);
				}
				//Radio button 22
				public static int getRandomRadOpt22()
				{
		int random = new Random().nextInt(21) + 1;
		return(random);
				}
				//Radio button 23
				public static int getRandomRadOpt23()
				{
		int random = new Random().nextInt(22) + 1;
		return(random);
				}
				
				//Radio button 26
				public static int getRandomRadOpt26()
				{
		int random = new Random().nextInt(25) + 1;
		return(random);
				}
				
				//Radio button 29
				public static int getRandomRadOpt29()
				{
		int random = new Random().nextInt(28) + 1;
		return(random);
				}
				//Radio button 29
				public static int getRandomRadOpt36()
				{
		int random = new Random().nextInt(35) + 1;
		return(random);
				}
		//Gender
		public static int getRandomRadioOpt3()
		{
		int random = new Random().nextInt(2) +1;
		return (random);
		}
		
		//Prospective or Retrospective
		public static int getRandomRadOpt2()
		{
				int random2 = new Random().nextInt(1) +1;
				return(random2);
		}
		
		//Further contact
		public static int getRandomFrCntact()
		{
		int randomFrtrcnt = new Random().nextInt(2);
		return(randomFrtrcnt);
		}
		
		//converting date
		
		public static String getRandomFutureDt()
		{
			DateFormat dateFormat= new SimpleDateFormat("MM/yyyy");
			Date date= new Date();
			String dateString= dateFormat.format(date);
			return (dateString);
		}
		//getting a number greater than 1 and less than 5
		public static int getRandomNum1to5()
		{
		int aNumber = 1 + (int) (Math.random() * ((5 - 1) +1));
		return(aNumber);
		}
		//getting a number greater than 12 and less than 20
				public static int getRandomNum12to20()
				{
				int aNumber = 12 + (int) (Math.random() * ((20 - 12) +1));
				return(aNumber);
				}
		//getting a number greater than 5 and less than 26
				public static int getRandomNum2to8()
				{
				int aNumber = 2 + (int) (Math.random() * ((8 - 2) + 1));
				return(aNumber);
				}
		
		//getting a number greater than 20 and less than 140
		public static int getRandom20to40()
		{
		int aNumber = 20 + (int) (Math.random() * ((40-19) + 1));
		return(aNumber);
		}		
		//getting a number greater than 50 and less than 101
		public static int getRandom20to100()
		{
		int aNumber = 20 + (int) (Math.random() * ((100-19) + 1));
		return(aNumber);
		}
		
		//getting a number greater than 150 and less than 201
		public static int getRandom120to200()
		{
		int aNumber = 120 + (int) (Math.random() * ((200 - 120) + 1));
		return(aNumber);
		}
		//Getting Random Number between 1900 to 2011
		public static int getRandom1900to1994()
		{
		int aNumber = 1900 + (int) (Math.random() * ((1994 -1900) + 1));
		return(aNumber);
		}
}
