#### Pakkauskaavio
Ohjelman rakenne voidaan nähdä allaolevasta kuvasta, ohjelma koostuu kolmesta pakkauksesta, silkspinUI, silkspindataobjects ja logicandoperations. Nimien mukaisesti silkspinUI koostuu ohjelman käyttöliittymänn rakentavasta javaFX luokasta, silkspindataobjects koostuu ohjelman tietojen tallennuksesta olioihin, ja logicandoperations sisältää vastaavasti ohjelman logiikasta vastaavan osan.

![SilkSpinner's package schematic](https://github.com/tvierz/ot-harjoitustyo/blob/master/laskarit/viikko3/Untitled.png)

Kuvasta voidaan myös nähdä sovelluksen luokat: User, DataSpec, BudgetPlan, BudgetLogic, RegisteredUsersLogic, SilkSpinDataSaving ja SilkSpinnerUI

Luokan SilkSpinnerUI tarkoitus on rakentaa ohjelman käyttöliittymä ja toimia pääluokkana josta käyttäjän toiminnot välittyvät eteenpäin logiikkaluokille ja User-luokalle.

Luokan User tarkoitus on kuvata ohjelman käyttäjää ja käyttäjän tietoja. Luokalla User on mm. metodeja.

String getData()

void changeAccount()

String getUsername()


Luokassa RegisteredUsersLogic on sovelluksen pääasiallinen käyttäjästä vastaava logiikka, jonka avulla User-olion tietoja tallennetaan, muokataan sekä haetaan. Luokan metodeja ovat mm.

void save()

void initialize()

String register(String username, String password)

HashMap<String, User> getListMap()


Luokan DataSpec tarkoitus on rakentaa sovelluksen kannalta oleellinen datatyyppi, jota käytetään lähe koko sovelluksessa. Luokan metodeja ovat mm.

getAmount()

String toString()

Luokan BudgetPlan tarkoitus on rakentaa olio, johon voidaan varastoida DataSpec-olioita siten, että niiden tiedot eivät sekoitu User-luokan datan kanssa. Laajentamista silmälläpitäen, luokkaan voidaan tulevaisuudessa lisätä erilaisia ominaisuuksia jotta BudgetPlan voitaisiin muodostaa useilla eri tavoilla. Luokan metodeja ovat mm.

void populateBudget()

ArrayList<DataSpec> getBudgetData()
  
BudgetPlan-olion toiminnallisuudesta vastaa pääasiallisesti luokka BudgetLogic, jonka avulla BudgetPlan oliota voidaan muokata, kutsua, sekä sille tallettaa tietoa. Luokan metodeja ovat mm.

String compareToUsersSpending()

String enterData()

Sovelluksessa on myös tietojen pysyväistallennuksesta vastaava kokonaan erillinen luokka SilkSpinDataSaving, jonka tarkoituksena on tallentaa RegisteredUsersLogic luokan HashMap olio Userdat.ser tiedostoon, tai lukea se ja asettaa se luokalle RegisteredUsersLogic parametriksi. Luokan pääasiallinen toiminto on tallentaa tai lukea kaikki käyttäjien data. Luokan metodeja ovat mm.

void write()

void read()


