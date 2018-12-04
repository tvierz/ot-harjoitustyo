/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.GregorianCalendar;
import java.util.HashMap;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import silkspinapp.DataSpec;
import silkspinapp.User;

/**
 *
 * @author tvierine
 */
public class UserTest {

    User u;
    DataSpec use;           // null entry on the data list
    int month;
    GregorianCalendar date = new GregorianCalendar();

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
        assertEquals(u.getData(), "You spun this silk: ");
    }

    @Test
    public void hashMapReturnedRight() {
        HashMap<Integer, DataSpec> n = new HashMap<>();
        u.setData("k, 2, n");
        n = u.getdataEntries();
        assertEquals(n.get(1).toString(), "04.12.2018: k AMOUNT: 2.0 TYPE OF EXPENSE: n");

    }

    @Test
    public void userHasDataWhenAdded() {
        u.setData("k, 2, n");
        assertEquals(u.getData(), "You spun this silk: \n"
                + "04.12.2018: k AMOUNT: 2.0 TYPE OF EXPENSE: n");
    }

    @Test
    public void userDataNullsWhenAccountDoesnotExist() {
        u.changeAccount(2);
        assertEquals(u.getdataEntries(), null);
    }

    @Test
    public void userDataAppendsCorrectly() {

        u.setData("o, 5, l");
        u.setData("k, 2, n");
        assertEquals(u.getData(), "You spun this silk: \n"
                + "04.12.2018: o AMOUNT: 5.0 TYPE OF EXPENSE: l\n"
                + "04.12.2018: k AMOUNT: 2.0 TYPE OF EXPENSE: n");
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
        u.setData("k, 2, n");
        assertEquals(u.getData(), "You spun this silk: \n"
                + "04.12.2018: k AMOUNT: 2.0 TYPE OF EXPENSE: n");

    }

    @Test
    public void setDataNotContaminateOtherAccounts() {

        u.createaccount();
        u.changeAccount(2);
        u.setData("k, 2, n");
        u.changeAccount(1);
        u.setData("o, 5, l");
        assertEquals(u.getData(), "You spun this silk: \n"
                + "04.12.2018: o AMOUNT: 5.0 TYPE OF EXPENSE: l");
    }

    @Test
    public void monthlyTotalIsRight() {
        u.setData("k, 2.0, y");
        u.setData("n, 5.0, t");
        String test = u.monthlyTotal() + "";
        assertEquals(test, "7.0");
    }

    @Test
    public void monthlyTotalIsZeroAtStart() {
        assertEquals(u.monthlyTotal() + "", 0, 0);
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
