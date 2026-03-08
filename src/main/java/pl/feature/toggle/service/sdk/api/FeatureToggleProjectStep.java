package pl.feature.toggle.service.sdk.api;

import java.util.UUID;

public interface FeatureToggleProjectStep {

    FeatureToggleEnvironmentStep project(UUID projectId);

}
