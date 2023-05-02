package rendition;

import java.io.FileReader;
import java.io.Reader;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import org.yaml.snakeyaml.Yaml;

import config.DriverBase;


public class YmlTester extends DriverBase   {
	
	/**
	 * Variable de Mapping 
	 * yaml file Path
	 */
	static Map<?, ?> Property ;
	static String objectfilepath = "./src/ObjectRepository/Objects.yml";
	static WebDriver driver ;
	
	 public static void initializeObjectProperty()
	 {
	  try {
	   Reader rd = new FileReader(objectfilepath);
	   Yaml yaml = new Yaml();
	   Property = (Map<?, ?>) yaml.load(rd);
	   rd.close();
	  } catch (Exception e) {
	   e.printStackTrace();
	  }
	 }
	 
	 public static String getLocator(String objectstring)
	 {  
	  Map <?,?> map = (Map <?, ?>) Property.get(objectstring.split("\\.")[0]); 
	  return map.get(objectstring.split("\\.")[1]).toString();
	 }
	
   
    public static void Click(String iden) throws InterruptedException {
    char op = '"' ;	
    System.out.println(YmlTester.getLocator(iden));
      WebElement webElement = driver.findElement(By.cssSelector(YmlTester.getLocator(iden)));
      webElement.click();
   }

@Test
public void test() throws Exception {
	 Thread.sleep(10000);
     Click("Home_page.Import_Menu");
     Thread.sleep(10000);
}
	
//	public static void main(String[] args) throws FileNotFoundException, InterruptedException {
//	     YmlTester.initializeObjectProperty();
//	     ClicksOn("Home_page.Import_Menu");
//	     Thread.sleep(10000);
//		
//	}

	}

