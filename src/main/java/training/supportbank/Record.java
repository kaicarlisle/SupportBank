package training.supportbank;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

public class Record {
	private Date date;
	private Person from;
	private Person to;
	private String narrative;
	private Float amount;
	
	public Record(String[] sLine) throws ParseException {
		this.date = new SimpleDateFormat("dd/MM/yyyy").parse(sLine[0]);
		
		//add from and to to this.people list, if they're not already in the list
		this.people = people;
		addPerson(sLine, 1);
		addPerson(sLine, 2);
		
		//set from and to as the people in people list
		this.from = getPerson(sLine[1]);
		this.to = getPerson(sLine[2]);
		
		this.narrative = sLine[3];
		this.amount = Float.parseFloat(sLine[4]);
		
		//add this record to the person who sent the money
		this.from.addRecord(this);
	}
	
	public LinkedList<Person> getPeople() {
		return this.people;
	}
	
	public float getAmountSpent() {
		this.to.receiveAmount(this.amount);
		return this.amount;
	}
	
	@Override
	public String toString() {
		return "    " + narrative;
	}
	
	private void addPerson(String[] sLine, Integer index) {
		Person curPerson = new Person(sLine[index]);
		if (!this.people.contains(curPerson)) {
			this.people.add(curPerson);
		} else {
			curPerson = this.people.get(this.people.indexOf(curPerson));
		}
	}
	
	private Person getPerson(String name) {
		Person curPerson = new Person(name);
		return people.get(people.indexOf(curPerson));
	}
}
