package bgu.spl.net.impl.Messages;

import bgu.spl.net.Database;
import bgu.spl.net.api.Message;

public class LogInMessage implements Message {
    /*---------------------------------fields---------------------------------*/
    private int opt;
    private String username;
    private String password;
    private boolean isAdmin;
    private Database database;
    /*-------------------------------constructors------------------------------*/

    public LogInMessage(String username, String password, boolean isAdmin) {
        this.opt = 3;
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
        if ((database.isRegistered(username, isAdmin)) && (database.isValidPassword(username, password, isAdmin)) && !database.isLogedIn(username, isAdmin)){
            database.logIn(username, isAdmin);
            return new AckMessage(opt, null); //no optional
        }
        else return new ErrorMessage(opt);
    }

}
