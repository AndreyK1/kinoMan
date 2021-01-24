package ru.chuhan.demo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AnswerRest {
    @JsonProperty("parent_id")
    public String parent_id;
    @JsonProperty("text")
    public String text;
    @JsonProperty("foundid_to_use")
    public String foundid_to_use;
    @JsonProperty("for_theme")
    public int for_theme;
    @JsonProperty("member")
    public int member;

    public AnswerRest(String parent_id, String text, String foundid_to_use) {
        parent_id = parent_id;
        text = text;
        foundid_to_use = foundid_to_use;
    }

    public AnswerRest() {}
}
