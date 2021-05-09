package com.btovee.reactiverestservice.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class EventClientDto {

    @JsonProperty("title")
    public String title;

    @JsonProperty("id")
    public String id;

    @JsonProperty("dateStatus")
    public String dateStatus;

    @JsonProperty("timeZone")
    public String timeZone;

    @JsonProperty("startDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'hh:mm:ss")
    public Date startDate;

    @JsonProperty("artists")
    public IdDto[] artists;

    @JsonProperty("venue")
    public IdDto venue;

    @JsonProperty("hiddenFromSearch")
    public Boolean hiddenFromSearch;

}
