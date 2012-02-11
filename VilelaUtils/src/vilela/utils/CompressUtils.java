package vilela.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.LinkedList;

import de.innosystec.unrar.Archive;
import de.innosystec.unrar.rarfile.FileHeader;

public class CompressUtils {

	static LinkedList<String> subtitleExtensions = new LinkedList<String>();
	static{
		subtitleExtensions.add(".srt");
		subtitleExtensions.add(".sub");
		subtitleExtensions.add(".SRT");
		subtitleExtensions.add(".SUB");
	}
	
	/**
	 * 
	 * @param rarFile
	 * @param allowedFileExtensions Descompacta apenas arquivos com as extensões permitidas.
	 * @param fileNameUpperCase Salva arquivos sempre com nome em caixa alta.
	 * @return
	 * @throws Exception
	 */
	public static boolean unrar(File rarFile, LinkedList<String> allowedFileExtensions, boolean fileNameUpperCase) throws Exception {
		System.out.println("Unrar file " + rarFile.getAbsolutePath() );
		boolean result = false;
		Archive archive = new Archive(rarFile);
		File folder = new File(rarFile.getParent());

		for (FileHeader fileHeader : archive.getFileHeaders()) {
			if (!fileHeader.isDirectory()) {
				for(String extension: allowedFileExtensions){
					if(fileHeader.getFileNameString().indexOf(extension) != -1){
						File file;
						
						if(fileNameUpperCase){
							file = new File(folder.getPath() + File.separator + fileHeader.getFileNameString().toUpperCase());
						}else{
							file = new File(folder.getPath() + File.separator + fileHeader.getFileNameString());
						}
						
						FileOutputStream fos = new FileOutputStream(file);
						archive.extractFile(fileHeader, fos);
						result = true;
					}
				}
			}
		}
		return result;
	}
}
