import analyzer.EventAnalyzer;
import analyzer.EventParser;
import analyzer.inputData.BaseballGameInput;
import analyzer.inputData.StringInput;
import analyzer.outputData.EventOutput;
import analyzer.outputData.TeamGameOutput;
import competitor.TeamGame;
import event.BaseballGame;

import java.util.LinkedList;
import java.util.List;

public class main {

    public static void main(String[] args) {

        List<String> lines = new LinkedList<>();
        lines.add("team1 - your team2 10:7 1.57 2.43 15.05.19");

        StringInput stringInput = StringInput.builder()
                .lines(new LinkedList<>())
                //.lines(lines)
                .build();
        stringInput.loadDataFromFile("C:\\Users\\rock\\Documents\\data2019.txt");

        String regexForParser = "(\\w+\\.*[\\s*\\w+]*) - (\\w+\\.*[\\s*\\w+]*)[\\s]+" +
                "(\\d*):(\\d*)[\\s]+" +
                "(\\d+[\\.{1},{1}]\\d+)*[\\s]+" +
                "(\\d+[\\.{1},{1}]\\d+)*[\\s]+" +
                "(\\d*\\.{1}\\d*\\.{1}\\d*|Today|Yesterday)*";
        EventOutput eventOutput = EventOutput.builder().baseballGames(new LinkedList<BaseballGame>()).build();

        EventParser eventParser = EventParser.builder()
                .events(new LinkedList<>())
                .stringInput(stringInput)
                .regexForParsers(regexForParser)
                .eventOutput(eventOutput)
                .build();

        eventParser.parseInputData();
        eventParser.setBaseballGamesInOutputData();
        eventParser.getEventOutput().submitDataOutput();

        EventAnalyzer analyzer = EventAnalyzer.builder()
                .teamGames(new LinkedList<>())
                .baseballGameInput(new BaseballGameInput(eventParser.getEventOutput().getOutputData()))
                .outputData(new TeamGameOutput(new LinkedList<>()))
                .build();

        analyzer.createTeamGames();
        analyzer.setTeamGameInOutputData();
        analyzer.getOutputData().submitDataOutput();

    }
}
