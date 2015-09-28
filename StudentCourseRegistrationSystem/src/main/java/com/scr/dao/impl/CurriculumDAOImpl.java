package com.scr.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.scr.dao.CurriculumDAO;
import com.scr.util.Constants;
import com.scr.util.DBConnectionManager;
import com.scr.util.PropertyLoader;
import com.scr.vo.CurriculumVO;



public class CurriculumDAOImpl implements CurriculumDAO {
	private Properties dbQueries = PropertyLoader.getDbProperties();

	/**
	 * This method lists all the curriculum details
	 * @param Topic_Name
	 * @return curriculumVO list
	 */	
	public List<CurriculumVO> getAllCurriculum() {
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		ResultSet resultSet = null;
		List<CurriculumVO> curriculumVOs = null;

		try{
			//getting connection to the DB
			connection = DBConnectionManager.getConnection();

			//Prepare the statement to list topic name using preparedStatement
			preparedStatement = connection.prepareStatement(dbQueries.getProperty("fetch.all.curriculum"));

			//executing the select query
			resultSet = preparedStatement.executeQuery();
			curriculumVOs = new ArrayList<CurriculumVO>();
			while(resultSet.next()){
				curriculumVOs.add(new CurriculumVO(resultSet.getString(Constants.TOPIC_NAME)));
			}	
		}catch(Exception exp){
			System.out.println("Error  : " + exp);
		}finally{
			DBConnectionManager.close(connection, preparedStatement, resultSet);
		}
		return curriculumVOs;
	}

	/**
	 * This method lists the curriculum details from the curriculum table
	 * @param Topic Name
	 * @return map
	 */	
	private boolean checkCurriculumExists(Connection connection, String Topic_Name) {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		int count = 0;
		boolean curriculumExists = false;

		try{
			//getting connection to the DB
			connection = DBConnectionManager.getConnection();
			connection.setAutoCommit(false);

			//Prepare the statement to list topic names using preparedStatement
			preparedStatement = connection.prepareStatement(dbQueries.getProperty("fetch.curriculum"));
			//set TopicName
			preparedStatement.setString(1, Topic_Name);
			//executing the select query
			resultSet = preparedStatement.executeQuery();

			while(resultSet.next())
				count = resultSet.getInt(1);
			connection.setAutoCommit(true);

			if(count>0)
				curriculumExists = true;
			else
				curriculumExists = false;

		}catch(Exception exp){
			System.out.println("Error  : " + exp);
		}finally{
			DBConnectionManager.close(null, preparedStatement, resultSet);
		}
		return curriculumExists;
	}

	/**
	 * This method lists the curriculum details related to the particular courseID
	 * @param courseID
	 * 	@return list of the topic name related to the particular course ID
	 */
	public List<CurriculumVO> getCourseCurriculum(int courseID) {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		List<CurriculumVO> curriculumVOs = null;

		try{
			//getting connection to the DB
			connection = DBConnectionManager.getConnection();
			connection.setAutoCommit(false);

			//Prepare the statement to list topic name using preparedStatement
			preparedStatement = connection.prepareStatement(dbQueries.getProperty("fetch.course.curriculum"));


			//set courseID
			preparedStatement.setInt(1, courseID);

			//executing the select query
			resultSet = preparedStatement.executeQuery();
			curriculumVOs =new ArrayList<CurriculumVO>();

			while(resultSet.next()){
				curriculumVOs.add(new CurriculumVO(resultSet.getString(Constants.TOPIC_NAME)));
			}
			connection.setAutoCommit(true);

		}catch(Exception exp){
			System.out.println("Error  : " + exp);
		}finally{
			DBConnectionManager.close(connection, preparedStatement, resultSet);
		}
		System.out.println("courseCurriculum === > " + curriculumVOs);
		return curriculumVOs;
	}

	/**
	 * 
	 * This method inserts data into Curriculum table
	 * @param curriculumId
	 * @param topicName
	 * @return statusmessage
	 */

	public String addCurriculum(CurriculumVO curriculumVO){
		String statusMessage = null;
		PreparedStatement preparedStatement = null;
		Connection connection = null;

		try {
			//get connection to DB
			connection = DBConnectionManager.getConnection();
			connection.setAutoCommit(false);
			boolean curriculumExists = checkCurriculumExists(connection, curriculumVO.getTopicName());

			//Check if curriculum already exists.
			if(curriculumExists)
				throw new Exception("topicName already exist");

			//Prepare insert statement to insert curriculum details using preparedStatement
			preparedStatement = connection.prepareStatement(dbQueries.getProperty("add.curriculum"));

			//set curriculumId and topicName
			preparedStatement.setString(2, curriculumVO.getTopicName());

			//executing the insert query
			int count=preparedStatement.executeUpdate();
			connection.commit();
			connection.setAutoCommit(true);
			if(count>0)
				statusMessage = Constants.SUCCESS;

		} catch(Exception exp){
			statusMessage = Constants.FAILURE;
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

	public String addCourseCurriculum(int courseId, List<CurriculumVO> curriculumList){

		String statusMessage = Constants.FAILURE;
		PreparedStatement preparedStatement = null;
		Connection connection = null;

		try {
			//get connection to DB
			connection = DBConnectionManager.getConnection();
			connection.setAutoCommit(false);
			//Prepare insert statement to insert curriculum details using preparedStatement
			preparedStatement = connection.prepareStatement(dbQueries.getProperty("add.course.curriculum"));
			for(CurriculumVO curriculumVO : curriculumList){
				preparedStatement.setInt(1, courseId);
				preparedStatement.setInt(2, curriculumVO.getCurriculumId());
				preparedStatement.addBatch();
			}
			System.out.println("Inserting into course curriculum "+preparedStatement.toString());
			//execute the insert statement
			int count[] = preparedStatement.executeBatch();			
			if(count.length > 0)
				statusMessage = Constants.SUCCESS;			
			System.out.println("insertCourseCurriculum recordcount== > " + count.length);	
			connection.commit();
			connection.setAutoCommit(true);	

		} catch(Exception exp){
			statusMessage = Constants.FAILURE;
			System.out.println("Error : " + exp);
		}finally{
			DBConnectionManager.close(connection, preparedStatement, null);
		}
		return statusMessage;
	}

	/**
	 * Updates the data in Curriculum table
	 * @param curriculumId
	 * @param topicName
	 * @return String
	 */
	public String updateCurriculum(CurriculumVO curriculumVO){

		String statusMessage = null;
		PreparedStatement preparedStatement = null;
		Connection connection = null;

		try {
			//get connection to DB
			connection = DBConnectionManager.getConnection();
			connection.setAutoCommit(false);

			//Prepare update statement to update curriculum details using preparedStatement
			preparedStatement = connection.prepareStatement(dbQueries.getProperty("update.curriculum"));
			preparedStatement.setString(1, curriculumVO.getTopicName());
			preparedStatement.setInt(2, curriculumVO.getCurriculumId());

			//execute the update statement
			int count = preparedStatement.executeUpdate();
			connection.commit();
			connection.setAutoCommit(true);
			if(count>0)
				statusMessage = Constants.SUCCESS;
		}catch(Exception exp){
			statusMessage = Constants.FAILURE;
			System.out.println("Error : " + exp);
		}finally{
			DBConnectionManager.close(connection, preparedStatement, null);
		}
		return statusMessage;
	}
	/**
	 * deletes the data based on curriculumId in curriculum table
	 * @param curriculumId
	 */
	public String deleteCurriculum(int curriculumId){

		PreparedStatement preparedStatement = null;
		Connection connection = null;
		String statusMessage= null;
		try {

			int count=0;

			//get connection to DB
			connection = DBConnectionManager.getConnection();
			connection.setAutoCommit(false);

			//Prepare delete statement to delete course curriculum details using preparedStatement
			preparedStatement = connection.prepareStatement(dbQueries.getProperty("delete.course.curriculum"));
			preparedStatement.setInt(1, curriculumId);
			preparedStatement.executeUpdate();
			preparedStatement.close();

			//Prepare delete statement to delete curriculum details using preparedStatement
			preparedStatement = connection.prepareStatement(dbQueries.getProperty("delete.curriculum"));
			preparedStatement.setInt(1, curriculumId);
			count=preparedStatement.executeUpdate();
			connection.commit();
			connection.setAutoCommit(true);

			if(count>0)
				statusMessage = Constants.SUCCESS;

		} catch(Exception exp){
			statusMessage = Constants.FAILURE;
			System.out.println("Error : " + exp);
		}finally{
			DBConnectionManager.close(connection, preparedStatement, null);
		}
		return statusMessage;
	}

}


