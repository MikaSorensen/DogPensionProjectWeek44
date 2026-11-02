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
        System.out.println("You have 3 choices:");

        System.out.println("1. Register Dog");
        System.out.println("2. Select dog for feeding");
        System.out.println("3. Print log card");

        select = in.nextInt();

        switch(select)
        {
            case 1:
                RegisterDog();
                break;
            case 2:
                FeedDog();
                break;
            case 3:
                PrintLogCard();
                break;
            default:
                break;
        }
    }

    public void RegisterDog()
    {
        ArrayList<Object> dogData = new ArrayList<Object>();

        String dogName = "";
        int dogAge = 0;
        String dogBreed = "";

        boolean isVaccinated = false;
        boolean hasFleaTreatment = false;
        boolean isInsured = false;
        String specialNeeds = "";

        int foodTypeID = 0;
        String foodType = "";
        double feedAmount = 0;
        int dailyFeedTimes = 0;

        System.out.println("Please enter your Dogs name: ");
        dogName = in.next();
        dogData.add(dogName);
        System.out.println();

        System.out.println("Please enter age of dog: ");
        dogAge = in.nextInt();
        dogData.add(dogAge);
        System.out.println();

        System.out.println("Please enter breed of dog: ");
        dogBreed = in.next();
        dogData.add(dogBreed);
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
        isInsured = in.hasNextBoolean();
        dogData.add(isInsured);
        System.out.println();

        System.out.println("Please enter any special needs for dog (optional)");
        specialNeeds = in.next();
        dogData.add(specialNeeds);
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

        System.out.println("Please enter amount of feed in gs:");
        feedAmount = in.nextDouble();
        dogData.add(feedAmount);
        System.out.println();

        System.out.println("Please enter daily feed times:");
        dailyFeedTimes = in.nextInt();
        dogData.add(dailyFeedTimes);
        System.out.println();



        ArrayList<String> dogFields = new ArrayList<String>(Arrays.asList("fldDogName", "fldDogAge", "fldDogBreed", "fldIsDogVaccinated",
                "fldIsDogTreatedForFleas", "fldIsDogInsured", "fldDogSpecialNeeds", "fldDogFoodTypeID", "fldFeedAmount", "fldDailyFeedTimes"));

        db.insertData("tblDog", dogFields, dogData);


    }

    public void FeedDog()
    {
        int dogId = 0;
        int feedTimes = 0;
        int currentFeeding = 0;
        int foodTypeId;

        System.out.println("Please enter dog ID");
        dogId = in.nextInt();
        System.out.println();

        feedTimes = Integer.parseInt(String.valueOf(db.getDataWhereDataIs("tblDog", "fldDailyFeedTimes", dogId, "fldDogId")));
        foodTypeId = Integer.parseInt(String.valueOf(db.getDataWhereDataIs("tblDog", "fldFoodTypeId", dogId, "fldDogId")));

        IncidentLog log = new IncidentLog(db, dogId);

        if(currentFeeding <= feedTimes)
        {
            log.insertFeedingIncident(foodTypeId, LocalDateTime.now());
            System.out.println("Feeding registered at: " + LocalDateTime.now());
        }
    }

    public void PrintLogCard()
    {
        int dogId = 0;
        int userInput = 0;
        System.out.println("Please enter dog id");
        dogId = in.nextInt();

        IncidentLog log = new IncidentLog(db, dogId);

        System.out.println("You have 3 choices:");

        System.out.println("1. Enter Walk Incident");
        System.out.println("2. Enter Feed Incident");
        System.out.println("3. print out Incident Log");
        userInput = in.nextInt();

        switch(userInput) {
            case 1:
                System.out.println("Please enter total distance of walk: ");
                double distance = in.nextDouble();
                log.insertWalkIncident(distance, LocalDateTime.now());
                log.updateIncidentLog();
                break;
            case 2:
                break;
            case 3:
                log.getPrintableIncidentLog();
                break;
            default:
                break;
        }
    }
}
