package training.supportbank;

public class MyDate {
	private String day;
	private String month;
	private String year;
	
	public MyDate(String date) throws BadDateException {
		String[] fields;
		try {
			fields = date.split("/");
			this.day = String.format("%02d", Integer.parseInt(fields[0]));
			this.month = String.format("%02d", Integer.parseInt(fields[1]));
			this.year = String.format("%04d", Integer.parseInt(fields[2]));
			
		} catch (Exception e) {
			throw new BadDateException(date);
		}
	}
	
	@Override
	public String toString() {
		return this.day + "/" + this.month + "/" + this.year;  
	}
}
