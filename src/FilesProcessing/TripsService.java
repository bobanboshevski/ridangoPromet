package FilesProcessing;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TripsService {

    private File trips;

    public TripsService(File trips) {
        this.trips = trips;
    }

    public Map<String, String> getTripsData (){
        // KEY = trip_id in VALUE = route_id
        Map<String, String> tripsData = new HashMap<>();

        try(Scanner myReaderTrips = new Scanner(trips)){
            if (myReaderTrips.hasNextLine()){
                myReaderTrips.nextLine();
            }
            while (myReaderTrips.hasNextLine()){
                String line = myReaderTrips.nextLine();
                String[] split_line = line.split(",");
                //System.out.println("TRIPS DATA:" + split_line[2] + " " + split_line[0]);
                tripsData.put(split_line[2], split_line[0]);
            }
        } catch (Exception e ){
            e.printStackTrace();
        }
        return tripsData;
    }
}
