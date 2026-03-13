package pl.feature.toggle.service.sdk.api;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import pl.feature.toggle.service.sdk.FeatureToggleSdkBuilder;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class FeatureToggle {

    public static FeatureToggleBaseUrlStep configure() {
        return new FeatureToggleSdkBuilder();
    }

}
