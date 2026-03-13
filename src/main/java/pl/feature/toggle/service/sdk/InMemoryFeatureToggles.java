package pl.feature.toggle.service.sdk;

import lombok.extern.slf4j.Slf4j;
import pl.feature.toggle.service.sdk.api.FeatureToggles;
import pl.feature.toggle.service.sdk.model.*;

@Slf4j
class InMemoryFeatureToggles implements FeatureToggles {

    private volatile FeatureToggleRegistry toggleRegistry = FeatureToggleRegistry.empty();

    void refresh(FeatureToggleSdkSnapshot snapshot) {
        this.toggleRegistry = FeatureToggleRegistry.from(snapshot);
    }

    @Override
    public boolean getBoolean(String featureToggle, boolean defaultValue) {
        var toggle = toggleRegistry.get(featureToggle);
        return toggle.resolveBoolean(defaultValue);
    }

    @Override
    public String getText(String featureToggle, String defaultValue) {
        var toggle = toggleRegistry.get(featureToggle);
        return toggle.resolveText(defaultValue);
    }

    @Override
    public Number getNumber(String featureToggle, Number defaultValue) {
        var toggle = toggleRegistry.get(featureToggle);
        return toggle.resolveNumber(defaultValue);
    }
}
