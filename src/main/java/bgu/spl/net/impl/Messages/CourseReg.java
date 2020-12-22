package bgu.spl.net.impl.Messages;

import bgu.spl.net.Database;
import bgu.spl.net.api.Message;

public class CourseReg implements Message {
    /*---------------------------------fields---------------------------------*/
    private int opt;
    String username;
    int courseNum;
    boolean isAdmin;
    Database database;
    /*-------------------------------constructors------------------------------*/

    public CourseReg(int opt, String username, int courseNum, boolean isAdmin) {
        this.opt = opt;
        this.username = username;
        this.courseNum = courseNum;
        this.isAdmin = isAdmin;
        database = Database.getInstance();
    }
    /*---------------------------------getters---------------------------------*/
    /*---------------------------------setters---------------------------------*/
    /*---------------------------------methods---------------------------------*/
    @Override
    public Message process() {
        boolean isApproved = !isAdmin && database.isExist(courseNum) && database.isRoomAvailable(courseNum) &&
                database.isRegistered(username, isAdmin) && database.isKdamDone(username, courseNum) &&
                database.isLogedIn(username, isAdmin);
        if (isApproved) {
            database.courseRegister(username, courseNum);
            return new AckMessage();
        }
        else
            return new ErrorMessage();
    }

}
