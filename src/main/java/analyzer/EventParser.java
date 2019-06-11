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

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

    private String stringDateCheckingAndFormat(String date){
        StringBuilder checkingDate = new StringBuilder(date);

        Pattern pattern = Pattern.compile("\\d{2}\\.{1}\\d{2}\\.{1}");
        Matcher matcher = pattern.matcher(date);

        if(date.equals("Today")){
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy");
            checkingDate = new StringBuilder(sdf.format(today()));
        } else if(date.equals("Yesterday")){
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy");
            checkingDate = new StringBuilder(sdf.format(yesterday()));
        } else if(matcher.find()){
            checkingDate.append("2019");
        }
        return checkingDate.toString();
    }

    private Date today(){
        return new Date(System.currentTimeMillis());
    }

    private Date yesterday(){
        final Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        return calendar.getTime();
    }

    public Timestamp parseEventTimestamp(String line, int groupName){

        String date = spaceRemove(parseDataInputLine(line, groupName));
        SimpleDateFormat sdf = new SimpleDateFormat("dd.mm.yy");
        try {
            date = stringDateCheckingAndFormat(date);
            return new Timestamp(sdf.parse(date).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
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
        Timestamp timestamp = parseEventTimestamp(line, 7);

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
        baseballGame.setGameTimestamp(timestamp);

        return baseballGame;
    }

    public void parseInputData(){
        for(String line : stringInput.getLines()){
            events.add(createBaseballGame(line));
        }
    }

    public void setBaseballGamesInOutputData(){
        eventOutput.setBaseballGames(events);
    }

    public static void main(String[] args) {

    }
}
