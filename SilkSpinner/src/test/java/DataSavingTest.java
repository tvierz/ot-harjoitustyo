/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;
import java.text.*;
import silkspinapp.silkspindataobjects.User;
import silkspinapp.logicandoperations.RegisteredUsersLogic;
import silkspinapp.logicandoperations.SilkSpinDataSaving;

/**
 *
 * @author tvierine
 */
public class DataSavingTest {

    RegisteredUsersLogic ru;
    User u;
    SilkSpinDataSaving s;

    public DataSavingTest() {

    }

    @Before
    public void setUp() {
        ru = new RegisteredUsersLogic();
        ru.changeFilenameForTests("testi.ser");
        ru.register("Kalmarimies", "kalastaja");    //creates new, empty user
        ru.login("Kalmarimies", "kalastaja");       //logs in the new user
        u = ru.getUser("Kalmarimies");         //creates dummy 'loggeduser' for the new account
        s = new SilkSpinDataSaving();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void newDataIsWrittenToFileAndThenReadCorrectly() {
        ru.save();                                  // saves the data with 1 user on the app
        ru.register("kille", "dille");              //creates bunch of accounts
        ru.register("nille", "tille");
        ru.initialize();                            //doesn't save the 2 accounts created previously but reads the save instead
        assertEquals(ru.getListMap().size(), 1);    //userlist should only have a single entry because the 2 dummy accounts weren't saved
    }

    @Test
    public void newDataIsWrittenToFileAndThenReadCorrectlyConfirmationToSaveTheExtraAccounts() {

        ru.save();                                  // saves the data with 1 user on the app
        ru.register("kille", "dille");              //creates bunch of accounts
        ru.register("nille", "tille");
        ru.save();                                  //saves the accounts this time
        ru.initialize();                            //reads the save 
        assertEquals(ru.getListMap().size(), 3);    //userlist should have 3 entries now, because the 2 extra accounts were saved
    }

    @Test
    public void newUsersDataIsSavedAndReadCorrectly() {
        ru.enterData(u, "2, crikey m8");                                        //this should get saved
        ru.save();
        ru.initialize();
        assertEquals(ru.getUser("Kalmarimies").getdataEntries().size(), 1);     // user should have a single saved data entry that was read
    }

    @Test
    public void newUsersDataIsReadCorrectlyAndIsLegible() {                  //tests that unsaved points don't appear to read data and that the data is read so it's information isn't lost
        ru.enterData(u, "20, Hilperttihööri");      //enters a single data entry to the testuser
        ru.initialize();
        assertEquals(ru.getUser("Kalmarimies").getdataEntries().get(1).toString(), "Date of entry: 21.12.2018 AMOUNT: 2.0€ TYPE OF EXPENSE: crikey m8");     //the user should only have the data entry from previous test
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
