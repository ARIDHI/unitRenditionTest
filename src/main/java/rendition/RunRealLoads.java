/**
 * 
 */
package rendition;

import java.io.File;
import java.nio.file.*;



/**
 * @author ARIDHIHICHEM
 *
 */
public class RunRealLoads  {
	/**
	 * create a .bat file at rendition folder 
	 * @param path
	 */
	public static void file_Creator(String path) {	
		
	   try {
	     File f = new File(path);
	      if (f.createNewFile())
	      System.out.println("File created");
	      else
	      System.out.println("File already exists");
	      }
	    catch (Exception e) {
	      System.err.println(e);
	     }
	    }
	/**
	 * Write commande on created file at rendition folder
	 * @param pathi
	 * @param Rendition
	 */
	public static void file_Writer(String pathi , String Rendition) {
		Path path = Paths.get(pathi);
		try {
			  String str = Rendition;
			  byte[] bs = str.getBytes();
			  Path writtenFilePath = Files.write(path, bs);
			  System.out.println("Written content in file:\n"+ new String(Files.readAllBytes(writtenFilePath)));
			} catch (Exception e) {
					e.printStackTrace();
		     }
	  }
    /**
     * start cmd.exe and run file .bat 
     * kill cmd.exe
     * @param Rendition
     */	
	public static void StartRendition(String Rendition) {

		file_Creator(System.getProperty("user.dir")+"\\rendition.bat");
		file_Writer(System.getProperty("user.dir")+"\\rendition.bat", Rendition);		
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
	 /**
	  * delete file with specific suffix 
	  * @param path
	  * @param suffix
	  * @return
	  */
	 public static boolean delete_File_ends_with_suffix(String path , String suffix) {
		 File dir = new File(path);
		 File[] listFiles = dir.listFiles();
		 for(File file : listFiles){
	     if(file.getName().endsWith(suffix)){  
	        file.delete();
	        System.out.println(file.getName() + "deleted");   
	      }  
		  }
		 return true;
	 }
	 /**
	  *check if file with specific suffix exist on repo 
	  * @param Path
	  * @param suffix
	  * @return
	  */

	 public static boolean fileExiste(String Path,String suffix) {
		 boolean result = false;
		 File dir = new File(Path);
		 File[] listFiles = dir.listFiles();		 
		     for(File file : listFiles){
			 String name = file.getName();	
			 if(name.endsWith(suffix)) {
				 result = true ;
				 break;
			 }
		     }

		 return result;	
	 }

}