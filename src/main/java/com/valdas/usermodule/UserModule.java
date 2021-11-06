package com.valdas.usermodule;

import com.foreign.tests.implementation.EmailValidator;
import com.foreign.tests.implementation.PasswordValidator;
import com.foreign.tests.implementation.PhoneValidator;
import org.apache.commons.configuration.ConfigurationException;
import org.assertj.core.util.VisibleForTesting;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class UserModule {

    static EmailValidator emailValidator;
    static PasswordValidator passwordValidator;
    static PhoneValidator phoneValidator;

    private static final String DATA_FILE = "src/main/resources/data.txt";
    private static List<User> users;
    private static int lastId;

    public static void main(String[] args) {
        init();

        users = readUserData();
        printUsers(users);
        lastId = users.get(users.size() - 1).getId();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Enter action: 1. Create User 2. Search by UserID 3. Delete by UserID 4. Edit by UserID");
            char choice = scanner.nextLine().trim().charAt(0);

            switch (choice) {
                case '1':
                    userCreateScenario();
                    break;
                case '2':
                    userSearchScenario();
                    break;
                case '3':
                    userDeleteScenario();
                    break;
                case '4':
                    userEditScenario();
                default:
                    break;
            }
        }

    }

    private static void userEditScenario() {
        int userId = readUserId();
        User user = users.stream().filter(u -> u.getId() == userId).findFirst().orElse(null);

        if (user == null) {
            System.out.println("No such user!");
        } else {
            System.out.println("Found user: " + user.toString());
            System.out.println("Enter new field values for user ( first name, last name, phoneNumber, email, address, password )");
            Scanner scanner = new Scanner(System.in);
            String data = scanner.nextLine();
            List<String> fields = Arrays.asList(data.split(" "));

            while (fields.size() < 6) {
                System.out.println("Please enter NEW first name, last name, phone number, email, address and password!");
                data = scanner.nextLine();
                fields = Arrays.asList(data.split(" "));
            }

            String phoneNumber = fields.get(2);
            String email = fields.get(3);
            String password = fields.get(5);

            User u = createUser(
                    fields.get(0),
                    fields.get(1),
                    phoneNumber,
                    email,
                    fields.get(4),
                    password);

            if (u != null) {
                user.setFirstName(u.getFirstName());
                user.setLastName(u.getLastName());
                user.setPhoneNumber(u.getPhoneNumber());
                user.setEmail(u.getEmail());
                user.setAddress(u.getAddress());
                user.setPassword(u.getPassword());
            }

            updateUsersFile();
        }

    }

    private static void userDeleteScenario() {
        int userId = readUserId();

        User user = users.stream().filter(u -> u.getId() == userId).findFirst().orElse(null);

        if (user == null) {
            System.out.println("No such user!");
        } else {
            users.removeIf(u -> u.getId() == userId);
            updateUsersFile();
        }
    }

    private static void userSearchScenario() {
        int userId = readUserId();

        User user = users.stream().filter(u -> u.getId() == userId).findFirst().orElse(null);

        if (user == null) {
            System.out.println("No such user!");
        } else {
            System.out.println(user.toString());
        }
    }

    private static int readUserId() {
        System.out.println("Enter UserID: ");
        Scanner scanner = new Scanner(System.in);
        return Integer.parseInt(String.valueOf(scanner.nextLine().trim().charAt(0)));
    }

    private static void userCreateScenario() {
        System.out.println("Enter user details to create a new user: ");
        Scanner scanner = new Scanner(System.in);
        String data = scanner.nextLine();
        System.out.println(data);
        List<String> fields = Arrays.asList(data.split(" "));

        if (fields.size() < 6) {
            System.out.println("Please enter first name, last name, phone number, email, address and password!");
        } else {
            String phoneNumber = fields.get(2);
            String email = fields.get(3);
            String password = fields.get(5);

            User user = createUser(
                    fields.get(0),
                    fields.get(1),
                    phoneNumber,
                    email,
                    fields.get(4),
                    password);

            if (user != null) {
                users.add(user);
                writeUserToFile(user, true);

                lastId++;
            }
        }
    }

    @VisibleForTesting
    static User createUser(String firstName, String lastName, String phoneNumber, String email, String address, String password) {
        boolean isValidPhoneNumber = checkPhoneNumber(phoneNumber);
        boolean isValidEmail = checkEmail(email);
        boolean isValidPassword = checkPassword(password);

        if (!isValidPhoneNumber || !isValidEmail || !isValidPassword) return null;

        User user = new User(
                lastId + 1,
                firstName,
                lastName,
                phoneNumber,
                email,
                address,
                password);
        return user;
    }

    @VisibleForTesting
    static boolean checkPhoneNumber(String phoneNumber) {
        boolean isValid = true;

        if (!phoneValidator.validatePhoneNumbers(Long.parseLong(phoneNumber))) {
            System.out.println("Invalid phone number!");
            isValid = false;
        }

        return isValid;
    }

    @VisibleForTesting
    static boolean checkEmail(String email) {
        boolean isValid = true;

        if (!emailValidator.validateEmailDomainAndTLD(email)) {
            isValid = false;
            System.out.println("Invalid email domain and TLD!");
        }

        if (!emailValidator.validateEmailETA(email)) {
            isValid = false;
            System.out.println("Invalid email. Please include @ symbol!");
        }

        if (!emailValidator.validateEmailSymbols(email)) {
            isValid = false;
            System.out.println("Invalid email. Please include special symbols!");
        }

        return isValid;
    }

    @VisibleForTesting
    static boolean checkPassword(String password) {
        boolean isValid = true;
        if (!passwordValidator.validatePasswordLength(password)) {
            isValid = false;
            System.out.println("Invalid password length!");
        }

        if (!passwordValidator.validatePasswordSymbols(password)) {
            isValid = false;
            System.out.println("Invalid password. Please include special symbols!");
        }

        if (!passwordValidator.validatePasswordUppercase(password)) {
            isValid = false;
            System.out.println("Invalid password. Please include at least one uppercase character!");
        }

        return isValid;
    }

    @VisibleForTesting
    static void init() {
        try {
            emailValidator = new EmailValidator();
            passwordValidator = new PasswordValidator();
            phoneValidator = new PhoneValidator();
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }

    private static void printUsers(List<User> users) {
        for (User u : users) {
            System.out.println(u.toString());
        }
    }

    private static void writeUserToFile(User user, boolean append) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_FILE, append));
            writer.write(
                    user.getId() + " " +
                        user.getFirstName() + " " +
                        user.getLastName() + " " +
                        user.getPhoneNumber() + " " +
                        user.getEmail() + " " +
                        user.getAddress() + " " +
                        user.getPassword() + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void eraseFileContents() {
        try {
            new BufferedWriter(new FileWriter(DATA_FILE, false)).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void updateUsersFile() {
        eraseFileContents();
        for (User user : users) {
            writeUserToFile(user, true);
        }
    }

    private static List<User> readUserData() {
        List<User> users = new ArrayList<>();
        File myObj = new File(DATA_FILE);
        try {
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] fields = data.split(" ");
                users.add(new User(
                        Integer.parseInt(fields[0]),
                        fields[1],
                        fields[2],
                        fields[3],
                        fields[4],
                        fields[5],
                        fields[6]
                ));
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return users;
    }
}
