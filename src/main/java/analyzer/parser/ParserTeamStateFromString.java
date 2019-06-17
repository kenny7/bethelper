package analyzer.parser;

import entity.competitor.TeamState;
import entity.competitor.TeamStateDateComparator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParserTeamStateFromString {

    private String regex = "(\\w+\\.*[\\s*\\w+]*)\\t" +
            "(\\d{2}\\.{1}\\d{2}\\.{1}\\d{4})\\t" +
            "(\\d*)\\t" +
            "(\\d*)\\t" +
            "(\\d*,{1}\\d*)";

    private List<String> inputData = new LinkedList<>();

    public List<TeamState> parseTeamStates(){

        List<TeamState> teamStates = new LinkedList<>();

        for(String s : inputData)
            teamStates.add(createTeamState(s));

        Collections.sort(teamStates, new TeamStateDateComparator());

        return teamStates;
    }

    public TeamState createTeamState(String line){

        TeamState teamState = TeamState.teamStateBuilder().build();

        teamState.setName(parseName(line, 1));
        teamState.setLocalDate(parseLocalDate(line, 2));
        teamState.setGamesCount(parseInteger(line, 3));
        teamState.setWinsCount(parseInteger(line, 4));
        teamState.setWinPercent(parseDouble(line, 5));

        return teamState;

    }

    private String parseDataInputLine(String line, int groupName){

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);
        String parsedLine = null;
        while (matcher.find())
            parsedLine = matcher.group(groupName);

        return parsedLine;
    }

    private String parseName(String line, int groupName){
        String name = parseDataInputLine(line, groupName);
        return name;
    }

    private LocalDate parseLocalDate(String line, int groupName){
        String date = parseDataInputLine(line, groupName);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate localDate = LocalDate.parse(date, formatter);
        return localDate;
    }

    private Integer parseInteger(String line, int groupName){
        String count = parseDataInputLine(line, groupName);
        return Integer.parseInt(count);
    }

    private Double parseDouble(String line, int groupName){
        String value = parseDataInputLine(line, groupName);
        value = value.replace(",", ".");
        return Double.parseDouble(value);
    }
}
