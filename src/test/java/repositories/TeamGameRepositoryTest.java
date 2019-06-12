package repositories;

import competitor.TeamGame;
import org.junit.Test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class TeamGameRepositoryTest {

    @Test
    public void write() {
    }

    @Test
    public void selectById() {
    }

    @Test
    public void selectBeforeDateNotIncluding() throws ParseException {

        Repository repository = TeamGameRepository.builder()
                .file("C:\\Users\\rock\\Documents\\BaseballTeamGames2019.txt")
                .teamGames(new LinkedList<>())
                .build();

        LocalDate localDate = LocalDate.of(2019, 05, 30);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        System.out.println(localDate.format(formatter));

        List<TeamGame> teamGames = ((TeamGameRepository) repository).selectBeforeDateNotIncluding(localDate);

        for (TeamGame teamGame : teamGames)
            System.out.println(teamGame);
    }
}