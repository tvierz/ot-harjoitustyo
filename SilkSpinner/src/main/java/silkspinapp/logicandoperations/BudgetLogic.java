/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package silkspinapp.logicandoperations;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;
import silkspinapp.silkspindataobjects.BudgetPlan;
import silkspinapp.silkspindataobjects.DataSpec;
import silkspinapp.silkspindataobjects.User;

/**
 * this class determines how budget operates
 *
 * @author tvierine
 */
public class BudgetLogic implements Serializable{

    BudgetPlan bp;
    int month;
    GregorianCalendar date = new GregorianCalendar();

    public BudgetLogic() {           //starts the class
        
    }

    public void refresh() {
        bp = new BudgetPlan();
    }

    public String printBudget() {  //returns string of a budgetplan specified

        return bp.toString();

    }
    public double returntotalbudget(User u){
        double g = 0;
        for(DataSpec d : u.getBpp().getBudgetData()){
            g = d.getAmount()+g;
        }
        return g;
    }

    public String enterData(User u, String data) {              //puts entered dataspec to the plan
        bp = u.getBpp();
        String[] dubs = data.split(", ");
        if (dubs.length == 3) {
            Scanner doubles = new Scanner(dubs[1]);
            if (doubles.hasNextDouble() == true) {
                bp.populateBudget(data);
                return "";
            } else {
                return "Please make sure your entry is in format: 'comment, amount, type'";
            }
        } else {
            return "Please make sure your entry is in format: 'comment, amount, type'"; //if the entered value doesn't have a double, it's false    
        }
    }

    public String compareToUsersSpending(User u) {       //summs the list of the budget and compares to user's total
        month = date.get(Calendar.MONTH) + 1;
        double difference = 0;
        for (DataSpec d : bp.getBudgetData()) {  //iterates through the list and adds all the values to total
            difference = difference + d.getAmount();
        }
        difference = difference - u.monthlyTotal();
        return "You have " + difference + " left of your budget";     //
    }

    public void newPlan(User u) {
        bp = new BudgetPlan();
        u.setBudget(bp);       //creates new plan to specified user
    }

}
