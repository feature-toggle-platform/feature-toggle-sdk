package pl.feature.toggle.service.sdk.api;

import java.util.UUID;

public interface FeatureToggleEnvironmentStep {

    FeatureToggleOptionalStep environment(UUID environmentId);
}
