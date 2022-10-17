package com.day22;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AddressBook {
  static final Scanner SC = new Scanner(System.in);

  ArrayList<Contact> contacts = new ArrayList<>();
  String name;

  public void menu(AddressBook a) {
    String choice = "add";
    contacts = a.contacts;

    while (!choice.equals("quit")) {
      System.out
      .print("\n" + a.name + " Menu. \n1. add \n2. edit \n3. delete \n4. show \n5. quit \nEnter your choice: ");
      choice = SC.nextLine().trim().toLowerCase();

      switch (choice) {
      case "add":
      case "1":
        Contact c = new Contact();
        addContact(c);
        break;

      case "edit":
      case "2":
        c = new Contact();
        contacts = c.showEditDelete(contacts, "edit");
        break;

      case "delete":
      case "3":
        c = new Contact();
        contacts = c.showEditDelete(contacts, "delete");
        break;

      case "show":
      case "4":
        c = new Contact();
        contacts = c.showEditDelete(contacts, "show");
        break;

      case "quit":
      case "5":
        choice = "quit";
        break;

      default:
        System.out.println("that didnt match any choice, try again");
        break;
      }
    }

  }

  public void addContact(Contact c) {

    System.out.print("Enter your first name: ");
    String fName = SC.nextLine();
    c.fName = fName;

    if (!isUnique(c)) {
      System.out.println("that contact already exists. enter a different name.");
      return;
    }

    try {
      c.getInputs(fName);
      System.out.println("Here's whats been added: " + c.fName + " " + c.lName + " " + c.address + " " + c.city + " "
        + c.state + " " + c.email + " " + c.zip + " " + c.phNum);
      contacts = c.addContact(contacts, c);
    } catch (InputMismatchException e) {
      System.out.println("Enter a numeric value for zip code and phone number next time.");
    }
  }

  public HashMap<String, AddressBook> viewEditDelete(HashMap<String, AddressBook> addressBookList, String s) {

    if (addressBookList.size() == 0) {
      System.out.println("You Address Book is empty. You might want to add first.");
      return addressBookList;
    }

    System.out.print("you have the following lists in AdressBook: ");

    for(String key : addressBookList.keySet()) { System.out.print(key + ", "); }
    System.out.print("\nEnter which one to " + s + " ");
    String name = SC.nextLine();

    if (!addressBookList.containsKey(name)) {
      System.out.println("\nwe couldnt find " + name + " in our Adressbook.");
      return addressBookList;
    }

    switch (s) {
      case "view":
        if (addressBookList.get(name).contacts.size() < 1) {
          System.out.println("There are no contacts in ." + name + ".");
        } else {
          System.out.print("AdressBook name: " + name + "\t\t\t Contacts: ");
          System.out.print(addressBookList.get(name) + "\n");
        }

        System.out.print("do you want to edit " + name + "(y/n): ");
        String ch = SC.nextLine().trim().toLowerCase();
        if (ch.contains("y")) menu(addressBookList.get(name));
        break;

      case "edit":
        menu(addressBookList.get(name));
        break;

      case "delete":
        addressBookList.remove(name);
        System.out.println(name + " has been deleted.");
        break;
    }

    return addressBookList;
  }

  @Override
  public String toString() {
    String contactStr = "";
    for(Contact c : contacts) contactStr += c.fName + ", ";
    return contactStr;
  }

  boolean isUnique(Contact c) {
    return !contacts.stream().anyMatch(con -> con.equals(c));
  }
}
