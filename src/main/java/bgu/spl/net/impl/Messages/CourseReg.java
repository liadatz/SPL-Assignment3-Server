package bgu.spl.net.impl.Messages;

import bgu.spl.net.Database;
import bgu.spl.net.api.Message;

public class CourseReg implements Message {
    /*---------------------------------fields---------------------------------*/
    private int opt;
    private String username;
    private int courseNum;
    private boolean isAdmin;
    private Database database;
    /*-------------------------------constructors------------------------------*/

    public CourseReg(String username, int courseNum, boolean isAdmin) {
        this.opt = 5;
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
            return new AckMessage(opt, null); //no optional
        }
        else
            return new ErrorMessage(opt);
    }

}
