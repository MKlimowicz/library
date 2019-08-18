package app;

import controller.LibraryController;
import exception.NoSuchOptionException;
import io.ConsolePrinter;
import io.DataReader;
import model.*;
import services.PublicationServices;
import services.UserServices;

import java.util.*;

public class LibraryUIAdmin {
    private ConsolePrinter printer = new ConsolePrinter();
    private DataReader dataReader = new DataReader(printer);

    private UserServices userServices = new UserServices();
    private PublicationServices publicationServices = new PublicationServices();

    private LibraryController libraryController = new LibraryController(
            printer,
            dataReader,
            userServices,
            publicationServices
    );

    void controlLoop() {
        Option option;
        do {
            printOptions();
            option = getOption();
            switch (option) {
                case ADD_BOOK:
                    addPublication(TypePublication.BOOK);
                    break;
                case ADD_MAGAZINE:
                    addPublication(TypePublication.MAGAZINE);
                    break;
                case PRINT_BOOKS:
                    printPublication(TypePublication.BOOK);
                    break;
                case PRINT_MAGAZINES:
                    printPublication(TypePublication.MAGAZINE);
                    break;
                case DELETE_BOOK:
                    deletePublication(TypePublication.BOOK);
                    break;
                case DELETE_MAGAZINE:
                    deletePublication(TypePublication.MAGAZINE);
                    break;
                case ADD_USER:
                    addUser();
                    break;
                case PRINT_USERS:
                    printUsers();
                    break;
                case DELETE_USER:
                    deleteUsers();
                    break;
                case FIND_BOOK:
                    findPublicationByTitle(TypePublication.BOOK);
                    break;
                case FIND_MAGAZINE:
                    findPublicationByTitle(TypePublication.MAGAZINE);
                    break;
                case EXIT:
                    exit();
                    break;
                default:
                    printer.printLine("Nie ma takiej opcji, wprowadź ponownie: ");
            }
        } while (option != Option.EXIT);
    }

    private void findPublicationByTitle(TypePublication typePublication){
        try {

            printer.printLine("Podaj tytuł książki");

            printer.printPublication(
                    libraryController.findPublicationsByTitle(
                            dataReader.getString(),
                            typePublication
                    )
            );

        } catch (InputMismatchException e){
            printer.printLine("Twój tutył zawiera błędy, sprawdź to");
        }
    }


    private void deleteUsers() {
        int execute = libraryController.deleteUser();
        if (execute == 1)
            printer.printLine("Udało Ci się usunąć użytkownika.");
        else if(execute == 0)
            printer.printLine("Brak wskazanego użytkownika.");
        else
            printer.printLine("Coś poszło nie tak podczas usuwania użytkownika.");
    }

    private void printUsers() {
        List<User> users = libraryController.getAllUsers();
        printer.printUsers(users);
    }

    private void addUser() {
        Optional<User> add = libraryController.addUser();
        if (add.isEmpty())
            System.out.println("Nie udało sie dodać  użytkownika");
        else
            System.out.println("Użytkownik został dodany: " + add.get().getLogin());
    }



    private void deletePublication(TypePublication typePublication){
        try {
                printPublication(typePublication);
                int id = dataReader.getId();
                int execute = libraryController.deletePublication(id,typePublication);

                if(execute == 1)
                    printer.printLine("Udało Ci się usunąć książke o id: " + id);


        } catch (InputMismatchException e){
            printer.printLine("Nie udało Ci się usunąć ksiązki, błądnie podane id");
        }

    }

    private void exit() {

        boolean execute = userServices.logOutWithSession();

        if(execute)
            printer.printLine("Pomyślnie sie wylogowales");
        else
            printer.printLine("Coś poszło nie tak podczas wylogowywania");

        printer.printLine("KONIEC PROGRAMU PA PA");
    }
    private void printPublication(TypePublication typePublication){
        switch (typePublication){
            case BOOK:
                printer.printPublication(libraryController
                        .getAllPublication(typePublication));
                break;
            case MAGAZINE:
                printer.printPublication(libraryController
                        .getAllPublication(typePublication));
                break;
        }
    }

    private void addPublication(TypePublication typePublication) {

        Optional<Publication> add = libraryController
                .addPublication(
                    getPublication(typePublication),
                    typePublication
                );

        if (add.isEmpty())
            System.out.println("Nie udało sie dodać publikacji");
        else
            System.out.println("Publikacja została dodana: " + add.get());
    }

    private Optional<Publication> getPublication(TypePublication typePublication) {
        Optional<Publication> publication = Optional.empty();

        try {
            switch (typePublication){
                case BOOK:
                    publication = Optional.of(dataReader.readAndCreateBook());
                    break;
                case MAGAZINE:
                    publication = Optional.of(dataReader.readAndCreateMagazine());
                    break;
            }
        } catch (InputMismatchException e){
            printer.printLine("Nie udało sie utworzyć ksiązki, błądnie podane dane");
        }
        return publication;
    }


    private Option getOption() {
        boolean optionOk = false;
        Option option = null;
        while (!optionOk){
            try {
                option = Option.createFromInt(dataReader.getInt());
                optionOk = true;
            }catch (NoSuchOptionException e){
                System.out.println(e.getMessage() + ", podaj ponownie");
            }catch (InputMismatchException ignored){
                System.out.println("Wprowadzona wartość,która nie jest liczbą,podaj ponownie.");
            }
        }
        return option;
    }

    private void printOptions() {
        System.out.println("Wybierz opcje");
        for (Option option:Option.values()) {
            System.out.println(option.toString());
        }
    }

    public enum Option {
        EXIT(0,"Wyjście z programu"),
        ADD_BOOK(1,"Dodanie książki"),
        ADD_MAGAZINE(2,"dodanie magazynu/gazety"),
        ADD_USER(3, "Dodaj czytelnika"),
        PRINT_BOOKS(4, "Wyświetlenie dostepnych ksiązek"),
        PRINT_MAGAZINES(5, "Wyświetlenie dostępnych magazynów/gazet"),
        PRINT_USERS(6, "Wyświetl czytelników"),
        DELETE_BOOK(7, "Usuń książkę"),
        DELETE_MAGAZINE(8, "Usuń magazyn"),
        DELETE_USER(9,"Usuń użytkownika"),
        FIND_BOOK(10, "Wyszukaj książkę po tytule"),
        FIND_MAGAZINE(11, "Wyszukaj magazyn po tytule");

        private int value;
        private String description;

        Option(int value, String description) {
            this.value = value;
            this.description = description;
        }

        @Override
        public String toString() {
            return value + " - " + description;
        }

        static Option createFromInt(int option) throws NoSuchOptionException{
            try {
                return Option.values()[option];
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new NoSuchOptionException("Brak opcji o id " + option);
            }
        }
    }
}
