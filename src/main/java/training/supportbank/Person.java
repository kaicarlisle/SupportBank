package training.supportbank;

import java.util.LinkedList;

public class Person implements Comparable<Person> {
	private String name;
	private float balance;
	private LinkedList<Record> records;
		
	public Person(String name) {
		this.name = name;
		this.balance = 0%.2f;
		records = new LinkedList<Record>();
	}
	
	public void addRecord(Record record) {
		this.records.add(record);
		this.balance -= record.getAmountSpent();
	}
	
	public LinkedList<Record> getRecords() {
		return this.records;
	}
	
	public void receiveAmount(float amount) {
		this.balance += amount;
	}
	
	@Override 
	public String toString() {
		return (this.name + " : " + this.balance);
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
