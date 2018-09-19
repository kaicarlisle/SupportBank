package training.supportbank;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class Main {
    public static void main(String args[]){
//		File file = new File("Transactions2014.csv");
    	File file = new File("test.csv");
		BufferedReader reader;
		String delimiter = ",|\\$|\\^";
		
		Set<Person> people = new HashSet<Person>();
		
		try{
			reader = new BufferedReader(new FileReader(file));
			//skip past header line of csv
			String line = reader.readLine();
			String[] sLine;
			
			//populate people list, a unique list of Person objects
			while ((line = reader.readLine()) != null) {
				sLine = line.split(delimiter);
				people = populatePeopleList(sLine, people);
			}
			for (Person p : people) {
				System.out.println(p);
				for (Record r : p.getRecords()) {
					System.out.println(r);
				}
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

	private static Set<Person> populatePeopleList(String[] sLine, Set<Person> people) {
		people.add(new Person(sLine[1]));
		return people;
	}    
    
//    private static LinkedList<Person> populatePeopleList(String[] sLine, LinkedList<Person> people) throws ParseException {
//		Record curRecord = new Record(sLine, people);
//		people = curRecord.getPeople();
//		return people;
//	}
}
