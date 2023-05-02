package renditionTest;

import org.testng.annotations.Test;

import config.DriverBase;

public class StartRendition extends DriverBase {
	
	@Test
	public void renditionRunner() {
		try {  
			String[] command = {"cmd.exe", "/C", "Start",System.getProperty("user.dir")+"\\rendition.bat" };
		        Runtime r = Runtime.getRuntime();
			    Process p = r.exec(command);
			                p.waitFor();
			                Thread.sleep(6000);
			                r.exec("taskkill /f /im cmd.exe") ;
			                Thread.sleep(6000);
			                } 
			                catch (Exception e) {
			                    System.out.println("erreur d'execution");	
			                }
	 }

}
