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
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	private Connection connection = null;


	/**
	 * This method adds schedule details to Schedule Table
	 * 
	 * @param scheduleVo
	 *            -scheduleVo contains schedule details which needs
	 *            to be persisted
	 * @return statusMessage specifying success or failure 
	 */
	public String addSchedule(ScheduleVO scheduleVo){

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
			System.out.println("Inserting schedule using DDL statement "+preparedStatement.toString());

			//execute the insert statement
			int count = preparedStatement.executeUpdate();
			connection.commit();
			if(count>0)
				statusMessage = "successfully added the schedule";

		} catch (Exception e) {
			e.printStackTrace();
			statusMessage="An error occurred while adding the schedule";
		}finally{
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
	 * This method gets the scheduleID for the schedule
	 * 
	 * @param connection
	 *              -- connection to create prepared statement
	 * @return
	 *              -scheduleID             
	 */
	private int getScheduleID (Connection connection, ScheduleVO scheduleVo) {
		int scheduleID = 0;
		try {

			preparedStatement = connection.prepareStatement(dbQueries.getProperty("fetch.schedule.id"));
			preparedStatement.setDate(1,new java.sql.Date(scheduleVo.getStartDate().getTime()));
			preparedStatement.setDate(2,new java.sql.Date(scheduleVo.getEndDate().getTime()));
			preparedStatement.setTime(3,scheduleVo.getStartTime());
			preparedStatement.setTime(4,scheduleVo.getEndTime());

			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) 
				scheduleID = resultSet.getInt("schedule_id");  
		}  catch (Exception exp) {
			System.out.println("Error : " + exp);
		} finally {
			DBConnectionManager.close(null, preparedStatement, resultSet);
		}
		return scheduleID;  
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
		int scheduleID = 0;
		int count = 0;
		Boolean scheduleOfCourseExists = false;
		if (checkScheduleExists(connection, scheduleVo)) {
			scheduleID = getScheduleID (connection, scheduleVo);  
		} else {
			return false;
		}
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

	public String deleteSchedule (Connection connection, ScheduleVO scheduleVo) {

		int scheduleID = 0;
		String returnMessage = "";
		//Checks if the schedule for the given scheduleID exists
		if (checkScheduleExists(connection, scheduleVo)) {
			scheduleID = getScheduleID (connection, scheduleVo);  
		} else {
			return "No such schedule exists";
		}

		//Checks if the course for the scheduleID exists
		if (checkScheduleOfCourseExists (connection, scheduleVo)) {
			return "Schedule is associated with a course.. Cannot delete";
		} else {
			try {
				//get connection to DB
				preparedStatement = connection.prepareStatement(dbQueries.getProperty("drop.schedule"));
				preparedStatement.setInt(1, scheduleID);
				resultSet = preparedStatement.executeQuery();

				returnMessage = "Schedule Deleted";
			}catch (Exception exp) {
				System.out.println("Error : " + exp);
			} finally {
				DBConnectionManager.close(connection, preparedStatement, resultSet);
			}  
		}
		return returnMessage;
	}

	/**
	 * This method updates schedule
	 * @param scheduleVo Object contains student details that has to be updated
	 * @return status message stating success or failure
	 */
	public String updateSchedule(ScheduleVO scheduleVo, ScheduleVO newScheduleVO) {
		int scheduleID = 0;
		String statusMessage = null;

		try {
			//get connection to DB
			connection = DBConnectionManager.getConnection();
			connection.setAutoCommit(false);

			scheduleID = getScheduleID (connection, scheduleVo);

			//Prepare insert statement to insert schedule details using preparedStatement
			preparedStatement = connection.prepareStatement(dbQueries.getProperty("update.schedule"));

			int index=0;
			//set Schedule values
			preparedStatement.setDate(++index, newScheduleVO.getStartDate());
			preparedStatement.setDate(++index, newScheduleVO.getEndDate());
			preparedStatement.setTime(++index, newScheduleVO.getStartTime());
			preparedStatement.setTime(++index, newScheduleVO.getEndTime());
			preparedStatement.setInt(++index, scheduleID);
			System.out.println("Updating Schedule using DDL statement "+preparedStatement.toString());

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


}
