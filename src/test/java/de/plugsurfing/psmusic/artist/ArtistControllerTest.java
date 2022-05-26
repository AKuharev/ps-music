package de.plugsurfing.psmusic.artist;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Set;

import static org.mockito.Mockito.*;
import static reactor.core.publisher.Mono.just;

/**
 * @author Andrei Kukharau
 * @since 0.0.1
 */
@WebFluxTest(ArtistController.class)
class ArtistControllerTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private ArtistService artistService;

    @Test
    void artistDetails() {
        String mbid = "0";
        Artist artist = Artist.builder()
                .mbid(mbid)
                .gender("T")
                .description("Description")
                .country("US")
                .disambiguation("King")
                .albums(Set.of(Artist.Album.builder()
                        .id("0")
                        .title("Album")
                        .imageUrl("url")
                        .build()))
                .build();

        var artistMono = just(artist);
        when(this.artistService.collectArtistData(mbid)).thenReturn(artistMono);

        this.webClient.get()
                .uri("/musify/music-artist/details/{mbid}", mbid)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Artist.class);

        verify(this.artistService, times(1)).collectArtistData(mbid);
    }
}
