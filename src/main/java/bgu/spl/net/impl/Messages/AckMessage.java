package bgu.spl.net.impl.Messages;

import bgu.spl.net.Database;
import bgu.spl.net.api.Message;

public class AckMessage implements Message {
    /*---------------------------------fields---------------------------------*/
    private int opt;
    private int msgOpcode;
    Object optional; //too general?
    /*-------------------------------constructors------------------------------*/

    public AckMessage(int msgOpcode, Object optional) {
        this.opt = 12;
        this.msgOpcode = msgOpcode;
        this.optional = optional;
    }
    /*---------------------------------getters---------------------------------*/
    /*---------------------------------setters---------------------------------*/
    /*---------------------------------methods---------------------------------*/
    @Override
    public Message process() { //not necessary, should implement message?
        return null;
    }

}
