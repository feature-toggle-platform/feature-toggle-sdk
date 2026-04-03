package com.configly.sdk;

import java.time.Duration;
import java.util.UUID;

record FeatureToggleSdkConfiguration(
        String baseUrl,
        UUID projectId,
        UUID environmentId,
        Duration readTimeout,
        Duration reconnectionDelay,
        Duration connectTimeout,
        int snapshotMaxAttempts,
        Duration snapshotRetryDelay
) {
}
