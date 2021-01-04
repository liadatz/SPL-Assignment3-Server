package bgu.spl.net.impl.BGRSServer;


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

	private final ConcurrentHashMap<String, Student> StudentsMap;
	private final ConcurrentHashMap<String, Admin> AdminsMap;
	private final ConcurrentHashMap<Short, Course> CoursesMap;
	private final ConcurrentHashMap<String, User> LoggedInMap;
	private final ArrayList<Short> CoursesList;

	//to prevent user from creating new Database
	private Database() {
		StudentsMap = new ConcurrentHashMap<>();
		AdminsMap = new ConcurrentHashMap<>();
		CoursesMap = new ConcurrentHashMap<>();
		LoggedInMap = new ConcurrentHashMap<>();
		CoursesList = new ArrayList<>();
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
				CoursesMap.put(Short.parseShort(currentLine[0]), newCourse);
				CoursesList.add(Short.parseShort(currentLine[0]));
			}
		}
		catch (FileNotFoundException e) {
			return false;
		}
		// add Kdam Course for each course
		String[] couresesAndKdams = Kdam.toString().split("[|]");
		for (int i = 0; i < couresesAndKdams.length; i = i + 2) {
			Course currentCourse = CoursesMap.get(Short.parseShort(couresesAndKdams[i]));
			String[] Kdams = couresesAndKdams[i+1].split(",");
			for (int j = 0; j < Kdams.length && !Kdams[j].equals(""); j++) {
				currentCourse.addKdamCourse(CoursesMap.get(Short.parseShort(Kdams[j])));
			}
		}
		return true;
	}

	public boolean isRegistered(String userName) {
		return AdminsMap.containsKey(userName) || StudentsMap.containsKey(userName);
	}

	public boolean isStudentExist(String userName) {
		return StudentsMap.containsKey(userName);
	}

	public boolean isValidPassword(String userName, String Password) {
		String toCompare;
		if (AdminsMap.containsKey(userName)) toCompare = AdminsMap.get(userName).getPassword();
		else toCompare = StudentsMap.get(userName).getPassword();
		return Password.equals(toCompare);
	}

	public boolean isCourseExist(short numOfCourse) {
		return CoursesMap.containsKey(numOfCourse);
	}

	public boolean isRoomAvailable(short numOfCourse) {
		return CoursesMap.get(numOfCourse).isRoomAvailable();
	}

	public boolean isKdamDone(String userName, short numOfCourse) {
		List<Course> KdamList = CoursesMap.get(numOfCourse).getKdamCoursesList();
		Student currentStudent = StudentsMap.get(userName);
		boolean result = true;
		for (Course course : KdamList) {
			result = currentStudent.checkCourse(course);
			if (!result) break;
		}
		return result;
	}

	public boolean isAdmin(String username) {
		return AdminsMap.containsKey(username);
	}

	public boolean register(String userName, String Password, boolean isAdmin) {
		if (isAdmin) {
			synchronized (AdminsMap) {
				if (isRegistered(userName)) return false;
				Admin newAdmin = new Admin(userName, Password);
				AdminsMap.put(userName, newAdmin);
			}
		}
		else {
			synchronized (StudentsMap) {
				if (isRegistered(userName)) return false;
				Student newStudent = new Student(userName, Password, new ArrayList<>()); // maybe change arraylist
				StudentsMap.put(userName, newStudent);
			}
		}
		return true;
	}

	public void courseRegister(String userName, short numOfCourse) {
		Course currentCourse = CoursesMap.get(numOfCourse);
		synchronized (StudentsMap.get(userName)) {
			StudentsMap.get(userName).addCourse(currentCourse);
		}
		synchronized (CoursesMap.get(numOfCourse)) {
			currentCourse.increaseNumOfRegistered();
		}
	}

	public String KdamCheck(short numOfCourse) {
		ArrayList<Course> list = CoursesMap.get(numOfCourse).getKdamCoursesList();
		return list.toString().replaceAll("\\s","");
	}


	public String ComposeCourseStat(short numOfCourse) {
		String output;
		Course currentCourse = CoursesMap.get(numOfCourse);
		synchronized (CoursesMap.get(numOfCourse)) {
			output = "Course: (" + numOfCourse + ") " + currentCourse.getCourseName() + "\n" +
					"Seats Available: " + currentCourse.getNumOfRegistered() + "/" + currentCourse.getNumOfMaxStudents() + "\n";
			output = output + getStudentsRegisteredList(currentCourse);
		}
		return output;
	}

	// ADMIN
	public String ComposeStudentStat(String userName) {
		Student currentStudent = StudentsMap.get(userName);
		synchronized (StudentsMap.get(userName)) {
			ArrayList<Course> toBeSorted = currentStudent.getCourses();
			sortCourses(toBeSorted);
			String sortedList = toBeSorted.toString().replaceAll("\\s", "");
			return "Student: " + currentStudent.getUsername() + "\n" + "Courses: " + sortedList;
		}
	}

	public String courseCheck(String userName, short numOfCourse) {
		Course course = CoursesMap.get(numOfCourse);
		if (StudentsMap.get(userName).checkCourse(course)) return "REGISTERED";
		else return "NOT REGISTERED";
	}

	public void unregister(String userName, short numOfCourse) {
		Course course = CoursesMap.get(numOfCourse);
		synchronized (StudentsMap.get(userName)) {
			StudentsMap.get(userName).removeCourse(course);
		}
		synchronized (CoursesMap.get(numOfCourse)) {
			course.decreaseNumOfRegistered();
		}
	}

	// TODO: need to be in CourseList order?
	public String myCourses(String userName) {
		return StudentsMap.get(userName).getCourses().toString().replaceAll("\\s","");
	}

	public boolean logIn(String username){
		synchronized (LoggedInMap) {
			if (!isLoggedIn(username)) {
				if (AdminsMap.containsKey(username)) {
					Admin admin = AdminsMap.get(username);
					LoggedInMap.put(username, admin);
				} else {
					Student student = StudentsMap.get(username);
					LoggedInMap.put(username, student);
				}
				return true;
			}
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
			// TODO: improve lock
			synchronized (StudentsMap.get(student.getUsername())) {
				if (student.checkCourse(courseToCheck)) registeredStudents.add(student.getUsername());
			}
		}
		registeredStudents.sort(Comparator.naturalOrder());
		return registeredStudents.toString().replaceAll("\\s","");
	}

	// TODO: sort by other array
	private void sortCourses(ArrayList<Course> courses) {
		Comparator<Course> comparator = Comparator.comparingInt(o -> CoursesList.indexOf(o.getCourseNum()));
		courses.sort(comparator);
	}

	// for testing purpose
	public void clear(){
		StudentsMap.clear();
		AdminsMap.clear();
		CoursesMap.clear();
		CoursesList.clear();
	}
}
