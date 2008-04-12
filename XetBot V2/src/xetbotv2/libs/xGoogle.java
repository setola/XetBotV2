package xetbotv2.libs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jibble.pircbot.Colors;

public class xGoogle {
	
	private String baseURL;
	private static final Pattern _pattern = Pattern.compile("^.*?<a href=[^>]*(http\\:\\/\\/[^>]+)\" class=l>(.*?)<\\/a>.*$");
	private static final Pattern _pattern_calc = Pattern.compile("^.*?\\Q<img src=/images/calc_img.gif>\\E.*?\\Qsize=+1><b>\\E([^<]*?)\\Q</b></td>\\E.*$");
    
	
	private String resultPage;
	private String[] result;
	private URL url;
	private String search;
	private String encodedSearch;
	private URLConnection temp;
	private HttpURLConnection conn;
	private int maxResults = 10;
	
	//http://www.google.it/search?hl=it&q=plugin+google+irc&btnG=Cerca+con+Google&meta=
	
	public xGoogle(String search){
		this.search = search;
		baseURL = "http://www.google.it/search?q=";
		result = new String[maxResults];
		init();
	}
	
	private void init(){
		try { 
			encodedSearch = URLEncoder.encode(search, "UTF-8"); 
			//baseURL = URLEncoder.encode("http://www.google.it/search?q=", "UTF-8");
		}
		catch (UnsupportedEncodingException e) { System.out.println(e.toString()); }
		
		try{
			url = new URL(baseURL + encodedSearch);
			temp = url.openConnection();
			conn = (HttpURLConnection)temp;
			
			conn.setDoOutput(false);        
			conn.setRequestMethod("GET");
			conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; PircBot; JVM)");                        
			conn.setRequestProperty("Pragma", "no-cache");
			
			conn.connect();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			int i = 0;
			while((line = reader.readLine()) != null){
				resultPage += line;
				
				Matcher matcher = _pattern.matcher(line);
				if (matcher.matches() && i < maxResults) {
					result[i] = Colors.RED + matcher.group(1) + Colors.NORMAL + 
						" (" + Colors.REVERSE + 
						matcher.group(2).replaceAll("<b>", Colors.BOLD).replaceAll("</b>", Colors.BOLD) +  Colors.NORMAL + ")";
					i++;
					//break;
				} else {
					matcher = _pattern_calc.matcher(line);
					if (matcher.matches()) {
						result[i] = "Calculator: "+matcher.group(1);
						i++;
						//break;
					}
				}
			}
		}
		catch(MalformedURLException mue)  {
			System.out.println("URL non corretto: " + mue.getMessage());
		}
		catch(IOException ioe)  {
			System.out.println("Errore nella richiesta o nella risposta HTTP: " + ioe.getMessage());
		}
	}
	
	public String getResults(int number){  
		String result = "";
		for(int i=0; i<number; i++) result += this.result[i] + "\n";
		return result; }
		
	public String getResult(int index){ 
		if(index<maxResults) return result[index]; 
		return null;
	}
	
	public String getResultPage(){ return resultPage; }
	
	public String getSearchString() { return search; }
	
	//public String getResult(int number){ return filterResult(number); }
	
	
	/*
	public static void main(String[] args){
		xGoogle asd = new xGoogle("ciccio inutile");
		System.out.println(asd.getResultPage());
		System.out.println("-----------------");
		System.out.println("risultati per: " + asd.getSearchString());
		System.out.println(asd.getResults(3));
	}*/
	
	/*public static void main(String[] args){
        try {
		// il server a cui connetersi
		URL url = new URL("http://www.google.it/search?hl=it&q=irc+%21google&btnG=Cerca&meta=");
		//connessione con il server e invio della Request HTTP
		URLConnection temp = url.openConnection();
		HttpURLConnection conn = (HttpURLConnection)temp;
		// lettura della Response HTTP
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String s;
			while((s = reader.readLine()) != null)
                System.out.println(s);
            // informazioni di servizio della Response HTTP
			System.out.println(System.getProperty("line.separator") + "---------------");
            System.out.println("Codice e messaggio della response: "
                    + conn.getResponseCode() + " " + conn.getResponseMessage());
            System.out.println("Tipo di contenuto della response: " + conn.getContentType());
            //rilascio delle risorse
            reader.close();
            conn.disconnect();
        }catch(MalformedURLException mue) 
                                        {
                                        System.out.println("URL non corretto: " + mue.getMessage());
                                        }
        catch(IOException ioe) 
                            {
                            System.out.println("Errore nella richiesta o nella risposta HTTP: " + ioe.getMessage());
                            }
	}*/
}