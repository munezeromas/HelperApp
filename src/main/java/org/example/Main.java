package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.util.*;
import java.io.*;

public class Main {
    private static final List<User> users = new ArrayList<>();
    private static final Map<String, List<User>> helpDirectory = new HashMap<>();
    private static final String[] helpOptions = {"Tutoring", "Farming", "Food", "Clothes", "Job Advice", "Health"};
    private static final String FILE_NAME = "users.txt";
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        loadUsersFromFile();

        while (true) {
            System.out.println("\nRWANDA COMMUNITY HELP SYSTEM");
            System.out.println("1. Register as Helper or Seeker");
            System.out.println("2. Find Help by Type and District");
            System.out.println("3. Show All Registered People");
            System.out.println("4. Exit");
            System.out.print("Choose option: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> registerUser();
                case 2 -> searchHelp();
                case 3 -> showAllUsers();
                case 4 -> {
                    saveUsersToFile();
                    System.out.println("All data saved. Thank you for using the system.");
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void registerUser() {
        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("District: ");
        String district = scanner.nextLine();

        System.out.print("Role (Helper or Seeker): ");
        String role = scanner.nextLine();

        System.out.println("Available help options:");
        for (String option : helpOptions) System.out.println("- " + option);

        System.out.print("Enter help types separated by comma (e.g., Tutoring,Clothes): ");
        String[] helpArray = scanner.nextLine().split(",");
        List<String> helpTypes = Arrays.asList(helpArray);

        User user = new User(name, district, role, helpTypes);
        users.add(user);

        for (String help : helpTypes) {
            helpDirectory.computeIfAbsent(help.trim(), k -> new ArrayList<>()).add(user);
        }

        System.out.println("Registered successfully.");
    }

    private static void searchHelp() {
        System.out.print("Enter type of help (e.g., Farming): ");
        String type = scanner.nextLine().trim();

        System.out.print("Enter your district: ");
        String district = scanner.nextLine().trim();

        if (helpDirectory.containsKey(type)) {
            List<User> matches = helpDirectory.get(type);
            System.out.println("Helpers for " + type + " in " + district + ":");

            boolean found = false;
            for (User u : matches) {
                if (u.getRole().equalsIgnoreCase("Helper") && u.getDistrict().equalsIgnoreCase(district)) {
                    System.out.println("- " + u.getName() + " (" + u.getDistrict() + ")");
                    found = true;
                }
            }

            if (!found) System.out.println("No helpers found in your district.");
        } else {
            System.out.println("No one registered for this help type yet.");
        }
    }

    private static void showAllUsers() {
        if (users.isEmpty()) {
            System.out.println("No users registered yet.");
            return;
        }

        System.out.println("Registered Users:");
        for (User u : users) {
            System.out.println(u.display());
        }
    }

    private static void saveUsersToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (User u : users) {
                writer.println(u.toString());
            }
        } catch (IOException e) {
            System.out.println("Error saving data.");
        }
    }

    private static void loadUsersFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(",");
                String name = parts[0];
                String district = parts[1];
                String role = parts[2];
                List<String> helpTypes = Arrays.asList(parts[3].split(";"));

                User user = new User(name, district, role, helpTypes);
                users.add(user);

                for (String help : helpTypes) {
                    helpDirectory.computeIfAbsent(help.trim(), k -> new ArrayList<>()).add(user);
                }
            }
        } catch (Exception e) {
            System.out.println("Error reading saved data.");
        }
    }
}
