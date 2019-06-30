package analyzer.parser.betexplorer;

import analyzer.parser.MLBStage;
import entity.event.MLBEvent;
import lombok.Data;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.xml.bind.SchemaOutputResolver;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Data
public class Parser {

    private int countSynchronousTasks;
    private static Semaphore semaphore;
    private static CountDownLatch stop;
    private List<MLBEvent> mlbEvents;
    private List<String> references;
    private Lock lock;
    private static ExecutorService mlbEventParserExecutor;
    private static ExecutorService parseTaskExecutor;
    private MLBStage mlbStage;
    private static int parserIdCounter;

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
        semaphore = new Semaphore(countTaskCheck(), true);
        parseTaskExecutor = Executors.newFixedThreadPool(countTaskCheck());

        for(int i = 0; i < countTaskCheck(); i++)
            parseTaskExecutor.submit(new ParseTask());

        waitParsingComplete();
        parseTaskExecutor.shutdown();
        mlbEventParserExecutor.shutdown();

        return mlbEvents;
    }

    private int countTaskCheck(){
        if(countSynchronousTasks < references.size())
            return countSynchronousTasks;
        else
            return references.size();
    }

    private boolean isReferencesIteratorHasNext(){
        synchronized (references){
            return references.iterator().hasNext();
        }
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
            mlbEventParserExecutor = Executors.newFixedThreadPool(countTaskCheck());
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
                    mlbEvents.add(mlbEvent);
                    System.out.println(mlbEvent + "was add in event list");
                }
            } finally {
                lock.unlock();
            }
        }

        void work(){

            while(!this.isInterrupted()){

                reference = getReference();

                MLBEvent mlbEvent = null;

                if(reference != null) {
                    System.out.println(this.getName() + " start parsing " + reference);
                    mlbEvent = mlbEventParser.parse(reference);
                }

                addInList(mlbEvent);
            }

        }

        @Override
        public void run() {
            while (isReferencesIteratorHasNext()) {
                try {
                    semaphore.acquire();

                    synchronized (references) {
                        if (references.iterator().hasNext()) {
                            reference = references.iterator().next();
                            references.remove(reference);
                        } else {
                            System.out.println("references list is empty");
                            reference = "--------------------------";
                            break;
                        }
                    }

                    //todo for test
                    System.out.println("start parsing " + reference);

                    MLBEvent mlbEvent = mlbEventParserExecutor.submit(new MLBEventParser(reference)).get();
                    mlbEvent.setMLBStage(mlbStage);

                    synchronized (mlbEvents) {
                        mlbEvents.add(mlbEvent);
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                    stop.countDown();
                }
            }
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
