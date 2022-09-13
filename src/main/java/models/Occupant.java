package models;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Occupant {
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String state;
    private int age;

    public boolean isAdult() {
        return this.age > 18;
    }
}
