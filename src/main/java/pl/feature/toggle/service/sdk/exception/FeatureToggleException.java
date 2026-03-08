package pl.feature.toggle.service.sdk.exception;

public class FeatureToggleException extends RuntimeException {


    public FeatureToggleException(String message) {
        super(message);
    }

    public FeatureToggleException(String message, Throwable cause) {
        super(message, cause);
    }

}
