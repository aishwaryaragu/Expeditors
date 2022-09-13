package ut;

import helpers.InputDataUtil;
import models.Occupant;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class ApplicationTests {
    @Test
    void testHappyCase() {
        InputDataUtil util = new InputDataUtil();
        HashMap<String, ArrayList<Occupant>> occupants = util.loadHouseholdMapFromFile(new File("test-data/happycase.csv"));
        Assertions.assertEquals(4, occupants.get("123mainstseattlewa").size());
    }

    @Test
    void testHappyCaseWithAgeOlderThan18() {
        InputDataUtil util = new InputDataUtil();
        HashMap<String, ArrayList<Occupant>> occupants = util.loadHouseholdMapFromFile(new File("test-data/happycase.csv"));
        Assertions.assertEquals(2, util.filterAdults(occupants.get("123mainstseattlewa")).size());
    }

    @Test
    void testSortAndPrintOccupants() {
        InputDataUtil util = new InputDataUtil();
        ArrayList<Occupant> occupants = new ArrayList<>();
        occupants.add(
                Occupant.builder().firstName("Dave").lastName("Smith").address("abc").age(20).build()
        );
        occupants.add(
                Occupant.builder().firstName("Alice").lastName("Smith").address("abc").age(25).build()
        );
        util.sortAndPrintOccupants(occupants);
    }

    @Test
    void testWithInvalidAgeInput() {
        InputDataUtil util = new InputDataUtil();
        Assertions.assertThrows(NumberFormatException.class, () -> util.loadHouseholdMapFromFile(new File("test-data/agenumberformatexception.csv")));
    }

    @Test
    void testInputWithoutDoubleQuotes() {
        InputDataUtil util = new InputDataUtil();
        AssertionError error = Assertions.assertThrows(AssertionError.class, () -> util.loadHouseholdMapFromFile(new File("test-data/invalidseparator.csv")));
        Assertions.assertEquals("Total fields should be six", error.getMessage());
    }
}
