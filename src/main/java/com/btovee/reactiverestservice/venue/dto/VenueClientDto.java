package com.btovee.reactiverestservice.venue.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class VenueClientDto {

    @JsonProperty("name")
    public String name;

    @JsonProperty("url")
    public String url;

    @JsonProperty("city")
    public String city;

    @JsonProperty("id")
    public String id;

}
