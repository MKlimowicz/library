package io;

import model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

class DataReaderTest {

    private ConsolePrinter consolePrinter = new ConsolePrinter();
    private DataReader dataReader;

    @BeforeEach
    void initialize(){
        dataReader = new DataReader(consolePrinter);
    }

    @Disabled
    @Test
    void shouldCreatNewBook(){
        //given
        Book bookTest = new Book("test","test",21,21,"test","text");
        given(dataReader.readAndCreateBook()).willReturn(bookTest);
        //when
        Book book = dataReader.readAndCreateBook();
        //then
        assertThat(bookTest,equalTo(book));
    }

}