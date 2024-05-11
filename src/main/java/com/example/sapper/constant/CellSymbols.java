package com.example.sapper.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CellSymbols {
    ZERO("0"),
    NOT_OPENED(" "),
    MINE("M"),
    MINE_SYMBOL_FOR_SHOW("X"),
    ;
    private final String value;
}
