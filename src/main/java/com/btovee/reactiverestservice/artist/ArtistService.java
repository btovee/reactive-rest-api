package com.btovee.reactiverestservice.artist;

import com.btovee.reactiverestservice.artist.dto.ArtistClientDto;
import com.btovee.reactiverestservice.artist.dto.ArtistDto;
import com.btovee.reactiverestservice.event.EventsClient;
import com.btovee.reactiverestservice.event.dto.EventClientDto;
import com.btovee.reactiverestservice.event.dto.EventDto;
import com.btovee.reactiverestservice.venue.VenueClient;
import com.btovee.reactiverestservice.venue.dto.VenueClientDto;
import com.btovee.reactiverestservice.venue.dto.VenueDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArtistService {

    private final ArtistClient artistClient;
    private final EventsClient eventsClient;
    private final VenueClient venueClient;

    public Mono<ArtistDto> getArtist(String id) {

        return Mono.zip(artistClient.getArtists(), eventsClient.getEvents(), venueClient.getVenues())
                .map((r) -> {
                    ArtistClientDto[] artistClientDtos = r.getT1();
                    Optional<ArtistClientDto> artistOptional = Arrays.stream(artistClientDtos).filter((artistClientDto -> id.equals(artistClientDto.id))).findFirst();
                    if (artistOptional.isPresent()) {
                        ArtistClientDto artistClientDto = artistOptional.get();

                        EventClientDto[] eventClientDtos = r.getT2();
                        VenueClientDto[] venueClientDtos = r.getT3();

                        Map<String, VenueClientDto> venueClientDtosById = Arrays.stream(venueClientDtos).collect(Collectors.toMap(VenueClientDto::getId, venueClientDto -> venueClientDto));

                        EventDto[] eventDtos = Arrays.stream(eventClientDtos)
                                .filter(eventClientDto -> Arrays.stream(eventClientDto.getArtists()).anyMatch(idDto -> id.equals(idDto.getId())))
                                .map(eventClientDto -> {
                                    VenueClientDto currentVenue = venueClientDtosById.get(eventClientDto.getVenue().getId());
                                    VenueDto venueDto = VenueDto.builder()
                                            .city(currentVenue.getCity())
                                            .name(currentVenue.getName())
                                            .url(currentVenue.getUrl())
                                            .build();
                                    return EventDto.builder()
                                            .dateStatus(eventClientDto.getDateStatus())
                                            .startDate(eventClientDto.getStartDate())
                                            .timeZone(eventClientDto.getTimeZone())
                                            .title(eventClientDto.getTitle())
                                            .hiddenFromSearch(eventClientDto.getHiddenFromSearch())
                                            .venue(venueDto)
                                            .build();
                                }).toArray(EventDto[]::new);


                        return ArtistDto.builder()
                                .name(artistClientDto.getName())
                                .imgSrc(artistClientDto.getImgSrc())
                                .url(artistClientDto.getUrl())
                                .rank(artistClientDto.getRank())
                                .eventDto(eventDtos)
                                .build();
                    }
                    return ArtistDto.builder().build();
                });


    }
}
