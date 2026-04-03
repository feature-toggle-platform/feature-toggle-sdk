package com.configly.sdk;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.configly.sdk.exception.FeatureToggleException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@AllArgsConstructor
@Slf4j
final class HttpSnapshotFetcher implements SnapshotFetcher {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final FeatureToggleSdkConfiguration configuration;

    private static final String HEADER_ACCEPT = "Accept";
    private static final String HEADER_ACCEPT_VALUE = "application/json";

    @Override
    public FeatureToggleSdkSnapshot fetch() {
        try {
            var uri = snapshotUri(configuration);
            log.debug("Feature-toggle snapshot will be fetched from: {}", uri);

            var request = buildHttpRequest(configuration, uri);
            var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (isErrorResponse(response)) {
                throw new FeatureToggleException("Snapshot fetch failed with status: " + response.statusCode());
            }

            var snapshot = objectMapper.readValue(response.body(), FeatureToggleSdkSnapshot.class);
            log.debug("[Feature-toggle] Snapshot successfully fetched");
            return snapshot;
        } catch (Exception exception) {
            throw new FeatureToggleException("Snapshot fetch failed", exception);
        }
    }

    private boolean isErrorResponse(HttpResponse<String> response) {
        return response.statusCode() < 200 || response.statusCode() >= 300;
    }

    private HttpRequest buildHttpRequest(FeatureToggleSdkConfiguration configuration, URI uri) {
        return HttpRequest.newBuilder(uri)
                .GET()
                .timeout(configuration.readTimeout())
                .header(HEADER_ACCEPT, HEADER_ACCEPT_VALUE)
                .build();
    }

    private URI snapshotUri(FeatureToggleSdkConfiguration configuration) {
        return URI.create(
                configuration.baseUrl()
                        + "/api/sdk/projects/" + configuration.projectId()
                        + "/environments/" + configuration.environmentId()
                        + "/feature-toggles"
        );
    }
}
