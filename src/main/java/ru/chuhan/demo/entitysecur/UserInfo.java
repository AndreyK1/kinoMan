package ru.chuhan.demo.entitysecur;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class UserInfo {
    @JsonProperty("name")
    private String name;
    @JsonProperty("roles")
    private List<String> roles;
}
