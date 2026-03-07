package pl.feature.toggle.service.sdk;

import java.time.Instant;

record FeatureToggleState(
        boolean initialized,
        long snapshotRevision,
        boolean consistent,
        Instant lastSuccessfulRefreshAt
) {

    static FeatureToggleState notStarted() {
        return new FeatureToggleState(
                false,
                0L,
                false,
                null
        );
    }

    static FeatureToggleState initialized(long snapshotRevision, boolean consistent, Instant lastSuccessfulRefreshAt) {
        return new FeatureToggleState(
                true,
                snapshotRevision,
                consistent,
                lastSuccessfulRefreshAt
        );
    }

    static FeatureToggleState createFrom(FeatureToggleSdkSnapshot snapshot) {
        return FeatureToggleState.initialized(
                snapshot.revision(),
                snapshot.status().consistent(),
                Instant.now());
    }
}
