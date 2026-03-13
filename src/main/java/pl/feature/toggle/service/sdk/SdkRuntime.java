package pl.feature.toggle.service.sdk;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.feature.toggle.service.sdk.api.FeatureToggles;

@AllArgsConstructor
@Slf4j
final class SdkRuntime {

    private final SnapshotFetcher snapshotFetcher;
    private final InMemoryFeatureToggles featureToggles;

    void refreshMemory() {
        var snapshot = snapshotFetcher.fetch();
        featureToggles.refresh(snapshot);
        log.info("[FeatureToggle] Feature toggle client refreshed");
    }

    FeatureToggles featureToggles() {
        return featureToggles;
    }
}
