package com.scr.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.scr.dao.CourseDAO;
import com.scr.util.Constants;
import com.scr.util.DBConnectionManager;
import com.scr.util.PropertyLoader;
import com.scr.vo.CourseInfoVO;
import com.scr.vo.CourseVO;
import com.scr.vo.ScheduleVO;

public class CourseDAOImpl implements CourseDAO {
	
private Properties dbQueries = PropertyLoader.getDbProperties();
	
	/**
	 * Lists all courses which are enabled
	 * @return List<CourseInfoVO> 
	 */
	public List<CourseInfoVO> listAllCourse(){
		
		Connection conn = null;
	    PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<CourseInfoVO> courseInfoList =null;
		try {
			conn = DBConnectionManager.getConnection();
			pstmt = conn.prepareStatement(dbQueries.getProperty("course.list.all"));
			rs = pstmt.executeQuery();
			
			courseInfoList = new ArrayList<CourseInfoVO>();
			while(rs.next()){
				courseInfoList.add(new CourseInfoVO(rs.getInt(Constants.COURSE_ID), rs.getString(Constants.COURSE_NAME), 
						rs.getInt(Constants.COURSE_AMOUNT), rs.getString(Constants.COURSE_DESC)));
			}
			
		}catch(SQLException sqlExp){
			System.out.println("SQLException occurred in listAllCourse() " + sqlExp.getMessage());
		}catch(Exception exp){
			System.out.println("Exception occurred in listAllCourse() " + exp.getMessage());
		}finally{
			DBConnectionManager.close(conn, pstmt, rs);
		}
		return courseInfoList;
	}
	
	/**
	 * Gets details of the particular course.
	 * @param courseId
	 * @return Map<String, Object>
	 */
	public Map<String,Object> getCourseDetails(int courseId){
		
		Connection conn = null;
	    PreparedStatement pstmt = null;
		ResultSet rs = null;
		Map<String,Object> courseDetails = new HashMap<String, Object>();
		try {
			conn = DBConnectionManager.getConnection();
			 
			CourseInfoVO courseInfo =  getCourseInfo(conn, courseId);
			List<ScheduleVO> courseSchedule = getCourseSchedule(conn, courseId);  
			List<String> courseCurriculum = getCourseCurriculum(conn, courseId); // may want to use CurriculumVO
			List<String> courseBooks = getCourseBooks(conn, courseId);  // may want to use BookVO
			
			courseDetails.put("courseInfo", courseInfo);
			courseDetails.put("courseSchedule", courseSchedule);
			courseDetails.put("courseCurriculum", courseCurriculum);
			courseDetails.put("courseBooks", courseBooks);
			
		}catch(SQLException sqlExp){
			System.out.println("SQLException occurred in getCourseDetails() " + sqlExp.getMessage());
		}catch (Exception e) {
			System.out.println("Exception occurred in getCourseDetails() "+e.getMessage());
		}finally{
			DBConnectionManager.close(conn, pstmt, rs);
		}
		return courseDetails;
	}
	
	/**
	 * Gets the Course Information
	 * @param conn
	 * @param courseId
	 * @return CourseInfoVO
	 */
	private CourseInfoVO getCourseInfo(Connection conn, int courseId) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CourseInfoVO courseInfo = null;
		
		try{
			pstmt = conn.prepareStatement(dbQueries.getProperty("course.get.info"));
			pstmt.setInt(1, courseId);
			rs = pstmt.executeQuery();
			while(rs.next()){
				courseInfo = new CourseInfoVO(rs.getInt("course_id"), rs.getString("course_name"), rs.getInt("amount"), rs.getString("course_description"));
			}
			
		}catch(SQLException sqlExp){
			System.out.println("SQLException occurred in getCourseInfo() " + sqlExp.getMessage());
		}catch(Exception exp){
			System.out.println("Exception occurred in getCourseInfo() "+exp.getMessage());
		}finally{
			DBConnectionManager.close(null, pstmt, rs);
		}
		return courseInfo;
	}
	
	/**
	 * Gets the course schedule
	 * @param conn
	 * @param courseId
	 * @return List<ScheduleVO>
	 */
	private List<ScheduleVO> getCourseSchedule(Connection conn, int courseId) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ScheduleVO> courseSchedule = null;
		
		try{
			pstmt = conn.prepareStatement(dbQueries.getProperty("course.get.schedule"));
			pstmt.setInt(1, courseId);
			rs = pstmt.executeQuery();
			courseSchedule =new ArrayList<ScheduleVO>();
			while(rs.next()){
				courseSchedule.add(new ScheduleVO(rs.getDate(1), rs.getDate(2), rs.getTime(3), rs.getTime(4)));
			}
			
		}catch(SQLException sqlExp){
			System.out.println("SQLException occurred in getCourseSchedule() " + sqlExp.getMessage());
		}catch(Exception exp){
			System.out.println("Exception occurred in getCourseSchedule() "+exp.getMessage());
		}finally{
			DBConnectionManager.close(null, pstmt, rs);
		}
		return courseSchedule;
	}
	
/**
 * Gets the Course Curriculum
 * @param conn
 * @param courseId
 * @return List<String>
 */
	private List<String> getCourseCurriculum(Connection conn, int courseId) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<String> courseCurriculum = null;
		
		try{
			pstmt = conn.prepareStatement(dbQueries.getProperty("course.get.curriculum"));
			pstmt.setInt(1, courseId);
			rs = pstmt.executeQuery();
			courseCurriculum =new ArrayList<String>();
			while(rs.next()){
				courseCurriculum.add(rs.getString(1));
			}
			
		}catch(SQLException sqlExp){
			System.out.println("SQLException occurred in getCourseCurriculum() " + sqlExp.getMessage());
		}catch(Exception exp){
			System.out.println("Exception occurred in getCourseCurriculum() "+exp.getMessage());
		}finally{
			DBConnectionManager.close(null, pstmt, rs);
		}
		return courseCurriculum;
	}
		
	/**
	 * Gets the Course Books
	 * @param conn
	 * @param courseId
	 * @return List<String>
	 */
	private List<String> getCourseBooks(Connection conn, int courseId) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<String> courseBooks = null;
		
		try{
			pstmt = conn.prepareStatement(dbQueries.getProperty("course.get.books"));
			pstmt.setInt(1, courseId);
			rs = pstmt.executeQuery();
			courseBooks =new ArrayList<String>();
			while(rs.next()){
				courseBooks.add(rs.getString(1));
			}
			
		}catch(SQLException sqlExp){
			System.out.println("SQLException occurred in getCourseBooks() " + sqlExp.getMessage());
		}catch(Exception exp){
			System.out.println("Exception occurred in getCourseBooks() "+exp.getMessage());
		}finally{
			DBConnectionManager.close(null, pstmt, rs);
		}
		return courseBooks;
	}

	/**
	 * Disables the course
	 * @param courseId
	 * @return String 
	 */
	public String disableCourse(int courseId){
		
		Connection conn = null;
	    PreparedStatement pstmt = null;
		StringBuffer sbuffStatus = new StringBuffer();
		
		try {
			conn = DBConnectionManager.getConnection();
			pstmt = conn.prepareStatement(dbQueries.getProperty("course.disable"));
			pstmt.setInt(1,courseId);
			
			int status = pstmt.executeUpdate();
			//Returns 1 if any rows updated, returns 0 if no rows updated
			if(status>0)
				sbuffStatus.append("successfully disabled the course : ").append(courseId);
			else if(status==0)
				sbuffStatus.append("No course with courseId : ").append(courseId).append(" available to disable");
			
		} catch(SQLException sqlExp){
			System.out.println("SQLException occurred in disableCourse() " + sqlExp.getMessage());
		}catch (Exception e) {
			System.out.println("Exception occurred in getCourseInfo() "+e.getMessage());
			sbuffStatus.append("An error occurred while disabling the course");
		}finally{
			DBConnectionManager.close(conn, pstmt, null);
		}
		
		return sbuffStatus.toString();
	}

	
	/**
	 * Adds a course and all the corresponding information like schedule, curriculum, books.
	 * @param courseVo
	 * @return String
	 * @throws SQLException
	 */
	public String addCourse(CourseVO courseVo){
	
		Connection conn = null;
	    PreparedStatement pstmt = null;
		String statusMessage ="";
	    try {
			conn = DBConnectionManager.getConnection();
			conn.setAutoCommit(false);
		
			int courseId = insertCourseInfo(conn, courseVo.getCourseInfo());
			boolean insertCourseScheduleStatus = insertCourseSchedule(conn, courseId, courseVo.getCourseSchList());
			boolean insertCourseCurriculumStatus = insertCourseCurriculum(conn, courseId, courseVo.getCurriculumList());
			boolean insertCourseBookStatus = insertCourseBook(conn, courseId, courseVo.getBooksList());
			
			if(insertCourseScheduleStatus && insertCourseCurriculumStatus && insertCourseBookStatus){
				conn.commit();
				statusMessage= "Successfully created course : "+courseVo.getCourseInfo().getCourseName();
			}else{
				conn.rollback();
				statusMessage = "Unable to create the course due to some issues";
			}
				
		} catch(SQLException sqlExp){
			System.out.println("SQLException occurred in addCourse() " + sqlExp.getMessage());
		}catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			statusMessage = "An error occurred while creating the course";
			System.out.println("Exception occurred in addCourse() "+e.getMessage());
		}finally{
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			DBConnectionManager.close(conn, pstmt, null);
		}
		return statusMessage;
	}

	/**
	 * Insert the Course information
	 * @param conn
	 * @param couseInfo
	 * @return int
	 * @throws Exception
	 */
	private int insertCourseInfo(Connection conn, CourseInfoVO couseInfo) throws Exception {
		
		 PreparedStatement pstmt = null;
		 ResultSet rs=null;
	//	 String insertCourseQuery = SqlQueryConstants.COURSE_INSERT_INFO;
	//	 String newCourseIdQuery = SqlQueryConstants.COURSE_NEW_COURSE_ID; // Get the latest/newly added courseId
		 int courseId=0;
		try{
			pstmt = conn.prepareStatement(dbQueries.getProperty("course.insert.info"));
			pstmt.setString(1, couseInfo.getCourseName());
			pstmt.setInt(2, couseInfo.getCourseAmount());
			pstmt.setString(3, couseInfo.getCourseDesc());
			pstmt.setString(4, "Y"); // adding with "Y" which means enabled
			int status = pstmt.executeUpdate();
			if(pstmt!=null)
				pstmt.close();
			
			if(status>0){
				pstmt = conn.prepareStatement(dbQueries.getProperty("course.new.course_id"));
				rs = pstmt.executeQuery();
				while(rs.next())
					courseId = rs.getInt(1);
			}
		}catch(SQLException sqlExp){
			System.out.println("SQLException occurred in insertCourseInfo() " + sqlExp.getMessage());
		}catch(Exception exp){
			System.out.println("Exception occurred in insertCourseInfo() "+exp.getMessage());
			throw exp;
		}finally{
			DBConnectionManager.close(null, pstmt, rs);
		}
		return courseId;
	}
	
	/**
	 * Adds the schedule for a course
	 * @param conn
	 * @param courseId
	 * @param courseSchList
	 * @return boolean
	 * @throws Exception
	 */
	
	private boolean insertCourseSchedule(Connection conn, int courseId, List<Integer> courseSchList) throws Exception {
		 PreparedStatement pstmt = null;
		 boolean success = false;
		 try{
			pstmt = conn.prepareStatement(dbQueries.getProperty("course.insert.schedule"));
			for(Integer scheduleId : courseSchList){
				pstmt.setInt(1, courseId);
				pstmt.setInt(2, scheduleId);
			    pstmt.addBatch();
			}
			int count[] = pstmt.executeBatch();
			if(count.length > 0)
				success =true;
			
		}catch(SQLException sqlExp){
			System.out.println("SQLException occurred in insertCourseSchedule() " + sqlExp.getMessage());
		}catch(Exception exp){
			System.out.println("Exception occurred in insertCourseSchedule() "+exp.getMessage());
			throw exp;
		}finally{
			DBConnectionManager.close(null, pstmt, null);
		}
		return success;
	}
	
	/**
	 * Inserts curriculum for a course
	 * @param conn
	 * @param courseId
	 * @param curriculumList
	 * @return boolean 
	 * @throws Exception
	 */
	private boolean insertCourseCurriculum(Connection conn, int courseId, List<Integer> curriculumList) throws Exception {
		 PreparedStatement pstmt = null;
		 boolean success = false;
		try{
			pstmt = conn.prepareStatement(dbQueries.getProperty("course.insert.curriculum"));
			for(int curriculum_id : curriculumList){
				pstmt.setInt(1, courseId);
				pstmt.setInt(2, curriculum_id);
			    pstmt.addBatch();
			}
			int count[] = pstmt.executeBatch();
			if(count.length > 0)
				success =true;
			
		}catch(SQLException sqlExp){
			System.out.println("SQLException occurred in insertCourseCurriculum() " + sqlExp.getMessage());
		}catch(Exception exp){
			System.out.println("Exception occurred in insertCourseCurriculum() "+exp.getMessage());
			throw exp;
		}finally{
			DBConnectionManager.close(null, pstmt, null);
		}
		return success;
	}

	/**
	 * Inserts books for a course
	 * @param conn
	 * @param courseId
	 * @param booksList
	 * @return boolean
	 * @throws Exception
	 */
	private boolean insertCourseBook(Connection conn, int courseId, List<Integer> booksList) throws Exception {
		 PreparedStatement pstmt = null;
		 boolean success = false;
		try{
			pstmt = conn.prepareStatement(dbQueries.getProperty("course.insert.book"));
			for(Integer bookId : booksList){
				pstmt.setInt(1, courseId);
				pstmt.setInt(2, bookId);
			    pstmt.addBatch();
			}
			int count[] = pstmt.executeBatch();
			if(count.length > 0)
				success =true;
		}catch(SQLException sqlExp){
			System.out.println("SQLException occurred in insertCourseBook() " + sqlExp.getMessage());
		}catch(Exception exp){
			System.out.println("Exception occurred in insertCourseBook() "+exp.getMessage());
			throw exp;
		}finally{
			DBConnectionManager.close(null, pstmt, null);
		}
		return success;
	}
	
	/**
	 * Updates the Course information
	 *
	 */

	public String updateCourseInfo(int courseId, CourseInfoVO courseInfoVo) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		String statusMessage = "";
		try{
			conn = DBConnectionManager.getConnection();
			pstmt = conn.prepareStatement(dbQueries.getProperty("course.update.course_info"));
			pstmt.setString(1, courseInfoVo.getCourseName());
			pstmt.setInt(2, courseInfoVo.getCourseAmount());
			pstmt.setString(3, courseInfoVo.getCourseDesc());
			pstmt.setInt(4, courseId);
			
			int count = pstmt.executeUpdate();
			if(count > 0)
				statusMessage ="Successfully updated the details";
			
		}catch(Exception exp){
			statusMessage = "An error occured while updating the details";
			System.out.println("Exception occurred in updateCourseInfo() "+exp.getMessage());
		}finally{
			DBConnectionManager.close(conn, pstmt, null);
		}
		return statusMessage;
	}
	
	/**
	 * Updates the course schedule
	 */
	public String updateCourseSchedule(int courseId, List<Integer> courseSchList) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		String statusMessage = "";
		try{
			conn = DBConnectionManager.getConnection();
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(dbQueries.getProperty("course.delete.schedule"));
			pstmt.setInt(1, courseId);
			int status = pstmt.executeUpdate();
			if(pstmt!=null)
				pstmt.close();
			
			if(status>0){
				boolean insertSchstatus = insertCourseSchedule(conn, courseId, courseSchList);
				if(insertSchstatus){
					conn.commit();
					statusMessage ="Successfully updated the details";
				}
			}
		}catch(Exception exp){
			try {
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("Exception occurred in updateCourseSchedule() "+exp.getMessage());
			statusMessage = "An error occured while updating the details";
		}finally{
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			DBConnectionManager.close(conn, pstmt, null);
		}
		return statusMessage;
	}

/**
 * Updates the course curriculum
 */
	public String updateCourseCurriculum(int courseId, List<Integer> curriculumList) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String statusMessage = "";
		try{
			conn = DBConnectionManager.getConnection();
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(dbQueries.getProperty("course.delete.curriculum"));
			pstmt.setInt(1, courseId);
			int status = pstmt.executeUpdate();
			if(pstmt!=null)
				pstmt.close();
			
			if(status>0){
				boolean insertCurrStatus = insertCourseCurriculum(conn, courseId, curriculumList);
				if(insertCurrStatus){
					conn.commit();
					statusMessage ="Successfully updated the details";
				}
			}
		}catch(Exception exp){
			try {
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("Exception occurred in updateCourseCurriculum() "+exp.getMessage());
			statusMessage = "An error occured while updating the details";
		}finally{
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			DBConnectionManager.close(conn, pstmt, null);
		}
		return statusMessage;
	}

	/**
	 * Updates the course book
	 */
	public String updateCourseBook(int courseId, List<Integer> booksList) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String statusMessage = "";
		try{
			conn = DBConnectionManager.getConnection();
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(dbQueries.getProperty("course.delete.book"));
			pstmt.setInt(1, courseId);
			int status = pstmt.executeUpdate();
			if(pstmt!=null)
				pstmt.close();
			
			if(status>0){
				boolean insertBookStatus = insertCourseBook(conn, courseId, booksList);
				if(insertBookStatus){
					conn.commit();
					statusMessage ="Successfully updated the details";
				}
			}
		}catch(Exception exp){
			try {
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("Exception occurred in updateCourseBook() "+exp.getMessage());
			statusMessage = "An error occured while updating the details";
		}finally{
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			DBConnectionManager.close(conn, pstmt, null);
		}
		return statusMessage;
	}
	
}
