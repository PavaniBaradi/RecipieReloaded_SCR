/**
 * 
 */
package com.scr.client;

import java.util.List;
import java.util.Scanner;

import com.scr.dao.StudentsDAO;
import com.scr.dao.impl.StudentDAOImpl;
import com.scr.vo.StudentVO;

public class SCRMain {
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		StudentsDAO studentDAO = new StudentDAOImpl();
		StudentVO studentVO = null;
		String firstName = null;
		String password = null;
		String email = null;
		String lastName = null;
		String statusMessage = null;
		int id;

		System.out.println("========================== Welcome to Student Course Registratin System =======================");
		while (true) {
			System.out.println("Please Enter the Operation");
			System.out.println("1. Get");
			System.out.println("2. Add");
			System.out.println("3. Update");
			System.out.println("4. Delete");
			System.out.println("5. Exit");
			int choice = scanner.nextInt();
			switch (choice) {
				case 1:	
					getValuesfor();
				break;
				
				case 2:	
					addValuesfor();
					break;
				
				case 3:	
					updateValuesfor();
					break;
				case 4:	
					deleteValuesfor();
					break;
				
				default:	
					
					break;
			
			}
			
			if (choice == 5) {
				break;
			}
			
		}		
	}


	private static void getValuesfor() {
		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.println("Please select get object: ");
			System.out.println("1. Student");
			System.out.println("2. Course");
			System.out.println("3. Curriculum");
			System.out.println("4. Books");
			System.out.println("5. Main menu");
			System.out.println("Please enter student id [-1 for everything]: ");
			int choice = scanner.nextInt();
			switch (choice) {
				case -1:	
					getStudents();
				break;
				case 1:	
					getStudentInfo();
				break;
				
				case 2:	
					System.out.println("Please enter course id [-1 for everything]: ");
					getCourses();
				break;
				
				case 3:	
					System.out.println("Please enter curriculum id [-1 for everything]: ");
					getCurriculums();;
					
					break;
				case 4:	
					System.out.println("Please enter book id [-1 for everything]: ");
					getBooks();
					
					break;
					
				
				case 5:	
					break;
			
			}
			
			if (choice == 5) {
				break;
				
			}
		}
		
	}

	private static void addValuesfor() {
		Scanner scanner = new Scanner(System.in);
		int id ;
		while (true) {
			System.out.println("Please select add object: ");
			System.out.println("1. Student");
			System.out.println("2. Course");
			System.out.println("3. Curriculum");
			System.out.println("4. Books");
			System.out.println("5. Main menu");
			int choice = scanner.nextInt();
			switch (choice) {
				case 1:	
					System.out.println("Please enter student information to add: ");
					addStudent();
				break;
				
				case 2:	
					System.out.println("Please enter course information to add: ");
					addCourse();
				break;
				
				case 3:	
					System.out.println("Please enter curriculum information to add: ");
					addCurriculum();
					
					break;
				case 4:	
					System.out.println("Please enter book information to add: ");
					addBook();
			
					break;
					
				
				case 5:	
					break;
			
			}
			
			if (choice == 5) {
				break;
				
			}
		}
		
	}

	
	private static void updateValuesfor() {
		Scanner scanner = new Scanner(System.in);
		int id ;
		while (true) {
			System.out.println("Please enter delete object: ");
			System.out.println("1. Student");
			System.out.println("2. Course");
			System.out.println("3. Curriculum");
			System.out.println("4. Books");
			System.out.println("5. Main menu");
			int choice = scanner.nextInt();
			switch (choice) {
				case 1:	
					System.out.println("Please enter student information to delete: ");
					deleteStudent();
				break;
				
				case 2:	
					System.out.println("Please enter course information to delete: ");
					deleteCourse();
				break;
				
				case 3:	
					System.out.println("Please enter curriculum information to delete: ");
					deleteCurriculum();
					
					break;
				case 4:	
					System.out.println("Please enter book information to delete: ");
					deleteBook();
			
					break;
					
				
				default:	
					break;
			
			}
			
			if (choice == 5) {
				break;
				
			}
		}
		
	}

	
	private static void deleteValuesfor() {
		Scanner scanner = new Scanner(System.in);
		int id ;
		while (true) {
			System.out.println("Please delete object: ");
			System.out.println("1. Student");
			System.out.println("2. Course");
			System.out.println("3. Curriculum");
			System.out.println("4. Books");
			System.out.println("5. Main menu");
			int choice = scanner.nextInt();
			switch (choice) {
				case 1:	
					System.out.println("Please enter student information to update: ");
					updateStudent();
				break;
				
				case 2:	
					System.out.println("Please enter course information to update: ");
					updateCourse();
				break;
				
				case 3:	
					System.out.println("Please enter curriculum information to update: ");
					updateCurriculum();
					
					break;
				case 4:	
					System.out.println("Please enter book information to update: ");
					updateBook();
			
					break;
					
				
				default:	
					break;
			
			}
			
			if (choice == 5) {
				break;
				
			}
		}
		
	}


	private static void getStudents() {
		StudentsDAO studentDAO = new StudentDAOImpl();
		List<StudentVO> studentsList = studentDAO.getStudents();
		printList(studentsList);
		// TODO Auto-generated method stub
		
	}
	private static void getStudentInfo() {
		Scanner scanner = new Scanner(System.in);
		int id ;
		while (true) {
			System.out.println("Please Enter student emailid for student details and -1 for exit ");

			String email = scanner.nextLine();
			System.out.println(Integer.toString(-1));
			if (email.equals(Integer.toString(-1)))
				break;

			StudentsDAO studentDAO = new StudentDAOImpl();
			StudentVO studentVO = studentDAO.getStudentDetails(email);
			System.out.println(studentVO.toString());
		}

	}
	
	private static void getCourses() {
		// TODO Auto-generated method stub
		
	}
	
	private static void getCurriculums() {
		// TODO Auto-generated method stub
		
	}
	
	private static void getBooks() {
		// TODO Auto-generated method stub
		
	}
	
	private static void deleteStudent() {
		// TODO Auto-generated method stub
		
	}
	
	private static void deleteCourse() {
		// TODO Auto-generated method stub
		
	}
	
	private static void deleteCurriculum() {
		// TODO Auto-generated method stub
		
	}
	
	private static void deleteBook() {
		// TODO Auto-generated method stub
		
	}
	
	private static void updateStudent() {
		// TODO Auto-generated method stub
		
	}
	
	private static void updateCourse() {
		// TODO Auto-generated method stub
		
	}
	
	private static void updateCurriculum() {
		// TODO Auto-generated method stub
		
	}
	
	private static void updateBook() {
		// TODO Auto-generated method stub
		
	}
	
	private static void addStudent() {
		// TODO Auto-generated method stub
		
	}
	
	private static void addCourse() {
		// TODO Auto-generated method stub
		
	}
	
	private static void addCurriculum() {
		// TODO Auto-generated method stub
		
	}
	
	private static void addBook() {
		// TODO Auto-generated method stub
		
	}
	
	private static void printList(List list){
		for (Object object : list) {
			System.out.println("List: "+object.toString());
		}
	}


}
