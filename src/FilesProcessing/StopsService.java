package FilesProcessing;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class StopsService {

    private File stopsFile;

    public StopsService(File stopsFile) {
        this.stopsFile = stopsFile;
    }

    public String getImePostaje (int idPostaje) {

        try(Scanner myReader = new Scanner(stopsFile)){
            if (myReader.hasNextLine()) {
                myReader.nextLine();
            }
            while (myReader.hasNextLine()){

                String line = myReader.nextLine();
                String[] line_splited = line.split(",");
                if (Integer.parseInt(line_splited[0]) == idPostaje){
                    myReader.close();
                    return line_splited[2];
                }
            }
            myReader.close();
            return "Ne obstaja ID postaje";
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void displayAllStops() {
        try (Scanner scanner = new Scanner(stopsFile)) {
            if (scanner.hasNextLine()) {
                scanner.nextLine(); // Skip header
            }
            while (scanner.hasNextLine()) {
                String[] data = scanner.nextLine().split(",");
                int stopId = Integer.parseInt(data[0]);
                String stopName = data[2];
                System.out.println(stopId + ", " + stopName);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
