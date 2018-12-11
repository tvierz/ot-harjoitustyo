/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package silkspinapp.logicandoperations;

import java.util.HashMap;
import silkspinapp.silkspindataobjects.User;
import java.io.*;
import java.util.Date;

import java.text.*;
import java.util.List;
import java.util.Scanner;
import silkspinapp.silkspindataobjects.BudgetPlan;

/**
 *
 *
 * this class lists all users that are registered to the system, also handles
 * removing users that remove their accounts
 *
 * @author tvierine
 */
public class RegisteredUsersLogic implements Serializable {

    HashMap<String, User> userlist;
    SilkSpinDataSaving spin = new SilkSpinDataSaving();
    public Date now;
    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");      //determines the displayed date
    String returnval;
//    = new HashMap<>();

    public RegisteredUsersLogic() {
        userlist = new HashMap();
        now = new Date();               //fetches current date upon being constructed
        returnval = sdf.format(now);
    }
//
//    public double fetchDateData(User u, int month) { //gets specified user, and uses given month to sum all entries of given month
//        return userlist.get(u).monthlyTotal(month);           //gets user's data from specified date
//    }

    public void initialize() {
        userlist = spin.read();                // reads savefile into the userlist
    }

    public String showDate() {               //formats the date to display DD.MM.YYYY

        return returnval;                                //returns the formatted date
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
                logged = logged;
                //does nothing is login is wrong
            }
            //___________________________password check end________________________________________________
        } else {
            logged = logged;
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

    public String enterData(User u, String data) {
        String[] dubs = data.split(", ");
        if (dubs.length == 3) {                             //entered data must split into list of length 3
            Scanner doubles = new Scanner(dubs[1]);
            if (doubles.hasNextDouble() == true) {
                u.setData(data);             // writes data to account that is the user's status at the moment
                return "Data has been entered";
            } else {
                return "Please make sure your entry is in format: 'comment, amount, type'";
            }
        } else {
            return "Please make sure your entry is in format: 'comment, amount, type'"; //if the entered value doesn't have a double, it's false    
        }
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
