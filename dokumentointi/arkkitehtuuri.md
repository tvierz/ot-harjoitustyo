#### Pakkauskaavio
Ohjelman pääosat koostuvat kolmesta luokasta, User, Registered Users ja SilkSpinLogic kuvan mukaisesti
![SilkSpinner's package schematic](https://github.com/tvierz/ot-harjoitustyo/blob/master/laskarit/viikko3/pakkauskaavio.jpg)

Luokka User tarjoaa metodeja, joiden avulla olion User dataa voidaan muokata tai kutsua, kuten:

String getData()

void changeAccount()

String getUsername()


Luokassa RegisteredUsers on sovelluksen pääasiallinen logiikka, jonka avulla tietoja tallennetaan, muokataan sekä haetaan, metodeja ovat mm.

void save()

void initialize()

String register(String username, String password)

HashMap<String, User> getListMap()


Viimeisenä tärkeänä osana on luokka SilkSpinLogic, jonka metodeilla kirjoitetaan ohjelman saama tieto yhteen Userdat.ser nimettyyn tiedostoon ohjelman kansiossa, käyttäjien tiedot eivät kuitenkaan sekoitu, sillä tallennuksessa käytetään serializable rajapintaa, jonka avulla oliot kuten HashMap voidaan tallentaa ja sitten kutsua sellaisinaan.
Nämä metodit ovat:

void write()

void read()


