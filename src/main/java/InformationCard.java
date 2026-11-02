import java.util.ArrayList;

public class InformationCard {

    // these are all the information used for the card, for a specific dog
    public String dogName;
    public int dogAge;
    public String dogBreed;
    public String dogSpecialNeeds;
    public int dogPrefferedVet;
    public int dogPensionDuration;
    public boolean dogVaccination;
    public boolean dogFleas;
    public boolean dogInsured;
    public int dogFoodTypeID;
    public int dogOwnerID;
    public String dogOwnerName;
    public String dogOwnerPhoneNo;
    public int dogID;
    public Database db;
    int dogWeight;
    double dogFeedAmount;
    int dogDailyFeedTimes;

    /*
    constructor for the information card, takes the database and dogID for the given card
     */
    public InformationCard(Database db, int dogID) {
        this.db = db;
        this.dogID = dogID;
    }

    /*
    initialisation of the information card,  gets the data where dog id is equal to the dog id given
     */
    public void initInformationCard () {
        this.dogName = db.getDataWhereDataIs("tblDog", "fldDogName", dogID, "fldDogID").getFirst().toString();
        this.dogAge = Integer.parseInt(db.getDataWhereDataIs("tblDog", "fldDogAge", dogID, "fldDogID").getFirst().toString());
        this.dogBreed = db.getDataWhereDataIs("tblDog", "fldDogBreed", dogID, "fldDogID").getFirst().toString();
        this.dogSpecialNeeds = db.getDataWhereDataIs("tblDog", "fldDogSpecialNeeds", dogID, "fldDogID").getFirst().toString();
        this.dogPrefferedVet = Integer.parseInt(db.getDataWhereDataIs("tblDog", "fldDogPreferredVet", dogID, "fldDogID").getFirst().toString());
        this.dogWeight = Integer.parseInt(db.getDataWhereDataIs("tblDog", "fldDogWeight", dogID, "fldDogID").getFirst().toString());
        this.dogPensionDuration = Integer.parseInt(db.getDataWhereDataIs("tblDog", "fldDogPensionDuration", dogID, "fldDogID").getFirst().toString());
        this.dogVaccination = Boolean.parseBoolean(db.getDataWhereDataIs("tblDog", "fldIsDogVaccinated", dogID, "fldDogID").getFirst().toString());
        this.dogFleas = Boolean.parseBoolean(db.getDataWhereDataIs("tblDog", "fldIsDogTreatedForFleas", dogID, "fldDogID").getFirst().toString());
        this.dogInsured = Boolean.parseBoolean(db.getDataWhereDataIs("tblDog", "fldIsDogInsured", dogID, "fldDogID").getFirst().toString());
        this.dogFoodTypeID = Integer.parseInt(db.getDataWhereDataIs("tblDog", "fldDogFoodTypeID", dogID, "fldDogID").getFirst().toString());
        this.dogFeedAmount = Double.parseDouble(db.getDataWhereDataIs("tblDog", "fldFeedAmount", dogID, "fldDogID").getFirst().toString());
        this.dogDailyFeedTimes = Integer.parseInt(db.getDataWhereDataIs("tblDog", "fldDailyFeedTimes", dogID, "fldDogID").getFirst().toString());
        this.dogOwnerID = Integer.parseInt(db.getDataWhereDataIs("tblDog", "fldOwnerID", dogID, "fldDogID").getFirst().toString());
        this.dogOwnerName = db.getDataWhereDataIs("tblOwner", "fldOwnerName", dogOwnerID, "fldOwnerID").getFirst().toString();
        this.dogOwnerPhoneNo = db.getDataWhereDataIs("tblOwner", "fldOwnerPhoneNo", dogOwnerID, "fldOwnerID").getFirst().toString();
    }

    /*
    getter for the information card as a formatted string
     */
    public String getPrintableInformationCard () {
        return String.format("DOG INFORMATION\n-------------------------\nDog ID: %d\nName: %s\nAge: %d\nBreed: %s\nSpecial Needs: %s\nPreferred Vet ID: %d\nPension Duration: %d days\nVaccinated: %b\nTreated for Fleas: %b\nInsured: %b\nFood Type ID: %d\n\nOWNER INFORMATION\n-------------------------\nOwner ID: %d\nOwner Name: %s\nOwner Phone Number: %s", dogID, dogName, dogAge, dogBreed, dogSpecialNeeds, dogPrefferedVet, dogPensionDuration, dogVaccination, dogFleas, dogInsured, dogFoodTypeID, dogOwnerID, dogOwnerName, dogOwnerPhoneNo);
    }
}
