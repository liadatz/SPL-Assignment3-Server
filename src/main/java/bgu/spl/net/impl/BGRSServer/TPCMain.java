package bgu.spl.net.impl.BGRSServer;

import bgu.spl.net.srv.BaseServer;
import bgu.spl.net.srv.Server;

public class TPCMain {
    public static void main (String [] args) {
        if (args[0] != null) {
            Server.threadPerClient(
                    Integer.parseInt(args[0]), //port
                    () -> new BGRSProtocol(), //protocol factory
                    () -> new BGRSEncoderDecoder() //message encoder decoder factory
            ).serve();
        }
        else{
            System.out.print("no arguments");
        }
    }
}