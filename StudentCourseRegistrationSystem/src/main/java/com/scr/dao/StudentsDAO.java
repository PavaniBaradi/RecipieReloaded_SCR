/**
 * 
 */
package com.scr.dao;

import java.util.List;

import com.scr.vo.StudentVO;

/**
 * @author pavanibaradi
 *
 */
public interface StudentsDAO {

	/**
	 * This method adds student details to STUDENT table
	 * 
	 * @param studentVO
	 *            - studentVO contains student details which needs to be
	 *            persisted
	 * @return - returns status message
	 */
	public String createStudent(StudentVO studentVO);
	
	/**
	 * This method updates student details
	 * @param studentVO
	 */
	public String updateStudent(StudentVO studentVO);
	
	/**
	 * This method deletes student's record from STUDENTS table
	 * @param studentVO
	 * @return status message stating success or failure
	 */
	public String deleteStudent(StudentVO studentVO);
	
	
	/**
	 * This method returns all the students details
	 * @return list of students
	 */
	public List<StudentVO> getStudents();
	
	
	/**
	 * This method a student's details
	 * @param studentVO
	 * @return studentVO object 
	 */
	public StudentVO getStudentDetails(String email);
	
	
	/**
	 * This method lists course details which are enrolled by a student
	 * @param email
	 * @return studentVO contains student details and course details
	 */
	public StudentVO getStudentEnrolledCourses(String email);


	/**
	 * This method checks if student has already enrolled for a course or not.
	 * If not student will be enrolled for a course
	 * 
	 * @param email
	 * @param courseId
	 * @param scheduleId
	 * @return status message
	 */
	public String enrollCourse(String email, int courseId, int scheduleId);
	
	/**
	 * This method drops course to which student enrolled earlier
	 * 
	 * @param email
	 * @param courseId
	 * @param scheduleId
	 * @return status message
	 */
	public String dropCourse(String email, int courseId, int scheduleId);


}
