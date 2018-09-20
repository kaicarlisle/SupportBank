package training.supportbank;

import java.util.HashMap;
import java.util.LinkedList;

public class Person implements Comparable<Person> {
	private String name;
	private Float balance;
	private LinkedList<Record> records;
	//maps how much this person owes to each other person
	private HashMap<Person, Float> individualOwes;
		
	public Person(String name) {
		this.name = name;
		this.balance = 0%.2f;
		this.records = new LinkedList<Record>();
		this.individualOwes = new HashMap<Person, Float>();
	}
	
	public LinkedList<Record> getRecords() {
		return this.records;
	}
	
	public void addRecord(Record record) {
		this.records.add(record);
	}
	
	public void deduct(Float amount) {
		this.balance -= amount;
	}
	
	public void receive(Float amount) {
		this.balance += amount;
	}
	
	public Float getBalance() {
		return this.balance;
	}
	
	public HashMap<Person, Float> getIndividualOwes() {
		return this.individualOwes;
	}
	
	public void setIndividualOwes(Person p, Float amount) {
		this.individualOwes.put(p, amount);
	}
	
	@Override 
	public String toString() {
		return (this.name);
	}
	
	@Override
	public boolean equals(Object b) {
		if (b instanceof Person) {
			Person c = (Person) b;
			return (this.name.equals(c.name));
		} else {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		return 1;
	}

	@Override
	public int compareTo(Person o) {
		if (this.balance > o.balance) {
			return 1;
		} else if (this.balance == o.balance) {
			return 0;
		} else if (this.balance < o.balance) {
			return -1;
		} else {
			return 0;
		}
	}
}
