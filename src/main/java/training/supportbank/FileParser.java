package training.supportbank;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;

import org.apache.logging.log4j.Logger;

public abstract class FileParser {
	protected BufferedReader reader;
	protected Logger LOGGER;

	protected LinkedList<Record> records;
	protected HashSet<Person> people;
	
	public FileParser(HashSet<Person> people, BufferedReader reader, Logger LOGGER) {
		this.reader = reader;
		this.LOGGER = LOGGER;
		
		this.people = people;
		this.records = new LinkedList<Record>();
	}
	
	public abstract LinkedList<Record> parseRecords() throws IOException;
	
	public abstract HashSet<Person> parsePeople() throws IOException;
	
	public abstract void resetReader(File file) throws FileNotFoundException;
}
