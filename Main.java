package com.day22;

import java.util.HashMap;
import java.util.Scanner;

public class Main {
  static final Scanner SC = new Scanner(System.in);

  static HashMap<String, AddressBook> addressBookList = new HashMap<>();

  public static void main(String[] args) {
    System.out.println("--Welcome to Address Book Program--");

    String choice = "add";

    while (!choice.equals("quit")) {
      System.out.print(
        "\nMain Menu \n1. create \n2. edit \n3. delete \n4. view \n5. search persons \n6. display persons \n7. count \n8. quit \nEnter your choice: ");
      choice = SC.nextLine().trim().toLowerCase();

      switch (choice) {
        case "add":
        case "1":
          AddressBook a = new AddressBook();
          System.out.print("Enter a name for your addressbook: ");
          String name = SC.nextLine();
          if (addressBookList.get(name) != null) {
            System.out.println("address book '" + name + "' already exists. enter a different name.");
            break;
          } else if (name.length() < 1) {
            System.out.println("address book name cannot be empty. try again.");
            break;
          }
          a.name = name;
          a.menu(a);
          addressBookList.put(name, a);
          System.out.println("address book '" + name + "' created.");
          break;

        case "edit":
        case "2":
          a = new AddressBook();
          addressBookList = a.viewEditDelete(addressBookList, "edit");
          break;

        case "delete":
        case "3":
          a = new AddressBook();
          addressBookList = a.viewEditDelete(addressBookList, "delete");
          break;

        case "view":
        case "4":
          a = new AddressBook();
          addressBookList = a.viewEditDelete(addressBookList, "view");
          break;

        case "search":
        case "5":
          a = new AddressBook();
          a.searchDisplayMenu(addressBookList, "search");
          break;

        case "display":
        case "6":
          a = new AddressBook();
          a.searchDisplayMenu(addressBookList, "display");
          break;

        case "count":
        case "7":
          a = new AddressBook();
          a.searchDisplayMenu(addressBookList, "count");
          break;

        case "quit":
        case "8":
          choice = "quit";
          break;

        default:
          System.out.println("that didnt match any choice, try again");
          break;
      }
    }

  }
}
