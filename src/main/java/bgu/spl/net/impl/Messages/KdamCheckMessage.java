package bgu.spl.net.impl.Messages;

import bgu.spl.net.srv.Database;
import bgu.spl.net.api.Message;

public class KdamCheckMessage implements Message {
    /*---------------------------------fields---------------------------------*/
    private int opt;
    private int courseNum;
    private Database database;
    /*-------------------------------constructors------------------------------*/

    public KdamCheckMessage(int courseNum) {
        this.opt = 6;
        this.courseNum = courseNum;
        database = Database.getInstance();
    }
    /*---------------------------------getters---------------------------------*/
    /*---------------------------------setters---------------------------------*/
    /*---------------------------------methods---------------------------------*/
    @Override
    public Message process() {
        return new StringMessage(database.KdamCheck(courseNum)); //not really sure it's the best way, but i dont know how to return a string otherwise
    }
}
