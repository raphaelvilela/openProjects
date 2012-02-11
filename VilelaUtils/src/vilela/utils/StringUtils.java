package vilela.utils;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
	
	public static String getCommaSeparatedText(String[] texts){
		String result = null;
		for(String text : texts){
			if(result == null){
				result = text;
			} else {
				result = result + "," + text;
			}
		}
		System.out.println(result);
		return result;
	}
	
	public static String getValueOfTagById(String parameter, LinkedList<String> htmlSource){
		for(String htmlLine: htmlSource){
			if(htmlLine.indexOf("id=\"" + parameter + "\"") != -1){
				return htmlLine.replaceAll("<.*?>", "");
			}
		}
		return null;
	}
	
	public static String getInnerValueOfTagByParameter(String parameter, String htmlLine){
		Pattern parameterPattern = Pattern.compile(parameter + "=\"(.*?)\"");
		Matcher valueMatcher = parameterPattern.matcher(htmlLine);
		if(valueMatcher.find()){
			return valueMatcher.group().replaceAll(parameter + "=", "").replaceAll("\"", "");
		} else {
			return null;
		}
	}

}
