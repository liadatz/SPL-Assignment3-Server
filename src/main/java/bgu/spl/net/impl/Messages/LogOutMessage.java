package bgu.spl.net.impl.Messages;

import bgu.spl.net.Database;
import bgu.spl.net.api.Message;

public class LogOutMessage implements Message {
    /*---------------------------------fields---------------------------------*/
    private int opt; //in all messages
    boolean isAdmin;
    Database database; //in all messages
    /*-------------------------------constructors------------------------------*/

    public LogOutMessage(int opt, boolean isAdmin) {
        this.opt = opt;
        this.isAdmin = isAdmin;
        database = Database.getInstance();
    }
    /*---------------------------------getters---------------------------------*/
    /*---------------------------------setters---------------------------------*/
    /*---------------------------------methods---------------------------------*/
    @Override
    public Message process() {
        return null;
        //not quiet sure what we suppose to implement here, similar to login? or something else?
    }

}
