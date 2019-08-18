package app;

import controller.LibraryController;
import exception.NoSuchOptionException;
import io.ConsolePrinter;
import io.DataReader;
import model.TypePublication;
import services.PublicationServices;
import services.UserServices;

import java.util.InputMismatchException;


public class LibraryUIUser {
    private ConsolePrinter printer = new ConsolePrinter();
    private DataReader dataReader = new DataReader(printer);
    private UserServices userServices = new UserServices();
    private PublicationServices publicationServices = new PublicationServices();
    private LibraryController libraryController = new LibraryController(printer,dataReader,userServices,publicationServices);


    public void controlLoop() {
    LibraryUIUser.Option option;
        do {
            printOptions();
            option = getOption();
            switch (option) {
                case PRINT_BOOK_NOT_BORROW:
                   publicationsNotBorrow(TypePublication.BOOK);
                    break;
                case PRINT_MAGAZINES_NOT_BORROW:
                    publicationsNotBorrow(TypePublication.MAGAZINE);
                    break;
                case PRINT_BOOK_BORROW:
                    publicationsBorrow(TypePublication.BOOK);
                    break;
                case PRINT_MAGAZINES_BORROW:
                    publicationsBorrow(TypePublication.MAGAZINE);
                    break;
                case EXIT:
                    exit();
                    break;
                default:
                    printer.printLine("Nie ma takiej opcji, wprowadź ponownie: ");
            }
        } while (option != LibraryUIUser.Option.EXIT);

    }

    private void publicationsBorrow(TypePublication publication) {

    }

    private void publicationsNotBorrow(TypePublication publication) {
        printer.printPublication(libraryController.getAllPublicationNotBorrow(publication));
    }

    private void exit() {
        boolean execute = userServices.logOutWithSession();

        if(execute)
            printer.printLine("Pomyślnie sie wylogowales");
        else
            printer.printLine("Coś poszło nie tak podczas wylogowywania");
    }

    private LibraryUIUser.Option getOption() {
        boolean optionOk = false;
        LibraryUIUser.Option option = null;
        while (!optionOk){
            try {
                option = LibraryUIUser.Option.createFromInt(dataReader.getInt());
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
        for (LibraryUIUser.Option option: LibraryUIUser.Option.values()) {
            System.out.println(option.toString());
        }
    }


    public enum Option {
        EXIT(0,"Wyjście z programu"),
        PRINT_BOOK_NOT_BORROW(1,"Wyświetl dostępne książki"),
        PRINT_MAGAZINES_NOT_BORROW(2,"Wyświetl książki wypożyczone przeze mnie"),
        PRINT_BOOK_BORROW(3,"Wyświetl książki wypożyczone przeze mnie"),
        PRINT_MAGAZINES_BORROW(4,"Wyświetl książki wypożyczone przeze mnie"),
        BORROW_BOOK(5,"Wypożycz książke"),
        RETURN_BOOK(6, "Zwróć książke");


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

        static LibraryUIUser.Option createFromInt(int option) throws NoSuchOptionException {
            try {
                return LibraryUIUser.Option.values()[option];
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new NoSuchOptionException("Brak opcji o id " + option);
            }
        }
    }
}
