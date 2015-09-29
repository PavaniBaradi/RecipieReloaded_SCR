package com.scr.dao.impl;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.scr.util.Constants;
import com.scr.vo.CoursesVO;
import com.scr.vo.StudentVO;

public class StudentDAOImplTest {
	private StudentDAOImpl studentDAOObj;


	@BeforeClass
	public void beforeClass() {
		studentDAOObj = new StudentDAOImpl();
	}


	@Test(dataProvider="createStudent_dp", enabled=false)
	public void createStudent(String firstName, String lastName, String emailId, String password,  String expectedMessage) {
		StudentVO studentVO = new StudentVO(0, firstName, lastName, emailId, password,Constants.USER_STUDENT);
		String actualMessage = studentDAOObj.createStudent(studentVO);
		Assert.assertEquals(actualMessage, expectedMessage);
	}

	@DataProvider
	public Object[][] createStudent_dp() {

		return new Object[][] {
			new Object[] { "Harika", "M" , "mharika@gmail.com", "mharika", Constants.FAILURE},
			new Object[] { "vidya", "ks" , "vks@gmail.com", "aasdh", Constants.FAILURE},	      
			new Object[] { "Pavani", "Baradi" , "pbaradi@gmail.com", "passwordBaradi1", Constants.FAILURE}
		};
	}

	@Test(dataProvider="deleteStudent_dp",enabled=false)
	public void deleteStudent(String emailId, String expectedMessage) {
		StudentVO studentVO = new StudentVO(0, null, null, emailId, null,null);
		String actualMessage = studentDAOObj.deleteStudent(studentVO);
		Assert.assertEquals(actualMessage, expectedMessage);
	}

	@DataProvider
	public Object[][] deleteStudent_dp() {

		return new Object[][] {
			new Object[] {"vks@gmail.com",Constants.SUCCESS},
			new Object[] {"nn@gmail.com",Constants.SUCCESS},
			new Object[] {"stomar@gmail.com",Constants.SUCCESS}
		};
	}

	@Test(dataProvider="dropCourse_dp",enabled=false)
	public void dropCourse(String emailId, int courseId, int scheduleId, String expectedMessage) {
		String actualMessage = studentDAOObj.dropCourse(emailId, courseId, scheduleId);
		Assert.assertEquals(actualMessage, expectedMessage);
	}

	@DataProvider
	public Object[][] dropCourse_dp() {

		return new Object[][] {
			new Object[] {"vks@gmail.com",1,1,Constants.SUCCESS},
			new Object[] {"nn@gmail.com",1,1,Constants.SUCCESS},
			new Object[] {"stomar@gmail.com",2,1,Constants.SUCCESS}
		};
	}

	@Test(dataProvider="enrollCourse_dp", enabled=false)
	public void enrollCourse(String emailId, int courseId, int scheduleId, String expectedMessage) {
		String actualMessage = studentDAOObj.enrollCourse(emailId, courseId, scheduleId);
		Assert.assertEquals(actualMessage, expectedMessage);
	}

	@DataProvider
	public Object[][] enrollCourse_dp() {

		return new Object[][] {
			new Object[] {"vks@gmail.com",1,1,Constants.SUCCESS},
			new Object[] {"nn@gmail.com",1,1,Constants.SUCCESS},
			new Object[] {"stomar@gmail.com",2,1,Constants.SUCCESS}
		};
	}


	@Test(dataProvider="getStudentDetails_dp", enabled=false)
	public void getStudentDetails(String email) {
		StudentVO studentVO = studentDAOObj.getStudentDetails(email);
		if(studentVO!=null)
			System.out.println("Student Details "+studentVO.toString());
	}

	@DataProvider
	public Object[][] getStudentDetails_dp() {

		return new Object[][] {
			new Object[] {"vks@gmail.com"},
			new Object[] {"nn@gmail.com"},
			new Object[] {"stomar@gmail.com"}
		};
	}

	@Test(dataProvider="getStudentEnrolledCourses_dp",enabled=false)
	public void getStudentEnrolledCourses(String emailId) {

		StudentVO studentVO = studentDAOObj.getStudentEnrolledCourses(emailId);
		if(studentVO!=null){
			System.out.println("Student Details "+studentVO.toString());
			List<CoursesVO> courseList = studentVO.getCourseList();
			for (CoursesVO coursesVO : courseList) {
				System.out.println("Course Details "+coursesVO.toString());
			}
		}
	}

	@DataProvider
	public Object[][] getStudentEnrolledCourses_dp() {

		return new Object[][] {
			new Object[] {"vks@gmail.com"},
			new Object[] {"nn@gmail.com"},
			new Object[] {"stomar@gmail.com"}
		};
	}

	@Test(enabled=true)
	public void getStudents() {
		List<StudentVO> studentList = studentDAOObj.getStudents();
		for (StudentVO studentVO : studentList) {
			System.out.println("Student Details "+studentVO.toString());
		}
	}

	@Test(dataProvider="updateStudent_dp",enabled=false)
	public void updateStudent(int studentID, String firstName, String lastName, String emailId, String password, String expectedMessage) {
		StudentVO studentVO = new StudentVO(studentID, firstName, lastName, emailId, password, null);
		String actualMessage = studentDAOObj.updateStudent(studentVO);
		Assert.assertEquals(actualMessage, expectedMessage);
	}

	@DataProvider
	public Object[][] updateStudent_dp() {

		return new Object[][] {
			new Object[] { 1,"Harika", "M" , "mharika@gmail.com", "mharika", Constants.SUCCESS},
			new Object[] { 2,"vidya", "ks" , "vks@gmail.com", "aasdh", Constants.SUCCESS},	      
			new Object[] { 3,"Pavani", "Baradi" , "pbaradi@gmail.com", "passwordBaradi1", Constants.SUCCESS}
		};
	}

	@Test(dataProvider="login_dp",enabled=false)
	public void login(String email, String password){
		StudentVO studentVO = studentDAOObj.login(email, password);
		if(studentVO!=null){
			System.out.println("Login successful. Student Details are "+studentVO.toString());
		}
	}
	
	@DataProvider
	public Object[][] login_dp() {

		return new Object[][] {
			new Object[] { "mharika@gmail.com", "mharika", Constants.SUCCESS},
			new Object[] { "vks@gmail.com", "aasdh", Constants.SUCCESS},	      
			new Object[] { "pbaradi@gmail.com", "passwordBaradi1", Constants.SUCCESS}
		};
	}

}
