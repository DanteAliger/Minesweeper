package com.example.sapper.entity.request;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class GameMoveRequest {
    @NotNull
    @JsonAlias(value = "game_id")
    private String gameId;

    @NotNull
    @PositiveOrZero
    private int col;

    @NotNull
    @PositiveOrZero
    private int row;
}
