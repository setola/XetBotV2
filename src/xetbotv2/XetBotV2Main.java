package xetbotv2;

import xetbotv2.libs.xConfig;

public class XetBotV2Main {
	public static void main(String[] args){
    
    if(args.length < 2) {
      printHelp();
      System.exit(1);
    }
		
		new XetBotV2(new xConfig(args[0], args[1]));
	}
  
  private static void printHelp(){
    System.out.println(
    "XetBot V2 - utilizzo del comando:\n"+
    "XetBotV2Main <jdbcurl> <number>\n"
    );
  }
}
