package pl.feature.toggle.service.sdk;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
final class FeatureToggleClientStarter {

    private final FeatureToggleRuntime runtime;
    private final RetryingSnapshotFetcher snapshotFetcher;
    private final FeatureToggleSdkConfiguration configuration;
    private final SseBackgroundRunner sseBackgroundRunner;

    void start() {
        ensureClientCanBeStarted();

        try {
            var snapshotResponse = snapshotFetcher.fetch(configuration);
            var state = FeatureToggleState.createFrom(snapshotResponse);
            runtime.markStarted(state);
            sseBackgroundRunner.start();
        } catch (Exception exception) {
            runtime.resetStartAttempt();
            log.error("[Feature-toggle] Failed to start feature toggle:", exception);
            log.error("[Feature-toggle] Will work in default mode");
        }
    }

    private void ensureClientCanBeStarted() {
        runtime.ensureNotClosed();
        runtime.ensureCanStartAndMarkStarted();
    }
}