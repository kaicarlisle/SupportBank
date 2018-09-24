package training.supportbank;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class XMLParser extends FileParser {
	
	private File file;
	private DocumentBuilderFactory builderFactory;
	private DocumentBuilder builder;
	private Document document;
	Element rootElement;

	public XMLParser(HashSet<Person> people, BufferedReader reader, Logger LOGGER, File file) {
		super(people, reader, LOGGER);
		this.builderFactory = DocumentBuilderFactory.newInstance();
		this.builder = null;
		this.file = file;
	}

	@Override
	public LinkedList<Record> parseRecords() throws IOException {
		return null;
	}

	@Override
	public HashSet<Person> parsePeople() throws IOException {
		try {
		    builder = builderFactory.newDocumentBuilder();
		    this.document = builder.parse(this.file);
		    
		    this.rootElement = this.document.getDocumentElement();
		    System.out.println(this.rootElement.getFirstChild().getNodeValue());
		} catch (ParserConfigurationException e) {
		    e.printStackTrace();  
		} catch (SAXException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void resetReader(File file) throws FileNotFoundException {
	}

}
