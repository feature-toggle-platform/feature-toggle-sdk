package pl.feature.toggle.service.sdk.model;

import pl.feature.toggle.service.sdk.exception.FeatureToggleException;

public record BooleanToggleValue(
        boolean value
) implements ToggleValue {

    @Override
    public boolean asBoolean() {
        return value;
    }

    @Override
    public String asText() {
        throw new FeatureToggleException("Feature toggle value is BOOLEAN, not TEXT");
    }

    @Override
    public Number asNumber() {
        throw new FeatureToggleException("Feature toggle value is BOOLEAN, not NUMBER");
    }
}
