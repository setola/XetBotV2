package xetbotv2.modules;

import xetbotv2.libs.xCommandList;
import xetbotv2.libs.xConfig;
import xetbotv2.libs.xQuiz;
import xetbotv2.libs.xIRCCommand;
import org.jibble.pircbot.Colors;

public class XetBotQuizModule implements BotFunction {
	
	public static final String ON_TEXT_QUIZ_START = "!quizstart";
	public static final String ON_TEXT_QUIZ_STOP = "!quizstop";
	public static final String ON_TEXT_QUIZ_NEXT = "!quiznext";
	public static final String ON_TEXT_QUIZ_RANK = "!quizrank";
	public static final String ON_TEXT_QUIZ_HINT = "!quizhint";
	public static final String ON_TEXT_QUIZ_SINGLE_QUESTION = "!quizsingle";
	public static final String ON_TEXT_QUIZ_ALL_TIMES_RANK = "!alltimesrank";
	
	private String jdbcurl;
	private xQuiz quiz;
	
	public XetBotQuizModule(xConfig config){
	    jdbcurl = config.get(xConfig.CONFIG_JDBCURL);
		quiz = new xQuiz(jdbcurl);
	}
	
	public xCommandList onText(String text){

		xCommandList result = new xCommandList(10);   
		
		if (text.startsWith(ON_TEXT_QUIZ_START)){
			result.add(new xIRCCommand("/msg# "+
					Colors.RED+"XetQuiz 1.0"+Colors.NORMAL+
					" - inizializzazione di una nuova partita."));
			result.add(new xIRCCommand("/msg# "+Colors.YELLOW+"prego attendere..."+Colors.NORMAL));
			quiz.start();
		}
		
		else if (text.startsWith(ON_TEXT_QUIZ_STOP)){
			quiz.stop();
			result.add(new xIRCCommand("/msg# "+
					Colors.RED+"XetQuiz 1.0"+Colors.NORMAL+
					" - deallocazione della memoria in corso."));
			result.add(new xIRCCommand("/msg# "+Colors.YELLOW+"prego attendere..."+Colors.NORMAL));
			result.add(new xIRCCommand("/delay 5 s"));
			result.add(new xIRCCommand("/msg# deallocazione ultimata. Buona giornata."));
		}

		else if (text.startsWith(ON_TEXT_QUIZ_NEXT)){
			quiz.next();
		}
		
		else if (text.startsWith(ON_TEXT_QUIZ_RANK)){
			quiz.getRank();
		}
		
		else if (text.startsWith(ON_TEXT_QUIZ_HINT)){
			if(quiz.isWaitingForAnswers())
				result.add(new xIRCCommand("/msg# Suggerimento: "+quiz.hint()));
		}
		
		else if (text.startsWith(ON_TEXT_QUIZ_SINGLE_QUESTION)){
			quiz.single();
			result.add(new xIRCCommand("/msg# Sto selezionando la prossima domanda..."));
			result.add(new xIRCCommand("/delay 5 s"));
			result.add(new xIRCCommand("/msg# "+Colors.REVERSE+quiz.getQuestion()+Colors.NORMAL));
			result.add(new xIRCCommand("/msg# "+Colors.DARK_GREEN+quiz.getInitialHints()+Colors.NORMAL));
		}
		
		else{
			if(quiz.isWaitingForAnswers() && quiz.checkAnswer(text))
				result.add(new xIRCCommand("/msg# "+Colors.RED+"$user"+Colors.NORMAL+" rulla!"));
		}
		
		return result;
	}
	
	
	
	
	
	
}