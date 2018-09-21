package training.supportbank;

import java.util.HashSet;
import java.util.LinkedList;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

public class Displayer {
	private LinkedList<Person> curPeople;
	private HashSet<Person> people;
	private String displayMode;
	private HashSet<Record> filteredRecords;
	private Logger LOGGER;
	
	public Displayer(HashSet<Person> people, String displayMode, Logger LOGGER) {
		this.people = people;
		this.displayMode = displayMode;
		this.curPeople = new LinkedList<Person>();
		this.filteredRecords = new HashSet<Record>();
		this.LOGGER = LOGGER;
	}
	
	public void display() {
		Person newPerson = new Person(this.displayMode);
		for (Person q : people) {
			if (q.equals(newPerson) || displayMode.toLowerCase().equals("all")) {
				curPeople.add(q);
			}
		}
		if (curPeople.size() > 0) {
			for (Person curPerson : curPeople) {
				System.out.println(curPerson);				
				for (Person p : people) {
					filteredRecords = new HashSet<Record>();
					for (Record r : curPerson.getRecords()) {
						if ((r.getFrom().equals(curPerson) && r.getTo().equals(p))) {
							filteredRecords.add(r);
						}
					}
					for (Record r : p.getRecords()) {
						if ((r.getFrom().equals(p) && r.getTo().equals(curPerson))) {
							try {	
								filteredRecords.add(r.negate(people));
							} catch (NumberFormatException e) {
								this.LOGGER.log(Level.WARN, "Invalid number when negating " + r);
							} catch (BadDateException e) {
								this.LOGGER.log(Level.WARN, "Invalid date when negating " + r);
							}
							
						}
					}
					if (filteredRecords.size() > 0) {
						Float amountCurPersonOwesP = curPerson.getAmountThisOwesA(p) - p.getAmountThisOwesA(curPerson);
						System.out.println(String.format("\t%1$-9s %2$-1s £%3$.2f", p.getName(), ":", amountCurPersonOwesP));
						if (curPeople.size() == 1) {
							for (Record r : filteredRecords) {
								System.out.println("\t\t" + r);
							}
						}
					}
				}
			}
		} else {
			System.out.println("No records found for " + displayMode);
		}
	}
}
