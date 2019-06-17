package analyzer.teamStateAnalyzer;

import analyzer.parser.ParserTeamGameFromString;
import analyzer.dao.FileTeamGameDAO;
import entity.competitor.TeamGame;
import org.junit.Test;
import repositories.TeamGameTextFileRepository;

import java.util.List;

public class DataPresenterTest {

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

    DataPresenter dataPresenter = DataPresenter.builder()
            .teamGameRepository(repository)
            .build();

    @Test
    public void presentDate() {
        repository.loadDataToRepository(null);

        List<TeamGame> teamGames = repository.selectByName("Arizona Diamondbacks");

        dataPresenter.setTeamGames(teamGames);

        dataPresenter.presentDate();


    }
}