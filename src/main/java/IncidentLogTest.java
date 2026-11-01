import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

public class IncidentLogTest {

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

        ArrayList<String> ownerCols = new ArrayList<>(Arrays.asList("fldOwnerName", "fldOwnerPhoneNo"));

        ArrayList<Object> ownerData = new ArrayList<>(Arrays.asList("John Doe", "+45 20256534"));

        db.insertData("tblOwner", ownerCols, ownerData);

        ArrayList<Object> foodIDs = db.getData("tblFoodType", "fldFoodTypeID");
        ArrayList<Object> ownerIDs = db.getData("tblOwner", "fldOwnerID");

        Object foodID = foodIDs.get(foodIDs.size() - 1);
        Object ownerID = ownerIDs.get(ownerIDs.size() - 1);

        ArrayList<String> dogCols = new ArrayList<>(Arrays.asList("fldDogName", "fldDogAge", "fldDogBreed", "fldDogSpecialNeeds", "fldDogPrefferedVet", "fldDogWeight", "fldDogPensionDuration", "fldIsDogVaccinated", "fldIsDogTreatedForFleas", "fldIsDogInsured", "fldDogFoodTypeID", "fldOwnerID"));

        ArrayList<Object> dogData = new ArrayList<>(Arrays.asList("Rex", 4, "Labrador", "None", 1, 32, 14, true, true, false, foodID, ownerID));

        db.insertData("tblDog", dogCols, dogData);

        ArrayList<Object> dogIDs = db.getDataWhereDataIs("tblDog", "fldDogID", "Rex", "fldDogName");

        testResult("Dog was inserted into database", !dogIDs.isEmpty());

        if (dogIDs.isEmpty()) {System.out.println("tests cannot run because the dog was not inserted.");db.wipeDatabaseData();return;}

        int dogID = Integer.parseInt(dogIDs.getFirst().toString());

        IncidentLog incidentLog = new IncidentLog(db, dogID);

        LocalDateTime walkTime = LocalDateTime.now();

        incidentLog.insertWalkIncident(2.5, walkTime);
        incidentLog.insertWalkIncident(3, walkTime);

        ArrayList<Object> insertedWalks = db.getDataWhereDataIs("tblWalk", "fldWalkID", dogID, "fldDogID");

        testResult("Walk incident was inserted", !insertedWalks.isEmpty());

        LocalDateTime feedingTime = LocalDateTime.now();

        incidentLog.insertFeedingIncident(Integer.parseInt(foodID.toString()), feedingTime);

        ArrayList<Object> insertedFeedings = db.getDataWhereDataIs("tblFeeding", "tblFeedingID", dogID, "fldDogID");

        testResult("Feeding incident was inserted", !insertedFeedings.isEmpty());

        incidentLog.updateIncidentLog();

        testResult("Walk was retrieved by updateIncidentLog", incidentLog.walkIDs.size() == 2);

        testResult("Feeding was retrieved by updateIncidentLog", incidentLog.feedingIDs.size() == 1);

        String printableLog = incidentLog.getPrintableIncidentLog();

        testResult("Printable log contains dog ID", printableLog.contains("Dog ID: " + dogID));

        testResult("Printable log contains walk ID", printableLog.contains("Walk ID: " + insertedWalks.getFirst()));

        testResult("Printable log contains feeding ID", printableLog.contains("Feeding ID: " + insertedFeedings.getFirst()));

        testResult("Printable log contains food type ID", printableLog.contains("Food Type ID: " + foodID));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        testResult("Walk date is formatted correctly", printableLog.contains(walkTime.format(formatter)));

        testResult("Feeding date is formatted correctly", printableLog.contains(feedingTime.format(formatter)));

        System.out.println("\n" + printableLog);

        db.wipeDatabaseData();
    }
}
