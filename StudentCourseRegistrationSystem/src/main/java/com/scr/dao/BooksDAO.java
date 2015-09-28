package com.scr.dao;

import java.sql.SQLException;
import java.util.List;

public interface BooksDAO {
	/**
	 * This method adds book details to BOOKS table
	 * 
	 * @param bookName - bookName gives the name of the book
	 * @return - returns status message
	 * @throws SQLException 
	 */
	public String addBook(String bookName) throws SQLException;
	/**
	 * This method lists all the books 
	 * @return - returns books list
	 * @throws SQLException 
	 */
	public List<String> getBooksList() throws SQLException;
	/**
	 * This method updates the book name
	 * @param book_name
	 * @param book_id
	 * @return statusMessage
	 * @throws SQLException 
	 */
	public String updateBookName(String book_name,int book_id) throws SQLException;
	/**
	 * This method deletes the book
	 * @param bookName - bookName gives the name of the book
	 * @return - statusMessage and deletes the book
	 * @throws - Exception
	 */
	public String deleteBook(String book_name) throws Exception;
	
}
