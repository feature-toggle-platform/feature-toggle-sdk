package com.configly.sdk;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

record FeatureToggleSdkSnapshot(
        Scope scope,
        Status status,
        long revision,
        Instant generatedAt,
        List<Toggle> toggles
) {

    record Scope(
            UUID projectId,
            UUID environmentId
    ) {

    }

    record Status(
            String project,
            String environment,
            boolean consistent
    ) {
    }

    record Toggle(
            UUID id,
            String name,
            String type,
            Object value,
            boolean active,
            long revision
    ) {
    }

}