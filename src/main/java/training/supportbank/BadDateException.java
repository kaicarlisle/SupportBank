package training.supportbank;

public class BadDateException extends Exception {
	public String badDate;
	
	public BadDateException(String date) {
		this.badDate = date;
	}
}
