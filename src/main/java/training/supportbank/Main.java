package training.supportbank;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashSet;
import java.util.LinkedList;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
	private static final Logger LOGGER = LogManager.getLogger();
	
    public static void main(String args[]){
//		File file = new File("Transactions2014.csv");
    	File file = new File("test.csv");
		BufferedReader reader;
		String delimiter = ",|\\$|\\^";
		
		HashSet<Person> people = new HashSet<Person>();
		LinkedList<Record> records = new LinkedList<Record>();
		String line;
		String sLine[];
		
		try{
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
			
			for (Record r : records) {
				//add each record to the person who owes money
				r.getFrom().addRecord(r);
				
				//remove the sent amount from the sender, and add it to the receiver
				r.getFrom().deduct(r.getAmount());
				r.getTo().receive(r.getAmount());
			}
			
			display(people, "Jon A");
			reader.close();
		} catch (FileNotFoundException e) {
			LOGGER.log(Level.FATAL, "No such file");
		} catch (IOException e) {
			LOGGER.log(Level.FATAL, "IO exception");
		}
    }

	private static HashSet<Person> populatePeopleSet(String[] sLine, HashSet<Person> people) {
		people.add(new Person(sLine[1]));
		people.add(new Person(sLine[2]));
		return people;
	}
	
	private static LinkedList<Record> populateRecordList(String[] sLine, LinkedList<Record> records, HashSet<Person> people) {
		try {
			records.add(new Record(sLine, people));
		} catch (ParseException e) {
			LOGGER.log(Level.WARN, "Invalid record in file");
		} 
		return records;
	}
	
	private static void display(HashSet<Person> people, String displayMode) {
		if (displayMode.equals("All")) {
			for (Person p : people) {
				System.out.println(p);
			}
		} else { //displayMode is someone's name
			Boolean foundPerson = false;
			Person curPerson = new Person(displayMode);
			HashSet<Record> filteredRecords;
			for (Person q : people) {
				if (q.equals(curPerson)) {
					foundPerson = true;
					curPerson = q;
					break;
				}
			}
			if (foundPerson) {
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
							filteredRecords.add(r.negate(people));
						}
					}
					if (filteredRecords.size() > 0) {
						Float amountCurPersonOwesP = curPerson.getAmountThisOwesA(p) - p.getAmountThisOwesA(curPerson);
						System.out.println(String.format("    %1$-7s %2$-1s £%3$.2f", p.getName(), ":", amountCurPersonOwesP));
						for (Record r : filteredRecords) {
							System.out.println("        " + r);
						}
					}
				}
			} else {
				System.out.println("No records found for " + displayMode);
			}
		}
	}
}
