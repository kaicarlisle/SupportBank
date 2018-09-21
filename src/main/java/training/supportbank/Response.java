package training.supportbank;

public class Response {
	private String date;
	private String from;
	private String to;
	private String narrative;
	private Float amount;
	
	public String getDate() {
		return this.date;
	}
	
	public String getFrom() {
		return this.from;
	}
	
	public String getTo() {
		return this.to;
	}
	
	public String getNarrative() {
		return this.narrative;
	}
	
	public String getAmount() {
		return String.valueOf((0 - this.amount));
	}
	
	@Override
	public String toString() {
		return this.narrative;
	}
}
