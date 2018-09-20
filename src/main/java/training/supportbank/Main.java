package training.supportbank;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
	private static final Logger LOGGER = LogManager.getLogger();
	
    public static void main(String args[]){    	
		BufferedReader reader;
		String delimiter = ",|\\$|\\^";
		
		LinkedList<File> files = new LinkedList<File>();
		files.add(addFile("Transactions2014.csv"));
		files.add(addFile("DodgyTransactions2015.csv"));
//		files.addAll(addFile)
//		files.add(addFile("test.csv"));
		
		HashSet<Person> people = new HashSet<Person>();
		LinkedList<Record> records = new LinkedList<Record>();
		String line;
		String sLine[];
		String displayMode;
		
		try{
			for (File file : files) {
				reader = new BufferedReader(new FileReader(file));
				//skip past header line of csv
				line = reader.readLine();
				
				//populate people set
				//populate record list
				while ((line = reader.readLine()) != null) {
					sLine = line.split(delimiter);
					people = populatePeopleSet(sLine, people);
					records = populateRecordList(sLine, records, people);
				}
				
				reader.close();
			}
		} catch (FileNotFoundException e) {
			LOGGER.log(Level.FATAL, "No such file");
		} catch (IOException e) {
			LOGGER.log(Level.FATAL, "IO exception");
		}
		
		for (Record r : records) {
			//add each record to the person who owes money
			r.getFrom().addRecord(r);
			
			//remove the sent amount from the sender, and add it to the receiver
			r.getFrom().deduct(r.getAmount());
			r.getTo().receive(r.getAmount());
		}
		
		displayMode = getDisplayMode();
		display(people, displayMode);
    }

	private static HashSet<Person> populatePeopleSet(String[] sLine, HashSet<Person> people) {
		people.add(new Person(sLine[1]));
		people.add(new Person(sLine[2]));
		return people;
	}
	
	private static LinkedList<Record> populateRecordList(String[] sLine, LinkedList<Record> records, HashSet<Person> people) {
		try {
			records.add(new Record(sLine, people));
		} catch (BadDateException e) {
			LOGGER.log(Level.WARN, "Invalid Date in file: " + e.badDate);
		} catch (NumberFormatException e) {
			LOGGER.log(Level.WARN, "Invalid Amount in file: " + e.getMessage());
		}
		return records;
	}
	
	private static String getDisplayMode() {
		String displayMode;
		do {
			try {
				System.out.print("Enter the list you want to view: ");
				Scanner reader = new Scanner(System.in);
				displayMode = reader.nextLine();
				reader.close();
			} catch (InputMismatchException e) {
				System.out.println("Invalid input");
				displayMode = null;
			}
		} while (displayMode == null);
		return displayMode;
	}
	
	private static File addFile(String filename) {
		File file = new File(filename);
		return file;
	}
	
	private static void display(HashSet<Person> people, String displayMode) {
		LinkedList<Person> curPeople = new LinkedList<Person>();
		Person newPerson = new Person(displayMode);
		HashSet<Record> filteredRecords;
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
								LOGGER.log(Level.WARN, "Invalid number when negating " + r);
							} catch (BadDateException e) {
								LOGGER.log(Level.WARN, "Invalid date when negating " + r);
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
