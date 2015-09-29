package com.scr.dao.impl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.scr.vo.BookVO;

public class BooksDAOImplTest {
	private BooksDAOImpl booksDAOObj = null;
	@BeforeClass
	public void beforeClass(){
		booksDAOObj = new BooksDAOImpl();
	}
    
	// Test case for getBooksList
	@Test (enabled=false)
	  public void getBooksList_Test() throws SQLException, IOException {
		  List<BookVO> bookslist = booksDAOObj.getBooksList();
		  
		  for(BookVO bookVO : bookslist){
			  System.out.println("book name : " +bookVO.getBookName());
		  }
	  }
	
	// Test case for addBook
	@DataProvider(name="Book_names")
	  public Object[][] addBook_dp() {
		
		return new Object[][] {	
	      new Object[] {new BookVO("Hive"),"success"},
	      new Object[] {new BookVO("TestNG Tutorial"),"success"}
	    };
	  }

	  @Test(dataProvider="Book_names", enabled=false)
	  public void addBook_Test(BookVO bookVO, String expectedStatusMessage) throws SQLException {
		 
		  String actualStatusMessage = "";
		  actualStatusMessage = booksDAOObj.addBook(bookVO);
		  Assert.assertEquals(actualStatusMessage, expectedStatusMessage);
	  }
	  
	  // Test case for updateBookName
	 @DataProvider(name="Update_Book_names")
	  public Object[][] updateBook_dp() {
	    return new Object[][] {
	      new Object[] { new BookVO(8,"ESP by Prof.Nouri"), "success"},
	      new Object[] { new BookVO(10,"jQuery Tutorial"),"success"},
	    };
	  } 

	  @Test(dataProvider="Update_Book_names", enabled=false)
	  public void updateBookName_Test(BookVO bookVO,String expectedStatusMessage) throws SQLException, IOException {
		 
		  String actualStatusMessage = booksDAOObj.updateBookName(bookVO);
		  Assert.assertEquals(actualStatusMessage, expectedStatusMessage);
	  }
	  
	 // Test case for deleteBook
	  @DataProvider(name="Delete_Book")
	  public Object[][] deleteBook_dp() {
	    return new Object[][] {
	      new Object[] { new BookVO("jQuery Tutorial"), "success"},	     
	    };
	  }

	  @Test(dataProvider="Delete_Book", enabled=false)
	  public void deleteBook_Test(BookVO bookVO, String expectedStatusMessage) throws Exception {
		 
		  String actualStatusMessage = booksDAOObj.deleteBook(bookVO);
		  Assert.assertEquals(actualStatusMessage, expectedStatusMessage);
	  }

}
