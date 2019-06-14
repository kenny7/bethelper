package analyzer.dao;

import analyzer.ParserTeamGameFromString;
import entity.competitor.TeamGame;
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
public class FileTeamGameDAO implements TeamGameDAO<TeamGame> {

    private String file;
    private ParserTeamGameFromString parser;

    @Override
    public TeamGame selectById(Long id) {
        return null;
    }

    @Override
    public List<TeamGame> selectByFilter(Filter filter) {
        List<TeamGame> teamGames = new LinkedList<>();
        if(filter == null){
            List<String> list = loadDataFromFile();
            parser.setInputData(list);
            teamGames = parser.parseTeamGames();
        }
        return teamGames;
    }

    @Override
    public void write(TeamGame baseballGame) {

    }

    @Override
    public void delete(TeamGame baseballGame) {

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
