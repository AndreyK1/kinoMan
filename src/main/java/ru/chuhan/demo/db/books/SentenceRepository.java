package ru.chuhan.demo.db.books;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.chuhan.demo.entity.book.Sentence;

import java.util.List;

@Repository
public interface SentenceRepository extends CrudRepository<Sentence, Integer> {


    List<Sentence> findByBookId(int bookId);

    void deleteByBookId(Integer bookId);

    @Query(value = "select * from Sentence ORDER BY random() LIMIT 1", nativeQuery = true)
    Sentence getRandomRow();

//    SELECT *
//    FROM words
//    WHERE Difficult = 'Easy' AND Category_id = 3
//    ORDER BY random()
//    LIMIT 1;
}
