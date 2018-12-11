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

    public String comment;
    public Double amount;
    public String type;
    Date now;
    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");      //determines the displayed date
    String date;

    public DataSpec(String comm, Double amount, String type) {      //creates a data entry
        now = new Date();               //confirms date on creation of entry, again
        this.comment = comm;
        this.amount = amount;
        this.type = type;
        date = sdf.format(now);

    }

    public Integer getMo() {     //returns int of current month
        String[] mo = date.split("\\.");
        String mo1 = mo[1];
        int i = parseInt(mo1);
        return i;
    }

    public double getAmount() {
        return amount;              // returns the double value of the entry
    }

    @Override
    public String toString() {
        return date + ": " + comment + " AMOUNT: " + amount.toString() + " TYPE OF EXPENSE: " + type;          //returns data entries in shape "date, comment, type, amount"
    }

}
