/**
 * 
 */
package config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.Platform;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;


/**
 * @author ARIDHIHichem
 *
 */
public class DriverConfig {

	public static Properties prop;
	public static WebDriver driver = null; 
	public static EventFiringWebDriver e_driver;
    private static int PAGE_LOAD_TIMEOUT =60 ;
	protected static int IMPLICIT_WAIT = 2 ;
    
	public DriverConfig(){
		
	    try {
	    	 prop = new Properties();
	    	 File src = new File("./src/config/config.properties");
	    	 FileInputStream ip = new FileInputStream(src);
	    	 prop.load(ip);
	        } catch (FileNotFoundException e) {
	            System.out.println("Properties file not found !");
	    	    }catch (IOException e) {
	    	    	e.printStackTrace();
	    	    }
	    
	 } 
	
		
	 
	public static void remoteDriver() throws MalformedURLException {
		String browserNameServer = System.getProperty("browser");
		String browserMode = System.getProperty("mode");
		  if(browserNameServer.equals("chrome")) {
		    	
			 DesiredCapabilities cap =DesiredCapabilities.chrome();
			 cap.setBrowserName("chrome");
			 cap.setPlatform(Platform.ANY);
			 
			 ChromeOptions options = new ChromeOptions();
			 if(browserMode.equals("headless")) {
			     options.addArguments("--headless", "--window-size=1920,1200");
				 }
		     else if(browserMode.equals("normal"))  {
			   options.addArguments("--window-size=1920,1200");
		     }
			 HashMap<String, Object> chromePref = new HashMap<>();
			 chromePref.put("download.default_directory", System.getProperty("user.dir")+"\\download");
			 options.setExperimentalOption("prefs", chromePref);
			 options.addArguments("--lang=fr-FR");
			 options.addArguments("--incognito");
	         options.addArguments("--disable-extensions");
	         options.addArguments("enable-automation"); 
	   	     options.addArguments("--disable-web-security");
		     options.addArguments("--allow-insecure-localhost");
		     options.addArguments("--ignore-urlfetcher-cert-requests");		 
		     options.setExperimentalOption("excludeSwitches",
	         Arrays.asList("disable-popup-blocking"));
	         options.setExperimentalOption("w3c", true);
			 options.merge(cap);
			 driver = new RemoteWebDriver(new URL("http://ec2-52-213-56-16.eu-west-1.compute.amazonaws.com:4456/wd/hub"), options); 
			 
		  }
	      
			 
			 else if(browserNameServer.equals("Firefox")) {
				 if(browserMode.equals("headless")) {
				    System.out.println("headless mode");
					 }
			     else if(browserMode.equals("normal"))  {
                    System.out.println("normal mode");			     }
				 DesiredCapabilities cap = DesiredCapabilities.firefox();
				 FirefoxOptions options = new FirefoxOptions();
				 
				 cap.setBrowserName("firefox");
				 cap.setPlatform(Platform.ANY);
				 options.merge(cap);
				 options.addArguments("--lang=fr-FR");
				 driver = new RemoteWebDriver(new URL("http://ec2-52-213-56-16.eu-west-1.compute.amazonaws.com:4456/wd/hub"), options); 
			 
			 }	
	}
	
 public static void localDriver() {
	 String browserNameLocal = prop.getProperty("browser");
	 String browserMode = prop.getProperty("mode");
	 if(browserNameLocal.equals("chrome")) {
		 System.setProperty("webdriver.chrome.driver",prop.getProperty("ChromeDriver_win64"));     		 
		 System.setProperty("webdriver.chrome.silentOutput","true");	
		 DesiredCapabilities capabilities = new DesiredCapabilities();
		 capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true); 
		 capabilities.setCapability("resolution", "1920x1080");
		 
		   ChromeOptions options = new ChromeOptions();
		   if(browserMode.equals("headless")) {
			     options.addArguments("--headless", "--window-size=1920,1080");
				 }
		   else {
			   options.addArguments("--window-size=1920,1200");
		   }
		   HashMap<String, Object> chromePref = new HashMap<>();
		   chromePref.put("download.default_directory", System.getProperty("user.dir")+"\\download");
			options.setExperimentalOption("prefs", chromePref);
		    options.addArguments("--incognito");
            options.addArguments("--disable-extensions");
            options.addArguments("enable-automation"); 
            options.addArguments("disable-infobars"); 
            options.addArguments("--no-sandbox"); 
            options.addArguments("--disable-browser-side-navigation"); 
            options.addArguments("--disable-gpu");
            options.addArguments("--dns-prefetch-disable");
		    options.addArguments("test-type");
		    options.addArguments("--enable-popup-blocking");
		    options.addArguments("--disable-web-security");
		    options.addArguments("--allow-insecure-localhost");
		    options.addArguments("--ignore-urlfetcher-cert-requests");
		    options.setExperimentalOption("excludeSwitches",
		    Arrays.asList("disable-popup-blocking"));
            options.setExperimentalOption("w3c", true);
		    
		 options.setPageLoadStrategy(PageLoadStrategy.NONE);
		 capabilities.setCapability(ChromeOptions.CAPABILITY, options); 
		 capabilities.setJavascriptEnabled(true);
          driver = new ChromeDriver(options);	    
	 }
	 
	 else if(browserNameLocal.equals("internetexplorer")) { 			
		 System.setProperty("webdriver.ie.driver",prop.getProperty("IEDriver_win64"));
		 
		 InternetExplorerOptions options = new InternetExplorerOptions();		 
		 options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		 options.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
		 options.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING,true);  
		 options.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);			
		 options.setCapability(InternetExplorerDriver.SILENT, true);
		 options.setCapability("unexpectedAlertBehaviour", "accept");
		 options.setCapability("ignoreProtectedModeSetting", true);
		 options.setCapability("disable-popup-blocking", true);	
		 driver = new InternetExplorerDriver(options);
	 }
	 else if(browserNameLocal.equals("Firefox")) {
		 System.setProperty("webdriver.gecko.driver",prop.getProperty("FirefoxDriver_win64"));	 
	    FirefoxOptions FfOptions = new FirefoxOptions();
	    FfOptions.addArguments("--private");
	    FfOptions.addPreference("javascript.enabled", true);
	    FfOptions.setCapability(FirefoxOptions.FIREFOX_OPTIONS,FfOptions);

        driver = new FirefoxDriver(FfOptions);
	 } 
	 else if(browserNameLocal.equals("edge")) {
		 System.setProperty("webdriver.edge.driver",  prop.getProperty("Edgeweb_win64")); 			 
	      driver = new EdgeDriver();
	 }
	 else {
		 System.err.println("No browser name is defined on prop file please check name");
	 }
     }

 
    public static void setRemoteURL() throws Exception {
    	
    	
    	 e_driver = new EventFiringWebDriver(driver);

		 driver = e_driver;
   	     driver.manage().window().maximize();  
         driver.manage().deleteAllCookies();
	     driver.manage().timeouts().pageLoadTimeout(PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
	     driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT, TimeUnit.SECONDS);
	     driver.get(System.getProperty("URL"));	     
	     
    }   
    
    public static void setLocalURL() throws Exception {
    	 e_driver = new EventFiringWebDriver(driver);

   	     driver.manage().window().maximize();  
         driver.manage().deleteAllCookies();
	     driver.manage().timeouts().pageLoadTimeout(PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
	     driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT, TimeUnit.SECONDS);
	     driver.get(prop.getProperty("URL"));	     
    }


}
