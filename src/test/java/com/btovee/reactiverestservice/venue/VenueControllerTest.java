package com.btovee.reactiverestservice.venue;

import com.btovee.reactiverestservice.artist.dto.ArtistDto;
import com.btovee.reactiverestservice.event.dto.EventWithVenueDto;
import com.btovee.reactiverestservice.venue.dto.VenueWithEventDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Calendar;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = VenueController.class)
class VenueControllerTest {

    @MockBean
    VenueService venueService;

    @Autowired
    private WebTestClient webClient;

    @Test
    void getVenueById() {

        EventWithVenueDto eventWithVenueDto = EventWithVenueDto.builder()
                .title("Fusion Prog")
                .dateStatus("singleDate")
                .hiddenFromSearch(false)
                .timeZone("Europe/London")
                .startDate(new Date(2009, Calendar.JANUARY, 11))
                .build();

        VenueWithEventDto expected = VenueWithEventDto.builder()
                .name("O2 Academy Sheffield")
                .url("/o2-academy-sheffield-tickets-sheffield/venue/41")
                .city("Sheffield")
                .events(new EventWithVenueDto[]{eventWithVenueDto})
                .build();

        when(venueService.getVenue(any()))
                .thenReturn(Mono.just(expected));

        webClient.get().uri("/venue/{id}", "41")
                .header(HttpHeaders.ACCEPT, "application/json")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ArtistDto.class);

        verify(venueService, times(1)).getVenue("41");

    }

}