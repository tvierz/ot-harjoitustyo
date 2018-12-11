/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package silkspinapp.silkspindataobjects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 *essentially a list of specified dataspec objects that have been planned as expenditures
 * @author tvierine
 */
public class BudgetPlan implements Serializable{
    double planned;
    private ArrayList<DataSpec> n;
    
    public BudgetPlan(){
        n = new ArrayList();        //whenever a new plan is created, a new list is made
    }
    
    public void populateBudget(String s){   //puts dataspec objects on the list
        String[] h = s.split(", ");
        DataSpec d = new DataSpec(h[0], Double.parseDouble(h[1]), h[2]);
        n.add(d);
    }
    public void addBudget(double d){
        planned = d;
    }
    public ArrayList<DataSpec> getBudgetData(){
        return n;
    }
    public String toString(){
        String e = "";
        for(DataSpec entry : n){
            e = e + entry.toString()+"\n";  //appends every entry on their own lines
        }
        return "Your budget is: \n" + e;
    }
}
