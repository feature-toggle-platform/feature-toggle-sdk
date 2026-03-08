package pl.feature.toggle.service.sdk.api;

public interface FeatureToggleClient extends AutoCloseable {

    void start();

    boolean getBoolean(String featureToggle, boolean defaultValue);

    String getText(String featureToggle, String defaultValue);

    Number getNumber(String featureToggle, Number defaultValue);

    @Override
    void close();

}
