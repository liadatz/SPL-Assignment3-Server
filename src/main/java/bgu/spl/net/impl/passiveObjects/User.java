package bgu.spl.net.impl.passiveObjects;

public class User {
    /*---------------------------------fields---------------------------------*/
    private String username;
    private String password;
    private boolean isLogged; // maybe add online status? -i think its the best option
    /*-------------------------------constructors------------------------------*/
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.isLogged = false;
    }
    /*---------------------------------getters---------------------------------*/
    public String getUsername(){
        return username;
    }
    public String getPassword() {
        return password;
    }
    public boolean isLogged(){return isLogged;}
    /*---------------------------------getters---------------------------------*/
    public void setLogIn(boolean status){this.isLogged = status;}
}
