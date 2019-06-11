package repositories;

import competitor.TeamGame;
import org.junit.Test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        Timestamp timestamp = new Timestamp(sdf.parse("30.03.2019").getTime());

        System.out.println(timestamp);

        List<TeamGame> teamGames = ((TeamGameRepository) repository).selectBeforeDateNotIncluding(timestamp);

        for (TeamGame teamGame : teamGames)
            System.out.println(teamGame);
    }
}