package com.example.pranav.helloandroid;

import com.google.gson.annotations.SerializedName;

public enum OptionType {
    @SerializedName("0")
    MULTIPLE(0),

    @SerializedName("1")
    SINGLE(1);

    private final int value;
    public int getValue() {
        return value;
    }

    private OptionType(int value) {
        this.value = value;
    }
}
