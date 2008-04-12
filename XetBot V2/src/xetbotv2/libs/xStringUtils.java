package xetbotv2.libs;

import java.util.Random;
import java.util.StringTokenizer;
/**
 * Some usefull functions for string managment
 * @author setola
 * @version 1.0
 */
public class xStringUtils {
	/**
	 * Take care of the string to manage
	 */
	private String s;
	
	/**
	 * Remembers the StringTokenizer of s
	 * This is reset on every call of 
	 * {@link xetbotv2.libs.xStringUtils#setString(String) setString}
	 * and set by {@link xetbotv2.libs.xStringUtils#getWordsNumber() getWordsNumber}
	 * or {@link xetbotv2.libs.xStringUtils#getLettersNumber() getLettersNumber}
	 */
	private StringTokenizer st;
	
	/**
	 * store the number of words contained 
	 * in the main string.
	 * This is reset on every call of 
	 * {@link xetbotv2.libs.xStringUtils#setString(String) setString}
	 * and set by {@link xetbotv2.libs.xStringUtils#getLettersNumber() getLettersNumber}
	 */
	private int numberOfWords;
	
	/**
	 * store the number of letters for every 
	 * word contained in main string
	 */
	private int[] numberOfLetters;
	
	/**
	 * Default constructor: 
	 * initializes s and additional info to null
	 */
	public xStringUtils(){ 
		this.s = null;
		st = null;
		numberOfLetters = null;
	}
	
	/**
	 * The most complete constructor: 
	 * initializes s to given string
	 * @param s the given string
	 */
	public xStringUtils(String s){ 
		this.s = s; 
		st = null;
		numberOfLetters = null;
	}
	
	/**
	 * Change the main string and reset additional info
	 * @param s the given string
	 */
	public void setString(String s){ 
		this.s = s; 
		st = null;
		numberOfLetters = null;
	}
	
	/**
	 * Gives you back a string where you can read 
	 * the first letter after a space character 
	 * and the space itself.
	 * For Example if main string is "Hello World"
	 * this method returns "H**** W****" 
	 * (quotes are not included in string)
	 * Note that this function sets 
	 * {@link xetbotv2.libs.xStringUtils#st st} to null
	 * @return string where you can read only 
	 * first letters of any words of the initial string
	 */
	public String getOnlyFirstLetter(){
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
		//reset the StringTokenizer
		st = null;
		return result;
	}

	/**
	 * Gives you back a string where you can read 
	 * some random characters and spaces.
	 * For Example if main string is "Hello World"
	 * this method returns "H*ll* W***d" 
	 * (quotes are not included in string)
	 * This algorithm gives you 30% of chance 
	 * to get the real char (%3==0)
	 * @return string where you can read some 
	 * random letters of the initial string
	 */
	public String getRandomScrambled(){
		String result = "";
		Random rnd = new Random();

		result += String.valueOf(s.charAt(0));
		for(int i=1; i<s.length();i++)
			if(s.charAt(i)==' ') result += " ";
			else if(rnd.nextInt()%3==0) result += String.valueOf(s.charAt(i));
			else result += "*";
		
		return result;
	}
	
	/**
	 * Gives you back full scrambled string
	 * where only spaces are showed
	 * @return scrambled version of the main string
	 */
	public String getAllScrambled(){
		String result = "";

		for(int i=1; i<s.length();i++)
			if(s.charAt(i)==' ') result += " ";
			else result += "*";
		
		return result;
	}
	
	/**
	 * Gives you the number of words 
	 * included in the main string.
	 * @return the number of the beast!
	 */
	public int getWordsNumber(){
		if(st == null){
			st = new StringTokenizer(s, " ");
			numberOfWords = st.countTokens();
		}
		return numberOfWords;
	}
	
	/**
	 * Gives you back an array containing the number 
	 * of letters of every word of the main string.
	 * Spaces are not counted.
	 * @return number of letters
	 */
	public int[] getLettersNumber(){
		if(st == null) getWordsNumber();
		if(numberOfLetters==null){
			numberOfLetters = new int[st.countTokens()];
		
			int i = 0;
			while(st.hasMoreTokens()){
				numberOfLetters[i] = st.nextToken().length();
				System.out.println("i="+i+" = "+numberOfLetters[i]);
				i++;
			}
		}
		return numberOfLetters;
	}
	
	
	
	public static void main(String args[]){
		xStringUtils s = new xStringUtils("Suca in punta");
		System.out.println(s.getOnlyFirstLetter());
		System.out.println(s.getAllScrambled());
		System.out.println(s.getRandomScrambled());
		System.out.println("words: "+s.getWordsNumber());
		for(int i=0; i<s.getWordsNumber(); i++)
			System.out.println(s.getLettersNumber()[i]);
	}
	
}