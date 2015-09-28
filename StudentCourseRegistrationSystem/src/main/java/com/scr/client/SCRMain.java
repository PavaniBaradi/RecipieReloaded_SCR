/**
 * 
 */
package com.scr.client;

import java.util.List;

import com.scr.dao.StudentsDAO;
import com.scr.dao.impl.StudentDAOImpl;
import com.scr.vo.StudentVO;

/**
 * @author pavanibaradi
 *
 */
public class SCRMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Jar Created");
		StudentsDAO studentDAO = new StudentDAOImpl();
		List<StudentVO> studentsList = studentDAO.getStudents();
		System.out.println("list" +studentsList);
	}

}
