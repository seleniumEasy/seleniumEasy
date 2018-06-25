package com.easySelenium.common;

import java.io.IOException;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.easySelenium.generic.Excel;
import com.easySelenium.generic.Utility;

import common.BasePage;

public class CustomListener implements ITestListener
{	
	static int passcount=0;
	static int failcount=0;

	@Override
	public void onTestFailure(ITestResult result) {
		failcount++;
		String name=result.getName();
		try {
			BasePage.getScreenshot(name);
			Excel.setStatus(name,"Failed");
		} catch (IOException exception) {
			// TODO Auto-generated catch-block stub.
			exception.printStackTrace();
		}
		String folder=CommonConstant.SNAP_PATH;
		Utility.getDesktopScreenshot(folder);
	}
	
	@Override
	public void onTestStart(ITestResult result) {
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		passcount++;
		String name=result.getName();
		try {
			BasePage.getScreenshot(name);
			Excel.setStatus(name,"Pass");
		} catch (IOException exception) {
			// TODO Auto-generated catch-block stub.
			exception.printStackTrace();
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFinish(ITestContext context) {
		Reporter.log("Passed: "+passcount);
		Reporter.log("Failed: "+failcount);
		// TODO Auto-generated method stub
		
	}

}
