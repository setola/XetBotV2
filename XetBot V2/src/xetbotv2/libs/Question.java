package xetbotv2.libs;

import java.sql.Timestamp;

public class Question{
	private String id;
	private String question;
	private String answer;
	private String subject;
	private String author;
	private String lasttime;
	private String hints;

	public static final int ID = 0;
	public static final int QUESTION = 1;
	public static final int ANSWER = 2;
	public static final int SUBJECT = 3;
	public static final int AUTHOR = 4;
	public static final int LASTTIME = 5;
	public static final int HINTS = 6;
		
	public Question(String id, String question, String answer, 
			String subject, String author, String lasttime, String hints){
		this.id = id;
		this.question = question;
		this.answer = answer;
		this.subject = subject;
		this.author = author;
		this.lasttime = lasttime;
		this.hints = hints;
	}

	public Question(int id, String question, String answer, 
			String subject, String author, Timestamp lasttime, String hints){
		this(String.valueOf(id),question,answer,subject,author,lasttime.toString(),hints);
	}
	public Question(String question, String answer){
		this(0,question,answer,null,null,null,null);
	}
	
	public Question(){
		this(0,null,null,null,null,null,null);
	}
		
	public boolean isCorrect(String answer){
		return this.answer.equalsIgnoreCase(answer);
	}
	
	public String get(int what){ 
		String result = "";
		switch (what){
		case ID:
			result = id;
			break;
		case QUESTION:
			result = question;
			break;
		case ANSWER:
			result = answer;
			break;
		case SUBJECT:
			result = subject;
			break;
		case AUTHOR:
			result = author;
			break;
		case HINTS:
			result = hints;
			break;
		case LASTTIME:
			result = lasttime.toString();
			break;
		}
		return result;
	}
	
	public void set(int what, String value){ 
		switch (what){
		case ID:
			this.id = value;
			break;
		case QUESTION:
			this.question = value;
			break;
		case ANSWER:
			this.answer = value;
			break;
		case SUBJECT:
			this.subject = value;
			break;
		case AUTHOR:
			this.author = value;
			break;
		case HINTS:
			this.hints = value;
			break;
		case LASTTIME:
			this.lasttime = value;
			break;
		}
	}
}