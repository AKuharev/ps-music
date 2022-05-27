package de.plugsurfing.psmusic.adapter.wikidata;

import de.plugsurfing.psmusic.adapter.Adapter;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.net.URI;
import java.time.Duration;

/**
 * @author Andrei Kukharau
 * @since 0.0.1
 */
@Component
public class WikidataAdapter extends Adapter {
    private static final String BASE_URL = "https://www.wikidata.org/wiki/Special:EntityData";

    public WikidataAdapter(WebClient.Builder webClientBuilder) {
        super(webClientBuilder, BASE_URL);
    }

    public Mono<WikidataEntityData> getData(String wikiResourceId) {
        return this.webClient.get()
                .uri(uriBuilder -> this.buildURI(uriBuilder, wikiResourceId))
                .retrieve()
                .bodyToMono(WikidataEntityData.class)
                .retryWhen(Retry.indefinitely()
                        .filter(throwable -> throwable instanceof WebClientResponseException.ServiceUnavailable)
                        .doBeforeRetryAsync(signal -> Mono.delay(Duration.ofMillis(200)).then()));
    }

    private URI buildURI(UriBuilder uriBuilder, String wikiResourceId) {
        return uriBuilder.path("/{wikiResourceId}.json")
                .build(wikiResourceId);
    }
}
