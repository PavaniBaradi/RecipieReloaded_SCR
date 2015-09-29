package com.scr.dao;

import java.util.List;
import java.util.Map;

import com.scr.vo.CourseInfoVO;
import com.scr.vo.CourseVO;

public interface CourseDAO {

	List<CourseInfoVO> listAllCourse();
	/**
	 * Lists all courses which are enabled
	 * @return List<CourseInfoVO> 
	 */

	Map<String, Object> getCourseDetails(int courseId);
	/**
	 * Gets details of the particular course.
	 * @param courseId
	 * @return Map<String, Object>
	 */
	
	String disableCourse(int courseId);
	/**
	 * Disables the course
	 * @param courseId
	 * @return String 
	 */
	
	String addCourse(CourseVO courseVo);
	/**
	 * Adds a course and all the corresponding information like schedule, curriculum, books.
	 * @param courseVo
	 * @return String
	 * @throws SQLException
	 */
	
	String updateCourseInfo(int courseId, CourseInfoVO courseInfoVo);
	/**
	 * Updates the Course information
	 * @param courseId,
	 * @param courseInfoVo
	 * @return String
	 */
	
	String updateCourseSchedule(int courseId,  List<Integer> courseSchList);
	/**
	 * Updates the course schedule
	  * @param courseId,
	 * @param courseSchList
	 * @return String
	 */
	
	String updateCourseCurriculum(int courseId, List<Integer> curriculumList);
	/**
	 * Updates the course curriculum
	 * @param courseId,
	 * @param curriculumList
	 * @return String
 	*/
	
	String updateCourseBook(int courseId, List<Integer> booksList);
	/**
	 * Updates the course book
	 * @param courseId,
	 * @param booksList
	 * @return String
	 */

}
