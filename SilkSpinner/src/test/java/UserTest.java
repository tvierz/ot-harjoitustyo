/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import java.io.*;
import java.nio.file.Files;
import static org.junit.Assert.*;
import silkspinapp.silkspindataobjects.BudgetPlan;
import silkspinapp.silkspindataobjects.DataSpec;
import silkspinapp.silkspindataobjects.User;

/**
 *
 * @author tvierine
 */
public class UserTest {

    User u;
    DataSpec use;           // null entry on the data list
    int month;
    Date now;
    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");      //determines the displayed date
    String date;

    @Before
    public void setUp() {
        u = new User("antti", "tuisku");
        now = new Date();               //confirms date on creation of entry, again
        date = sdf.format(now);
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
        u.setData("2, n");
        n = u.getdataEntries();
        assertEquals(n.get(1).toString(),"Date of entry: " + date + " AMOUNT: 2.0€ TYPE OF EXPENSE: n");

    }

    @Test
    public void userHasDataWhenAdded() {
        u.setData("2, n");
        assertEquals(u.getData(), "You spun this silk: \n"
                + "Date of entry: " + date + " AMOUNT: 2.0€ TYPE OF EXPENSE: n");
    }

    @Test
    public void userDataStatusNegativeWhenAccountDoesnotExist() {
        u.changeAccount(2+"");
        assertEquals(u.getStatus(), -1);
    }

    @Test
    public void userDataAppendsCorrectly() {

        u.setData("5, l");
        u.setData("2, n");
        assertEquals(u.getData(), "You spun this silk: \n"
                + "Date of entry: " + date + " AMOUNT: 5.0€ TYPE OF EXPENSE: l\n"
                + "Date of entry: " + date + " AMOUNT: 2.0€ TYPE OF EXPENSE: n");
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
        u.changeAccount(2+"");
        assertEquals(u.getStatus(), 2);
    }
    @Test
    public void userBpCanBeAssignedAndIsCorrect(){
        BudgetPlan bp = new BudgetPlan();
        bp.populateBudget("20, y");
        u.setBudget(bp);
        assertEquals(u.getBpp().getBudgetData().get(0).getAmount() + "", 20.0 + "");
    }
    @Test
    public void accountChangeWorksCorrectlyAndAccountCanBeCreated(){
        u.createaccount();
        assertEquals(u.changeAccount("2"), "Your account number 2 has been selected");
        
    }
    @Test
    public void wrongAccountTellsFault(){
        assertEquals(u.changeAccount("29"), "Account number 29 doesn't exist, make sure you entered correct account number");
    }
    

    @Test
    public void toStringRight() {
        assertEquals(u.toString(), u.getUsername());
    }

    @Test
    public void setDataOnAccountRight() {
        u.createaccount();
        u.changeAccount(2+"");
        u.setData("2, n");
        assertEquals(u.getData(), "You spun this silk: \n"
                +"Date of entry: " + date + " AMOUNT: 2.0€ TYPE OF EXPENSE: n");

    }

    @Test
    public void setDataNotContaminateOtherAccounts() {

        u.createaccount();
        u.changeAccount(2+"");
        u.setData("2, n");
        u.changeAccount(1+"");
        u.setData("5, l");
        assertEquals(u.getData(), "You spun this silk: \n"
                +"Date of entry: " + date + " AMOUNT: 5.0€ TYPE OF EXPENSE: l");
    }

    @Test
    public void monthlyTotalIsRight() {
        u.setData("2.0, y");
        u.setData("5.0, t");
        String test = u.monthlyTotal() + "";
        assertEquals(test, "7.0");
    }
    @Test
    public void monthlyTotalIsWrong() {
        u.setData("2.0, y");
        u.setData("5.0, t");
        u.changeAccount("29");
        assertEquals(u.monthlyTotal() + "", 0.0 + "");
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
