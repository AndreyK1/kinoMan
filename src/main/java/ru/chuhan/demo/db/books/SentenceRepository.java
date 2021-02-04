package ru.chuhan.demo.db.books;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.chuhan.demo.entity.book.Sentence;

public interface SentenceRepository extends JpaRepository<Sentence, Long> {
}
