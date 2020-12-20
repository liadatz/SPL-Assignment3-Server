package bgu.spl.net.impl.passiveObjects;

import java.util.List;

public class Course {
    int courseNum;
    String courseName;
    List<Course> KdamCoursesList;
    int numOfMaxStudents;
    int numOfRegistered;

    public Course(int courseNum, String courseName, List<Course> kdamCoursesList, int numOfMaxStudents, int numOfRegistered) {
        this.courseNum = courseNum;
        this.courseName = courseName;
        KdamCoursesList = kdamCoursesList;
        this.numOfMaxStudents = numOfMaxStudents;
        this.numOfRegistered = numOfRegistered;
    }

    public int getCourseNum() {
        return courseNum;
    }

    public String getCourseName() {
        return courseName;
    }

    public List<Course> getKdamCoursesList() {
        return KdamCoursesList;
    }

    public int getNumOfMaxStudents() {
        return numOfMaxStudents;
    }

    public int getNumOfRegistered() {
        return numOfRegistered;
    }

    public void increaseNumOfRegistered(){
        if (numOfRegistered < numOfMaxStudents) numOfRegistered++;
    }


}


