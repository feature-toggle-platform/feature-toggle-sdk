package pl.feature.toggle.service.sdk;

import java.util.UUID;

public interface FeatureToggleProjectStep {

    FeatureToggleEnvironmentStep project(UUID projectId);

}
