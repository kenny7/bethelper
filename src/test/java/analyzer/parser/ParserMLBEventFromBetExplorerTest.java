package analyzer.parser;

import analyzer.parser.betexplorer.ParserMLBEventFromBetExplorer;
import entity.event.MLBEvent;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class ParserMLBEventFromBetExplorerTest {

    ParserMLBEventFromBetExplorer parser = new ParserMLBEventFromBetExplorer();

    @Test
    public void parseMLB() {

        List<MLBEvent> mlbEvents = new LinkedList<>();

    }

    @Test
    public void parseMLBEvent() {
    }

    @Test
    public void openResultsPage() {
    }

    @Test
    public void createGameStatusReferencesMap() {

        Document resultPage = parser.openResultsPage("2017");
        System.out.println(parser.createGameStatusReferencesMap(resultPage));

    }

    @Test
    public void createGameReferences() {

        Document startPage =
                parser.openStagePage("https://www.betexplorer.com/baseball/usa/mlb-2017/results/?stage=jgBpIosb");

        Element table_main = parser.find_table_main_In(startPage);
        Elements table_rows = parser.findTableRows(table_main);

        List<String> list = parser.createGameReferences(table_rows);

        for(String s : list){
            System.out.println(s);
        }

    }

    @Test
    public void parseMLBEvent1() {

        MLBEvent mlbEvent =
                parser.parseMLBEvent
                ("https://www.betexplorer.com/baseball/usa/mlb-2017/los-angeles-dodgers-houston-astros/hOvkshIk/");

        System.out.println(mlbEvent);
    }

    @Test
    public void parseMLBStage() {

        List<MLBEvent> mlbEvents =
                parser.parseMLBStage
                        (MLBStage.PRE_SEASON, "https://www.betexplorer.com/baseball/usa/mlb-2017/results/?stage=jgBpIosb");

        for(MLBEvent event : mlbEvents)
            System.out.println(event);

    }

    @Test
    public void find_table_main() {

        Element mlbEventPage =
                parser.openMLBEventPage(
                        "https://www.betexplorer.com/baseball/usa/mlb-2017/los-angeles-dodgers-los-angeles-angels/ULeVXWVO/");

        Element element = parser.find_table_main(mlbEventPage);

        System.out.println(element);

    }

    public static void main(String[] args) throws IOException {

        ParserMLBEventFromBetExplorer parser = new ParserMLBEventFromBetExplorer();

        Element mlbEventPage =
                parser.openMLBEventPage(
                        "https://www.betexplorer.com/baseball/usa/mlb-2017/los-angeles-dodgers-los-angeles-angels/ULeVXWVO/");

        String location = ((Document) mlbEventPage).location();

        String s = location.substring(location.length()-9, location.length()-1);

        StringBuilder path = new StringBuilder("https://www.betexplorer.com/match-odds/");
        path.append(s).append("/1/ha/");

        System.out.println(path.toString());

        Document doc = Jsoup.connect("https://www.betexplorer.com/match-odds/ULeVXWVO/1/ha/")
                //.header("path", "/match-odds/ULeVXWVO/1/ha/")
                .referrer("https://www.betexplorer.com/baseball/usa/mlb-2017/los-angeles-dodgers-los-angeles-angels/ULeVXWVO/")
                .get();

        Elements tr = doc.getElementsByTag("tfoot");

        Element tfoot = tr.first();

        Elements td = tfoot.getElementsByTag("td");

        for(Element e : td){

            String attr = e.attr("data-odd");

            if(!attr.isEmpty())
                System.out.println(attr);



            //System.out.println(e);
            //System.out.println("---------------------------");
        }

    }
}