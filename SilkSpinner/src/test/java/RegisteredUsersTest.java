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
import silkspinapp.User;
import silkspinapp.RegisteredUsers;

/**
 *
 * @author tvierine
 */
public class RegisteredUsersTest {

    public User test;
    public RegisteredUsers ru;

    @Before
    public void setUp() {                       //initializes tests
        test = new User("antti", "kala");
        ru = new RegisteredUsers();
        ru.listUser(test);                      //user added to list
        ru.listUser(new User("no", "no"));
    }

    @Test
    public void userLogsInRight() {
        assertEquals(ru.login("antti", "kala"), test);
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
    public void userCanBeListed() {              //if user can be added into registry, test passes
        ru.listUser(test);
        assertTrue(ru.freeUser(test.getUsername()));
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
    public void multipleUsernamesNotAllowed() {  //tests that multiple users cannot register with same username
        assertEquals(ru.listUser(test), "user is already listed");
    }

    @Test
    public void dataWrittenRightOnRightAccountOfUser() {
        ru.enterData(test, 1, "kaija");
        ru.getUser(test.getUsername()).createaccount();
        ru.getUser(test.getUsername()).changeAccount(2);
        ru.enterData(test, 2, "n");
        assertEquals(test.getData(), "Data starts: n");
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
