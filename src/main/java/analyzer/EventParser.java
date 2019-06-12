package analyzer;

import analyzer.inputData.StringInput;
import analyzer.outputData.EventOutput;
import competitor.Team;
import event.BaseballGame;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import odd.Odd;
import score.Run;

import java.text.SimpleDateFormat;
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
public class EventParser {
    private List<BaseballGame> events;

    private StringInput stringInput;
    private EventOutput eventOutput;
    private String regexForParsers;

    public void parseInputData(){
        for(String line : stringInput.getLines()){
            events.add(createBaseballGame(line));
        }
    }

    public BaseballGame createBaseballGame(String line){

        BaseballGame baseballGame = BaseballGame.builder().build();
        String firstTeamName = parseTeamName(line, 1);
        String secondTeamName = parseTeamName(line, 2);
        Integer firstTeamRuns = parseTeamRuns(line, 3);
        Integer secondTeamRuns = parseTeamRuns(line, 4);
        Odd win1Coeff = parseEventCoefficient(line, 5);
        Odd win2Coeff = parseEventCoefficient(line, 6);
        LocalDate localDate = parseBaseballGameLocalDate(line, 7);

        Team team = Team.builder()
                .name(firstTeamName)
                .build();
        baseballGame.setTeam1(team);
        List<Run> runs = new LinkedList<>();
        Run run;
        for (int i = 0; i < firstTeamRuns; i++){
            run = Run.builder()
                    .team(team)
                    .build();
            runs.add(run);
        }
        baseballGame.setFirstTeamRun(runs);

        team = Team.builder()
                .name(secondTeamName)
                .build();
        baseballGame.setTeam2(team);
        runs = new LinkedList<>();
        for (int i = 0; i < secondTeamRuns; i++){
            run = Run.builder()
                    .team(team)
                    .build();
            runs.add(run);
        }

        baseballGame.setSecondTeamRun(runs);
        baseballGame.setCoefficientOfWin1(win1Coeff);
        baseballGame.setCoefficientOfWin2(win2Coeff);
        baseballGame.setLocalDate(localDate);

        return baseballGame;
    }

    public String parseDataInputLine(String line, int groupName){

        Pattern pattern = Pattern.compile(regexForParsers);
        Matcher matcher = pattern.matcher(line);
        String parsedLine = null;
        while (matcher.find())
            parsedLine = matcher.group(groupName);

        return parsedLine;
    }

    public String parseTeamName(String line, int groupName){
        String name = parseDataInputLine(line, groupName);
        return name;
    }

    public Integer parseTeamRuns(String line, int groupName){
        String runs = parseDataInputLine(line, groupName);
        return Integer.parseInt(runs);
    }

    public Odd parseEventCoefficient(String line, int groupName){
        String coeff = parseDataInputLine(line, groupName);
        Odd odd = Odd.builder().build();
        if(coeff != null)
            odd.setValue(Double.parseDouble(spaceRemove(commaReplacement(coeff))));
        return odd;
    }

    private String commaReplacement(String s){
        if(s != null)
            return s.replace(",", ".");
        else return null;
    }

    private String spaceRemove(String s){
        if (s != null)
            return s.replace("\\s", "");
        else
            return null;
    }

    public LocalDate parseBaseballGameLocalDate(String line, int groupName){

        LocalDate localDate = null;

        String date = spaceRemove(parseDataInputLine(line, groupName));
        if(isToday(date))
            localDate = today();
        if(isYesterday(date))
            localDate = yesterday();
        if(isDateHasNotYear(date))
            localDate = dateHasNotYear(date);
        if(isDateForSpecifyPattern(line))
            localDate = dateForSpecifyPattern(date);

        return localDate;
    }

    private boolean isToday(String line){
        if(line.equals("Today"))
            return true;
        else
            return false;
    }

    private LocalDate today(){
        return LocalDate.now();
    }

    private boolean isYesterday(String line){
        if(line.equals("Yesterday"))
            return true;
        else
            return false;
    }

    private LocalDate yesterday(){
        return LocalDate.now().minusDays(1);
    }

    private boolean isDateHasNotYear(String line){

        LocalDate localDate = null;
        StringBuilder date = new StringBuilder();

        Pattern pattern = Pattern.compile("\\d{2}\\.{1}\\d{2}\\.{1}");
        Matcher matcher = pattern.matcher(line);

        if(matcher.find())
            return true;
        else
            return false;
    }

    private LocalDate dateHasNotYear(String line){

        StringBuilder date = new StringBuilder();
        LocalDate localDate = null;

        date.append(line).append(LocalDate.now().getYear());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        localDate = LocalDate.parse(date, formatter);

        return localDate;
    }

    private boolean isDateForSpecifyPattern(String line){

        Pattern pattern = Pattern.compile("\\d{2}\\.{1}\\d{2}\\.{1}\\d{2}");
        Matcher matcher = pattern.matcher(line);

        if(matcher.find())
            return true;
        else
            return false;
    }

    private LocalDate dateForSpecifyPattern(String date){
        LocalDate localDate = null;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        localDate = LocalDate.parse(date, formatter);

        return localDate;
    }

    public void setBaseballGamesInOutputData(){
        eventOutput.setBaseballGames(events);
    }

    public static void main(String[] args) {

    }
}
