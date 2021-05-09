package com.btovee.reactiverestservice.artist;


import com.btovee.reactiverestservice.artist.dto.ArtistDto;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import static com.btovee.reactiverestservice.config.swagger.SwaggerTags.ARTIST;


@RestController
@RequestMapping("artist")
@RequiredArgsConstructor
@Api(tags = ARTIST)
public class ArtistController {

    private final ArtistService artistService;

    // TODO: Add more logging

    @GetMapping("{id}")
    @ApiOperation(value = "Get artist information for a specific artistId", tags = ARTIST)
    @ApiResponses({
            // TODO: switch to HttpStatus.OK
            @ApiResponse(code = 200, message = "Retrieved artist information", response = ArtistDto.class)
    })
    public Mono<ArtistDto> getArtist(@PathVariable String id) {
        return artistService.getArtist(id);
    }

}
