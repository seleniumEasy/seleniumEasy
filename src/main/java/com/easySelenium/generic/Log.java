package com.easySelenium.generic;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * TODO Put here a description of what this class does.
 *
 * @author ssamaji.
 *         Created Jun 22, 2018.
 */
public class Log {

	// initialize log4j logs
	private static Logger Log=Logger.getLogger(Log.class.getName());

	static
	{
	PropertyConfigurator.configure("Log4j.properties");
	}
	
	//This is to print logs at the beggining of the test case
	public static void startTestCase(String eTestCaseName) {
		Log.info("*********************************************************************************");
		Log.info("*********************************************************************************");
		Log.info("$$$$$$$$$$$$$$$$$$$$$$$       "+eTestCaseName+"        $$$$$$$$$$$$$$$$$$$$$$$$$$");
		Log.info("*********************************************************************************");
		Log.info("*********************************************************************************");
	}
	//This is to print logs at the end of the test case
	public static void endTestCase(String eTestCaseName){
		Log.info("*********************************************************************************");
		Log.info("*********************************************************************************");
		Log.info("$$$$$$$$$$$$$$$$$$$$$$$       END TEST CASE        $$$$$$$$$$$$$$$$$$$$$$$$$$");
		Log.info("*********************************************************************************");
		Log.info("*********************************************************************************");
	}
	
	public static void info(String message){
		Log.info(message);
	}
	
	public static void warn(String message){
		Log.warn(message);
	}
	
	public static void error(String message){
		Log.error(message);
	}
	
	public static void fatal(String message){
		Log.fatal(message);
	}
	
	public static void debug(String message){
		Log.debug(message);
	}
}
