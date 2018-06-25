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
 *         Created May 29, 2018.
 */
public class CheckBoxTest extends BaseTest {
	private final static String DESCRIPTION = "This test case is to verify check box functionality";
	private final static String CLASSNAME=CheckBoxTest.class.getSimpleName();
	 /**
	 * TODO Put here a description of what this constructor does.
	 *
	 */
	public CheckBoxTest() {
		super(DESCRIPTION, CheckBoxTest.class);
	}
	
	@Test(description=DESCRIPTION)
	public static void verifyCheckBoxFun(){
		InputFormPageOperations oper=new InputFormPageOperations(driver);
		boolean sCheck=false;
		boolean mCheck=false;
		try{
			sCheck=oper.verifySingleCheckBox();
			mCheck=oper.verifyMultiCheckBox();
		}catch(Exception e){
			Log.info("FAIL:"+e+" "+true);
		}
		
		if(sCheck==true && mCheck==true){
			m_isTestPassed=true;
			Log.info("PASS:verifyCheckBoxFun test case has passed "+m_isTestPassed);
		}
		else
			Assert.assertTrue(m_isTestPassed);
	}
}
