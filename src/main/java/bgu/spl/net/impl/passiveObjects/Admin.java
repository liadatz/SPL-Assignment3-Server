package bgu.spl.net.impl.passiveObjects;

import java.util.List;

public class Admin {
    /*---------------------------------fields---------------------------------*/
    private String username;
    private String password;
    /*-------------------------------constructors------------------------------*/
    public Admin(String username, String password){
        this.username = username;
        this.password = password;
    }
    /*---------------------------------getters---------------------------------*/
    public String getUsername(){
        return username;
    }
    public String getPassword() {
        return password;
    }
}
