package com.configly.sdk;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.configly.sdk.api.*;

import java.net.http.HttpClient;
import java.time.Duration;
import java.util.Objects;
import java.util.UUID;

public final class FeatureToggleSdkBuilder implements FeatureToggleBaseUrlStep,
        FeatureToggleProjectStep,
        FeatureToggleEnvironmentStep,
        FeatureToggleOptionalStep {

    private String baseUrl;
    private UUID projectId;
    private UUID environmentId;
    private Duration connectTimeout = Duration.ofSeconds(3);
    private Duration readTimeout = Duration.ofSeconds(5);
    private Duration reconnectDelay = Duration.ofSeconds(2);
    private int snapshotMaxAttempts = 3;
    private Duration snapshotRetryDelay = Duration.ofSeconds(5);

    @Override
    public FeatureToggleProjectStep baseUrl(String baseUrl) {
        this.baseUrl = requireNotBlank(baseUrl, "baseUrl");
        return this;
    }

    @Override
    public FeatureToggleOptionalStep environment(UUID environmentId) {
        this.environmentId = Objects.requireNonNull(environmentId, "environmentId must not be null");
        return this;
    }

    @Override
    public FeatureToggleEnvironmentStep project(UUID projectId) {
        this.projectId = Objects.requireNonNull(projectId, "projectId must not be null");
        return this;
    }

    @Override
    public FeatureToggleOptionalStep connectTimeout(Duration connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }

    @Override
    public FeatureToggleOptionalStep readTimeout(Duration readTimeout) {
        this.readTimeout = readTimeout;
        return this;
    }

    @Override
    public FeatureToggleOptionalStep reconnectDelay(Duration reconnectDelay) {
        this.reconnectDelay = reconnectDelay;
        return this;
    }

    @Override
    public FeatureToggleOptionalStep snapshotMaxAttempts(int snapshotMaxAttempts) {
        this.snapshotMaxAttempts = snapshotMaxAttempts;
        return this;
    }

    @Override
    public FeatureToggleOptionalStep snapshotRetryDelay(Duration snapshotRetryDelay) {
        this.snapshotRetryDelay = snapshotRetryDelay;
        return this;
    }

    @Override
    public FeatureToggleClient build() {
        var configuration = new FeatureToggleSdkConfiguration(
                baseUrl,
                projectId,
                environmentId,
                readTimeout,
                reconnectDelay,
                connectTimeout,
                snapshotMaxAttempts,
                snapshotRetryDelay
        );
        var httpClient = HttpClient.newBuilder()
                .connectTimeout(connectTimeout)
                .build();
        var objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule());
        var snapshotFetcher = new HttpSnapshotFetcher(httpClient, objectMapper, configuration);
        var retryingSnapshotFetcher = new RetryingSnapshotFetcher(snapshotFetcher, configuration);
        var featureToggles = new InMemoryFeatureToggles();
        var runtime = new SdkRuntime(retryingSnapshotFetcher, featureToggles);

        var sseRunner = new SseBackgroundRunner(
                httpClient,
                configuration,
                runtime::refreshMemory
        );

        return new DefaultFeatureToggleClient(runtime, sseRunner);
    }

    private static String requireNotBlank(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }
        return value;
    }
}
