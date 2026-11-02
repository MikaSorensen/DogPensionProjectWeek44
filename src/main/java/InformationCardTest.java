import java.util.ArrayList;
import java.util.Arrays;

public class InformationCardTest {

    public static void testResult(String testName, boolean passed) {

        if (passed) {
            System.out.println("\uD83D\uDFE9 PASS: " + testName);
        } else {
            System.out.println("\uD83D\uDFE5 FAIL: " + testName);
        }
    }

    public static void main(String[] args) {

        Database db = new Database();
        db.wipeDatabaseData();

        ArrayList<String> foodCols = new ArrayList<>(Arrays.asList("fldFoodTypeName"));
        ArrayList<Object> foodData = new ArrayList<>(Arrays.asList("Dry kibble"));

        db.insertData("tblFoodType", foodCols, foodData);

        ArrayList<Object> foodIDs = db.getData("tblFoodType", "fldFoodTypeID");
        Object foodID = foodIDs.get(foodIDs.size() - 1);

        ArrayList<String> ownerCols = new ArrayList<>(Arrays.asList("fldOwnerName", "fldOwnerPhoneNo"));

        ArrayList<Object> ownerData = new ArrayList<>(Arrays.asList("John Doe", "+45 20256534"));

        db.insertData("tblOwner", ownerCols, ownerData);

        ArrayList<Object> ownerIDs = db.getData("tblOwner", "fldOwnerID");
        Object ownerID = ownerIDs.get(ownerIDs.size() - 1);

        ArrayList<String> dogCols = new ArrayList<>(Arrays.asList("fldDogName", "fldDogAge", "fldDogBreed", "fldDogSpecialNeeds", "fldDogPrefferedVet", "fldDogWeight", "fldDogPensionDuration", "fldIsDogVaccinated", "fldIsDogTreatedForFleas", "fldIsDogInsured", "fldDogFoodTypeID", "fldOwnerID"));

        ArrayList<Object> dogData = new ArrayList<>(Arrays.asList("Rex", 4, "Labrador", "None", 1, 32, 14, true, true, false, foodID, ownerID));

        db.insertData("tblDog", dogCols, dogData);

        ArrayList<Object> dogIDs = db.getDataWhereDataIs("tblDog", "fldDogID", "Rex", "fldDogName");

        testResult("Dog was inserted into database", !dogIDs.isEmpty());

        if (dogIDs.isEmpty()) {
            System.out.println("tests cannot run because the dog was not inserted.");
            db.wipeDatabaseData();
            return;
        }

        int dogID = Integer.parseInt(dogIDs.getFirst().toString());

        InformationCard informationCard = new InformationCard(db, dogID);
        informationCard.initInformationCard();

        testResult("dog information was retrieved correctly", informationCard.dogName.equals("Rex") && informationCard.dogAge == 4 && informationCard.dogBreed.equals("Labrador"));

        testResult("Owner information was retrieved correctly", informationCard.dogOwnerName.equals("John Doe") && informationCard.dogOwnerPhoneNo.equals("+45 20256534"));

        String printableCard = informationCard.getPrintableInformationCard();

        testResult("printable information card has dog and owner information", printableCard.contains("Name: Rex") && printableCard.contains("Breed: Labrador") && printableCard.contains("Owner Name: John Doe") && printableCard.contains("Owner Phone Number: +45 20256534"));

        System.out.println("\n" + printableCard);

        db.wipeDatabaseData();
    }
}