package com.btovee.reactiverestservice.venue.dto;

import com.btovee.reactiverestservice.event.dto.EventWithVenueDto;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class VenueWithEventDto  {
    public String name;
    public String url;
    public String city;
    EventWithVenueDto[] events;
}
