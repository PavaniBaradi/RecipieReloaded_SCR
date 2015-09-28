/**
 * 
 */
package com.scr.vo;

import java.util.List;

public class StudentVO {
	private int studentId;
	private String firstName ;
	private String lastName ;
	private String emailId ;
	private String password ;
	private List<CoursesVO> courseList;
	
	
	/**
	 * @param firstName
	 * @param lastName
	 * @param emailId
	 * @param password
	 */
	public StudentVO(int studentID, String firstName, String lastName, String emailId, String password) {
		super();
		this.studentId = studentID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailId = emailId;
		this.password = password;
	}
	/**
	 * @return the studentId
	 */
	public int getStudentId() {
		return studentId;
	}
	/**
	 * @param studentId the studentId to set
	 */
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	/**
	 * @return the emailId
	 */
	public String getEmailId() {
		return emailId;
	}
	/**
	 * @param emailId the emailId to set
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the courseList
	 */
	public List<CoursesVO> getCourseList() {
		return courseList;
	}
	/**
	 * @param courseList the courseList to set
	 */
	public void setCourseList(List<CoursesVO> courseList) {
		this.courseList = courseList;
	}
	
	public String toString(){
		return "Student Name: "+firstName +" "+lastName +" email: "+emailId;
	}
}
