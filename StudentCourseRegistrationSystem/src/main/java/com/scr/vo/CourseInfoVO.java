package com.scr.vo;

public class CourseInfoVO {
	private int courseId;
	private String courseName;
	private int courseAmount;
	private String courseDesc;
	
	public CourseInfoVO(int courseId, String courseName, int courseAmount, String courseDesc){
		this.courseId = courseId;
		this.courseName = courseName;
		this.courseAmount = courseAmount;
		this.courseDesc = courseDesc;
	}

	public int getCourseId() {
		return courseId;
	}
	
	public String getCourseName() {
		return courseName;
	}
	
	public int getCourseAmount() {
		return courseAmount;
	}

	public String getCourseDesc() {
		return courseDesc;
	}
}
