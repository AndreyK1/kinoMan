package ru.chuhan.demo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ru.chuhan.demo.entitysecur.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Theme  implements Serializable {
    @Id
    @GeneratedValue
    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    private String name;


//    @JsonProperty("user")
    @JsonProperty("user_id")
    @Column(columnDefinition="bigint default 0")
//    @OneToOne(mappedBy="theme")
    private long user_id;
//    private User user;

    @OneToMany(mappedBy="theme")
    @JsonProperty("answers")
    private List<TreeItem> answers;

    @Column(columnDefinition="integer default 0")
    @JsonProperty("start_id")
    private int start_id;
}
