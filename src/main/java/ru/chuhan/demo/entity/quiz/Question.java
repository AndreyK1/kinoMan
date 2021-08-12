package ru.chuhan.demo.entity.quiz;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "question")
@Entity
@Data
@Accessors(chain = true)
public class Question {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    private int quizNum;
    private int rowNum;
    private int partNum;
    @Column(length=1000)
    private String rus;
    @Column(length=1000)
    private String eng;
    private String answer;
}
