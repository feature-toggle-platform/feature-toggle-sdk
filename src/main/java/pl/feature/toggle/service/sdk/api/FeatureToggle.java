package pl.feature.toggle.service.sdk.api;


import pl.feature.toggle.service.sdk.FeatureToggleSdkBuilder;

public final class FeatureToggle {

    private FeatureToggle() {
    }

    public static FeatureToggleBaseUrlStep configure() {
        return new FeatureToggleSdkBuilder();
    }

}
