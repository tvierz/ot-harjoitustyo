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
 * this class contains methods to modify and manipulate details of BudgetPlan
 * and save it to User class
 *
 * @author tvierine
 */
public class BudgetLogic implements Serializable {

    BudgetPlan bp;
    int month;
    GregorianCalendar date = new GregorianCalendar();

    /**
     * Empty constructor that initializes the class
     *
     *
     *
     *
     */
    public BudgetLogic() {           //starts the class

    }

    /**
     * Method that allows refreshing the BudgetPlan class, erasing all data on
     * it by creating new one
     *
     *
     *
     *
     */
    public void refresh() {
        bp = new BudgetPlan();
    }

    /**
     * Prints out String representation of the BudgetLogic class
     *
     *
     *
     * @return string representation of toString methods of DataSpec objects on
     * BudgetPlan
     */
    public String printBudget() {  //returns string of a budgetplan specified

        return bp.toString();

    }

    /**
     * Method that allows calling the total value of user's budget
     *
     * @param u User that is logged in
     *
     * @return combined double value of all DataSpec objects contained in the
     * User's BudgetPlan
     */
    public double returntotalbudget(User u) {
        double g = 0;
        for (DataSpec d : u.getBpp().getBudgetData()) {
            g = d.getAmount() + g;
        }
        return g;
    }

    /**
     * Method that is used to enter DataSpec objects on the User's BudgetPlan
     *
     * @param u User whose dataplan is being appended to
     * @param data String version of data to be saved
     *
     * @return String representing success or failure of the event
     */
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

    /**
     * Method used to check User's budget with their BudgetPlan
     *
     * @param u User whose data is used
     *
     * @return double representing difference between BudgetPlan's total value
     * and User's active account s total value for current month
     */
    public String compareToUsersSpending(User u) {       //summs the list of the budget and compares to user's total
        month = date.get(Calendar.MONTH) + 1;
        double difference = 0;
        for (DataSpec d : bp.getBudgetData()) {  //iterates through the list and adds all the values to total
            difference = difference + d.getAmount();
        }
        difference = difference - u.monthlyTotal();
        return "You have " + difference + " left of your budget";     //
    }

}
