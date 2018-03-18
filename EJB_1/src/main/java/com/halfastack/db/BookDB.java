package com.halfastack.db;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.ejb.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class BookDB {
	
	volatile Map<String, String> bookList;

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	public BookDB() {
		bookList = new ConcurrentHashMap<String, String>();
		
		bookList.put("Jack Kerouac", "On The Road");
		bookList.put("John Steinback", "Of Mice and Men");
		
		logger.info("Finished DB initialization, num of books available: "+ bookList.size());
	}

	
	public String findByAuthor(String name) {
		String result = "Not available";
		
		if(bookList.containsKey(name)) {
			result = "Book by author: "+ name + " titled: "+ bookList.get(name) + " is available.";
		}
		
		return result;
	}
	
	public String findByTitle(String name) {
		String result = "Not available";
		
		if(bookList.containsValue(name)) {
			result = "Book titled: "+ name + " is available.";
		}
		return result;
	}
}
