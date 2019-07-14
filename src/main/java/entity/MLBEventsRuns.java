package entity;

import lombok.Data;

import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Data
public class MLBEventsRuns {


    private Long mlbEventId;

    private Long runId;

}
