package common;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.tools.ant.taskdefs.Mkdir;
import org.apache.velocity.texen.util.FileUtil;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import com.easySelenium.common.CommonConstant;
import com.easySelenium.generic.FileOperations;
import com.easySelenium.generic.Property;
import com.easySelenium.generic.*;

public class BasePage {
  public static WebDriver driver;
  public String configFile;
  public long timeout;
  
  public BasePage(WebDriver edriver){
	  driver=edriver;
	  configFile=CommonConstant.CONFIG_PATH+CommonConstant.CONFIG_FILE;
	  timeout=Long.parseLong(Property.getPropertyValue(configFile, "EXPLICIT"));
  }
  
  public static boolean returnResults(List<Boolean> results) {
	  	return results.contains(false) ? false : true;
  }
  
  public void waitTillElementIsVisible(WebElement element){
	  new WebDriverWait(driver,timeout)
	  .until(ExpectedConditions.visibilityOf(element));
	  
  }
  
  public void verifyURLis(String expectedUrl){
	  new WebDriverWait(driver,timeout).until(ExpectedConditions.urlToBe(expectedUrl));
  }
  
  public void verifyURLhas(String expectedUrl){
	  new WebDriverWait(driver,timeout).until(ExpectedConditions.urlContains(expectedUrl));
  }
  
  public boolean isElementSelected(WebElement element){
		return element.isSelected();
  }
  
  public String getText(WebElement element) {
	  return element.getText();
  }
  
  public void moveToElement(WebElement element) {
	  Actions actions=new Actions(driver);
	  actions.moveToElement(element).perform();
  }
  
  public static File getScreenshot(String imageName) {
	  try {
		  File screenshot= ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		  File images = FileOperations.getScreenshotFolder();
		  
		  images.mkdirs();
		  if(!images.exists()) {
			  Reporter.log("Unable to use screenshot folder ");
			  Log.info("Unable to use screenshot folder ");
		  }
		  int count = images.listFiles().length + 1;
		 // String imageName = String.format("Image_%d.png", count);
		  imageName=imageName+".png";
		  
		  File imageFile = new File (images , imageName);
		  FileUtils.copyFile(screenshot,imageFile);
		  return imageFile;		  
	  } catch (IOException ioe) {
		  Reporter.log(" Error in file I/O while capturing screenshot ");
		  Log.info(" Error in file I/O while capturing screenshot ");
	  }
	  return new File("");
  }
}
