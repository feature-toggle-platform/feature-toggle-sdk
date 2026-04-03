package com.configly.sdk;

import com.configly.sdk.model.*;

record StoredToggle(
        boolean active,
        ToggleValue value
) {

    boolean resolveBoolean(boolean defaultValue) {
        if (!active) {
            return defaultValue;
        }
        return value.asBoolean();
    }

    String resolveText(String defaultValue) {
        if (!active) {
            return defaultValue;
        }
        return value.asText();
    }

    Number resolveNumber(Number defaultValue) {
        if (!active) {
            return defaultValue;
        }
        return value.asNumber();
    }

    static StoredToggle notActive() {
        return new StoredToggle(false, null);
    }

    static StoredToggle from(FeatureToggleSdkSnapshot.Toggle toggle) {
        return new StoredToggle(toggle.active(), toToggleValue(toggle));
    }

    static ToggleValue toToggleValue(FeatureToggleSdkSnapshot.Toggle toggle) {
        return switch (ToggleType.valueOf(toggle.type())) {
            case BOOLEAN -> new BooleanToggleValue((Boolean) toggle.value());
            case TEXT -> new TextToggleValue((String) toggle.value());
            case NUMBER -> new NumberToggleValue((Number) toggle.value());
        };
    }
}
