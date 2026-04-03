package com.configly.sdk.api;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import com.configly.sdk.FeatureToggleSdkBuilder;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class FeatureToggle {

    public static FeatureToggleBaseUrlStep configure() {
        return new FeatureToggleSdkBuilder();
    }

}
