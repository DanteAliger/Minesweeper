package com.example.sapper.entity.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class GameDto {
    @JsonProperty(value = "game_id", index = 1)
    String gameId;
    Integer width;
    Integer height;
    @JsonProperty(value = "mines_count")
    Integer minesCount;
    Boolean completed;
    List<List<String>> field;
}
