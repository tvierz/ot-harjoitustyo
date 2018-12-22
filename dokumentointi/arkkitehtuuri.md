#### Pakkauskaavio
Ohjelman rakenne voidaan nähdä allaolevasta kuvasta, ohjelma koostuu kolmesta pakkauksesta, silkspinUI, silkspindataobjects ja logicandoperations. Nimien mukaisesti silkspinUI koostuu ohjelman käyttöliittymän rakentavasta javaFX luokasta, silkspindataobjects koostuu ohjelman tietoa tallentavsta olioista, ja logicandoperations sisältää vastaavasti ohjelman logiikasta ja tietojen tallennuksesta vastaavan osan.

![SilkSpinner's package schematic](https://github.com/tvierz/ot-harjoitustyo/blob/master/laskarit/viikko3/Untitled.png)


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
The application's main functions are: registering new useraccount, logging in, entering data to accounts, and creating a budget plan.

When the user logs in, the following actions take place, assuming that the username and the user's password are correct. If the user's password is incorrect, the RegisteredUsersLogic would return a "no" user instead of the actual user, and the UI wouldn't change it's scene, but instead change a label to explain the login was incorrect.
![Login event sequence](https://github.com/tvierz/Imagerepo/blob/master/SilkSpinLogin.png)

When the user registers a new useraccount, the following actions take place. Assuming a valid username and password have been input, the ui calls for RegisteredUsersLogic's method register, which then creates a new User and puts it to it's own list. After this, the ui calls for RegisteredUsersLogic's save() method, which writes the userlist into the Userdat.ser file. In case the parameters for new account aren't valid, the new account isn't created. the "msg" in picture below represents a String variable that is returned and set as the label to indicate whether the registration was succesful or not, and if it wasn't, why.
![Register event sequence](https://github.com/tvierz/Imagerepo/blob/master/SilkSpinRegin.png)

When entering the data to accounts, users must give a valid entry to put into their account. This entry consists of a string of type: "x, y", where x is a string that is parseable to double and y is any string. Assuming a valid entry has been made, the following events take place. The "msg" below represents a string that informs user how the data entry even went. The enterData("x, y") method checks whether x is a valid parseable double, if it's not, the "msg" is returned immediately and a the User's and DataSpec's methods won't be used.
![Data entry even sequence](https://github.com/tvierz/Imagerepo/blob/master/SilkSpinDaten.png)

Users start with a budget plan, which can be entered the data in similar manner as to an account, but the BudgetPlan can be scrapped and a new one can be made. This is due to need to create new budget plan in certain intervals. The "msg" represents a message "Latest budget plan cleared, add new budget entries". When the refreshbudgetbtn is pressed, the ui calls for BudgetLogic's method refresh(User u), which changes it's internal BudgetPlan variable into a new BudgetPlan(). BudgetLogic then calls for parameter User's method setBudget(), which changes the related User's BudgetPlan into the new BudgetPlan() created earlier.
![BudgetPlan refresh event sequence](https://github.com/tvierz/Imagerepo/blob/master/SilkSpinBggt.png)


The rest of the application works in similar manner, where the UI calls for the method from a logic class, or User class, which updates the properties of a data object, or displays data object's data on the active Scene. The data contained in the data objects is saved every time the program is closed, or specific actions such as logout is preformed.

#### Structural weaknesses
##### In general
The program's user interface is constructed by javaFX class, causing possible incompatibilities with programs such as maven if the java is updated from java 8 to java 11 for example. Personally I had no idea on how to update the program to function with the updated java 11/10, so I wasn't able to use maven's operations such as mvn test, mvn test jacoco:report, mvn package at all.
It is likely that by updating maven to function with the java 10 would resolve these issues, as well as importing a custom class containing methods of javaFX that are no longer part of java 10/11, but as I had no idea how to do any of that, above weakness remained in the code.

The variables and method names used in the program could be renamed more appropriately and uniformly, as although them being easy to understand to some as shorthands of their actual purpose, it can make their meaning uncertain at a glance. Too long variable and method names should be avoided as well, since they would feel like needless clutter in the coding window. In the end this part is more or less a style consideration though.



##### User Interface
The program's UI is created by a long javaFX class which can cause incompatibilities with some programs, as mentioned above, but due to singular class creating the entire UI, the code can be a chore to read and comprehend, therefore it would be good idea to possibly split the Scene building parts of the UI class into their own methods. This would also make it easier to modify each Scene separately, and add more scenes without cluttering the main class.

#### Testing
The branch coverage, and tests overall were difficult to finish, since in last parts of the project, maven couldn't be used to do anything other than checkstyle confirmation. On top of that, the tests for data saving classes required their own testing files, which can clutter the root folder. This could be fixed by adding lines of code that create a new file, initialize the file into a .ser file and the removing the test file once tests are finished. This improvement proved troublesome as sometimes a line of code could perfectly create a file, but some times it couldn't. For the stability and testing considerations, those clutter files were left as is.
