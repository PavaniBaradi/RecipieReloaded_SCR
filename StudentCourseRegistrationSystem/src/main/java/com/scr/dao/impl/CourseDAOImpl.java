package com.scr.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.scr.dao.CourseDAO;
import com.scr.util.Constants;
import com.scr.util.DBConnectionManager;
import com.scr.util.PropertyLoader;
import com.scr.vo.BookVO;
import com.scr.vo.CoursesVO;
import com.scr.vo.CurriculumVO;
import com.scr.vo.ScheduleVO;

public class CourseDAOImpl implements CourseDAO {
	
	private Properties dbQueries = PropertyLoader.getDbProperties();
	
	/**
	 * Lists all courses which are enabled
	 * @return List<CourseInfoVO> 
	 */
	public List<CoursesVO> listAllCourse(){
		
		Connection connection = null;
	    PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<CoursesVO> coursesList =null;
		try {
			connection = DBConnectionManager.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(dbQueries.getProperty("course.list.all"));
			resultSet = preparedStatement.executeQuery();
			
			coursesList = new ArrayList<CoursesVO>();
			while(resultSet.next()){
				coursesList.add(new CoursesVO(resultSet.getInt(Constants.COURSE_ID), resultSet.getString(Constants.COURSE_NAME), 
						resultSet.getInt(Constants.COURSE_AMOUNT), resultSet.getString(Constants.COURSE_DESC)));
			}
			
		}catch(SQLException sqlExp){
			System.out.println("SQLException occurred in listAllCourse() " + sqlExp.getMessage());
		}catch(Exception exp){
			System.out.println("Exception occurred in listAllCourse() " + exp.getMessage());
		}finally{
			try {
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
			DBConnectionManager.close(connection, preparedStatement, resultSet);
		}
		return coursesList;
	}
	
	/**
	 * Gets details of the particular course.
	 * @param courseId
	 * @return Map<String, Object>
	 */
	public CoursesVO getCourseDetails(int courseId){
		
		Connection connection = null;
	    PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		CoursesVO courseVO = null;
		try {
			connection = DBConnectionManager.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(dbQueries.getProperty("course.get.info"));
			preparedStatement.setInt(1, courseId);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				courseVO = new CoursesVO(resultSet.getInt(Constants.COURSE_ID), resultSet.getString(Constants.COURSE_NAME), resultSet.getInt(Constants.COURSE_AMOUNT), resultSet.getString(Constants.COURSE_DESC));
			}
			List<ScheduleVO> scheduleList = new ScheduleDAOImpl().getCourseSchedule(courseId);  
			List<CurriculumVO> curriculumList = new CurriculumDAOImpl().getCourseCurriculum(courseId); // may want to use CurriculumVO
			List<BookVO> booksList = new BooksDAOImpl().getCourseBooks(courseId);  // may want to use BookVO
			
			courseVO.setBooksList(booksList);
			courseVO.setCurriculumList(curriculumList);
			courseVO.setScheduleList(scheduleList);
		}catch(SQLException sqlExp){
			System.out.println("SQLException occurred in getCourseDetails() " + sqlExp.getMessage());
		}catch (Exception e) {
			System.out.println("Exception occurred in getCourseDetails() "+e.getMessage());
		}finally{
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			DBConnectionManager.close(connection, preparedStatement, resultSet);
		}
		return courseVO;
	}
	
	/**
	 * Gets the Course Information
	 * @param connection
	 * @param courseId
	 * @return CourseInfoVO
	 */
//	private CourseInfoVO getCourseInfo(Connection connection, int courseId) {
//		PreparedStatement preparedStatement = null;
//		ResultSet resultSet = null;
//		CourseInfoVO courseInfo = null;
//		
//		try{
//			preparedStatement = connection.prepareStatement(dbQueries.getProperty("course.get.info"));
//			preparedStatement.setInt(1, courseId);
//			resultSet = preparedStatement.executeQuery();
//			while(resultSet.next()){
//				courseInfo = new CourseInfoVO(resultSet.getInt("course_id"), resultSet.getString("course_name"), resultSet.getInt("amount"), resultSet.getString("course_description"));
//			}
//			
//		}catch(SQLException sqlExp){
//			System.out.println("SQLException occurred in getCourseInfo() " + sqlExp.getMessage());
//		}catch(Exception exp){
//			System.out.println("Exception occurred in getCourseInfo() "+exp.getMessage());
//		}finally{
//			DBConnectionManager.close(null, preparedStatement, resultSet);
//		}
//		return courseInfo;
//	}
	
	/**
	 * Gets the course schedule
	 * @param connection
	 * @param courseId
	 * @return List<ScheduleVO>
	 */
//	private List<ScheduleVO> getCourseSchedule(Connection connection, int courseId) {
//		PreparedStatement preparedStatement = null;
//		ResultSet resultSet = null;
//		List<ScheduleVO> courseSchedule = null;
//		
//		try{
//			preparedStatement = connection.prepareStatement(dbQueries.getProperty("course.get.schedule"));
//			preparedStatement.setInt(1, courseId);
//			resultSet = preparedStatement.executeQuery();
//			courseSchedule =new ArrayList<ScheduleVO>();
//			while(resultSet.next()){
//				courseSchedule.add(new ScheduleVO(resultSet.getDate(1), resultSet.getDate(2), resultSet.getTime(3), resultSet.getTime(4)));
//			}
//			
//		}catch(SQLException sqlExp){
//			System.out.println("SQLException occurred in getCourseSchedule() " + sqlExp.getMessage());
//		}catch(Exception exp){
//			System.out.println("Exception occurred in getCourseSchedule() "+exp.getMessage());
//		}finally{
//			DBConnectionManager.close(null, preparedStatement, resultSet);
//		}
//		return courseSchedule;
//	}
	
/**
 * Gets the Course Curriculum
 * @param connection
 * @param courseId
 * @return List<String>
 */
//	private List<String> getCourseCurriculum(Connection connection, int courseId) {
//		PreparedStatement preparedStatement = null;
//		ResultSet resultSet = null;
//		List<String> courseCurriculum = null;
//		
//		try{
//			preparedStatement = connection.prepareStatement(dbQueries.getProperty("course.get.curriculum"));
//			preparedStatement.setInt(1, courseId);
//			resultSet = preparedStatement.executeQuery();
//			courseCurriculum =new ArrayList<String>();
//			while(resultSet.next()){
//				courseCurriculum.add(resultSet.getString(1));
//			}
//			
//		}catch(SQLException sqlExp){
//			System.out.println("SQLException occurred in getCourseCurriculum() " + sqlExp.getMessage());
//		}catch(Exception exp){
//			System.out.println("Exception occurred in getCourseCurriculum() "+exp.getMessage());
//		}finally{
//			DBConnectionManager.close(null, preparedStatement, resultSet);
//		}
//		return courseCurriculum;
//	}
		
	/**
	 * Gets the Course Books
	 * @param connection
	 * @param courseId
	 * @return List<String>
	 */
//	private List<String> getCourseBooks(Connection connection, int courseId) {
//		PreparedStatement preparedStatement = null;
//		ResultSet resultSet = null;
//		List<String> courseBooks = null;
//		
//		try{
//			preparedStatement = connection.prepareStatement(dbQueries.getProperty("course.get.books"));
//			preparedStatement.setInt(1, courseId);
//			resultSet = preparedStatement.executeQuery();
//			courseBooks =new ArrayList<String>();
//			while(resultSet.next()){
//				courseBooks.add(resultSet.getString(1));
//			}
//			
//		}catch(SQLException sqlExp){
//			System.out.println("SQLException occurred in getCourseBooks() " + sqlExp.getMessage());
//		}catch(Exception exp){
//			System.out.println("Exception occurred in getCourseBooks() "+exp.getMessage());
//		}finally{
//			DBConnectionManager.close(null, preparedStatement, resultSet);
//		}
//		return courseBooks;
//	}

	/**
	 * Disables the course
	 * @param courseId
	 * @return String 
	 */
	public String disableCourse(int courseId){
		
		Connection connection = null;
	    PreparedStatement preparedStatement = null;
		StringBuffer sbuffStatus = new StringBuffer();
		
		try {
			connection = DBConnectionManager.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(dbQueries.getProperty("course.disable"));
			preparedStatement.setInt(1,courseId);
			
			int status = preparedStatement.executeUpdate();
			//Returns 1 if any rows updated, returns 0 if no rows updated
			if(status>0)
				sbuffStatus.append("successfully disabled the course : ").append(courseId);
			else if(status==0)
				sbuffStatus.append("No course with courseId : ").append(courseId).append(" available to disable");
			connection.commit();
		} catch(SQLException sqlExp){
			System.out.println("SQLException occurred in disableCourse() " + sqlExp.getMessage());
			try {
				connection.rollback();
			} catch (SQLException e1) {
				System.out.println(sbuffStatus.toString());
			}
		}catch (Exception e) {
			System.out.println("Exception occurred in getCourseInfo() "+e.getMessage());
			sbuffStatus.append("An error occurred while disabling the course");
			try {
				connection.rollback();
			} catch (SQLException e1) {
				System.out.println(sbuffStatus.toString());
			}
		}finally{
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			DBConnectionManager.close(connection, preparedStatement, null);
		}
		
		return sbuffStatus.toString();
	}
	
	/**
	 * Disables the course
	 * @param courseId
	 * @return String 
	 */
	public String enableCourse(int courseId){
		
		Connection connection = null;
	    PreparedStatement preparedStatement = null;
		StringBuffer sbuffStatus = new StringBuffer();
		
		try {
			connection = DBConnectionManager.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(dbQueries.getProperty("course.enable"));
			preparedStatement.setInt(1,courseId);
			
			int status = preparedStatement.executeUpdate();
			//Returns 1 if any rows updated, returns 0 if no rows updated
			if(status>0)
				sbuffStatus.append("successfully enabled the course : ").append(courseId);
			else if(status==0)
				sbuffStatus.append("No course with courseId : ").append(courseId).append(" available to enable");
			connection.commit();
		} catch(SQLException sqlExp){
			System.out.println("SQLException occurred in enableCourse() " + sqlExp.getMessage());
			try {
				connection.rollback();
			} catch (SQLException e1) {
				System.out.println(sbuffStatus.toString());
			}
		}catch (Exception e) {
			System.out.println("Exception occurred in getCourseInfo() "+e.getMessage());
			sbuffStatus.append("An error occurred while enabling the course");
			try {
				connection.rollback();
			} catch (SQLException e1) {
				System.out.println(sbuffStatus.toString());
			}
		}finally{
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			DBConnectionManager.close(connection, preparedStatement, null);
		}
		
		return sbuffStatus.toString();
	}

	
	/**
	 * Adds a course and all the corresponding information like schedule, curriculum, books.
	 * @param courseVo
	 * @return String
	 * @throws SQLException
	 */
	public String addCourse(CoursesVO courseVo){
	
		Connection connection = null;
	    PreparedStatement preparedStatement = null;
		String statusMessage = null;
	    try {
			connection = DBConnectionManager.getConnection();
			connection.setAutoCommit(false);
		
			int courseId = addCourseInfo(connection, courseVo);
			String insertCourseScheduleStatus = new ScheduleDAOImpl().addCourseSchedule(courseId, courseVo.getScheduleList());
			String insertCourseCurriculumStatus = new CurriculumDAOImpl().addCourseCurriculum(courseId, courseVo.getCurriculumList());
			String insertCourseBookStatus = new BooksDAOImpl().addCourseBook(courseId, courseVo.getBooksList());
			String eanbleCourseStatus = enableCourse(courseId);
			
			
			if(insertCourseScheduleStatus.equals(Constants.SUCCESS) && insertCourseCurriculumStatus.equals(Constants.SUCCESS) && insertCourseBookStatus.equals(Constants.SUCCESS) && 
					eanbleCourseStatus.equals(Constants.SUCCESS)){
				connection.commit();
				statusMessage= "Successfully created course : "+courseVo.getCourseName();
			}else{
				connection.rollback();
				statusMessage = "Unable to create the course due to some issues";
			}
			connection.commit();
				
		} catch(SQLException sqlExp){
			System.out.println("SQLException occurred in addCourse() " + sqlExp.getMessage());
		}catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			statusMessage = "An error occurred while creating the course";
			System.out.println("Exception occurred in addCourse() "+e.getMessage());
		}finally{
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			DBConnectionManager.close(connection, preparedStatement, null);
		}
		return statusMessage;
	}

	/**
	 * Insert the Course information
	 * @param connection
	 * @param couseInfo
	 * @return int
	 * @throws Exception
	 */
	private int addCourseInfo(Connection connection, CoursesVO courseVO) throws Exception {
		
		 PreparedStatement preparedStatement = null;
		 ResultSet resultSet=null;
		 int courseId=0;
		try{
			preparedStatement = connection.prepareStatement(dbQueries.getProperty("course.insert.info"),PreparedStatement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, courseVO.getCourseName());
			preparedStatement.setInt(2, courseVO.getCourseAmount());
			preparedStatement.setString(3, courseVO.getCourseDesc());
			preparedStatement.setString(4, "N"); // adding with "Y" which means enabled
			preparedStatement.executeUpdate();
			resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next())
				courseId = resultSet.getInt(Constants.COURSE_ID);
			
		}catch(SQLException sqlExp){
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			System.out.println("SQLException occurred in insertCourseInfo() " + sqlExp.getMessage());
		}catch(Exception exp){
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			System.out.println("Exception occurred in insertCourseInfo() "+exp.getMessage());
			throw exp;
		}finally{
			DBConnectionManager.close(null, preparedStatement, resultSet);
		}
		return courseId;
	}
	
	/**
	 * Adds the schedule for a course
	 * @param connection
	 * @param courseId
	 * @param courseSchList
	 * @return boolean
	 * @throws Exception
	 */
	
//	private boolean insertCourseSchedule(Connection connection, int courseId, List<Integer> courseSchList) throws Exception {
//		 PreparedStatement preparedStatement = null;
//		 boolean success = false;
//		 try{
//			preparedStatement = connection.prepareStatement(dbQueries.getProperty("course.insert.schedule"));
//			for(Integer scheduleId : courseSchList){
//				preparedStatement.setInt(1, courseId);
//				preparedStatement.setInt(2, scheduleId);
//			    preparedStatement.addBatch();
//			}
//			int count[] = preparedStatement.executeBatch();
//			if(count.length > 0)
//				success =true;
//			
//		}catch(SQLException sqlExp){
//			System.out.println("SQLException occurred in insertCourseSchedule() " + sqlExp.getMessage());
//		}catch(Exception exp){
//			System.out.println("Exception occurred in insertCourseSchedule() "+exp.getMessage());
//			throw exp;
//		}finally{
//			DBConnectionManager.close(null, preparedStatement, null);
//		}
//		return success;
//	}
	
	/**
	 * Inserts curriculum for a course
	 * @param connection
	 * @param courseId
	 * @param curriculumList
	 * @return boolean 
	 * @throws Exception
	 */
//	private boolean insertCourseCurriculum(Connection connection, int courseId, List<Integer> curriculumList) throws Exception {
//		 PreparedStatement preparedStatement = null;
//		 boolean success = false;
//		try{
//			preparedStatement = connection.prepareStatement(dbQueries.getProperty("course.insert.curriculum"));
//			for(int curriculum_id : curriculumList){
//				preparedStatement.setInt(1, courseId);
//				preparedStatement.setInt(2, curriculum_id);
//			    preparedStatement.addBatch();
//			}
//			int count[] = preparedStatement.executeBatch();
//			if(count.length > 0)
//				success =true;
//			
//		}catch(SQLException sqlExp){
//			System.out.println("SQLException occurred in insertCourseCurriculum() " + sqlExp.getMessage());
//		}catch(Exception exp){
//			System.out.println("Exception occurred in insertCourseCurriculum() "+exp.getMessage());
//			throw exp;
//		}finally{
//			DBConnectionManager.close(null, preparedStatement, null);
//		}
//		return success;
//	}

	/**
	 * Inserts books for a course
	 * @param connection
	 * @param courseId
	 * @param booksList
	 * @return boolean
	 * @throws Exception
	 */
//	private boolean insertCourseBook(Connection connection, int courseId, List<Integer> booksList) throws Exception {
//		 PreparedStatement preparedStatement = null;
//		 boolean success = false;
//		try{
//			preparedStatement = connection.prepareStatement(dbQueries.getProperty("course.insert.book"));
//			for(Integer bookId : booksList){
//				preparedStatement.setInt(1, courseId);
//				preparedStatement.setInt(2, bookId);
//			    preparedStatement.addBatch();
//			}
//			int count[] = preparedStatement.executeBatch();
//			if(count.length > 0)
//				success =true;
//		}catch(SQLException sqlExp){
//			System.out.println("SQLException occurred in insertCourseBook() " + sqlExp.getMessage());
//		}catch(Exception exp){
//			System.out.println("Exception occurred in insertCourseBook() "+exp.getMessage());
//			throw exp;
//		}finally{
//			DBConnectionManager.close(null, preparedStatement, null);
//		}
//		return success;
//	}
	
	/**
	 * Updates the Course information
	 *
	 */

	public String updateCourseInfo(CoursesVO courseVo) {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String statusMessage = null;
		try{
			connection = DBConnectionManager.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(dbQueries.getProperty("course.update.course_info"));
			preparedStatement.setString(1, courseVo.getCourseName());
			preparedStatement.setInt(2, courseVo.getCourseAmount());
			preparedStatement.setString(3, courseVo.getCourseDesc());
			preparedStatement.setInt(4, courseVo.getCourseId());
			
			int count = preparedStatement.executeUpdate();
			if(count > 0)
				statusMessage =Constants.SUCCESS;
			connection.commit();
		}catch(Exception exp){
			statusMessage = Constants.FAILURE;
			System.out.println("Exception occurred in updateCourseInfo() "+exp.getMessage());
		}finally{
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			DBConnectionManager.close(connection, preparedStatement, null);
		}
		return statusMessage;
	}
	
	/**
	 * Updates the course schedule
	 */
	public String updateCourseSchedule(int courseId, List<ScheduleVO> scheduleVOs) {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String statusMessage = null;
		try{
			connection = DBConnectionManager.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(dbQueries.getProperty("course.delete.schedule"));
			preparedStatement.setInt(1, courseId);
			int status = preparedStatement.executeUpdate();
			if(preparedStatement!=null)
				preparedStatement.close();
			
			if(status>0){
				String insertSchstatus = new ScheduleDAOImpl().addCourseSchedule(courseId, scheduleVOs);
				if(insertSchstatus.equals(Constants.SUCCESS)){
					connection.commit();
					statusMessage = Constants.SUCCESS;
				}
			}
		}catch(Exception exp){
			try {
				connection.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("Exception occurred in updateCourseSchedule() "+exp.getMessage());
			statusMessage = Constants.FAILURE;
		}finally{
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			DBConnectionManager.close(connection, preparedStatement, null);
		}
		return statusMessage;
	}

/**
 * Updates the course curriculum
 */
	public String updateCourseCurriculum(int courseId, List<CurriculumVO> curriculumList) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String statusMessage = "";
		try{
			connection = DBConnectionManager.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(dbQueries.getProperty("course.delete.curriculum"));
			preparedStatement.setInt(1, courseId);
			int status = preparedStatement.executeUpdate();
			if(preparedStatement!=null)
				preparedStatement.close();
			
			if(status>0){
				String insertCurrStatus = new CurriculumDAOImpl().addCourseCurriculum(courseId, curriculumList);
				if(insertCurrStatus.equals(Constants.SUCCESS)){
					connection.commit();
					statusMessage = Constants.SUCCESS;
				}
			}
		}catch(Exception exp){
			try {
				connection.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("Exception occurred in updateCourseCurriculum() "+exp.getMessage());
			statusMessage = Constants.FAILURE;
		}finally{
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			DBConnectionManager.close(connection, preparedStatement, null);
		}
		return statusMessage;
	}

	/**
	 * Updates the course book
	 */
	public String updateCourseBook(int courseId, List<BookVO> booksList) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String statusMessage = "";
		try{
			connection = DBConnectionManager.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(dbQueries.getProperty("course.delete.book"));
			preparedStatement.setInt(1, courseId);
			int status = preparedStatement.executeUpdate();
			if(preparedStatement!=null)
				preparedStatement.close();
			
			if(status>0){
				String insertBookStatus = new BooksDAOImpl().addCourseBook(courseId, booksList);
				if(insertBookStatus.equals(Constants.SUCCESS)){
					connection.commit();
					statusMessage = Constants.SUCCESS;
				}
			}
		}catch(Exception exp){
			try {
				connection.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("Exception occurred in updateCourseBook() "+exp.getMessage());
			statusMessage = Constants.FAILURE;
		}finally{
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			DBConnectionManager.close(connection, preparedStatement, null);
		}
		return statusMessage;
	}
	
}
