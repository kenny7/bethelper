package repositories;

import analyzer.parser.ParserTeamGameFromString;
import analyzer.dao.FileTeamGameDAO;
import entity.competitor.Team;
import org.junit.Test;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TeamGameTextFileRepositoryTest {

    ParserTeamGameFromString parser = ParserTeamGameFromString.builder()
            .regex("\\t")
            .build();

    FileTeamGameDAO dao = FileTeamGameDAO.builder()
            .file("C:\\Users\\rock\\Documents\\TeamGame2018.txt")
            .parser(parser)
            .build();

    TeamGameTextFileRepository repository = TeamGameTextFileRepository.builder()
            .teamGameDAO(dao)
            .build();

    @Test
    public void write() {
    }

    @Test
    public void selectById() {
    }

    @Test
    public void selectBeforeDateNotIncluding() throws ParseException {

       /* TeamGameRepository repository = TeamGameTextFileRepository.builder()
                .file("C:\\Users\\rock\\Documents\\BaseballTeamGames2019.txt")
                .teamGames(new LinkedList<>())
                .build();

        LocalDate localDate = LocalDate.of(2019, 05, 30);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        System.out.println(localDate.format(formatter));

        List<TeamGame> teamGames = ((TeamGameTextFileRepository) repository).selectBeforeDateNotIncluding(localDate);

        for (TeamGame teamGame : teamGames)
            System.out.println(teamGame);*/
    }

    @Test
    public void selectTeamGameByNameAndDate() {
        repository.loadDataToRepository(null);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate localDate = LocalDate.parse("01.05.2018", formatter);

        System.out.println(repository.selectTeamGameByNameAndDate(Team.builder()
                .name("Arizona Diamondbacks")
                .build(), localDate));
    }
}