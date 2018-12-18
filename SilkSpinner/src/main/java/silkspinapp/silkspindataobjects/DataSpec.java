/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package silkspinapp.silkspindataobjects;

import java.io.*;
import static java.lang.Integer.parseInt;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * @author tvierine //this class will later specify the data user gives to the
 * software with the account
 */
public class DataSpec implements Serializable {
    public Double amount;
    public String type;
    Date now;
    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");      //determines the displayed date
    String date;

    /**
     * Constructor used for creating DataSpec objects
     * DataSpec is the object used to represent budget entries
     *
     * @param amount value of the DataSpec
     * @param type type of the DataSpec
     *
     */
    public DataSpec(Double amount, String type) {      //creates a data entry
        now = new Date();               //confirms date on creation of entry, again
        this.amount = amount;
        this.type = type;
        date = sdf.format(now);         //every dataspec entry gets a date on which they are created

    }
    

    /**
     * Method used to get integer representing the current month
     *
     * @return integer representing the current month
     *
     *
     */
    public Integer getMo() {     //returns int of current month
        String[] mo = date.split("\\.");
        String mo1 = mo[1];
        int i = parseInt(mo1);
        return i;
    }

    public double getAmount() {
        return amount;              // returns the double representing the object's value
    }

    @Override
    public String toString() {
        String s = date + ": " + "AMOUNT: " + amount.toString() + "€" + " TYPE OF EXPENSE: " + type;
        StringBuilder sb = new StringBuilder();
        
        return s;          //returns data entries in shape "date, comment, type, amount"
    }

}
