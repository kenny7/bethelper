package analyzer.dao;

import analyzer.parser.ParserTeamStateFromString;
import entity.competitor.TeamState;
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
public class FileTeamStateDAO {

    private String file;
    private ParserTeamStateFromString parser;

    public List<TeamState> selectByFilter(Filter filter) {
        List<TeamState> teamStates = new LinkedList<>();
        if(filter == null){
            List<String> list = loadDataFromFile();
            parser.setInputData(list);
            teamStates = parser.parseTeamStates();
        }
        return teamStates;
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
