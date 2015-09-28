package com.scr.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import com.scr.dao.CurriculumDAO;
import com.scr.vo.CurriculumVO;
import com.scr.vo.CoursesVO;


import com.scr.util.DBConnectionManager;
import com.scr.util.PropertyLoader;



public class CurriculumDAOImpl implements CurriculumDAO {
	private Properties dbQueries = PropertyLoader.getDbProperties();
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	private Connection connection = null;
	
	/**
	 * This method lists all the curriculum details
	 * @param Topic_Name
	 * @return map
	 */	
public Map<Integer,String> getAllCurriculum() {
		
		ResultSet rs = null;
		Map<Integer,String> map = null;
		
		try{
			//getting connection to the DB
			connection = DBConnectionManager.getConnection();
			
			//Prepare the statement to list topic name using preparedStatement
			preparedStatement = connection.prepareStatement(dbQueries.getProperty("fetch.all.curriculum"));
			
			//executing the select query
			rs = preparedStatement.executeQuery();
			map= new HashMap<Integer, String>();
			while(rs.next()){
				map.put(rs.getInt(1), rs.getString(2));
			}	
		}catch(Exception exp){
			System.out.println("Error  : " + exp);
		}finally{
			DBConnectionManager.close(null, preparedStatement, rs);
		}
		return map;
	}

/**
 * This method lists the curriculum details from the curriculum table
 * @param Topic Name
 * @return map
 */	
public Map<Integer,String> getCurriculum(String Topic_Name) {
		
		ResultSet rs = null;
		Map<Integer,String> map = null;
		
		try{
			//getting connection to the DB
			connection = DBConnectionManager.getConnection();
			connection.setAutoCommit(false);
			
			//Prepare the statement to list topic names using preparedStatement
			preparedStatement = connection.prepareStatement(dbQueries.getProperty("fetch.curriculum"));
					
			//set TopicName
			preparedStatement.setString(1, Topic_Name);
			
			//executing the select query
			rs = preparedStatement.executeQuery();
			map= new HashMap<Integer, String>();
			while(rs.next()){
				map.put(rs.getInt(1), rs.getString(2));
			}
			connection.setAutoCommit(true);
		}catch(Exception exp){
			System.out.println("Error  : " + exp);
		}finally{
			DBConnectionManager.close(null, preparedStatement, rs);
		}
		return map;
	}

	/**
	 * This method lists the curriculum details related to the particular courseID
	 * @param courseID
	 * 	@return list of the topic name related to the particular course ID
	*/
	public List<String> getCourseCurriculum(int courseID) {
		
		ResultSet rs = null;
		//String query = " select clm.topic_name from courses c, course_curriculum cc, curriculum clm where c.course_id = cc.course_id and cc.curriculum_id=clm.curriculum_id and c.course_id= ? ";
		List<String> courseCurriculum = null;
		
		try{
			//getting connection to the DB
			connection = DBConnectionManager.getConnection();
			connection.setAutoCommit(false);
			
			//Prepare the statement to list topic name using preparedStatement
			preparedStatement = connection.prepareStatement(dbQueries.getProperty("fetch.course.curriculum"));
			
			
			//set courseID
			preparedStatement.setInt(1, courseID);
			
			//executing the select query
			rs = preparedStatement.executeQuery();
			courseCurriculum =new ArrayList<String>();

			while(rs.next()){
				courseCurriculum.add(rs.getString(1));
			}
			connection.setAutoCommit(true);
			
		}catch(Exception exp){
			System.out.println("Error  : " + exp);
		}finally{
			DBConnectionManager.close(null, preparedStatement, rs);
		}
		System.out.println("courseCurriculum === > " + courseCurriculum);
		return courseCurriculum;
	}

	/**
	 * 
	 * This method inserts data into Curriculum table
	 * @param curriculumId
	 * @param topicName
	 * @return statusmessage
	 */
	
	public String addCurriculum(int curriculumId, String topicName){
		//int index = 0;
		String statusMessage = null;

		try {
			//get connection to DB
			connection = DBConnectionManager.getConnection();
			connection.setAutoCommit(false);
			boolean curriculumExists = checkcurriculumExists(topicName);

			//Check if curriculum already exists.
			if(curriculumExists)
				throw new Exception("topicName already exist");

			//Prepare insert statement to insert curriculum details using preparedStatement
			preparedStatement = connection.prepareStatement(dbQueries.getProperty("add.curriculum"));
			
			//set curriculumId and topicName
			preparedStatement.setInt(1, curriculumId);
			preparedStatement.setString(2, topicName);
			
			//executing the insert query
			int count=preparedStatement.executeUpdate();
		
			connection.setAutoCommit(true);
			if(count>0)
				statusMessage = "inserted successfully";

		} catch(Exception exp){
			System.out.println("Error : " + exp);
		}finally{
			DBConnectionManager.close(connection, preparedStatement, null);
		}
		return statusMessage;
		
	}
	
	/**
	 * Inserts courseId and curriculum_id into course_curriculum table
	 * @param courseId
	 * @param curriculumList
	 * @return  statusmessage
	 */
	
	public boolean addCourseCurriculum(int courseId, List<Integer> curriculumList){
		
		int index = 0;
		boolean statusMessage = false;

		try {
			//get connection to DB
			connection = DBConnectionManager.getConnection();
			connection.setAutoCommit(false);
			//Prepare insert statement to insert schedule details using preparedStatement
			preparedStatement = connection.prepareStatement(dbQueries.getProperty("add.course.curriculum"));
			for(int curriculum_id : curriculumList){
				preparedStatement.setInt(1, courseId);
				preparedStatement.setInt(2, curriculum_id);
				preparedStatement.addBatch();
			}
			//execute the insert statement
			int count[] = preparedStatement.executeBatch();			
			if(count.length > 0)
				statusMessage = true;			
			System.out.println("insertCourseCurriculum recordcount== > " + count.length);	
			System.out.println("Inserting into course curriculum "+preparedStatement.toString());
			connection.setAutoCommit(true);	

		} catch(Exception exp){
			System.out.println("Error : " + exp);
		}finally{
			DBConnectionManager.close(connection, preparedStatement, null);
		}
		return statusMessage;
	}
	
	private boolean checkcurriculumExists(String TopicName) {
		Map<Integer,String> map = new HashMap<Integer,String>();
		CurriculumDAOImpl cdi = new CurriculumDAOImpl();
		try {
			//prepare query to check if schedule exists
	         map = cdi.getCurriculum(TopicName);
	         
	         if(map.size()>0)
	        	 return true;
	         else
	        	 return false;

		
		}catch (Exception exp) {
			System.out.println("Error occured while checking if schedule exists or not" + exp);
		} finally {
			
		}
		return false;
		}
	
	/**
	 * Updates the data in Curriculum table
	 * @param curriculumId
	 * @param topicName
	 * @return String
	 */
	public String updateCurriculum(int curriculumId, String topicName){
		
		String statusMessage = null;
		try {
			//get connection to DB
			connection = DBConnectionManager.getConnection();
			connection.setAutoCommit(false);
			
			//Prepare update statement to update curriculum details using preparedStatement
			preparedStatement = connection.prepareStatement(dbQueries.getProperty("update.curriculum"));
			
			
			preparedStatement.setString(1, topicName);
			preparedStatement.setInt(2, curriculumId);

				
			//execute the update statement
			preparedStatement.executeUpdate();
			connection.setAutoCommit(true);
		}catch(Exception exp){
			System.out.println("Error : " + exp);
		}finally{
			DBConnectionManager.close(null, preparedStatement, null);
		}
		return statusMessage;
}
	/**
	 * deletes the data based on curriculumId in curriculum table
	 * @param curriculumId
	 */
	public void deleteCurriculum(int curriculumId)
	{
		try {
			String statusMessage= null;
			int count=0;
			
			//get connection to DB
			connection = DBConnectionManager.getConnection();
			connection.setAutoCommit(false);
			
			//Prepare delete statement to delete course curriculum details using preparedStatement
			preparedStatement = connection.prepareStatement(dbQueries.getProperty("delete.course.curriculum"));
			preparedStatement.setInt(1, curriculumId);
			preparedStatement.executeUpdate();
		
			//Prepare delete statement to delete curriculum details using preparedStatement
			preparedStatement = connection.prepareStatement(dbQueries.getProperty("delete.curriculum"));
			preparedStatement.setInt(1, curriculumId);
			count=preparedStatement.executeUpdate();
			
			connection.setAutoCommit(true);

			if(count>0)
				statusMessage = "deleted successfully";

		} catch(Exception exp){
			System.out.println("Error : " + exp);
		}finally{
			DBConnectionManager.close(connection, preparedStatement, null);
		}
	}
	
	
	//public static void main(String args[]) throws Exception{
		//CurriculumDAOImpl cdi = new CurriculumDAOImpl();
		
		//CurriculumVO obj = new CurriculumVO(8,"exceptions");
		
		//cdi.addCurriculum(8,"exceptions");
		//System.out.println("Done with add ciriculum");
		//cdi.getCourseCurriculum(1);
		//System.out.println("Done with getCiCourseciriculum");
		//cdi.updateCourseCurriculum(8, "handlings");
		//System.out.println("Done with aUpdate");
		//cdi.deleteCurriculum(2);

		  // System.out.println("Done");
	

	//}
}


