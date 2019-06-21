package analyzer;

import analyzer.repository.MLBEventRepository;
import analyzer.repository.TeamRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import analyzer.repository.TeamGameRepository;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DatabaseAdministrator {

    private TeamGameRepository teamGameRepository;
    private MLBEventRepository MLBEventRepository;
    private TeamRepository teamRepository;


}
