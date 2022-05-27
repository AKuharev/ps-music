package de.plugsurfing.psmusic.artist;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * @author Andrei Kukharau
 * @since 0.0.1
 */
@Slf4j
@RestController
@RequestMapping("/musify/music-artist")
@RequiredArgsConstructor
class ArtistController {
    private final ArtistService artistService;

    @GetMapping("/details/{mbid}")
    public Mono<Artist> artistDetails(@PathVariable String mbid) {
        return this.artistService.collectArtistData(mbid);
    }

    @ExceptionHandler(ArtistNotFoundException.class)
    ResponseEntity<Object> artistNotFound(ArtistNotFoundException exception) {
        log.debug("Artist not found", exception);
        return ResponseEntity.notFound().build();
    }
}
