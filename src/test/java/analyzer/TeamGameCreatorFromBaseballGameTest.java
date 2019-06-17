package analyzer;

import analyzer.dao.FileBaseballGameDAO;
import analyzer.parser.ParserBaseballGameFromStrings;
import org.junit.Test;
import printer.Printer;
import printer.TeamGamePrinter;
import repositories.BaseballGameTeamGameRepository;

public class TeamGameCreatorFromBaseballGameTest {

    @Test
    public void createTeamGames() {

        String regex = "(\\w+\\.*[\\s*\\w+]*) - (\\w+\\.*[\\s*\\w+]*)[\\s]+" +
                "(\\d*):(\\d*)[\\s]+" +
                "(\\d+[\\.{1},{1}]\\d+)*[\\s]+" +
                "(\\d+[\\.{1},{1}]\\d+)*[\\s]+" +
                "(\\d*\\.{1}\\d*\\.{1}\\d*|Today|Yesterday)*";

        ParserBaseballGameFromStrings parser = ParserBaseballGameFromStrings.builder()
                .regexForParsers(regex)
                .build();

        FileBaseballGameDAO baseballGameDAO = FileBaseballGameDAO.builder()
                .file("C:\\Users\\rock\\Documents\\data2018.txt")
                .parser(parser)
                .build();

        BaseballGameTeamGameRepository repository = BaseballGameTeamGameRepository.builder()
                .baseballGameDao(baseballGameDAO)
                .build();
        repository.loadDataToRepository(null);

        TeamGameCreatorFromBaseballGame creator = TeamGameCreatorFromBaseballGame.builder()
                .baseballGames(repository.selectAllBaseballGames())
                .build();

        Printer printer = TeamGamePrinter.builder()
                .teamGames(creator.createTeamGames())
                .build();
        printer.printData();
    }
}