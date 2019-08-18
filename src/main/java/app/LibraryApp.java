package app;


import exception.IncorrectLoginDetailsException;
import io.ConsolePrinter;
import io.DataReader;
import services.UserServices;
import java.util.InputMismatchException;

public class LibraryApp {
    private static final String APP_NAME = "Biblioteka v5.0";
    public static void main(String[] args) {
        LibraryUIAdmin libControl = new LibraryUIAdmin();
        LibraryUIUser libraryUIUser = new LibraryUIUser();
        ConsolePrinter printer = new ConsolePrinter();

        System.out.println(APP_NAME);

        try {

            String group = loginSystem();

            if(group.equals("admin"))
                libControl.controlLoop();
            else if(group.equals("user"))
                libraryUIUser.controlLoop();


        }catch (IncorrectLoginDetailsException e){
            printer.printLine(e.getLocalizedMessage());
        }

    }

    public static String loginSystem(){

        ConsolePrinter printer = new ConsolePrinter();
        DataReader dataReader = new DataReader(printer);
        UserServices userServices = new UserServices();

        boolean exist = false;
        String group = " ";

        try {

            printer.printLine("Podaj swój login: ");
            String login = dataReader.getString();
            printer.printLine("Poda swoje hasło: ");
            String password = dataReader.getString();

            exist = userServices.checkIfThisDataIsCorrect(login,password);

            if(exist){
                 group = userServices.checkTheGroup(login);

                 if(!group.equals(" "))
                    setSessionForThisAccount(login);
                 else
                     printer.printLine("COŚ POSZŁO NIE TAK,TWOJE KONTO NIE POSIADA GRUPY. " +
                             "SKONTAKTUJ SIĘ W TEJ SPRAWIE Z ADMINISTRATOREM");

            }else {
                throw new IncorrectLoginDetailsException("Twój login i hasło są złe, spróbuj ponownie.");
            }

        }catch (InputMismatchException e){
            printer.printLine("Twoje dane zawierają błedy w pisowni");
        }

        return group;
    }

    private static void setSessionForThisAccount(String login) {
        ConsolePrinter printer = new ConsolePrinter();
        DataReader dataReader = new DataReader(printer);
        UserServices userServices = new UserServices();

        int id = userServices.findUserByLogin(login);
        int completed = userServices.saveForASession(id);

        if(completed > 0)
            printer.printLine("Witaj w na swoim koncie w bibliotece.");

    }
}
