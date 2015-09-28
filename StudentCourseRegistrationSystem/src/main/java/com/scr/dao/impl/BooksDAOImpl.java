package com.scr.dao.impl;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.scr.util.DBConnectionManager;

public class BooksDAOImpl {
	/**
	 * This method adds book details to BOOKS table
	 * 
	 * @param bookName - bookName gives the name of the book
	 * @return - returns status message
	 * @throws SQLException 
	 * @throws IOException 
	 */
	public String addBook(String bookName) throws SQLException, IOException{

		Connection conn = null;
		PreparedStatement pstmt = null;
		Properties prop = new Properties();
    	InputStream inputStream= getClass().getClassLoader().getResourceAsStream("dbqueries.properties");
    	if (inputStream != null) {
			prop.load(inputStream);
    	}
		String statusMessage = "Error occurred while adding the book";
		try {
			// Connection to the database
			conn = DBConnectionManager.getConnection();
			// Setting auto commit to false to avoid committing of data immediately after executing
			conn.setAutoCommit(false);
			boolean bookExists = checkbookExists(conn, bookName);
			if (bookExists)
				throw new Exception("Book " + bookName + " already exists");

			pstmt = conn.prepareStatement(prop.getProperty("add.book"));
			pstmt.setString(1, bookName);
			pstmt.executeUpdate();
			statusMessage = "Successfully added the book";
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			statusMessage = e.getMessage();
		} finally {
			    conn.setAutoCommit(true);
				DBConnectionManager.close(conn, pstmt, null);
		}
		return statusMessage;
	}
	
	/**
	 * This method checks if book already exists or not
	 * @param conn
	 * @param bookName
	 * @return boolean value
	 * @throws SQLException 
	 * @throws IOException 
	 */
	private boolean checkbookExists(Connection conn, String bookName) throws SQLException, IOException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Properties prop = new Properties();
    	InputStream inputStream= getClass().getClassLoader().getResourceAsStream("dbqueries.properties");
    	if (inputStream != null) {
			prop.load(inputStream);
    	}
		boolean bookExists = false;
		int count = 0;
		try {
			// Setting auto commit to false to avoid committing of data immediately after executing
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(prop.getProperty("check.book.name"));
			pstmt.setString(1, bookName);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				count = rs.getInt(1);
			}

			System.out.println("count = " + count);
			if (count > 0) {
				bookExists = true;
			}
		} catch (Exception exp) {
			System.out.println("Error : " + exp);
		} finally {
			conn.setAutoCommit(true);
			DBConnectionManager.close(null, pstmt, rs);
		}
		System.out.println("bookExists ===== > " + bookExists);
		return bookExists;

	}
	
	/**
	 * This method lists all the books 
	 * @return - returns books list
	 * @throws SQLException 
	 * @throws IOException 
	 */
	public List<String> getBooksList() throws SQLException, IOException {
		String book_name = null;
        Connection conn = null;
    	Statement stmt = null;
		ResultSet rs = null;
		Properties prop = new Properties();
    	InputStream inputStream= getClass().getClassLoader().getResourceAsStream("dbqueries.properties");
    	if (inputStream != null) {
			prop.load(inputStream);
    	}
		 List<String> books_list = new ArrayList<String>();
		try{
			// Connection to the database
			conn = DBConnectionManager.getConnection();
			// Setting auto commit to false to avoid committing of data immediately after executing
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(prop.getProperty("fetch.books.list"));
			while(rs.next())
			{
				book_name = rs.getString("book_name");
							
				//Insertion
				books_list.add(book_name);
				
				//Printing
				System.out.println("The list of books:" +books_list);
	 			}
		 } catch(Exception e){
			 System.out.println("Error occured while getting the details" +e.getMessage());
		 }
		 finally {
			 conn.setAutoCommit(true);
			 DBConnectionManager.close(conn, stmt, rs);
		 }
		return books_list;
	}
	
	/**
	 * This method updates the book name
	 * @param book_name
	 * @param book_id
	 * @return statusMessage
	 * @throws SQLException 
	 * @throws IOException 
	 */
	public String updateBookName(String book_name,int book_id) throws SQLException, IOException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String statusMessage="";
		Properties prop = new Properties();
    	InputStream inputStream= getClass().getClassLoader().getResourceAsStream("dbqueries.properties");
    	if (inputStream != null) {
			prop.load(inputStream);
    	}
		try {
			// Connection to the database
			conn = DBConnectionManager.getConnection();
			// Setting auto commit to false to avoid committing of data immediately after executing
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(prop.getProperty("update.book.name"));

			pstmt.setString(1, book_name);
			pstmt.setInt(2, book_id );
			pstmt.executeUpdate();
            statusMessage = "Successfully updated the book name";

		} catch (Exception exp) {
			statusMessage = exp.getMessage();
		} finally {
			conn.setAutoCommit(true);
			DBConnectionManager.close(conn, pstmt, null);
		}
		return statusMessage;
	}
	
	/**
	 * This method deletes the book
	 * @param bookName - bookName gives the name of the book
	 * @return - statusMessage and deletes the book
	 * @throws - Exception
	 */
	public String deleteBook(String book_name) throws Exception {
		Connection conn = null;
    	PreparedStatement pstmt = null;
    	String statusMessage = "";
    	Properties prop = new Properties();
    	InputStream inputStream= getClass().getClassLoader().getResourceAsStream("dbqueries.properties");
    	if (inputStream != null) {
			prop.load(inputStream);
    	}
		 try{
			    // Connection to the database
				conn = DBConnectionManager.getConnection();
				// Setting auto commit to false to avoid committing of data immediately after executing
				conn.setAutoCommit(false);
				
				pstmt = conn.prepareStatement(prop.getProperty("delete.book"));
				pstmt.setString(1, book_name);
				
				// Executing the delete operation
				pstmt.executeUpdate();
				statusMessage = "Book successfully deleted";
		 } catch (SQLException e) {
			 statusMessage = e.getMessage();
		 } finally {
			    conn.setAutoCommit(true);
				DBConnectionManager.close(conn, pstmt, null);
		}
		return statusMessage;
	}
   
}


