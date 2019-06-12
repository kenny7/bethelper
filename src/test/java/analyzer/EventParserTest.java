package analyzer;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class EventParserTest {

    String regexForParser = "(\\w+\\.*[\\s*\\w+]*) - (\\w+\\.*[\\s*\\w+]*)[\\s]+" +
            "(\\d*):(\\d*)[\\s]+" +
            "(\\d+[\\.{1},{1}]\\d+)*[\\s]+" +
            "(\\d+[\\.{1},{1}]\\d+)*[\\s]+" +
            "(\\d*\\.{1}\\d*\\.{1}\\d*|Today|Yesterday)*";

    EventParser eventParser = EventParser.builder()
            .regexForParsers(regexForParser)
            .build();

    List<String> lines = new LinkedList<>();

    @Test
    public void parseDataInputLine() {

        lines.add("team1 - your team2 10:7 1.57 2.43 15.05.19");
        lines.add("Arizona Diamondbacks - Colorado Rockies\t9:8\t1,67\t2,25\t31.03.2018");
        lines.add("Los Angeles Dodgers - San Francisco Giants\t0:1\t1,59\t2,40\t31.03.2018");
        lines.add("New York Mets - St.Louis Cardinals\t6:2\t1,79\t2,06\t31.03.2018");
        lines.add("Los Angeles Angels - New York Yankees\t1:2\t1.78\t  2.07  \t30.04.2018");
        lines.add("Atlanta Braves - New York Mets\t5:8\t \t \t29.05.2018");
        lines.add("Cincinnati Reds - Pittsburgh Pirates\t0:5\t1.72\t  2.18  \t31.03.");

        System.out.println(eventParser.parseDataInputLine(lines.get(6), 7));
    }

    @Test
    public void parseTeamName() {
    }

    @Test
    public void parseTeamRuns() {
    }

    @Test
    public void parseEventCoefficient() {
    }

    @Test
    public void parseEventTimestamp() {

        lines.add("Cincinnati Reds - Pittsburgh Pirates\t0:5\t1.72\t  2.18  \t31.03.");
        lines.add("San Diego Padres - Washington Nationals\t1:4\t2.52\t  1.54  \tToday");
        lines.add("Cleveland Indians - New York Yankees\t8:4\t  2.11  \t1.76\tYesterday");
        SimpleDateFormat sdf = new SimpleDateFormat("dd.mm.yy");

        LocalDate date = eventParser.parseBaseballGameLocalDate(lines.get(1), 7);

        System.out.println(sdf.format(date));

    }

    @Test
    public void createBaseballGame() {
    }

    @Test
    public void parseInputData() {


    }
}
