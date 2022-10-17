package com.day22;

import java.util.ArrayList;
import java.util.Scanner;

public class Contact {
  String fName, lName, address, city, state, email;
  int zip;
  long phNum;

  Scanner sc = new Scanner(System.in);

  void getInputs() {
    System.out.print("Enter your first name: ");
    fName = sc.nextLine();

    System.out.print("Enter your last name: ");
    lName = sc.nextLine();

    System.out.print("Enter your address: ");
    address = sc.nextLine();

    System.out.print("Enter your city: ");
    city = sc.nextLine();

    System.out.print("Enter your state: ");
    state = sc.nextLine();

    System.out.print("Enter your email: ");
    email = sc.nextLine();

    System.out.print("Enter your area zip code: ");
    zip = sc.nextInt();

    System.out.print("Enter your phone number: ");
    phNum = sc.nextLong();

  }

  ArrayList<Contact> addContact(ArrayList<Contact> contacts, Contact c) {
    contacts.add(c);
    System.out.println("a new contact has been added");
    return contacts;
  }

  ArrayList<Contact> editContact(ArrayList<Contact> contacts, short index) {

    Contact cc = new Contact();
    cc.getInputs();
    contacts.set(index, cc);
    System.out.println("contact has been updated.");

    return contacts;
  }

  short getIndex(ArrayList<Contact> contacts, String name) {
    short found = -1;

    for(short i = 0; i < contacts.size(); i++) {

      if (contacts.get(i).fName.toLowerCase().equals(name.toLowerCase())) {
        found = i;
        break;
      }
    }

    return found;
  }

  ArrayList<Contact> deleteContact(ArrayList<Contact> contacts, short index) {
    contacts.remove(index);
    return contacts;

  }

  ArrayList<Contact> showEditDelete(ArrayList<Contact> contacts, String str) {

    if (contacts.size() == 0) {
      System.out.print("You have no contacts yet. 'add' one before you " + str + ".\n");
      return contacts;

    } else {
      System.out.print("Your contacts are: ");

      for(Contact c : contacts) {
        if (c != null) System.out.print(c.fName + ", ");
        else break;
      }
      System.out.println();

      System.out.print("\nEnter a name to " + str + ". ");
      String name = sc.nextLine();

      short index = getIndex(contacts, name);

      if (index == -1) {
        System.out.println("we couldnt find " + name + ". try again. ");
      } else {

        switch (str) {
          case "edit":
            System.out.println("\n* " + name + " is being edited *");
            contacts = editContact(contacts, index);
            break;

          case "delete":
            contacts = deleteContact(contacts, index);
            System.out.println("contact " + name + " has been deleted.");
            break;

          case "show":
            showDetails(contacts, index);
            break;

        }
      }

      return contacts;
    }

  }

  void showDetails(ArrayList<Contact> c, short i) {
    System.out.println("Contact Details ");
    System.out.print("Name: " + c.get(i).fName + " " + c.get(i).lName + "\nAddress: " + c.get(i).address + "\nCity: "
      + c.get(i).city + "\t\tState: " + c.get(i).state + "\t\tZip: " + c.get(i).zip + "\nEmail: " + c.get(i).email
      + "\t\tPhone number: " + c.get(i).phNum + "\n");
  }
}