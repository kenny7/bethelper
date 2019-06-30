package analyzer.parser.betexplorer;

import analyzer.parser.MLBStage;
import entity.event.MLBEvent;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class PreSeasonReferencesCreator extends StageGameReferencesCreator{

    public PreSeasonReferencesCreator(String reference, MLBStage stage) {
        super(reference, stage);
    }

    public PreSeasonReferencesCreator() {
    }

    @Override
    public List<String> getReferences() {

        Document stagePage = openStagePage(reference);
        Element table_main = find_table_main_In(stagePage);
        Elements table_rows = findTableRows(table_main);

        List<String> references = createGameReferences(table_rows);

        return references;
    }
}
