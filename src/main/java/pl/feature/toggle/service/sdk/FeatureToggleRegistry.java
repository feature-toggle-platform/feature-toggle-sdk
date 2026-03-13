package pl.feature.toggle.service.sdk;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@AllArgsConstructor
final class FeatureToggleRegistry {

    private final Map<String, StoredToggle> toggles;

    static FeatureToggleRegistry from(FeatureToggleSdkSnapshot snapshot) {
        var newMap = new HashMap<String, StoredToggle>();
        snapshot.toggles().forEach(it -> newMap.put(it.name(), StoredToggle.from(it)));
        return new FeatureToggleRegistry(Map.copyOf(newMap));
    }

    static FeatureToggleRegistry empty() {
        return new FeatureToggleRegistry(new HashMap<>());
    }

    StoredToggle get(String name) {
        var toggle = toggles.get(name);
        if (toggle == null) {
            log.debug("[FeatureToggle] Toggle '{}' not found, default value will be used", name);
            return StoredToggle.notActive();
        }
        return toggle;
    }

}
