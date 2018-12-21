/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.GregorianCalendar;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import silkspinapp.logicandoperations.BudgetLogic;
import silkspinapp.silkspindataobjects.BudgetPlan;
import silkspinapp.silkspindataobjects.DataSpec;
import silkspinapp.silkspindataobjects.User;

/**
 *
 * @author tvierine
 */
public class BudgetLogicTest {
    BudgetPlan bp;
    int month;
    GregorianCalendar date = new GregorianCalendar();
    BudgetLogic bl;
    User u;
    DataSpec i;
    public BudgetLogicTest() {
    }
    
    
    @Before
    public void setUp() {
        bp = new BudgetPlan();
        bl = new BudgetLogic();
        u = new User("antti", "kala");
        bp.addBudget(10.0);
        i = new DataSpec(20.0, "jes");
        bp.populateBudget("20, jes");
        u.setBudget(bp);
        bl.selectBp(u);
        
        
    }
    @Test
    public void refreshFunctionsRight(){
        bl.refresh(u);
        u.getBpp().populateBudget("11.0, näin");                  //adds this value to new list
        assertEquals(u.getBpp().getBudgetData().get(0).getAmount() + "" , 11.0 + ""); //gets the first and supposedly only value if correct
    }
    @Test
    public void bpAssignedRight(){
        assertEquals(u.getBpp(), bl.getBudgetPlanOut());        //checks that the budget plan for logic is same as it is for the user
        
    }
    
    @Test
    public void bpReturnedRight(){
        
        assertEquals(bl.printBudget(), u.getBpp().toString());
    }
    @Test
    public void totalBudgetRight(){
        assertEquals(bl.returntotalbudget(u) + "", 20.0 + "");
    }
    @Test
    public void dataEnteredRight(){
        
        assertEquals(bl.enterData(u, "10.0, i"), "");
    }
    @Test
    public void dataEnteredWrong1(){
        
        assertEquals(bl.enterData(u, "10.0hahahahahhahaha, i"), "Please make sure your entry is in format: 'amount, type'");
    }
    @Test
    public void dataEnteredWrong2TooShort(){
        assertEquals(bl.enterData(u, "10.0"), "Please make sure your entry is in format: 'amount, type'");
    }
    @Test
    public void comparisonToUserRight(){
        u.setData("10, s");                     //user's budget is 20.0, here user adds 10.0 of spendings
        assertEquals(bl.compareToUsersSpending(u), "You have 10.0€ left of your budget");
    }
    @Test
    public void usersWrongAccountNotUsed(){
        u.changeAccount("29");
        assertEquals(bl.compareToUsersSpending(u), "Select existing account to compare");
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
