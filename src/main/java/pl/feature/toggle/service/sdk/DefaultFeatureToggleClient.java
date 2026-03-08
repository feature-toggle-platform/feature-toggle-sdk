package pl.feature.toggle.service.sdk;

import pl.feature.toggle.service.sdk.api.FeatureToggleClient;

final class DefaultFeatureToggleClient implements FeatureToggleClient {

    private final FeatureToggleClientStarter featureToggleClientStarter;
    private final FeatureToggleRuntime runtime;
    private final SseBackgroundRunner sseBackgroundRunner;

    DefaultFeatureToggleClient(FeatureToggleClientStarter featureToggleClientStarter, FeatureToggleRuntime runtime, SseBackgroundRunner sseBackgroundRunner) {
        this.featureToggleClientStarter = featureToggleClientStarter;
        this.runtime = runtime;
        this.sseBackgroundRunner = sseBackgroundRunner;
    }

    @Override
    public void start() {
        featureToggleClientStarter.start();
    }

    @Override
    public boolean getBoolean(String featureToggle, boolean defaultValue) {
        return false;
    }

    @Override
    public String getText(String featureToggle, String defaultValue) {
        return "";
    }

    @Override
    public Number getNumber(String featureToggle, Number defaultValue) {
        return null;
    }

    @Override
    public void close() {
        runtime.close();
        sseBackgroundRunner.stop();
    }
}
