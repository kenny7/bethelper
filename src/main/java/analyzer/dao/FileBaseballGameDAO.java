package analyzer.dao;

import analyzer.parser.ParserBaseballGameFromStrings;
import entity.event.BaseballGame;
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
public class FileBaseballGameDAO implements BaseballGameDAO<BaseballGame> {

    private String file;
    private ParserBaseballGameFromStrings parser;

    @Override
    public BaseballGame selectById(Long id) {
        return null;
    }

    @Override
    public List<BaseballGame> selectByFilter(Filter filter) {

        List<BaseballGame> baseballGames = new LinkedList<>();

        if(filter == null){
            List<String> list = loadDataFromFile();
            parser.setInputData(list);
            baseballGames = parser.parseBaseballGames();
        }

        return baseballGames;
    }

    @Override
    public void write(BaseballGame baseballGame) {

    }

    @Override
    public void delete(BaseballGame baseballGame) {

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
