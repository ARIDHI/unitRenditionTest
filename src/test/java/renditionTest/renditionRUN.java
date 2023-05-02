/**
 * 
 */
package renditionTest;

import java.util.Scanner;

import org.junit.Assert;
import org.testng.annotations.Test;

import rendition.RunRealLoads;

/**
 * @author ARIDHIHichem
 *
 */
public class renditionRUN extends RunRealLoads {
    
	public void initialization() {
		delete_File_ends_with_suffix(System.getProperty("user.dir"), "rendition.bat");
		delete_File_ends_with_suffix(System.getProperty("user.dir"), ".txt");
		delete_File_ends_with_suffix(System.getProperty("user.dir"), ".log");
		delete_File_ends_with_suffix(System.getProperty("user.dir"), ".csv");
	}
	
	
	@Test(priority =1)
	public void Start_Up_Rendition(String version) throws InterruptedException {
		initialization();
		StartRendition("java -jar C:\\Users\\arondor-arender-rendition-tester-"+version+".jar");
		Assert.assertTrue("\n****ERROR*****\nFile ends with suffix (.log) does not exist on repositorie\n**********************", RunRealLoads.fileExiste(System.getProperty("user.dir"), ".log"));
		Assert.assertTrue("\n****ERROR*****\nFile ends with suffix (.txt) does not exist on repositorie\n**********************", RunRealLoads.fileExiste(System.getProperty("user.dir"), ".txt"));
	}
	
	
	@Test(priority = 2 , enabled = true )
	public void Rendition_port_config(String version) throws InterruptedException {	
		initialization();
		StartRendition("java -jar C:\\Users\\arondor-arender-rendition-tester-"+version+".jar "+"-d "+ server +" -w " +port+" -t 4 -v report_ARender.csv");				
		Assert.assertTrue("\n****ERROR*****\nFile ends with suffix (.csv) does not exist on repositorie\n**********************", RunRealLoads.fileExiste(System.getProperty("user.dir"), ".csv"));
	}
	
	
	@Test(priority = 3 , enabled = true )
	public void Create_txt_file() throws InterruptedException {		
		initialization();
		file_Creator(System.getProperty("user.dir")+"\\qaTest.txt");
		file_Writer(System.getProperty("user.dir")+"\\qaTest.txt", "ARender.pdf \nARenderTest.eml");
		Assert.assertTrue("\n****ERROR*****\nFile ends with suffix (.txt) does not exist on repositorie\n**********************", RunRealLoads.fileExiste(System.getProperty("user.dir"), ".txt"));;
	}
	
	@Test(priority = 4 , enabled = true )
	public void Start_up_rendition_from_txt_file(String version) throws InterruptedException {		
		StartRendition("java -jar C:\\Users\\arondor-arender-rendition-tester-"+version+".jar "+"-d "+localserver+" -w "+port+" -t 4 -l -f qaTest.txt -v rapport_ARender.csv");
	    Thread.sleep(8000);
	}
	
	@Test(priority = 5 , enabled = true )
	public void Start_up_rendition_as_iteration(String version) throws InterruptedException {	
		try {
		StartRendition("java -jar C:\\Users\\arondor-arender-rendition-tester-"+version+".jar "+"-d "+localserver+" -w "+port+" -t 4 -l -f qaTest.txt -v rapport_ARender.csv -x 10"	);
		}
		finally{
		Thread.sleep(40000);
		delete_File_ends_with_suffix(System.getProperty("user.dir"), ".txt");
		delete_File_ends_with_suffix(System.getProperty("user.dir"), ".csv");
		}
	}
	public static void verionInput() {
		System.out.println("\nPlease download the rendition under your machine directory C:\\Users\\ \n*********  \n*********");
		System.out.println("Wait until the .jar to completly downloaded on your machine Than enter the rendition version \n*********  \n*********");		              
	}
	
	public static void main(String[] args) throws InterruptedException {
		
		verionInput();
		Scanner sc= new Scanner(System.in); 
		System.out.print("Please enter rendition version here :    ");  
		String version= sc.nextLine();  
		renditionRUN runner = new renditionRUN();
		runner.Start_Up_Rendition(version);
		runner.Rendition_port_config(version);
		runner.Create_txt_file();
		runner.Start_up_rendition_from_txt_file(version);
		runner.Start_up_rendition_as_iteration(version);
	}

String server = apo+"http://serveur-de-rendition:8761/"+apo ;
String localserver = apo+"http://localhost:8761/"+apo;
String port = apo+"(100,1100,100)"+apo;
public static char apo ='"'  ;
}