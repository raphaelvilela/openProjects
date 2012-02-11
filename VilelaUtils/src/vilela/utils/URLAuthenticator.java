package vilela.utils;

import java.net.Authenticator;
import java.net.InetAddress;
import java.net.PasswordAuthentication;

public class URLAuthenticator extends Authenticator {
	
	private String user;
	private String password;
	
	public URLAuthenticator(String user, String password){
		super();
		this.user = user;
		this.password = password;
	}
	
	// This method is called when a password-protected URL is accessed
	protected PasswordAuthentication getPasswordAuthentication() {
		
		// Get information about the request
		String promptString = getRequestingPrompt();
		String hostname = getRequestingHost();
		InetAddress ipaddr = getRequestingSite();
		int port = getRequestingPort();
		
		// Get the username from the user...
		String username = this.user;
		
		// Get the password from the user...
		String password = this.password;
		
		// Return the information
		return new PasswordAuthentication(username, password.toCharArray());
	}
}
