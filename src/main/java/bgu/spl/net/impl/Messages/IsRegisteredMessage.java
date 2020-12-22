package bgu.spl.net.impl.Messages;

import bgu.spl.net.Database;
import bgu.spl.net.api.Message;

public class IsRegisteredMessage implements Message {
    /*---------------------------------fields---------------------------------*/
    private int opt;
    String username;
    int courseNum;
    boolean isAdmin;
    Database database;
    /*-------------------------------constructors------------------------------*/

    public IsRegisteredMessage(int opt, String username, int courseNum, boolean isAdmin) {
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
        return null; //finish imp
    }

}
