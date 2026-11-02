import java.sql.SQLOutput;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;



public class MainMenu {

    Scanner in = new Scanner(System.in);
    Database db = new Database();


    public void StartProgram()
    {

        int select = 0;

        System.out.println("Welcome to Betty's dog retirement home");
        System.out.println("You have 6 choices:");

        System.out.println("1. Register Owner");
        System.out.println("2. Register Dog");
        System.out.println("3. Register a feeding of dog");
        System.out.println("4. Register a walk of dog");
        System.out.println("5. Print log card");
        System.out.println("6. Print Information card");
        System.out.println("7. Wipe database");
        System.out.println("8. Quit");

        select = in.nextInt();
        in.nextLine();

        switch(select)
        {
            case 1:
                RegisterOwner();
                break;
            case 2:
                RegisterDog();
                break;
            case 3:
                FeedDog();
                break;
            case 4:
                WalkDog();
                break;
            case 5:
                PrintLogCard();
                break;
            case 6:
                PrintInformationCard();
                break;
            case 7:
                db.wipeDatabaseData();
                db.insertFoodTypes();
                System.out.println("Database data has been wiped\n");
                break;
            case 8:
                System.exit(0);
                break;
            default:
                break;
        }
    }

    private void RegisterOwner() {

        ArrayList<Object> ownerData = new ArrayList<>();

        System.out.println("Enter Owner Name:");
        String ownerName = in.nextLine();
        ownerData.add(ownerName);

        System.out.println("Enter Owner Phone no:");
        String ownerPhoneNo = in.nextLine();
        ownerData.add(ownerPhoneNo);

        ArrayList<String> ownerFlds = new ArrayList<>(Arrays.asList("fldOwnerName", "fldOwnerPhoneNo"));

        db.insertData("tblOwner", ownerFlds, ownerData);

        int registeredOwnerID = Integer.parseInt(String.valueOf(db.getDataWhereDataIs("tblOwner", "fldOwnerID", ownerPhoneNo, "fldOwnerPhoneNo").getFirst()));
        System.out.printf("Registered ID for owner is: %d\n", registeredOwnerID);

    }

    public void RegisterDog()
    {
        ArrayList<Object> dogData = new ArrayList<>();

        String dogName;
        int dogAge;
        String dogBreed;

        boolean isVaccinated;
        boolean hasFleaTreatment;
        boolean isInsured;
        String specialNeeds;
        int dogPreferredVet;
        int dogWeight;
        int dogPensionDuration;
        int dogOwnerID;

        int foodTypeID;
        double feedAmount;
        int dailyFeedTimes;

        System.out.println("Please enter your Dogs name: ");
        dogName = in.nextLine();
        dogData.add(dogName);
        System.out.println();

        System.out.println("Please enter age of dog: ");
        dogAge = in.nextInt();
        in.nextLine();
        dogData.add(dogAge);
        System.out.println();

        System.out.println("Please enter breed of dog: ");
        dogBreed = in.nextLine();
        dogData.add(dogBreed);
        System.out.println();

        System.out.println("Please enter any special needs for dog (if No Special Needs enter \"None\")");
        specialNeeds = in.nextLine();
        dogData.add(specialNeeds);
        System.out.println();

        System.out.println("Please enter the preferred vet? 1. 2. or 3.");
        dogPreferredVet = in.nextInt();
        dogData.add(dogPreferredVet);
        System.out.println();

        System.out.println("Please enter weight of dog: ");
        dogWeight = in.nextInt();
        dogData.add(dogWeight);
        System.out.println();

        System.out.printf("Please enter the duration of the stay: ");
        dogPensionDuration = in.nextInt();
        dogData.add(dogPensionDuration);
        System.out.println();

        System.out.println("Please enter true if dog is vaccinated or false if not: ");
        isVaccinated = in.nextBoolean();
        dogData.add(isVaccinated);
        System.out.println();

        System.out.println("Please enter true if dog has flea treatment or false if not: ");
        hasFleaTreatment = in.nextBoolean();
        dogData.add(hasFleaTreatment);
        System.out.println();

        System.out.println("Please enter true is dog has insurance or false if not: ");
        isInsured = in.nextBoolean();
        dogData.add(isInsured);
        System.out.println();

        System.out.println();
        System.out.println("The dogs food plan will now be registered");
        System.out.println("Please select which type of feed for dog:\n");

        System.out.println("1. Standard Dry feed");
        System.out.println("2. Dry Feed for Young Dogs");
        System.out.println("3. Dry Feed For Old Dogs");
        System.out.println("4. Luxury Food");
        System.out.println("5. Diet Food");
        foodTypeID = in.nextInt();
        dogData.add(foodTypeID);
        System.out.println();

        System.out.println("Please enter amount of food per feeding in grams:");
        feedAmount = in.nextDouble();
        dogData.add(feedAmount);
        System.out.println();

        System.out.println("Please enter daily amount of times fed:");
        dailyFeedTimes = in.nextInt();
        dogData.add(dailyFeedTimes);
        System.out.println();

        System.out.println("Please enter owner id: ");
        dogOwnerID = in.nextInt();
        in.nextLine();
        dogData.add(dogOwnerID);
        System.out.println();

        ArrayList<String> dogFields = new ArrayList<>(Arrays.asList("fldDogName", "fldDogAge", "fldDogBreed", "fldDogSpecialNeeds", "fldDogPreferredVet", "fldDogWeight", "fldDogPensionDuration", "fldIsDogVaccinated", "fldIsDogTreatedForFleas", "fldIsDogInsured", "fldDogFoodTypeID", "fldFeedAmount", "fldDailyFeedTimes", "fldOwnerID"));

        db.insertData("tblDog", dogFields, dogData);
        int registeredDogID = Integer.parseInt(String.valueOf(db.getDataWhereDataIs("tblDog", "fldDogID", dogName, "fldDogName").getFirst()));
        System.out.printf("Registered ID for dog is: %d\n", registeredDogID);
    }

    public void FeedDog()
    {
        int dogId;
        int feedTimes;
        int currentFeeding;
        int foodTypeId;

        System.out.println("Please enter dog ID");
        dogId = in.nextInt();

        feedTimes = Integer.parseInt(String.valueOf(db.getDataWhereDataIs("tblDog", "fldDailyFeedTimes", dogId, "fldDogID").getFirst()));

        foodTypeId = Integer.parseInt(String.valueOf(db.getDataWhereDataIs("tblDog", "fldDogFoodTypeID", dogId, "fldDogID").getFirst()));

        IncidentLog log = new IncidentLog(db, dogId);
        log.updateIncidentLog();
        currentFeeding = log.getFeedingCount();

        if (currentFeeding < feedTimes) {
            log.insertFeedingIncident(foodTypeId, LocalDateTime.now());

            System.out.println("Feeding registered");

            System.out.println("Dog has been fed " + (currentFeeding + 1) + " out of " + feedTimes + " times.");
        } else {
            System.out.println("This dog has already been fed enough times today");
        }
    }

    private void WalkDog() {
        int dogID;
        double walkingDistance;
        System.out.println("Please enter dog ID");
        dogID = in.nextInt();
        System.out.println("Please enter walking distance");
        walkingDistance = in.nextDouble();

        IncidentLog log = new IncidentLog(db, dogID);
        log.updateIncidentLog();
        log.insertWalkIncident(walkingDistance, LocalDateTime.now());

        System.out.println("Walking registered");
    }

    public void PrintLogCard()
    {
        int dogId;
        System.out.println("Please enter dog id");
        dogId = in.nextInt();

        IncidentLog log = new IncidentLog(db, dogId);

        log.updateIncidentLog();
        System.out.println(log.getPrintableIncidentLog());
        System.out.println();

    }

    private void PrintInformationCard() {
        int dogId;
        System.out.println("Please enter dog id");
        dogId = in.nextInt();

        InformationCard ic = new InformationCard(db, dogId);

        ic.initInformationCard();

        System.out.println(ic.getPrintableInformationCard());


    }
}
