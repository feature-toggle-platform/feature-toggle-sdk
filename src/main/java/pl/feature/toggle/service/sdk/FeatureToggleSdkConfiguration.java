package pl.feature.toggle.service.sdk;

import java.util.UUID;

record FeatureToggleSdkConfiguration(
        String baseUrl,
        UUID projectId,
        UUID environmentId
) {
}
