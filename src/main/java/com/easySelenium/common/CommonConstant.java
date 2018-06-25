package com.easySelenium.common;

/**
 * TODO Put here a description of what this class does.
 *
 * @author ssamaji.
 *         Created May 25, 2018.
 */
public interface CommonConstant {
	
	//Paths
	public static  final String ACTUAL_PATH="./actual/";
	public static  final String CONFIG_PATH="./config/";
	public static  final String DRIVER_PATH="./driver/";
	public static  final String EXPECTED_PATH="./expected/";
	public static  final String JARS_PATH="./jars/";
	public static  final String REPORT_PATH="./report/";
	public static  final String SNAP_PATH="./snap/";
	public static 	final String INPUT_PATH="./data/input.xlsx";
	
	
	//Files Names
	public static final String EXTEND_REPORT_CONFIG_FILE="extend-config.xml";
	public static final String EXTEND_REPORT_HTML_FILE="STMExtentReport.html";
	
	public static  final String CONFIG_FILE="config.properties";
	
	public static  final String CHROME_FILE="chromedriver.exe";
	public static  final String GECKO_FILE="geckodriver.exe";

	public static final String EXCEL_REPORT="report.xls";
	public static  final String REPORT_FILE="report.html";
	
	public static  final String CHROME_KEY="webdriver.chrome.driver";
	public static  final String GECKO_KEY="webdriver.gecko.driver";
		
	
	// Common Constants
	public static final String StartWith="StartWith";
	public static final String EndWith="EndWith";
	public static final String Contains= "Contains" ;
	public static final String Equals= "Equals" ;

	
	public final static String MAIL_HOST = "smtp.office365.com"; // "mail.host";
	public final static String MAIL_PORT = "587"; // "mail.port";
	public final static String MAIL_SSL = "TLS v1-1.2";
	public final static String MAIL_USER_NAME = "seleniumeasys@gmail.com"; // "mail.username";
	public final static String MAIL_PASSWORD = "selenium@easy"; // "mail.password";
	public final static String GIT_PASSWORD = "selenium@easy2018"; 
	public final static String MAIL_FROM = "mail.from";
	public final static String MAIL_TO = "mail.to";
	public final static String MAIL_CC = "mail.cc";
	public final static String MAIL_SUBJECT = "mail.subject";
	public final static String MAIL_CONTENT = "mail.content";
	public final static String MAIL_ENABLE = "mail.send";
	public final static String MAIL_TO_EMAIL_ID="avinashdp@gmail.com";
	public final static String EMAIL_CC_EMAIL_ID = "ssamaji22@gmail.com";

}
