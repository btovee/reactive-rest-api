package com.btovee.reactiverestservice.artist.dto;

import com.btovee.reactiverestservice.event.dto.EventDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;

@Value
@Getter
@Builder
public class ArtistDto {

    String name;
    String imgSrc;
    String url;
    Integer rank;
    EventDto[] eventDto;

}
