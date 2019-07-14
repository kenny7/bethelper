package analyzer.parser.betexplorer;

import analyzer.parser.MLBStage;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

public class PlayOffReferencesCreator extends StageGameReferencesCreator{

    public PlayOffReferencesCreator(String reference, MLBStage stage) {
        super(reference, stage);
    }

    public PlayOffReferencesCreator() {
    }

    @Override
    public List<String> getReferences() {
        Document stagePage = openPage(reference);
        Element table_main = find_table_main_In(stagePage);
        Elements table_rows = findTableRows(table_main);

        List<String> references = createGameReferences(table_rows);

        return references;
    }

    @Override
    protected Element find_table_main_In(Document stagePage){
        return stagePage.getElementsByClass("table-main h-mb15 js-tablebanner-t js-tablebanner-ntb").first();
    }
}
