package analyzer.parser.betexplorer;

import org.junit.Test;

import static org.junit.Assert.*;

public class MLBEventParserTest {

    MLBEventParser mlbEventParser = new MLBEventParser();

    @Test
    public void parse() {

        System.out.println(
                mlbEventParser.parse("https://www.betexplorer.com/baseball/usa/mlb-2018/san-diego-padres-colorado-rockies/UNOkLOf8/"));

    }
}