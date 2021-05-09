package com.btovee.reactiverestservice.venue;

import com.btovee.reactiverestservice.venue.dto.VenueWithEventDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import static com.btovee.reactiverestservice.config.swagger.SwaggerTags.ARTIST;
import static com.btovee.reactiverestservice.config.swagger.SwaggerTags.VENUE;

@RestController
@RequestMapping("venue")
@RequiredArgsConstructor
@Api(tags = VENUE)
public class VenueController {

    private final VenueService venueService;

    @GetMapping("{id}")
    @ApiOperation(value = "Get venue information for a specific venueId", tags = ARTIST)
    @ApiResponses({
            // TODO: switch to HttpStatus.OK
            @ApiResponse(code = 200, message = "Retrieved venue information", response = VenueWithEventDto.class)
    })
    public Mono<VenueWithEventDto> getVenue(@PathVariable String id) {
        return venueService.getVenue(id);
    }
}
