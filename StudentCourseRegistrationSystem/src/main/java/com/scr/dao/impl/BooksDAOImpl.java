package com.scr.dao.impl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.scr.dao.BooksDAO;
import com.scr.util.Constants;
import com.scr.util.DBConnectionManager;
import com.scr.util.PropertyLoader;
import com.scr.vo.BookVO;

public class BooksDAOImpl implements BooksDAO{

	private Properties dbQueries = PropertyLoader.getDbProperties();

	/**
	 * This method adds book details to BOOKS table
	 * 
	 * @param bookName - bookName gives the name of the book
	 * @return - returns status message
	 * @throws SQLException 
	 */
	public String addBook(BookVO bookVO){

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String bookName = bookVO.getBookName();

		String statusMessage = "Error occurred while adding the book";
		try {
			// Connection to the database
			connection = DBConnectionManager.getConnection();
			// Setting auto commit to false to avoid committing of data immediately after executing
			connection.setAutoCommit(false);
			boolean bookExists = checkbookExists(connection, bookName);
			if (bookExists)
				throw new Exception("Book " + bookName + " already exists");

			preparedStatement = connection.prepareStatement(dbQueries.getProperty("add.book"));
			preparedStatement.setString(1, bookName);
			preparedStatement.executeUpdate();
			connection.commit();
			connection.setAutoCommit(true);
			statusMessage = Constants.SUCCESS;
		} catch (SQLException e) {
			statusMessage = Constants.FAILURE;
			e.printStackTrace();
		} catch (Exception e) {
			statusMessage = Constants.FAILURE;
			e.printStackTrace();
			statusMessage = e.getMessage();
		} finally {
			DBConnectionManager.close(connection, preparedStatement, null);
		}
		return statusMessage;
	}

	/**
	 * This method checks if book already exists or not
	 * @param connection
	 * @param bookName
	 * @return boolean value
	 * @throws SQLException 
	 * @throws IOException 
	 */
	private boolean checkbookExists(Connection connection, String bookName) {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		boolean bookExists = false;
		int count = 0;
		try {
			// Setting auto commit to false to avoid committing of data immediately after executing
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(dbQueries.getProperty("check.book.name"));
			preparedStatement.setString(1, bookName);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				count = resultSet.getInt(1);
			}

			System.out.println("count = " + count);
			if (count > 0) {
				bookExists = true;
			}
			connection.setAutoCommit(true);
		} catch (Exception exp) {
			System.out.println("Error : " + exp);
		} finally {
			DBConnectionManager.close(null, preparedStatement, resultSet);
		}
		System.out.println("bookExists ===== > " + bookExists);
		return bookExists;

	}

	/**
	 * This method lists all the books 
	 * @return - returns books list
	 * @throws SQLException 
	 */
	public List<BookVO> getBooksList() {
		String bookName = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		List<BookVO> booksList = new ArrayList<BookVO>();
		try{
			// Connection to the database
			connection = DBConnectionManager.getConnection();
			// Setting auto commit to false to avoid committing of data immediately after executing
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(dbQueries.getProperty("fetch.books.list"));
			resultSet = preparedStatement.executeQuery();

			while(resultSet.next())
			{
				bookName = resultSet.getString(Constants.BOOK_NAME);		
				//Insertion
				booksList.add(new BookVO(bookName));
			}
			//Printing
			System.out.println("The list of books:" +booksList);
			connection.setAutoCommit(true);
		} catch(Exception e){
			System.out.println("Error occured while getting the details" +e.getMessage());
		}
		finally {
			DBConnectionManager.close(connection, preparedStatement, resultSet);
		}
		return booksList;
	}

	/**
	 * This method updates the book name
	 * @param book_name
	 * @param book_id
	 * @return statusMessage
	 * @throws SQLException 
	 * @throws IOException 
	 */
	public String updateBookName(BookVO bookVO) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String statusMessage=null;

		try {
			// Connection to the database
			connection = DBConnectionManager.getConnection();
			// Setting auto commit to false to avoid committing of data immediately after executing
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(dbQueries.getProperty("update.book.name"));

			preparedStatement.setString(1, bookVO.getBookName());
			preparedStatement.setInt(2, bookVO.getBookID() );
			preparedStatement.executeUpdate();
			statusMessage = Constants.SUCCESS;
			connection.commit();
			connection.setAutoCommit(true);
		} catch (Exception exp) {
			statusMessage = Constants.FAILURE;
		} finally {
			DBConnectionManager.close(connection, preparedStatement, null);
		}
		return statusMessage;
	}

	/**
	 * This method deletes the book
	 * @param bookName - bookName gives the name of the book
	 * @return - statusMessage and deletes the book
	 * @throws - Exception
	 */
	public String deleteBook(BookVO bookVO) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String statusMessage = null;
		try{
			// Connection to the database
			connection = DBConnectionManager.getConnection();
			// Setting auto commit to false to avoid committing of data immediately after executing
			connection.setAutoCommit(false);

			preparedStatement = connection.prepareStatement(dbQueries.getProperty("delete.book"));
			preparedStatement.setString(1, bookVO.getBookName());

			// Executing the delete operation
			preparedStatement.executeUpdate();
			connection.commit();
			connection.setAutoCommit(true);
			statusMessage = Constants.SUCCESS;
		} catch (SQLException e) {
			statusMessage = Constants.FAILURE;
		} catch (Exception e) {
			statusMessage = Constants.FAILURE;
		} finally {
			DBConnectionManager.close(connection, preparedStatement, null);
		}
		return statusMessage;
	}

}


