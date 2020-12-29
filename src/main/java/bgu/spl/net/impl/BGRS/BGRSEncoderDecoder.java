package bgu.spl.net.impl.BGRS;

import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.impl.Messages.Message;

public class BGRSEncoderDecoder implements MessageEncoderDecoder<Message> {
    @Override
    public Message decodeNextByte(byte nextByte) {
        return null;
    }

    @Override
    public byte[] encode(Message message) {
        return new byte[0];
    }
}
