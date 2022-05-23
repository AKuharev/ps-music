package de.plugsurfing.psmusic.adapter;

import de.plugsurfing.psmusic.adapter.dto.MBArtist;
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
public class MusicBrainzAdapter {
    private static final String BASE_URL = "https://musicbrainz.org/ws/2/artist";

    private final WebClient.RequestHeadersUriSpec<?> uriSpec;

    public MusicBrainzAdapter() {
        this.uriSpec = WebClient.create(BASE_URL).get();
    }

    public Mono<MBArtist> get(String mbid) {
        return this.uriSpec.uri(uriBuilder -> this.buildURI(uriBuilder, mbid))
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
