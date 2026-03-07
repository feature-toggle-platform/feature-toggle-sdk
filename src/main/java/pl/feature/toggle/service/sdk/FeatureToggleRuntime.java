package pl.feature.toggle.service.sdk;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

final class FeatureToggleRuntime {

    private final AtomicBoolean started = new AtomicBoolean(false);
    private final AtomicBoolean closed = new AtomicBoolean(false);
    private final AtomicReference<FeatureToggleState> state =
            new AtomicReference<>(FeatureToggleState.notStarted());

    void ensureNotClosed() {
        if (closed.get()) {
            throw new IllegalStateException("Feature toggle client is already closed");
        }
    }

    void ensureCanStartAndMarkStarted() {
        if (!started.compareAndSet(false, true)) {
            throw new IllegalStateException("Feature toggle client is already started");
        }
    }

    void markStarted(FeatureToggleState newState) {
        state.set(Objects.requireNonNull(newState, "newState must not be null"));
    }

    void resetStartAttempt() {
        started.set(false);
        state.set(FeatureToggleState.notStarted());
    }

    void update(FeatureToggleState newState) {
        ensureNotClosed();

        if (!started.get()) {
            throw new IllegalStateException("Feature toggle client is not started");
        }

        state.set(Objects.requireNonNull(newState, "newState must not be null"));
    }

    FeatureToggleState currentState() {
        return state.get();
    }

    boolean isStarted() {
        return started.get();
    }

    boolean isClosed() {
        return closed.get();
    }

    void close() {
        ensureNotClosed();
        closed.set(true);
    }
}