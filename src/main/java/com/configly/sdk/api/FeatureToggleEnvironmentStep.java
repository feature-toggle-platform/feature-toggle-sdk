package com.configly.sdk.api;

import java.util.UUID;

public interface FeatureToggleEnvironmentStep {

    FeatureToggleOptionalStep environment(UUID environmentId);
}
