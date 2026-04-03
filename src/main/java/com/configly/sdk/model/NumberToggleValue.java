package com.configly.sdk.model;

import com.configly.sdk.exception.FeatureToggleException;

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
