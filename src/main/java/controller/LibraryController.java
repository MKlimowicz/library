package controller;

import exception.EmptyCollectionException;
import exception.PublicationAlreadyExistException;
import exception.UserAlreadyExistException;
import io.ConsolePrinter;
import io.DataReader;
import model.*;
import services.PublicationServices;
import services.UserServices;

import java.util.*;


public class LibraryController {
    private ConsolePrinter printer;
    private DataReader dataReader;
    private UserServices userServices;
    private PublicationServices ps;

    public LibraryController(ConsolePrinter consolePrinter,
                             DataReader dataReader,
                             UserServices userServices,
                             PublicationServices publicationServices){
        this.printer = consolePrinter;
        this.dataReader = dataReader;
        this.userServices = userServices;
        this.ps = publicationServices;

    }


    public List<Publication> getAllPublication(TypePublication typePublication){
        List<Publication>  publications = Collections.emptyList();

        try {
            if(typePublication == TypePublication.BOOK) {
                publications = ps.getAllBook();
            }else if(typePublication == TypePublication.MAGAZINE){
                publications = ps.getAllMagazine();
            }
        }catch (EmptyCollectionException e){
            printer.printLine(e.getMessage());
        }

        return publications;
    }

    public int deletePublication(int id, TypePublication typePublication){
        return ps.deletePublication(id,typePublication);
    }


    public Optional<User> addUser() {
        try {
            User user = dataReader.createLibraryUser();
            Optional<User> optionalUser = userServices.addUser(user);

            if (optionalUser.isPresent())
                return optionalUser;

        }catch (UserAlreadyExistException e){
            printer.printLine(e.getMessage());
        }catch (InputMismatchException e){
            printer.printLine("Nie udało sie utworzyć użytkownika, błądnie podane dane");
        }
        return Optional.empty();

    }

    public List<User> getAllUsers() {
        List<User> users = Collections.emptyList();
        try {
            users = userServices.getAllUsers();
        }catch (EmptyCollectionException e){
            printer.printLine(e.getMessage());
        }
        return users;

    }

    public int deleteUser() {
        int execute = 2;
        try {
            printer.printUsers(getAllUsers());
            int id = dataReader.getId();
            execute = userServices.deleteUser(id);
        } catch (InputMismatchException e){
            printer.printLine("Nie udało Ci się usunąć ksiązki, błądnie podane id");
        }
        return execute;
    }


    public Optional<Publication> addPublication(Optional<Publication> publication, TypePublication typePublication) {
        Optional<Publication> publicationOptional = Optional.empty();

        try {
            if (typePublication.equals(TypePublication.BOOK)) {
                publicationOptional = ps.saveBook(publication, publicationOptional);
            } else if (typePublication.equals(TypePublication.MAGAZINE)) {
                publicationOptional = ps.saveMagazine(publication, publicationOptional);
            }
        } catch (PublicationAlreadyExistException e){
            printer.printLine(e.getMessage());
        }

        return publicationOptional;
    }

    public List<Publication> findPublicationsByTitle(String title, TypePublication typePublication) {
        List<Publication>  publications = Collections.emptyList();
        try {
            if (typePublication.equals(TypePublication.BOOK)) {
                publications = ps.findBookByTitle(title);
            } else if (typePublication.equals(TypePublication.MAGAZINE)) {
                publications = ps.findMagazineByTitle(title);
            }

        }catch (EmptyCollectionException e){
            printer.printLine(e.getMessage());
        }

        return publications;
    }

    public List<Publication> getAllPublicationNotBorrow(TypePublication typePublication) {
        List<Publication>  publications = Collections.emptyList();

        try {
            if(typePublication == TypePublication.BOOK) {
                publications = ps.getAllBookNotBorrow();
            }else if(typePublication == TypePublication.MAGAZINE){
                publications = ps.getAllMagazineNotBorrow();
            }
        }catch (EmptyCollectionException e){
            printer.printLine(e.getMessage());
        }

        return publications;

    }
}
