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
 * This class provides methods to modify and confirm details of User class
 *
 * @author tvierine
 */
public class RegisteredUsersLogic implements Serializable {

    HashMap<String, User> userlist;
    SilkSpinDataSaving spin = new SilkSpinDataSaving();
    public Date now;
    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");      //determines the displayed date
    String returnval;
    String filename;                               //creates global filename for changing file's name, is Testfile.ser by default as shown in constructor
//    = new HashMap<>();

    /**
     *
     * This method creates instance of RegisteredUsersLogic, including listed
     * users, and date
     *
     *
     *
     *
     */
    public RegisteredUsersLogic() {
        userlist = new HashMap();
        now = new Date();               //fetches current date upon being constructed
        returnval = sdf.format(now);
        filename = "Testfile.ser";
    }
    
    /**
     *
     * This method allows changing the file in which the app reads from and/or writes to by replacing "Testfile.ser" with another name
     * 
     * Note that a textfile that is named "File.ser", where 'file' is the desired filename, MUST be present at the root folder
     *
     *This is because java seems to be unable to create the file by itself due to acccess right problems
     *
     *
     */
    public void changeFilenameForTests(String s){   //changes the write/read destination of this class to Testfile.ser  ##tested
        filename = s;
        spin.changeFileForTests(filename);
    }

    /**
     * This method is responsible for reading the file where userdata is stored
     * and saving the data for the class to use
     */
    public void initialize() {                                                                                          //##tested
        userlist = spin.read();                // reads savefile into the userlist
    }

    /**
     * This method is used to call String value for date
     *
     * @return current date in form dd.mm.yyyy form
     *
     *
     */
    public String showDate() {               //formats the date to display DD.MM.YYYY           ##Tested        

        return returnval;                                //returns the formatted date
    }

    /**
     * Saving of currently active data to a file
     *
     */
    public void save() {             // saves current data on registeredusers to the local file ##tested
        spin.write(userlist);
    }

    /**
     * Method that can be used to confirm presence of user on the list
     *
     * @param u User that is trying to register
     *
     *
     *
     * @return returns string representing whether or not user is on the list       ##tested
     */
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

    /**
     * Method used to log user in to the application
     *
     *
     *
     *
     * @param user username
     * @param pass password or safeword
     *
     * @return returns the User matching given parameters, otherwise a default
     * "no" user is returned
     * The "no" user is unique and cannot be created by users
     */
    public User login(String user, String pass) {                       //creates empty user with name that can't be registered ##tested
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
                //does nothing is login is wrong (checkstyle required pointless code line)
            }
            //___________________________password check end________________________________________________
        } else {
            logged = logged;
            //does nothing if username is wrong  (checkstyle required pointless code line)

        }
        return logged;                                      //returns either the default "no" user, or the user who logged in
    }

    /**
     * Method used for the registration event
     *The username chosen to be registered has to be at least 5 symbols long
     * The chosen password has to be at least 3 letters long
     *
     * @param user username
     * @param pass password
     * @return method returns a string value representing the registration event
     */
    public String register(String user, String pass) {           //handles registry part and tells ui what happened as result  ##tested
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

    /**
     * Method used to enter data values for the user
     *
     * @param u user that is logged in
     * @param data data that user is trying to save
     *
     *
     * @return method returns string representation of the data entry event
     *
     */
    public String enterData(User u, String data) {                              //##tested
        String[] dubs = data.split(", ");
        if(u.getStatus() == -1){
            return "Account doesn't exist";
        }
        if (dubs.length >= 2) {                             //entered data must split into list of length 2 or greater
            Scanner doubles = new Scanner(dubs[0]);         //confirms that the first entry is the value of the entry
            if (doubles.hasNextDouble() == true) {
                u.setData(data);             // writes data to account that is the user's status at the moment
                return "Data has been entered";
            } else {
                return "Please make sure your entry is in format: 'amount, type'";
            }
        } else {
            return "Please make sure your entry is in format: 'amount, type'"; //if the entered value doesn't have a double, it's false    
        }
    }

    /**
     * Get user
     *
     * @param u username
     *
     * @return method returns the user that is associated with the parameter
     */
    public User getUser(String u) {
        return userlist.get(u);                                 //returns user with given username
    }

    /**
     * Alternative method to check user's presence on the list
     *
     * @param u username
     *
     * @return true if user is on the list
     */
    public Boolean freeUser(String u) {
        return userlist.containsKey(u); //if username on the list, returns true, false is returned otherwise

    }

    /**
     * Method that returns the list containing Users and usernames involved with
     * them
     *
     *
     *
     * @return HashMap<String, User> representing currently active data
     */
    public HashMap<String, User> getListMap() {
        return userlist;
    }

    /**
     * Method that allows removal of User and all their data from the list
     *
     *
     *
     *
     */
    public void removeUser(String u) {           //removes all userdata from the program
        userlist.remove(u, userlist.get(u));
    }

}
