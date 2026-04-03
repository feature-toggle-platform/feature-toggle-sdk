package com.configly.sdk;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static java.lang.Thread.sleep;

@RequiredArgsConstructor
@Slf4j
final class SseBackgroundRunner implements SseRunner {

    private final HttpClient httpClient;
    private final FeatureToggleSdkConfiguration configuration;
    private final Runnable onRefreshRequiredAction;

    private volatile boolean running = true;

    @Override
    public void stop() {
        running = false;
    }

    @Override
    public void start() {
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
                log.debug("SSE connection established");

                response.body().forEach(line -> {
                    if (line.startsWith("data:")) {
                        handleEvent(line.substring(5).trim());
                    }
                });

            } catch (Exception e) {
                log.warn("SSE connection failed, retrying...");
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
            onRefreshRequiredAction.run();
        } catch (Exception e) {
            log.error("SSE handling event failed", e);
        }
    }

    private URI sseUri(FeatureToggleSdkConfiguration configuration) {
        return URI.create(
                configuration.baseUrl()
                        + "/api/sdk/projects/" + configuration.projectId()
                        + "/environments/" + configuration.environmentId()
                        + "/feature-toggles/stream"
        );
    }

}
