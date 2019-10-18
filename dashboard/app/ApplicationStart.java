package modules;

import javax.inject.*;
import play.inject.ApplicationLifecycle;
import play.Environment;
import java.util.concurrent.CompletableFuture;

import java.util.Scanner;
import com.typesafe.config.Config;
import edu.cmu.sei.ttg.kalki.database.Postgres;

// This creates an `ApplicationStart` object once at start-up.
@Singleton
public class ApplicationStart {

    // Inject the application's Environment upon start-up and register hook(s) for shut-down.
    @Inject
    public ApplicationStart(ApplicationLifecycle lifecycle, Environment environment, Config config) {
        // Shut-down hook
        lifecycle.addStopHook( () -> {
            return CompletableFuture.completedFuture(null);
        } );

        // Initializes the database
        Postgres.initialize(config.getString("db_host"), "5432", "kalkidb", "kalkiuser", "kalkipass");
    }
}