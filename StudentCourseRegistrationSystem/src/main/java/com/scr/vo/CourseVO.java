package com.scr.vo;

import java.util.List;

public class CourseVO {

	private CourseInfoVO courseInfo;
	private List<Integer> courseSchList;
	private List<Integer> curriculumList;
	private List<Integer> booksList;
	
	public CourseVO(CourseInfoVO courseInfo, List<Integer> courseSchList, List<Integer> curriculumList,	List<Integer> booksList) {
		this.courseInfo = courseInfo;
		this.courseSchList = courseSchList;
		this.curriculumList = curriculumList;
		this.booksList = booksList;
	}
	
	public CourseInfoVO getCourseInfo() {
		return courseInfo;
	}

	public List<Integer> getCourseSchList() {
		return courseSchList;
	}

	public List<Integer> getCurriculumList() {
		return curriculumList;
	}
	public List<Integer> getBooksList() {
		return booksList;
	}

}
