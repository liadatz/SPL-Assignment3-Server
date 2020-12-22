package bgu.spl.net.impl.Messages;

import bgu.spl.net.Database;
import bgu.spl.net.api.Message;

public class IsRegisteredMessage implements Message {
    /*---------------------------------fields---------------------------------*/
    private int opt;
    private String username;
    private int courseNum;
    private boolean isAdmin;
    private Database database;
    /*-------------------------------constructors------------------------------*/

    public IsRegisteredMessage(String username, int courseNum, boolean isAdmin) {
        this.opt = 9;
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
        if (database.isRegistered(username, isAdmin) && database.isExist(courseNum)){
            if (database.courseCheck(username, courseNum)) {
                System.out.print("REGISTERED");
                return new AckMessage(opt, null);//no optional
            }
            else {
                System.out.print("NOT REGISTERED");
                return new AckMessage(opt, null);//no optional
            }
            }
        else
            return new ErrorMessage(opt);
        }
}
