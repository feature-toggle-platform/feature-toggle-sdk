package pl.feature.toggle.service.sdk;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.feature.toggle.service.sdk.exception.FeatureToggleException;

import java.time.Duration;

@AllArgsConstructor
@Slf4j
final class RetryingSnapshotFetcher {

    private static final int MAX_ATTEMPTS = 3;
    private static final Duration DELAY = Duration.ofSeconds(2);

    private final SnapshotFetcher delegate;

    FeatureToggleSdkSnapshot fetch(FeatureToggleSdkConfiguration configuration) {
        RuntimeException lastException = null;

        for (int attempt = 1; attempt <= MAX_ATTEMPTS; attempt++) {
            try {
                return delegate.fetch(configuration);
            } catch (RuntimeException exception) {
                lastException = exception;
                log.warn("[Feature-toggle] Snapshot fetch attempt {}/{} failed: {}",
                        attempt,
                        MAX_ATTEMPTS,
                        exception.getMessage());

                if (attempt < MAX_ATTEMPTS) {
                    sleep(DELAY);
                }
            }
        }

        throw new FeatureToggleException("Snapshot fetch failed after retries", lastException);
    }

    private void sleep(Duration duration) {
        try {
            Thread.sleep(duration.toMillis());
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new FeatureToggleException("Retry interrupted", exception);
        }
    }
}
