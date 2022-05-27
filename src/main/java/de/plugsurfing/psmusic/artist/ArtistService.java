package de.plugsurfing.psmusic.artist;

import de.plugsurfing.psmusic.adapter.coverartarchive.CoverArtArchiveAdapter;
import de.plugsurfing.psmusic.adapter.coverartarchive.CoverArtArchiveDTO;
import de.plugsurfing.psmusic.adapter.musicbrainz.MBArtist;
import de.plugsurfing.psmusic.adapter.musicbrainz.MBArtistMapper;
import de.plugsurfing.psmusic.adapter.musicbrainz.MusicBrainzAdapter;
import de.plugsurfing.psmusic.adapter.wikidata.WikidataAdapter;
import de.plugsurfing.psmusic.adapter.wikipedia.WikipediaAdapter;
import de.plugsurfing.psmusic.adapter.wikipedia.WikipediaDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Set;
import java.util.stream.Collectors;

import static reactor.core.publisher.Mono.just;
import static reactor.core.publisher.Mono.zip;

/**
 * @author Andrei Kukharau
 * @since 0.0.1
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ArtistService {
    private final MusicBrainzAdapter musicBrainzAdapter;
    private final MBArtistMapper mbArtistMapper;

    private final WikidataAdapter wikidataAdapter;

    private final WikipediaAdapter wikipediaAdapter;

    private final CoverArtArchiveAdapter coverArtArchiveAdapter;

    public Mono<Artist> collectArtistData(String mbid) {
        return this.musicBrainzAdapter.getData(mbid)
                .doOnError(throwable -> log.error(throwable.getMessage(), throwable))
                .onErrorMap(throwable -> new ArtistNotFoundException(mbid, throwable))
                .flatMap(it -> zip(this.getDescription(it), this.getAlbums(it.getReleaseGroups()),
                        (description, albums) -> this.mbArtistMapper.to(it, description, albums)));
    }

    private Mono<String> getDescription(MBArtist artist) {
        return just(artist)
                .map(MBArtist::getWikiResourceId)
                .doOnError(throwable -> log.error(throwable.getMessage(), throwable))
                .onErrorReturn("no description")
                .flatMap(it -> this.wikidataAdapter.getData(it)
                        .flatMap(wikidataEntityData -> this.wikipediaAdapter.getData(wikidataEntityData.getEnTitle(it)))
                        .map(WikipediaDTO::getExtractHtml));

    }

    private Mono<Set<Artist.Album>> getAlbums(Set<MBArtist.ReleaseGroup> releaseGroups) {
        return Flux.fromIterable(releaseGroups)
                .delayElements(Duration.ofMillis(100))
                .flatMap(it -> zip(just(it), this.coverArtArchiveAdapter.getData(it.id()), this::createAlbum))
                .collect(Collectors.toSet());
    }

    private Artist.Album createAlbum(MBArtist.ReleaseGroup releaseGroup, CoverArtArchiveDTO coverArtArchiveDTO) {
        return new Artist.Album(releaseGroup.id(), releaseGroup.title(), coverArtArchiveDTO.getFrontImageUrl());
    }
}
