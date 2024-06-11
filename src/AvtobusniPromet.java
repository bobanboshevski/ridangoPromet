import FilesProcessing.StopTimesService;
import FilesProcessing.StopsService;
import FilesProcessing.TripsService;

import java.io.File;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class AvtobusniPromet {

    private StopsService stopsService;
    private StopTimesService stopTimesService;
    private TripsService tripsService;

    //static File stops = new File("stops.txt");
    //static File stop_times = new File("stop_times.txt");
    //static File trips = new File("trips.txt");

    // -- Singleton --
    private static AvtobusniPromet instance = null;
    public static AvtobusniPromet getInstance() {
        if (instance == null){
            instance = new AvtobusniPromet();
        }
        return instance;
    }
    private AvtobusniPromet(){
        stopsService = new StopsService(new File("stops.txt"));
        stopTimesService = new StopTimesService(new File("stop_times.txt"));
        tripsService = new TripsService(new File("trips.txt"));
    };
    // - - - - //

    public void prikazStops(){
        stopsService.displayAllStops();
    }

    private void displayPromet(String routeId, List<String> arivalTimes) {
        System.out.print(routeId + ": ");
        for (int i = 0; i < arivalTimes.size(); i++) {
            System.out.print(arivalTimes.get(i));
            if (i < arivalTimes.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();
    }

    public void getAvtobusneLinijeZaPostajo (int idPostaje, int steviloNaslednjihAvtobusov, String format){

        // lahko je
        //LocalTime now = LocalTime.now();
        // sem vstavil fiksno za lazje testiranje
        LocalTime now = LocalTime.of(10,0,0);
        List<String[]> stopTimes = stopTimesService.getStopTimesZaDolocenoPostajoZaManjKot2h(idPostaje, now);

        Map<String, String> tripsData = tripsService.getTripsData();

        // route_id = 0 index in arrival_time = 1 index
        List<String[]> joinedData = new ArrayList<>();
        for (String[] stopTime: stopTimes){
            String tripId = stopTime[0];
            String arrivalTime = stopTime[1];

            if (tripsData.containsKey(tripId)){
                String routeId = tripsData.get(tripId);
                joinedData.add(new String[]{routeId, arrivalTime});
            }
        }
        /*
        // anonimni notrajni razred
        Collections.sort(joinedData, new Comparator<String[]>() {
            @Override
            public int compare(String[] o1, String[] o2) {
                return o1[0].compareTo(o2[0]);
            }
        }); */
        //Collections.sort(joinedData, new ArrayComparator());

        joinedData.sort(new ArrayComparator());
        Map<String, Integer>  countMap = new HashMap<>();

        /*
        for (String[] data: joinedData){
            System.out.println( data[0]+ " "+ data[1]); //"Potrebne podatke: "+
        }*/

        System.out.println(stopsService.getImePostaje(idPostaje));

        Map<String, List<String>> routeTimesMap = new HashMap<>();
        for (String[] data: joinedData){
            String routeId = data[0];
            String arrivalTime = data[1];

            LocalTime arrivalTimeLocalTime = LocalTime.parse(arrivalTime, DateTimeFormatter.ofPattern("HH:mm:ss"));

            if (!routeTimesMap.containsKey(routeId)){
                routeTimesMap.put(routeId, new ArrayList<>());
            }
            if (routeTimesMap.get(routeId).size() < steviloNaslednjihAvtobusov){
                if (format.equals("absolute")){
                    String arrivalTimeBrezSec = arrivalTimeLocalTime.format(DateTimeFormatter.ofPattern("HH:mm"));
                    routeTimesMap.get(routeId).add(arrivalTimeBrezSec);
                } else if (format.equals("relative")) {
                    long minutesDifference = Duration.between(now, arrivalTimeLocalTime).toMinutes();
                    routeTimesMap.get(routeId).add(minutesDifference+"min");
                }
            }
        }
        for (Map.Entry<String, List<String>> entry: routeTimesMap.entrySet()){
            displayPromet(entry.getKey(), entry.getValue());
        }
    }

}
