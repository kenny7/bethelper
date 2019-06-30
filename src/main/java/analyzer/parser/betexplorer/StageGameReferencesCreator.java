package analyzer.parser.betexplorer;

import analyzer.parser.MLBStage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class StageGameReferencesCreator {

    protected String reference;
    protected MLBStage stage;

    public abstract List<String> getReferences();

    public static StageGameReferencesCreator creatorFactory(MLBStage stage, String reference){

        StageGameReferencesCreator creator = null;

        switch(stage){
            case PRE_SEASON: creator = new PreSeasonReferencesCreator(reference, stage);
            break;
            case MAIN: creator = new MainReferencesCreator(reference, stage);
            break;
            case WILD_CARD: creator = new WildCardReferencesCreator(reference, stage);
            break;
            case ALL_STARS: creator = new AllStarsReferencesCreator(reference, stage);
            break;
            case PLAY_OFF: creator = new PlayOffReferencesCreator(reference, stage);
            break;
        }

        return creator;
    }

    protected Document openStagePage(String location){
        Document stagePage = null;
        try {
            stagePage = Jsoup.connect(location)
                    .timeout(20*1000)
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stagePage;
    }

    protected Element find_table_main_In(Document stagePage){
        return stagePage.getElementsByClass("table-main h-mb15 js-tablebanner-t js-tablebanner-ntb").first();
    }

    protected Elements findTableRows(Element table_main){
        if(table_main != null)
            return table_main.getElementsByTag("tr");
        else
            return null;
    }

    protected List<String> createGameReferences(Elements table_rows){

        List<String> gameReferences = new LinkedList<>();

        for(Element row : table_rows){

            Element table_column = find_first_td_In(row);
            Element a = find_tag_a_In(table_column);
            String href = find_href_In(a);
            if(href != null)
                gameReferences.add("https://www.betexplorer.com" + href);
        }

        return gameReferences;

    }

    protected Element find_first_td_In(Element table_row){
        if(table_row != null)
            return table_row.getElementsByTag("td").first();
        else
            return null;
    }

    protected Element find_tag_a_In(Element element){
        if(element != null)
            return element.getElementsByTag("a").first();
        else
            return null;
    }

    protected String find_href_In(Element a){
        if(a != null)
            return a.attr("href");
        else
            return null;
    }
}
