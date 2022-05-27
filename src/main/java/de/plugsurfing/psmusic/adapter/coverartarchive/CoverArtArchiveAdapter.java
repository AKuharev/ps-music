package de.plugsurfing.psmusic.adapter.coverartarchive;

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
public class CoverArtArchiveAdapter extends Adapter {
    private static final String BASE_URL = "http://coverartarchive.org/release-group";

    public CoverArtArchiveAdapter(WebClient.Builder webClientBuilder) {
        super(webClientBuilder, BASE_URL);
    }

    public Mono<CoverArtArchiveDTO> getData(String albumId) {
        return this.webClient.get()
                .uri(uriBuilder -> this.buildURI(uriBuilder, albumId))
                .retrieve()
                .bodyToMono(CoverArtArchiveDTO.class)
                .retryWhen(Retry.indefinitely()
                        .filter(throwable -> throwable instanceof WebClientResponseException.ServiceUnavailable)
                        .doBeforeRetryAsync(signal -> Mono.delay(Duration.ofSeconds(1)).then()));
    }

    private URI buildURI(UriBuilder uriBuilder, String albumId) {
        return uriBuilder.path("/{albumId}")
                .build(albumId);
    }
}
