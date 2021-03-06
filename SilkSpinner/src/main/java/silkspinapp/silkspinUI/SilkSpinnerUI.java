/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package silkspinapp.silkspinUI;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.*;
import silkspinapp.silkspindataobjects.User;
import silkspinapp.logicandoperations.RegisteredUsersLogic;
import silkspinapp.logicandoperations.SilkSpinDataSaving;
import java.util.*;
import java.text.*;
import silkspinapp.logicandoperations.BudgetLogic;

/**
 *Check date for un: Kalle, pass: kala account entries also budget page entries
 * @author tvierine
 */
//this class creates the UI for the app
public class SilkSpinnerUI extends Application {

    private Scene registryScreen;                                          
    private Scene loginScreen;                                              
    private Scene userScene;
    private Scene accountScene;
    private Scene settingScene;
    private final RegisteredUsersLogic ru = new RegisteredUsersLogic();     // operations pertaining to userdata as well as saving and reading the given data
    private User loggeduser;                                                //signifies user that has logged in
    private BudgetLogic bl = new BudgetLogic();                             //logic used relating to budget operations
    private Scene budgetScene;                                              //budget screen that is created later

    private Label menuEntries = new Label();

 

    @Override
    public void start(Stage primaryStage) {
        //starts the app
        ru.initialize();        //loads saved user profiles to the registeredusers

        //creates the values used for loginscreen
        //_______LOGINSCREEN VALUES___________________________________________________________
        VBox loggingscreenBg = new VBox(20);
        VBox userPass = new VBox(20);
        HBox passInput = new HBox(10);
        HBox userInput = new HBox(10);
        Label loginUser = new Label("Enter Username here: "); //gives label to username textbox
        Label passLabel = new Label("Enter Password here: "); //labels the password box
        TextField insertUser = new TextField();
        PasswordField insertPass = new PasswordField();

        userInput.getChildren().addAll(loginUser, insertUser);
        passInput.getChildren().addAll(passLabel, insertPass);

        //create loginbutton in the screen
        Label loginLabel = new Label("Today's date is: " + ru.showDate());// label describing actions in loginscreen
        Label creation = new Label();//label describing actions in registration screen

        Button loginbtn = new Button("Login");
        Button registerButton = new Button("Register new account");
        loginbtn.setDefaultButton(true);    //loginbutton is pressed using enter when app starts
        loginbtn.setOnAction(a -> {                             //attempt to log in
            String usersname = insertUser.getText();            //gets the entire username entered
            String password = insertPass.getText();             //gets the entire password

            loggeduser = ru.login(usersname, password);
            if (loggeduser.getUsername().equals("no")) {
                loginLabel.setText("Entered username or password is incorrect"); //tells that login attempt failed
            } else {
                insertUser.setText("");//cleans account and password boxes so they are clear on logout
                insertPass.setText("");
                loginLabel.setText("Entering");
                menuEntries.setText(usersname + " you have logged in on your account number: " + loggeduser.getStatus()); //tells user they have logged in
                primaryStage.setScene(userScene);                       // swaps to loginscreen of the user

            }
        });

        loggingscreenBg.getChildren().addAll(loginLabel, userInput, passInput, loginbtn, registerButton);//adds the textboxes and buttons to loginscreen
        loginScreen = new Scene(loggingscreenBg, 600, 400);                              //tells how loginscreen looks like
        //__________________LOGINSCREEN MAIN END__________________________________________________________________________  

        //________________REGISTRY SCREEN START___________________________________________________________________
        //registry screen textbox labels
        Label newuser = new Label("Enter your desired username: ");
        Label newuserpass = new Label("Enter your desired password");
        registerButton.setOnAction(a -> {                   //clears textboxes from registration screen
            creation.setText("");
            insertUser.setText("");
            insertPass.setText("");
            primaryStage.setScene(registryScreen);
        });

        //creates base for registration screen
        VBox regginscreen = new VBox(20);
        VBox register = new VBox(20);
        HBox registerus = new HBox(10);
        HBox registpas = new HBox(10);
        TextField newusenter = new TextField(); //entry for username (registry textboxes)
        TextField newuspass = new TextField(); //entry for password

        Button registbtn = new Button("Register");
        Button returnbtn = new Button("Return to login");

        returnbtn.setOnAction(a -> {                //returns back to login screen and resets labels
            loginLabel.setText("");
            newusenter.setText("");
            newuspass.setText("");
            primaryStage.setScene(loginScreen);
        });

        registerus.getChildren().addAll(newuser, newusenter);      //connects HBox and text field for username
        registpas.getChildren().addAll(newuserpass, newuspass);      //connects HBox and text field for password

        registbtn.setOnAction(a -> {
            creation.setText("");
            String username = newusenter.getText();
            String password = newuspass.getText();

            creation.setText(ru.register(username, password));   //attempts to register user with their input and sets the registry screen text to reflect how the registration went
            ru.save();      //saves the current list, nothing is changed if registry didn't succeed however
        });

        regginscreen.getChildren().addAll(creation, registerus, registpas, registbtn, returnbtn);   //puts registration screen items on the VBox
        registryScreen = new Scene(regginscreen, 600, 400);                                          //puts registration VBox onto the screen
        //_________________REGISTRY SCREEN END________________________________________________________________________

        //creates buttons and their functions for user that has logged in, exceptions being text entry confirmation buttons
        //______________USER BUTTONS START________________________________________________
        Button accountbtn = new Button("Account management");       // enters logged user to their accounts
        Button settingsbtn = new Button("Settings");                //enters the user to their settings
        Button logoutbtn = new Button("logout");
        Button backbtn1 = new Button("Back to userscreen");         //creates button returning user to their account page from accounts
        Button backbtn2 = new Button("Back to userscreen");          //backbutton to userscreen from settings
        Button displaybtn = new Button("Display your account data");
        Button enterbtn = new Button("Enter data to your account");
        Button safebtn = new Button("Confirm safeword");
        Button removebtn = new Button("Permanently remove your account and all data on it");
        Button newaccount = new Button("Create new account"); // button for new account
        Button monthlytotalbtn = new Button("Get this month's total spendings");
        Button budgetpagebtn = new Button("Enter budget page");
        Button enterbudgetbtn = new Button("Enter item on your budget plan");
        Label displayacc = new Label();
        ScrollPane scrollingaccountdisplay = new ScrollPane(displayacc);        //utilizes scrollable window to display account data that exceeds the window's standard size

        //creates new account functionality
        Label displayaccountno = new Label("Select your account number: ");
        TextField selectaccount = new TextField();
        Button selectaccountbtn = new Button("Select account");
        Button createaccountbtn = new Button("Create new account");

        budgetpagebtn.setOnAction(a -> {
            primaryStage.setScene(budgetScene); //hops to budget screen
            bl.selectBp(loggeduser);            // selects the budget plan of logged user to be used on the budget page
            enterbudgetbtn.setDefaultButton(true);  //dataentry button  on budget page used by default when pressing enter on entering budget screen
        });
        createaccountbtn.setOnAction(a -> {
            loggeduser.createaccount();         //creates account for currently logged user
        });
        monthlytotalbtn.setOnAction(a -> {
            String n = "This month's total spendings: " + loggeduser.monthlyTotal() + "€";
            menuEntries.setText(n);

        });
        selectaccountbtn.setOnAction(a -> {
            menuEntries.setText(loggeduser.changeAccount(selectaccount.getText()));           //clears all labels from accountscreen and changes account if entry valid
            displayacc.setText("");             //
        });

        settingsbtn.setOnAction(a -> {                                //gives functionality to the buttons in userscreen
            menuEntries.setText("Set your safeword and press confirm to link it to your account");
            primaryStage.setScene(settingScene);
        });
        removebtn.setOnAction(a -> {
            primaryStage.setScene(loginScreen);                 //logs user out and removes their account
            ru.removeUser(loggeduser.getUsername());
            ru.save();                                          //saves the registeredusers so that the removal is permanent
            displayacc.setText("");
            loggeduser = null;                                           //nulls logged user
            loginLabel.setText("Account succesfully removed, goodbye");  //displays succsessful account removal in login screen
        });
        accountbtn.setOnAction(a -> {
            enterbtn.setDefaultButton(true);  //dataentry button on accounts page used by default when pressing enter on entering accounts page
            primaryStage.setScene(accountScene);
        });
        backbtn1.setOnAction(a -> {
            menuEntries.setText("Select your account here");
            primaryStage.setScene(userScene);                      //returns user from accounts to main screen

        });
        backbtn2.setOnAction(a -> {
            menuEntries.setText("Select your account here");
            primaryStage.setScene(userScene);                      //returns user from accounts to main screen (it seems using same button twice disables it from the first part
        });
        logoutbtn.setOnAction(a -> {                                //logs user out back to the loginscreen
            ru.save();                                      //saves data user has given locally
            primaryStage.setScene(loginScreen);
            displayacc.setText("");                                 // clears displaywindow after logout
            loggeduser = null;
            loginLabel.setText("Logged out");                                 //Notifies of logout
            
            loginbtn.setDefaultButton(true);    //loginbutton is set to be pressed on enter when logged out
        });
        displaybtn.setOnAction(a -> {
            displayacc.setText(loggeduser.getData());               //displays userdata on request from display button
        });
        //____USER BUTTONS END________________________________________

        //This part creates the screen for users that have logged in
        //___________LOGGED IN SCREENS START___________________________________________________________________
        VBox userscreen = new VBox(20);
        VBox accountScreen = new VBox(20);
        VBox settingScreen = new VBox(20);
        VBox budgetScreen = new VBox(20);
        //creates general user screen
        HBox userss = new HBox(20);
        userscreen.getChildren().addAll(userss, menuEntries, selectaccount, selectaccountbtn, logoutbtn, accountbtn, settingsbtn, budgetpagebtn);
        userScene = new Scene(userscreen, 600, 400);

        //creates accounts screen
        HBox accountss = new HBox(20);
        TextField dataentry = new TextField();
        accountScreen.getChildren().addAll(accountss, menuEntries, dataentry, scrollingaccountdisplay, enterbtn, displaybtn, backbtn1, monthlytotalbtn);      //connects HBox and text field for data entry
        
        enterbtn.setOnAction(a -> {                                          //functionalizes data entry button
            menuEntries.setText(ru.enterData(loggeduser, dataentry.getText()));       //saves entered data to user's currently specified account and gives message describing entry event
            ru.save();
            displayacc.setText(loggeduser.getData()); //refreshes the account display with entered data
            dataentry.setText("");

        });
        accountScene = new Scene(accountScreen, 600, 400);

        //creates settings screen
        HBox settingss = new HBox(10);
        TextField safe = new TextField();
        settingScreen.getChildren().addAll(settingss, createaccountbtn, safe, safebtn, backbtn2, removebtn);// puts things into the VBox

        safebtn.setOnAction(a -> {                                  // functionalizes safeword entry button
            loggeduser.setSafeword(safe.getText());
            ru.save();
            safe.setText("");
        });
        settingScene = new Scene(settingScreen, 600, 400);              //puts VBox into the display (600x400)

        //%% budget Manibulation screen
        HBox budgetplannerHB = new HBox(10);
        TextField budgetdataentry = new TextField();
        Label budgetlisted = new Label();
        ScrollPane budgetpanel = new ScrollPane(budgetlisted);          //sets the budget window to be a scroll pane so it can be used to display budget entries better
        Label entryopsbudget = new Label("Do you need to make a new budget plan for the month? Today's date is: " + ru.showDate());
        
        Button comparetousedbtn = new Button("Compare your monthly spendings to your budget");
        Button backbtn3 = new Button("Return to user screen");      //yet another back button since they stop working somewhere if you put them on multiple VBoxes
        Button refreshbudgetbtn = new Button("Clear budget plan");      //clears the active budget plan so new one can be made
        budgetScreen.getChildren().addAll(budgetplannerHB, budgetpanel, entryopsbudget, enterbudgetbtn, budgetdataentry, comparetousedbtn, backbtn3, refreshbudgetbtn);
        budgetScene = new Scene(budgetScreen, 600, 400);
        
        enterbudgetbtn.setOnAction(a -> {
            entryopsbudget.setText(bl.enterData(loggeduser, budgetdataentry.getText())); //tells how the operation of adding to budget went

            budgetlisted.setText(bl.printBudget() + "\n Total budget for the month is:" + bl.returntotalbudget(loggeduser) + "€");       //refreshes the budgetplan with every entry, so User can see their plan
            budgetdataentry.setText("");    //cleans the text field after data entry
        });
        
        refreshbudgetbtn.setOnAction(a -> {
            bl.refresh(loggeduser);
            budgetlisted.setText("Latest budget plan cleared, add new budget entries");
        });
        comparetousedbtn.setOnAction(a -> {     //sets the banner to tell user how much of their budget they have used
            budgetlisted.setText(bl.compareToUsersSpending(loggeduser));
        });
        backbtn3.setOnAction(a -> {
            primaryStage.setScene(userScene);
        });
//________LOGGED IN SCREENS END___________________________________________________
        //creates the screen user sees
        primaryStage.setTitle("Silk Spinner, spinning your silk");
        primaryStage.setScene(loginScreen);
        primaryStage.show();

    }

    @Override
    public void stop() {
        //closes the app
        ru.save(); //saves data in case user decides to close the app instead of logging out
        System.out.println("Ending the app, and your finances");

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
