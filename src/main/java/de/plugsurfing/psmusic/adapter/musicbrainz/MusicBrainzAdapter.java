package de.plugsurfing.psmusic.adapter.musicbrainz;

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
public class MusicBrainzAdapter extends Adapter {
    private static final String BASE_URL = "https://musicbrainz.org/ws/2/artist";

    public MusicBrainzAdapter(WebClient.Builder webClientBuilder) {
        super(webClientBuilder, BASE_URL);
    }

    public Mono<MBArtist> getData(String mbid) {
        return this.webClient.get()
                .uri(uriBuilder -> this.buildURI(uriBuilder, mbid))
                .retrieve()
                .bodyToMono(MBArtist.class);
    }

    private URI buildURI(UriBuilder uriBuilder, String mbid) {
        return uriBuilder.path("/{mbid}")
                .queryParam("inc", "url-rels+release-groups")
                .queryParam("fmt", "json")
                .build(mbid);
    }
}
