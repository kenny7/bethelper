package analyzer;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

public class ParserBaseballGameFromStringsTest {

    String regexForParser = "(\\w+\\.*[\\s*\\w+]*) - (\\w+\\.*[\\s*\\w+]*)[\\s]+" +
            "(\\d*):(\\d*)[\\s]+" +
            "(\\d+[\\.{1},{1}]\\d+)*[\\s]+" +
            "(\\d+[\\.{1},{1}]\\d+)*[\\s]+" +
            "(\\d*\\.{1}\\d*\\.{1}\\d*|Today|Yesterday)*";

    ParserBaseballGameFromStrings parserBaseballGameFromStrings = ParserBaseballGameFromStrings.builder()
            .regexForParsers(regexForParser)
            .build();

    List<String> lines = new LinkedList<>();

    @Test
    public void parseBaseballGames() {

        String regexForParser = "(\\w+\\.*[\\s*\\w+]*) - (\\w+\\.*[\\s*\\w+]*)[\\s]+" +
                "(\\d*):(\\d*)[\\s]+" +
                "(\\d+[\\.{1},{1}]\\d+)*[\\s]+" +
                "(\\d+[\\.{1},{1}]\\d+)*[\\s]+" +
                "(\\d*\\.{1}\\d*\\.{1}\\d*|Today|Yesterday)*";

    }

    @Test
    public void createBaseballGame1() {
    }
}
