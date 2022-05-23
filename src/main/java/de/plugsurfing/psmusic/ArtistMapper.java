package de.plugsurfing.psmusic;

import de.plugsurfing.psmusic.adapter.dto.MBArtist;
import de.plugsurfing.psmusic.web.dto.Artist;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/**
 * @author Andrei Kukharau
 * @since 0.0.1
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ArtistMapper {
    @Mapping(target = "description", ignore = true)
    @Mapping(target = "albums", ignore = true)
    Artist to(String mbid, MBArtist mbArtist);
}
