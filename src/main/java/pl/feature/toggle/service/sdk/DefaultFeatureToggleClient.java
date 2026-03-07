package pl.feature.toggle.service.sdk;

final class DefaultFeatureToggleClient implements FeatureToggleClient {

    private final FeatureToggleSdkConfiguration configuration;

    DefaultFeatureToggleClient(FeatureToggleSdkConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public void start() {

    }

    @Override
    public boolean getBoolean(String featureToggle, boolean defaultValue) {
        return false;
    }

    @Override
    public String getText(String featureToggle, String defaultValue) {
        return "";
    }

    @Override
    public Number getNumber(String featureToggle, Number defaultValue) {
        return null;
    }

    @Override
    public void close() {

    }
}
