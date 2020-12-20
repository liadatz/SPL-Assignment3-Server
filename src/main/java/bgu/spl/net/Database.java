package bgu.spl.net;


import bgu.spl.net.impl.passiveObjects.Admin;
import bgu.spl.net.impl.passiveObjects.Course;
import bgu.spl.net.impl.passiveObjects.Student;
import bgu.spl.net.impl.passiveObjects.User;

import java.util.ArrayList;
import java.util.List;
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



	//to prevent user from creating new Database
	private Database() {
		StudentsMap = new ConcurrentHashMap<>();
		AdminsMap = new ConcurrentHashMap<>();
		CoursesMap = new ConcurrentHashMap<>();
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
		// TODO: implement
		return false;
	}

	private boolean isRegistered(String userName, boolean isAdmin) {
		if (isAdmin) return AdminsMap.containsKey(userName);
		else return StudentsMap.containsKey(userName);
	}

	private boolean isValidPassword(String userName, String Password, boolean isAdmin) {
		String toCompare;
		if (isAdmin) toCompare = AdminsMap.get(userName).getPassword();
		else toCompare = StudentsMap.get(userName).getPassword();
		return Password.equals(toCompare);
	}

	private boolean isExist(int numOfCourse) {
		return CoursesMap.containsKey(numOfCourse);
	}

	private boolean isRoomAvailable(int numOfCourse) {
		return CoursesMap.get(numOfCourse).isRoomAvailable();
	}

	private boolean isKdamDone(String userName, int numOfCourse) {
		List<Course> KdamList = CoursesMap.get(numOfCourse).getKdamCoursesList();
		Student currentStudent = StudentsMap.get(userName);
		boolean result = true;
		for (Course course : KdamList) {
			result = currentStudent.checkCourse(course);
			if (!result) break;
		}
		return result;
	}

	private boolean isAdmin(User user) {
		return user instanceof Admin;
	}

	private void register(String userName, String Password, boolean isAdmin) {
		if (isAdmin) {
			Admin newAdmin = new Admin(userName, Password);
			AdminsMap.put(userName, newAdmin);
		}
		else {
			Student newStudent = new Student(userName, Password, new ArrayList<>()); // maybe change arraylist
			StudentsMap.put(userName, newStudent);
		}
	}

	private void courseRegister(String userName, int numOfCourse) {
		Course currentCourse = CoursesMap.get(numOfCourse);
		StudentsMap.get(userName).addCourse(currentCourse);
	}

	private String KdamCheck(int numOfCourse) {
		return CoursesMap.get(numOfCourse).getKdamCoursesList().toString();
	}

	private String ComposeCourseStat(int numOfCourse) {
		String output;
		Course currentCourse = CoursesMap.get(numOfCourse);
		output = "Course: (" + numOfCourse + ") " + currentCourse.getCourseName() + "/n"
				+ currentCourse.getNumOfRegistered() + "/" + currentCourse.getNumOfMaxStudents() + "/n";
		// add Student registered list
		return output;
	}

	private String ComposeStudentStat(String userName) {
		Student currentStudent = StudentsMap.get(userName);
		return  "Student: " + currentStudent.getUsername() + "/n" + currentStudent.getCourses().toString();
	}

	private boolean courseCheck(String userName, int numOfCourse) {
		Course course = CoursesMap.get(numOfCourse);
		return StudentsMap.get(userName).checkCourse(course);
	}

	private void unregister(String userName, int numOfCourse) {
		Course course = CoursesMap.get(numOfCourse);
		StudentsMap.get(userName).removeCourse(course);
	}

	private String myCourses(String userName) {
		return StudentsMap.get(userName).getCourses().toString();
	}
}
