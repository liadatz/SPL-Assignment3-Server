package bgu.spl.net.impl.passiveObjects;

import java.util.List;

public class Course {
/*---------------------------------fields---------------------------------*/
    short courseNum;
    String courseName;
    List<Course> KdamCoursesList;
    int numOfMaxStudents;
    int numOfRegistered;
/*-------------------------------constructors------------------------------*/
    public Course(short courseNum, String courseName, List<Course> kdamCoursesList, int numOfMaxStudents) {
        this.courseNum = courseNum;
        this.courseName = courseName;
        KdamCoursesList = kdamCoursesList;
        this.numOfMaxStudents = numOfMaxStudents;
        this.numOfRegistered = 0;
    }
/*---------------------------------getters---------------------------------*/
    public short getCourseNum() {
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

/*---------------------------------methods---------------------------------*/
    public void increaseNumOfRegistered(){
        if (numOfRegistered < numOfMaxStudents) numOfRegistered++;
    }

    public boolean isRoomAvailable(){
        return numOfRegistered < numOfMaxStudents;
    }

    public String toString(){
        return "" + courseNum;
    }

    public void addKdamCourse(Course courseToAdd) {
        KdamCoursesList.add(courseToAdd);
    }


}


