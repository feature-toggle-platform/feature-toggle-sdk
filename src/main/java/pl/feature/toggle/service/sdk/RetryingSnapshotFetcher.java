package pl.feature.toggle.service.sdk;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.feature.toggle.service.sdk.exception.FeatureToggleException;

import java.time.Duration;

@AllArgsConstructor
@Slf4j
final class RetryingSnapshotFetcher implements SnapshotFetcher {

    private final HttpSnapshotFetcher delegate;
    private final FeatureToggleSdkConfiguration configuration;

    @Override
    public FeatureToggleSdkSnapshot fetch() {
        RuntimeException lastException = null;

        for (int attempt = 1; attempt <= configuration.snapshotMaxAttempts(); attempt++) {
            try {
                return delegate.fetch();
            } catch (RuntimeException exception) {
                lastException = exception;
                log.warn("[Feature-toggle] Snapshot fetch attempt {}/{} failed: {}",
                        attempt,
                        configuration.snapshotMaxAttempts(),
                        exception.getMessage());

                if (attempt < configuration.snapshotMaxAttempts()) {
                    sleep(configuration.snapshotRetryDelay());
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
