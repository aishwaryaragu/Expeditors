import helpers.InputDataUtil;
import models.Occupant;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Application {
    public static void main(String[] args) {
        InputDataUtil util = new InputDataUtil();
        HashMap<String, ArrayList<Occupant>> occupants = util.loadHouseholdMapFromFile(new File("input.csv"));
        for (Map.Entry<String, ArrayList<Occupant>> e: occupants.entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue().size());
            List<Occupant> filteredList = util.filterAdults(e.getValue());
            util.sortAndPrintOccupants(filteredList);
            System.out.println("-------------------------");
        }
    }
}
