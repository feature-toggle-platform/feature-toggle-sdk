package pl.feature.toggle.service.sdk.api;

public interface FeatureToggleClient extends AutoCloseable {

    void start();

    FeatureToggles featureToggles();

    @Override
    void close();

}
