package repositories;

import score.Run;

import java.util.LinkedList;
import java.util.List;

public class RunRepository {

    private List<Run> runs = new LinkedList<>();

    public void addRun(Run run){
        if(!containsRun(run))
            runs.add(run);
    }

    public boolean containsRun(Run run){
        if(runs.contains(run))
            return true;
        else
            return false;
    }
}
