package com.easySelenium.common;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.text.Utilities;

import org.apache.commons.io.FileUtils;

import com.easySelenium.generic.FileOperations;
import com.easySelenium.generic.Log;
import com.easySelenium.generic.Utility;
import com.marketshare.core.util.ZIPFileOperations;

/**
 * TODO Put here a description of what this class does.
 *
 * @author ssamaji.
 *         Created Jun 21, 2018.
 */
public class CustomReport {


	// Days to remove the report folders.
	private int m_daysBack = 30;

	// Holds today folder name.
	protected String m_todayFolder;

	// Holds Base Directory details.
	protected String m_baseDirectory = String.format("%s/easySeleniumReport",
			System.getProperty("user.dir").replace("target", ""));

	/**
	 * Constructor. We will delete the files which are older than 30 days and
	 * creates the new folder with today's date.
	 */
	public CustomReport() {

		// Create folder with today's date. In this folder we will be generating
		// the test results reports.
		m_todayFolder = Utility.getFormatedDate("MMddyyyy");

		// Remove report files which are older than 30 days.
		FileOperations.removeFilesOlderThanNDays(m_baseDirectory, m_daysBack);

		// Create the directory with today date if its not available means..
		FileOperations.createDirectory(getBaseDirWithTodayDate());
	}
	
	public String getBaseDirWithTodayDate() {
		return String.format("%s/%s", m_baseDirectory, m_todayFolder).replace("\\", "/");
	}
	
	public void generateReports(){
		StringBuilder m_builder = new StringBuilder();
		copyLogFile();
		copyCustomReportFile();
		copySummaryFile();
		copyScreenshots();
		FileOperations.zipFolder(getBaseDirWithTodayDate());
		String zipFolder= String.format("%s.zip", getBaseDirWithTodayDate());
		try {
			SendMail.execute(zipFolder);
		} catch (Exception exception) {
			// TODO Auto-generated catch-block stub.
			exception.printStackTrace();
		}
	}
	
	public void copyScreenshots(){
		File srcDir = FileOperations.getScreenshotFolder();
		File destDir = new File (getBaseDirWithTodayDate() , srcDir.getName());
		try {
			if (srcDir.exists()) {
				FileUtils.copyDirectory(srcDir , destDir);
				FileUtils.cleanDirectory(srcDir);
				Log.info("Screenshots copied successfully ");
			}
		} catch (IOException ie) {
			Log.error( ie.toString());
		} catch (NullPointerException npe){
			Log.error(npe.toString());
		}
	}
	
	
	public void copyLogFile(){
		String logPath = String.format("%s/log/testlog.log",System.getProperty("user.dir"));
		File logFile = new File(logPath);
		String destinationLogPath = String.format("%s/testlog.log",getBaseDirWithTodayDate());
		File destinationFile = new File(destinationLogPath);
		//try{
			if (logFile.exists()) {
				FileOperations.copyFile(logFile , destinationFile);
				//FileUtils.cleanDirectory(destinationFile);
				Log.info("Logs file copied successfully ");
			} else
				Log.error("Destination report base directory doesn't found ");
//		} catch (IOException exception) {
//			Log.error(exception.toString());
//		} 
	}
	public void copySummaryFile(){
		String reportPath = String.format("%s/report/"+CommonConstant.EXCEL_REPORT,System.getProperty("user.dir"));
		File reportFile = new File(reportPath);
		String destinationLogPath = String.format("%s/"+CommonConstant.EXCEL_REPORT,getBaseDirWithTodayDate());
		File destinationFile = new File(destinationLogPath);
		//try{
			if (reportFile.exists()) {
				FileOperations.copyFile(reportFile , destinationFile);
			//	FileUtils.cleanDirectory(destinationFile);
				Log.info("Summary Report copied successfully ");
				
			} else
				Log.error("Destination report base directory doesn't found ");
//		} catch (IOException io){
//			Log.error(io.toString());
//		}
	}
	
	public void copyCustomReportFile(){
		String reportPath = String.format("%s/report/"+CommonConstant.EXTEND_REPORT_HTML_FILE,System.getProperty("user.dir"));
		File reportFile = new File(reportPath);
		String destinationLogPath = String.format("%s/"+CommonConstant.EXTEND_REPORT_HTML_FILE,getBaseDirWithTodayDate());
		File destinationFile = new File(destinationLogPath);
		//try{
			if (reportFile.exists()) {
				FileOperations.copyFile(reportFile , destinationFile);
			//	FileUtils.cleanDirectory(destinationFile);
				Log.info("Custom Report copied successfully ");
				
			} else
				Log.error("Destination report base directory doesn't found ");
//		} catch (IOException io){
//			Log.error(io.toString());
//		}
	}
	
	
}
