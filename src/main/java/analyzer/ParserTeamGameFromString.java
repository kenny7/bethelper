package analyzer;

import entity.competitor.Team;
import entity.competitor.TeamGame;
import entity.competitor.indicator.HomeOrAway;
import entity.competitor.indicator.WinOrLose;
import entity.odd.Odd;
import entity.score.Run;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParserTeamGameFromString {

    private String regex;
    private List<String> inputData = new LinkedList<>();

    public List<TeamGame> parseTeamGames(){
        List<TeamGame> teamGames = new LinkedList<>();
        for(String teamGame : inputData)
            teamGames.add(createTeamGame(teamGame));
        return teamGames;
    }

    public TeamGame createTeamGame(String line){

        TeamGame teamGame = TeamGame.teamGameBuilder().build();

        String[] array = line.split("\t");

        teamGame.setName(array[0]);
        teamGame.setPlace(parsePlace(array[1]));
        teamGame.setOpponent(Team.builder().name(array[2]).build());
        teamGame.setLocalDate(parseLocalDate(array[3]));
        teamGame.setRuns(parseRuns(array[4]));
        teamGame.setMissedRuns(parseRuns(array[5]));
        teamGame.setCoefficientOfWin(parseOdd(array[6]));
        teamGame.setResult(parseResult(array[7]));
        return teamGame;
    }

    private String parseDataInputLine(String line, int groupName){

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);
        String parsedLine = null;
        while (matcher.find())
            parsedLine = matcher.group(groupName);

        return parsedLine;
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
