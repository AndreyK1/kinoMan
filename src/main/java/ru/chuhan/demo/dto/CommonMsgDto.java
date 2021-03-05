package ru.chuhan.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CommonMsgDto {

    @JsonProperty("message")
    private String message;
}
