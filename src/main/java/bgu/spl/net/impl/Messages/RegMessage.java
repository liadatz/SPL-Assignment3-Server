package bgu.spl.net.impl.Messages;

public class RegMessage extends Message {
    /*---------------------------------fields----------------------------------*/
    private String username;
    private String password;
    /*-------------------------------constructors------------------------------*/
    public RegMessage(short opcode, String username, String password) {
        super(opcode);
        this.username = username;
        this.password = password;
    }
    /*---------------------------------getters---------------------------------*/
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    /*---------------------------------setters---------------------------------*/
    /*---------------------------------methods---------------------------------*/
}
