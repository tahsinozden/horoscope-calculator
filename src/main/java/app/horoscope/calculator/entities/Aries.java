package app.horoscope.calculator.entities;

import java.util.HashMap;

public final class Aries extends Horoscope {
		private HashMap<String, String> RISING_SIGN_MAP; 
//	    static
//	    {
//	    	RISING_SIGN_MAP = new HashMap<String, Horoscope>();
//	    	RISING_SIGN_MAP.put("a", "b");
//	    	RISING_SIGN_MAP.put("c", "d");
//	    }
	    
		public Aries(){
			super("ARIES", "03-21", "04-19");

	    	RISING_SIGN_MAP = new HashMap<String, String>();
	    	RISING_SIGN_MAP.put("46", "ARIES");
	    	RISING_SIGN_MAP.put("24", "PISCES");
	    	RISING_SIGN_MAP.put("02", "AQUARIUS");
	    	RISING_SIGN_MAP.put("220", "CAPRICORN");
	    	RISING_SIGN_MAP.put("2022", "SAGITTARIUS");
	    	RISING_SIGN_MAP.put("1820", "SCORPIO");
	    	RISING_SIGN_MAP.put("1618", "LIBRA");
	    	RISING_SIGN_MAP.put("1416", "VIRGO");
	    	RISING_SIGN_MAP.put("1214", "LEO");
	    	RISING_SIGN_MAP.put("1012", "CANCER");
	    	RISING_SIGN_MAP.put("810", "GEMINI");
	    	RISING_SIGN_MAP.put("68", "TAURUS");
	    	
		}
		
		public String getRisingSign(String time){
			return RISING_SIGN_MAP.get(this.createIndexFromTime(time));
		}
	}