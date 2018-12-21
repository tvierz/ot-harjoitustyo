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
import silkspinapp.silkspindataobjects.BudgetPlan;
import silkspinapp.silkspindataobjects.DataSpec;

/**
 *
 * @author tvierine
 */
public class BudgetPlanTest {
    BudgetPlan bp;
    DataSpec d;
    
    public BudgetPlanTest() {
    }
    
    @Before
    public void setUp() {
        bp = new BudgetPlan();
        bp.addBudget(20.0);
        d = new DataSpec(20.0, "yy");
        bp.populateBudget("20, yy");
    }
    @Test
    public void bpCanBePopulatedRight(){
        bp.populateBudget("50, ll");                    //adds entry
        assertEquals(bp.getBudgetData().size(), 2);     //there should now be 2 entries, before tests entry and this method's entry
    }
    @Test
    public void bpToStringRight(){
        assertEquals(bp.toString(), "Your budget is: \n" + d.toString() + "\n");
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
