package org.example;
import java.util.List;

public class User {
    private String name;
    private String district;
    private String role;
    private List<String> helpTypes;

    public User(String name, String district, String role, List<String> helpTypes) {
        this.name = name;
        this.district = district;
        this.role = role;
        this.helpTypes = helpTypes;
    }

    public String getName() {
        return name;
    }

    public String getDistrict() {
        return district;
    }

    public String getRole() {
        return role;
    }

    public List<String> getHelpTypes() {
        return helpTypes;
    }

    @Override
    public String toString() {
        return name + "," + district + "," + role + "," + String.join(";", helpTypes);
    }

    public String display() {
        return name + " from " + district + " is a " + role + " for: " + helpTypes;
    }
}

