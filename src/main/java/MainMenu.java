import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
        ArrayList dogData = new ArrayList();
        String dogName = "";
        int dogAge = 0;
        String dogBreed = "";

        boolean isVaccinated = false;
        boolean hasFleaTreatment = false;
        boolean isInsured = false;
        String specialNeeds = "";

        System.out.println("Please enter your Dogs name: ");
        dogName = in.next();
        dogData.add(dogName);

        System.out.println("Please enter age of dog: ");
        dogAge = in.nextInt();
        dogData.add(dogAge);

        System.out.println("Please enter breed of dog: ");
        dogBreed = in.next();
        dogData.add(dogBreed);

        System.out.println("Please enter true if dog is vaccinated or false if not: ");
        isVaccinated = in.nextBoolean();
        dogData.add(isVaccinated);

        System.out.println("Please enter true if dog has flea treatment or false if not: ");
        hasFleaTreatment = in.nextBoolean();
        dogData.add(hasFleaTreatment);

        System.out.println("Please enter true is dog has insurance or false if not: ");
        isInsured = in.hasNextBoolean();
        dogData.add(isInsured);

        System.out.println("Please enter any special needs for dog (optional)");
        specialNeeds = in.next();
        dogData.add(specialNeeds);

        ArrayList<String> dogFields = new ArrayList<String>();

        dogFields.add("fldDogName");
        dogFields.add("fldDogAge");
        dogFields.add("fldDogBreed");
        dogFields.add("fldIsDogVaccinated");
        dogFields.add("fldIsDogTreatedForFleas");
        dogFields.add("fldIsDogInsured");
        dogFields.add("fldDogSpecialNeeds");

        db.insertData("tblDog", dogFields, dogData);


    }

    public void FeedDog()
    {

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

        switch(userInput)
        {
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
