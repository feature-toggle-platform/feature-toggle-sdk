package pl.feature.toggle.service.sdk.model;

import pl.feature.toggle.service.sdk.exception.FeatureToggleException;

public record NumberToggleValue(
        Number value
) implements ToggleValue {

    @Override
    public boolean asBoolean() {
        throw new FeatureToggleException("Feature toggle value is NUMBER, not BOOLEAN");
    }

    @Override
    public String asText() {
        throw new FeatureToggleException("Feature toggle value is NUMBER, not TEXT");
    }

    @Override
    public Number asNumber() {
        return value;
    }
}
