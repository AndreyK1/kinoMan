package ru.chuhan.demo.entity.book;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Table(name = "sentence")
@Entity
@Data
@Accessors(chain = true)
public class Sentence {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    private int bookId;
    private int rowNum;
    private int paragraphNum;
    @Column(length=1000)
    private String rus;
    @Column(length=1000)
    private String eng;
}
