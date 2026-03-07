package pl.feature.toggle.service.sdk;

import java.time.Duration;
import java.util.UUID;

record FeatureToggleSdkConfiguration(
        String baseUrl,
        UUID projectId,
        UUID environmentId,
        Duration readTimeout,
        Duration reconnectionDelay,
        Duration connectTimeout
) {
}
