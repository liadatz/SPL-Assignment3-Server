package bgu.spl.net.impl.Messages;

import bgu.spl.net.Database;
import bgu.spl.net.api.Message;

public class RegisterMessage implements Message { //for both ADMINREG and STUDENTREG
/*---------------------------------fields---------------------------------*/
    private int opt;
    String username;
    String password;
    boolean isAdmin;
    Database database;
/*-------------------------------constructors------------------------------*/

    public RegisterMessage(int opt, String username, String password, boolean isAdmin) {
        this.opt = opt;
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
        database = Database.getInstance();
    }
/*---------------------------------getters---------------------------------*/
/*---------------------------------setters---------------------------------*/
/*---------------------------------methods---------------------------------*/
    @Override
    public Message process() {
        if (!database.isRegistered(username, isAdmin)){
            database.register(username, password, isAdmin);
            return new AckMessage();
        }
        else return new ErrorMessage();
    }
}
