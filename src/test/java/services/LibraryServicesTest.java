package services;

import exception.PublicationAlreadyExistException;
import io.ConsolePrinter;
import io.DataReader;
import model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.InputMismatchException;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class LibraryServicesTest {

//    Book book;
//
//    @BeforeEach
//    void initialize(){
//        book = new Book("test","test",21,21,"test","test");
//    }
//
//    @InjectMocks
//    LibraryController libraryServices;
//    @Mock
//    BookServices bookServies;
//    @Mock
//    DataReader dataReader;
//    @Mock
//    ConsolePrinter consolePrinter;
//
//    @Test
//    void shouldReturnTheSameBookIfTheBookWasAdded(){
//        //given
//        given(bookServies.addBook(ArgumentMatchers.any())).willReturn(Optional.of(book));
//        //when
//        Optional<Book> optionalBook = libraryServices.addBook();
//        //then
//        optionalBook.ifPresent(value -> assertThat(value, equalTo(book)));
//    }
//
//    @Test
//    void shouldReturnTheEmptyOptionalIfTheBookWasntAdded(){
//        //given
//        given(bookServies.addBook(ArgumentMatchers.any())).willReturn(Optional.empty());
//        //when
//        Optional<Book> optionalBook = libraryServices.addBook();
//        //then
//        assertThat(optionalBook, is(Optional.empty()));
//    }





}