/**
 * 
 */
package com.scr.vo;

/**
 * @author pavanibaradi
 *
 */
public class BookVO {
	private int bookID;
	private String bookName;
	/**
	 * @param bookName
	 */
	public BookVO(String bookName) {
		super();
		this.bookName = bookName;
	}
	public BookVO(int bookID, String bookName) {
		this.bookID = bookID;
		this.bookName = bookName;
	}
	/**
	 * @return the bookID
	 */
	public int getBookID() {
		return bookID;
	}
	/**
	 * @param bookID the bookID to set
	 */
	public void setBookID(int bookID) {
		this.bookID = bookID;
	}
	/**
	 * @return the bookName
	 */
	public String getBookName() {
		return bookName;
	}
	/**
	 * @param bookName the bookName to set
	 */
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

}
