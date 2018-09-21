package training.supportbank;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;

import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JSONParser extends FileParser {
	
	private Gson gson;
	private String jsonString;
	private String line;

	public JSONParser(HashSet<Person> people, BufferedReader reader, Logger LOGGER) {
		super(people, reader, LOGGER);
		this.gson = new GsonBuilder().create();
		this.jsonString = "";
	}
	
	@Override
	public void resetReader(File file) throws FileNotFoundException {
		this.reader = new BufferedReader(new FileReader(file));
		this.jsonString = "";
	}
	
	@Override
	public LinkedList<Record> parseRecords() throws IOException, NumberFormatException, BadDateException {
		while ((this.line = this.reader.readLine()) != null) {
			this.jsonString += this.line;
		}
		Response[] responses = gson.fromJson(jsonString, Response[].class);
		for (Response r : responses) {
			this.records.add(new Record(r.getDate(), r.getFrom(), r.getTo(), r.getNarrative(), r.getAmount(), this.people));
		}
		
		return this.records;
	}

	@Override
	public HashSet<Person> parsePeople() throws IOException {
		while ((this.line = this.reader.readLine()) != null) {
			this.jsonString += this.line;
		}
		Response[] responses = gson.fromJson(jsonString, Response[].class);
		for (Response r : responses) {
			this.people.add(new Person(r.getFrom()));
			this.people.add(new Person(r.getTo()));
		}
		
		return this.people;
	}

}
