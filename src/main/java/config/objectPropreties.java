/**
 * 
 */
package config;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

/**
 * @author PC
 *
 */
public class objectPropreties {
	
	
	static Map<?, ?> Property ;
	static String objectfilepath = "./src/ObjectRepository/Objects.yml";
	
	 public static void initializeObjectProperty()
	 {
	  try {
	   Reader rd = new FileReader(objectfilepath);
	   Yaml yaml = new Yaml();
	   Property = (Map<?, ?>) yaml.load(rd);
	   rd.close();
	  } catch (FileNotFoundException e) {
	   e.printStackTrace();
	  } catch (IOException e) {
	   e.printStackTrace();
	  }
	 }
	 
	 public static String getElementProperty(String objectstring)
	 {  
	  Map <?,?> map = (Map <?, ?>) Property.get(objectstring.split("\\.")[0]); 
	  return map.get(objectstring.split("\\.")[1]).toString();

	 }
}
