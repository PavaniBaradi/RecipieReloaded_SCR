package com.scr.dao;

import java.util.List;
import java.util.Map;

import com.scr.vo.CourseInfoVO;
import com.scr.vo.CourseVO;

public interface CourseDAO {

	List<CourseInfoVO> listAllCourse();

	Map<String, Object> getCourseDetails(int courseId);
	
	String disableCourse(int courseId);
	
	String addCourse(CourseVO courseVo);
	
	String updateCourseInfo(int courseId, CourseInfoVO courseInfoVo);
	
	String updateCourseSchedule(int courseId,  List<Integer> courseSchList);
	
	String updateCourseCurriculum(int courseId, List<Integer> curriculumList);
	
	String updateCourseBook(int courseId, List<Integer> booksList);

}