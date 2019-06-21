package analyzer.parser;

import entity.competitor.Team;
import entity.competitor.TeamGame;
import entity.competitor.teamGame.HomeOrAway;
import entity.competitor.teamGame.EventResult;
import entity.competitor.indicator.WinPercent;
import entity.odd.Odd;
import entity.odd.Winner1;
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

        teamGame.setEventId(Long.parseLong(array[0]));
        teamGame.setName(array[1]);
        teamGame.setPlace(parsePlace(array[2]));
        teamGame.setOpponent(Team.builder().name(array[3]).build());
        teamGame.setLocalDate(parseLocalDate(array[4]));
        teamGame.setRuns(parseRuns(array[5]));
        teamGame.setMissedRuns(parseRuns(array[6]));
        teamGame.setCoefficientOfWin(parseOdd(array[7]));
        teamGame.setResult(parseResult(array[8]));
        teamGame.setWinPercent(new WinPercent(Double.parseDouble(array[9].replace(",", "."))));
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
        if(s.equals(HomeOrAway.HOME.toString()))
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

        Odd odd = Winner1.winner1Builder()
                .build();

        if(!s.equals("none"))
            odd.setValue(Double.parseDouble(s));

        return odd;
    }

    private EventResult parseResult(String s){
        if(s.equals(EventResult.WIN.toString()))
            return EventResult.WIN;
        else
            return EventResult.LOSE;
    }

}
