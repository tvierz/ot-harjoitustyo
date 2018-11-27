/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package silkspinapp;

import java.io.*;
import java.util.*;
import java.util.HashMap;
import silkspinapp.User;

/**
 *
 * @author tvierine
 *
 * The purpose of this class is to define how the software functions
 */
public class SilkSpinLogic {

    public void write(HashMap<String, User> hm) {                //saves contents of given hashmap in a local file
        try {
            FileOutputStream fos
                    = new FileOutputStream("Userdat.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(hm);
            oos.close();
            fos.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public HashMap<String, User> read() {

        HashMap<String, User> hm = new HashMap<>();

        User nulluser = new User("no", "no");           //resets the null user on every save
        hm.put("no", nulluser);
        try {
            FileInputStream fis = new FileInputStream("Userdat.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            hm = (HashMap) ois.readObject();    //writes data into given hashmap
            ois.close();
            fis.close();

        } catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            c.printStackTrace();
        }
        return hm;                  //returns read hashmap containing at least the null user

    }
}
