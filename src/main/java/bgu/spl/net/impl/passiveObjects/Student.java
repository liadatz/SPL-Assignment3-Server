package bgu.spl.net.impl.passiveObjects;

import java.util.List;

public class Student {
/*---------------------------------fields---------------------------------*/
    private String username;
    private String password;
    private List<Course>  courses;
/*-------------------------------constructors------------------------------*/
    public Student(String username, String password, List<Course> courses){
        this.username = username;
        this.password = password;
        this.courses = courses;
    }
/*---------------------------------getters---------------------------------*/
    public String getUsername(){
        return username;
    }
    public String getPassword() {
        return password;
    }
    public List<Course> getCourses() {
        return courses;
    }
/*---------------------------------methods---------------------------------*/
    public void addCourse(Course toAdd){
        if (toAdd != null)
            courses.add(toAdd);
    }

    public boolean checkCourse(Course toCheck){
        if (toCheck != null)
            return (courses.contains(toCheck));
        return false;
    }

}
