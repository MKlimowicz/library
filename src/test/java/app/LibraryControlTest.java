package app;

import exception.NoSuchOptionException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.jupiter.api.Assertions.*;

class LibraryControlTest {


    @ParameterizedTest
    @EnumSource(LibraryUIAdmin.Option.class)
    void allOrderStatusShouldBeShorterThan20Chars(LibraryUIAdmin.Option option){
        assertThat(option.toString().length(),lessThan(50));
    }


    @Test
    void shouldReturnOptionWhoIChoose() throws NoSuchOptionException {
        //given
        //when
        LibraryUIAdmin.Option testOne = LibraryUIAdmin.Option.createFromInt(2);
        LibraryUIAdmin.Option testTwo = LibraryUIAdmin.Option.createFromInt(5);
        //then
        assertThat(testOne.toString(),is("2 - dodanie magazynu/gazety"));
        assertThat(testTwo.toString(),is("5 - Usuń książkę"));
    }

    @Test
    void shouldThrowNoSuchOptionExceptionIfGiveTheWronNumber() throws NoSuchOptionException {
        //given
        //when
        //then
        assertThrows(NoSuchOptionException.class,() ->{
            LibraryUIAdmin.Option.createFromInt(10);
        });

    }
}