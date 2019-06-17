package analyzer;

import analyzer.repository.BaseballGameRepository;
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
    private BaseballGameRepository baseballGameRepository;
    private TeamRepository teamRepository;

}
