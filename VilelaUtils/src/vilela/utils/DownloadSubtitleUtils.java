package vilela.utils;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.StringTokenizer;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class DownloadSubtitleUtils {

	private PostUtils postUtils = new PostUtils();
	
	public void login() throws Exception {
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("txtLogin", "raphael.vilela");
		data.put("txtSenha", "lambda21");
		data.put("entrar", "entrar");

		URL url = new URL("http://legendas.tv/login_verificar.php");
		postUtils.post(url, data);
	}

	public boolean downloadSubtitlesAndMatch(String searchParameters, File folder) throws Exception{
		LinkedList<String> links = findSubtitleDownloadLinks(searchParameters);
		if(links.size() > 0){
			for (String link : links) {
				File subtitleFile = new File(folder.getAbsolutePath() + File.separator + searchParameters + ".rar");
				System.out.println("Downloading link: " + link + " to " + subtitleFile.getAbsolutePath());
				subtitleFile.delete();
				postUtils.post(new URL(link), null, true, subtitleFile.getAbsoluteFile(), PostUtils.POST_METHOD);
				
				try{
					System.out.println("Extracting File: " + subtitleFile.getAbsolutePath());
					if (CompressUtils.unrar(subtitleFile,
							CompressUtils.subtitleExtensions, true)) {
						subtitleFile.delete();
					}
				}catch (Exception ex){
					System.out.println("Arquivo." + subtitleFile.getName() + " is corrupted.");
				}
				
				return true;
			}
		}
		return false;
	}
	
	private LinkedList<String> searchSubtitle(String searchParameters) throws Exception {
		System.out.println("Searching subtitles by name: " + searchParameters);
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("txtLegenda", searchParameters);
		data.put("selTipo", "1"); //Release
		data.put("int_idioma", "1"); //Portugues

		URL url = new URL("http://legendas.tv/index.php?opcao=buscarlegenda");
		postUtils.post(url, data);
		return parseHtmlToFindDownloadSubtitlesWindowLinks(postUtils.getHtml());
		
	}
	
	private LinkedList<String> findSubtitleDownloadLinks(String searchParameters) throws Exception {
		LinkedList<String> links =  searchSubtitle(searchParameters.toLowerCase());
		if(links.size() == 0){
			 links =  searchSubtitle(searchParameters.toUpperCase());
		}

		if (links.size() == 0) {
			
			String new_searchparameter = "";
			
			StringTokenizer stringTokenizer = new StringTokenizer(searchParameters, ".");
			if(stringTokenizer.countTokens() > 1){
				new_searchparameter += stringTokenizer.nextToken() + " " + stringTokenizer.nextToken();
			}else{
				stringTokenizer = new StringTokenizer(searchParameters, " ");
				if(stringTokenizer.countTokens() > 1){
					new_searchparameter += stringTokenizer.nextToken() + " " + stringTokenizer.nextToken();
				}
			}
			
			if (searchParameters.indexOf("E0") != -1) {
				new_searchparameter += searchParameters.toLowerCase()
						.substring(0, searchParameters.indexOf("E0") + 3)
						.replace('.', ' ');
			}
				
			if (searchParameters.indexOf("-") != -1) {
				new_searchparameter += " "
						+ searchParameters.substring(searchParameters
								.indexOf("-") + 1, searchParameters.length());
			}
			
			links = searchSubtitle(new_searchparameter);
			if (links.size() == 0) {
				System.out.println("Não foi possível encontrar a legenda.");
			}
		}

		return links;
	}
	
	private LinkedList<String> parseHtmlToFindDownloadSubtitlesWindowLinks(LinkedList<String> html) throws Exception{
		LinkedList<String> codes = new LinkedList<String>();
		LinkedList<String> links = new LinkedList<String>();
		if (html != null){
			for (String downloadCode : html) {
				//Encontrou resultado?
				if (downloadCode.indexOf("javascript:abredown('") != -1){
					//Resultado é busca destaque?
					if(downloadCode.indexOf("buscaDestaque") !=-1 ){
						codes = new LinkedList<String>();
						downloadCode = downloadCode.substring(downloadCode.indexOf("javascript:abredown('"),downloadCode.length());
						downloadCode = downloadCode.substring(downloadCode.indexOf("('") + 2,downloadCode.indexOf("')"));
						codes.add(downloadCode);
						break;
					}else{
						downloadCode = downloadCode.substring(downloadCode.indexOf("javascript:abredown('"),downloadCode.length());
						downloadCode = downloadCode.substring(downloadCode.indexOf("('") + 2,downloadCode.indexOf("')"));
						codes.add(downloadCode);						
					}
				}
			}
		
			for (String code : codes) {
				links.add("http://legendas.tv/info.php?d="+code+"&c=1");
			}
		}
		return links;
	}

	/**
	 * @param args
	 * @throws Exception
	 * @throws MalformedURLException
	 */
	public static void main(String[] args) throws Exception {
		// Authenticator.setDefault(new
		// URLAuthenticator("tr_revilela","1naocopiem1"));
		
		JFileChooser fc = new JFileChooser();
		fc.showOpenDialog(null);
		File video = fc.getSelectedFile();
		video.renameTo(new File(video.getParent() + File.separator + video.getName().toUpperCase()));
		
		DownloadSubtitleUtils finder = new DownloadSubtitleUtils();
		finder.login();
		if(!finder.downloadSubtitlesAndMatch(video.getName().replaceAll(".AVI", ""), video.getParentFile())){
			JOptionPane.showMessageDialog(null, "Legenda não foi encontrada pelo nome do arquivo.");
			String serie = JOptionPane.showInputDialog("Qual é o nome da série?");
			String session = JOptionPane.showInputDialog("Qual é a temporada? (com dois digitos)");
			String episode = JOptionPane.showInputDialog("Qual é o episódio? (com dois digitos)");
			String dist = JOptionPane.showInputDialog("Qual é a distribuição? (FQM, 2HD, SYS, FHW)");
			if (serie.isEmpty() || session.isEmpty() || episode.isEmpty() ){
				JOptionPane.showMessageDialog(null, "Faltando dados para busca. Programa finalizado!!!");
			}else{
				finder.downloadSubtitlesAndMatch( serie + " s" + session + " e" + episode + " " + dist , video.getParentFile());
			}
		}
	}
}
