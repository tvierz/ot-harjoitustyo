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

/**
 *
 * @author tvierine
 */
public class UserTest {

    User u;

    @Before
    public void setUp() {
        u = new User("antti", "tuisku");
    }

    @Test
    public void userHasName() {
        assertEquals(u.getUsername(), "antti");
    }

    @Test
    public void userHasPassword() {
        assertEquals(u.getPassword(), "tuisku");
    }

    @Test
    public void userHasNoDataAtStart() {
        assertEquals(u.getData(), "Data starts:");
    }

    @Test
    public void userHasDataWhenAdded() {
        u.setData("u");
        assertEquals(u.getData(), "Data starts: u");
    }

    @Test
    public void userDataAppendsCorrectly() {
        u.setData("u");
        u.setData("u");
        assertEquals(u.getData(), "Data starts: u u");
    }

    @Test
    public void userSafewordCorrectAtStart() {
        assertEquals(u.getSafeword(), "onlysuperadmingodknowthispasswordimthebesthahahahahahahaa4329832576");
    }

    @Test
    public void userSafewordCanBeChanged() {
        u.setSafeword("n");
        assertEquals(u.getSafeword(), "n");
    }

    @Test
    public void userStatusIsOneAtStart() {
        assertEquals(u.getStatus(), 1);
    }

    @Test
    public void accountsCreatedRight() {
        u.createaccount();
        u.changeAccount(2);
        assertEquals(u.getStatus(), 2);
    }

    @Test
    public void toStringRight() {
        assertEquals(u.toString(), "s");
    }

    @Test
    public void setDataOnAccountRight() {
        u.createaccount();
        u.changeAccount(2);
        u.setData("k");
        assertEquals(u.getData(), "Data starts: k");

    }

    @Test
    public void setDataNotContaminateOtherAccounts() {

        u.createaccount();
        u.changeAccount(2);
        u.setData("k");
        u.changeAccount(1);
        assertEquals(u.getData(), "Data starts:");
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
