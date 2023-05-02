package config;

import java.lang.reflect.Method;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
/**
 * @author ARIDHIHichem
 *
 */
public class DriverBase extends DriverConfig{
    
	public static HandlerDriverBase page = null;
	


	@BeforeSuite 
	  public static void initialization() throws Exception  {
		

	    String serverName = prop.getProperty("server");
	  
	    if(serverName.equals("jenkins")) {
	    	DriverConfig.remoteDriver();
	    	DriverConfig.setRemoteURL();
	    	objectPropreties.initializeObjectProperty();
	    	page = new HandlerDriverBase(driver); 
		}	 
	    
	    else if(serverName.equals("local")) {
	    	DriverConfig.localDriver();
	    	DriverConfig.setLocalURL();
	    	objectPropreties.initializeObjectProperty();
	    	page = new HandlerDriverBase(driver); 
	    }
	    else {
	    	System.err.println("No server is defined on prop file !");
	    }
	}


	@AfterMethod(alwaysRun = true)
	public void beforeMethod(Method test, ITestResult testResult) {

	    //get name of test. if testName annotation is empty get method name.
	    String name = test.getName().toString().replace("_", " ");

	    String description = test.getDeclaredAnnotation(org.testng.annotations.Test.class).description();

	    //get test result
	    String result = "Uknowen";
	  
	    switch (testResult.getStatus()) {
	        case 1:
	            result = "\u001B[32mSUCCESS";
	            break;
	        case 2:
	            result = "\u001B[31mFAILURE";
	            break;
	        case 3:
	            result = "\u001B[33mSKIP";
	            break;
	        case 4:
	            result = "SUCCESS_PERCENTAGE_FAILURE";
	            break;

	    }

	    System.out.println(String.format("%s|%s|%s", "\u001B[37m"+name, "\u001B[37m"+description, result+"\u001B[0m\n                             *** \n                             ***"));
	}

	public static WebDriver getDriver() {
		return driver ;
	}



	    @AfterSuite(alwaysRun = true)
	    public static void closeDriverObjects() {
	    	 driver.close();
			 driver.quit();
	    }
	}		
