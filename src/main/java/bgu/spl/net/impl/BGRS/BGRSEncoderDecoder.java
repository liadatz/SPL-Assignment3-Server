package bgu.spl.net.impl.BGRS;

import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.impl.Messages.Message;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class BGRSEncoderDecoder implements MessageEncoderDecoder<Message> {

    private byte[] shortArray = new byte[2];
    private byte[] stringBytes = new byte[1 << 10];
    private int length = 0;
    private ByteBuffer intBuffer = ByteBuffer.allocate(4); //final?
    private int optCounter = 0; //can be replaced with length ?
    private int shortCounter = 0; //can be replaced with length ?
    private short optNum = 0;
    private String username; //?
    private String password; //?

    @Override
    public Message decodeNextByte(byte nextByte) {

        if (optCounter < 2){
            shortArray[optCounter] = nextByte;
            optCounter++;
            return null;
        }

        if (optCounter == 2){
            optNum = bytesToShort(shortArray);
            optCounter++;
        }

        if (optNum == 4 | optNum == 11){ //opt is 4 or 11
            Message decoded = new Message(optNum);
            shortCounter = 0;
            optNum = 0;
            return decoded;
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

            else if (nextByte == '0'){ //
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
            if (shortCounter < 2){
                shortArray[shortCounter] = nextByte;
                shortCounter++;
                return null;
            }

            if (shortCounter == 2){
                Message decoded = new Message(optNum);
                decoded.setCourseNum(bytesToShort(shortArray));
                shortCounter = 0;
                optNum =0;
                return decoded;

            }
        }
        return null; // if opNum is still 0 (?)
    }

    public Message decodeString(byte nextByte){
        return null;
    }


    public short bytesToShort(byte[] byteArr)
    {
        short result = (short)((byteArr[0] & 0xff) << 8);
        result += (short)(byteArr[1] & 0xff);
        return result;
    }


    @Override
    public byte[] encode(Message message) {
        return new byte[0];
    }
}
