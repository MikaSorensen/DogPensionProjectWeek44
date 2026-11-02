package org.example;

public class Main {
    public static void main(String[] args) {
        com.dogpension.Database database = new com.dogpension.Database();

         // int dogId = 1; // TODO id of the dog from tblDog (the dog and food type must exist before feedDog)
        RegisterFeeding registerFeeding = new RegisterFeeding(database, dogId);
        AddDogInfo addDogInfo = new AddDogInfo(registerFeeding);
        IncidentLog incidentLog = new IncidentLog(registerFeeding);

        addDogInfo.inputDogInformation();

        incidentLog.reportFeedIntolerance("PLACEHOLDER");

        // registerFeeding.feedDog();  // saves amount, time and change of feed to tblFeeding
    }
}