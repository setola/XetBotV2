package xetbotv2.libs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringTokenizer;

/**
 * Irc port of a "answer the question" game.
 * Manages questions from a mySQL db, checks 
 * for correct answers and manages delays
 * between questions. The game is startable and stoppable
 * and store an hall of fame and current quiz rank
 * @author setola
 * @version 1.0
 */
public class xQuiz {
	/**
	 * stores the current question
	 */
	private Question question;
	
	/**
	 * remember if the quiz is running or not
	 */
	private boolean quizIsStarted;
	
	/**
	 * is the systema waiting for answers or is idling?
	 */
	private boolean quizIsWaitingForAnswers;
	
	/**
	 * standard jdbc URL to question database
	 */
	private String jdbcurl;
	
	/**
	 * this is the number of hint you have requested until now
	 * i.e. how many times you say !hint or similar.
	 * will never be greater than MAX_NUMBER_OF_HINTS 
	 */
	private int currentHint;
	
	/**
	 * for optimizations this remembers every 
	 * hint for the current question
	 */	
	private String[] hints;
	
	/**
	 * For optimizations this remembers number
	 * of words of the right answer
	 */
	private int numberOfWords;
	
	/**
	 * For optimizations this remembers number
	 * of letters of the every word of the right answer 
	 * Spaces are not Counted.
	 */
	private int[] numberOfLetters;
	
	/**
	 * max number of hints... 3 is default
	 */
	public static int MAX_NUMBER_OF_HINTS = 3;
	
	/**
	 * Default constructor: initializes private 
	 * variables and stores jdbc URL
	 * 
	 * @param jdbcurl
	 */
	public xQuiz(String jdbcurl){
		this.jdbcurl = jdbcurl;
		quizIsStarted = false;
		quizIsWaitingForAnswers = false;
		currentHint = 0;
	}
	
	/**
	 * starts the game and retrieves a brand 
	 * new question from db only if game is stopped.
	 */
	public void start(){
		if(!quizIsStarted){
			getQuestionFromDB();
			quizIsStarted = true;
		}
	}
	
	/**
	 * Stops the game
	 */
	public void stop(){
		if(quizIsStarted)quizIsStarted = false;
	}
	
	/**
	 * Retrieves current game ranks if game is started
	 */
	public void getRank(){
		
	}
	
	/**
	 * If the game is running and the system is
	 * waiting for an answer, this checks if the 
	 * given answer is correct by calling 
	 * Question.isCorrect() method.
	 * 
	 * @param answer answer to be checked
	 * @return true if your answer is correct, false otherwise
	 */
	public boolean checkAnswer(String answer){
		if(quizIsWaitingForAnswers && question.isCorrect(answer)){
			quizIsWaitingForAnswers = false;
			return true;
		}
		return false;
	}

	/**
	 * Sets system to "wait for an answer"
	 * and gives you back the current question
	 * 
	 * @return String representing current question
	 */
	public String getQuestion(){
		quizIsWaitingForAnswers = true;
		return question.get(Question.QUESTION);
	}
	
	/**
	 * Gives back the author of current question
	 * 
	 * @return who added this question
	 */
	public String getAuthor(){return question.get(Question.AUTHOR);}
	
	/**
	 * Connects to the database, retrieves a 
	 * random question and stores it in question.
	 * It also generate automatic hints if none was 
	 * found in database and stores in hints
	 */
	private void getQuestionFromDB(){
		xSQL db = new xSQL(jdbcurl);
		db.openConnection();
		boolean hasResults = false;
		
		String query = "SELECT * FROM quiz_questions ORDER BY RAND() LIMIT 0,1";
		
		hasResults = db.runQuery(query);
		ResultSet rs = db.getResults();
		
		String hintsList = "";
		
		if(hasResults)
		try{
			hintsList = rs.getString("hints");
			question = new Question(
				rs.getInt("id"), rs.getString("question"), 
				rs.getString("answer"), rs.getString("subject"), 
				rs.getString("author"), rs.getTimestamp("lasttime"),
				rs.getString("hints"));
		}
		catch(SQLException e){e.printStackTrace();}
		
		db.relaseResources();
		
		xStringUtils s = new xStringUtils(question.get(Question.ANSWER));
		
		if(question.get(Question.HINTS) != null){ //manage hints from db
			StringTokenizer tokens = new StringTokenizer(hintsList, "\n");
			int numberOfHints = tokens.countTokens();
			hints = new String[numberOfHints];
			for(int i=0;i<numberOfHints;i++)
				hints[i] = tokens.nextToken();
			s = new xStringUtils("");
		}
		else{ //generate automatic hints
			hints = new String[MAX_NUMBER_OF_HINTS];
			hints[0] = s.getAllScrambled();
			hints[1] = s.getOnlyFirstLetter();
			hints[2] = s.getRandomScrambled();
		}
		
		numberOfLetters = s.getLettersNumber();
		numberOfWords = s.getWordsNumber();
	}
	
	/**
	 * Returns number of letters and words
	 * in the answer
	 * 
	 * @return generic hints
	 */
	public String getInitialHints(){
		String result = String.valueOf(numberOfWords);
		if(numberOfWords>1) result += " parole ( ";
		else result += " parola ( ";
		
		for(int i=0; i<numberOfLetters.length; i++)
			result += String.valueOf(numberOfLetters[i])+" ";
		
		return result+")";
	}
	
	/**
	 * Gives you back an appropriate hint to 
	 * answer current question.
	 * Next time you call this method it will 
	 * return next hint if possible, elsewhere
	 * the last one.
	 * 
	 * @return current hint
	 */
	public String hint(){
		if(hints == null) return "";
		String result = hints[currentHint];
		if(currentHint < hints.length-1) currentHint++;
		return result;
	}
	
	/**
	 * Return true only if the system is waiting for answers
	 */
	public boolean isWaitingForAnswers(){ return quizIsWaitingForAnswers; }
	
	/**
	 * Return true only if the game is started
	 */
	public boolean isStarted(){ return quizIsStarted; }
	
	/**
	 * If none can give the correct answer, 
	 * let's skip to next one
	 */
	public void next(){
		getQuestionFromDB();
	}
	
	/**
	 * Retrieves a single question without starting the game.
	 * Usefull for short gaming sessions...
	 */
	public void single(){
		getQuestionFromDB();
	}
	
	/**
	 * Returns a string representing current question's parameters
	 * 
	 * @deprecated useless...
	 */
	public String toString(){
		return 	"current question: "+question.get(Question.QUESTION)+
				"\nanswer: "+question.get(Question.ANSWER)+
				"\nauthor: "+question.get(Question.AUTHOR)+
				"\ntimestamp: "+question.get(Question.LASTTIME)+
				"\nsubject: "+question.get(Question.SUBJECT);
	}
	
	
	public static void main(String args[]){
		xQuiz quiz = new xQuiz("jdbc:mysql://localhost/ircbot?user=root&password=");
		quiz.getQuestionFromDB();
		System.out.println(quiz);
		System.out.println(quiz.getInitialHints());
		System.out.println(quiz.hint());
		System.out.println(quiz.hint());
		System.out.println("-------------");
		System.out.println(quiz.hint());
		System.out.println(quiz.hint());
	}
	
}
	

	

	
	
