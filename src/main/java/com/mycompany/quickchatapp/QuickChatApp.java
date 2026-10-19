package com.mycompany.quickchatapp;
// Main app menu loop
// message arrays and storage
// reports and analytics
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Main application driver providing UI menu interaction and array analytics for Part 3.
 */
public class QuickChatApp {
// Main app menu loop
    // message arrays and storage
    // Parallel collections for Part 3
    // reports and analytics
    private static ArrayList<String> sentMessages = new ArrayList<>();
    private static ArrayList<String> disregardedMessages = new ArrayList<>();
    private static ArrayList<String> storedMessages = new ArrayList<>();
    private static ArrayList<String> messageHashes = new ArrayList<>();
    private static ArrayList<String> messageIDs = new ArrayList<>();
    private static ArrayList<Message> allMessageObjects = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Login userLogin = new Login();

        System.out.println("=== QuickChat Account Registration ===");
        System.out.print("Enter First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter Last Name: ");
        String lastName = scanner.nextLine();
        userLogin.setFirstName(firstName);
        userLogin.setLastName(lastName);

        boolean registered = false;
        while (!registered) {
            System.out.print("Enter Username: ");
            String username = scanner.nextLine();
            System.out.print("Enter Password: ");
            String password = scanner.nextLine();
            System.out.print("Enter SA Cell Phone (+27...): ");
            String cell = scanner.nextLine();

            String regMsg = userLogin.registerUser(username, password, cell);
            System.out.println(regMsg);

            if (regMsg.contains("successfully captured")) {
                registered = true;
            } else {
                System.out.println("Registration failed. Try again.\n");
            }
        }

        System.out.println("\n=== QuickChat User Login ===");
        boolean loggedIn = false;
        while (!loggedIn) {
            System.out.print("Enter Username: ");
            String inUser = scanner.nextLine();
            System.out.print("Enter Password: ");
            String inPass = scanner.nextLine();

            loggedIn = userLogin.loginUser(inUser, inPass);
            System.out.println(userLogin.returnLoginStatus(loggedIn));
        }

        System.out.println("\nWelcome to QuickChat.");
        boolean running = true;

        while (running) {
            System.out.println("\nSelect an Option:");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Stored Messages & Reports");
            System.out.println("4) Quit");
            System.out.print("Choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    sendMessagesRoutine(scanner);
                    break;
                case "2":
                    System.out.println("Coming Soon.");
                    break;
                case "3":
                    storedMessagesMenu(scanner);
                    break;
                case "4":
                    running = false;
                    System.out.println("Exiting QuickChat. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid selection. Try again.");
            }
        }
        scanner.close();
    }

    private static void sendMessagesRoutine(Scanner scanner) {
        System.out.print("How many messages do you wish to enter? ");
        int count = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < count; i++) {
            System.out.println("\n--- Message " + (i + 1) + " ---");
            System.out.print("Enter Recipient Cell Number (+27...): ");
            String recipient = scanner.nextLine();

            System.out.print("Enter Message (max 250 chars): ");
            String text = scanner.nextLine();

            Message temp = new Message();
            String val = temp.validateMessageLength(text);
            if (!val.equals("Message ready to send.")) {
                System.out.println(val);
                i--; 
                continue;
            }

            System.out.println("Select Action:\n1) Send Message\n2) Disregard Message\n3) Store Message to send later");
            System.out.print("Choice: ");
            String action = scanner.nextLine();

            Message msg = new Message(i, recipient, text, action);
            allMessageObjects.add(msg);
            messageHashes.add(msg.getMessageHash());
            messageIDs.add(msg.getMessageID());

            if (msg.getMessageStatus().equals("Sent")) {
                sentMessages.add(msg.getMessageText());
            } else if (msg.getMessageStatus().equals("Disregarded")) {
                disregardedMessages.add(msg.getMessageText());
            } else if (msg.getMessageStatus().equals("Stored")) {
                storedMessages.add(msg.storeMessageJSON());
            }

            System.out.println("\n--- Message Summary ---");
            System.out.println(msg.printMessages());
        }
    }

    private static void storedMessagesMenu(Scanner scanner) {
        System.out.println("\n=== Stored Messages Analytics ===");
        System.out.println("a) Display Sender and Recipient of stored messages");
        System.out.println("b) Display Longest Stored Message");
        System.out.println("c) Search for Message ID");
        System.out.println("d) Search for Messages by Recipient");
        System.out.println("e) Delete Message using Message Hash");
        System.out.println("f) Display Stored Messages Report");
        System.out.print("Choice: ");
        String sub = scanner.nextLine().toLowerCase();

        switch (sub) {
            case "a":
                for (Message m : allMessageObjects) {
                    if ("Stored".equals(m.getMessageStatus())) {
                        System.out.println("Recipient: " + m.getRecipient() + " | Status: Stored");
                    }
                }
                break;
            case "b":
                String longest = "";
                for (Message m : allMessageObjects) {
                    if (m.getMessageText().length() > longest.length()) {
                        longest = m.getMessageText();
                    }
                }
                System.out.println("Longest Message: " + longest);
                break;
            case "c":
                System.out.print("Enter Message ID: ");
                String searchID = scanner.nextLine();
                for (Message m : allMessageObjects) {
                    if (m.getMessageID().equals(searchID)) {
                        System.out.println("Found -> Recipient: " + m.getRecipient() + " | Message: " + m.getMessageText());
                    }
                }
                break;
            case "d":
                System.out.print("Enter Recipient Number: ");
                String searchRec = scanner.nextLine();
                for (Message m : allMessageObjects) {
                    if (m.getRecipient().equals(searchRec)) {
                        System.out.println("Message: " + m.getMessageText());
                    }
                }
                break;
            case "e":
                System.out.print("Enter Message Hash to Delete: ");
                String delHash = scanner.nextLine();
                allMessageObjects.removeIf(m -> {
                    boolean match = m.getMessageHash().equalsIgnoreCase(delHash);
                    if (match) {
                        System.out.println("Message: \"" + m.getMessageText() + "\" successfully deleted.");
                    }
                    return match;
                });
                break;
            case "f":
                System.out.println("\n--- Full Message Report ---");
                for (Message m : allMessageObjects) {
                    System.out.println("Hash: " + m.getMessageHash() + " | Recipient: " + m.getRecipient() + " | Text: " + m.getMessageText());
                }
                break;
            default:
                System.out.println("Invalid choice.");
                // project complete
        }
    }
}