package bgu.spl.net.impl.passiveObjects;

public class User {
    /*---------------------------------fields---------------------------------*/
    private String username;
    private String password;
    private boolean isLogedIn; // maybe add online status? -i think its the best option
    /*-------------------------------constructors------------------------------*/
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.isLogedIn = false;
    }
    /*---------------------------------getters---------------------------------*/
    public String getUsername(){
        return username;
    }
    public String getPassword() {
        return password;
    }
    public boolean isLogedIn(){return isLogedIn;}
    /*---------------------------------getters---------------------------------*/
    public void setLogIn(boolean status){this.isLogedIn = status;}
}
