package pl.feature.toggle.service.sdk;

import java.time.Duration;

public interface FeatureToggleOptionalStep {

    FeatureToggleOptionalStep connectTimeout(Duration connectTimeout);

    FeatureToggleOptionalStep readTimeout(Duration readTimeout);

    FeatureToggleOptionalStep reconnectDelay(Duration reconnectDelay);

    FeatureToggleClient build();

}
