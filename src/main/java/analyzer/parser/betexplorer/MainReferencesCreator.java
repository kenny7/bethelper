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

        List<String> references = new LinkedList<>();

        Document stagePage = openPage(reference);
        Elements monthsReferencesList = getMonths(stagePage);
        System.out.println(monthsReferencesList);

        for(int i = 0; i < monthsReferencesList.size() - 1; i++){
            Element month = monthsReferencesList.get(i);
            Element a = find_tag_a_In(month);
            String href = "https://www.betexplorer.com" + find_href_In(a);
            Document monthPage = openPage(href);

            Element table_main = find_table_main_In(monthPage);
            Elements table_rows = findTableRows(table_main);

            references.addAll(createGameReferences(table_rows));
        }

        return references;
    }

    private Elements getMonths(Document stagePage){
        if(stagePage != null) {
            Element listMonth = stagePage.getElementsByClass("list-tabs list-tabs--secondary list-tabs--short").first();
            Elements months = listMonth.getElementsByClass("list-tabs__item");
            return months;
        }
        else
            return null;
    }
}