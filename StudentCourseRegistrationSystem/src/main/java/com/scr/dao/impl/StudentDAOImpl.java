/**
 * 
 */
package com.scr.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.scr.dao.StudentsDAO;
import com.scr.util.Constants;
import com.scr.util.DBConnectionManager;
import com.scr.util.PropertyLoader;
import com.scr.vo.CoursesVO;
import com.scr.vo.ScheduleVO;
import com.scr.vo.StudentVO;


public class StudentDAOImpl implements StudentsDAO{
	private Properties dbQueries = PropertyLoader.getDbProperties();
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	private Connection connection = null;

	/**
	 * This method adds student details to STUDENT table
	 * 
	 * @param studentVO
	 *            - studentVO contains student details which needs to be
	 *            persisted
	 * @return statusMessage specifying success or failure
	 */
	@Override
	public String createStudent(StudentVO studentVO) {
		int index = 0;
		String statusMessage = null;
		try {
			//get connection to DB
			connection = DBConnectionManager.getConnection();
			connection.setAutoCommit(false);
			boolean studentExists = checkStudentExists(connection, studentVO.getEmailId());

			//Check if student already exists. If student exists throw exception saying student already exists
			if (studentExists)
				throw new Exception("Student with emailId: " + studentVO.getEmailId() + " already exists");

			//Prepare insert statement to insert student details using preparedStatement
			preparedStatement = connection.prepareStatement(dbQueries.getProperty("add.student"));

			//set Student values
			preparedStatement.setString(++index, studentVO.getFirstName());
			preparedStatement.setString(++index, studentVO.getLastName());
			preparedStatement.setString(++index, studentVO.getEmailId());
			preparedStatement.setString(++index, studentVO.getPassword());
			preparedStatement.setString(++index, studentVO.getUserFlag());
			System.out.println("Inserting student using DDL statement "+preparedStatement.toString());

			//execute the insert statement
			preparedStatement.executeUpdate();
			connection.commit();
			connection.setAutoCommit(true);
			statusMessage = Constants.SUCCESS;
		} catch (SQLException e) {
			e.printStackTrace();
			statusMessage = Constants.FAILURE;
			try {
				connection.rollback();
			} catch (SQLException e1) {
				System.out.println(statusMessage);
			}

		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				System.out.println(statusMessage);
			}
		} finally {
			DBConnectionManager.close(connection, preparedStatement, null);
		}
		System.out.println("statusMessage " + statusMessage);
		return statusMessage;
	}



	/**
	 * This method checks if student already exists or not
	 * 
	 * @param connection
	 *            - connection to create prepared statement
	 * @param emailId
	 *            - to check the if student with emailid already exists
	 * @return true if student exists else false
	 */
	private boolean checkStudentExists(Connection connection, String emailId) {
		boolean studentExists = false;
		int count = 0;
		try {
			//prepare query to check if student exists
			preparedStatement = connection.prepareStatement(dbQueries.getProperty("check.student"));
			preparedStatement.setString(1, emailId);

			System.out.println("Checking if student existis or not using query "+preparedStatement.toString());
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				count = resultSet.getInt(1);
			}

			if (count > 0) {
				studentExists = true;
				System.out.println("Student with emailId "+emailId +"exists");
			}
			System.out.println("Student with emailId "+emailId +"doesnot exist");
		} catch (Exception exp) {
			System.out.println("Error occured while checking if student exists or not " + exp);
		} finally {
			DBConnectionManager.close(null, preparedStatement, resultSet);
		}
		System.out.println("studentExists " + studentExists);
		return studentExists;
	}


	/**
	 * This method updates student 
	 * @param studentVO Object contains student details that has to be updated
	 * @return status message stating success or failure
	 */
	@Override
	public String updateStudent(StudentVO studentVO) {
		int index = 0;
		String statusMessage = null;

		try {
			//get connection to DB
			connection = DBConnectionManager.getConnection();
			connection.setAutoCommit(false);

			//Prepare insert statement to insert student details using preparedStatement
			preparedStatement = connection.prepareStatement(dbQueries.getProperty("update.student"));

			//set Student values
			preparedStatement.setString(++index, studentVO.getFirstName());
			preparedStatement.setString(++index, studentVO.getLastName());
			preparedStatement.setString(++index, studentVO.getEmailId());
			preparedStatement.setString(++index, studentVO.getPassword());
			preparedStatement.setInt(++index, studentVO.getStudentId());
			System.out.println("Updating student using DDL statement "+preparedStatement.toString());

			//execute the insert statement
			preparedStatement.executeUpdate();
			connection.commit();
			connection.setAutoCommit(true);
			statusMessage = Constants.SUCCESS;
		} catch (SQLException e) {
			e.printStackTrace();
			statusMessage = Constants.FAILURE;
			try {
				connection.rollback();
			} catch (SQLException e1) {
				System.out.println(statusMessage);
			}

		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				System.out.println(statusMessage);
			}
		} finally {
			DBConnectionManager.close(connection, preparedStatement, null);
		}
		System.out.println("statusMessage =====> " + statusMessage);
		return statusMessage;

	}

	/**
	 * This method deletes student's record from STUDENTS table
	 * @param studentVO
	 * @return status message stating success or failure
	 */
	@Override
	public String deleteStudent(StudentVO studentVO) {
		String statusMessage = null;

		try {
			//get connection to DB
			connection = DBConnectionManager.getConnection();
			connection.setAutoCommit(false);

			//Prepare DDL statement to delete student details using preparedStatement
			preparedStatement = connection.prepareStatement(dbQueries.getProperty("delete.student"));

			//set emailid
			preparedStatement.setString(1, studentVO.getEmailId());
			System.out.println("Deleting student using DDL statement "+preparedStatement.toString());

			//execute the delete statement
			preparedStatement.executeUpdate();
			connection.commit();
			connection.setAutoCommit(true);
			statusMessage = Constants.SUCCESS;
		} catch (SQLException e) {
			e.printStackTrace();
			statusMessage = Constants.FAILURE;
			try {
				connection.rollback();
			} catch (SQLException e1) {
				System.out.println(statusMessage);
			}

		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				System.out.println(statusMessage);
			}
		} finally {
			DBConnectionManager.close(connection, preparedStatement, null);
		}
		System.out.println("statusMessage =====> " + statusMessage);
		return statusMessage;
	}

	/**
	 * This method returns all the students details
	 * @return list of students
	 */
	@Override
	public List<StudentVO> getStudents() {
		String statusMessage = null;
		List<StudentVO> studentsList = new ArrayList<StudentVO>();
		StudentVO studentVO = null;

		try {
			//get connection to DB
			connection = DBConnectionManager.getConnection();

			//Prepare DDL statement to delete student details using preparedStatement
			preparedStatement = connection.prepareStatement(dbQueries.getProperty("list.students"));
			System.out.println("Lising student details using query "+preparedStatement.toString());

			//execute the select query 
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				studentVO = new StudentVO(resultSet.getInt(Constants.STUDENT_ID), resultSet.getString(Constants.FIRST_NAME), resultSet.getString(Constants.LAST_NAME), resultSet.getString(Constants.EMAIL), resultSet.getString(Constants.PASSWORD), resultSet.getString(Constants.USER_FLAG));
				studentsList.add(studentVO);
			}
			statusMessage = Constants.SUCCESS;
		} catch (SQLException e) {
			e.printStackTrace();
			statusMessage = Constants.FAILURE;
		} catch (Exception e) {
			statusMessage = Constants.FAILURE;
		} finally {
			DBConnectionManager.close(connection, preparedStatement, null);
		}
		System.out.println("statusMessage =====> " + statusMessage);
		return studentsList;
	}

	/**
	 * This method a student's details
	 * @param email - student's email
	 * @return studentVO object 
	 */
	@Override
	public StudentVO getStudentDetails(String email) {
		StudentVO studentVO =null;
		try {
			connection = DBConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement(dbQueries.getProperty("list.student.details"));
			preparedStatement.setString(1, email);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				studentVO = new StudentVO(resultSet.getInt(Constants.STUDENT_ID), resultSet.getString(Constants.FIRST_NAME), resultSet.getString(Constants.LAST_NAME), resultSet.getString(Constants.EMAIL), resultSet.getString(Constants.PASSWORD), resultSet.getString(Constants.USER_FLAG));
			}

		} catch (Exception exp) {
			System.out.println("Error : " + exp);
		} finally {
			DBConnectionManager.close(null, preparedStatement, resultSet);
		}
		return studentVO;
	}


	/**
	 * This method lists course details which are enrolled by a student
	 * @param email students' email id whose enrolled courses should be listed
	 * @return studentVO contains student details and course details
	 */
	@Override
	public StudentVO getStudentEnrolledCourses(String emailId) {
		StudentVO studentVO = null;
		List<CoursesVO> courseList = null;
		try {
			studentVO = getStudentDetails(emailId);
			System.out.println("student Details " + studentVO);

			courseList = getCourseSchedule(connection, emailId);
			System.out.println("courseList " + courseList);
			studentVO.setCourseList(courseList);

		} catch (Exception exp) {
			System.out.println("Exception occurred in getStudentEnrolledCourses.." + exp.getMessage());
		} finally {
			DBConnectionManager.close(connection, null, null);
		}
		System.out.println(" enrollmentDetails Details " + studentVO);
		return studentVO;
	}

	/**
	 * This method fetches course schedule details
	 * @param connection
	 * @param emailId
	 * @return
	 */
	private List<CoursesVO> getCourseSchedule(Connection connection, String emailId) {
		List<CoursesVO> courseList = null;
		CoursesVO courseVO = null;
		int courseId = 0;
		String courseName = null;
		List<ScheduleVO> scheduleList = null;
		ScheduleVO scheduleVO = null;
		Date startDate;
		Date endDate;
		Time startTime;
		Time endTime;

		try{
			preparedStatement = connection.prepareStatement(dbQueries.getProperty("list.student.enrolled.courses"));
			preparedStatement.setString(1, emailId);
			resultSet = preparedStatement.executeQuery();
			courseList = new ArrayList<CoursesVO>();
			while (resultSet.next()) {
				courseName = resultSet.getString(Constants.COURSE_NAME);
				startDate = resultSet.getDate(Constants.START_DATE);
				endDate = resultSet.getDate(Constants.END_DATE);
				startTime = resultSet.getTime(Constants.START_TIME);
				endTime = resultSet.getTime(Constants.END_TIME);
				scheduleVO = new ScheduleVO(startDate, endDate, startTime, endTime);
				if(courseId == 0){
					scheduleList = new ArrayList<ScheduleVO>();
					courseId = resultSet.getInt(1);
					courseVO = new CoursesVO(courseId, courseName, 0, null,null,null,null);
					scheduleList.add(scheduleVO);
				}else if(resultSet.getInt(1) != courseId){
					//If course ID changes, add the schedule list of earlier course to courseVO
					//Add course VO to courseList
					courseVO.setScheduleList(scheduleList);
					courseList.add(courseVO);
					scheduleList = new ArrayList<ScheduleVO>();

					courseId = resultSet.getInt(Constants.COURSE_ID);
					courseVO = new CoursesVO(courseId, courseName, 0, null,null,null,null);
					scheduleList.add(scheduleVO);
				}else{
					scheduleList.add(scheduleVO);
				}
			}

		} catch (Exception exp) {
			DBConnectionManager.close(null, preparedStatement, resultSet);
		}
		return courseList;
	}


	/**
	 * This method checks if student has already enrolled for a course or not.
	 * If not student will be enrolled for a course
	 * 
	 * @param emailId
	 * @param courseId
	 * @param scheduleId
	 * @return status message
	 */
	@Override
	public String enrollCourse(String emailId, int courseId, int scheduleId) {
		String statusMessage = null;

		try {
			connection = DBConnectionManager.getConnection();
			connection.setAutoCommit(false);

			boolean alreadyEnrolled = checkCourseEnrolled(connection, emailId, courseId);
			if (alreadyEnrolled)
				throw new Exception("Student with emailId: " + emailId + " already enrolled for this course !!! ");

			preparedStatement = connection.prepareStatement(dbQueries.getProperty("student.enroll.course"));
			preparedStatement.setString(1, emailId);
			preparedStatement.setInt(2, courseId);
			preparedStatement.setInt(3, scheduleId);

			preparedStatement.executeUpdate();
			connection.commit();
			connection.setAutoCommit(true);
			statusMessage = Constants.SUCCESS;
		} catch (SQLException sqlexp) {
			sqlexp.printStackTrace();
			statusMessage = Constants.FAILURE;
		} catch (Exception e) {
			statusMessage = Constants.FAILURE;
		} finally {
			DBConnectionManager.close(connection, preparedStatement, null);
		}

		System.out.println("statusMessage  " + statusMessage);
		return statusMessage;
	}

	/**
	 * This method checks if this course is already enrolled by student or not
	 * 
	 * @param connection - connection object to start the transaction
	 * @param email - email id of student
	 * @param courseId -course id which to student will enroll
	 * @return true if student already enrolled else false 
	 */
	private boolean checkCourseEnrolled(Connection connection, String emailId, int courseId) {
		int count=0;
		boolean alreadyEnrolled = false;
		try{
			preparedStatement = connection.prepareStatement(dbQueries.getProperty("check.course.enrolled"));
			preparedStatement.setString(1, emailId);
			preparedStatement.setInt(2, courseId);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				count = resultSet.getInt(1);
			}

			System.out.println("count = " + count);
			if (count > 0) {
				alreadyEnrolled = true;
			}
		} catch (Exception exp) {
			System.out.println("Error while checking course is already enrolled or not " + exp);
		} finally {
			DBConnectionManager.close(null, preparedStatement, resultSet);
		}
		System.out.println("already enrolled " + alreadyEnrolled);
		return alreadyEnrolled;
	}


	/**
	 * This method drops course to which student enrolled earlier
	 * 
	 * @param email - email id of student
	 * @param courseId -course id which has to be dropped
	 * @param scheduleId - schedule id of course which has to be dropped
	 * @return status message stating success or failure
	 */
	@Override
	public String dropCourse(String emailId, int courseId, int scheduleId){
		String statusMessage = null;

		try {
			connection = DBConnectionManager.getConnection();
			connection.setAutoCommit(false);

			preparedStatement = connection.prepareStatement(dbQueries.getProperty("student.drop.course"));
			preparedStatement.setString(1, emailId);
			preparedStatement.setInt(2, courseId);
			preparedStatement.setInt(3, scheduleId);

			preparedStatement.executeUpdate();
			connection.commit();
			connection.setAutoCommit(true);
			statusMessage = Constants.SUCCESS;
		} catch (SQLException sqlexp) {
			sqlexp.printStackTrace();
			statusMessage = Constants.FAILURE;
		} catch (Exception e) {
			statusMessage = Constants.FAILURE;
		} finally {
			DBConnectionManager.close(connection, preparedStatement, null);
		}
		System.out.println("statusMessage  " + statusMessage);
		return statusMessage;
	}


	/**
	 * This method validates the user with email and password
	 * @param email - user's email
	 * @param password - user's password
	 * @return returns StudentVO object if student exists else returns null
	 */
	@Override
	public StudentVO login(String email, String password) {
		StudentVO studentVO =null;
		try {
			connection = DBConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement(dbQueries.getProperty("login"));
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, password);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				studentVO = new StudentVO(resultSet.getInt(Constants.STUDENT_ID), resultSet.getString(Constants.FIRST_NAME), resultSet.getString(Constants.LAST_NAME), resultSet.getString(Constants.EMAIL), resultSet.getString(Constants.PASSWORD),resultSet.getString(Constants.USER_FLAG));
			}

		} catch (Exception exp) {
			System.out.println("Error : " + exp);
		} finally {
			DBConnectionManager.close(null, preparedStatement, resultSet);
		}
		return studentVO;
	}

}
