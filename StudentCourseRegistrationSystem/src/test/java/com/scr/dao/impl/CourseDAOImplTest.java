package com.scr.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.scr.dao.CourseDAO;
import com.scr.dao.impl.CourseDAOImpl;
import com.scr.vo.CourseInfoVO;
import com.scr.vo.CourseVO;
import com.scr.vo.ScheduleVO;

public class CourseDAOImplTest {
  
	private CourseDAO courseDAOObj = null;
	@BeforeClass
	public void beforeClass(){
		courseDAOObj = new CourseDAOImpl();
	}
	
	 @Test (enabled=false)
	  public void getAllCourses_Test() {
		  List<CourseInfoVO> courseInfoList = courseDAOObj.listAllCourse();
		  
		  for(CourseInfoVO courseInfo : courseInfoList){
			  System.out.println("CourseID : "+ courseInfo.getCourseId() + " - CourseName :" + courseInfo.getCourseName() + " - CourseDesc : " + courseInfo.getCourseDesc() + " - CourseAmount :" + courseInfo.getCourseAmount());
		  }
	  }
	 
	 @DataProvider
	  public Object[][] getCourseDetails_dp() {
	    return new Object[][] {
	      new Object[] { 1},
	      new Object[] { 2},
	    };
	  }
	  
	  @Test(dataProvider="getCourseDetails_dp", enabled=false)
	  public void getCourseDetails_Test(int courseID) {
		  Map<String, Object> courseDetailsMap = courseDAOObj.getCourseDetails(courseID);
		  System.out.println("courseDetailsMap = " + courseDetailsMap);
		  
		  List<ScheduleVO> courseSchedule =  (List<ScheduleVO>) courseDetailsMap.get("courseSchedule");
		  
		  for(ScheduleVO scVo :courseSchedule){
			  System.out.println("StartDate : " + scVo.getStartDate() + " - StartTime : " + scVo.getStartTime());
		  }
	  }
	  

	  
	  @DataProvider
	  public Object[][] addCourse_dp() {
		
		  SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		 
		  //Test Case 1
		  String courseName1 = "First_Test_Course_Name_"+ new Random().nextInt(1000);
		  CourseInfoVO courseInfoVo1 = new CourseInfoVO(0, courseName1, 10011, "Added this course from unit test @ "+ sdf.format(new Date()));
		  List<Integer> courseSchList1 = new ArrayList<Integer>(Arrays.asList(5, 6));
		  List<Integer> currList1 = new ArrayList<Integer>(Arrays.asList(2, 3));
		  List<Integer> bookList1 = new ArrayList<Integer>(Arrays.asList(1, 2));
		
		  CourseVO CourseVo1 = new CourseVO(courseInfoVo1, courseSchList1, currList1, bookList1);
		  
		  //Test case 2
		  String courseName2 = "Second_Test_Course_Name_"+ new Random().nextInt(1000);
		  CourseInfoVO courseInfoVo2 = new CourseInfoVO(0, courseName2, 20022, "Added this course from unit test @ "+ sdf.format(new Date()));
		  List<Integer> courseSchList2 = new ArrayList<Integer>(Arrays.asList(1, 4, 5));
		  List<Integer> currList2 = new ArrayList<Integer>(Arrays.asList(2, 3)); 
		  List<Integer> bookList2 = new ArrayList<Integer>();  // Not adding any books here which will return status as false - transaction rollback
		  
		  CourseVO CourseVo2 = new CourseVO(courseInfoVo2, courseSchList2, currList2, bookList2);
		  
	    return new Object[][] {
	      new Object[] {CourseVo1, "Successfully created course : "+courseName1},
	      new Object[] {CourseVo2, "Unable to create the course due to some issues"}
	    };
	  }
	  
	  
	  @Test(dataProvider="addCourse_dp", enabled=false)
	  public void addCourse_Test(CourseVO addCourseVo1, String expectedMessage){
		  
		  String actualStatusMessage= courseDAOObj.addCourse(addCourseVo1);
	
		Assert.assertEquals(actualStatusMessage, expectedMessage);
	  }
	  
	  @DataProvider
	  public Object[][] disableCourse_dp() {
			int courseId1 = 21;
			int courseId2 = 200000;
	  	
	    return new Object[][] {
	    	//Success case - where courseId 1 exists
	      new Object[] {courseId1, "successfully disabled the course : "+courseId1},
	       //CourseId doesn't exists - no rows updated 
	      new Object[] {courseId2, "No course with courseId : "+courseId2 + " available to disable"},
	    };
	  }
	  
	  @Test(dataProvider="disableCourse_dp", enabled=false)
	  public void disableCourse_Test(int  courseId, String expectedStatusMessage){
		  
		  String actualStatusMessage = courseDAOObj.disableCourse(courseId);
		  Assert.assertEquals(actualStatusMessage, expectedStatusMessage);
	  }
	  
	  @DataProvider
	  public Object[][] updateCourseInfo_dp() {
		  
		  String courseName1 = "First_Test_Course_Name_"+ new Random().nextInt(1000);
		  SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		 
		  CourseInfoVO CourseInfoVo1 = new CourseInfoVO(0, courseName1, 1999, "Updated course Info from unit test @ "+ sdf.format(new Date()));
		  
	    return new Object[][] {
	      new Object[] {1,CourseInfoVo1, "Successfully updated the details"},
	    //  new Object[] {addCourseVo2, "Unable to create the course due to some issues"}
	    };
	  }
	  
	  
	  @Test(dataProvider="updateCourseInfo_dp", enabled=false)
	  public void updateCourseInfo_Test(int courseID, CourseInfoVO CourseInfoVo1, String expectedMessage){
		  
		  String actualStatusMessage= courseDAOObj.updateCourseInfo(courseID,CourseInfoVo1);
	
		  Assert.assertEquals(actualStatusMessage, expectedMessage);
	  }
	  
	  @DataProvider
	  public Object[][] updateCourseSchedule_dp() {
		List<Integer> courseSchList1 = new ArrayList<Integer>(Arrays.asList(1, 2));
		  
	    return new Object[][] {
	      new Object[] {13, courseSchList1, "Successfully updated the details"},
	    //  new Object[] {addCourseVo2, "Unable to create the course due to some issues"}
	    };
	  }
	  
	  
	  @Test(dataProvider="updateCourseSchedule_dp", enabled=false)
	  public void updateCourseSchedule_Test(int courseID, List<Integer> courseSchList, String expectedMessage){
		 
		  String actualStatusMessage= courseDAOObj.updateCourseSchedule(courseID, courseSchList);
		  Assert.assertEquals(actualStatusMessage, expectedMessage);
	  }
	  
	  @DataProvider
	  public Object[][] updateCourseCurriculum_dp() {
		List<Integer> courseCurrList1 = new ArrayList<Integer>(Arrays.asList(2, 3));
		  
	    return new Object[][] {
	      new Object[] {13, courseCurrList1, "Successfully updated the details"},
	    //  new Object[] {addCourseVo2, "Unable to create the course due to some issues"}
	    };
	  }
	  
	  
	  @Test(dataProvider="updateCourseCurriculum_dp", enabled=false)
	  public void updateCourseCurriculum_Test(int courseID, List<Integer> courseCurrList, String expectedMessage){
		 
		  String actualStatusMessage= courseDAOObj.updateCourseCurriculum(courseID, courseCurrList);
		  Assert.assertEquals(actualStatusMessage, expectedMessage);
	  }
	  
	  @DataProvider
	  public Object[][] updateCourseBook_dp() {
		List<Integer> courseBookList1 = new ArrayList<Integer>(Arrays.asList(1, 3));
		  
	    return new Object[][] {
	      new Object[] {13, courseBookList1, "Successfully updated the details"},
	    //  new Object[] {addCourseVo2, "Unable to create the course due to some issues"}
	    };
	  }
	  
	  
	  @Test(dataProvider="updateCourseBook_dp", enabled=false)
	  public void updateCourseBook_Test(int courseID, List<Integer> courseSchList, String expectedMessage){
		 
		  String actualStatusMessage= courseDAOObj.updateCourseBook(courseID, courseSchList);
		  Assert.assertEquals(actualStatusMessage, expectedMessage);
	  }
	  
}
