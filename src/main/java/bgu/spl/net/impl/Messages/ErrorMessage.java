package bgu.spl.net.impl.Messages;

import bgu.spl.net.api.Message;

public class ErrorMessage implements Message {
    /*---------------------------------fields---------------------------------*/
    private int opt;
    private int msgOpcode;
    /*-------------------------------constructors------------------------------*/

    public ErrorMessage(int msgOpcode) {
        this.opt = 12;
        this.msgOpcode = msgOpcode;
    }
    /*---------------------------------getters---------------------------------*/
    /*---------------------------------setters---------------------------------*/
    /*---------------------------------methods---------------------------------*/
    @Override
    public Message process() { //not necessary, should implement message?
        return null;
    }

}
