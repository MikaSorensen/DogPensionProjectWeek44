import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class MainMenu {

    Scanner in = new Scanner(System.in);
    Database db = new Database();

    /*
    Initial start screen for the program, shows a list of actions and this is treated as a switch case
     */
    public void StartProgram()
    {
        int select;

        System.out.println("Welcome to Betty's dog pension");
        System.out.println("You have 8 choices:");

        System.out.println("1. Register Owner");
        System.out.println("2. Register Dog");
        System.out.println("3. Register a feeding of dog");
        System.out.println("4. Register a walk of dog");
        System.out.println("5. Print log card");
        System.out.println("6. Print Information card");
        System.out.println("7. Wipe database");
        System.out.println("8. Quit");

        // input validation, we check that the input is an int and is between 1 and 8, keepts promting user untill it succeeds
        while (true) {
            if (in.hasNextInt()) {
                select = in.nextInt();
                in.nextLine();

                if (select >= 1 && select <= 8) {
                    break;
                }
            } else {
                in.nextLine();
            }
            System.out.println("Invalid input, please choose a number between 1 and 8.");
        }

        // the main switch statement that decides what the user wants to do
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
        }
    }

    /*
    method to register an owner, the user is promted for the owners name and phone number, and after owner is added, the owner id is shown to the user for later use
     */
    private void RegisterOwner() {

        ArrayList<Object> ownerData = new ArrayList<>();

        String ownerName;

        // input validation for the owners name, we check that the string isnt empty
        while (true) {
            System.out.println("Enter Owner Name:");
            ownerName = in.nextLine();

            if (!ownerName.equalsIgnoreCase("")) {
                break;
            }
            System.out.println("Invalid input, name cannot be empty.");
        }
        ownerData.add(ownerName);

        // input validation for phone number
        String ownerPhoneNo;
        while (true) {
            System.out.println("Enter Owner Phone no:");
            ownerPhoneNo = in.nextLine();

            if (!ownerPhoneNo.equalsIgnoreCase("")) { // isnt empty
                break;
            }
            System.out.println("Invalid input, phone number cannot be empty.");
        }
        ownerData.add(ownerPhoneNo); // we add it to the array

        ArrayList<String> ownerFlds = new ArrayList<>(Arrays.asList("fldOwnerName", "fldOwnerPhoneNo")); // the columns that we wish to add to in tthe db

        db.insertData("tblOwner", ownerFlds, ownerData); // inserting the data

        // the owners id is fecthed using their provided phone number , as this is unique per person
        int registeredOwnerID = Integer.parseInt(String.valueOf(db.getDataWhereDataIs("tblOwner", "fldOwnerID", ownerPhoneNo, "fldOwnerPhoneNo").getFirst()));
        System.out.printf("Registered ID for owner is: %d\n", registeredOwnerID); // shows the user their id
    }

    /*
    method to register a dog, pretty much same deal as the owner registration, promts the user for relevant dog info, validates it, and inserts it. shows the user their dogid at the end
     */
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

        String value;

        while (true) {
            System.out.println("Please enter your Dogs name: ");
            dogName = in.nextLine();

            if (!dogName.equalsIgnoreCase("")) {
                break;
            }
            System.out.println("Invalid input, name cannot be empty.");
        }
        dogData.add(dogName);
        System.out.println();

        while (true) {
            System.out.println("Please enter age of dog: ");

            if (in.hasNextInt()) {
                dogAge = in.nextInt();
                in.nextLine();

                if (dogAge >= 0 && dogAge <= 100) {
                    break;
                }
            } else {
                in.nextLine();
            }
            System.out.println("Invalid input enter an age between 0 and 30");
        }
        dogData.add(dogAge);
        System.out.println();

        while (true) {
            System.out.println("Please enter breed of dog: ");
            dogBreed = in.nextLine();

            if (!dogBreed.equalsIgnoreCase("")) {
                break;
            }
            System.out.println("Invalid input breed cannot be empty");
        }
        dogData.add(dogBreed);
        System.out.println();

        while (true) {
            System.out.println("Please enter any special needs for dog (if No Special Needs enter \"None\")");
            specialNeeds = in.nextLine();

            if (!specialNeeds.equalsIgnoreCase("")) {
                break;
            }
            System.out.println("Invalid input please enter the special needs or \"None\".");
        }
        dogData.add(specialNeeds);
        System.out.println();

        while (true) {
            System.out.println("Please enter the preferred vet? 1. 2. or 3.");

            if (in.hasNextInt()) {
                dogPreferredVet = in.nextInt();
                in.nextLine();

                if (dogPreferredVet >= 1 && dogPreferredVet <= 3) {
                    break;
                }
            } else {
                in.nextLine();
            }
            System.out.println("Invalid input please choose 1, 2 or 3.");
        }
        dogData.add(dogPreferredVet);
        System.out.println();

        while (true) {
            System.out.println("Please enter weight of dog: ");

            if (in.hasNextInt()) {
                dogWeight = in.nextInt();
                in.nextLine();

                if (dogWeight >= 1 && dogWeight <= 120) {
                    break;
                }
            } else {
                in.nextLine();
            }
            System.out.println("Invalid input please enter a weight between 1 and 120.");
        }
        dogData.add(dogWeight);
        System.out.println();

        while (true) {
            System.out.println("Please enter the duration of the stay: ");

            if (in.hasNextInt()) {
                dogPensionDuration = in.nextInt();
                in.nextLine();

                if (dogPensionDuration >= 1 && dogPensionDuration <= 365) {
                    break;
                }
            } else {
                in.nextLine();
            }
            System.out.println("Invalid input please enter a duration between 1 and 365.");
        }
        dogData.add(dogPensionDuration);
        System.out.println();

        while (true) {
            System.out.println("Please enter true if dog is vaccinated or false if not: ");
            value = in.nextLine();

            if (value.equalsIgnoreCase("true")) {
                isVaccinated = true;
                break;
            } else if (value.equalsIgnoreCase("false")) {
                isVaccinated = false;
                break;
            }
            System.out.println("Invalid input, please enter true or false.");
        }
        dogData.add(isVaccinated);
        System.out.println();

        while (true) {
            System.out.println("Please enter true if dog has flea treatment or false if not: ");
            value = in.nextLine();

            if (value.equalsIgnoreCase("true")) {
                hasFleaTreatment = true;
                break;
            } else if (value.equalsIgnoreCase("false")) {
                hasFleaTreatment = false;
                break;
            }
            System.out.println("Invalid input, please enter true or false.");
        }
        dogData.add(hasFleaTreatment);
        System.out.println();

        while (true) {
            System.out.println("Please enter true if dog has insurance or false if not: ");
            value = in.nextLine();

            if (value.equalsIgnoreCase("true")) {
                isInsured = true;
                break;
            } else if (value.equalsIgnoreCase("false")) {
                isInsured = false;
                break;
            }
            System.out.println("Invalid input, please enter true or false.");
        }
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

        while (true) {
            if (in.hasNextInt()) {
                foodTypeID = in.nextInt();
                in.nextLine();

                if (foodTypeID >= 1 && foodTypeID <= 5) {
                    break;
                }
            } else {
                in.nextLine();
            }
            System.out.println("Invalid input please choose a number between 1 and 5.");
        }
        dogData.add(foodTypeID);
        System.out.println();

        while (true) {
            System.out.println("Please enter amount of food per feeding in grams:");

            if (in.hasNextDouble()) {
                feedAmount = in.nextDouble();
                in.nextLine();

                if (feedAmount > 0 && feedAmount <= 2000) {
                    break;
                }
            } else {
                in.nextLine();
            }
            System.out.println("Invalid input please enter an amount between 1 and 2000.");
        }
        dogData.add(feedAmount);
        System.out.println();

        while (true) {
            System.out.println("Please enter daily amount of times fed:");

            if (in.hasNextInt()) {
                dailyFeedTimes = in.nextInt();
                in.nextLine();

                if (dailyFeedTimes >= 1 && dailyFeedTimes <= 10) {
                    break;
                }
            } else {
                in.nextLine();
            }
            System.out.println("Invalid input please enter a number between 1 and 10.");
        }
        dogData.add(dailyFeedTimes);
        System.out.println();

        while (true) {
            System.out.println("Please enter owner id: ");

            if (in.hasNextInt()) {
                dogOwnerID = in.nextInt();
                in.nextLine();

                if (dogOwnerID >= 1) {
                    break;
                }
            } else {
                in.nextLine();
            }
            System.out.println("Invalid input please enter a valid owner id.");
        }
        dogData.add(dogOwnerID);
        System.out.println();

        ArrayList<String> dogFields = new ArrayList<>(Arrays.asList("fldDogName", "fldDogAge", "fldDogBreed", "fldDogSpecialNeeds", "fldDogPreferredVet", "fldDogWeight", "fldDogPensionDuration", "fldIsDogVaccinated", "fldIsDogTreatedForFleas", "fldIsDogInsured", "fldDogFoodTypeID", "fldFeedAmount", "fldDailyFeedTimes", "fldOwnerID"));

        db.insertData("tblDog", dogFields, dogData);
        int registeredDogID = Integer.parseInt(String.valueOf(db.getDataWhereDataIs("tblDog", "fldDogID", dogName, "fldDogName").getFirst()));
        System.out.printf("Registered ID for dog is: %d\n", registeredDogID);
    }

    /*
    insert a feeding incident/event for a dog, prompts the user for dog id and adds a feeding incident
     */
    public void FeedDog()
    {
        int dogId;
        int feedTimes;
        int currentFeeding;
        int foodTypeId;

        while (true) {
            System.out.println("Please enter dog ID");

            if (in.hasNextInt()) {
                dogId = in.nextInt();
                in.nextLine();

                if (dogId >= 1) {
                    break;
                }
            } else {
                in.nextLine();
            }
            System.out.println("Invalid input please enter a valid dog ID.");
        }

        // we get teh amount of times the user has specified the dog should be fed
        feedTimes = Integer.parseInt(String.valueOf(db.getDataWhereDataIs("tblDog", "fldDailyFeedTimes", dogId, "fldDogID").getFirst()));

        // we get the food type the user specified for the dog
        foodTypeId = Integer.parseInt(String.valueOf(db.getDataWhereDataIs("tblDog", "fldDogFoodTypeID", dogId, "fldDogID").getFirst()));

        // creation of incident log
        IncidentLog log = new IncidentLog(db, dogId);
        log.updateIncidentLog(); // updating log before fetching feeding count, as update runs sql to update the arrays
        currentFeeding = log.getFeedingCount(); // we get the amount of times the dog has been fed at the current date/day

        if (currentFeeding < feedTimes) {
            log.insertFeedingIncident(foodTypeId, LocalDateTime.now());

            System.out.println("Feeding registered");

            System.out.println("Dog has been fed " + (currentFeeding + 1) + " out of " + feedTimes + " times.");
        } else {
            System.out.println("This dog has already been fed enough times today");
        }
    }

    /*
    method for inserting walking dog event/incident, promts the user for the dog id and the distance walked, there is no count check
    on this one because there isnt a max amount of walks per day, however the walk here is set to be max 50 km
     */
    private void WalkDog() {
        int dogID;
        double walkingDistance;

        while (true) {
            System.out.println("Please enter dog ID");

            if (in.hasNextInt()) {
                dogID = in.nextInt();
                in.nextLine();

                if (dogID >= 1) {
                    break;
                }
            } else {
                in.nextLine();
            }
            System.out.println("Invalid input, please enter a valid dog ID.");
        }

        while (true) {
            System.out.println("Please enter walking distance [km]");

            if (in.hasNextDouble()) {
                walkingDistance = in.nextDouble();
                in.nextLine();

                if (walkingDistance > 0 && walkingDistance <= 50) {
                    break;
                }
            } else {
                in.nextLine();
            }
            System.out.println("Invalid input please enter a distance between 0 and 50.");
        }

        IncidentLog log = new IncidentLog(db, dogID);
        log.updateIncidentLog();
        log.insertWalkIncident(walkingDistance, LocalDateTime.now());

        System.out.println("Walking registered");
    }

    /*
    method for printing the log incident card for a specific dog id
     */
    public void PrintLogCard()
    {
        int dogId;

        while (true) {
            System.out.println("Please enter dog id");

            if (in.hasNextInt()) {
                dogId = in.nextInt();
                in.nextLine();

                if (dogId >= 1) {
                    break;
                }
            } else {
                in.nextLine();
            }
            System.out.println("Invalid input please enter a valid dog ID.");
        }

        IncidentLog log = new IncidentLog(db, dogId);

        log.updateIncidentLog();
        System.out.println(log.getPrintableIncidentLog()); // gets the printable log as a string and prints it
        System.out.println();
    }

    /*
    method to handle printing information card for specific dog id
     */
    private void PrintInformationCard() {
        int dogId;

        while (true) {
            System.out.println("Please enter dog id");

            if (in.hasNextInt()) {
                dogId = in.nextInt();
                in.nextLine();

                if (dogId >= 1) {
                    break;
                }
            } else {
                in.nextLine();
            }
            System.out.println("Invalid input please enter a valid dog ID.");
        }

        InformationCard ic = new InformationCard(db, dogId);

        ic.initInformationCard();

        System.out.println(ic.getPrintableInformationCard()); // printing getted information card
    }
}