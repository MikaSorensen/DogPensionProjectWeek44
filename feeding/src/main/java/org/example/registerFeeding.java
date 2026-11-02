package org.example;

import java.util.ArrayList;
import java.util.List;


 // time of feeding is stored as an int in HHmm format: 830 = 08:30, 1415 = 14:15.

public class registerFeeding {

    private static final int NO_TIME = -1;

    private final com.dogpension.Database database;
    private final int dogId;

    private String feedType;
    private int dailyFeedTimes;
    private double feedAmount;
    private int feedingTime = NO_TIME;
    private boolean feedChanged;   // pending, attached to the next saved feeding

    public registerFeeding(com.dogpension.Database database, int dogId) {
        this.database = database;
        this.dogId = dogId;
    }

    public String getFeedType() {
        // TODO check input?call from database
        return feedType;
    }

    public void setFeedType(String feedType) {
        // TODO check input
        this.feedType = feedType;
    }

    public int getDailyAmountOfFeedTimes() {
        // TODO check input
        return dailyFeedTimes;
    }

    public void setDailyAmountOfFeedTimes(int dailyFeedTimes) {
        // TODO check input
        this.dailyFeedTimes = dailyFeedTimes;
    }

    public void enterAmountOfFeed(double amount) {
        this.feedAmount = amount;
    }

    // time of feeding (HHmm)
    public void enterTimeOfFeeding(int time) {
        int hours = time / 100;
        int minutes = time % 100;
        if (time < 0 || hours > 23 || minutes > 59) {
            throw new IllegalArgumentException("time must be in HHmm format");
        }
        this.feedingTime = time;
    }

    // called from incidentLog, check where to call from
    public void enterChangeOfFeed(String newFeedType) {
        this.feedType = newFeedType;
        this.feedChanged = true;
    }

    // stores amount, time and change of feed(if true) in tblFeeding
    public void feedDog() {
        if (feedType == null || feedType.isBlank()) {
            throw new IllegalStateException("no feed type set");
        }
        if (feedAmount <= 0) {
            throw new IllegalStateException("amount of feed must be > 0");
        }
        if (feedingTime == NO_TIME) {
            throw new IllegalStateException("no time of feeding given");
        }

        int foodTypeId = findFoodTypeId(feedType);

        ArrayList<String> columns = new ArrayList<>(List.of(
                "fldFeedingTime", "fldDogID", "fldFoodTypeID", "fldFeedAmount", "fldFeedChanged"));
        ArrayList<Object> data = new ArrayList<>(List.of(
                feedingTime, dogId, foodTypeId, feedAmount, feedChanged));

        database.insertData("tblFeeding", columns, data);

        // reset values for the next feeding
        feedAmount = 0;
        feedingTime = NO_TIME;
        feedChanged = false;
    }

    // looks up the id of the feed typein tblFoodType
    private int findFoodTypeId(String name) {
        String quoted = "'" + name.replace("'", "''") + "'";
        ArrayList<Object> ids = database.getDataWhereDataIs(
                "tblFoodType", "fldFoodTypeID", quoted, "fldFoodTypeName");
        if (ids.isEmpty()) {
            throw new IllegalStateException("Unknown feed type: " + name);
        }
        return ((Number) ids.get(0)).intValue();
    }
}