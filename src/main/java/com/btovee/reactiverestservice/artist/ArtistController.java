package com.btovee.reactiverestservice.artist;


import com.btovee.reactiverestservice.artist.dto.ArtistDto;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import static com.btovee.reactiverestservice.config.swagger.SwaggerTags.ARTIST;


@RestController
@RequestMapping("artist")
@RequiredArgsConstructor
@Api(tags = ARTIST)
public class ArtistController {

    private final ArtistService artistService;

    @GetMapping("{id}")
    @ApiOperation(value = "Get artist information for a specific artistId", tags = ARTIST)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Retrieved artist information", response = ArtistDto.class)
    })
    @ResponseStatus(HttpStatus.OK)
    public Mono<ArtistDto> getArtist(@PathVariable String id) {
        return artistService.getArtist(id);
    }

}
