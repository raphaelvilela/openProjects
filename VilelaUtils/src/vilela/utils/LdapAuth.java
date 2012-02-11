package vilela.utils;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;

public class LdapAuth {

	//Configuração de LDAP e-mail iG
	private static final String MAIL_LDAP_USER = "Admin";
	private static final String MAIL_LDAP_PASSWORD = "Br7M@!LpAs$";
	private static final String MAIL_LDAP_O = "brtmail";
	private static final String MAIL_LDAP_OU = "ig.com.br";
	private static final String MAIL_LDAP_HOST = "ldap://ldap-50.ig.com.br";
	private static final String MAIL_LDAP_SECURITY_PRINCIPAL = "cn=" + MAIL_LDAP_USER + ",o=" + MAIL_LDAP_O ;
	private static final String MAIL_LDAP_TEST_LOGIN = "cn=" + "mmzumba" + ",ou=" + MAIL_LDAP_OU + ",o=" + MAIL_LDAP_O;

	//Configuração de LDAP e-mail SuperiG
	private static final String SMAIL_LDAP_USER = "Admin";
	private static final String SMAIL_LDAP_PASSWORD = "Br7M@!LpAs$";
	private static final String SMAIL_LDAP_O = "brtmail";
	private static final String SMAIL_LDAP_OU = "superig.com.br";
	private static final String SMAIL_LDAP_HOST = "ldap://ldap-50.ig.com.br";
	private static final String SMAIL_LDAP_SECURITY_PRINCIPAL = "cn=" + SMAIL_LDAP_USER + ",o=" + SMAIL_LDAP_O ;
	private static final String SMAIL_LDAP_TEST_LOGIN = "cn=" + "simonefeu" + ",ou=" + SMAIL_LDAP_OU + ",o=" + SMAIL_LDAP_O;
	
	//Configuração de LDAP acelerador iG
	private static final String ACC_LDAP_USER = "Admin";
	private static final String ACC_LDAP_PASSWORD = "ldapigpass";
	private static final String ACC_LDAP_O = "ig";
	private static final String ACC_LDAP_OU = "ig";
	private static final String ACC_LDAP_HOST = "ldap://ldap-1.ig.com.br";
	private static final String ACC_LDAP_SECURITY_PRINCIPAL = "cn=" + ACC_LDAP_USER + ",o=" + ACC_LDAP_O;
	private static final String ACC_LDAP_TEST_LOGIN = "cn=" + "testeproduto3.6_portal" + ",ou=" + ACC_LDAP_OU + ",o=" + ACC_LDAP_O;
	
	//Configuração de LDAP banda larga iG
	private static final String BL_LDAP_USER = "Manager";
	private static final String BL_LDAP_PASSWORD = "secret";
	private static final String BL_LDAP_O = "iG";
	private static final String BL_LDAP_OU = "adsl-telefonica";
	private static final String BL_LDAP_HOST = "ldap://ldapqmail.ig.com.br";
	private static final String BL_LDAP_SECURITY_PRINCIPAL = "cn=" + BL_LDAP_USER + ",o=" + BL_LDAP_O;
	private static final String BL_LDAP_LOGIN = "cn=" + "testeproduto_852@adsl-telefonica" + ",ou=" + BL_LDAP_OU + ",o=" + BL_LDAP_O;
	
	public static boolean activateEmail(String securityPrincipal, String password, String login, String host) {
		DirContext ctx = null;
		try {
			Hashtable<String, String> env = new Hashtable<String, String>();
			env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
			env.put(Context.PROVIDER_URL, host);
			env.put(Context.SECURITY_AUTHENTICATION, "simple");
			env.put("com.sun.jndi.ldap.connect.pool", "true");
			env.put(Context.SECURITY_PRINCIPAL, securityPrincipal);
			env.put(Context.SECURITY_CREDENTIALS, password);
			ctx = new InitialDirContext(env);
			ctx.modifyAttributes(login,new ModificationItem[]{new ModificationItem(ctx.REPLACE_ATTRIBUTE, new BasicAttribute("accountStatus", "A"))});
		}
		catch (NamingException e) {
			e.printStackTrace();
		} finally{
			try {
				ctx.close();
			} catch (NamingException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public static boolean getData(String securityPrincipal, String password, String login, String host) {
		DirContext ctx = null;
		try {
			Hashtable<String, String> env = new Hashtable<String, String>();
			env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
			env.put(Context.PROVIDER_URL, host);
			env.put(Context.SECURITY_AUTHENTICATION, "simple");
			env.put("com.sun.jndi.ldap.connect.pool", "true");
			env.put(Context.SECURITY_PRINCIPAL, securityPrincipal);
			env.put(Context.SECURITY_CREDENTIALS, password);
			
			ctx = new InitialDirContext(env);
			StringBuffer sBufferNameDn = new StringBuffer();
			
			sBufferNameDn.append(login);
			NamingEnumeration<String> it = ctx.getAttributes(sBufferNameDn.toString()).getIDs();
			while( it.hasMore() ) {
				String id = it.next();
				System.out.println(ctx.getAttributes(sBufferNameDn.toString()).get(id));
			}
			
			if (ctx.getAttributes(sBufferNameDn.toString()).size() > 0){
				return true;
			} else {
				return false;
			}
			
		} catch (NamingException e) {
			e.printStackTrace();
		} finally{
			try {
				ctx.close();
			} catch (NamingException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//Testando BL
		//System.out.println("Teste Banda Larga: " + getData(BL_LDAP_SECURITY_PRINCIPAL, BL_LDAP_PASSWORD, BL_LDAP_LOGIN, BL_LDAP_HOST));

		//Testando E-mail Superig
		
		activateEmail(SMAIL_LDAP_SECURITY_PRINCIPAL, SMAIL_LDAP_PASSWORD, SMAIL_LDAP_TEST_LOGIN, SMAIL_LDAP_HOST);
		System.out.println("Teste E-mail: " + getData(SMAIL_LDAP_SECURITY_PRINCIPAL, SMAIL_LDAP_PASSWORD, SMAIL_LDAP_TEST_LOGIN, SMAIL_LDAP_HOST));
		
		//Testando E-mail
		//System.out.println("Teste E-mail: " + getData(MAIL_LDAP_SECURITY_PRINCIPAL, MAIL_LDAP_PASSWORD, MAIL_LDAP_TEST_LOGIN, MAIL_LDAP_HOST));
		
		//Testando Acelerador
		//System.out.println("Teste acelerador: " + getData(ACC_LDAP_SECURITY_PRINCIPAL, ACC_LDAP_PASSWORD, ACC_LDAP_TEST_LOGIN, ACC_LDAP_HOST));

	}

}
