package com.example.sapper.entity.request;


import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CreateGameRequest {
    @NotNull
    @Positive
    @Max(value = 30)
    private int width;

    @NotNull
    @Positive
    @Max(value = 30)
    private int height;

    @Positive
    @JsonAlias(value = "mines_count")
    private int minesCount;
}
