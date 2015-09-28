/**
 * 
 */
package com.scr.client;

import java.util.Scanner;

import com.scr.dao.StudentsDAO;
import com.scr.dao.impl.StudentDAOImpl;
import com.scr.util.Constants;
import com.scr.vo.StudentVO;

public class SCRMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		StudentsDAO studentDAO = new StudentDAOImpl();
		StudentVO studentVO = null;
		String firstName = null;
		String password = null;
		String email = null;
		String lastName = null;
		String statusMessage = null;

		System.out.println("========================== Welcome to Student Course Registratin System =======================");
		System.out.println("Are you a new Student? or Existing Student? Select one of the following.");
		System.out.println("1. New Student");
		System.out.println("2. Existing Student ");
		System.out.println("3. Admin "+scanner.next());
		switch (Integer.parseInt(args[0])) {
		case 1:	
			System.out.println("Enter your details to signup");
			System.out.println("First Name");
			firstName = scanner.next();
			System.out.println("Last Name");
			lastName = scanner.next();
			System.out.println("Email id");
			email = scanner.next();
			System.out.println("Password");
			password = scanner.next();
			studentVO = new StudentVO(0, firstName, lastName, email, password, Constants.USER_STUDENT);
			statusMessage = studentDAO.createStudent(studentVO);
			if(statusMessage.equals(Constants.SUCCESS)){
				System.out.println("Student registered succesfully");
			}else
				System.out.println("Cannot create student with email id"+email);
			break;
		case 2:
			System.out.println("Enter login credentials");
			System.out.println("Email ID: ");
			email = scanner.next();
			System.out.println("Password: ");
			password = scanner.next();
			studentVO = studentDAO.login(email, password);
			if(studentVO!=null)
				System.out.println("Student successfully logged in "+studentVO.toString());
		case 3:
			System.out.println("Enter login credentials");
			System.out.println("Email ID: ");
			email = scanner.next();
			System.out.println("Password: ");
			password = scanner.next();
			studentVO = studentDAO.login(email, password);
			if(studentVO!=null && studentVO.getUserFlag().equals(Constants.USER_ADMIN))
				System.out.println("Admin successfully logged in "+studentVO.toString());
			else
				System.out.println("Admin with userid "+email +" doesnot exist");
		default:
			break;
		}



		scanner.close();
	}

}
