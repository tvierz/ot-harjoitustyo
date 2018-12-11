/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package silkspinapp.silkspindataobjects;

import java.io.*;
import static java.lang.Double.parseDouble;
import java.util.HashMap;
import java.util.Map;
import java.util.*;
import java.text.*;

/**
 *
 * @author tvierine
 *
 * This class defines the user of the program
 */
public class User implements Serializable {

    //User has their data, username, safeword, password and a login status acccounts and a 
    String username;
    HashMap<Integer, DataSpec> data;                                    //list of data entries
    HashMap<Integer, HashMap<Integer, DataSpec>> accountlist = new HashMap<>();           //list of accounts with data entries
    String safeword = "onlysuperadmingodknowthispasswordimthebesthahahahahahahaa4329832576"; //generates a standard safeword for admin that nobody else should know, or be able to crack
    String password;
    int status;
    int dataentry;
    DataSpec use;           // null entry on the data list
    int month;
    GregorianCalendar date = new GregorianCalendar();
    BudgetPlan BP;

    //luodaan metodit käyttäjän luomiselle, ja käyttäjän tietojen hakemiselle ja asettamiselle
    public User(String username, String password) {
        //when new user is created, software asks for username and a password
        this.username = username;
        this.password = password;
        data = new HashMap<>();
        BP = new BudgetPlan();
        this.status = 1;
        this.dataentry = 0;     //starts data entries from 0
        accountlist.put(1, data);
        //adds first, default account specified by integer, account contains data in form of string

    }

    public void setSafeword(String safeword) {
        //sets safeword for user
        this.safeword = safeword;
    }

    public void setBudget(BudgetPlan bp) {
        BP = bp;
    }
    public BudgetPlan getBpp(){
        return BP;
    }
    public double monthlyTotal() {

        month = date.get(Calendar.MONTH) + 1;
        double mototal = 0;             //starts at nothing
        for (Integer i : data.keySet()) {
            if (data.get(i).getMo() == month) {               //goes through user's active account's data and adds it to total
                mototal = mototal + data.get(i).getAmount();
            }
        }
        return mototal;

    }

    //methods to get userdata
    public String getUsername() {
        return this.username;       //returns username
    }

    public HashMap<Integer, DataSpec> getdataEntries() {
        return data;
    }

    public String getSafeword() {
        return this.safeword;
    }

    public String getPassword() {
        return this.password;
    }

    public String getData() {
        String s = "You spun this silk: ";
        if (data == null) {
            return "No account selected";
        } else {
            if (data.keySet().size() > 0) {
                for (Integer i : data.keySet()) {
                    s = s + "\n" + data.get(i).toString();
                }
            } else {
                return s;
            }
        }
        return s;           //returns datatype entry
    }

    public void setData(String s) {      // enters data entry to user's account's Map
        //double check is in RegisteredUsers, so not needed here
        data = accountlist.get(status);
        this.dataentry = data.size() + 1;         //puts new entries on separate values
        String[] split = s.split(", ");    //splits given data entry into parts
        String comm = split[0];
        Double amount = Double.parseDouble(split[1]);    //splits double value into a double
        String type = split[2];
        DataSpec enter = new DataSpec(comm, amount, type);
        data.put(this.dataentry, enter);   //puts the appended data on the account
    }

    public String changeAccount(String s) {
        int i = 0;
        String message = "That account doesn't exist, make sure you entered your account number correctly\";";
        try {                            //checks that the entered value is integer
            i = Integer.parseInt(s);

            if (accountlist.containsKey(i) && i > 0) {
                this.status = i;
                data = accountlist.get(i);
                message = i + "";
                return "Your account number " + message + " has been selected";
            } else {
                data = null;
                return message;
            }

        } catch (Exception e) {            //if fed value isn't integer, error is displayed to user
            return message;
        }
    }

    public void createaccount() {
        use = new DataSpec("", 0.0, "");
        int size = accountlist.size() + 1;
        this.accountlist.put(size, new HashMap<>());  //creates new account with number that is 1 greater than current account amount total

    }

    public int getStatus() {
        return this.status;
    }

    public String toString() {
        return "s";
    }
}
