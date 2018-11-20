/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package silkspinapp;

import java.util.HashMap;

/**
 *
 * 
 * this class lists all users that are registered to the system, also handles removing users that remove their accounts
 * @author tvierine
 */
public class RegisteredUsers {
    HashMap<String, User> userlist;
//    = new HashMap<>();
    public RegisteredUsers(){
        userlist = new HashMap();
    }
    public String listUser(User u){
        if(userlist.containsKey(u.getUsername()) == false){     // if user is not on the list, they are added to the list
            
        String username = u.getUsername();
        userlist.put(username, u);                              //adds user and their username to the list of users
        return "User registered succesfully";                   //returns statement whether or not registration succeeded or not
        }
        else{
            // if user is listed, do nothing
            return "user is already listed";                   //returns statement whether or not registration succeeded or not
        }
    }
    public User getUser(String u){
        return userlist.get(u);                                 //returns user with given username
    }
    public Boolean freeUser(String u){
        return userlist.containsKey(u);//if username on the list, returns true, false is returned otherwise
        
    }
    
    
   
    
}
