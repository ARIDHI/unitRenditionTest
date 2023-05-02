/**
 * 
 */
package config;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
/**
 * 
 * @author ARIDHIHichem
 *
 */
public abstract class abstractHandlerDriver extends DriverBase  {
	
    public static Actions builder;
    public static WebDriver driver = getDriver();
    public FluentWait<WebDriver> wait;
    public static String PAGE_LOAD_TIMEOUT ="60" ;

    /**
     * 
     * @param driver
     * @throws Exception 
     */
	    public abstractHandlerDriver(WebDriver driver) throws Exception {
		this.wait = new WebDriverWait(driver, 320).ignoring(NoSuchElementException.class)
				.ignoring(NullPointerException.class).ignoring(InterruptedException.class);
	    builder =new Actions(driver);
	  
	}
	/**
	 * 
	 * @param locator
	 * @return
	 * @throws InterruptedException 
	 */
	 public abstract void waitForPageLoaded() throws InterruptedException;    
     public abstract WebElement _findElement(String identifier) throws InterruptedException;
     public abstract WebElement waitForElementPresent(String identifier );
     public abstract WebElement waitForElementToBeClickable(String identifier );
     public abstract boolean waitForElementinvisible(String identifier );
     public abstract WebElement waitForElementvisible(String identifier );
	 public abstract boolean clickOnElement(String identifier ) throws InterruptedException;    
     public abstract String getNotificationMsg(String identifier ) throws InterruptedException;
     public abstract void sendText(String text,String identifier ) throws InterruptedException;
     public abstract List<WebElement> findElements(String identifier );
     public abstract boolean waitUntilElementNotDisplayed(String identifier );
     public abstract boolean waitNumberOfElementsToBeLessThan(int count,String identifier );
     public abstract boolean waitForAlert();



    /**
     * 
     * @param <TabstractannotationHandlerBase>
     * @param abstractannotationHandlerBaseClass
     * @return
     */
     public <TabstractannotationHandlerBase extends HandlerDriverBase> 
     TabstractannotationHandlerBase getInstance(Class<TabstractannotationHandlerBase> abstractannotationHandlerBaseClass ) {
     	
     	try {
     		return abstractannotationHandlerBaseClass.getDeclaredConstructor(WebDriver.class).newInstance(this.driver);
     	}
     	catch(Exception e) {
     		
     		e.printStackTrace();
     		return null;
     	   }	
     	}

}
