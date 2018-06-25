package com.easySelenium.pages;
import org.openqa.selenium.WebDriver;

import com.easySelenium.pages.operations.InputFormPageOperations;

public class InputFormPage {
	/**
	 * Holds ProjectsPageOperations instance
	 */
	public static InputFormPageOperations operations = null;

	/**
	 * Constructor - creates instance for ProjectsPageOperations.
	 */
	public InputFormPage(WebDriver driver) {
		operations = new InputFormPageOperations(driver);
	}
}
