package pl.feature.toggle.service.sdk.api;

public interface FeatureToggles {

    boolean getBoolean(String featureToggle, boolean defaultValue);

    String getText(String featureToggle, String defaultValue);

    Number getNumber(String featureToggle, Number defaultValue);

}
