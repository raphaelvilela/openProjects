package vilela.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.StringTokenizer;

import javax.net.ssl.HttpsURLConnection;

public class PostUtils {
	
	public static String POST_METHOD = "POST";
	public static String GET_METHOD = "GET";

	private LinkedList<String> html;
	private Map<String, String> cookieMap = new HashMap<String, String>();
	private String cookies;

	public LinkedList<String> getHtml() {
		return html;
	}

	public String getCompleteHtml() {
		String result = "";
		if (html != null)
			for (String line : html) {
				result += line;
			}
		return result;
	}
	
	public void printLastHtml() {
		if (html != null)
			for (String line : html) {
				System.out.println(line);
			}
	}

	public void post(URL url, HashMap<String, String> parameters)
			throws Exception {
		post(url, parameters, false, null, PostUtils.POST_METHOD);
	}
	
	public void get(URL url, HashMap<String, String> parameters)
			throws Exception {
		post(url, parameters, false, null, PostUtils.GET_METHOD);
	}

	/**
	 * Realiza o download de um arquivo binário.
	 * @param url
	 * @param downloadFile
	 */
	public void downloadFile(URL url, File downloadFile){
        try{  
            
            OutputStream out = new FileOutputStream(downloadFile, false);  
            URLConnection conn = url.openConnection();  
            InputStream in = conn.getInputStream();  
              
            int i=0;  
            while ((i = in.read()) != -1){  
                out.write(i);  
            }  
            in.close();  
            out.close();  
        }  
          
        catch (FileNotFoundException e){  
            System.out.println("Arquivo não encontrado. Causa: " + e.getMessage());  
        }  
        catch (MalformedURLException e){  
            System.out.println("Erro na formação da URL. Causa: " + e.getMessage());   
        }  
        catch (IOException e){  
            System.out.println("Erro de entrada/saida de dados. Causa: " + e.getMessage());  
        }  
	}
	
	public void post(URL url, HashMap<String, String> parameters, boolean saveReturn, File file, String method) throws Exception {

		String data = "";
		if (parameters != null) {
			for (String key : parameters.keySet()) {
				//System.out.println(key + " : " + parameters.get(key));
				//data += URLEncoder.encode(key, "UTF-8") + "=" + URLEncoder.encode(parameters.get(key), "UTF-8") + "&";
				data += URLEncoder.encode(key, "ISO-8859-1") + "=" + URLEncoder.encode(parameters.get(key), "ISO-8859-1") + "&";
			}
		}
		
		
		
		URLConnection conn = url.openConnection();
		if(cookies != null){

			/*Set<String> set = cookieMap.keySet();
			for (String key :set){
				cookies += key + "=" + cookieMap.get(key) + "&";
			}*/
			
			//cookies = "OlhClickC=Parceiro:=UaiClick; __utma=195055400.170423499.1273287475.1273455645.1273539072.13; __utmz=195055400.1273287475.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); ASP.NET_SessionId=wzsfw155cxtpruzdmn4ftg55; __utmb=195055400.8.10.1273539072; __utmc=195055400";
			
			conn.setRequestProperty("Cookie", cookies);
			//conn.setRequestProperty("Cookie", updatedCookie);
			
			//System.out.println("POST para "+ url.toString() + " com params " + data + " e cookies " + cookies);
		}
		
		conn.setRequestProperty("Host", url.getHost());
		conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.6; en-US; rv:1.9.2) Gecko/20100115 Firefox/3.6");
		conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		//conn.setRequestProperty("Accept-Language", "en-us,en;q=0.5");
		//conn.setRequestProperty("Accept-Encoding", "gzip,deflate");
		conn.setRequestProperty("Accept-Charset", "ISO-8859-1,utf-8;q=0.7,*;q=0.7");
		conn.setRequestProperty("Keep-Alive", "115");
		conn.setRequestProperty("Connection", "keep-alive");
		//conn.setRequestProperty("Connection", "http://www.uaiclick.com.br/produto_3047_home-theater-com-dvd-player-usb--5.1-canais-360w-rms-conex%C3%A3o-hdmi");
		
		if (conn instanceof HttpsURLConnection)
			throw new Exception("Sistema nao funciona com paginas HTTPs");
		else
			postHttp((HttpURLConnection) conn, data, saveReturn, file, method);
	}

	private void updateCookies(HttpURLConnection conn) {

		if (conn.getHeaderField("Set-Cookie") != null){
			this.cookies = conn.getHeaderField("Set-Cookie");
		}
		
		if (cookies != null) {
			StringTokenizer st = new StringTokenizer(this.cookies, ";");
			Map<String, String> cookie = new HashMap<String, String>();
			String name = "";
			String value = "";
			
			while (st.hasMoreElements()) {
				String token = st.nextToken();
				
				if( token.indexOf("=") != -1 ){
					name = token.substring(0, token.indexOf("=")).trim();
					value = token.substring(token.indexOf("=") + 1, token.length()).trim();
				}else{
					name = token;
				}
				cookie.put(name, value);
				cookieMap.put(name, value);
			}
		}
	}

	private void postHttp(HttpURLConnection conn, String data,
			boolean saveReturn, File file, String method) throws Exception {
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setUseCaches(false);
		conn.setFollowRedirects(true);
		
		conn.setRequestMethod(method);
		
		OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		wr.write(data);
		wr.flush();

		this.updateCookies(conn);

		// Get the response
		BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

		this.html = new LinkedList<String>();
		String line;
		while ((line = rd.readLine()) != null) {
			this.html.add(line);
		}
		
		if (saveReturn) {
			FileWriter fileWriter = new FileWriter(file);
			file.createNewFile();

			BufferedWriter out = new BufferedWriter(fileWriter);
			
			for (String htmlLine : html) {
				out.write(htmlLine);
			}
			
			out.close();
			fileWriter.close();
		}
		wr.close();
		rd.close();

	}


}
