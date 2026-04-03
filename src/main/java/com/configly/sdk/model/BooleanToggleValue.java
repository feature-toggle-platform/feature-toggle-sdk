package com.configly.sdk.model;

import com.configly.sdk.exception.FeatureToggleException;

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
