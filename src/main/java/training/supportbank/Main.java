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
		LinkedList<File> files = new LinkedList<File>();
//		files.add(new File("DodgyTransactions2015.csv"));
//		files.add(new File("Transactions2014.csv"));
//		files.add(new File("Transactions2013.json"));
//		files.add(new File("Transactions2012.xml"));
		files.add(new File("test.xml"));
		
		HashSet<Person> people = new HashSet<Person>();
		LinkedList<Record> records = new LinkedList<Record>();
		
		BufferedReader reader;
		String displayMode;
		FileParser parser = null;
		
		for (File file : files) {
			try {
				reader = new BufferedReader(new FileReader(file));

				if (file.getName().endsWith(".csv")) {
					parser = new CSVParser(people, reader, LOGGER, ",|\\$|\\^");
				} else if (file.getName().endsWith(".json")) {
					parser = new JSONParser(people, reader, LOGGER);
				} else if (file.getName().endsWith(".xml")) {
					parser = new XMLParser(people, reader, LOGGER, file);
				}
				
				if (parser != null) {
					people.addAll(parser.parsePeople());
					parser.resetReader(file);
					records.addAll(parser.parseRecords());
				}
				
				reader.close();
				
			} catch (FileNotFoundException e) {
				LOGGER.log(Level.FATAL, "No such file " + file.getName());
			} catch (IOException e) {
				LOGGER.log(Level.FATAL, "Failed to parse file " + file.getName());
			} catch (NullPointerException e) {
				LOGGER.log(Level.WARN, "No records found in " + file.getName());
			}
		}
		
		for (Record r : records) {
			//add each record to the person who owes money
			r.getFrom().addRecord(r);
			
			//remove the sent amount from the sender, and add it to the receiver
			r.getFrom().deduct(r.getAmount());
			r.getTo().receive(r.getAmount());
		}
		
		displayMode = getDisplayMode();
		Displayer displayer = new Displayer(people, displayMode, LOGGER);
		displayer.display();
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
}
