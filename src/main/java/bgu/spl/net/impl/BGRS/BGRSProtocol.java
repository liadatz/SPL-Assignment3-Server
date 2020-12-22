package bgu.spl.net.impl.BGRS;

import bgu.spl.net.api.Message;
import bgu.spl.net.api.MessagingProtocol;

public class BGRSProtocol implements MessagingProtocol<Message> {

    @Override
    public Message process(Message msg) {
        Message reply = msg.process();
        return reply;
    }

    public boolean shouldTerminate() {
        return false;
    }
}
