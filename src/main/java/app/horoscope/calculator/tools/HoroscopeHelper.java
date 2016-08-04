package app.horoscope.calculator.tools;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeParseException;
import java.util.Date;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

@Component
public class HoroscopeHelper {
	
	private String createIndexFromTime(String time){
		// replace : char if exist with . char
		time = time.replace(':', '.');
		
		String index = "";
		// time must be passed in 24 h format and sperated by dot. ( 7.10, 14.47 etc)
		int timeVal = Double.valueOf(time).intValue();
		if (timeVal % 2 == 0){
			index = String.valueOf(timeVal) + String.valueOf(timeVal + 2);
		}
		else {
			index = String.valueOf(timeVal-1) + String.valueOf(timeVal + 1);
		}
		return index;
	}
	
	private String getHoroscope(String date) throws IOException{
		String horoscope = "";
		Reader in = new FileReader("horoscopes.csv");
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader().parse(in);
		for (CSVRecord record : records) {
			String timeStart = record.get("TimeStart"); 
			String timeEnd = record.get("TimeEnd"); 
			// if the time is matched, get the horoscope
			if ( date.compareTo(timeStart) >= 0  && date.compareTo(timeEnd) <= 0){
				horoscope = record.get("Horoscope");
				break;
			}
		}
		return horoscope;
	}
	
	private String getRisingSign(String time, String horoscope) throws IOException{
		String index = this.createIndexFromTime(time);
		String rising = "";
		
		Reader in = new FileReader("rising_signs.csv");
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader().parse(in);
		for (CSVRecord record : records) {
			if (record.get("TimeIndex").equals(index)){
				rising = record.get(horoscope);
			}
		}
		return rising;
	}
	
	public boolean validateDate(String strDate, String format){
		System.out.println("Date got with format : " + format + ", date : " + strDate);
		// check the length
//		if (strDate.length() != format.length())
//			return false;
		
		try {
//			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
			DateFormat formatter = new SimpleDateFormat(format);
			Date date = formatter.parse(strDate);
		} catch (Exception e) {
			System.err.println("Date parse error with format " + format + " -> " + e.getMessage());
			return false;
		}
		return true;
		
	}
	
	public void parseCSV() throws IOException{
		Reader in = new FileReader("rising_signs.csv");
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader().parse(in);
		for (CSVRecord record : records) {
			System.out.println(record.get("Time"));
		}
	}
	
	public String findRisingSignHoroscope(String fullDate) throws IOException{
		if (!validateDate(fullDate, "yyyy-MM-dd'T'HH:mm"))
			throw new DateTimeParseException(null, null, 0, null);
		
		String[] fullTime = fullDate.split("T");
		String date = fullTime[0];
		String time = fullTime[1];
		
		// get month and the day
		String modDate = date.substring(5);
		String horoscope = this.getHoroscope(modDate);
		String rising = this.getRisingSign(time, horoscope);
		return rising;
	}
	
	public String findHoroscope(String date) throws IOException{
		if (!validateDate(date.substring(0, "yyyy-MM-dd".length()), "yyyy-MM-dd"))
			throw new DateTimeParseException(null, null, 0, null);
		
		// get month and day
		String modDate = date.substring(5, 10).replace('.', '-');
		return this.getHoroscope(modDate);
	}
}
