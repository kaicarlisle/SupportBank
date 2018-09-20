package training.supportbank;

import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
import java.util.HashSet;

public class Record {
//	private Date date;
	private String date;
	private Person from;
	private Person to;
	private String narrative;
	private Float amount;
	
	public Record(String[] sLine, HashSet<Person> people) throws ParseException {
//		this.date = new SimpleDateFormat("dd/MM/yyyy").parse(sLine[0]);	
		this.date = sLine[0];
		
		//set from and to as the people in people list
		this.from = getPerson(sLine[1], people);
		this.to = getPerson(sLine[2], people);
		
		this.narrative = sLine[3];
		this.amount = 0 - Float.parseFloat(sLine[4]);
	}
	
	public Record(String date, String from, String to, String narrative, String amount, HashSet<Person> people) {
		this.date = date;
		this.from = getPerson(from, people);
		this.to = getPerson(to, people);
		this.narrative = narrative;
		this.amount = Float.parseFloat(amount);
	}
	
	public Person getFrom() {
		return this.from;
	}
	
	public Person getTo() {
		return this.to;
	}
	
	public Float getAmount() {
		return this.amount;
	}
	
	public Record negate(HashSet<Person> people) {
		String nAmount = String.valueOf(0 - this.amount);
		return new Record(this.date, this.from.getName(), this.to.getName(), this.narrative, nAmount, people);
	}
	
	@Override
	public String toString() {
		return String.format("%1$-13s %2$-20s £%3$.2f", this.date, this.narrative, this.amount);
	}
	
	private Person getPerson(String name, HashSet<Person> people) {
		Person curPerson = new Person(name);
		for (Person p : people) {
			if (p.equals(curPerson)) {
				return p;
			}
		}
		//should never be hit, as there will always be a person in people for all transactions
		return new Person("ERROR");
	}
}
