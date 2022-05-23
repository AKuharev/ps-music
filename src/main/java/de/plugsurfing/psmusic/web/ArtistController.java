package de.plugsurfing.psmusic.web;

import de.plugsurfing.psmusic.ArtistMapper;
import de.plugsurfing.psmusic.adapter.MusicBrainzAdapter;
import de.plugsurfing.psmusic.web.dto.Artist;
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
    private final MusicBrainzAdapter musicBrainzAdapter;
    private final ArtistMapper artistMapper;

    @GetMapping("/details/{mbid}")
    public Mono<Artist> artistDetails(@PathVariable String mbid) {
        return this.musicBrainzAdapter.get(mbid)
                .map(mbArtist -> this.artistMapper.to(mbid, mbArtist));
    }
}
