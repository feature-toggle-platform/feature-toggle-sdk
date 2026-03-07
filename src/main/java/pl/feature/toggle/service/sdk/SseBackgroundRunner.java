package pl.feature.toggle.service.sdk;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static java.lang.Thread.sleep;

final class SseBackgroundRunner {

    private final HttpClient httpClient;
    private final SnapshotFetcher snapshotFetcher;
    private final FeatureToggleRuntime runtime;
    private final FeatureToggleSdkConfiguration configuration;

    private volatile boolean running = true;

    SseBackgroundRunner(
            HttpClient httpClient,
            SnapshotFetcher snapshotFetcher,
            FeatureToggleRuntime runtime,
            FeatureToggleSdkConfiguration configuration
    ) {
        this.httpClient = httpClient;
        this.snapshotFetcher = snapshotFetcher;
        this.runtime = runtime;
        this.configuration = configuration;
    }

    void stop() {
        running = false;
    }

    void start() {
        Thread.startVirtualThread(this::runLoop);
    }

    private void runLoop() {
        while (running) {
            try {
                var request = HttpRequest.newBuilder()
                        .uri(sseUri(configuration))
                        .GET()
                        .build();

                var response = httpClient.send(request, HttpResponse.BodyHandlers.ofLines());
                System.out.println("Received response from sse: " + response.body());

                response.body().forEach(line -> {
                    if (line.startsWith("data:")) {
                        handleEvent(line.substring(5).trim());
                    }
                });

            } catch (Exception e) {
                System.err.println("SSE connection failed, retrying...");
                try {
                    sleep(2000);
                } catch (InterruptedException ignored) {
                }
            }
        }
    }

    private void handleEvent(String eventData) {
        try {
            System.out.println("SSE event received: " + eventData);
            var snapshot = snapshotFetcher.fetch(configuration);
            var state = FeatureToggleState.createFrom(snapshot);
            runtime.update(state);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private URI sseUri(FeatureToggleSdkConfiguration configuration) {
        return URI.create(
                configuration.baseUrl()
                        + "/rest/api/sdk/projects/" + configuration.projectId()
                        + "/environments/" + configuration.environmentId()
                        + "/feature-toggles/stream"
        );
    }

}
