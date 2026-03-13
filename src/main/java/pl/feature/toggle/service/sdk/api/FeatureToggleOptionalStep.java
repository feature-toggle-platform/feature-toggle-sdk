package pl.feature.toggle.service.sdk.api;

import java.time.Duration;

public interface FeatureToggleOptionalStep {

    FeatureToggleOptionalStep connectTimeout(Duration connectTimeout);

    FeatureToggleOptionalStep readTimeout(Duration readTimeout);

    FeatureToggleOptionalStep reconnectDelay(Duration reconnectDelay);

    FeatureToggleOptionalStep snapshotMaxAttempts(int snapshotMaxAttempts);

    FeatureToggleOptionalStep snapshotRetryDelay(Duration snapshotRetryDelay);

    FeatureToggleClient build();

}
