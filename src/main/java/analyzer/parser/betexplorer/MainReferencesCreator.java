package analyzer.parser.betexplorer;

import analyzer.parser.MLBStage;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.LinkedList;
import java.util.List;

public class MainReferencesCreator extends StageGameReferencesCreator{

    public MainReferencesCreator(String reference, MLBStage stage) {
        super(reference, stage);
    }

    public MainReferencesCreator() {
    }

    @Override
    public List<String> getReferences() {

        Document stagePage = openStagePage(reference + "&month=all");
        Element table_main = find_table_main_In(stagePage);
        Elements table_rows = findTableRows(table_main);

        List<String> references = createGameReferences(table_rows);

        return references;
    }


}
