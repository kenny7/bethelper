package analyzer.parser.betexplorer;

import analyzer.parser.MLBStage;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

public class WildCardReferencesCreator extends StageGameReferencesCreator{

    public WildCardReferencesCreator(String reference, MLBStage stage) {
        super(reference, stage);
    }

    public WildCardReferencesCreator() {
    }

    @Override
    public List<String> getReferences() {
        Document stagePage = openPage(reference);
        Element table_main = find_table_main_In(stagePage);
        Elements table_rows = findTableRows(table_main);

        List<String> references = createGameReferences(table_rows);

        return references;
    }
}
