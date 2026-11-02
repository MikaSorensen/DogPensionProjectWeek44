import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.time.format.DateTimeFormatter;

public class IncidentLog {

    // lists for the feeding data
    ArrayList<Object> feedingDateTimes;
    ArrayList<Object> feedingIDs;
    ArrayList<Object> foodTypeIDs;

    // listss for the walking data
    ArrayList<Object> walkDateTimes;
    ArrayList<Object> walkIDs;
    ArrayList<Object> walkDistances;

    Database db;
    int dogID;

    /* constructor for the incidentlog, takes the database and the dogID for the specific log
     */
    public IncidentLog(Database db, int dogID) {

        this.db = db;
        this.dogID = dogID;

        //makes the lists
        feedingDateTimes = new ArrayList<>();
        feedingIDs = new ArrayList<>();
        foodTypeIDs = new ArrayList<>();
        walkDateTimes = new ArrayList<>();
        walkIDs = new ArrayList<>();
        walkDistances = new ArrayList<>();
    }

    /*
    method to update the information from the walks and feedings directly from the database
     */
    public void updateIncidentLog() {

        feedingDateTimes = db.getDataWhereDataIs("tblFeeding", "fldFeedingDateTime", dogID, "fldDogID");
        feedingIDs = db.getDataWhereDataIs("tblFeeding", "tblFeedingID", dogID, "fldDogID");
        foodTypeIDs = db.getDataWhereDataIs("tblFeeding", "fldFoodTypeID", dogID, "fldDogID");
        walkDateTimes = db.getDataWhereDataIs("tblWalk", "fldDateTime", dogID, "fldDogID");
        walkIDs = db.getDataWhereDataIs("tblWalk", "fldWalkID", dogID, "fldDogID");
        walkDistances = db.getDataWhereDataIs("tblWalk", "fldDistance", dogID, "fldDogID");
    }

    /*
    getter for the log, this is not made with a formataable string as the one for the informationcard is, because there isnt just one of each this time, but multiple
    returns the log as a string
     */
    public String getPrintableIncidentLog() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); // formatter for the time, to not get anything smaller than a second

        String printableLog = "INCIDENT LOG\n";
        printableLog += "-------------------------\n";

        printableLog += "Dog ID: " + dogID + "\n\n";

        printableLog += "FEEDINGS\n";
        printableLog += "-------------------------\n";

        // for loop that runs thru all the feedings
        for (int i = 0; i < feedingDateTimes.size(); i++) {
            printableLog += "Feeding ID: " + feedingIDs.get(i) + "\n";
            printableLog += "Date and time: " + ((Timestamp) feedingDateTimes.get(i)).toLocalDateTime().format(formatter) + "\n";
            printableLog += "Food Type ID: " + foodTypeIDs.get(i) + "\n\n";
        }

        printableLog += "WALKS\n";
        printableLog += "-------------------------\n";

        // for loop that runs thru all the walks
        for (int i = 0; i < walkDateTimes.size(); i++) {
            printableLog += "Walk ID: " + walkIDs.get(i) + "\n";
            printableLog += "Date and time: " + ((Timestamp) walkDateTimes.get(i)).toLocalDateTime().format(formatter) + "\n";
            printableLog += "Distance: " + walkDistances.get(i) + " km\n\n";
        }

        return printableLog;
    }

    /*
    method to insert a walk incident, given distance and the datetime
     */
    public void insertWalkIncident(double distance, LocalDateTime dateTime) {

        ArrayList<String> columns = new ArrayList<>(Arrays.asList("fldDogID", "fldDistance", "fldDateTime"));

        ArrayList<Object> data = new ArrayList<>(Arrays.asList(dogID, distance, Timestamp.valueOf(dateTime)));

        db.insertData("tblWalk", columns, data);
    }

    /*
    method to insert a feeding incident given the foodtypid and the datetime
     */
    public void insertFeedingIncident(int foodTypeID, LocalDateTime dateTime) {

        ArrayList<String> columns = new ArrayList<>(Arrays.asList("fldDogID", "fldFoodTypeID", "fldFeedingDateTime"));

        ArrayList<Object> data = new ArrayList<>(Arrays.asList(dogID, foodTypeID, Timestamp.valueOf(dateTime)));

        db.insertData("tblFeeding", columns, data);
    }

    public int getFeedingCount()
    {
        updateIncidentLog();

        int count = 0;

        for (int i = 0; i < feedingDateTimes.size(); i++)
        {
            Timestamp feedingTime = (Timestamp) feedingDateTimes.get(i);

            if (feedingTime.toLocalDateTime().toLocalDate().equals(LocalDateTime.now().toLocalDate()))
            {
                count++;
            }
        }

        return count;
    }
}