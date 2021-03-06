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

/**
 *
 * @author tvierine
 */
public class RegisteredUsersTest {

    public User test;
    public RegisteredUsersLogic ru;
    Date now;
    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");      //determines the displayed date
    String returnval;
    String filename;

    @Before
    public void setUp() {                       //initializes tests
        test = new User("antti", "kala");
        ru = new RegisteredUsersLogic();
        ru.listUser(test);                      //user added to list
        ru.listUser(new User("no", "no"));
        now = new Date();               //fetches current date upon being constructed
        returnval = sdf.format(now);

    }

    @Test
    public void fileNameChangesRight() {         //if filename changes correctly, only "no" user should be present on the list
        ru.save();                              //saves the data to currently active file
        ru.changeFilenameForTests("embtii.ser");            //changed the data storage file into "Testfile.ser"
        ru.initialize();                        //loads the empty file
        assertEquals(ru.getListMap().size(), 1); //userdata should now only have a single entry, the default "no" user
    }

    @Test
    public void dateIsCorrect() {
        assertEquals(returnval, ru.showDate());
    }

    @Test
    public void userLogsInRight() {
        assertEquals(ru.login("antti", "kala"), test);
    }

    @Test
    public void userCanLogWithSafeword() {
        test.setSafeword("nnii");
        assertEquals(ru.login("antti", "nnii"), test);
    }

    @Test
    public void unknownUserCantLogIn() {
        assertEquals(ru.login("k", "t").getUsername(), "no");
    }

    @Test
    public void userCanRegister() {
        assertEquals(ru.register("kilon", "siika"), "Account has been created successfully");
    }

    @Test
    public void userCanBeListed() {              //if new user can be added into registry, test passes
        ru.listUser(new User("hahaa", "krökkels"));
        assertTrue(ru.freeUser("hahaa"));
    }

    @Test
    public void multipleSameUsernamesCantList() {
        assertEquals(ru.listUser(test), "user is already listed");
    }

    @Test
    public void tooShortPasswordNotAccepted() {
        assertEquals(ru.register("kilon", "si"), "Your password is too weak");

    }

    @Test
    public void takenUsernameNotAccepted() {
        assertEquals(ru.register("antti", "siiika"), "This username is already taken or your username is shorter than 4 characters");
    }

    @Test
    public void tooShortUsernameNotAccepted() {
        assertEquals(ru.register("SI", "lon"), "your username is too short");

    }

    @Test
    public void mapReturnedRightWithObjectsOnIt() {
        assertEquals(ru.getListMap().get("antti"), test);
    }

    @Test
    public void mapEmptyWhenEmptied() {
        ru.getListMap().clear();
        assertEquals(ru.getUser("antti"), null);
    }

    @Test
    public void enterDataRequiresParseableIntoDoubleAsSecondValue() {
        assertEquals(ru.enterData(test, "1, n"), "Data has been entered");

    }

    @Test
    public void dataEntryGoesRight() {
        ru.enterData(test, "8005, l");
        assertEquals(test.getdataEntries().get(1).getAmount() + "", 8005.0 + "");
    }

    @Test
    public void enterDataRejectsNonDoubleParseableValue() {
        assertEquals(ru.enterData(test, "EITOIMI, n"), "Please make sure your entry is in format: 'amount, type'");

    }

    @Test
    public void userCalledRight() {
        assertEquals(ru.getUser("antti"), test);
    }

    @Test
    public void multipleUsernamesNotAllowed() {  //tests that multiple users cannot register with same username
        assertEquals(ru.listUser(test), "user is already listed");
    }

    @Test
    public void dataWrittenRightOnRightAccountOfUser() {
        ru.enterData(test, "3.0");
        ru.getUser(test.getUsername()).createaccount();
        ru.getUser(test.getUsername()).changeAccount(2 + "");
        ru.enterData(test, "5.08");
        assertEquals(test.getData(), "You spun this silk: ");
    }

    @Test
    public void userCanBeCalledFromMapByUsername() {
        assertEquals(ru.getUser(test.getUsername()), test);     //calls user "test" from the list and compares it to the user test
    }

    @Test
    public void userCanBeRemovedFromMapByUsername() {
        ru.removeUser(test.getUsername());
        assertFalse(ru.freeUser(test.getUsername()));
    }

    @After
    public void tearDown() {

    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
