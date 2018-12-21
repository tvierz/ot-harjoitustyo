/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package silkspinapp.silkspindataobjects;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * essentially a list of specified dataspec objects that have been planned as
 * expenditures
 *
 * @author tvierine
 */
public class BudgetPlan implements Serializable {

    double planned;
    ArrayList<DataSpec> n;

    /**
     * Constructor that is used to create BudgetPlan objects which store
     * DataSpec objects on an ArrayList
     *
     *
     *
     *
     */
    public BudgetPlan() {
        n = new ArrayList();        //whenever a new plan is created, a new list is made
    }

    /**
     * Method used for entering DataSpec objects onto the BudgetPlan
     *
     *
     *
     *
     */
    public void populateBudget(String s) {   //puts dataspec objects on the list
        String[] h = s.split(", ");
        DataSpec d = new DataSpec(Double.parseDouble(h[0]), h[1]);
        n.add(d);
    }

    public void addBudget(double d) {
        planned = d;
    }

    public double getBudgetPlanned() {
        return planned;
    }

    public ArrayList<DataSpec> getBudgetData() {
        return n;
    }

    public String toString() {
        String e = "";
        for (DataSpec entry : n) {
            e = e + entry.toString() + "\n";  //appends every entry on their own lines
        }
        return "Your budget is: \n" + e;
    }
}
