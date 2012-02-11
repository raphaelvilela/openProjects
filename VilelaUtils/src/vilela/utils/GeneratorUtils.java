package vilela.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class GeneratorUtils {

	public static String generateRandomCpf() {
		String cpf = "" + Math.round( Math.pow(10, 9) * Math.random());
		int soma = 0;
		int resultado;
		if (cpf.length() > 9) {
			cpf = cpf.substring(1, 10);
		}
		for (int x = cpf.length(); x < 9; x++) {
			cpf = cpf + Math.round(9 * Math.random());
		}
		for (int i = 10; i > 1; i--) {
			soma += Integer.parseInt(cpf.charAt(10 - i) + "") * i;
		}
		if((soma % 11) < 2){
			resultado = 0;
		} else {
			resultado =  11 - (soma % 11);
		}
		cpf = cpf + resultado;
		soma = 0;
		for (int i = 11; i > 1; i--) {
			soma += Integer.parseInt(cpf.charAt(11 - i) + "") * i;
		}
		resultado = soma % 11 < 2 ? 0 : 11 - soma % 11;
		cpf = cpf + resultado;
		return cpf;
	}
	
	public static Date generateRandomBirthdayDate( int minimumAge, int maximumAge) {
		Calendar birthday = new GregorianCalendar();
		int year = (birthday.get(Calendar.YEAR) - minimumAge) - ((int) (Math.random() * (maximumAge - minimumAge)));
		int month = (int) (Math.random() * 10) + 1;
		int day = (int) (Math.random() * 27) + 1;
		birthday.set(Calendar.YEAR, year);
		birthday.set(Calendar.MONTH, month);
		birthday.set(Calendar.DAY_OF_MONTH, day);
		return birthday.getTime();
	}
	
	public static String generateRandomLogin(String name, String append) {
		System.out.println(name);
		String[] names = name.split(" ");
		String login = names[0];

		if((Math.random()*2) > 1){
			login = names[1];	
		}
		
		if((Math.random()*2) > 1 || names.length <= 2){
			login = login + names[1].charAt(0) + append;
		} else {
			login = login + names[1].charAt(0) + names[2].charAt(0);
		}
		return login.toLowerCase();
	}

}
