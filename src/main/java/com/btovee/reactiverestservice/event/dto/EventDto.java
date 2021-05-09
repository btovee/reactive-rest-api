package com.btovee.reactiverestservice.event.dto;

import com.btovee.reactiverestservice.venue.dto.VenueDto;
import lombok.Builder;
import lombok.Value;

import java.util.Date;

@Value
@Builder
public class EventDto {
    String title;
    String dateStatus;
    String timeZone;
    Date startDate;
    Boolean hiddenFromSearch;
    VenueDto venue;
}
