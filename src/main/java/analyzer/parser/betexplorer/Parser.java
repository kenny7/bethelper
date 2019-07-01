package analyzer.parser.betexplorer;

import analyzer.parser.MLBStage;
import entity.event.MLBEvent;
import lombok.Data;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Data
public class Parser {

    private int countSynchronousTasks;
    private List<MLBEvent> mlbEvents;
    private List<String> references;
    private MLBStage mlbStage;
    private static ExecutorService parseTaskExecutor;
    private static CountDownLatch stop;
    private static int parserIdCounter;
    private Lock lock;

    public Parser(int countSynchronousTasks) {
        this.countSynchronousTasks = countSynchronousTasks;
        lock  = new ReentrantLock();
        mlbEvents = new LinkedList<>();
        parserIdCounter = 0;
    }

    public List<MLBEvent> parseMLB(String season){

        List<MLBEvent> mlbEvents = new LinkedList<>();

        Document resultPage = openResultsPage(season);
        Map<MLBStage, String> stagesReferences = createGameStatusReferencesMap(resultPage);

        for (Map.Entry<MLBStage, String> entry : stagesReferences.entrySet())
            System.out.println(entry.getKey() + " : " +entry.getValue());

        for(Map.Entry<MLBStage, String> entry : stagesReferences.entrySet()){

            mlbEvents.addAll(parseMLBEventStage(
                    StageGameReferencesCreator.creatorFactory(
                            entry.getKey(), entry.getValue()
                    )
            ));
        }

        return mlbEvents;
    }

    public Document openResultsPage(String season){

        Document resultPage = null;

        StringBuilder url = new StringBuilder("https://www.betexplorer.com/baseball/usa/mlb-")
                .append(season)
                .append("/results/");

        try {
            resultPage = Jsoup.connect(url.toString())
                    .timeout(10*1000)
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return resultPage;
    }

    public Map<MLBStage, String> createGameStatusReferencesMap(Document resultsPage){

        Map<MLBStage, String> gameStatusReferencesMap = new HashMap<>();

        Elements list_tabs_secondary = find_list_tabs_secondary_In(resultsPage);
        Elements list_tabs_items = find_list_tabs_items_In(list_tabs_secondary);

        for(Element list_tabs_item : list_tabs_items){

            Element a = find_tag_a_In(list_tabs_item);
            String href = find_href_In(a);
            String text = a.text();

            String location = new StringBuilder(resultsPage.location()).append(href).toString();

            gameStatusReferencesMap.put(chooseGameStatusByText(text), location);

        }

        return gameStatusReferencesMap;
    }

    public Elements find_list_tabs_secondary_In(Document resultsPage){
        return resultsPage.getElementsByClass("list-tabs list-tabs--secondary");
    }

    public Elements find_list_tabs_items_In(Elements list_tabs_secondary){
        return list_tabs_secondary.first().getElementsByClass("list-tabs__item");
    }

    public Element find_tag_a_In(Element element){
        return element.getElementsByTag("a").first();
    }

    public String find_href_In(Element a){
        return a.attr("href");
    }

    public MLBStage chooseGameStatusByText(String text){

        MLBStage MLBStage = null;

        switch (text){
            case "Pre-season" :
                MLBStage = MLBStage.PRE_SEASON;
                break;
            case "Main" :
                MLBStage = MLBStage.MAIN;
                break;
            case "Wild Card" :
                MLBStage = MLBStage.WILD_CARD;
                break;
            case "Play Offs" :
                MLBStage = MLBStage.PLAY_OFF;
                break;
            case "All Stars" :
                MLBStage = MLBStage.ALL_STARS;
                break;
            default: MLBStage = null;
        }

        return MLBStage;
    }

    public List<MLBEvent> parseMLBEventStage(StageGameReferencesCreator creator){

        references = creator.getReferences();
        mlbStage = creator.getStage();
        stop = new CountDownLatch(references.size());
        parseTaskExecutor = Executors.newFixedThreadPool(countTaskCheck());

        System.out.println("references size = " + references.size());

        for(int i = 0; i < countTaskCheck(); i++)
            parseTaskExecutor.submit(new ParseTask());

        waitParsingComplete();

        shutdownAndAwaitTermination(parseTaskExecutor);

        System.out.println("mlbEvents size = " + mlbEvents.size());

        return mlbEvents;
    }

    private void shutdownAndAwaitTermination(ExecutorService executor){
        parseTaskExecutor.shutdown();
        try {
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                executor.shutdownNow();
                if (!executor.awaitTermination(10, TimeUnit.SECONDS))
                    System.err.println("Pool did not terminate");
            }
        } catch (InterruptedException ie) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    private int countTaskCheck(){
        if(countSynchronousTasks >= references.size())
            return references.size();
        else
            return countSynchronousTasks;
    }

    private void waitParsingComplete(){
        try {
            stop.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    class ParseTask extends Thread{

        private String reference;
        private MLBEventParser mlbEventParser;
        private final int id;

        public ParseTask() {
            mlbEventParser = new MLBEventParser();
            id = ++parserIdCounter;
            this.setName("parser - " + id);
            reference = null;
        }

        String getReference(){
            try{
                if(lock.tryLock(5, TimeUnit.SECONDS)){
                    Iterator<String> iterator = references.iterator();
                    if(iterator.hasNext()) {
                        reference = iterator.next();
                        iterator.remove();
                        System.out.println(this.getName() + " get reference: " + reference);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
            return reference;
        }

        void addInList(MLBEvent mlbEvent){
            try{
                lock.lock();
                if(mlbEvent != null) {
                    mlbEvent.setMLBStage(mlbStage);
                    mlbEvents.add(mlbEvent);
                    System.out.println(this.getName() + ": " + mlbEvent + " was add in event list");
                }
            } finally {
                lock.unlock();
            }
        }

        MLBEvent parseReference(String reference){
            MLBEvent mlbEvent = null;
            try{
                if(reference != null) {
                    System.out.println(this.getName() + " start parsing");
                    mlbEvent = mlbEventParser.parse(reference);
                }
            }finally {
                stop.countDown();
            }
            return mlbEvent;
        }

        void work(){
            while(!this.isInterrupted()){

                reference = getReference();

                MLBEvent mlbEvent = parseReference(reference);

                addInList(mlbEvent);

                reference = null;
            }
        }

        @Override
        public void run() {
            work();
        }
    }

    public static void main(String[] args) {

        Parser parser = new Parser(5);

        StageGameReferencesCreator creator =
                StageGameReferencesCreator.creatorFactory(MLBStage.PRE_SEASON,
                        "https://www.betexplorer.com/baseball/usa/mlb-2017/results/?stage=jgBpIosb");

        List<MLBEvent> events = parser.parseMLBEventStage(creator);

        for
        (MLBEvent event : events)
            System.out.println(event);

    }

}
