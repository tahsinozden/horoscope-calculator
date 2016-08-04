package app.horoscope.calculator.controllers;

import java.io.IOException;
import java.time.format.DateTimeParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import app.horoscope.calculator.tools.HoroscopeHelper;

@RestController(value="/horoscope-calculator")
public class HoroscopeController {

	@Autowired
	private HoroscopeHelper horoscopeHelper;
	
	@RequestMapping
	public Response getHoroscope(@RequestParam String date) throws IOException{
//		System.out.println(horoscopeHelper.findHoroscope(date));
		Response res = null;
		if (horoscopeHelper.validateDate(date, "yyyy-MM-dd'T'HH:mm"))
			res = new Response(date, horoscopeHelper.findHoroscope(date), horoscopeHelper.findRisingSignHoroscope(date));
		else if (horoscopeHelper.validateDate(date, "yyyy-MM-dd") 
						&& date.length() == "yyyy-MM-dd".length()){
			res = new Response(date, horoscopeHelper.findHoroscope(date), "");
			System.out.println("HERE");
		}
		else
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
		
		return res;
	}
	
	class Response {
		public String date;
		public String horoscopeSign;
		public String risingHoroscopeSign;
		public Response() {}
		public Response(String date, String horoscopeSign, String risingHoroscopeSign) {
			super();
			this.date = date;
			this.horoscopeSign = horoscopeSign;
			this.risingHoroscopeSign = risingHoroscopeSign;
		}

		
	}
}
