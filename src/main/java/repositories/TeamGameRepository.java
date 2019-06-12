package repositories;

import competitor.Team;
import competitor.TeamGame;
import competitor.indicator.HomeOrAway;
import competitor.indicator.WinOrLose;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import odd.Odd;
import score.Run;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamGameRepository implements Repository{

    private List<TeamGame> teamGames;
    private String file;

    @Override
    public <T> void write(T t) {

    }

    @Override
    public TeamGame selectById(Long id) {
        return null;
    }

    @Override
    public TeamGame selectAllByName(String name) {
        return null;
    }

    @Override
    public List<TeamGame> selectBeforeDateNotIncluding(LocalDate localDate) {

        loadTeamGamesFromFile(file);

        List<TeamGame> selectedTeamGames = new LinkedList<>();

        for(TeamGame teamGame : teamGames){
            if(compareDateLessThanDate(teamGame.getLocalDate(), localDate)){
                selectedTeamGames.add(teamGame);
            }
        }

        return selectedTeamGames;
    }

    private boolean compareDateLessThanDate(LocalDate comparedDate, LocalDate lessThanDate){

        if(comparedDate.getYear() <= lessThanDate.getYear()){
                if(comparedDate.getDayOfYear() < lessThanDate.getDayOfYear())
                    return true;
                else
                    return false;
        } else
            return false;
    }

    private void loadTeamGamesFromFile(String file){
        try {

            BufferedReader reader = new BufferedReader(new FileReader(file));
            TeamGame teamGame = null;

            while (reader.ready()){

                teamGame = TeamGame.teamGameBuilder().build();
                String[] array = reader.readLine().split("\\t");

                teamGame.setName(array[0]);
                teamGame.setPlace(parsePlace(array[1]));
                teamGame.setOpponent(Team.builder().name(array[2]).build());
                teamGame.setLocalDate(parseLocalDate(array[3]));
                teamGame.setRuns(parseRuns(array[4]));
                teamGame.setMissedRuns(parseRuns(array[5]));
                teamGame.setCoefficientOfWin(parseOdd(array[6]));
                teamGame.setResult(parseResult(array[7]));

                teamGames.add(teamGame);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private HomeOrAway parsePlace(String s){
        if(s.equals(HomeOrAway.HOME))
            return HomeOrAway.HOME;
        else
            return HomeOrAway.AWAY;
    }

    private LocalDate parseLocalDate(String s){

        LocalDate localDate = null;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        localDate = LocalDate.parse(s, formatter);

        return localDate;
    }

    private List<Run> parseRuns(String s){

        List<Run> runs = new LinkedList<>();
        int count = Integer.parseInt(s);

        for(int i = 0; i < count; i++){
            runs.add(new Run());
        }
        return runs;
    }

    private Odd parseOdd(String s){

        Odd odd = Odd.builder()
                .build();

        if(!s.equals("none"))
            odd.setValue(Double.parseDouble(s));

        return odd;
    }

    private WinOrLose parseResult(String s){
        if(s.equals(WinOrLose.WIN.toString()))
            return WinOrLose.WIN;
        else
            return WinOrLose.LOSE;
    }
}
