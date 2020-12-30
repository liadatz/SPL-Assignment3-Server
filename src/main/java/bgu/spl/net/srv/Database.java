package bgu.spl.net.srv;


import bgu.spl.net.impl.passiveObjects.Admin;
import bgu.spl.net.impl.passiveObjects.Course;
import bgu.spl.net.impl.passiveObjects.Student;
import bgu.spl.net.impl.passiveObjects.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Passive object representing the Database where all courses and users are stored.
 * <p>
 * This class must be implemented safely as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You can add private fields and methods to this class as you see fit.
 */
public class Database {
	private static class SingletonHolder {
		private static Database instance = new Database();
	}

	private ConcurrentHashMap<String, Student> StudentsMap;
	private ConcurrentHashMap<String, Admin> AdminsMap;
	private ConcurrentHashMap<Integer, Course> CoursesMap;
	private ConcurrentHashMap<String, User> LoggedInMap;

	//to prevent user from creating new Database
	private Database() {
		StudentsMap = new ConcurrentHashMap<>();
		AdminsMap = new ConcurrentHashMap<>();
		CoursesMap = new ConcurrentHashMap<>();
		LoggedInMap = new ConcurrentHashMap<>();
	}

	/**
	 * Retrieves the single instance of this class.
	 */
	public static Database getInstance() {
		return SingletonHolder.instance;
	}
	
	/**
	 * loades the courses from the file path specified 
	 * into the Database, returns true if successful.
	 */
	boolean initialize(String coursesFilePath) {
		// read file from Path
		File file = new File(coursesFilePath);
		StringBuilder Kdam = new StringBuilder();
		try {
			Scanner sc = new Scanner(file);
			while(sc.hasNextLine()) {
				String[] currentLine = sc.nextLine().split("[|]");
				Course newCourse = new Course(Short.parseShort(currentLine[0]), currentLine[1], new ArrayList<>(), Integer.parseInt(currentLine[3]));
				Kdam.append(currentLine[0]).append("|").append(currentLine[2], 1, currentLine[2].length() - 1).append("|");
				CoursesMap.put(Integer.parseInt(currentLine[0]), newCourse);
			}
		}
		catch (FileNotFoundException e) {
			return false;
		}
		// add Kdam Course for each course
		String[] couresesAndKdams = Kdam.toString().split("[|]");
		for (int i = 0; i < couresesAndKdams.length; i = i + 2) {
			Course currentCourse = CoursesMap.get(Integer.parseInt(couresesAndKdams[i]));
			String[] Kdams = couresesAndKdams[i+1].split(",");
			for (int j = 0; j < Kdams.length && !Kdams[j].equals(""); j++) {
				currentCourse.addKdamCourse(CoursesMap.get(Integer.parseInt(Kdams[j])));
			}
		}
		return true;
	}

	public boolean isRegistered(String userName) {
		return AdminsMap.containsKey(userName) || StudentsMap.containsKey(userName);
	}

	public boolean isValidPassword(String userName, String Password) {
		String toCompare;
		if (AdminsMap.containsKey(userName)) toCompare = AdminsMap.get(userName).getPassword();
		else toCompare = StudentsMap.get(userName).getPassword();
		return Password.equals(toCompare);
	}

	public boolean isCourseExist(int numOfCourse) {
		return CoursesMap.containsKey(numOfCourse);
	}

	public boolean isRoomAvailable(int numOfCourse) {
		return CoursesMap.get(numOfCourse).isRoomAvailable();
	}

	public boolean isKdamDone(String userName, int numOfCourse) {
		List<Course> KdamList = CoursesMap.get(numOfCourse).getKdamCoursesList();
		Student currentStudent = StudentsMap.get(userName);
		boolean result = true;
		for (Course course : KdamList) {
			result = currentStudent.checkCourse(course);
			if (!result) break;
		}
		return result;
	}

//	public boolean isAdmin(String username) {
//		return user instanceof Admin;
//	}

	public void register(String userName, String Password, boolean isAdmin) {
		if (isAdmin) {
			Admin newAdmin = new Admin(userName, Password);
			AdminsMap.put(userName, newAdmin);
		}
		else {
			Student newStudent = new Student(userName, Password, new ArrayList<Course>()); // maybe change arraylist
			StudentsMap.put(userName, newStudent);
		}
	}

	public void courseRegister(String userName, int numOfCourse) {
		Course currentCourse = CoursesMap.get(numOfCourse);
		StudentsMap.get(userName).addCourse(currentCourse);
		currentCourse.increaseNumOfRegistered();
	}

	public String KdamCheck(int numOfCourse) {
		return CoursesMap.get(numOfCourse).getKdamCoursesList().toString();
	}

	// ADMIN TODO: erase spaces
	public String ComposeCourseStat(int numOfCourse) {
		String output;
		Course currentCourse = CoursesMap.get(numOfCourse);
		output = "Course: (" + numOfCourse + ") " + currentCourse.getCourseName() + "\n"
				+ currentCourse.getNumOfRegistered() + "/" + currentCourse.getNumOfMaxStudents() + "\n";
		output = output + getStudentsRegisteredList(currentCourse);
		return output;
	}

	// ADMIN TODO: erase spaces
	public String ComposeStudentStat(String userName) {
		Student currentStudent = StudentsMap.get(userName);
		return  "Student: " + currentStudent.getUsername() + "\n" + "Courses: " + currentStudent.getCourses().toString();
	}

	public String courseCheck(String userName, int numOfCourse) {
		Course course = CoursesMap.get(numOfCourse);
		if (StudentsMap.get(userName).checkCourse(course)) return "REGISTERED";
		else return "NOT REGISTERED";
	}

	public void unregister(String userName, int numOfCourse) {
		Course course = CoursesMap.get(numOfCourse);
		StudentsMap.get(userName).removeCourse(course);
	}

	// need to check how to print in a specific order
	public String myCourses(String userName) {
		return StudentsMap.get(userName).getCourses().toString();
	}

	public boolean logIn(String username){
		if (AdminsMap.containsKey(username)) {
			Admin admin = AdminsMap.get(username);
			LoggedInMap.put(username, admin);
			return true;
		}
		else {
			Student student = StudentsMap.get(username);
			LoggedInMap.put(username, student);
			return false;
		}
	}

	public void logOut(String username){
		LoggedInMap.remove(username);
	}

	public boolean isLoggedIn(String username){
		return LoggedInMap.containsKey(username);
	}

	private String getStudentsRegisteredList(Course courseToCheck) {
		ArrayList<String> registeredStudents = new ArrayList<>();
		for (Student student : StudentsMap.values()) {
			if (student.checkCourse(courseToCheck)) registeredStudents.add(student.getUsername());
		}
		registeredStudents.sort(Comparator.naturalOrder());
		return registeredStudents.toString();
	}

	// for testing purpose
	public void clear(){
		StudentsMap = new ConcurrentHashMap<>();
		AdminsMap = new ConcurrentHashMap<>();
		CoursesMap = new ConcurrentHashMap<>();
	}
}
