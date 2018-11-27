/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package silkspinapp;

import java.util.HashMap;
import silkspinapp.User;
import java.io.*;

/**
 *
 *
 * this class lists all users that are registered to the system, also handles
 * removing users that remove their accounts
 *
 * @author tvierine
 */
public class RegisteredUsers implements Serializable {

    HashMap<String, User> userlist;
    SilkSpinLogic spin = new SilkSpinLogic();
//    = new HashMap<>();

    public RegisteredUsers() {
        userlist = new HashMap();
    }

    public void initialize() {
        userlist = spin.read();                // reads savefile into the userlist
    }

    public void save() {             // saves current data on registeredusers to the local file
        spin.write(userlist);
    }

    public String listUser(User u) {
        if (userlist.containsKey(u.getUsername()) == false) {     // if user is not on the list, they are added to the list

            String username = u.getUsername();
            userlist.put(username, u);                              //adds user and their username to the list of users
            return "User registered succesfully";                   //returns statement whether or not registration succeeded or not
        } else {
            // if user is listed, do nothing
            return "user is already listed";                   //returns statement whether or not registration succeeded or not
        }
    }

    public User login(String user, String pass) {                       //creates empty user with name that can't be registered
        User logged = new User("no", "no");                 //placeholder for usertest
        if (userlist.containsKey(user)) {                           //if user map contains username
            //____________password checks________________________________________________________
            if (userlist.get(user).getPassword().equals(pass)) { //if user's password matches their username
                logged = userlist.get(user);                     // sets the returned user to be the one that logged in
            }
            if (userlist.get(user).getSafeword().equals(pass)) {    //allows logging in with safeword
                logged = userlist.get(user);                  // sets the returned user to be the one that logged in

            } else {
                //does nothing is login is wrong
            }
            //___________________________password check end________________________________________________
        } else {
            //does nothing if username is wrong

        }
        return logged;                                      //returns either the default "no" user, or the user who logged in
    }

    public String register(String user, String pass) {           //handles registry part and tells ui what happened as result
        String msg = "Registry has failed";
        if (userlist.containsKey(user) == false) {                         //if username not taken, program lists user and tells them they have been registered
            if (user.length() > 4) {                              //confirms that username is not too short
                if (pass.length() > 2) {                          //confirms that password isn't too short

                    User add = new User(user, pass);
                    userlist.put(user, add);
                    msg = ("Account has been created successfully");
                } else {                                                  //tells user if their password is too short
                    msg = ("Your password is too weak");
                }

            } else {
                msg = ("your username is too short");
            }
        } else {                                                   //if username is taken, program tells user that                                                       
            msg = ("This username is already taken or your username is shorter than 4 characters");
        }

        return msg;
    }

    public void enterData(User u, Integer accountno, String data) {
        u.changeAccount(accountno);             //changes the user's account status to specified value
        u.setData(data);                        // writes data to account that is the user's status at the moment
    }

    public User getUser(String u) {
        return userlist.get(u);                                 //returns user with given username
    }

    public Boolean freeUser(String u) {
        return userlist.containsKey(u); //if username on the list, returns true, false is returned otherwise

    }

    public HashMap<String, User> getListMap() {
        return userlist;
    }

    public void removeUser(String u) {           //removes all userdata from the program
        userlist.remove(u, userlist.get(u));
    }

}
