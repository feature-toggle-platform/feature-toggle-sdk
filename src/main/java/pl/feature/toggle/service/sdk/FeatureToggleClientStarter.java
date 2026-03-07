package pl.feature.toggle.service.sdk;

final class FeatureToggleClientStarter {

    private final FeatureToggleRuntime runtime;
    private final SnapshotFetcher snapshotFetcher;
    private final FeatureToggleSdkConfiguration configuration;
    private final SseBackgroundRunner sseBackgroundRunner;

    FeatureToggleClientStarter(FeatureToggleRuntime runtime, SnapshotFetcher snapshotFetcher,
                               FeatureToggleSdkConfiguration configuration, SseBackgroundRunner sseBackgroundRunner) {
        this.runtime = runtime;
        this.snapshotFetcher = snapshotFetcher;
        this.configuration = configuration;
        this.sseBackgroundRunner = sseBackgroundRunner;
    }

    void start() {
        ensureClientCanBeStarted();

        try {
            var snapshot = snapshotFetcher.fetch(configuration);
            var state = FeatureToggleState.createFrom(snapshot);
            runtime.markStarted(state);
            sseBackgroundRunner.start();
        } catch (Exception exception) {
            runtime.resetStartAttempt();
            throw new IllegalStateException("Failed to start feature toggle client", exception);
        }
    }

    private void ensureClientCanBeStarted() {
        runtime.ensureNotClosed();
        runtime.ensureCanStartAndMarkStarted();
    }
}