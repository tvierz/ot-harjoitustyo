/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package silkspinapp.silkspinUI;

import java.io.File;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import silkspinapp.User;
import silkspinapp.RegisteredUsers;
import silkspinapp.SilkSpinLogic;
import java.util.*;
import java.text.*;

/**
 *
 * @author tvierine
 */
//this class creates the UI for the app
public class SilkSpinnerUI extends Application {

    private Scene registryScreen;
    private Scene loginScreen;
    private Scene userScene;
    private Scene accountScene;
    private Scene settingScene;
    private final RegisteredUsers ru = new RegisteredUsers();
    private User loggeduser;
    private File f;

    private Label menuEntries = new Label();
    

    public void startup() throws Exception {
        Properties input = new Properties();
        input.load(new FileInputStream("Configure"));

        String userData = input.getProperty("user");
        String userOwned = input.getProperty("userLog");

    }

//    public Node createNodes(){
//        
//    }
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
        TextField insertPass = new TextField();

        userInput.getChildren().addAll(loginUser, insertUser);
        passInput.getChildren().addAll(passLabel, insertPass);

        //create loginbutton in the screen
        Label loginLabel = new Label("Today's date is: " + ru.showDate());// label describing actions in loginscreen
        Label creation = new Label();//label describing actions in registration screen

        Button loginbtn = new Button("Login");
        Button registerButton = new Button("Register new account");
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

        returnbtn.setOnAction(a -> {                //returns back to login screen
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
        Label displayacc = new Label();

        //creates new account functionality
        Label displayaccountno = new Label("Select your account number: ");

        TextField selectaccount = new TextField();
        Button selectaccountbtn = new Button("Select account");
        Button createaccountbtn = new Button("Create new account");

        createaccountbtn.setOnAction(a -> {
            loggeduser.createaccount();         //creates account for currently logged user

        });
        monthlytotalbtn.setOnAction(a ->{
//            System.out.println(loggeduser.a());
            String n = "This month's total spendings: " + loggeduser.monthlyTotal();
            menuEntries.setText(n);
            
        });
        selectaccountbtn.setOnAction(a -> {

            loggeduser.changeAccount(Integer.parseInt(selectaccount.getText()));
            menuEntries.setText("");            //clears all labels from accountscreen
            displayacc.setText("");             //

        });

        settingsbtn.setOnAction(a -> {                                //gives functionality to the buttons in userscreen
            menuEntries.setText("Set your safeword and press confirm to link it to your account");
            primaryStage.setScene(settingScene);
        });
//        newaccount.setOnAction(a ->{
//            oggeduser.
//        });
        removebtn.setOnAction(a -> {
            primaryStage.setScene(loginScreen);                 //logs user out and removes their account
            ru.removeUser(loggeduser.getUsername());
            ru.save();                                          //saves the registeredusers so that the removal is permanent
            displayacc.setText("");
            loggeduser = null;                                           //nulls logged user
            loginLabel.setText("Account succesfully removed, goodbye");  //displays succsessful account removal in login screen
        });
        accountbtn.setOnAction(a -> {
            primaryStage.setScene(accountScene);
        });
        backbtn1.setOnAction(a -> {
            primaryStage.setScene(userScene);                      //returns user from accounts to main screen
//            spin.dump(ru.getListMap());                // dumps given list into a file for local storage// clears account data entry text panel after data entry
//        
        });
        backbtn2.setOnAction(a -> {
            primaryStage.setScene(userScene);                      //returns user from accounts to main screen (it seems using same button twice disables it from the first part
        });
        logoutbtn.setOnAction(a -> {                                //logs user out back to the loginscreen
            ru.save();                                      //saves data user has given locally
            primaryStage.setScene(loginScreen);
            displayacc.setText("");                                 // clears displaywindow after logout
            loggeduser = null;
            loginLabel.setText("Logged out");                                 //Notifies of logout
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
        //creates general user screen
        HBox userss = new HBox(20);
        userscreen.getChildren().addAll(userss,menuEntries, selectaccount, selectaccountbtn, logoutbtn, accountbtn, settingsbtn);
        userScene = new Scene(userscreen, 600, 400);

        //creates accounts screen, which currently consists out of textbox that enters data, and a label that displays entered data
        HBox accountss = new HBox(20);
        TextField dataentry = new TextField();
        accountScreen.getChildren().addAll(accountss, menuEntries, dataentry, displayacc, enterbtn, displaybtn, backbtn1, monthlytotalbtn);      //connects HBox and text field for data entry
        enterbtn.setOnAction(a -> {                                          //functionalizes data entry button
            menuEntries.setText(ru.enterData(loggeduser, loggeduser.getStatus(), dataentry.getText()));       //saves entered data to user's currently specified account and gives message describing entry event
            
            ru.save();
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

        //________LOGGED IN SCREENS END___________________________________________________
        //creates the screen user sees
        primaryStage.setTitle("Silk Spinner, spinning your silk");
        primaryStage.setScene(loginScreen);
        primaryStage.show();

    }

    @Override
    public void stop() {
        //closes the app
        ru.save();//saves data in case user decides to close the app instead of loggin out
        System.out.println("Ending the app, and your finances");

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
