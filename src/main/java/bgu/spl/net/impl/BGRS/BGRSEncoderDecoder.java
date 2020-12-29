package bgu.spl.net.impl.BGRS;

import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.impl.Messages.Message;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class BGRSEncoderDecoder implements MessageEncoderDecoder<Message> {

    private final ByteBuffer optBuffer = ByteBuffer.allocate(2);
    private byte[] stringBytes = new byte[1 << 10];
    private int length = 0;
    private ByteBuffer intBuffer = ByteBuffer.allocate(4); //final?
    private int shortCounter = 0; //can be replaced with length ?
    private int intCounter = 0; //can be replaced with length ?
    private short optNum = 0;
    private String username; //?
    private String password; //?

    @Override
    public Message decodeNextByte(byte nextByte) {

        if (shortCounter < 2){
            optBuffer.put(nextByte);
            shortCounter++;
            return null;
        }

        if (shortCounter == 2){
            optBuffer.flip();
            optNum = optBuffer.getShort();
            shortCounter++;
            return null;
        }

        if (optNum == 4 | optNum == 11){ //opt is 4 or 11
            shortCounter = 0;
            optNum = 0;
            return new Message(optNum);
        }

        else if (optNum != 0 & optNum < 4 | optNum == 8){ //opt is 1-3 or 8 (decode String)
            if (nextByte == '*'){ //end of message- just for now
                Message decoded = new Message(optNum);
                decoded.setUsername(username);
                decoded.setPassword(password);
                optNum = 0;
                username = new String();
                password = new String();
                return decoded;
            }

            else if (nextByte == '0'){
                if (username.isEmpty()) {
                    username = new String(stringBytes, 0, length, StandardCharsets.UTF_8);
                }
                else{
                    password = new String(stringBytes, 0, length, StandardCharsets.UTF_8);
                }
                length = 0;//reset for next string
                return null;
            }
        }

        else if (optNum != 0){ //opt is 5-7 or 9-10 (decode int)
            return decodeInt(nextByte);
        }

        return null; // if opNum is still 0 (?)
    }

    public Message decodeString(byte nextByte){
        return null;
    }

    public Message decodeInt(byte nextByte){
        if (intCounter < 4){
            intBuffer.put(nextByte);
            intCounter++;
            return null;
        }

        else {
            Message decoded = new Message(optNum);
            intBuffer.flip();
            decoded.setCourseNum(intBuffer.getInt());
            intCounter = 0;
            optNum =0;
            return decoded;
        }
    }

    @Override
    public byte[] encode(Message message) {
        return new byte[0];
    }
}
