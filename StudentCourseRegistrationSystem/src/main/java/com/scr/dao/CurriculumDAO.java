package com.scr.dao;

import java.util.List;
import java.util.Map;


public interface CurriculumDAO {

	/**
	 * This method adds curriculam details to CURRICULAM table
	 * 
	 * @param CurriculamVO
	 * 				CurriculamVO consists of curriculam details which need to be persisted
	 *
*/					
	
	//public boolean insertCourseCurriculum(int courseId, List<Integer> curriculumList);
	
	public Map<Integer,String> getAllCurriculum();
	/**
	 * This method lists all the curriculum details
	 * @param Topic_Name
	 * @return map
	 */
	
	public Map<Integer,String> getCurriculum(String Topic_Name);
	/**
	 * This method lists the curriculum details from the curriculum table
	 * @param Topic Name
	 * @return map
	 */
	
	public List<String> getCourseCurriculum(int courseID);
	
	/**
	 * This method lists the curriculum details related to the particular courseID
	 * @param courseID
	 * 	@return list of the topic name related to the particular course ID
	*/

	public boolean addCourseCurriculum(int courseId, List<Integer> curriculumList);
	/**
	 * Inserts courseId and curriculum_id into course_curriculum table
	 * @param courseId
	 * @param curriculumList
	 * @return  statusmessage
	 */
	
	public String addCurriculum(int curriculumId, String topicName);
	/** 
	 * This method inserts data into Curriculum table
	 * @param curriculumId
	 * @param topicName
	 * @return statusmessage
	 */
	
	

	public String updateCurriculum(int curriculumId, String topicName);
	/**
	 * Updates the data in Curriculum table
	 * @param curriculumId
	 * @param topicName
	 * @return String
	 */

	
	public void deleteCurriculum(int curriculumId);
	/**
	 * deletes the data based on curriculumId in Curriculum table
	 * @param curriculumId
	 */
	

}


