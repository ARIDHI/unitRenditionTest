/**
 * 
 */
package config;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;


/**
 * @author ARIDHIHichem
 *
 */
public class HandlerDriverBase  extends  abstractHandlerDriver  {
  
	public HandlerDriverBase(WebDriver driver) throws Exception  {
  	super(driver);
	}
  	public static By locator ;

	/**
	 * @param identifier The value of the "identifier" attribute to search for.
	 * @return A By which locates elements by the value of the "identifier" attribute.
	 * identifier is saved on yml file \ObjectRepository\Objects.yml
	 */
	public static By Element(String identifier) {
		try {
	  	locator = By.id(objectPropreties.getElementProperty(identifier));
		}
	    catch (NoSuchElementException | NullPointerException | IndexOutOfBoundsException | TimeoutException f){
	      try {
	       locator  = By.xpath(objectPropreties.getElementProperty(identifier));	
	       }
	      catch (NoSuchElementException | NullPointerException | IndexOutOfBoundsException | TimeoutException j){
	    	  try {
	             locator = By.cssSelector(objectPropreties.getElementProperty(identifier));
	              }
	    	  catch (Exception o){
	    		  System.err.println("Element not found on doom");
	    		  Assert.fail("\nNo such element founded on doom ! \n check if element is present and try again");
	    	  }
	        }
	    }
		return locator;
	    }

@Override
public void waitForPageLoaded() throws InterruptedException {
	ExpectedCondition<Boolean> expectation = new
            ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver driver) {
                    return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
                }
            };
    try {
        Thread.sleep(1000);
        WebDriverWait wait = new WebDriverWait(driver, 120);
        wait.until(expectation);
    } catch (Throwable error) {
        Assert.fail("Timeout waiting for Page Load Request to complete.");
    }
    finally {
    	Thread.sleep(5000);
    }
}
    

@Override
public boolean waitUntilElementNotDisplayed( final String identifier ) {
		
		ExpectedCondition<Boolean> elementIsDisplayed = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
			  try {
			     try {
					_findElement( identifier ).isDisplayed();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			     return false;
			  }
			  catch (NoSuchElementException e ) {
			    return true;
			  }
			  catch (StaleElementReferenceException f) {
			    return true;
			  }
			}
			};
			wait.until(elementIsDisplayed);
		return true;
	}

@Override
public WebElement _findElement(String identifier) throws InterruptedException {
 
  
  WebElement ele = null ;	
  try {
	  ele = driver.findElement(Element(identifier));
      return ele;
  }catch(NoSuchElementException | NullPointerException | TimeoutException e) {
  try {
	  ele = waitForElementPresent(identifier);
  return ele;
  } catch(NoSuchElementException | NullPointerException | TimeoutException  i) {
  try {
  List<WebElement> allElement = findElements(identifier);
  ele = allElement.get(0);
  return ele; 
  }catch (NoSuchElementException | NullPointerException | IndexOutOfBoundsException | TimeoutException f) {   
  System.out.println("Element not found"); 
  }
  }
  }
  return ele;
  }


@Override
public boolean clickOnElement(String identifier ) throws InterruptedException {	

	try { 
	waitForElementToBeClickable(identifier);
	builder.moveToElement(_findElement( identifier )).build().perform();  
	_findElement( identifier ).click();
	 
	} 
    catch(NoSuchElementException | NullPointerException | ElementNotInteractableException e ){
	/**
	 * if exception present try to click with js method       
	 */
    try {
    driver.findElement(Element( identifier )).click();
    
    } 
     /**
      * if exception present while try to wait element ToBeClickable 
      */
	catch(ElementNotInteractableException | NoSuchElementException | NullPointerException | TimeoutException f) {
	JavascriptExecutor js = (JavascriptExecutor) driver;
	js.executeScript("arguments[0].click();",_findElement(identifier ));
    }        
    } 
    Thread.sleep(1000);
    return true ;
    }	   


@Override
public WebElement waitForElementvisible(String identifier ) {	
	    WebDriverWait wait = new WebDriverWait(driver, 15);
	    WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(Element( identifier )));
	    if(element != null) {
	    return element;
	    }
	    else {
	    	System.out.println(element +"not visible");	
	    }
		return element;
	    
}

@Override
public WebElement waitForElementPresent(String identifier ) {
	    FluentWait<WebDriver> wait = new WebDriverWait(driver, 15).ignoring(NoSuchElementException.class);
	    WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(Element( identifier )));
//	    System.out.println("waiting for .." + element);
	    return element;
}

@Override
public  WebElement waitForElementToBeClickable(String identifier ) {
	    WebDriverWait wait = new WebDriverWait(driver, 15);
	    WebElement element = wait.until(ExpectedConditions.elementToBeClickable(Element(identifier )));
	    return element;
}
 

@Override
public boolean waitNumberOfElementsToBeLessThan( int count ,String identifier ){
    try {
        WebDriverWait waiter = new WebDriverWait(driver, 20);
        waiter.until(ExpectedConditions.numberOfElementsToBeLessThan(Element( identifier ) , count));
        return true;
    }
    catch (Exception e){
        return false;
    }
}
   
@Override
public List<WebElement> findElements (String identifier ) {
   		List<WebElement> ele = null ;
   		try {
   			ele = driver.findElements(Element( identifier ));
   			return ele;
   		}
   		catch (NoSuchElementException i) {
   		}
   		catch(Exception e) {
   		}
   		return ele ;
   	}

@Override
	
public String getNotificationMsg(String identifier ) throws InterruptedException {		
		return  _findElement( identifier ).getText();
	}
   	
@Override 
public void sendText(String identifier ,String text) throws InterruptedException {
		
		if(_findElement(identifier ) != null) {
			_findElement(identifier ).sendKeys(text);
		}
		else {
			System.out.println(locator +"[error] : Element not Clickable" );
		}
		  return ;
	}
		
@Override
public boolean waitForElementinvisible(String identifier ) { 
	
   try {
	   WebDriverWait waiter = new WebDriverWait(driver, 15);
	   waiter.until(ExpectedConditions.invisibilityOfAllElements(findElements(identifier )));		
	}
	catch(Exception timeOutException) {
			return false;
		}
		return true;
    }


@BeforeMethod
public void beforeEachTest() {
    wait = new WebDriverWait(driver, 2);
}

@Override
public boolean waitForAlert() {
	try {
		WebDriverWait waiter = new WebDriverWait(driver, 1);
		if(waiter.until(ExpectedConditions.alertIsPresent())!=null)
			driver.switchTo().alert().accept();
		}
	     catch (Exception e) {
	    	 System.out.println("No Alert");		     
	    	 }	return true;
}



}
