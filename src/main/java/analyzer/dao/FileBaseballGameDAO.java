package analyzer.dao;

import analyzer.parser.ParserBaseballGameFromStrings;
import entity.event.MLBEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import repositories.filter.Filter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileBaseballGameDAO implements BaseballGameDAO<MLBEvent> {

    private String file;
    private ParserBaseballGameFromStrings parser;

    @Override
    public MLBEvent selectById(Long id) {
        return null;
    }

    @Override
    public List<MLBEvent> selectByFilter(Filter filter) {

        List<MLBEvent> MLBEvents = new LinkedList<>();

        if(filter == null){
            List<String> list = loadDataFromFile();
            parser.setInputData(list);
            MLBEvents = parser.parseBaseballGames();
        }

        return MLBEvents;
    }

    @Override
    public void write(MLBEvent MLBEvent) {

    }

    @Override
    public void delete(MLBEvent MLBEvent) {

    }

    public List<String> loadDataFromFile(){

        List<String> result = new LinkedList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            while(reader.ready()){
                result.add(reader.readLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

}
