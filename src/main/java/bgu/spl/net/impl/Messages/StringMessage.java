package bgu.spl.net.impl.Messages;

import bgu.spl.net.Database;
import bgu.spl.net.api.Message;

public class StringMessage implements Message {
    /*---------------------------------fields---------------------------------*/
    String msg;
    /*-------------------------------constructors------------------------------*/
    public StringMessage(String msg) {
        this.msg = msg;
    }
    /*---------------------------------getters---------------------------------*/
    /*---------------------------------setters---------------------------------*/
    /*---------------------------------methods---------------------------------*/
    @Override
    public Message process() { //no need, nothing to process
        return null;
    }
}

