package com.configly.sdk;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.configly.sdk.api.FeatureToggleClient;
import com.configly.sdk.api.FeatureToggles;
import com.configly.sdk.exception.FeatureToggleException;

@Slf4j
@RequiredArgsConstructor
final class DefaultFeatureToggleClient implements FeatureToggleClient {

    private final SdkRuntime runtime;
    private final SseRunner sseRunner;
    private boolean started;

    @Override
    public void start() {
        if (started) {
            throw new FeatureToggleException("Feature toggle client is already started");
        }

        runtime.refreshMemory();
        sseRunner.start();
        started = true;
        log.info("[FeatureToggle] Feature toggle client started");
    }

    @Override
    public FeatureToggles featureToggles() {
        return runtime.featureToggles();
    }

    @Override
    public void close() {
        sseRunner.stop();
        started = false;
        log.info("[FeatureToggle] Feature toggle client stopped");
    }
}