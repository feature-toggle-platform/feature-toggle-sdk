package com.configly.sdk.api;

public interface FeatureToggleClient extends AutoCloseable {

    void start();

    FeatureToggles featureToggles();

    @Override
    void close();

}
