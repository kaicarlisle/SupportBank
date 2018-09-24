package training.supportbank;

public class JSONResponse {
	private String date;
	private String fromAccount;
	private String toAccount;
	private String narrative;
	private Float amount;
	
	public String getDate() {
		return this.date;
	}
	
	public String getFrom() {
		return this.fromAccount;
	}
	
	public String getTo() {
		return this.toAccount;
	}
	
	public String getNarrative() {
		return this.narrative;
	}
	
	public String getAmount() {
		return String.valueOf((0 - this.amount));
	}
	
	@Override
	public String toString() {
		return this.date + this.fromAccount + this.toAccount + this.narrative + this.amount;
	}
}
