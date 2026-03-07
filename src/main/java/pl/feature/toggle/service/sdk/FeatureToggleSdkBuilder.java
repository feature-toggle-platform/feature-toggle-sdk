package pl.feature.toggle.service.sdk;

import java.time.Duration;
import java.util.Objects;
import java.util.UUID;

final class FeatureToggleSdkBuilder implements FeatureToggleBaseUrlStep,
        FeatureToggleProjectStep,
        FeatureToggleEnvironmentStep,
        FeatureToggleOptionalStep {

    private String baseUrl;
    private UUID projectId;
    private UUID environmentId;
    private Duration connectTimeout = Duration.ofSeconds(3);
    private Duration readTimeout = Duration.ofSeconds(5);
    private Duration reconnectDelay = Duration.ofSeconds(2);

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
    public FeatureToggleClient build() {
        return new DefaultFeatureToggleClient(
                new FeatureToggleSdkConfiguration(
                        baseUrl,
                        projectId,
                        environmentId
                )
        );
    }

    private static String requireNotBlank(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }
        return value;
    }
}
