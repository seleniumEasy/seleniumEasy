package com.easySelenium.pages.elements;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * TODO Put here a description of what this class does.
 *
 * @author ssamaji.
 *         Created Jun 7, 2018.
 */
public class Elements_Page {
	
	WebDriver driver=null;
	
	public Elements_Page(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver,this);
	}
	
	@FindBy(xpath="//li[@class='tree-branch']//a[text()='Input Forms']")
	public WebElement inputForms;
	
	@FindBy(xpath="//ul//li[@class='tree-branch']//a[text()='Simple Form Demo']")
	public WebElement simpleFormDemo;
		
	@FindBy(xpath="//ul//li[@class='tree-branch']//a[text()='Select Dropdown List']")
	public WebElement selectDropDownButton;
	
	@FindBy(xpath="//ul//li[@class='tree-branch']//a[text()='Input Form Submit']")
	public WebElement inputFormSubmit;
	
	@FindBy(xpath="//ul//li[@class='tree-branch']//a[text()='Ajax Form Submit']")
	public WebElement ajaxFormSubmit;
	
	@FindBy(xpath="//ul//li[@class='tree-branch']//a[text()='JQuery Select dropdown']")
	public WebElement jquerySelectDropDown;
	
	@FindBy(xpath="(//ul//li//a[text()='Checkbox Demo'])[2]")
	public WebElement checkBoxDemo;

	@FindBy(xpath="//ul//li//a[text()='Radio Buttons Demo']")
	public WebElement radioButtonDemo;
	
	@FindBy(xpath="//ul//li//a[text()='Select Dropdown List']")
	public WebElement selectDropDownDemo;
	
	@FindBy(xpath="//ul//li//a[text()='Input Form Submit']")
	public WebElement inputFormDemo;
	
	@FindBy(xpath="//ul//li//a[text()='Ajax Form Submit']")
	public WebElement ajaxFormDemo;

	@FindBy(xpath="//ul//li//a[text()='JQuery Select dropdown']")
	public WebElement jquerySelectDemo;
	
	@FindBy(css=".form-group>input#user-message")
	public WebElement enterMessage;
	
	@FindBy(xpath="//button[@class='btn btn-default' and text()='Show Message']")
	public WebElement showMessage;
	
	@FindBy(xpath="//button[@class='btn btn-default' and text()='Get Total']")
	public WebElement getTotal;
	
	@FindBy(css="#user-message>span")
	public WebElement getActualMessage;
	
	@FindBy(css="#sum1")
	public WebElement valueA;
	
	@FindBy(css="#sum2")
	public WebElement valueB;
	
	@FindBy(css="#displayvalue")
	public WebElement actualTotal;
	
	@FindBy(css="#txtAge")
	public WebElement singleCheckBoxMessage;
	
	@FindBy(xpath="//div[@class='panel-body']//div[@class='checkbox']//label//input")
	public WebElement countCheckbox;
	
	@FindBy(css="#isAgeSelected")
	public  WebElement singleCheckBox;
	
//	@FindBy(xpath=selectXpath)
//	public WebElement selectCheckBox;
	
	@FindBy(xpath="(//div[@class='panel-body']//div[@class='checkbox']//label//input)[2]")
	public  WebElement firstCheckBox;

	@FindBy(xpath="(//div[@class='panel-body']//div[@class='checkbox']//label//input)[3]")
	public  WebElement secondCheckBox;

	@FindBy(xpath="(//div[@class='panel-body']//div[@class='checkbox']//label//input)[4]")
	public WebElement thirdCheckBox;

	@FindBy(xpath="(//div[@class='panel-body']//div[@class='checkbox']//label//input)[5]")
	public WebElement fourthCheckBox;
	
	@FindBy(css="#check1[value='Check All']")
	public WebElement checkAll;

	@FindBy(css="#check1[value='Uncheck All']")
	public WebElement uncheckAll;
	

}
