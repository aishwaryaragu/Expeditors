package helpers;

import models.Occupant;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class InputDataUtil {

    private static final String SEPARATOR = "\",\"";

    public HashMap<String, ArrayList<Occupant>> loadHouseholdMapFromFile(File fileName) {
        HashMap<String, ArrayList<Occupant>> occupants = new HashMap<>();
        try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)))) {
            String line;
            while ((line=br.readLine()) != null) {
                Occupant occupant = createOccupant(line);
                String household = getHouseHold(occupant);
                if(occupants.containsKey(household)) {
                    ArrayList<Occupant> occupantList = occupants.get(household);
                    occupantList.add(occupant);
                    occupants.replace(household, occupantList);
                } else {
                    ArrayList<Occupant> occupantList = new ArrayList<>();
                    occupantList.add(occupant);
                    occupants.put(household, occupantList);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return occupants;
    }

    private String getHouseHold(Occupant occupant) {
       return occupant.getAddress().toLowerCase(Locale.ROOT).replaceAll("[^a-zA-Z0-9]", "")
               + occupant.getCity().toLowerCase(Locale.ROOT).replaceAll("[^a-zA-Z0-9]", "")
               + occupant.getState().toLowerCase(Locale.ROOT).replaceAll("[^a-zA-Z0-9]", "");
    }

    private Occupant createOccupant(String line) {
        assert line.split(SEPARATOR).length == 6: "Total fields should be six";
        return Occupant.builder()
                .firstName(cleanupField(line.split(SEPARATOR)[0]))
                .lastName(cleanupField(line.split(SEPARATOR)[1]))
                .address(cleanupField(line.split(SEPARATOR)[2]))
                .city(cleanupField(line.split(SEPARATOR)[3]))
                .state(cleanupField(line.split(SEPARATOR)[4]))
                .age(Integer.parseInt(cleanupField(line.split(SEPARATOR)[5])))
                .build();
    }

    private String cleanupField(String s) {
        return s.replace("\"", "").trim();
    }

    public java.util.List<Occupant> filterAdults(ArrayList<Occupant> occupantList) {
        return occupantList.stream().filter(Occupant::isAdult).collect(Collectors.toList());
    }

    public void sortAndPrintOccupants(List<Occupant> occupants) {
        occupants.sort(
                Comparator.comparing(Occupant::getLastName).thenComparing(Occupant::getFirstName)
        );

        for (Occupant occupant: occupants) {
            System.out.println(occupant.getFirstName() + "," + occupant.getLastName() + "," + occupant.getAddress() + "," + occupant.getAge());
        }
    }
}
