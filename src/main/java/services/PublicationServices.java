package services;

import exception.EmptyCollectionException;
import exception.PublicationAlreadyExistException;
import model.Book;
import model.Magazine;
import model.Publication;
import model.TypePublication;
import sqlQuery.BookQuery;
import sqlQuery.MagazineQuery;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;



public class PublicationServices {

    private BookQuery bookQuery;
    private MagazineQuery magazineQuery;

    public PublicationServices() {
        this.bookQuery = new BookQuery();
        this.magazineQuery = new MagazineQuery();
    }

    public Optional<Publication> saveMagazine(Optional<Publication> publication, Optional<Publication> publicationOptional) {
        if (publication.isPresent()) {
            Magazine magazine = (Magazine) publication.get();
            if (magazineQuery.checkIfItExists(magazine.getIsbn()))
                throw new PublicationAlreadyExistException(
                        "Publikacja o takim isbn jest już w bazie : " + magazine.getIsbn());
            else {
                publicationOptional = magazineQuery.addMagazine(magazine);
            }
        }
        return publicationOptional;
    }

    public Optional<Publication> saveBook(Optional<Publication> publication, Optional<Publication> publicationOptional) {
        if (publication.isPresent()) {
            Book book = (Book) publication.get();
            if (bookQuery.checkIfItExists(book.getIsbn()))
                throw new PublicationAlreadyExistException(
                        "Publikacja o takim isbn jest już w bazie : " + book.getIsbn());
            else {
                publicationOptional = bookQuery.addBook(book);
            }
        }
        return publicationOptional;
    }

    public List<Publication> getAllBook() {
        return changeBooksForPublications(bookQuery.getAllBook());
    }

    public List<Publication> getAllMagazine() {
        return changeMagazinesForPublications(magazineQuery.getAllMagazine());
    }

    private List<Publication> changeMagazinesForPublications(List<Magazine> allMagazine) {
        if (allMagazine.size() == 0)
            throw new EmptyCollectionException("Niestety niema żadnych magazynow");

        return allMagazine.stream()
                .map(m -> (Publication) m)
                .collect(Collectors.toList());
    }

    public int deletePublication(int id, TypePublication typePublication) {
        int execute = 2;
        if (typePublication.equals(TypePublication.BOOK)) {
            execute = bookQuery.deleteBookById(id);
        } else if (typePublication.equals(TypePublication.MAGAZINE)) {
            execute =  magazineQuery.deleteMagazineById(id);
        }
       return execute;
    }

    public List<Publication> findBookByTitle(String title) {
        return changeBooksForPublications(bookQuery.findBookByTitle(title));
    }


    public List<Publication> findMagazineByTitle(String title) {
        return changeMagazinesForPublications(magazineQuery.findBookByTitle(title));
    }


    private List<Publication> changeBooksForPublications(List<Book> allBook) {
        if (allBook.size() == 0)
            throw new EmptyCollectionException("Niestety niema żadnych książek");

        return allBook.stream()
                .map(book -> (Publication) book)
                .collect(Collectors.toList());
    }

    public List<Publication> getAllBookNotBorrow() {
        return changeBooksForPublications(bookQuery.getAllBookNotBorrow());
    }

    public List<Publication> getAllMagazineNotBorrow() {
        return changeMagazinesForPublications(magazineQuery.getAllMagazineNotBorrow());
    }
}
