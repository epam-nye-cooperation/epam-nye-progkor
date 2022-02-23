package hu.nye.progkor.warehouse.populator;

import java.util.List;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("!prod")
@Slf4j
public class DBPopulatorService {

    private final List<DBPopulator> dbPopulators;

    public DBPopulatorService(final List<DBPopulator> dbPopulators) {
        this.dbPopulators = dbPopulators;
    }

    @PostConstruct
    public void populateDatabase() {
        log.info("Populates data base...");
        dbPopulators.forEach(DBPopulator::populateDatabase);
        log.info("Database populate process is finished.");
    }
}
