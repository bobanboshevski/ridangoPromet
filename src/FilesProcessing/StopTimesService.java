package FilesProcessing;

import java.io.File;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StopTimesService {

    private File stop_times;

    public StopTimesService(File stop_times) {
        this.stop_times = stop_times;
    }

    public List<String[]> getStopTimesZaDolocenoPostajoZaManjKot2h(int idPostaje, LocalTime now){

        List<String[]> stopTimes = new ArrayList<>();
        // dobimo avtobuse za doloceno postajo in ki pridejo za manj kot 2 uri
        try (Scanner myReaderStopTimes = new Scanner(stop_times)){

            //LocalTime now = LocalTime.now();
            //LocalTime now = LocalTime.of(10,0,0);

            LocalTime in2hours = now.plusHours(2);
            DateTimeFormatter datumFormater = DateTimeFormatter.ofPattern("HH:mm:ss");
            now = LocalTime.parse(now.format(datumFormater));

            //System.out.println("TIME: "+ now);

            if (myReaderStopTimes.hasNextLine()){
                myReaderStopTimes.nextLine();
            }
            while (myReaderStopTimes.hasNextLine()){
                String line = myReaderStopTimes.nextLine();
                String[] split_line = line.split(",");

                LocalTime arrivalTime = LocalTime.parse(split_line[1], datumFormater);
                if (Integer.parseInt(split_line[3]) == idPostaje && arrivalTime.isAfter(now) && arrivalTime.isBefore(in2hours) ){
                    //System.out.println(line);
                    //System.out.println("Izpisemo avtobuse ki prihajajo za manj kot 2 uri: " + split_line[0] + ", "+ split_line[1] + ", "+ split_line[3]);
                    stopTimes.add(new String[]{split_line[0], split_line[1]});
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return stopTimes;
    }
}
