package com.easySelenium.common;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.easySelenium.generic.Excel;
import com.easySelenium.generic.FileOperations;
import com.easySelenium.generic.Property;
import com.easySelenium.generic.Utility;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.easySelenium.generic.Log;
import test.inputform.CheckBoxTest;

@Listeners(CustomListener.class)
public class BaseTest implements CommonConstant
{
	public static WebDriver driver;
	public ExtentTest testReport;
	
	public static String url;
	public static long timeout;
	public static ExtentReports eReport;
	protected String m_currentTestClassName = null;
	private String m_currentTestCaseName = null;
	private String m_currentTestCaseDescription = null;
	protected static boolean m_isTestPassed = false;
	
	/**
	 * Constructor
	 * TODO Put here a description of what this method does.
	 *
	 * @param browser
	 * @throws MalformedURLException
	 */
	public BaseTest(String description, Class<?> classname){
		m_currentTestCaseDescription=description;
		m_currentTestCaseName=classname.getSimpleName();
	}
		
	@Parameters({"browser"})
	@BeforeSuite
	public void initApplication(@Optional("chrome") String  browser) throws MalformedURLException{
		Log.info("initializing ExtentReport");

		eReport=new ExtentReports (CommonConstant.REPORT_PATH+"STMExtentReport.html", true);
		eReport.loadConfig(new File(System.getProperty("user.dir")+"/extent-config.xml"));
	
		url=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "URL");
		timeout=Long.parseLong(Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "IMPLICIT"));
		Log.info("Browser:"+browser);
		if(browser.equals("chrome")){
			System.setProperty(CHROME_KEY,DRIVER_PATH+CHROME_FILE);
			driver=new ChromeDriver();
			Log.info("executing scripts on Chrome Browser");
		} else {
			System.setProperty(GECKO_KEY,DRIVER_PATH+GECKO_FILE);
			driver=new FirefoxDriver();
			Log.info("executing scripts on FireFox Browser");
		}
		driver.manage().window().maximize();
		driver.get(url);
		Log.info("TimeOut:"+timeout);
		driver.manage().timeouts().implicitlyWait(timeout,TimeUnit.SECONDS);
		FileOperations.deleteFile(CommonConstant.REPORT_PATH,CommonConstant.EXCEL_REPORT);
		Excel.createExcelFile();
	}

	@BeforeClass
	public void beforeClass(){
		testReport = eReport.startTest(String.format("TestCase - %s, started @ %s \n", m_currentTestCaseName,
				Utility.getFormatedDate("yyyy-MM-dd HH:mm:ss.SSS ")));
		
	}
	
	@BeforeMethod
	public void beforeMethod(){
		m_isTestPassed=false;
		Log.info(String.format("TestCase - %s, started @ %s \n",m_currentTestCaseName,
				Utility.getFormatedDate("yyyy-MM-dd HH:mm:ss.SSS ")));
		Log.info(String.format("Description: %s.", m_currentTestCaseDescription));
	}
	
	@AfterMethod
	public void afterMethod(ITestResult result){
		testReport=new ExtentTest(result.getName(),result.getMethod().getDescription());
		if(result.getStatus()==ITestResult.FAILURE){
			testReport.log(LogStatus.FAIL,"Script failed check log for details");
			Log.info(result.getName()+" is failed");
		} else	{
			testReport.log(LogStatus.PASS, "stepName", "details");
			Log.info(result.getName()+" is passed");
		}
		Log.info(String.format("TestCase - %s, Completed @ %s \n",m_currentTestCaseName,
				Utility.getFormatedDate("yyyy-MM-dd HH:mm:ss.SSS ")));
		eReport.endTest(testReport);
	}
	
	private String getStatusMessage(){
		return (m_isTestPassed==true) ? " successfully.." : " with failures...";
	}
	
	@AfterClass
	public void postCondition(){
		
	}

	@AfterSuite
	public void cleanApplication(){
		eReport.flush();
		CustomReport customReport = new CustomReport();
		customReport.generateReports();
		driver.close();
	}
}





