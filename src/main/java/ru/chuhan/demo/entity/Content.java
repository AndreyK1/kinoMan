package ru.chuhan.demo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Content {
    @JsonProperty("id")
    public String customer_id;
    @JsonProperty("content")
    public String customer_content;

    public Content(String id, String content) {
        customer_id = id;
        customer_content = content;
    }

    public Content() {}
}
