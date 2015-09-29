package com.scr.dao;

import java.util.List;

import com.scr.vo.BookVO;
import com.scr.vo.CoursesVO;
import com.scr.vo.CurriculumVO;
import com.scr.vo.ScheduleVO;

public interface CourseDAO {

	public List<CoursesVO> listAllCourse();

	public CoursesVO getCourseDetails(int courseId);
	
	public String disableCourse(int courseId);
	
	public String enableCourse(int courseId);
	
	public String addCourse(CoursesVO courseVo);
	
	public String updateCourseInfo(CoursesVO courseVO);
	
	public String updateCourseSchedule(int courseId,  List<ScheduleVO> courseSchList);
	
	public String updateCourseCurriculum(int courseId, List<CurriculumVO> curriculumList);
	
	public String updateCourseBook(int courseId, List<BookVO> booksList);

}