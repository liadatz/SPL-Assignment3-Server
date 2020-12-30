package bgu.spl.net.impl.BGRS;

import bgu.spl.net.api.MessagingProtocol;
import bgu.spl.net.impl.Messages.Message;
import bgu.spl.net.srv.Database;

public class BGRSProtocol implements MessagingProtocol<Message> {
    private Database database = Database.getInstance();
    private String userName;
    private String Password;
    private Boolean isAdmin;
    private boolean isLogged; // client may terminate after logOut, maybe not needed

    @Override
    public Message process(Message msg) {
        short opCode = msg.getOpcode();
        Message answer = null;
        switch (opCode) {
            case 1:
                if (!database.isRegistered(msg.getUsername())) {
                    database.register(msg.getUsername(), msg.getPassword(), true);
                    answer = composeACK(opCode, null);
                }
            case 2:
                if (!database.isRegistered(msg.getUsername())) {
                    database.register(msg.getUsername(), msg.getPassword(), false);
                    answer = composeACK(opCode, null);
                }
            case 3:
                // Check if Protocol already used login, if User is registered,
                // if User is already login somewhere else and if password is matching username
                if (!isLogged && database.isRegistered(msg.getUsername()) && !database.isLoggedIn(msg.getUsername())
                        && database.isValidPassword(msg.getUsername(), msg.getPassword())) {
                    isAdmin = database.logIn(msg.getUsername());
                    // update data in Protocol
                    userName = msg.getUsername();
                    Password = msg.getPassword();
                    isLogged = true;
                    answer = composeACK(opCode, null);
                }
            case 4:
                // Check if Protocol already used login, if Protocol data is matching msg info
                // and if User is Logged in database
                if (isLogged && userName.equals(msg.getUsername()) && Password.equals(msg.getPassword())
                        && database.isLoggedIn(msg.getUsername())) {
                    database.logOut(userName);
                    // erase data from Protocol
                    userName = null;
                    Password = null;
                    isLogged = false;
                    isAdmin = null;
                    answer = composeACK(opCode, null);
                }
            case 5:
                // Check if the student is not logged in, no such course is exist,
                // no seats are available in this course,the student does not have all the Kdam courses,
                int CourseNum = msg.getCourseNum();
                if (isLogged && !isAdmin && database.isLoggedIn(userName) && database.isCourseExist(CourseNum)
                        && database.isRoomAvailable(CourseNum) && database.isKdamDone(userName, CourseNum)) {
                    database.courseRegister(userName, CourseNum);
                    answer = composeACK(opCode, null);
                }
            case 6: // TODO: check if user to be logged in for using it
                // Check if Course is exist
                if (database.isCourseExist(msg.getCourseNum())) {
                    String toAttach = database.KdamCheck(msg.getCourseNum());
                    answer = composeACK(opCode, toAttach);
                }
            case 7:
                // Check if User is Admin
                if (isAdmin) {
                    String toAttach = database.ComposeCourseStat(msg.getCourseNum());
                    answer = composeACK(opCode, toAttach);
                }
            case 8:
                // Check if User is Admin
                if (isAdmin) {
                    String toAttach = database.ComposeStudentStat(msg.getUsername());
                    answer = composeACK(opCode, toAttach);
                }
            case 9:
                // Check if User is not Admin and if user is logged in
                if (!isAdmin && database.isLoggedIn(userName)) {
                    String toAttach = database.courseCheck(userName, msg.getCourseNum());
                    answer = composeACK(opCode, toAttach);
                }
            case 10:
                // Check if User is not Admin and if user is logged in
                if (!isAdmin && database.isLoggedIn(userName)) {
                    database.unregister(userName, msg.getCourseNum());
                    answer = composeACK(opCode, null);
                }
            case 11:
                // Check if User is not Admin and if user is logged in
                if (!isAdmin && database.isLoggedIn(userName)) {
                    String toAttach = database.myCourses(userName);
                    answer = composeACK(opCode, toAttach);
                }
        }
        if (answer == null) {
            answer = new Message((short) 13);
            answer.setMsgOpcode(msg.getOpcode());
        }
        return answer;
    }

    private Message composeACK(short OpCode, String toAttach) {
        Message output = new Message((short) 12);
        output.setMsgOpcode(OpCode);
        if (toAttach != null) output.setOptional(toAttach);
        return output;
    }

    public boolean shouldTerminate() {
        return false;
    }
}
