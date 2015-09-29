package com.scr.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.scr.dao.ScheduleDAO;
import com.scr.util.Constants;
import com.scr.util.DBConnectionManager;
import com.scr.util.PropertyLoader;
import com.scr.vo.ScheduleVO;

public class ScheduleDAOImpl implements ScheduleDAO {
	private Properties dbQueries = PropertyLoader.getDbProperties();

	/**
	 * This method adds schedule details to Schedule Table
	 * 
	 * @param scheduleVo
	 *            -scheduleVo contains schedule details which needs
	 *            to be persisted
	 * @return statusMessage specifying success or failure 
	 */
	public String addSchedule(ScheduleVO scheduleVo){
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		int index = 0;
		String statusMessage = null;

		try {
			//get connection to DB
			connection = DBConnectionManager.getConnection();
			connection.setAutoCommit(false);
			boolean scheduleExists = checkScheduleExists(connection , scheduleVo);

			//Check if student already exists. If schedule exists throw exception saying student already exists
			if(scheduleExists)
				throw new Exception("Schedule already exist");

			//Prepare insert statement to insert schedule details using preparedStatement
			preparedStatement = connection.prepareStatement(dbQueries.getProperty("add.schedule"));

			preparedStatement.setDate(++index,new java.sql.Date(scheduleVo.getStartDate().getTime()));
			preparedStatement.setDate(++index,new java.sql.Date(scheduleVo.getEndDate().getTime()));
			preparedStatement.setTime(++index,scheduleVo.getStartTime());
			preparedStatement.setTime(++index,scheduleVo.getEndTime());
			preparedStatement.setBoolean(++index,scheduleVo.isDayExists("Monday"));
			preparedStatement.setBoolean(++index,scheduleVo.isDayExists("Tuesday"));
			preparedStatement.setBoolean(++index,scheduleVo.isDayExists("Wednesday"));
			preparedStatement.setBoolean(++index,scheduleVo.isDayExists("Thursday"));
			preparedStatement.setBoolean(++index,scheduleVo.isDayExists("Friday"));
			preparedStatement.setBoolean(++index,scheduleVo.isDayExists("Saturday"));
			preparedStatement.setBoolean(++index,scheduleVo.isDayExists("Sunday"));
			System.out.println("Inserting schedule using DDL statement "+preparedStatement.toString());

			//execute the insert statement
			int count = preparedStatement.executeUpdate();
			connection.commit();
			if(count>0)
				statusMessage = Constants.SUCCESS;

		} catch (Exception e) {
			statusMessage=Constants.FAILURE;
			try {
				connection.rollback();
			} catch (SQLException e1) {
				System.out.println(statusMessage);
			}
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
	 * This method gives the list of all the schedules available 
	 *                                          in schedule table.
	 *                                          
	 * @return List of schedule	
	 */
	public List<ScheduleVO> getAllSchedule(){
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		ResultSet resultSet = null;

		List<ScheduleVO> scheduleList =null;
		try {
			//get connection to DB
			connection = DBConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement(dbQueries.getProperty("list.schedule"));

			//execute the select query 
			resultSet = preparedStatement.executeQuery();

			scheduleList = new ArrayList<ScheduleVO>();
			while(resultSet.next()){
				scheduleList.add(new ScheduleVO(resultSet.getDate(Constants.START_DATE), resultSet.getDate(Constants.END_DATE), 
						resultSet.getTime(Constants.START_TIME), resultSet.getTime(Constants.END_TIME)));
			}
		}catch(SQLException sqlExp){
			System.out.println("SQLException getAllSchedule " + sqlExp);
		}catch(Exception exp){
			System.out.println("Exception in getAllSchedule " + exp);
		}finally{
			DBConnectionManager.close(connection, preparedStatement, resultSet);
		}
		System.out.println("scheduleList == " + scheduleList);
		return scheduleList;
	}

	/**
	 * This method checks if schedule already exists or not
	 * 
	 * @param connection
	 *            - connection to create prepared statement
	 *
	 * @return true if schedule exists else false
	 */

	private boolean checkScheduleExists(Connection connection, ScheduleVO scheduleVo) {
		Boolean scheduleExists = false;
		int count =0;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			//prepare query to check if schedule exists
			preparedStatement = connection.prepareStatement(dbQueries.getProperty("check.schedule"));

			preparedStatement.setDate(1,new java.sql.Date(scheduleVo.getStartDate().getTime()));
			preparedStatement.setDate(2,new java.sql.Date(scheduleVo.getEndDate().getTime()));
			preparedStatement.setTime(3,scheduleVo.getStartTime());
			preparedStatement.setTime(4,scheduleVo.getEndTime());

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				count = resultSet.getInt(1);
			}

			System.out.println("count = " + count);
			if (count > 0) {
				scheduleExists = true;
			}
		}catch (Exception exp) {
			System.out.println("Error occured while checking if schedule exists or not" + exp);
		} finally {
			DBConnectionManager.close(null, preparedStatement, resultSet);
		}
		System.out.println("scheduleExists===== > " + scheduleExists);
		return scheduleExists;
	}

	/**
	 * This method checks if the course corresponding to the scheduleID exists
	 * 
	 * @param connection
	 *              -- connection to create prepared statement
	 * @ return
	 *           -true if the course exists for the scheduleID else False           
	 */
	private boolean checkScheduleOfCourseExists (Connection connection, ScheduleVO scheduleVo) {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		int scheduleID = 0;
		int count = 0;
		Boolean scheduleOfCourseExists = false;
		try {
			preparedStatement = connection.prepareStatement(dbQueries.getProperty("check.schedule.course"));
			preparedStatement.setInt(1, scheduleID);

			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				count = resultSet.getInt(1);  
			}
			System.out.println("count = " + count);
			if (count > 0) {
				scheduleOfCourseExists = true;
			}
		}catch (Exception exp) {
			System.out.println("Error : " + exp);
		} finally {
			DBConnectionManager.close(null, preparedStatement, resultSet);
		}
		System.out.println("scheduleOfCourseExists ===== > " + scheduleOfCourseExists);
		return scheduleOfCourseExists;
	}


	/**
	 * This method delete the schedule from schedule table
	 * 
	 * @param connection
	 *               - connection to create prepared statement
	 * @return
	 *           -Delete the schedule    
	 */

	public String deleteSchedule (ScheduleVO scheduleVo) {
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		ResultSet resultSet = null;

		String statusMessage = null;

			try {
				connection = DBConnectionManager.getConnection();
				connection.setAutoCommit(false);
				//Checks if the course for the scheduleID exists
				if (checkScheduleOfCourseExists (connection, scheduleVo)) {
					return "Schedule is associated with a course.. Cannot delete";
				}
				//get connection to DB
				preparedStatement = connection.prepareStatement(dbQueries.getProperty("drop.schedule"));
				preparedStatement.setInt(1, scheduleVo.getScheduleId());
				int result = preparedStatement.executeUpdate();
				if(result>0)
				statusMessage = Constants.SUCCESS;
				else
					statusMessage = Constants.FAILURE;
			}catch (Exception exp) {
				statusMessage = Constants.FAILURE;
				System.out.println("Error : " + exp);
				try {
					connection.rollback();
				} catch (SQLException e1) {
					System.out.println(statusMessage);
				}
			} finally {
				try {
					connection.setAutoCommit(true);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				DBConnectionManager.close(connection, preparedStatement, resultSet);
			}  
		return statusMessage;
	}

	/**
	 * This method updates schedule
	 * @param scheduleVo Object contains student details that has to be updated
	 * @return status message stating success or failure
	 */
	public String updateSchedule(ScheduleVO scheduleVO) {
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		String statusMessage = null;

		try {
			//get connection to DB
			connection = DBConnectionManager.getConnection();
			connection.setAutoCommit(false);

			//Prepare insert statement to insert schedule details using preparedStatement
			preparedStatement = connection.prepareStatement(dbQueries.getProperty("update.schedule"));

			int index=0;
			//set Schedule values
			preparedStatement.setDate(++index, scheduleVO.getStartDate());
			preparedStatement.setDate(++index, scheduleVO.getEndDate());
			preparedStatement.setTime(++index, scheduleVO.getStartTime());
			preparedStatement.setTime(++index, scheduleVO.getEndTime());
			preparedStatement.setInt(++index, scheduleVO.getScheduleId());
			System.out.println("Updating Schedule using DDL statement "+preparedStatement.toString());

			//execute the insert statement
			preparedStatement.executeUpdate();
			connection.commit();
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
			statusMessage = Constants.FAILURE;
			try {
				connection.rollback();
			} catch (SQLException e1) {
				System.out.println(statusMessage);
			}
		} finally {
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			DBConnectionManager.close(connection, preparedStatement, null);
		}
		System.out.println("statusMessage =====> " + statusMessage);
		return statusMessage;

	}

	/**
	 * Gets the course schedule
	 * @param connection
	 * @param courseId
	 * @return List<ScheduleVO>
	 */
	public List<ScheduleVO> getCourseSchedule(int courseId) {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<ScheduleVO> courseSchedule = null;
		Connection connection = null;

		try{
			connection = DBConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement(dbQueries.getProperty("course.get.schedule"));
			preparedStatement.setInt(1, courseId);
			resultSet = preparedStatement.executeQuery();
			courseSchedule =new ArrayList<ScheduleVO>();
			while(resultSet.next()){
				ScheduleVO scheduleVO = new ScheduleVO(resultSet.getDate(Constants.START_DATE), resultSet.getDate(Constants.END_DATE), resultSet.getTime(Constants.START_TIME), resultSet.getTime(Constants.END_TIME));
				courseSchedule.add(scheduleVO);
				System.out.println("Course Schedule for courseID: "+courseId+" "+scheduleVO.toString());
			}

		}catch(SQLException sqlExp){
			System.out.println("SQLException occurred in getCourseSchedule() " + sqlExp.getMessage());
		}catch(Exception exp){
			System.out.println("Exception occurred in getCourseSchedule() "+exp.getMessage());
		}finally{
			DBConnectionManager.close(connection, preparedStatement, resultSet);
		}
		return courseSchedule;
	}

	/**
	 * Adds the schedule for a course
	 * @param connection
	 * @param courseId
	 * @param courseSchList
	 * @return boolean
	 * @throws Exception
	 */

	public String addCourseSchedule(int courseId, List<ScheduleVO> scheduleVOs){
		PreparedStatement preparedStatement = null;
		String statusMessage = null;
		Connection connection = null;
		try{
			connection = DBConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement(dbQueries.getProperty("course.insert.schedule"));
			for(ScheduleVO scheduleVO : scheduleVOs){
				preparedStatement.setInt(1, courseId);
				preparedStatement.setInt(2, scheduleVO.getScheduleId());
				preparedStatement.addBatch();
			}
			int count[] = preparedStatement.executeBatch();
			if(count.length > 0)
				statusMessage = Constants.SUCCESS;
connection.commit();
		}catch(SQLException sqlExp){
			statusMessage = Constants.FAILURE;
			try {
				connection.rollback();
			} catch (SQLException e1) {
				System.out.println(statusMessage);
			}
			System.out.println("SQLException occurred in insertCourseSchedule() " + sqlExp.getMessage());
		}catch(Exception exp){
			statusMessage = Constants.FAILURE;
			try {
				connection.rollback();
			} catch (SQLException e1) {
				System.out.println(statusMessage);
			}
			System.out.println("Exception occurred in insertCourseSchedule() "+exp.getMessage());
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


}
