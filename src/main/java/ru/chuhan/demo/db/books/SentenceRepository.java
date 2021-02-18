package ru.chuhan.demo.db.books;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.chuhan.demo.entity.book.Sentence;

import java.util.List;

@Repository
public interface SentenceRepository extends CrudRepository<Sentence, Long> {


    List<Sentence> findByBookId(int bookId);

    void deleteByBookId(Integer bookId);
}
