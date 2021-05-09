package com.btovee.reactiverestservice.venue.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VenueDto {
    public String name;
    public String url;
    public String city;
}
