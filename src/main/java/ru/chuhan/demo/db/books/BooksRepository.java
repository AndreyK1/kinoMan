package ru.chuhan.demo.db.books;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.chuhan.demo.entity.book.Book;

public interface BooksRepository  extends JpaRepository<Book, Long> {
}
