package ru.chuhan.demo.entity.book;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "books")
@Entity
@Data
@Accessors(chain = true)
public class Books {
    @Id
    @GeneratedValue
    private int id;

    private String name;

    private String author;

}
