package app.horoscope.calculator.entities;

public class Horoscope {
	private String horoscopeName;
	private String startDate;
	private String endDate;
	public Horoscope(){}
	public Horoscope(String horoscopeName, String startDate, String endDate) {
		super();
		this.horoscopeName = horoscopeName;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	public String getHoroscopeName() {
		return horoscopeName;
	}
	public void setHoroscopeName(String horoscopeName) {
		this.horoscopeName = horoscopeName;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	public String createIndexFromTime(String time){
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
	
	@Override
	public String toString() {
		return "Horoscope [horoscopeName=" + horoscopeName + ", startDate=" + startDate + ", endDate=" + endDate + "]";
	}
	
	
}
