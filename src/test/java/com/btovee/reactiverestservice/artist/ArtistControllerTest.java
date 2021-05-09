package com.btovee.reactiverestservice.artist;

import static org.mockito.Mockito.times;

import com.btovee.reactiverestservice.artist.dto.ArtistDto;
import com.btovee.reactiverestservice.event.dto.EventDto;
import com.btovee.reactiverestservice.venue.dto.VenueDto;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = ArtistController.class)
class ArtistControllerTest {

    @MockBean
    ArtistService artistService;

    @Autowired
    private WebTestClient webClient;

    @Test
    void getArtistById() {

        VenueDto venueDto = VenueDto.builder()
                .name("O2 Academy Sheffield")
                .url("/o2-academy-sheffield-tickets-sheffield/venue/41")
                .city("Sheffield")
                .build();

        EventDto eventDto = EventDto.builder()
                .title("Fusion Prog")
                .dateStatus("singleDate")
                .timeZone("Europe/London")
                .startDate(new Date(2009, Calendar.JANUARY, 11))
                .hiddenFromSearch(false)
                .venue(venueDto)
                .build();

        ArtistDto expected = ArtistDto.builder()
                .name("HRH Prog")
                .url("/hrh-prog-tickets/artist/21")
                .imgSrc("//some-base-url/hrh-prog.jpg")
                .rank(1)
                .eventDto(new EventDto[]{eventDto})
                .build();


        when(artistService.getArtist(any()))
                .thenReturn(Mono.just(expected));

        webClient.get().uri("/artist/{id}", "22")
                .header(HttpHeaders.ACCEPT, "application/json")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ArtistDto.class);

        verify(artistService, times(1)).getArtist("22");
    }

}