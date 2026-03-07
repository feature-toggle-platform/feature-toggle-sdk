package pl.feature.toggle.service.sdk;

import java.util.UUID;

public interface FeatureToggleEnvironmentStep {

    FeatureToggleOptionalStep environment(UUID environmentId);
}
