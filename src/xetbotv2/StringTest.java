package xetbotv2;

import java.util.Random;

public class StringTest {

	private static String showOnlyFirstLetter(String s){
		String result = "";
		
		boolean isNewWord = true;
		for(int i=0; i<s.length();i++){
			if(isNewWord){
				result += s.charAt(i);
				isNewWord = false;
			}
			else if(s.charAt(i)==' ') {
				result += " ";
				isNewWord = true;
			}
			else result += "*";
		}
		return result;
	}
	
	private static String showScrambled(String s){
		String result = "";
		Random rnd = new Random();

		result += String.valueOf(s.charAt(0));
		for(int i=1; i<s.length();i++)
			if(s.charAt(i)==' ') result += " ";
			else if(rnd.nextInt()%3==0) result += String.valueOf(s.charAt(i));
			else result += "*";
		
		return result;
	}
	
	public static void main(String args[]){
		String s = "Suca in punta";
		System.out.println(showOnlyFirstLetter(s));
		System.out.println(showScrambled(s));
	}
}