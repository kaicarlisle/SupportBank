package training.supportbank;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;

import org.apache.logging.log4j.Logger;

public class CSVParser extends FileParser {
	private String csvDelimiter;
	private String line;
	private String sLine[];

	public CSVParser(HashSet<Person> people, BufferedReader reader, Logger LOGGER, String delimiter) {
		super(people, reader, LOGGER);
		this.csvDelimiter = delimiter;
	}
	
	public void resetReader(File file) throws FileNotFoundException {
		this.reader = new BufferedReader(new FileReader(file));
	}

	public LinkedList<Record> parseRecords() throws IOException, NumberFormatException, BadDateException {
		//skip past header line of csv
		this.line = this.reader.readLine();
		
		//populate record list
		while ((this.line = this.reader.readLine()) != null) {
			this.sLine = this.line.split(this.csvDelimiter);
			this.records.add(new Record(sLine, people));
		}
		return this.records;
	}

	public HashSet<Person> parsePeople() throws IOException {
		//skip past header line of csv
		this.line = this.reader.readLine();
		
		//populate people set
		while ((this.line = this.reader.readLine()) != null) {
			this.sLine = this.line.split(this.csvDelimiter);
			this.people.add(new Person(sLine[1]));
			this.people.add(new Person(sLine[2]));
		}
		return this.people;
	}
}
