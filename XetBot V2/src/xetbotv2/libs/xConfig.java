package xetbotv2.libs;

import xetbotv2.libs.xSQL;

/**
	retrives and manage basic config stored in db
*/
public class xConfig{
	public static final int CONFIG_JDBCURL = 0;
	public static final int CONFIG_ID = 1;
	public static final int CONFIG_SERVER = 2;
	public static final int CONFIG_NICKNAME = 3;
	public static final int CONFIG_NICKNAME2 = 4;
	public static final int CONFIG_ENCODING = 5;
	public static final int CONFIG_FINGER_MESSAGE = 6;
	public static final int CONFIG_ANTIFLOOD_DELAY = 7;
	public static final int CONFIG_LOGIN = 8;
	
	/** the reference database */
	private xSQL db;
	
	/** mainteins data retrived from db */
	private String[] config;
	
	/** id of the wanted config row */
	private String id;
	
	
	public xConfig(String jdbcurl, String id){
		db = new xSQL(jdbcurl);
		this.id = id;
		config = new String[9];
    config[0] = jdbcurl;
		init();
	}
	
	private void init(){
		db.openConnection();
		if(db.runQuery("select * from botconfig where id='"+id+"'")){
			for(int i = 1; i < config.length; i++){
				config[i] = "";
				config[i] += db.getResultString(i);
			}
		}
	}
	
	public String get(int what) {
		if(what>=config.length) return "";
		return config[what]; 
	}
	
	
	
	

	public static void main(String[] args){
		xConfig asd = new xConfig("jdbc:mysql://localhost/ircbot?user=root&password=texrulez", "1"); 
		
		for(int i=0; i<=9; i++) System.out.println("param"+i+asd.get(i));
	}
  
}