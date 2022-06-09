package com.greasecake.kooshots.docs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class GoogleDocClient {
    @Value("${google-doc.id}")
    private String docId;
    @Value("${google-doc.range}")
    private String docRange;
    @Value("${google-doc.token}")
    private String docToken;
    @Value("${google-doc.url}")
    private String docUrl;

    public ResponseRows getData() {
        return WebClient.create().get()
                .uri(docUrl, docId, docRange, docToken)
                .retrieve()
                .bodyToMono(ResponseRows.class)
                .block();
    }
}
