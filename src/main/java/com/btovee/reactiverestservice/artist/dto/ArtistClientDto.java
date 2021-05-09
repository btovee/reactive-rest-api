package com.btovee.reactiverestservice.artist.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ArtistClientDto {

    @JsonProperty("name")
    public String name;

    @JsonProperty("id")
    public String id;

    @JsonProperty("imgSrc")
    public String imgSrc;

    @JsonProperty("url")
    public String url;

    @JsonProperty("rank")
    public Integer rank;

}
