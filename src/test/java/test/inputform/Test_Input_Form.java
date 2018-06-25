package test.inputform;

import org.junit.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.easySelenium.common.BaseTest;
import com.easySelenium.generic.Log;
import com.easySelenium.pages.operations.InputFormPageOperations;

/**
 * TODO Put here a description of what this class does.
 *
 * @author ssamaji.
 *         Created May 28, 2018.
 */
public class Test_Input_Form extends BaseTest{

	/**
	 * TODO Put here a description of what this constructor does.
	 *
	 */
	public Test_Input_Form() {
		super(DESCRIPTION,Test_Input_Form.class);
		// TODO Auto-generated constructor stub.
	}
	
	private final static String DESCRIPTION = "This test case is to verify input text box";
	private final static String CLASSNAME=Test_Input_Form.class.getSimpleName();
	
	@Test(description=DESCRIPTION)
	public static void test_SimpleForm(){
		boolean verifyMessage=false, verifyTotal=false;
		InputFormPageOperations operations=new InputFormPageOperations(driver);
		try{
			verifyMessage=operations.enterMessageAndVerifyText("Hello");
			verifyTotal=operations.getTotalValue("15","5");
		} catch(Exception e){
			Log.info("FAIL:"+e+" "+true);
		}
		if(verifyMessage==true && verifyTotal==true){
			m_isTestPassed=true;
		}
		else
			Assert.assertTrue(m_isTestPassed);
		Log.info("Passed: test_SimpleForm \n"+DESCRIPTION);
	}
		
}
