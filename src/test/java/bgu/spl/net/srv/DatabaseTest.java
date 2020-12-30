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
        assertTrue(database.isCourseExist((short) 101));
        assertTrue(database.isCourseExist((short) 102));
        assertTrue(database.isCourseExist((short) 103));
        assertTrue(database.isCourseExist((short) 111));
        assertTrue(database.isCourseExist((short) 201));
        assertTrue(database.isCourseExist((short) 202));
        assertTrue(database.isCourseExist((short) 211));
        assertTrue(database.isCourseExist((short) 311));
        assertTrue(database.isCourseExist((short) 301));
        assertTrue(database.isCourseExist((short) 400));
        assertFalse(database.isCourseExist((short) 0));
    }

    @Test
    void isRoomAvailable() {
        assertTrue(database.isRoomAvailable((short) 101));
        assertTrue(database.isRoomAvailable((short) 102));
        assertTrue(database.isRoomAvailable((short) 103));
        assertTrue(database.isRoomAvailable((short) 111));
        assertTrue(database.isRoomAvailable((short) 201));
        assertTrue(database.isRoomAvailable((short) 202));
        assertTrue(database.isRoomAvailable((short) 211));
        assertTrue(database.isRoomAvailable((short) 311));
        assertTrue(database.isRoomAvailable((short) 301));
        assertTrue(database.isRoomAvailable((short) 400));
        database.register("liad1","password1", false);
        database.register("liad2","password2", false);
        database.courseRegister("liad1", (short) 101);
        database.courseRegister("liad2", (short) 101);
        assertFalse(database.isRoomAvailable((short) 101));

    }
    @Test
    void isKdamDone() {
        database.register("liad1","password1", false);
        assertFalse(database.isKdamDone("liad1",(short) 301));
        database.courseRegister("liad1", (short) 103);
        database.courseRegister("liad1", (short) 102);
        database.courseRegister("liad1", (short) 101);
        database.courseRegister("liad1", (short) 201);
        database.courseRegister("liad1", (short) 202);
        assertTrue(database.isKdamDone("liad1", (short) 301));
    }


    @Test
    void kdamCheck() {
        assertEquals("[103,111,201,202,211,311,301]", database.KdamCheck((short) 400));
    }

    @Test
    void composeCourseStat() {
        database.register("liad1","password1", false);
        database.register("liad2","password2", false);
        database.courseRegister("liad2", (short) 111);
        database.courseRegister("liad1", (short) 111);
        System.out.println(database.ComposeCourseStat((short) 111));
    }


    @Test
    void composeStudentStat() {
        database.register("liad1","password1", false);
        database.courseRegister("liad1", (short) 103);
        database.courseRegister("liad1", (short) 102);
        database.courseRegister("liad1", (short) 101);
        database.courseRegister("liad1", (short) 201);
        database.courseRegister("liad1", (short) 202);
        System.out.println(database.ComposeStudentStat("liad1"));
    }


    @Test
    void unregister() {
        database.register("liad1","password1", false);
        assertNotEquals("REGISTERED", database.courseCheck("liad1", (short)101));
        assertEquals("NOT REGISTERED", database.courseCheck("liad1", (short)101));
        database.courseRegister("liad1", (short)101);
        assertEquals("REGISTERED", database.courseCheck("liad1", (short)101));
        assertNotEquals("NOT REGISTERED", database.courseCheck("liad1", (short)101));
        database.unregister("liad1", (short)101);
        assertEquals("NOT REGISTERED", database.courseCheck("liad1", (short)101));
        assertNotEquals("REGISTERED", database.courseCheck("liad1", (short)101));
    }

    @Test
    void myCourses() {
        database.register("liad1","password1", false);
        assertEquals("[]", database.myCourses("liad1"));
        database.courseRegister("liad1", (short)101);
        database.courseRegister("liad1", (short)102);
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