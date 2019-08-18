package io;

import model.Book;
import model.User;
import model.Magazine;

import java.util.Scanner;

public class DataReader {
    private Scanner sc = new Scanner(System.in);
    private ConsolePrinter printer;

    public DataReader(ConsolePrinter consolePrinter) {
        this.printer = consolePrinter;
    }

    public void close(){
        sc.close();
    }

    public int getInt(){
        try {
            return sc.nextInt();
        } finally {
            getString();
        }
    }

    public String getString(){
        return sc.nextLine();
    }

    public Book readAndCreateBook() {
        printer.printLine("Tytuł: ");
        String title = sc.nextLine();
        printer.printLine("Autor: ");
        String author = sc.nextLine();
        printer.printLine("Wydawnictwo: ");
        String publisher = sc.nextLine();
        printer.printLine("ISBN: ");
        String isbn = sc.nextLine();
        printer.printLine("Rok wydania: ");
        int releaseDate = getInt();
        printer.printLine("Ilość stron: ");
        int pages = getInt();

        return new Book(title, author, releaseDate, pages, publisher, isbn);
    }

    public Magazine readAndCreateMagazine() {
        printer.printLine("Tytuł: ");
        String title = sc.nextLine();
        printer.printLine("Wydawnictwo: ");
        String publisher = sc.nextLine();
        printer.printLine("Język: ");
        String language = sc.nextLine();
        printer.printLine("Rok wydania: ");
        int year = getInt();
        printer.printLine("Miesiąc: ");
        int month = getInt();
        printer.printLine("Dzień: ");
        int day = getInt();
        printer.printLine("ISBN: ");
        String isbn = sc.nextLine();

        return new Magazine(title, publisher, language, year, month, day, isbn);
    }

    public int getId(){
        printer.printLine("Podaj id rzeczy która chcesz usunać:");
        return getInt();
    }

    public User createLibraryUser() {
        printer.printLine("Imię");
        String firstName = sc.nextLine();
        printer.printLine("Nazwisko");
        String lastName = sc.nextLine();
        printer.printLine("Pesel");
        String pesel = sc.nextLine();
        printer.printLine("Login");
        String login = sc.nextLine();
        printer.printLine("Password");
        String password = sc.nextLine();
        printer.printLine("group admin/user");
        String group = sc.nextLine();

        User user = new User(firstName, lastName, pesel,login,group);
        user.setPassword(password);
        return user;
    }
}

