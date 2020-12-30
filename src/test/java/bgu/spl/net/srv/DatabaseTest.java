package bgu.spl.net.srv;

import bgu.spl.net.impl.passiveObjects.Admin;
import bgu.spl.net.impl.passiveObjects.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {
    Database database = Database.getInstance();

    @BeforeEach
    void setUp(){
        database.initialize("C:\\Users\\ASUS\\IntelliJProjects\\Assignment3\\Server\\Courses.txt");
    }

    @AfterEach
    void closeUp(){
        database.clear();
    }

    @Test
    void RegisterChecks() {
        database.register("liad1", "password1", true);
        assertTrue(database.isRegistered("liad1"));
        database.register("liad2","password2", false);
        assertTrue(database.isRegistered("liad2"));
    }

    @Test
    void isValidPassword() {
        database.register("liad1", "password1", true);
        assertTrue(database.isValidPassword("liad1", "password1"));
        database.register("liad2","password2", false);
        assertTrue(database.isValidPassword("liad2","password2"));
    }

    @Test
    void isExist() {
        assertTrue(database.isCourseExist(101));
        assertTrue(database.isCourseExist(102));
        assertTrue(database.isCourseExist(103));
        assertTrue(database.isCourseExist(111));
        assertTrue(database.isCourseExist(201));
        assertTrue(database.isCourseExist(202));
        assertTrue(database.isCourseExist(211));
        assertTrue(database.isCourseExist(311));
        assertTrue(database.isCourseExist(301));
        assertTrue(database.isCourseExist(400));
        assertFalse(database.isCourseExist(0));
    }

    @Test
    void isRoomAvailable() {
        assertTrue(database.isRoomAvailable(101));
        assertTrue(database.isRoomAvailable(102));
        assertTrue(database.isRoomAvailable(103));
        assertTrue(database.isRoomAvailable(111));
        assertTrue(database.isRoomAvailable(201));
        assertTrue(database.isRoomAvailable(202));
        assertTrue(database.isRoomAvailable(211));
        assertTrue(database.isRoomAvailable(311));
        assertTrue(database.isRoomAvailable(301));
        assertTrue(database.isRoomAvailable(400));
        database.register("liad1","password1", false);
        database.register("liad2","password2", false);
        database.courseRegister("liad1", 101);
        database.courseRegister("liad2", 101);
        assertFalse(database.isRoomAvailable(101));

    }
    @Test
    void isKdamDone() {
        database.register("liad1","password1", false);
        assertFalse(database.isKdamDone("liad1",301));
        database.courseRegister("liad1", 103);
        database.courseRegister("liad1", 102);
        database.courseRegister("liad1", 101);
        database.courseRegister("liad1", 201);
        database.courseRegister("liad1", 202);
        assertTrue(database.isKdamDone("liad1",301));
    }


    @Test
    void kdamCheck() {
        assertEquals("[301,311,211,202,201,111,103]", database.KdamCheck(400));
    }

//    @Test
//    void composeCourseStat() {
//    }


//    @Test
//    void composeStudentStat() {
//    }


    @Test
    void unregister() {
        database.register("liad1","password1", false);
        assertNotEquals("REGISTERED", database.courseCheck("liad1", 101));
        assertEquals("NOT REGISTERED", database.courseCheck("liad1", 101));
        database.courseRegister("liad1", 101);
        assertEquals("REGISTERED", database.courseCheck("liad1", 101));
        assertNotEquals("NOT REGISTERED", database.courseCheck("liad1", 101));
        database.unregister("liad1", 101);
        assertEquals("NOT REGISTERED", database.courseCheck("liad1", 101));
        assertNotEquals("REGISTERED", database.courseCheck("liad1", 101));
    }

    @Test
    void myCourses() {
        database.register("liad1","password1", false);
        assertEquals("[]", database.myCourses("liad1"));
        database.courseRegister("liad1", 101);
        database.courseRegister("liad1", 102);
        assertEquals("[101,102]", database.myCourses("liad1"));
    }

    @Test
    void log() {
        database.register("liad1", "password1", true);
        database.logIn("liad1");
        assertTrue(database.isLoggedIn("liad1"));
        database.logOut("liad1");
        assertFalse(database.isLoggedIn("liad1"));

        database.register("liad2","password2", false);
        database.logIn("liad2");
        assertTrue(database.isLoggedIn("liad2"));
        database.logOut("liad2");
        assertFalse(database.isLoggedIn("liad2"));
    }
}