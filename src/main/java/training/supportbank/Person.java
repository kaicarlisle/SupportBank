package training.supportbank;

import java.util.LinkedList;

public class Person implements Comparable<Person> {
	private String name;
	private Float balance;
	private LinkedList<Record> records;
		
	public Person(String name) {
		this.name = name;
		this.balance = 0%.2f;
		this.records = new LinkedList<Record>();
	}
	
	public LinkedList<Record> getRecords() {
		return this.records;
	}
	
	public void addRecord(Record record) {
		this.records.add(record);
	}
	
	public void deduct(Float amount) {
		this.balance += amount;
	}
	
	public void receive(Float amount) {
		this.balance -= amount;
	}
	
	public Float getAmountThisOwesA(Person A) {
		Float total = 0%.2f;
		for (Record r : this.records) {
			if (r.getTo().equals(A)) {
				total += r.getAmount();
			}
			if (r.getFrom().equals(A)) {
				total -= r.getAmount();
			}
		}
		return total;
	}
	
	public String getName() {
		return this.name;
	}
	
	@Override 
	public String toString() {
		return String.format("%1$-9s %2$-1s £%3$.2f", this.name, ":", this.balance);
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
