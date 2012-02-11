package vilela.utils.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import javax.swing.JFileChooser;

public class FileUtils {
	
	public static void copyfile(File source, File target) {
		try {
			InputStream in = new FileInputStream(source);
			OutputStream out = new FileOutputStream(target);

			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			in.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static boolean renamefile(File source, String newFileName) {
		try {
			File f2 = new File(source.getParent() + File.separator + newFileName);
			return source.renameTo(f2);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
	
	public static void changeExtension(File basePath, String extension) {
		File afile[] = basePath.listFiles();
		for (int i = 0; i < afile.length; i++) {
			File file = afile[i];
			if (file.isDirectory()){
				changeExtension(file, extension);
			} else {
				renamefile(file, file.getName() + "." + extension);
			}
		}
	}
	
	
	public static void removeSpaceInFileNames(File basePath) {
		File afile[] = basePath.listFiles();
		for (int i = 0; i < afile.length; i++) {
			File file = afile[i];
			if (file.isDirectory()){
				removeSpaceInFileNames(file);
			} else {
				renamefile(file, file.getName().replaceAll(" ",""));
			}
		}
	}

	public static void main(String args[]) throws Exception {
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		fc.showOpenDialog(null);
		removeSpaceInFileNames(fc.getSelectedFile());
		changeExtension(fc.getSelectedFile(), "smc");
	}

}
