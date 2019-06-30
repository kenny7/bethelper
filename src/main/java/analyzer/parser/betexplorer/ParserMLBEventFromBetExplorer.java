package analyzer.parser.betexplorer;

import analyzer.parser.MLBStage;
import analyzer.repository.TeamRepository;
import entity.competitor.Team;
import entity.event.MLBEvent;
import entity.odd.Odd;
import entity.odd.Winner1;
import entity.odd.Winner2;
import entity.score.Run;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParserMLBEventFromBetExplorer {

    private String localURL = "https://www.betexplorer.com";
    private int countTask;
    private static Semaphore semaphore;
    private List<MLBEvent> mlbEvents;
    private Queue<Future<MLBEvent>> futures;
    private List<String> references;

    public List<MLBEvent> parseMLB(String season){

        List<MLBEvent> mlbEvents = new LinkedList<>();

        Document resultPage = openResultsPage(season);
        Map<MLBStage, String> gameStatusStringMap = createGameStatusReferencesMap(resultPage);

        for(Map.Entry<MLBStage, String> entry : gameStatusStringMap.entrySet()){

            mlbEvents.addAll(parseMLBStage(StageGameReferencesCreator.creatorFactory(entry.getKey(), entry.getValue())));

        }

        return mlbEvents;
    }

    public List<MLBEvent> parseMLBStage(StageGameReferencesCreator creator){

        List<MLBEvent> mlbEvents = new LinkedList<>();
        Queue<Future<MLBEvent>> futures = new LinkedBlockingQueue<>();

        Producer producer = new Producer(5, 3, 300,
                creator.getReferences(), futures);

        producer.createFutures();

        for(Future<MLBEvent> future : futures){
            try {
                MLBEvent mlbEvent = future.get();
                mlbEvent.setMLBStage(creator.getStage());
                mlbEvents.add(mlbEvent);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        return mlbEvents;
    }

    class Producer extends Thread{

        int taskCount;
        int threadCount;
        int timeout;
        List<String> references;
        Queue<Future<MLBEvent>> futures;
        ExecutorService exec;

        public Producer(int taskCount, int threadCount, int timeout,
                        List<String> references, Queue<Future<MLBEvent>> futures) {
            this.taskCount = taskCount;
            this.threadCount = threadCount;
            this.timeout = timeout;
            this.references = references;
            this.futures = futures;
        }

        @Override
        public void run() {
            createFutures();
        }

        public void createFutures(){
            exec = Executors.newFixedThreadPool(threadCount);

            System.out.println(references.size());
            for(String s : references)
                System.out.println(s);

            while (references.size() > 0){

                for (int i = 0; i < switchInt(taskCount, references.size()); i++){

                    String reference = references.get(i);
                    futures.add(exec.submit(new MLBEventParseTask(reference)));

                }

                for(int i = 0; i < switchInt(taskCount, references.size()); i++)
                    references.remove(i);

                try {
                    Thread.sleep(timeout);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        private int switchInt(int taskCount, int listSize){
            if(taskCount < listSize)
                return taskCount;
            else
                return listSize;
        }

        public synchronized void barrierSetter(int barrier){
            barrier = references.size();
        }
    }

    class MLBEventParseTask implements Callable<MLBEvent>{

        public MLBEventParseTask(String reference) {
            this.reference = reference;
        }

        String reference;

        @Override
        public MLBEvent call() throws Exception {
            return parseMLBEvent(reference);
        }
    }

    public MLBEvent parseMLBEvent(String reference){

        MLBEvent mlbEvent = MLBEvent.builder().build();

        Document mlbEventPage = openMLBEventPage(reference);
        Element list_details = find_list_details(mlbEventPage);
        Elements list_details_item = find_list_details_item(list_details);

        String firstTeam = null;
        String secondTeam = null;
        String date = null;
        String scores = null;

        for(int i = 0; i < list_details_item.size(); i++){

            Element item = list_details_item.get(i);

            if(i == 0){
                Element title = find_list_details_item_title(item);
                firstTeam = title.text();
            }
            if(i == 1){
                Element p = find_list_details_item_date(item);
                date = p.attr("data-dt");
                scores = find_list_details_item_score(item).text();
            }
            if(i == 2){
                Element title = find_list_details_item_title(item);
                secondTeam = title.text();
            }
        }

        mlbEvent.setTeam1(new Team(firstTeam));
        mlbEvent.setTeam2(new Team(secondTeam));
        mlbEvent.setTimestamp(parseDate(date));
        mlbEvent.setRuns(parseRuns(scores, mlbEvent));
        mlbEvent.setOdds(parseOdds(mlbEventPage));

        return mlbEvent;

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

    public Document openMLBEventPage(String reference){

        Document mlbEventPage = null;

        try {
            mlbEventPage = Jsoup.connect(reference).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return mlbEventPage;
    }

    public Element find_list_details(Document mlbEventPage){
        if(mlbEventPage != null)
            return mlbEventPage.getElementsByClass("list-details").first();
        else
            return null;
    }

    public Elements find_list_details_item(Element list_details){
        if(list_details != null)
            return list_details.getElementsByClass("list-details__item");
        else
            return null;
    }

    public Element find_list_details_item_title(Element find_list_details_item){
        if(find_list_details_item != null)
            return find_list_details_item.getElementsByClass("list-details__item__title").first();
        else
            return null;
    }

    public Element find_list_details_item_date(Element list_details_item){
        if(list_details_item != null)
            return list_details_item.getElementsByClass("list-details__item__date").first();
        else
            return null;
    }

    public Element find_list_details_item_score(Element list_details_item){
        if(list_details_item != null)
            return list_details_item.getElementsByClass("list-details__item__score").first();
        else
            return null;
    }

    public LocalDateTime parseDate(String date){

        if(date != null) {
            String[] s = date.split(",");
            int year = Integer.parseInt(s[2]);
            int mounth = Integer.parseInt(s[1]);
            int day = Integer.parseInt(s[0]);
            int hour = Integer.parseInt(s[3]);
            int minute = Integer.parseInt(s[4]);
            return LocalDateTime.of(year, mounth, day, hour, minute);
        } else
            return null;
    }

    public List<Run> parseRuns(String scores, MLBEvent mlbEvent){

        if(isRightParseRunData(scores)){
            return parseCheckedData(scores, mlbEvent);
        } else {
            List<Run> runs = new LinkedList<>();
            return runs;
        }
    }

    public boolean isRightParseRunData(String data){

        Pattern pattern = Pattern.compile("\\d+:\\d+");
        Matcher matcher = pattern.matcher(data);

        return matcher.find() ? true : false;
    }

    public List<Run> parseCheckedData(String scores, MLBEvent mlbEvent){

        List<Run> runs = new LinkedList<>();

        String[] score = scores.split(":");

        int runCount = Integer.parseInt(score[0]);
        for(int i = 0; i < runCount; i++){
            runs.add(new Run(mlbEvent.getTeam1().get()));
        }

        runCount = Integer.parseInt(score[1]);
        for(int i = 0; i < runCount; i++){
            runs.add(new Run(mlbEvent.getTeam2().get()));
        }

        return runs;

    }

    public List<Odd> parseOdds(Document mlbEventStage){

        List<Odd> odds = new LinkedList<>();

        String referrer =getReferrer(mlbEventStage);
        String path = getPath(mlbEventStage.location());
        Document oddsData = getOddsData(referrer, path);
        Element tfoot = find_tfoot(oddsData);
        Elements tds = find_tds(tfoot);

        return getOddsFromTds(odds, tds);
    }

    public String getReferrer(Document mlbEventPage){
        return mlbEventPage.location();
    }

    public String getPath(String location){
        String s = location.substring(location.length()-9, location.length()-1);

        StringBuilder path = new StringBuilder("https://www.betexplorer.com/match-odds/");
        path.append(s).append("/1/ha/");

        return path.toString();
    }

    public Document getOddsData(String referrer, String path){

        Document oddsData =null;

        try {
            oddsData = Jsoup.connect(path)
                    .timeout(10*1000)
                    .referrer(referrer).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return oddsData;
    }

    public Element find_tfoot(Document oddsData){
        return oddsData.getElementsByTag("tfoot").first();
    }

    public Elements find_tds(Element element){
        if(element != null)
            return element.getElementsByTag("td");
        else
            return null;
    }

    public List<Odd> getOddsFromTds(List<Odd> odds, Elements tds){
        if(tds != null) {
            int counter = 0;
            for (int i = 0; i < tds.size(); i++) {

                Element td = tds.get(i);
                String d = td.attr("data-odd");
                if (!d.isEmpty()) {
                    counter++;
                    odds.add(chooseOdd(counter, d));
                }
            }
        }
        return odds;
    }

    public Odd chooseOdd(int x, String d){

        Odd odd = null;

        switch (x){
            case 1 : odd = new Winner1();
            break;
            case 2 : odd = new Winner2();
            break;
        }

        if(odd != null)
            odd.setValue(parseDouble(d));

        return odd;
    }

    public Double parseDouble(String d){
        if(!d.isEmpty()) {
            Pattern pattern = Pattern.compile("(\\d+\\.{1}\\d+)");
            Matcher matcher = pattern.matcher(d);

            if(matcher.find())
                d = matcher.group(1);

            return Double.parseDouble(d);
        }
        else
            return new Double(0);
    }

    public Element find_table_main(Element mlbEventPage){

        return mlbEventPage.getElementById("sortable-1");

    }

    public static void main(String[] args) {

        Document document = null;
        try {
            document = Jsoup.connect("https://www.betexplorer.com/baseball/usa/mlb-2017/results/?stage=jgBpIosb").get();

            Elements table = document.getElementsByClass("table-main h-mb15 js-tablebanner-t js-tablebanner-ntb");
            System.out.println(table);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}