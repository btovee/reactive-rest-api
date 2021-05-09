package com.btovee.reactiverestservice.venue;

import com.btovee.reactiverestservice.artist.dto.ArtistClientDto;
import com.btovee.reactiverestservice.event.EventsClient;
import com.btovee.reactiverestservice.event.dto.EventClientDto;
import com.btovee.reactiverestservice.event.dto.EventWithVenueDto;
import com.btovee.reactiverestservice.venue.dto.VenueClientDto;
import com.btovee.reactiverestservice.venue.dto.VenueWithEventDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VenueService {

    private final EventsClient eventsClient;
    private final VenueClient venueClient;

    public Mono<VenueWithEventDto> getVenue(String venueId) {

        return Mono.zip(eventsClient.getEvents(), venueClient.getVenues())
                .map((r) -> {

                    EventClientDto[] eventClientDtos = r.getT1();
                    VenueClientDto[] venueClientDtos = r.getT2();

                    Optional<VenueClientDto> venueClientDtoOptional = Arrays.stream(venueClientDtos).filter(venue -> venueId.equals(venue.getId())).findFirst();

                    if (venueClientDtoOptional.isPresent()) {
                        VenueClientDto venueClientDto = venueClientDtoOptional.get();
                        EventWithVenueDto[] eventDtos = Arrays.stream(eventClientDtos)
                                .filter(eventClientDto -> venueId.equals(eventClientDto.getVenue().getId()))
                                .map(eventClientDto -> EventWithVenueDto.builder()
                                        .dateStatus(eventClientDto.getDateStatus())
                                        .startDate(eventClientDto.getStartDate())
                                        .timeZone(eventClientDto.getTimeZone())
                                        .title(eventClientDto.getTitle())
                                        .hiddenFromSearch(eventClientDto.getHiddenFromSearch())
                                        .build()).toArray(EventWithVenueDto[]::new);

                        return VenueWithEventDto.builder()
                                .name(venueClientDto.getName())
                                .city(venueClientDto.getCity())
                                .url(venueClientDto.getUrl())
                                .events(eventDtos)
                                .build();
                    }
                    return VenueWithEventDto.builder().build();
                });
    }
}
