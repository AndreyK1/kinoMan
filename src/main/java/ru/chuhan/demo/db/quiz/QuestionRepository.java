package ru.chuhan.demo.db.quiz;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.chuhan.demo.entity.book.Sentence;
import ru.chuhan.demo.entity.quiz.Question;

import java.util.List;

@Repository
public interface QuestionRepository extends CrudRepository<Question, Integer> {


    List<Question> findByQuizNumAndPartNum(int quizNum, int partNum);

    Question findByQuizNumAndPartNumAndRowNum(int quizNum, int partNum, int rowNum);

    void deleteByQuizNum(Integer bookId);



    @Query(value = "select * from Question ORDER BY random() LIMIT 1", nativeQuery = true)
    Question getRandomRow();

}
