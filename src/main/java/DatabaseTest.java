import java.util.ArrayList;
import java.util.Arrays;

public class DatabaseTest {
    public static void main(String[] args) {
        Database db = new Database();

        ArrayList<String> foodCols = new ArrayList<>(Arrays.asList("fldFoodTypeName"));
        ArrayList<Object> foodData = new ArrayList<>(Arrays.asList("Dry kibble"));
        db.insertData("tblFoodType", foodCols, foodData);

        ArrayList<Object> foodIDs =
                db.getData("tblFoodType", "fldFoodTypeID");

        Object foodID = foodIDs.get(foodIDs.size() - 1);

        ArrayList<String> dogCols = new ArrayList<>(Arrays.asList(
                "fldDogName", "fldDogAge", "fldDogBreed", "fldDogSpecialNeeds",
                "fldDogPrefferedVet", "fldDogWeight", "fldDogPensionDuration",
                "fldIsDogVaccinated", "fldIsDogTreatedForFleas", "fldIsDogInsured",
                "fldDogFoodTypeID"
        ));

        ArrayList<Object> dogData = new ArrayList<>(Arrays.asList(
                "Rex", 4, "Labrador", "",
                1, 32, 14,
                true, true, false,
                foodID
        ));

        db.insertData("tblDog", dogCols, dogData);

        String[] cols = {"fldDogID", "fldDogName", "fldDogAge", "fldDogBreed", "fldDogSpecialNeeds",
                "fldDogPrefferedVet", "fldDogWeight", "fldDogPensionDuration", "fldIsDogVaccinated",
                "fldIsDogTreatedForFleas", "fldIsDogInsured", "fldDogFoodTypeID", "fldOwnerID"};

        for (String col : cols) {
            System.out.println(col + "   " + db.getData("tblDog", col));
        }

        db.wipeDatabaseData();
    }
}
