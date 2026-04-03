package com.configly.sdk.model;

import com.configly.sdk.exception.FeatureToggleException;

public record TextToggleValue(
        String value
) implements ToggleValue {

    @Override
    public boolean asBoolean() {
        throw new FeatureToggleException("Feature toggle value is TEXT, not BOOLEAN");
    }

    @Override
    public String asText() {
        return value;
    }

    @Override
    public Number asNumber() {
        throw new FeatureToggleException("Feature toggle value is TEXT, not NUMBER");
    }
}
