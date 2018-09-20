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
		this.amount = Float.parseFloat(sLine[4]);
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
	
	public String negateString() {
		return this.date + " " + narrative + " -" + String.format("%.2f", this.amount);
	}
	
	@Override
	public String toString() {
		return this.date + " " + narrative + " " + String.format("%.2f", this.amount);
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
