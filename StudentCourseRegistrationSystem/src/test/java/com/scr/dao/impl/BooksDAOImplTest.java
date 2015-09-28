package com.scr.dao.impl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.scr.dao.impl.BooksDAOImpl;

public class BooksDAOImplTest {
	private BooksDAOImpl booksDAOObj = null;
	@BeforeClass
	public void beforeClass(){
		booksDAOObj = new BooksDAOImpl();
	}
    
	// Test case for getBooksList
	@Test (enabled=false)
	  public void getBooksList_Test() throws SQLException, IOException {
		  List<String> bookslist = booksDAOObj.getBooksList();
		  
		  for(String bookVO : bookslist){
			  System.out.println("book name : " +bookVO);
		  }
	  }
	
	// Test case for addBook
	@DataProvider(name="Book_names")
	  public Object[][] addBook_dp() {
	    return new Object[][] {
	      new Object[] { "ESP by Rakesh Ranjan", "Successfully added the book"},
	      new Object[] { "jQuery Tutorial", "Successfully added the book"},
	      new Object[] { "PERL Tutorial", "Successfully added the book"},
	      new Object[] { "Java Tutorial", "Successfully added the book"},
	     
	    };
	  }

	  @Test(dataProvider="Book_names", enabled=false)
	  public void addBook_Test(String bookName, String expectedStatusMessage) throws SQLException, IOException {
		 
		  String actualStatusMessage = booksDAOObj.addBook(bookName);
		  Assert.assertEquals(actualStatusMessage, expectedStatusMessage);
	  }
	  
	  // Test case for updateBookName
	  @DataProvider(name="Update_Book_names")
	  public Object[][] updateBook_dp() {
	    return new Object[][] {
	      new Object[] { "ESP by Prof.Ahmad Nouri",8, "Successfully updated the book name"},
	      new Object[] { "jQuery",9,"Successfully updated the book name"},
	    };
	  } 
	  @Test(dataProvider="Update_Book_names", enabled=false)
	  public void updateBookName_Test(String bookName,int book_id,String expectedStatusMessage) throws SQLException, IOException {
		 
		  String actualStatusMessage = booksDAOObj.updateBookName(bookName,book_id);
		  Assert.assertEquals(actualStatusMessage, expectedStatusMessage);
	  }
	  
	 // Test case for deleteBook
	  @DataProvider(name="Delete_Book")
	  public Object[][] deleteBook_dp() {
	    return new Object[][] {
	      new Object[] { "jQuery", "Book successfully deleted"},	     
	    };
	  }

	  @Test(dataProvider="Delete_Book", enabled=true)
	  public void deleteBook_Test(String bookName, String expectedStatusMessage) throws Exception {
		 
		  String actualStatusMessage = booksDAOObj.deleteBook(bookName);
		  Assert.assertEquals(actualStatusMessage, expectedStatusMessage);
	  }
	  
}
