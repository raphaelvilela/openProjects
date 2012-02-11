package vilela.utils;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Set;

public class UaiClickUtils {
	
	private String slvCode = null;
	private String slv_field = "slh";
	private String cl = null;
	private String cle = null;

	
	public PostUtils postUtils = new PostUtils();
	
	public void registerProduct(URL url, String cl, String cle) throws Exception{
		this.cl = cl;
		this.cle = cle;
		postUtils.post(url, new LinkedHashMap<String, String>(), true, new File("/Users/raphaelvilela/Desktop/result1.html"),PostUtils.POST_METHOD);
		
	}

	private String getHiddenValue(String parameter, LinkedList<String> html) {
		String beginHiddenTag = "name=\"" + parameter+ "\"";
		String begin = "value=\"";
		String end = "\"";
		
		if (html != null) {
			for (String line : html) {
				if (line.indexOf(beginHiddenTag) != -1 ) {
					
					if (line.indexOf(begin) != -1) {

						line = line.substring(line.indexOf(begin), line.length() - 1);

						line = line.replaceAll(begin, "");

						String tag = line.substring(0, line.indexOf(end));

						return tag.replaceAll(end, "");
					} else {
						return "";
					}
					
				}
			}
		}
		return null;
	}
	
	public void updateSLVCode(LinkedList<String> html) {
		String begin = slv_field + "=";
		String end = "\"";

		if (html != null) {
			for (String line : html) {
				if (line.indexOf(begin) != -1) {
					line = line.substring(line.indexOf(begin),line.length() - 1);
					line = line.replaceAll(begin, "");
					line = line.replaceAll(end, "");
					this.slvCode = line;
				}
			}
		}
	}
	
	public void updateInfo() throws MalformedURLException, Exception{
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("cl", cl);
		//http://www.uaiclick.com.br//RefreshDet.aspx?cl=3047&cle=2571556&rdm=0.2662076948394714&slv=1164c2c7-44e9-4166-bc35-9af4fcbefbbf
		data.put("cle", cle);
		data.put("rdm", Math.random() + "");
		data.put(slv_field, this.slvCode);
		
		String url = "http://www.uaiclick.com.br//RefreshDet.aspx";
		postUtils.get(new URL(url), data);
		postUtils.printLastHtml();
	}
	
	public void updateLeilaoInfo() throws MalformedURLException, Exception{
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("cdl", "3042,3047,3049,3050");
		data.put("rdm", Math.random() + "");
		data.put(slv_field, this.slvCode);
		
		String url = "http://www.uaiclick.com.br//Refresh_LeiloesObj.aspx";
		postUtils.get(new URL(url), data);
		postUtils.printLastHtml();
	}
	
	private LinkedList<String> getHiddenTags(LinkedList<String> html) {
		LinkedList<String> tags = new LinkedList<String>();
		String hiddenTag = "type=\"hidden\"";
		String begin = "name=\"";
		String end = "\"";
		
		if (html != null) {
			for (String line : html) {

				if (line.indexOf(hiddenTag) != -1) {
					
					line = line.substring(line.indexOf(begin), line.length() - 1);
					
					line = line.replaceAll(begin, "");
					
					String tag = line.substring(0, line.indexOf(end));
					
					tags.add(tag.replaceAll(end, ""));
				}
			}
		}
		return tags;
	}
	
	public void login() throws Exception {

		HashMap<String, String> data = new HashMap<String, String>();
		
		URL url = new URL("http://www.uaiclick.com.br/login.aspx");
		postUtils.post(url, data, true, new File("/Users/raphaelvilela/Desktop/validate.html"),PostUtils.POST_METHOD);
		
		/*
		LinkedList<String> tags  = getHiddenTags(postUtils.getHtml());
		for (String tag : tags) {
			//data.put(tag, getHiddenValue(tag, postUtils.getHtml()));
		}
		
		
		String values = "__LASTFOCUS=&__EVENTTARGET=&__EVENTARGUMENT=&__VIEWSTATE=WhXql%2FTpW%2FJmmLD7pTANYEbfR1jNuUG44Zhxk6RrYvoErEv%2FQ5XsvRErwTLyghLhwx6p%2BXwCC1eK8IA0nyVFg8ktdo%2FmkJV1dQwrVnZ5Im91oEPID4imCnPDuX%2FlMWiqBq89WbbqV7Ud3MNHOGuw76UYwvaSM1%2F9Vn0K5cJ5Vahbyubxje7Ss3LOJueO9MmLQlvEFogjKVMTNEir5AZy7FTpoq5LX%2FGpZrRnsKoOBNuFsWxj%2FVveBBrY%2FgxgEta44K54q2%2FxEVo63oGj8WQOTZANJuo8VXTNYW4uo4a%2FqJgqC%2FVmFd7d0VX%2F08JYvlqdxDxzNAe8AN6wwSzsef8dX%2Fg%2B22xvsXlR5xDPnlUeDC8S0CpAkSmzeqLvl94qKJBPZSpca44sJCi8D52O2acdqsY6%2BMryv6jPnB2o0FGUTNF9TuJQVkVrNUQLeiOi7NGnmplDVqDvtkmVLhXq48ssmT4HYSuYVO4GrE0DQjccICNSN6MMBbwMqaZfzHs0Z%2FigeTMMqxupCG54rBY%2BBrv7Oy1NiaIAJHFbut22%2FmhhNQ03jNmMUPyX71xX268Yv52FdpPOl7LMkvMKLzQgldxg41UdKhKFX3gW5s7%2BL4Pq0EoCiIGQuiHy7lfHiG82QkGC58osdT8f8ho7kKKlxqTpNsNeuTz4TlIo5ToVziqudLroklJMPUeqPMmtseCow97jO2w0eSAJuVSOYU5oztXY6u%2BUn89Q%2BJrpd7Y1D7TfdbqSAFedyjqTZ35R8tYWqCOehj7TbEvgD1f1BN7Q4PlSreCKYTpjnA36l9o73eOGlN7H%2FaIcGTrFC6mAw9CR3VUFblqdT%2BScMIDHl67DWjdjVdy58tvXj3Um734kPK74vmecEW%2FDTXSJ4KIEnkBl8Swz2scH6SEXnOA1Y4p8lGmswfmpM2sOTP%2F8D%2BgucURtPwXyWkr%2F1h0tKxUAG8zJB6NY95kdGyojTdFIsbr%2B2zO2NGZqHOGk97JMiDefhvhm5quPvkcwB3L%2F%2FlsNvH7M54rj3ab2aFKQZEpLqPAWlBBRjamcRp62aStv80w2kaOEaC4aKNdmj%2FIiWMwg%2Fd6w3RIerrYlSfUrkLm3FI7pNxE424IUz2sQkevWpp27m7GJBx6%2BGZwX%2FXBmXJzXazaw08oEf1E1gvmjf44bJIEMihRKxAkiJGPP5QeBIR%2BU4Znw7bIRrXOKwoU8iGKDMbz9dBnE0WKyAz7Ml77ghWvoykt%2BFHSwwrTpOGTzTL5pv6jwKXkT7TIcBpiccruFdaPdP7lglnZlgdkv7ZGzl9fTNZm2vujf8c9q8qChoH8eJfs74KCYzzs826VW4SiZ2k%2Fh1iiQIYEQ6T9Kzd31T0DFOOZCrayfELorSJWGEhhyZHknrkCZCFq7uIPJCN%2FeZq%2BRECWyHKgQf1THIkEz56yUm4UibAu4cON6M7YKx6pZWBjcmeBQZNQVqaB6fLNudkB9ciOL%2FrBrX%2BDA7zk73OY7Na5mv5oREPoIn91PqtCK%2F9EqcUTIJz0vB0z2d4SPNXK%2BXfiEFRUcLA4NPmw4XpwgwZCG0EMksyIJBGl5naN%2FJEpnPPu3nWG%2FfJwosdlyyfZLUiuFfl5ncufAaDNjnCg%2FHgg5vuAHC2qmArc5mpbiNY%2FzP41Klil7bMli78O13nLFcmNSHLMwVxAQ6Dgc%2BGEgByeGVSuiRvqZV9cgQ%2FO7e23abnG46uSem2nS2eaCzQwimN%2FfGxw6cei8MGAMzf7BlGtmOKJ4BOFGsLn5VcU8T6gTvmYvqdTDj5wWe70LdVwR%2BYWVff1Ogj5hIG1dEQ1yL7yPOPyMGyEbRjmW33M7QGwdrYaVIwcd06hT7DAceaspNs%2B9L2SoFbvRAArawFx8tF3lrK5jgw10c0ICXENmkAytg3HZO5qNRrQ%2FvPHkTuUxOXhPx%2BX099HpX4grKXEdlNbCpPgYQws3LzE9r%2BoIf0lF%2F5IOoI1H4wMhBDIdHpNyoXv%2BWnlUvcXpOhFQ%2BQ2UcoQNLpw5iqT61Itw8f8uugCaK4olWVlAe7BVX3Vb%2Bv76XnhS4GgNK5NU0zc%2FMjZPec4YgR5PiB%2FxpLm5HkoBdt2qBkb65RqARGS2S53e4%2BgY5B316Sv9Y5Y0hQxBIqbbhPVUVB0rKU8MCxKocQoYzGQG3yeY1aNPKoibbkGbUJ3Khsb5DB1XzitJlaussGYLosAB48MJgMb9BptGmCe6THqML6jBJGv2KU8ja1kRdYuVIjHBRAUIN5K%2FAB7whju9WxAY%2FVQOuyFE83v2%2Blm1l7mnfQd0IrfIN7JYKhaDA4Flem4%2F9dMPNPnlPGojaOFq%2FirPZJ4J%2BEnCZAJssOEWsNMh2LEyKEvGkhvKc5mZ9wf6Jo5x0HGzUWBAgAs2CiFjCh3mIvG%2BnSZZGA%2FvxgbLnmvL%2BtwktuSyTbZrgyRF%2FXCSbV5DUKmyD9HZYJ%2F6p9xygnYUzRJiKYuQEPiW8zKaeU%2FcQGm3w7Sz4RASB6GB6NYu2QQJGpey1kaRYW5xvOx4gZw5RyQeqEfwdk%2BcFDUUZasWIOelY0lRxiM%2B8lYFyn8BFadXoyteZt4jE%2FbLKxCBsYtt11faRNJE91CvQq8YQpuZWyxhalL6efxADPhL5En1eeVsfGmH5e4v8hP3pzmd1du%2BdoBTdCO2Pmu%2FcjIrC0UqewDXG8fFrCajiEVfKpFWHiGARMhOiCZP7wJ%2BFVhnVWjXyS2oKLFM2GtTBoptmDAkNSLn7%2FXPmmIx%2Fj5iNViT5DiBXUWrxZDpJsY7Cbn4r1kquDz1toXuwAgcu9CSqIISV%2F1AS5mzTymACsXzvWuWIw2SDxg0mZo%2BF%2FfoMZ7pIkmCkhQPzGSu%2FOANPPE5mAx4UsfFf8Gzuy3eP%2FQqmBW6NnJutr%2B2Ymq7aMOPyac%2B81LrbmwRHHbGpzqmFmUAc3LEOBfNjvcWDWxEyngPzxtMXGPJ9w%2B53WTnVv3AxttJ%2B%2BA5Nts3DezhucvQmuajmANCA97R%2BMJ94%2Fr2H%2BKQF22x3Kz%2F%2BCv1cYtVEgy17DjOt%2B5Y0sirgL%2BjGZ5u5pKU%2Fce9Py21r1J9TsPPX2qiWvurj0Xuo4uvpY9KwHaVaYWkFoZ6YoKgRAwt%2BkvQmxVHQkYqT%2FmZLAKdZElMuX%2FmwJKLcAOVyt3wNnKkK0tP8qCszSGjnNgFsh247fDz39Z3X%2Fm1VKP7%2BQxPAhDLEFZA0fwJSavBcgu%2FBaU7CFudFd%2BFbVZUUwer28IE9G%2FiKbHUIfuorlC1%2FCZ2uf0VD0U6ynrILIC6Bh5SKdgRbaHCR%2FtOmewGpu4HwGRde3nYsuD9fXBT8bU8UJc4%2Bd4fhkqJXgXsYbAyASWPXwNP07NSnASgUcif%2Bhz5JWnGJhW7sKU2JfAkqm3KDhMGKgx%2FDlYFkJyH7FpNc1AALRKS4d9lL8tAoxSYuABnpFBoP4M6tQx6dvHh%2Fs3jeUGlL0DBgze13Q4Xepi%2F5%2FO3qCqD%2BqEJkjI%2Ft79WSgbo7Y5CsUtfunY%2FykSf9lT835W0h9fy8FW%2F%2B58ZFTptqJBHylKf3wZsD0b0mJtUUJ1ducxVpneqaDLPKxQ6qzSI7WSyNfByZzHeqw9NSlgojR8yw4gMoQKSnZIyX4%2B67LGT%2BSnxALRhMMeWvbUlUwGB%2BSyYv%2F2u2J2FygXJpciW9746tyGQ7h9aWFShJ5EPyICQJuIGBSb0cClzehSudh6BogZ%2B0ytAtLggySk1Icg%2Bic%2Bp1mOiK9DRACMnQcV0eMLsK7Gv8XQP0ialVJ15Yeuxpa7k6LJodoG5%2FF4XnRm8cmRzVLGctOGTDtgIenmOglnBjYGOYVHgTf7KdaCdwZTGsZa2%2B6jq095SAlhXPwRY4XtTlWntJCsS5sfwlk3aE4UbyWK6ub0p8aQSyVURNbT8oLoZF%2Brn33dgQwTFzHOCv4QKfgcQZ48vkwaVT%2BwjIEDewtoeQEcFlaaWs2lHf8VvjUy8mJFsmP9dUvB7VXxHf0IXwqg1OFmzYvgf%2BgUxC00KeC52sKRMi1BsRWoGDFMEXOK9fomjBhKooCatHzK2cwgGH%2BkfMqVU1Go%2B9jUvtwTm6kvnpMSTXyL3oNg8dXDRiBcy4INBVu%2BJHCAEoNJzPkrlrVF9QHmqXbpUdT9Mn1glHrrl%2BOget9gvaMBjbCqenf7CuCnoUZ00DXPeR%2BrR0eBC%2FAfrZkZHvhQ8dhDMjIV2ZAQhEDJ3NuJ40GIWy2VJ2E7LLbisEZab3m1xxxmxGP8TjtAcArkYxcw5YymTmbBOfz3ydd5d1ILp%2BktHt5k1KdyVw%2Bv7%2FJMsuG1EpcrzSAXEOkGOf47UkSLmX0Qonr8zIdG7fFmB5HectzeNFcLIdDpE9HaR0JhvfgEqYcOCObroOKgOnfadINGzXfsWJO3rzMTl%2FywytZse47tqRi6V06ki3FxHZzyIqua7Z1zlsfM%2F1UcOzjy7pw2xZRx%2BDIOSDy6XX0j6IHCy%2BsJkS3Jf%2FzltD6huqTb5evTKG%2FfvbhPRk8sXK4L3tZflOpgcUcxjQrrt%2FgYf3pgWTGFL3JMfe%2BrWRT7NgfZ3W01NUGs2%2F%2F6IdoPO6GGbEnSourAAzEcYpQ3rkw%2BQRM6JX0uAQeGU3FWNXpUh95QVLYR0qEZgbZxQvVWOwCSDfpSVNjiQY3Ru2qHW3ZO%2BruzSdJqS1coIjNE%2FzI7Hf8YjqQESZLV%2BHkPZWM3SFBktgm4BHtUXiZfvUrayGWqw%2B3XWk3eY06ckSDtP37b50z17xTzKREkhoUGwbm112NpdwWiXzVF%2BfTROuDaMgYOIKY9ATDd%2Fr7ZYtASfuie37pPcxwoiDhLBIQVcJl8fSPeIKwDqase2aXvYVYKxXNm4xTRq6N0ZB4fgO0QxPbLj%2BbIW%2BEyNLddGFj56YrjzjoPSaT2fMz84CoYAmU2G5IXTyUXjWzjZDn6hBGf0LYuc%2Bdz%2B%2B3VaB7QLN21wGi4T7kg%2FYefIa7b6%2FrBgN%2Fyz39FuU4JuURN5jWhJ9Y3moMhGieukL4jJW727%2BHb1hlO0fircaQWFNIS%2BfuR9mK5OfN9nUbMHc3KyDOkKf6QVzgrJE%2BIY5Dw6DBUSLcJ6TZ5XDFdxRFNXXzCeqCySseVwj8vRsJDCFWSqGaHLGkN4f3IAOBswWuB4sokrTbp1ryEE2IjhkQvqPWtQFksOFihuTl%2F%2FUc%2FXHUlXxXk8xytG878%2Fwi2%2FN5sW8NvDqqjhM%2FPBjdWxP7Y0oEulJ94CW1UFG75vcGriFVDCI1VBSNlDsNLGsIDXDebq%2B0RB3dZKqKGvK%2BewjIUrNhyuH718ZRw3s3wu2AOAj8WiQ3jHyJW2emWKZDVrZ2dzwWLkEL4xI6ZmP5F8QR5OcvOhZ5rcUlmyPhm0M%2FlI0S4ruZj7m3ALG7UvG0HYwyC8MSIjc%2B83qW8krYo2Fba%2B2pO07q%2F8rRrWs7faEqRnG%2BQMDmgQTXFBxH2MAG0XYPwJEjhf46lr1owsPUkNTVvSmnmvHnYcoOP4k7hYqzsHQTOFNl%2FmQ51Lt8SaAF3oeYiCF%2FIagTYLUGzOx5h2O5JMLEAPDCknvmNi7DHzHuCRmu8of9HvAh4R0ySc3P2asl09hemAkgG%2FJeZ49KbGicgPv%2F5T%2BzQ4Xvn1arejfkb4e6Y3SbbUuuwRFFq3zEotJ1sd2heWzVcRm3sDgw1n4%2FQbJwMX5FRxM9vwtP6WFefoSoNqpkLUUlcb6K6rCFdllGDBs9mjQe93mxfTuUJzRKZKAfCFLMA%2F1yTh5L4eZGtWHN%2FT17Dhq%2BvsCv1it8vuhAnMMawXPdExhxCHiMdAoWqMvm89gPg5HJZZlogzuKCjXm8xE0zFSDoB6xmKzVQRkk%2FvG6g1WU2C9kq1yZ2C6ZLDsil5xVZjpQyaxwjD56AL0RrpW4RWUAEOA4YZKZcP5ki0cnHf8rGtTs2El1v%2BrDxb1XSGsBEq46UyDYq3HkFMZc%2BZNq2ntN5wmdWnEBqk6CNDTuMydwqxUL7BvYJZiojXvZh%2BX85JxEc269ghZSRti6zgGvTAZVWf%2FGKOSgbAwe9SqWtIsbmEmO2qwzXHGWc46UlhC6BuzBe32TjQZLAmuqp4SyeSFPSpod5T%2BIzQrRtaFvH%2F3t%2BSQjToLW5c6nRnvFWFDGiBo83292g3mmL6%2BasNHIOcLJN5l7TSFG7q1lt8m7JRiV6oBn1Cq7rzns06Ka33PvRmKGKUX7ZmGqG%2F7aACkrHYfKZPfhSpiZ1cYzQNjuMERh5bSiKcurr7CHM9E47STs75PmhJOjrza3HotdU%2FQlyu3bZhFvfLcUYpZh74yn9NO2pJxEPgHTknT0lJOwiVcFWN5ogRP72RNX%2Fml2qvSWKKh4C7tvOD51%2FYCV2ZVbxJj1c3c7yOa9MNztqj1gM1%2FCvV%2BlwDfhDxV8KuM9JT4uDigt2XBRoRKqeR9bGH7JywaUkLvadJs5fE%2BOWLLkRL5hBqZjJLGDgQ3SqoOGFw2TVKg6%2Fd3A0In%2FB33MQ7hbj51hkKPfDMWtQs5rgjlfpgOxvu1gmYBHk8%2Be%2B7sBhDCCgcEHfmWCwvC%2Fhi2q1AtskaXAUj3Q0jhBDniHPQUjWHnF2Qd%2Bn%2FVAYE3j4blGK%2BMxDSM%2BS%2FXiLnXZhhcsiag9tf1N9cAn63wabw6A96uVJVxqKXGr%2B%2BoiVaJ%2FhbyMG%2FKdjVFokbBevsnYY6pqJ2jfUVCIIW%2BHlFIv4DaACFhGdnyitlGh9DhXBvmNWzmmiG2%2F9E7I5FJzBLFhPQL1QyUhrS4M9gLxpjsjKPxCmtmXwp0ZU2YZuACkEk54Lx2kR%2FJht0x7b4NTRHZ0zXQwyEqPoRgMeU%2Fv9E3zC8iiFWNf8BEJuRhj8zbdXuu%2F6EwYwhR6e9udZuEhhMduR8Bkf5k%2F9%2Bma1geeZNpCW2rTHPkVor4EkwIPyJ97BJgK%2BaREx1IxmGNurcglDYSX6naTL2UWMzsF0MkTrOlrtCXxjS6Te79OlqDhKaDVMxXp3vml%2BxE1WXk1sdtRS82TlxwY%2FXhCV3nhx5jw6a6X5vstwU%2FlvWNSP6qb%2BKV1zfCGAWrAyS7GLfCAVbXZkZQTusyCXhZGsK2v3c4vPAuJ2%2FIPSRpra1ZWWLX9w4laJWvMDxKBitRHSm%2FKxzl7SEKUfe1IIs7zN9t81WgbpsTkzlilBHkIn8tgRVyT4MYOhb4WaGPGIkmmKYeJkEvbUla7JSDo9Q%2BQtpL9iB%2FlN9f68CmdDtg48TR3lqGtBJaULcuMXbnl67ckrxM3Rh50i9hM2nChm2rQigElYrndHcjV8Wudcmnupxlk9AsxVw5Su8YVyRBvYY%2Bmz2ivY%2BSTXnxw3INDMS4FwP%2BSS1Ro%2BfXn6eaVjZg3l9Len02p4RSgqquxjKi2IzomrpCCwt2m%2B4PEX2FlPpu1RuDzpVSpOwWO4JnVmV5TBXr8GnGfcyoMmL88aSQkwNc1bjt1dsGCjB30GZRji%2BagyXc1q%2BIxS2zKkr8Muz36Dnx2GW7wvvckDTaHqzMZ0AuJ2AkWwFO3mfVM1G%2Bv9V%2FHTtaUwycgLivF9O9U83nmoEyvAZObWOaDBjU7mSu8mmAU9CvsvrhWjDM9SyO7eMKtwz9%2FLTkNnEY9rIfbe%2FkBSc6w%3D%3D&ctl00%24hCaminhoSite=http%3A%2F%2Fwww.uaiclick.com.br%2F%2F&ctl00%24hTemaParceiro=UaiClick&ctl00%24txtLogin=&ctl00%24txtSenha=&ctl00%24ContentPlaceHolder1%24txtLogin=raphaelvilela&ctl00%24ContentPlaceHolder1%24txtSenha=lambda21&ctl00%24ContentPlaceHolder1%24btnLogar.x=18&ctl00%24ContentPlaceHolder1%24btnLogar.y=10&ctl00%24Control_RightTopo1%24txtProdutoDesejado=&__SCROLLPOSITIONX=0&__SCROLLPOSITIONY=401&__VIEWSTATEENCRYPTED=&__PREVIOUSPAGE=0DozkHXrAY3ehHl_ckDz7Q2&__EVENTVALIDATION=T3rgqb3EzNDhLk0kz4YSeMNye2Da%2BNqIXLLtmeKtUtLCfxDaAInsDIGE6ls0Vg8VO5Kg9kCREOo8lPQKwyKxZalSp2s0%2BaQvVsdZqlhq60hqhKkhtxXsLWJEecPWCJ4E%2BFPuN0KGobYv06yQCTtIqFcKb1wohl5e8Kjy%2Bd0Ve2LsnGdfdbhSugfREjQekT91eQ4%2BjRRDZU0BAVBeJp7DDQ5bevqfQrrADNhv7smpvz7nKh%2FCVBV%2F%2BMJcK2PY0VgX";
		StringTokenizer sep_values = new StringTokenizer(values, "&");
		while (sep_values.hasMoreElements()){
			StringTokenizer stringTokenizer = new StringTokenizer((String)sep_values.nextElement(), "=");
			String keyParam = (String)stringTokenizer.nextElement();
			String keyValue = (stringTokenizer.hasMoreElements())?(String)stringTokenizer.nextElement():"";
			data.put(keyParam, keyValue);
		}*/
		
		data.put("__LASTFOCUS", "");
		data.put("__EVENTTARGET", "");
		data.put("__EVENTARGUMENT", "");
		data.put("__SCROLLPOSITIONY", "0");
		data.put("__SCROLLPOSITIONX", "0");
		data.put("__VIEWSTATEENCRYPTED", "");
		data.put("__VIEWSTATE", "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");
		data.put("ctl00$hCaminhoSite", "http://www.uaiclick.com.br//");
		data.put("ctl00$hTemaParceiro","UaiClick");
		data.put("ctl00$ContentPlaceHolder1$txtLogin", "raphaelvilela");
		data.put("ctl00$ContentPlaceHolder1$txtSenha", "lambda21");
		data.put("ctl00$ContentPlaceHolder1$btnLogar.x","18");
		data.put("ctl00$ContentPlaceHolder1$btnLogar.y","12");
		data.put("__PREVIOUSPAGE","ebfzvcgmZ3T_4-AW7x2Z7A2");
		data.put("__EVENTVALIDATION","eGJSKMignFdUobvhpJS2n8vCz4RO7Lpc3un3WEkcGFJqG4HvR7S4/X3kj05wKxod6gbji/fSMnROxrPZ3R8x+C+MXYsDH+zOkolY9J6amhSIoV/+AuNYFd0Jk4S3uVdas0cfAlpbPWEMgygn2XOUUs9eMI++1t0m8yI5N/yH4dWSDEq956iNZlcIae4fHZLccOJImSFrKvfbWRe7H1vGLicjmRNmIny/8/EUbuY7iqBa+IGsDKFVDSVFqNlRbk9G");
		
		url = new URL("http://www.uaiclick.com.br/login.aspx");
		
		postUtils.post(url, data, true, new File("/Users/raphaelvilela/Desktop/result1.html"),PostUtils.POST_METHOD);
		
		updateSLVCode(postUtils.getHtml());
		
	}
}
