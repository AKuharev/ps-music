package de.plugsurfing.psmusic.adapter.wikipedia;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

/**
 * @author Andrei Kukharau
 * @since 0.0.1
 */
@Getter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class WikipediaDTO {
    private String extractHtml;
}
