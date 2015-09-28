/**
 * 
 */
package com.scr.dao;
import java.sql.Connection;
import java.util.List;

import com.scr.vo.ScheduleVO;



public interface ScheduleDAO {

	/**
	 * This method adds schedule details to Schedule Table
	 * 
	 * @param scheduleVo
	 *            -scheduleVo contains schedule details which needs
	 *            to be persisted
	 * @return statusMessage specifying success or failure 
	 */
	public String addSchedule(ScheduleVO scheduleVo);


	/**
	 * This method gives the list of all the schedules available 
	 *                                          in schedule table.
	 *                                          
	 * @return List of schedule	
	 */
	public List<ScheduleVO> getAllSchedule();



	/**
	 * This method delete the schedule from schedule table
	 * 
	 * @param connection
	 *               - connection to create prepared statement
	 * @return
	 *           -Message to confirm if the schedule is deleted or not   
	 */
	public String deleteSchedule (Connection connection, ScheduleVO scheduleVo); 


	/**
	 * This method updates schedule
	 * @param scheduleVo Object contains student details that has to be updated
	 * @return status message stating success or failure
	 */
	public String updateSchedule(ScheduleVO scheduleVo, ScheduleVO newScheduleVO) ;



}
