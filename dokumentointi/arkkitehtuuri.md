#### Pakkauskaavio
Ohjelman rakenne voidaan nähdä allaolevasta kuvasta, ohjelma koostuu kolmesta pakkauksesta, silkspinUI, silkspindataobjects ja logicandoperations. Nimien mukaisesti silkspinUI koostuu ohjelman käyttöliittymän rakentavasta javaFX luokasta, silkspindataobjects koostuu ohjelman tietoa tallentavsta olioista, ja logicandoperations sisältää vastaavasti ohjelman logiikasta ja tietojen tallennuksesta vastaavan osan.

![SilkSpinner's package schematic](https://github.com/tvierz/ot-harjoitustyo/blob/master/laskarit/viikko3/Untitled.png)
###### Picture 1, The composition of SilkSpin app.

Kuvasta voidaan myös nähdä sovelluksen luokat: User, DataSpec, BudgetPlan, BudgetLogic, RegisteredUsersLogic, SilkSpinDataSaving ja SilkSpinnerUI.

Luokan SilkSpinnerUI tarkoitus on rakentaa ohjelman käyttöliittymä ja toimia pääluokkana josta käyttäjän toiminnot välittyvät eteenpäin logiikkaluokille ja User-luokalle.



#### User Interface
The application's function is based on 6 different scenes with their own functionality. These scenes are login screen, registry screen, user screen, accounts screen, settings screen and budget screen.

The different screens have been implemented as Scene type objects, populated with Button-, Label-, TextBox-, PasswordBox-, and ScrollBox objects. These objects are used to manipulate user's data, input user's data, and display the user's data. The objects are placed in HBox objects, which are then placed into VBox objects, and then given as parameter for the Scene-object.

The user interface relies mainly on calling methods of logic classes, so the actual functions of the application are implemented separately from the graphical user interface. The user interface also contains an instance of User variable that is changed to reflect the user that is currently logged in, allowing the application to call simple operations from User class directly when needed.

#### Application logic
The application consists of 2 logical classes, RegisteredUsersLogic and BudgetLogic, and 3 data object classes, User, BudgetPlan, DataSpec. 

The logical classes BudgetLogic and RegisteredUsersLogic are responsible for nearly all the operations of the aforementioned data objects. Examples of these operations would be BudgetLogic, which has a method: enterData(User u, String data){}, which enters the data onto a BudgetPlan variable of User that has been given as a parameter, returntotalbudget(User u){}, which returns the combined value of all DataSpec objects on the user's BudgetPlan.

The RegisteredUsersLogic class contains the application's data saving and reading methods. void initialize(){} method reads a specific file, "Userdat.ser", and then assigns the returned HashMap<String, Username> as the userlist of the RegisteredUsersLogic. The method void save(){} dumps the entire contents of the current userlist into the Userdata.ser file, overwriting previous data.
Other methods of the RegisteredUsersLogic include User login(String user, String pass){} which handle the login event, register(String user, String pass){}, enterData(User u, String data){}. the login method works by comparing the username to usernames recorded on userlist's key set. If the user matches, and their password also matches, this method returns the user with given username and password, else a default "no" user is returned, which causes the UI to display an error message. The userlist HashMap<> variable is the reason the application only allows a single instance of a username.


#### Application data
The application contains data classes, which contain data which the application uses, these classes are DataSpec, User, and BudgetPlan. 

Note that due to the DataSpec's nature and it containing a double value, the program can crash if the double isn't checked properly, thus all the checks for entered DataSpec's validity are preformed in the logic classes

The User class is the central data class which contains all data a single user has. It's methods mainly involve in changing, appending to, or returning data according to the logic classes. User class also contains some simple methods for convenience, such as double monthlyTotal(){}, which returns a double representing all DataSpec objects on the User's active account, which can then be used conveniently by BudgetLogic to compare to BudgetPlan's total value.
The methods of this class mainly consist of methods such as:

String getData(){}
void setData(String s){}
void changeAccount(String s){}


The DataSpec class defines a data entry used to store information to BudgetPlan or User's account. The DataSpec's methods consist of mainly methods that return the values of the DataSpec objects, allowing User to conveniently enter values to their account, accompanied by a possible comment on the type of the entry. The DataSpec objects also are assigned a date when they are created, so it's convenient to track entries from specific timespan. The class includes some simple data operations for convenience, such as Integer getMo(){}, which returns the month of the DataSpec object's String variable, date.
Methods of this class include:

Integer getMo(){}
double getAmount(){}


Lastly, the class BudgetPlan specifies the BudgetPlan object. The class contains methods to access or modify the BudgetPlan's contents.
Methods of this class include:

ArrayList<DataSpec> getBudgetData(){}
void populateBudget(String s){}


#### Data Saving
The application saves the contents when user preforms certain actions, such as setting their safeword, or logging out, or closing the application from the x in upper right corner of the window. The data is saved locally by entering it to a specific file, which is read and saved by utilizing class SilkSpinDataSaving, which contains only three methods, void changeFileForTests(String s){}, void save(HashMap<> hm){} and HashMap<> read(){}. 

The reading and saving methods have been described above in the RegisteredUsersLogic as it is the only class that uses the SilkSpinDataSaving class.

#### Files
The application mainly uses the default "Userdat.ser" file, but this file can be changed by giving the RegisteredUsersLogic a parameter to change the name of the destination file. To make sure that the software's saving functions correctly, the new destination file should be present in the root folder on running the application. On Linux this works by creating a new empty text file and renaming it.

The data is saved by utilizing streams, which write the HashMap<String, User> directly into the file as serialized data. This works as all the classes used implement Serializable.


### Main functions
The application's main functions are: registering new account, logging in, creating and changing accounts, entering data to accounts, and creating a budget plan.

When the user logs in, the following actions take place
