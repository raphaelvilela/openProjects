package vilela.utils;

import java.io.File;


public class RemoveAllFiles {

	public static void main (String args[]){
		File folder = new File(".");
		System.out.println(folder.getAbsolutePath());
		
		File[] files = folder.listFiles();
		
		for (File file:files){
			System.out.println("Apagando arquivo" + file.getName());
			//file.delete();
		}
		
	}
	
}
