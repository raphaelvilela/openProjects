package vilela.utils;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;

public class SimplePopClient {
	private String host;
	private String username;
	private String password;
	private Properties props = new Properties();
	private String provider = "pop3";
	private boolean disableTop = false;
	private Store store;
	private Folder inbox;

	public static void main(String[] args) throws IOException, MessagingException {
		SimplePopClient client = new SimplePopClient("mail.superhumail.com", "email@superhumail.com", "1qaz2wsx", true);
		client.connect();
		Message[] messages = client.getMessages();
		for(Message message: messages){
			System.out.println(message.getContent());
		}
		client.disconnect();
	}

	public void connect() {
		try {
			if (disableTop) {
				props.put("mail.pop3.disabletop", true);
			}
			// Connect to the POP3 server
			Session session = Session.getDefaultInstance(props, null);
			store = session.getStore(provider);
			store.connect(host, username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void disconnect() {
		try {
			inbox.close(true);
			store.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SimplePopClient(String host, String username, String password,
			boolean disableTop) {
		this.host = host;
		this.username = username;
		this.password = password;
		this.disableTop = disableTop;
	}

	public Message[] getMessages() {
		Message[] messages = null;
		try {
			// Open the folder
			inbox = store.getFolder("INBOX");
			if (inbox == null) {
				System.out.println("No DefaulFolder");
				System.exit(1);
			}

			inbox.open(Folder.READ_WRITE);	
			
			// Get the messages from the server
			messages = inbox.getMessages();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return messages;
	}

}