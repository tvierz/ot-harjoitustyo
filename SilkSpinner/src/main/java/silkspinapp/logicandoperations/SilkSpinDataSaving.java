/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package silkspinapp.logicandoperations;

import java.io.*;
import java.util.*;
import java.util.HashMap;
import silkspinapp.silkspindataobjects.User;

/**
 *
 * @author tvierine
 *
 * This class contains methods to save and read data
 */
public class SilkSpinDataSaving {

    String filename;

    public SilkSpinDataSaving() {            //by default, the data is saved to Userdat.ser file
        filename = "Userdat.ser";
    }

    public void changeFileForTests(String s) {   //Destination file can be changed here or by calling from RegisteredUsersLogic
        filename = s;
    }

    /**
     * Method that is used to save active data into a Userdat.ser file
     *
     * @param hm HashMap<String, User> that is being saved
     *
     *
     */
    public void write(HashMap<String, User> hm) {                //saves contents of given hashmap in a local file
        try {
            FileOutputStream fos
                    = new FileOutputStream(filename);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(hm);
            oos.close();
            fos.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * Method that allows reading the Userdat.ser file and storing the data into
     * a HashMap<>
     *
     *
     *
     * @return HashMap<String, User> containing the data from Userdat.ser file
     */
    public HashMap<String, User> read() {

        HashMap<String, User> hm = new HashMap<>();

        User nulluser = new User("no", "no");           //resets the null user on every save
        hm.put("no", nulluser);
        try {
            FileInputStream fis = new FileInputStream(filename);
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

    public String filenameReturnForPointlessReasonsForTest() {
        return filename;
    }
}
