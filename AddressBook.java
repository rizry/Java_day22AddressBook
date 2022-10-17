package com.day22;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class AddressBook {
  static final Scanner SC = new Scanner(System.in);

  ArrayList<Contact> contacts = new ArrayList<>();
  static Map<String, String> cityDictionary = new HashMap<>();
  static Map<String, String> stateDictionary = new HashMap<>();

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

      AddressBook.cityDictionary.put(c.fName, c.city);
      AddressBook.stateDictionary.put(c.fName, c.state);

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
    System.out.print("\nEnter which one to " + s + ": ");
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

  boolean isUnique(Contact c) {
    return !contacts.stream().anyMatch(con -> con.equals(c));
  }

  void searchDisplayMenu(HashMap<String, AddressBook> addressBookList, String str) {
    if (addressBookList.size() < 1) {
      System.out.println(
        "the addressbook list happens to be empty. we suggest you ADD a few CONTACTS before " + str + "ing people.");
      return;
    }

    String capStr = str.substring(0, 1).toUpperCase() + str.substring(1);
    String choice = "";

    do {
      System.out.print(
        "\n" + capStr + " Menu \n1. " + str + " by city \n2. " + str + " by state \n3. quit \nEnter your choice: ");

      choice = SC.nextLine().trim().toLowerCase();
      switch (str) {
        case "search":
          switch (choice) {
            case "1":
            case "city":
              searchByCity(addressBookList);
              break;

            case "2":
            case "state":
              searchByState(addressBookList);
              break;

            case "3":
            case "quit":
              choice = "quit";
              System.out.println("quitting " + str + " menu...");
              break;

            default:
              System.out.println("that didnt match any choice, try again");
              break;
          }
          break;

        case "display":
          switch (choice) {
            case "1":
            case "city":
              displayByCity();
              break;

            case "2":
            case "state":
              displayByState();
              break;

            case "3":
            case "quit":
              choice = "quit";
              System.out.println("quitting " + str + " menu...");
              break;

            default:
              System.out.println("that didnt match any choice, try again");
              break;
          }
          break;

        case "count":
          switch (choice) {
            case "1":
            case "city":
              countByCity();
              break;

            case "2":
            case "state":
              countByState();
              break;

            case "3":
            case "quit":
              choice = "quit";
              System.out.println("quitting " + str + " menu...");
              break;

            default:
              System.out.println("that didnt match any choice, try again");
              break;
          }
          break;

      }
    } while (!choice.equals("quit"));
  }

  private void searchByCity(HashMap<String, AddressBook> addressBookList) {
    System.out.print("enter the city to search people in: ");
    String cityToSearch = SC.nextLine();
    List<String> peopleOfThatCity = new ArrayList<>();

    for(Map.Entry<String, AddressBook> addressBook : addressBookList.entrySet()) {
      List<String> matchedePeople = addressBook.getValue().contacts.stream()
      .filter(c -> c.city.equalsIgnoreCase(cityToSearch)).map(c -> c.fName + " " + c.lName)
      .collect(Collectors.toList());

      peopleOfThatCity.addAll(matchedePeople);
    }

    if (peopleOfThatCity.size() > 0)
      System.out.println("people from '" + cityToSearch + "' city are: " + peopleOfThatCity);
    else System.out.println("we couldnt find any people from '" + cityToSearch + "' city in the addressbook list.");
  }

  private void searchByState(HashMap<String, AddressBook> addressBookList) {
    System.out.print("enter the city to search people in: ");
    String stateToSearch = SC.nextLine();
    List<String> peopleOfThatState = new ArrayList<>();

    for(Map.Entry<String, AddressBook> addressBook : addressBookList.entrySet()) {
      List<String> matchedePeople = addressBook.getValue().contacts.stream()
      .filter(c -> c.state.equalsIgnoreCase(stateToSearch)).map(c -> c.fName + " " + c.lName)
      .collect(Collectors.toList());

      peopleOfThatState.addAll(matchedePeople);
    }

    if (peopleOfThatState.size() > 0)
      System.out.println("people from '" + stateToSearch + "' state are: " + peopleOfThatState);
    else System.out.println("we couldnt find any people from '" + stateToSearch + "' state in the addressbook list.");
  }

  private void displayByCity() {
    List<String> cityList = AddressBook.cityDictionary.values().stream().distinct().collect(Collectors.toList());

    for(String city : cityList) {
      System.out.print("people from city '" + city + "': ");
      AddressBook.cityDictionary.entrySet().stream().filter(en -> en.getValue().equalsIgnoreCase(city))
      .forEach(en -> System.out.print(en.getKey() + ", "));
      System.out.println();
    }
  }

  private void displayByState() {
    List<String> stateList = AddressBook.stateDictionary.values().stream().distinct().collect(Collectors.toList());

    for(String state : stateList) {
      System.out.print("people from state '" + state + "': ");
      AddressBook.stateDictionary.entrySet().stream().filter(en -> en.getValue().equalsIgnoreCase(state))
      .forEach(en -> System.out.print(en.getKey() + ", "));
      System.out.println();

    }
  }

  private void countByCity() {
    List<String> cityList = AddressBook.cityDictionary.values().stream().distinct().collect(Collectors.toList());

    for(String city : cityList) {
      System.out.print("number of people from city '" + city + "': ");
      System.out.println(
        AddressBook.cityDictionary.entrySet().stream().filter(en -> en.getValue().equalsIgnoreCase(city)).count());
    }
  }

  private void countByState() {
    List<String> stateList = AddressBook.stateDictionary.values().stream().distinct().collect(Collectors.toList());

    for(String state : stateList) {
      System.out.print("number of people from state '" + state + "': ");
      System.out.println(
        AddressBook.stateDictionary.entrySet().stream().filter(en -> en.getValue().equalsIgnoreCase(state)).count());
    }
  }

  @Override
  public String toString() {
    String contactStr = "";
    for(Contact c : contacts) contactStr += c.fName + ", ";
    return contactStr;
  }
}
