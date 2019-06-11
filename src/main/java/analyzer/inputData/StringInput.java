package analyzer.inputData;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StringInput implements InputData{

    private List<String> lines;

    @Override
    public List<String> getInputData() {
        return lines;
    }

    @Override
    public <T> void setInputData(T t) {
        if(t.getClass() == String.class) {
            lines.add((String) t);
        }
    }

    public void loadDataFromFile(String file){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            while(reader.ready()){

                lines.add(reader.readLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        StringInput input = StringInput.builder()
                .lines(new LinkedList<>())
                .build();
        input.loadDataFromFile("C:\\Users\\rock\\Documents\\data.txt");

        System.out.println(input.getLines());
    }
}
