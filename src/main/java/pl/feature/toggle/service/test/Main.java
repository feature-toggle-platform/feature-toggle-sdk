package pl.feature.toggle.service.test;

import pl.feature.toggle.service.sdk.FeatureToggle;

import java.util.UUID;

class Main {

    public static void main(String[] args) throws InterruptedException {
            FeatureToggle.configure()
                    .baseUrl("http://localhost:8090")
                    .project(UUID.fromString("59c673c8-cee5-4725-badb-e82669a7fae7"))
                    .environment(UUID.fromString("c69ad594-7b7c-4f53-bc77-b41528460bad"))
                    .build()
                    .start();
        Thread.currentThread().join();
    }
}
