package pl.feature.toggle.service.sdk;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

final class SnapshotFetcher {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    SnapshotFetcher(HttpClient httpClient, ObjectMapper objectMapper) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
    }

    FeatureToggleSdkSnapshot fetch(FeatureToggleSdkConfiguration configuration) {
        try {
            var uri = snapshotUri(configuration);
            System.out.println("Snapshot URI: " + uri);
            var requestBuilder = HttpRequest.newBuilder(uri)
                    .GET()
                    .timeout(configuration.readTimeout())
                    .header("Accept", "application/json");

            var response = httpClient.send(requestBuilder.build(), HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() < 200 || response.statusCode() >= 300) {
                throw new IllegalStateException("Snapshot fetch failed with status: " + response.statusCode());
            }

            var snapshot = objectMapper.readValue(response.body(), FeatureToggleSdkSnapshot.class);
            System.out.println("Reveived Snapshot from snapshot to fetch: " + objectMapper.writeValueAsString(snapshot));
            return snapshot;
        } catch (Exception exception) {
            throw new IllegalStateException("Failed to fetch feature toggle snapshot", exception);
        }
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
