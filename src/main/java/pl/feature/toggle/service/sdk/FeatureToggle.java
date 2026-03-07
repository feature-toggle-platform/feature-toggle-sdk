package pl.feature.toggle.service.sdk;


public final class FeatureToggle {

    private FeatureToggle() {
    }

    public static FeatureToggleBaseUrlStep configure() {
        return new FeatureToggleSdkBuilder();
    }

}
