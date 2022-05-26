package de.plugsurfing.psmusic.adapter.musicbrainz;

import de.plugsurfing.psmusic.artist.Artist;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.Set;

/**
 * @author Andrei Kukharau
 * @since 0.0.1
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MBArtistMapper {
    @Mapping(target = "mbid", source = "mbArtist.id")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "albums", source = "albums")
    Artist to(MBArtist mbArtist, String description, Set<Artist.Album> albums);
}
