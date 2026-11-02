import java.util.ArrayList;
import java.util.Arrays;

public class DatabaseTest {

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

        ArrayList<Object> foodNames = db.getData("tblFoodType", "fldFoodTypeName");

        testResult("data was inserted and gotten correctly", foodNames.contains("Dry kibble"));

        ArrayList<String> ownerCols = new ArrayList<>(Arrays.asList("fldOwnerName", "fldOwnerPhoneNo"));

        ArrayList<Object> ownerData = new ArrayList<>(Arrays.asList("John Doe", "+45 20256534"));

        db.insertData("tblOwner", ownerCols, ownerData);

        ArrayList<Object> ownerIDs = db.getData("tblOwner", "fldOwnerID");

        Object ownerID = ownerIDs.get(ownerIDs.size() - 1);

        ArrayList<Object> ownerNames = db.getDataWhereDataIs("tblOwner", "fldOwnerName", ownerID, "fldOwnerID");

        testResult("Data was retrieved using a where condition", ownerNames.contains("John Doe"));

        db.wipeDatabaseData();

        ArrayList<Object> remainingFood = db.getData("tblFoodType", "fldFoodTypeName");

        ArrayList<Object> remainingOwners = db.getData("tblOwner", "fldOwnerName");

        testResult("data was deleted correctly", remainingFood.isEmpty() && remainingOwners.isEmpty());
    }
}