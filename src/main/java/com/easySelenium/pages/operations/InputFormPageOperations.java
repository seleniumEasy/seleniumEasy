package com.easySelenium.pages.operations;

import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

import com.easySelenium.common.CommonMessage;
import com.easySelenium.generic.Log;
import com.easySelenium.pages.elements.Elements_Page;

import common.BasePage;

/**
 * TODO Put here a description of what this class does.
 *
 * @author ssamaji.
 *         Created May 26, 2018.
 */
public class InputFormPageOperations extends BasePage{
	
	Elements_Page elements=null;
	/*
	 * Constuctor	
	 */
	public InputFormPageOperations(WebDriver driver) {
		super(driver);
		elements = new Elements_Page(driver);
		PageFactory.initElements(driver,this);
	}
		
//	public static void setXpathOfSelect(int i){
//		selectXpath=String.format("(//div[@class='panel-body']//div[@class='checkbox']//label//input)[%d]",i);
//	}
	public void selectSingleCheckBox(){
		elements.singleCheckBox.click();
	}
	
	public void selectFirstCheckBox(){
		elements.firstCheckBox.click();
	}
	public void selectSecondCheckBox(){
		elements.secondCheckBox.click();
	}
	public void selectThirdCheckBox(){
		elements.thirdCheckBox.click();
	}
	public void selectFourthCheckBox(){
		elements.fourthCheckBox.click();
	}
	public void selectAll(){
		elements.checkAll.click();
	}
	
	public void unSelectAll(){
		elements.uncheckAll.click();
	}
	
	public void enterValueOfA(String a){
		elements.valueA.sendKeys(a);
	}
	
	public void enterValueOfB(String b){
		elements.valueB.sendKeys(b);
	}
	
	public void clickGetTotal(){
		elements.getTotal.click();
	}
	
	public String getActulTotal(){
		return elements.actualTotal.getText();
	}
	
	public void clickSimpleFormDemo(){
		elements.simpleFormDemo.click();
	}
	
	public void clickInputFormDemo(){
		elements.inputForms.click();
	}
	
	public void clickCheckBoxFormDemo(){
		elements.checkBoxDemo.click();
	}
	
	public void clickradioButtonDemo(){
		elements.radioButtonDemo.click();
	}
	
	public void clickselectDropDownDemo(){
		elements.selectDropDownDemo.click();
	}
	
	public void clickajaxFormDemo(){
		elements.ajaxFormDemo.click();
	}
	
	public void clickjquerySelectDemo(){
		elements.jquerySelectDemo.click();
	}
	
	public String getActualMessage(){
		return elements.getActualMessage.getText();
	}
	
	public boolean verifySingleCheckBox(){
		List<Boolean>results= new LinkedList<>();
		boolean ischecked=false,successMessage=false;
		waitTillElementIsVisible(elements.inputForms);
		clickInputFormDemo();
	//	waitTillElementIsVisible(elements.checkBoxDemo);	
		clickCheckBoxFormDemo();
	//	waitTillElementIsVisible(elements.singleCheckBox);
		selectSingleCheckBox();
		results.add(isElementSelected(elements.singleCheckBox));
		String actualMessage=getText(elements.singleCheckBoxMessage);
		if(CommonMessage.SINGLE_CHECKBOX_MESSAGE.equals(actualMessage))
			successMessage=true;
		results.add(successMessage);
		selectSingleCheckBox();
		Log.info("Result of verifySingleCheckBox is "+results);
		return returnResults(results);
	}
	
	public boolean verifyMultiCheckBox(){
		List<Boolean>results= new LinkedList<>();
		selectFirstCheckBox();
		results.add(isElementSelected(elements.firstCheckBox));
		selectSecondCheckBox();
		results.add(isElementSelected(elements.secondCheckBox));
		selectThirdCheckBox();
		results.add(isElementSelected(elements.thirdCheckBox));
		selectFourthCheckBox();
		results.add(isElementSelected(elements.fourthCheckBox));
		unSelectAll();
		results.add(!isElementSelected(elements.firstCheckBox));
		results.add(!isElementSelected(elements.secondCheckBox));
		results.add(!isElementSelected(elements.thirdCheckBox));
		results.add(!isElementSelected(elements.fourthCheckBox));
		selectAll();
		results.add(isElementSelected(elements.firstCheckBox));
		results.add(isElementSelected(elements.secondCheckBox));
		results.add(isElementSelected(elements.thirdCheckBox));
		results.add(isElementSelected(elements.fourthCheckBox));
		Log.info("Result of multi Check is "+results);
		
		return returnResults(results);
	}
	
	
	
	public boolean enterMessageAndVerifyText(String text){
		boolean result=false;
		waitTillElementIsVisible(elements.inputForms);
		clickInputFormDemo();
		waitTillElementIsVisible(elements.simpleFormDemo);
		clickSimpleFormDemo();
		elements.enterMessage.sendKeys(text);
		elements.showMessage.click();
		String actualString=getActualMessage();
		if(text.equals(actualString)){
			Log.info("Expected result matched with actual result: expected :"+text+" actual "+actualString);
			result=true;
		}
		
		return result;
	}
	
	public boolean getTotalValue(String a, String b){
		boolean result=false;
		int va=Integer.valueOf(a);
		int vb=Integer.valueOf(b);
		int total=va+vb;
		String expectedTotal=Integer.toString(total);
		enterValueOfA(a);
		enterValueOfB(b);
		clickGetTotal();
		String actualTotal=getActulTotal();
		if(actualTotal.equals(expectedTotal)){
			Log.info("Expected result matched with actual result: "+"expected: "+expectedTotal+" actual: "+actualTotal);
			result=true;
		}
		return result;
	}
	
	
	
	

}
