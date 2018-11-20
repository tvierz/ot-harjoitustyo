/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package silkspinapp;

/**
 *
 * @author tvierine
 * 
 * This class defines the user of the program
 */
public class User {
    //User has their data, username, safeword, password and a login status
    String username;
    String data;
    String safeword;
    String password;
    int status;

    
    //luodaan metodit käyttäjän luomiselle, ja käyttäjän tietojen hakemiselle ja asettamiselle
    public User(String username, String password) {
        //when new user is created, software asks for username and a password
        this.username = username;
        this.password = password;
        data = "";
    }
    public void setSafeword(String safeword){
        //sets safeword for user
        this.safeword = safeword;
    }
    public boolean status() {
        Boolean logstat = false;
        if (this.status == 1) {
            logstat = true;
        }
        return logstat;         //returns false if user status is 0, true if user status is 1.
    }
    
    
    //methods to get userdata
    public String getUsername(){
        return this.username;       //returns username
    }
    public String getSafeword(){
        return this.safeword;
    }
    public String getPassword(){
        return this.password;
    }
    public String getData(){
        return this.data;
    }
    public void setData(String s){      // appends data to user's file
       
        data = data + " "+ s;
        
    }
   
    
    public void logIn(){
        this.status = 1;        //sets status to 1 when logged in
    }
    public void logOut(){
        this.status = 0;        //sets status to 0 when logging out
    }

}
