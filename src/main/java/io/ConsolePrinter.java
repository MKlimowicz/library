package io;

import model.Book;
import model.Magazine;
import model.Publication;
import model.User;

import java.util.Collections;
import java.util.List;

public class ConsolePrinter {
    public void printLine(String text){
        System.out.println(text);
    }

    public void printPublication(List<Publication> publications){
            publications
                .forEach(p -> printLine(p.toString()));

    }
    public void printMagazine(List<Magazine> magazines){
        magazines.stream()
                .map(Magazine::toString)
                .forEach(this::printLine);
    }

    public void printUsers(List<User> users){
        users.stream()
                .map(User::toString)
                .forEach(this::printLine);
    }
}
