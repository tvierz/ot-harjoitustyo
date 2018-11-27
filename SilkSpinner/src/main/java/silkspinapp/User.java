/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package silkspinapp;

import java.io.*;
import java.util.HashMap;

/**
 *
 * @author tvierine
 *
 * This class defines the user of the program
 */
public class User implements Serializable {

    private //User has their data, username, safeword, password and a login status
            String username;
    HashMap<Integer, String> data;
    String safeword = "onlysuperadmingodknowthispasswordimthebesthahahahahahahaa4329832576"; //generates a standard safeword for admin that nobody else should know, or be able to crack
    String password;
    int status;

    //luodaan metodit käyttäjän luomiselle, ja käyttäjän tietojen hakemiselle ja asettamiselle
    public User(String username, String password) {
        //when new user is created, software asks for username and a password
        this.username = username;
        this.password = password;
        data = new HashMap<>();
        data.put(1, "Data starts:");                //adds first account specified by integer, account contains data in form of string
        status = 1;
    }

    public void setSafeword(String safeword) {
        //sets safeword for user
        this.safeword = safeword;
    }

    //methods to get userdata
    public String getUsername() {
        return this.username;       //returns username
    }

    public String getSafeword() {
        return this.safeword;
    }

    public String getPassword() {
        return this.password;
    }

    public String getData() {
        return data.get(status);
    }

    public void setData(String s) {      // appends data to user's file
        String current = data.get(status);               //gets data on currently used account
        current = current + " " + s;                   //appends given data to the String
        data.put(status, current);                       //puts the appended data on the account
    }

    public void changeAccount(Integer i) {
        this.status = i;
    }

    public void createaccount() {
        data.put(data.size() + 1, "Data starts:");   //creates new account with number that is 1 greater than current account amount total
    }

    public int getStatus() {
        return this.status;
    }

    public String toString() {
        return "s";
    }
}
