package bgu.spl.net.impl.Messages;

import bgu.spl.net.Database;
import bgu.spl.net.api.Message;

public class KdamCheckMessage implements Message {
    /*---------------------------------fields---------------------------------*/
    private int opt;
    int courseNum;
    Database database;
    /*-------------------------------constructors------------------------------*/

    public KdamCheckMessage(int opt, int courseNum) {
        this.opt = opt;
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
