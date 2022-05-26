package de.plugsurfing.psmusic.artist;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author Andrei Kukharau
 * @since 0.0.1
 */
@RestController
@RequestMapping("/musify/music-artist")
@RequiredArgsConstructor
class ArtistController {
    private final ArtistService artistService;

    @GetMapping("/details/{mbid}")
    public Mono<Artist> artistDetails(@PathVariable String mbid) {
        return this.artistService.collectArtistData(mbid);
    }
}
