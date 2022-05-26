package de.plugsurfing.psmusic.adapter.wikipedia;

import de.plugsurfing.psmusic.adapter.Adapter;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;

/**
 * @author Andrei Kukharau
 * @since 0.0.1
 */
@Component
public class WikipediaAdapter extends Adapter {
    private static final String BASE_URL = "https://en.wikipedia.org/api/rest_v1/page/summary/";

    public WikipediaAdapter(WebClient.Builder webClientBuilder) {
        super(webClientBuilder, BASE_URL);
    }

    public Mono<WikipediaDTO> getData(String title) {
        return this.webClient.get()
                .uri(uriBuilder -> this.buildURI(uriBuilder, title))
                .retrieve()
                .bodyToMono(WikipediaDTO.class);
    }

    private URI buildURI(UriBuilder uriBuilder, String title) {
        return uriBuilder.path("/{title}")
                .build(title);
    }
}
