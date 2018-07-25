package modules;

import javax.inject.*;
import play.inject.ApplicationLifecycle;
import play.Environment;
import java.util.concurrent.CompletableFuture;

import java.util.Scanner;
import kalkidb.database.Postgres;

// This creates an `ApplicationStart` object once at start-up.
@Singleton
public class ApplicationStart {

    // Inject the application's Environment upon start-up and register hook(s) for shut-down.
    @Inject
    public ApplicationStart(ApplicationLifecycle lifecycle, Environment environment) {
        // Shut-down hook
        lifecycle.addStopHook( () -> {
            return CompletableFuture.completedFuture(null);
        } );

        // Initializes the database
        Postgres.initialize("localhost", "5432", "kalki-db", "kalki-user", "kalki-pass");
//        Postgres.initialize("host.docker.internal", "5432", "kalki-db", "kalki-user", "kalki-pass");
    }
}