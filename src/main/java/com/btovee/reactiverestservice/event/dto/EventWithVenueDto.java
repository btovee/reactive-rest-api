package com.btovee.reactiverestservice.event.dto;

import lombok.Builder;
import lombok.Value;

import java.util.Date;

@Value
@Builder
public class EventWithVenueDto {
    String title;
    String dateStatus;
    String timeZone;
    Date startDate;
    Boolean hiddenFromSearch;
}
