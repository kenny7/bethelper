package repositories;

import analyzer.parser.ParserBaseballGameFromStrings;
import analyzer.dao.FileBaseballGameDAO;
import org.junit.Test;
import printer.BaseballGamePrinter;
import printer.Printer;

public class MLBEventRepositoryTest {

    @Test
    public void selectAllBaseballGames() {

        String regex = "(\\w+\\.*[\\s*\\w+]*) - (\\w+\\.*[\\s*\\w+]*)[\\s]+" +
                "(\\d*):(\\d*)[\\s]+" +
                "(\\d+[\\.{1},{1}]\\d+)*[\\s]+" +
                "(\\d+[\\.{1},{1}]\\d+)*[\\s]+" +
                "(\\d*\\.{1}\\d*\\.{1}\\d*|Today|Yesterday)*";

        ParserBaseballGameFromStrings parser = ParserBaseballGameFromStrings.builder()
                .regexForParsers(regex)
                .build();

        FileBaseballGameDAO baseballGameDAO = FileBaseballGameDAO.builder()
                .file("C:\\Users\\rock\\Documents\\baseballgames.txt")
                .parser(parser)
                .build();

        BaseballGameTeamGameRepository repository = BaseballGameTeamGameRepository.builder()
                .baseballGameDao(baseballGameDAO)
                .build();
        repository.loadDataToRepository(null);

        Printer printer = BaseballGamePrinter.builder()
                .MLBEvents(repository.selectAllBaseballGames())
                .build();

        printer.printData();
    }


}