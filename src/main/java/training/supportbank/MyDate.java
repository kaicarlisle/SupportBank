package training.supportbank;

public class MyDate {
	private String day;
	private String month;
	private String year;
	
	private String validDay = "[0-2][0-9]|[3][0-1]"; //00-31
	private String validMonth = "[0][9]|[1][0-2]"; //00-12
	private String validYear = "[0-2][0-9][0-9][0-9]"; //0000-2999
	
	public MyDate(String date) throws BadDateException {
		String[] fields;
		try {
			fields = date.split("/");
			this.day = String.format("%02d", Integer.parseInt(fields[0]));
			this.month = String.format("%02d", Integer.parseInt(fields[1]));
			this.year = String.format("%04d", Integer.parseInt(fields[2]));
			
		} catch (Exception e) {
			try {
				fields = date.split("-");
				this.day = String.format("%02d", Integer.parseInt(fields[2]));
				this.month = String.format("%02d", Integer.parseInt(fields[1]));
				this.year = String.format("%04d", Integer.parseInt(fields[0]));
			} catch (Exception f) {
				throw new BadDateException(date);
			}
		}
	}
	
	@Override
	public String toString() {
		return this.day + "/" + this.month + "/" + this.year;  
	}
}
