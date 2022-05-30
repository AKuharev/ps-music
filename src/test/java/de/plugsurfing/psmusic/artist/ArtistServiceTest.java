package de.plugsurfing.psmusic.artist;

import de.plugsurfing.psmusic.adapter.coverartarchive.CoverArtArchiveAdapter;
import de.plugsurfing.psmusic.adapter.coverartarchive.CoverArtArchiveDTO;
import de.plugsurfing.psmusic.adapter.musicbrainz.MBArtist;
import de.plugsurfing.psmusic.adapter.musicbrainz.MBArtistMapperImpl;
import de.plugsurfing.psmusic.adapter.musicbrainz.MusicBrainzAdapter;
import de.plugsurfing.psmusic.adapter.musicbrainz.NoWikidataResourceIdException;
import de.plugsurfing.psmusic.adapter.wikidata.WikidataAdapter;
import de.plugsurfing.psmusic.adapter.wikidata.WikidataEntityData;
import de.plugsurfing.psmusic.adapter.wikipedia.WikipediaAdapter;
import de.plugsurfing.psmusic.adapter.wikipedia.WikipediaDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.test.StepVerifier;

import java.util.Set;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static reactor.core.publisher.Mono.just;

/**
 * @author Andrei Kukharau
 * @since 0.0.1
 */
@ExtendWith(MockitoExtension.class)
class ArtistServiceTest {
    private static final String MBID = "0";

    @InjectMocks
    private ArtistService artistService;

    @Mock
    private MusicBrainzAdapter musicBrainzAdapter;

    @Mock
    private MBArtistMapperImpl mbArtistMapper;

    @Mock
    private WikidataAdapter wikidataAdapter;

    @Mock
    private WikipediaAdapter wikipediaAdapter;

    @Mock
    private CoverArtArchiveAdapter coverArtArchiveAdapter;

    @Test
    void collectArtistData() {
        var artist = Artist.builder()
                .build();

        var albumId = "albumId";
        var releaseGroup = new MBArtist.ReleaseGroup(albumId, "album");
        var wikiResourceId = "wikiResourceId";
        var mbArtist = mock(MBArtist.class);
        when(mbArtist.getWikiResourceId()).thenReturn(wikiResourceId);
        when(mbArtist.getReleaseGroups()).thenReturn(Set.of(releaseGroup));

        var enTitle = "enTitle";
        var wikidataEntityData = mock(WikidataEntityData.class);
        when(wikidataEntityData.getEnTitle(wikiResourceId)).thenReturn(enTitle);

        var extractHtml = "extractHtml";
        var wikipediaDTO = mock(WikipediaDTO.class);
        when(wikipediaDTO.getExtractHtml()).thenReturn(extractHtml);

        var imageUrl = "imageUrl";
        var coverArtArchiveDTO = mock(CoverArtArchiveDTO.class);
        when(coverArtArchiveDTO.getFrontImageUrl()).thenReturn(imageUrl);

        when(this.musicBrainzAdapter.getData(anyString())).thenReturn(just(mbArtist));
        when(this.mbArtistMapper.to(eq(mbArtist), anyString(), anySet())).thenReturn(artist);
        when(this.wikidataAdapter.getData(wikiResourceId)).thenReturn(just(wikidataEntityData));
        when(this.wikipediaAdapter.getData(enTitle)).thenReturn(just(wikipediaDTO));
        when(this.coverArtArchiveAdapter.getData(albumId)).thenReturn(just(coverArtArchiveDTO));

        var artistMono = this.artistService.collectArtistData(MBID);

        StepVerifier.create(artistMono)
                .expectNext(artist)
                .verifyComplete();
    }

    @Test
    void collectArtistData_whenNoWikidataResourceIdException() {
        var albumId = "albumId";
        var releaseGroup = new MBArtist.ReleaseGroup(albumId, "album");
        var mbArtist = mock(MBArtist.class);
        when(mbArtist.getWikiResourceId()).thenThrow(NoWikidataResourceIdException.class);
        when(mbArtist.getReleaseGroups()).thenReturn(Set.of(releaseGroup));
        when(this.musicBrainzAdapter.getData(anyString())).thenReturn(just(mbArtist));

        var artistMono = this.artistService.collectArtistData(MBID);

        StepVerifier.create(artistMono)
                .expectError(NullPointerException.class)
                .verify();
    }
}