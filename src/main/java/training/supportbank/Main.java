package training.supportbank;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
	private static final Logger LOGGER = LogManager.getLogger();
	
    public static void main(String args[]){
		File file = new File("Transactions2014.csv");
//    	File file = new File("test.csv");
		BufferedReader reader;
		String delimiter = ",|\\$|\\^";
		
		HashSet<Person> people = new HashSet<Person>();
		LinkedList<Record> records = new LinkedList<Record>();
		Float alreadyOwes;
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
				
				//update the sender individual owes amounts
				if (r.getFrom().getIndividualOwes().containsKey(r.getTo())) {
					alreadyOwes = r.getFrom().getIndividualOwes().get(r.getTo());
				} else {
					alreadyOwes = 0%.2f;
				}
				alreadyOwes -= r.getAmount();
				r.getFrom().setIndividualOwes(r.getTo(), alreadyOwes);
				
				//remove the sent amount from the sender, and add it to the receiver
				r.getFrom().deduct(r.getAmount());
				r.getTo().receive(r.getAmount());
			}
			
			display(people, "All");
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
			Record curRecord = new Record(sLine, people);
			records.add(curRecord);
		} catch (ParseException e) {
			LOGGER.log(Level.WARN, "Invalid record in file");
		} 
		return records;
	}
	
	private static void display(HashSet<Person> people, String displayMode) {
		HashMap<Person, Float> individualOwes;
		if (displayMode.equals("All")) {
			for (Person p : people) {
				System.out.println(p + " : " + p.getBalance());
				individualOwes = p.getIndividualOwes();
				for (Person q : individualOwes.keySet()) {
					System.out.println("    " + q + " : " + String.format("%.2f", individualOwes.get(q)));
				}
			}
		} else { //displayMode is someone's name
			Boolean foundPerson = false;
			Person curPerson = new Person(displayMode);
			for (Person q : people) {
				if (q.equals(curPerson)) {
					foundPerson = true;
					curPerson = q;
					break;
				}
			}
			if (foundPerson) {
				System.out.println(curPerson + " : " + String.format("%.2f", curPerson.getBalance()));
				
				individualOwes = curPerson.getIndividualOwes();
				for (Person p : individualOwes.keySet()) {
					System.out.println("    " + p + " : " + String.format("%.2f", individualOwes.get(p)));
					//People who owe curPerson
					for (Record r : p.getRecords()) {
						if (r.getTo().equals(curPerson)) {
							System.out.println("        " + r);
						}
					}
					//people curPerson owes
					for (Record r : curPerson.getRecords()) {
						if (r.getTo().equals(p)) {
							System.out.println("        " + r.negateString());
						}
					}
				}
			} else {
				System.out.println("No records found for " + displayMode);
			}
		}
	}
}
